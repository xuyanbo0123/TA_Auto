package name.mi.analysis.derivative;

import name.mi.util.UtilityFunctions;
import name.mi.analysis.util.ReportRowByItemsComparator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@JsonPropertyOrder(value = {"Name", "Column_Field", "Column_Header", "Row_Field", "Row_Header", "Data"})
public class Section {
    @JsonIgnore
    private String
            mName;
    private Report.BreakDown
            mColumnField,
            mRowField;
    private ColumnHeader
            mColumnHeader;
    private List<ReportRow>
            mRows;

    /**
     * constructor
     */
    public Section() {
        mName = null;
        mColumnField = null;
        mRowField = null;
        mColumnHeader = new ColumnHeader();
        mRows = new ArrayList<ReportRow>();
    }

    /**
     * constructor
     *
     * @param iName
     * @param iColumnField
     * @param iRowField
     * @param iColumnHeader
     * @param iRows
     */
    public Section(
            String iName,
            Report.BreakDown iColumnField,
            Report.BreakDown iRowField,
            ColumnHeader iColumnHeader,
            List<ReportRow> iRows
    ) {
        mName = iName;
        mColumnField = iColumnField;
        mRowField = iRowField;
        mColumnHeader = iColumnHeader;
        mRows = new ArrayList<ReportRow>();
    }

    /**
     * get section name
     *
     * @return
     */
    @JsonProperty("Name")
    public final String getName() {
        if (UtilityFunctions.isEmpty(mName))
            return "Unknown";
        else
            return mName;
    }

    /**
     * get column Field
     *
     * @return
     */
    @JsonProperty("Column_Field")
    public final Report.BreakDown getColumnField() {
        return mColumnField;
    }

    /**
     * get row Field
     *
     * @return
     */
    @JsonProperty("Row_Field")
    public final Report.BreakDown getRowField() {
        return mRowField;
    }

    /**
     * get column header
     *
     * @return
     */
    @JsonProperty("Column_Header")
    public final ColumnHeader getColumnHeader() {
        return mColumnHeader;
    }

    /**
     * get row header
     *
     * @return
     */
    @JsonProperty("Row_Header")
    public final List<String> getRowHeader() {
        List<String> vRowHeader = new ArrayList<String>();
        for (int i = 0; i < mRows.size(); i++) {
            vRowHeader.add(mRows.get(i).getName());
        }
        return vRowHeader;
    }

    /**
     * get rows
     *
     * @return
     */
    @JsonProperty("Rows")
    public final List<ReportRow> getRows() {
        return mRows;
    }

    /**
     * set name
     *
     * @param iName
     */
    public void setName(String iName) {
        if (UtilityFunctions.isEmpty(iName))
            mName = "Unknown";
        else
            mName = iName;
    }

    /**
     * set column Field
     *
     * @param iColumnField
     */
    public void setColumnField(Report.BreakDown iColumnField) {
        mColumnField = iColumnField;
    }

    /**
     * set row Field
     *
     * @param iRowField
     */
    public void setRowField(Report.BreakDown iRowField) {
        mRowField = iRowField;
    }

    /**
     * set rows
     *
     * @param iRows
     */
    public void setRows(List<ReportRow> iRows) {
        mRows = iRows;
    }

    /**
     * add one row
     *
     * @param iRow
     */
    public void addRow(ReportRow iRow) {
        mRows.add(iRow);
    }

    public void sort(String iOrder) {
        if (iOrder.equals("DESC") || iOrder.equals("desc")) {
            Collections.sort(mRows, Collections.reverseOrder(new Comparator<ReportRow>() {
                public int compare(ReportRow x, ReportRow y) {
                    return x.getName().compareTo(y.getName());
                }
            }));
        } else {
            Collections.sort(mRows, new Comparator<ReportRow>() {
                public int compare(ReportRow x, ReportRow y) {
                    return x.getName().compareTo(y.getName());
                }
            });
        }
    }

    public void sort(String iDataOption, String iColumn, String iOrder) {
        if (iColumn == null)
            sort(mColumnHeader.getSubColumnIndex(iDataOption), 0, iOrder);
        else
            sort(mColumnHeader.getSubColumnIndex(iDataOption), mColumnHeader.getColumnIndex(iColumn), iOrder);
        return;
    }

    public void sort(int iDataOptionIndex, int iColumnIndex, String iOrder) {
        if (iColumnIndex<0 || iDataOptionIndex<0)
        {
            sort(iOrder);
            return;
        }
        ReportRow.OrderColumn = iColumnIndex;
        ReportRow.OrderDataOption = iDataOptionIndex;
        if (iOrder.equals("DESC") || iOrder.equals("desc"))
            Collections.sort(mRows, Collections.reverseOrder(new ReportRowByItemsComparator()));
        else
            Collections.sort(mRows, new ReportRowByItemsComparator());

    }
}