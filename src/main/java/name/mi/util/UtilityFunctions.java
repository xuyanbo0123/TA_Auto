package name.mi.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityFunctions {
    public static String toYesNo(Boolean vValue) {
        if (vValue == null)
            return null;
        if (vValue)
            return "Yes";
        else
            return "No";
    }

    public static String encode64(String iStr) {
        return Base64.encodeBase64URLSafeString(iStr.getBytes());
    }

    public static String decode64(String iStr64) {
        try {
            String vStr = new String(Base64.decodeBase64(iStr64), "UTF-8");
            return vStr;
        } catch (Exception e) {
            return null;
        }
    }

    public static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    public static double[] parseDoubleArrFromStringArr(String[] iValue
    ) throws Exception {
        double[] vDoubles = new double[iValue.length];
        for (int i = 0; i < iValue.length; i++) {
            vDoubles[i] = Double.parseDouble(iValue[i]);
        }
        return vDoubles;
    }

    public static long[] parseLongArrFromStringArr(String[] iValue) {
        long[] vLongs = new long[iValue.length];
        for (int i = 0; i < iValue.length; i++) {
            try {
                vLongs[i] = Long.parseLong(iValue[i]);
            } catch (Exception e) {
                vLongs[i] = -1;
            }
        }
        return vLongs;
    }

    public static int[] parseIntArrFromStringArr(String[] iValue) {
        int[] vInts = new int[iValue.length];
        for (int i = 0; i < iValue.length; i++) {
            try {
                vInts[i] = Integer.parseInt(iValue[i]);
            } catch (Exception e) {
                vInts[i] = -1;
            }
        }
        return vInts;
    }

    /**
     * Takes 0 or 1, and converts to false or true, respectively
     *
     * @param iValue
     * @return
     */
    public static boolean parseBooleanFromInt(int iValue) {
        if (iValue < 0 || iValue > 1) {
            throw new IllegalArgumentException("Input must be 0 or 1");
        }

        return iValue == 1;
    }

    /**
     * Takes "0" or "1"
     *
     * @param iValue
     * @return
     */
    public static boolean parseBooleanFromString(String iValue) {
        if (iValue == null || !iValue.equals("0") && !iValue.equals("1")) {
            throw new IllegalArgumentException("Input must be 0 or 1");
        }

        int vValue = Integer.parseInt(iValue);
        return parseBooleanFromInt(vValue);
    }

    public static boolean parseBooleanFromIntWithDefault(int iValue, boolean iDefault) {
        if (iValue < 0 || iValue > 1) {
            return iDefault;
        }
        return iValue == 1;
    }

    public static boolean parseBooleanFromIntWithFalseDefault(int iValue) {
        return parseBooleanFromIntWithDefault(iValue, false);
    }

    public static boolean parseBooleanFromStringWithDefault(String iValue, boolean iDefault) {
        if (iValue == null || !iValue.equals("0") && !iValue.equals("1")) {
            return iDefault;
        }

        int vValue = Integer.parseInt(iValue);
        return parseBooleanFromIntWithDefault(vValue, iDefault);
    }

    public static boolean parseBooleanFromStringWithFalseDefault(String iValue) {
        return parseBooleanFromStringWithDefault(iValue, false);
    }

    /**
     * ********************************************************************************
     */

    public static int parseIntFromStringWithDefault(String iValue, int iDefault) {
        int vValue = 0;

        if (iValue == null) {
            return iDefault;
        }

        try {
            vValue = Integer.parseInt(iValue);
        } catch (NumberFormatException e) {
            vValue = iDefault;
        }

        return vValue;
    }

    public static int parseIntFromStringWithDefault(String iValue) {
        return parseIntFromStringWithDefault(iValue, DBConstants.DB_DEFAULT_INT);
    }

    /**
     * ********************************************************************************
     */

    public static long parseLongFromStringWithDefault(String iValue, long iDefault) {
        long vValue = 0;

        if (iValue == null) {
            return iDefault;
        }

        try {
            vValue = Long.parseLong(iValue);
        } catch (NumberFormatException e) {
            vValue = iDefault;
        }

        return vValue;
    }

    public static long parseLongFromStringWithDefault(String iValue) {
        return parseLongFromStringWithDefault(iValue, DBConstants.DB_DEFAULT_LONG);
    }

    /**
     * ********************************************************************************
     */

    public static double parseDoubleFromStringWithDefault(String iValue, double iDefault) {
        double vValue = 0.0;

        if (iValue == null) {
            return iDefault;
        }

        try {
            vValue = Double.parseDouble(iValue);
        } catch (NumberFormatException e) {
            vValue = iDefault;
        }

        return vValue;
    }

    /**
     * Default value set to DBConstants.DB_DEFAULT_DOUBLE
     *
     * @param iValue
     * @return
     */
    public static double parseDoubleFromStringWithDefault(String iValue) {
        return parseDoubleFromStringWithDefault(iValue, DBConstants.DB_DEFAULT_DOUBLE);
    }

    /**
     * ********************************************************************************
     */

    public static String implodeStringArray(String[] iInputArray, String iDelimiter) {
        String vOutput = "";

        if (iInputArray.length > 0) {
            StringBuilder vStrB = new StringBuilder();
            vStrB.append(iInputArray[0]);

            for (int i = 1; i < iInputArray.length; i++) {
                vStrB.append(iDelimiter);
                vStrB.append(iInputArray[i]);
            }

            vOutput = vStrB.toString();
        }

        return vOutput;
    }

    public static String implodeStringArray(String[] iInputArray) {
        return implodeStringArray(iInputArray, ",");
    }

    /**
     * ********************************************************************************
     */

    public static String getFromStringArray(String[] iSrc, int iIdx) {
        if (iSrc == null) {
            return null;
        } else {
            return iSrc[iIdx];
        }
    }

    public static String getFirstFromStringArray(String[] iSrc) {
        return getFromStringArray(iSrc, 0);
    }

    /**
     * ********************************************************************************
     */

    public static Date parseDateFromStringWithDefault(String iSrc, SimpleDateFormat iFormat) {
        Date vResultDate = null;
        if (iSrc == null) {
            return null;
        } else {
            try {
                vResultDate = iFormat.parse(iSrc);
            } catch (ParseException e) {
                return null;
            }

            return vResultDate;
        }
    }

    /**
     * ********************************************************************************
     */
    public static Enum[] parseEnumArrFromStringArr(String[] iValues, Class iEnumClass) {
        Enum[] vEnums = new Enum[iValues.length];
        for (int i = 0; i < iValues.length; i++)
            try {
                vEnums[i] = Enum.valueOf(iEnumClass, iValues[i]);
            } catch (Exception e) {
                vEnums[i] = null;
            }
        return vEnums;
    }

    public static Enum parseEnumFromStringWithDefault(String iSrc, Class iEnumClass) {
        Enum vResultEnum = null;
        if (iSrc == null) {
            return null;
        } else {
            try {
                vResultEnum = Enum.valueOf(iEnumClass, iSrc);
            } catch (Exception e) {
                return null;
            }

            return vResultEnum;
        }
    }

    /**
     * ********************************************************************************
     */

    public static String getEnumStringWithDefault(Enum iSrc) {
        if (iSrc == null) {
            return null;
        } else {
            return iSrc.name();
        }
    }

    /**
     * ********************************************************************************
     */
    public static String timestampToString(Timestamp iTS) {
        if (iTS == null)
            return "";
        Date vDate = new Date(iTS.getTime());
        SimpleDateFormat vFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return vFormatter.format(vDate);
    }

    /**
     * ********************************************************************************
     */
    public static String dateToString(Date iDate) {
        if (iDate == null)
            return "";
        SimpleDateFormat vFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return vFormatter.format(iDate);
    }

    public static String dateToDay(Date iDate) {
        if (iDate == null)
            return "";
        SimpleDateFormat vFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return vFormatter.format(iDate);
    }

    /**
     * ********************************************************************************
     */

    public static java.sql.Date getSQLDateWithDefault(Date iSrc) {
        if (iSrc == null) {
            return null;
        } else {
            return new java.sql.Date(iSrc.getTime());
        }
    }

    /**
     * ********************************************************************************
     */

    public static Date prepareDateForCalendarSetTime(Date iSrcDate) {
        if (iSrcDate == null) {
            return new Date(DBConstants.DB_DEFAULT_DATE_LONG);
        } else {
            return iSrcDate;
        }
    }

    public static boolean isEmpty(String iS) {
        return iS == null || iS.isEmpty();
    }

    /*********************************************************************************************/
    /**
     * @param iConnection
     * @param iStringList
     * @return
     * @throws SQLException
     */
    public static String generateSqlStringArrayReplacement(Connection iConnection, List<String> iStringList) throws SQLException {
        PreparedStatement vPreparedStatement = null;
        try {
            if (iStringList == null || iStringList.isEmpty()) {
                return "";
            } else {
                String[] vStrArray = new String[iStringList.size()];
                for (int i = 0; i < vStrArray.length; i++) {
                    vStrArray[i] = "?";
                }
                vPreparedStatement = iConnection.prepareStatement(StringUtils.join(vStrArray, ','));
                for (int i = 0; i < iStringList.size(); i++) {
                    vPreparedStatement.setString(i + 1, iStringList.get(i));
                }

                Pattern p1 = Pattern.compile("^[^:]+:\\s{0,1}(.+)$");
                Matcher m1 = p1.matcher(vPreparedStatement.toString());
                if (m1.find()) {
                    return m1.group(1);
                } else {
                    return vPreparedStatement.toString();
                }
            }
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * @param iConnection
     * @param iStringArray
     * @return
     * @throws SQLException
     */
    public static String generateSqlStringArrayReplacement(Connection iConnection, String[] iStringArray) throws SQLException {
        PreparedStatement vPreparedStatement = null;
        try {
            if (iStringArray == null || iStringArray.length == 0) {
                return "";
            } else {
                String[] vStrArray = new String[iStringArray.length];
                for (int i = 0; i < vStrArray.length; i++) {
                    vStrArray[i] = "?";
                }
                vPreparedStatement = iConnection.prepareStatement(StringUtils.join(vStrArray, ','));
                for (int i = 0; i < iStringArray.length; i++) {
                    vPreparedStatement.setString(i + 1, iStringArray[i]);
                }

                Pattern p1 = Pattern.compile("^[^:]+:\\s{0,1}(.+)$");
                Matcher m1 = p1.matcher(vPreparedStatement.toString());
                if (m1.find()) {
                    return m1.group(1);
                } else {
                    return vPreparedStatement.toString();
                }
            }
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static String generateSqlStringReplacement(Connection iConnection, String iString) throws SQLException {
        PreparedStatement vPreparedStatement = null;
        try {
            if (iString == null || iString.isEmpty()) {
                return "";
            } else {
                vPreparedStatement = iConnection.prepareStatement("?");
                vPreparedStatement.setString(1, iString);

                Pattern p1 = Pattern.compile("^[^:]+:\\s{0,1}(.+)$");
                Matcher m1 = p1.matcher(vPreparedStatement.toString());
                if (m1.find()) {
                    return m1.group(1);
                } else {
                    return vPreparedStatement.toString();
                }
            }
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    public static String getValueByNameFromJsonNode(JsonNode iNode, String iName) {
        String vReturn = null;
        String vTmp = null;
        if (iNode.isContainerNode()) {
            if (iNode.isArray()) {
                for (int i = 0; i < iNode.size(); ++i) {
                    vTmp = getValueByNameFromJsonNode(iNode.get(i), iName);
                    if (vTmp != null) {
                        vReturn = vTmp;
                    }
                }
            } else if (iNode.isObject()) {
                Iterator<String> vNames = iNode.getFieldNames();
                while (vNames.hasNext()) {
                    String vName = vNames.next();
                    JsonNode vValue = iNode.get(vName);

                    if (vValue.isArray()) {
                        vTmp = getValueByNameFromJsonNode(vValue, iName);
                        if (vTmp != null) {
                            vReturn = vTmp;
                        }
                    }
                    if (vValue.isObject()) {
                        vTmp = getValueByNameFromJsonNode(vValue, iName);
                        if (vTmp != null) {
                            vReturn = vTmp;
                        }
                    }
                    if (vValue.isValueNode()) {
                        if (vName.equals(iName)) {
                            return vValue.asText();
                        }
                    }
                }
            }
        }
        if (iNode.isMissingNode()) {
            // Ignored
        }
        if (iNode.isValueNode()) {
            // Ignored
        }
        return vReturn;
    }

    // XYB:
    public static List<String> getValuesByNameFromJsonNode(JsonNode iNode, String iName) {
        List<String> vValueList = new ArrayList<String>();
        List<String> vTmpList;
        if (iNode.isContainerNode()) {
            if (iNode.isArray()) {
                for (int i = 0; i < iNode.size(); ++i) {
                    vTmpList = getValuesByNameFromJsonNode(iNode.get(i), iName);
                    if (vTmpList.size() != 0) {
                        vValueList.addAll(vTmpList);
                    }
                }
            } else if (iNode.isObject()) {
                Iterator<String> vNames = iNode.getFieldNames();
                while (vNames.hasNext()) {
                    String vName = vNames.next();
                    JsonNode vValue = iNode.get(vName);

                    if (vValue.isArray()) {
                        vTmpList = getValuesByNameFromJsonNode(vValue, iName);
                        if (vTmpList.size() != 0) {
                            vValueList.addAll(vTmpList);
                        }
                    }
                    if (vValue.isObject()) {
                        vTmpList = getValuesByNameFromJsonNode(vValue, iName);
                        if (vTmpList.size() != 0) {
                            vValueList.addAll(vTmpList);
                        }
                    }
                    if (vValue.isValueNode()) {
                        if (vName.equals(iName)) {
                            vValueList.add(vValue.asText());
                        }
                    }
                }
            }
        }
        if (iNode.isMissingNode()) {
            // Ignored
        }
        if (iNode.isValueNode()) {
            // Ignored
        }
        return vValueList;
    }

    // XYB:
    public static String getUUID(String iWebsiteUrl) {
        return iWebsiteUrl + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-" + UUID.randomUUID().toString();
    }

    // XYB:
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}




