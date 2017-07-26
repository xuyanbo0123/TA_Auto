package name.mi.util;

import org.codehaus.jackson.JsonNode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class JsonNodeParser
{
    private JsonNode mJsonNode;

    public JsonNodeParser(JsonNode iJsonNode)
    {
        mJsonNode = iJsonNode;
    }

    public boolean getBoolean(String iKey)
            throws Exception
    {
        String vValue = getString(iKey);

        if (vValue.toLowerCase().equals("true")||vValue.equals("1"))
            return true;
        if (vValue.toLowerCase().equals("false")||vValue.equals("0"))
            return false;
        throw new IllegalStateException("JsonNodeParser: Invalid string for boolean.");
    }

    public boolean getBoolean(String iKey, boolean iDefault)
            throws Exception
    {
        try
        {
           return getBoolean(iKey);
        }
        catch (Exception ex)
        {
            return iDefault;
        }
    }

    public Date getDate(String iKey, SimpleDateFormat iFormat)
            throws Exception
    {
            return iFormat.parse(getString(iKey));
    }
    public double getDouble(String iKey)
            throws Exception
    {
        return Double.parseDouble(getString(iKey));
    }

    public long getLong(String iKey)
            throws Exception
    {
        return Long.parseLong(getString(iKey));
    }

    public String getString(String iKey)
            throws Exception
    {
        return getString(mJsonNode, iKey);

    }

    public ArrayList<String> getStringList(String iKey)
            throws Exception
    {
        return getStringList(mJsonNode, iKey);
    }

    public ArrayList<Long> getLongList(String iKey)
            throws Exception
    {
        ArrayList<String> vList = getStringList(mJsonNode, iKey);
        ArrayList<Long> vLongList = new ArrayList<Long>();
        for (String vValue:vList)
        {
            vLongList.add(Long.parseLong(vValue));
        }
        return vLongList;
    }


    public ArrayList<Date> getDateList(String iKey, SimpleDateFormat iFormat)
            throws Exception
    {
        ArrayList<String> vList = getStringList(mJsonNode, iKey);
        ArrayList<Date> vDateList = new ArrayList<Date>();
        for (String vValue:vList)
        {
            vDateList.add(iFormat.parse(vValue));
        }
        return vDateList;
    }

    public ArrayList<Double> getDoubleList(String iKey)
            throws Exception
    {
        ArrayList<String> vList = getStringList(mJsonNode, iKey);
        ArrayList<Double> vDoubleList = new ArrayList<Double>();
        for (String vValue:vList)
        {
            vDoubleList.add(Double.parseDouble(vValue));
        }
        return vDoubleList;
    }

    private String getString(JsonNode iJsonNode, String iKey)
            throws Exception
    {
        String vReturn = null;
        String vTmp = null;
        if (iJsonNode.isContainerNode())
        {
            if (iJsonNode.isArray())
            {
                for (int i = 0; i < iJsonNode.size(); ++i)
                {
                    vTmp = getString(iJsonNode.get(i), iKey);
                    if (vTmp != null)
                    {
                        vReturn = vTmp;
                    }
                }
            } else if (iJsonNode.isObject())
            {
                Iterator<String> vNames = iJsonNode.getFieldNames();
                while (vNames.hasNext())
                {
                    String vName = vNames.next();
                    JsonNode vValue = iJsonNode.get(vName);

                    if (vValue.isArray())
                    {
                        vTmp = getString(vValue, iKey);
                        if (vTmp != null)
                        {
                            vReturn = vTmp;
                        }
                    }
                    if (vValue.isObject())
                    {
                        vTmp = getString(vValue, iKey);
                        if (vTmp != null)
                        {
                            vReturn = vTmp;
                        }
                    }
                    if (vValue.isValueNode())
                    {
                        if (vName.equals(iKey))
                        {
                            return vValue.asText();
                        }
                    }
                }
            }
        }
        if (iJsonNode.isMissingNode())
        {
            // Ignored
        }
        if (iJsonNode.isValueNode())
        {
            // Ignored
        }
        return vReturn;
    }

    private ArrayList<String> getStringList(JsonNode iJsonNode, String iKey)
            throws Exception
    {
        ArrayList<String> vValueArrayList = new ArrayList<String>();
        ArrayList<String> vTmpArrayList = new ArrayList<String>();
        if (iJsonNode.isContainerNode())
        {
            if (iJsonNode.isArray())
            {
                for (int i = 0; i < iJsonNode.size(); ++i)
                {
                    vTmpArrayList = getStringList(iJsonNode.get(i), iKey);
                    if (vTmpArrayList.size() != 0)
                    {
                        vValueArrayList.addAll(vTmpArrayList);
                    }
                }
            } else if (iJsonNode.isObject())
            {
                Iterator<String> vNames = iJsonNode.getFieldNames();
                while (vNames.hasNext())
                {
                    String vName = vNames.next();
                    JsonNode vValue = iJsonNode.get(vName);

                    if (vValue.isArray())
                    {
                        vTmpArrayList = getStringList(vValue, iKey);
                        if (vTmpArrayList.size() != 0)
                        {
                            vValueArrayList.addAll(vTmpArrayList);
                        }
                    }
                    if (vValue.isObject())
                    {
                        vTmpArrayList = getStringList(vValue, iKey);
                        if (vTmpArrayList.size() != 0)
                        {
                            vValueArrayList.addAll(vTmpArrayList);
                        }
                    }
                    if (vValue.isValueNode())
                    {
                        if (vName.equals(iKey))
                        {
                            vValueArrayList.add(vValue.asText());
                        }
                    }
                }
            }
        }
        if (iJsonNode.isMissingNode())
        {
            // Ignored
        }
        if (iJsonNode.isValueNode())
        {
            // Ignored
        }
        return vValueArrayList;
    }

    public JsonNode getJsonNode()
    {
        return mJsonNode;
    }

    public void setJsonNode(JsonNode iJsonNode)
    {
        mJsonNode = iJsonNode;
    }
}
