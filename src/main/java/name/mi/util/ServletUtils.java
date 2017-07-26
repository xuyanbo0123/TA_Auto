package name.mi.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ServletUtils {
    private HttpServletRequest mRequest;

    private Logger mLogger;

    public ServletUtils(HttpServletRequest iRequest, Logger iLogger) {
        mRequest = iRequest;
        mLogger = iLogger;
    }

    public String getStringParameterWithDefault(String iKey, String iDefault
    ) throws Exception {
        String
                vStrValue = mRequest.getParameter(iKey);

        mLogger.info(iKey + "=" + vStrValue);
        if (vStrValue == null || vStrValue.isEmpty()) {
            vStrValue = iDefault;
        }
        return vStrValue;
    }

    public String getStringParameter(String iKey
    ) throws Exception {
        String
                vStrValue = mRequest.getParameter(iKey);

        mLogger.info(iKey + "=" + vStrValue);
        if (vStrValue == null || vStrValue.isEmpty()) {
            throw new IllegalStateException("Missing Parameter " + iKey);
        }
        return vStrValue;
    }

    public int getIntParameterWithDefault(String iKey, int iDefault) {
        try {
            String
                    vStrValue = getStringParameter(iKey);
            return Integer.parseInt(vStrValue);
        } catch (Exception e) {
            return iDefault;
        }
    }

    public int getIntParameter(String iKey)
            throws Exception {
        String
                vStrValue = getStringParameter(iKey);
        int vIntValue = Integer.parseInt(vStrValue);
        return vIntValue;
    }

    public int[] getIntParameters(String iKey, int iLength)
            throws Exception {
        String[]
                vStrValues = getStringParameters(iKey, iLength);

        int[] vIntValues = UtilityFunctions.parseIntArrFromStringArr(vStrValues);
        return vIntValues;
    }

    public Enum getEnumParameterWithDefault(String iKey, Class iEnumClass, Enum iDefault
    ) throws Exception {
        try {
            String
                    vStrValue = getStringParameter(iKey);
            Enum vEnumValue = UtilityFunctions.parseEnumFromStringWithDefault(vStrValue, iEnumClass);
            if (vEnumValue == null) {
                return iDefault;
            }
            return vEnumValue;
        } catch (Exception e) {
            return iDefault;
        }
    }

    public String getJSONParameterMap()
            throws Exception
    {
        ObjectMapper vMapper = new ObjectMapper();
        String vJSON = null;
        Map<String, String[]> vParameterMap = mRequest.getParameterMap();
        vJSON = vMapper.writeValueAsString(vParameterMap);
        return vJSON;
    }

    public Enum getEnumParameter(String iKey, Class iEnumClass)
            throws Exception {
        String
                vStrValue = getStringParameter(iKey);
        Enum vEnumValue = UtilityFunctions.parseEnumFromStringWithDefault(vStrValue, iEnumClass);
        if (vEnumValue == null) {
            throw new IllegalStateException("Invalid value " + iKey);
        }
        return vEnumValue;
    }

    public String[] getStringParameters(String iKey)
            throws Exception {
        String[]
                vStrValues = mRequest.getParameterValues(iKey);

        mLogger.info(iKey + "=" + StringUtils.join(vStrValues, ","));

        if (vStrValues == null || vStrValues.length == 0) {
            throw new IllegalStateException("Missing Parameter " + iKey);
        }

        return vStrValues;
    }

    public String[] getStringParameters(String iKey, int iLength)
            throws Exception {
        String[]
                vStrValues = getStringParameters(iKey);

        if (vStrValues.length != iLength) {
            throw new IllegalStateException("Parameter number not match for " + iKey + ", wanted " + iLength + " but get " + vStrValues.length + ".");
        }
        return vStrValues;
    }

    public long getLongParameter(String iKey)
            throws Exception {
        String
                vStrValue = getStringParameter(iKey);
        long vLongValue = Long.parseLong(vStrValue);
        return vLongValue;
    }

    public long[] getLongParameters(String iKey, int iLength)
            throws Exception {
        String[]
                vStrValues = getStringParameters(iKey, iLength);

        long[] vLongValues = UtilityFunctions.parseLongArrFromStringArr(vStrValues);
        return vLongValues;
    }

    public double[] getDoubleParameters(String iKey, int iLength)
            throws Exception {
        String[]
                vStrValues = getStringParameters(iKey, iLength);

        double[] vDoubleValues = UtilityFunctions.parseDoubleArrFromStringArr(vStrValues);
        return vDoubleValues;
    }
}
