package name.mi.analysis.derivative;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder(value = {"Name","Items"})
public class ReportRow
{
    @JsonIgnore
    private String
            mName;
    public static int OrderColumn;
    public static int OrderDataOption;

    private List<List<String>> mItems;

    /**
     * constructor
     */
    @JsonCreator
    public ReportRow()
    {
        mName = new String();
        mItems = new ArrayList<List<String>>();
    }

    /**
     * constructor
     * @param iName
     */
    @JsonCreator
    public ReportRow(String iName)
    {
        mName = iName;
        mItems = new ArrayList<List<String>>();
    }


    public void addItem(List<String> iItem)
    {
        mItems.add(iItem);
    }

    @JsonProperty("Name")
    public String getName()
    {
        return mName;
    }

    @JsonProperty("Items")
    public List<List<String>> getItems()
    {
        return mItems;
    }
    @JsonIgnore
    public double getCompareData()
    {
        String strData = mItems.get(OrderColumn).get(OrderDataOption);
        try
        {
            return Double.parseDouble(strData);
        }
        catch (Exception ex)
        {
            return -1;
        }
    }
}
