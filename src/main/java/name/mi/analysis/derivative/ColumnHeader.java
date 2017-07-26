package name.mi.analysis.derivative;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder(value = {"Column_Names", "Sub_Column_Names"})
public class ColumnHeader {
    @JsonIgnore
    private List<String>
            mColumnNames,
            mSubColumnNames;

    /**
     * constructor
     */
    @JsonCreator
    public ColumnHeader() {
        mColumnNames = new ArrayList<String>();
        mSubColumnNames = new ArrayList<String>();
    }

    /**
     * constructor
     *
     * @param iColumnNames
     * @param iSubColumnNames
     */
    @JsonCreator
    public ColumnHeader(

            @JsonProperty("Column_Names") List<String> iColumnNames,
            @JsonProperty("Sub_Column_Names") List<String> iSubColumnNames
    ) {

        mColumnNames = iColumnNames;
        mSubColumnNames = iSubColumnNames;
    }

    @JsonIgnore
    public final int getColumnCount() {
        return mColumnNames.size();
    }

    @JsonIgnore
    public final int getSubColumnCount() {
        return mSubColumnNames.size();
    }

    @JsonProperty("Column_Names")
    public final List<String> getColumnNames() {
        return mColumnNames;
    }

    @JsonProperty("Sub_Column_Names")
    public final List<String> getSubColumnNames() {
        return mSubColumnNames;
    }

    public void addColumn(List<String> vNameList) {
        for (int i = 0; i < vNameList.size(); i++) {
            String vName = vNameList.get(i);
            if (vName.isEmpty())
                vName = "unknown";
            addColumn(vName);
        }
    }

    public void addSubColumn(String[] vNames) {
        for (int i = 0; i < vNames.length; i++) {
            addSubColumn(vNames[i]);
        }
    }

    public void addSubColumn(Report.DataOption[] vDataOptions) {
        for (int i = 0; i < vDataOptions.length; i++) {
            addSubColumn(vDataOptions[i].name());
        }
    }


    public void addColumn(String iName) {
        mColumnNames.add(iName);
    }

    public void addSubColumn(String iName) {
        mSubColumnNames.add(iName);
    }
    @JsonIgnore
    public int getSubColumnIndex(String iSubColumnName)
    {
        return mSubColumnNames.indexOf(iSubColumnName);
    }
    @JsonIgnore
    public int getColumnIndex(String iColumnName)
    {
        return mColumnNames.indexOf(iColumnName);
    }

}
