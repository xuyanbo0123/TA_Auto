package name.mi.analysis.dao;

import name.mi.util.SqlUtils;
import name.mi.analysis.derivative.Report;
import name.mi.analysis.derivative.ReportRow;
import name.mi.analysis.derivative.Section;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * data access layer for report
 */
public class ReportDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ReportDAO.class));

    private static final String
            DAILY_REPORT = QUERY_MAP.get("DAILY_REPORT_FULL");
    //DAILY_REPORT_FULL = QUERY_MAP.get("DAILY_REPORT_FULL");

    public static List<String> getFieldNames(
            Logger iLogger,
            Connection iConnection,
            Report.BreakDown iField,
            String iFilter
    ) throws Exception {
        PreparedStatement
                vStatement = null;
        ResultSet
                vResultSet = null;
        List<String> vList = new ArrayList<String>();
        String str = String.format("SELECT DISTINCT %s FROM (%s) as TMP",
                iField.name(), // for iField, no necessary to execute sql injection because iField is enum.
                DAILY_REPORT + iFilter); // for iFilter, sql injection has been executed.
        vStatement = iConnection.prepareStatement(str);

        vResultSet = vStatement.executeQuery();
        while (vResultSet.next()) {
            vList.add(vResultSet.getString(1));
        }
        SqlUtils.close(vResultSet);
        SqlUtils.close(vStatement);
        return vList;
    }

    public static Report getReport(
            Logger iLogger,
            Connection iConnection,
            String iTitle,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            Report.BreakDown iColumnField,
            String iFilter,
            String iOrderBy, // DataOption.Column  if empty then use RowNames
            String iOrder
    ) throws Exception {
        List<String> vRowNames = ReportDAO.getFieldNames(iLogger, iConnection, iRowField, iFilter);
        List<String> vColumnNames = ReportDAO.getFieldNames(iLogger, iConnection, iColumnField, iFilter);
        String[][][] vResultTable = getData(iLogger, iConnection, iDataOptions, iRowField, iColumnField,
                vRowNames, vColumnNames, iFilter);

        Section vSection = new Section();
        vSection.setRowField(iRowField);
        vSection.setColumnField(iColumnField);
        vSection.getColumnHeader().addColumn(vColumnNames);
        vSection.getColumnHeader().addSubColumn(iDataOptions);

        for (int i = 0; i < vRowNames.size(); i++) {
            String vRowName = vRowNames.get(i);
            ReportRow vRow = new ReportRow(vRowName);
            for (int j = 0; j < vColumnNames.size(); j++) {
                List<String> vItem = new ArrayList<String>();
                for (int k = 0; k < iDataOptions.length; k++) {
                    vItem.add(vResultTable[k][i][j]);
                }
                vRow.addItem(vItem);
            }
            vSection.getRows().add(vRow);
        }
        if (!iOrderBy.isEmpty()) {
            String[] vStrings = iOrderBy.split("\\.");
            String vDataOption = vStrings[0];
            String vColumn = vStrings[1];
            vSection.sort(vDataOption, vColumn, iOrder);
        } else {
            vSection.sort(iOrder);
        }

        Report vReport = new Report();
        vReport.setTitle(iTitle);
        vReport.setSectionField(null);
        vReport.addSection(vSection);
        return vReport;
    }

    public static Report getReport(
            Logger iLogger,
            Connection iConnection,
            String iTitle,
            Report.BreakDown iSectionField,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            Report.BreakDown iColumnField,
            String iFilter,
            String iOrderBy, // DataOption.Column  if empty then use RowNames
            String iOrder
    ) throws Exception {
        List<String> vSectionNames = ReportDAO.getFieldNames(iLogger, iConnection, iSectionField, iFilter);
        List<String> vRowNames = ReportDAO.getFieldNames(iLogger, iConnection, iRowField, iFilter);
        List<String> vColumnNames = ReportDAO.getFieldNames(iLogger, iConnection, iColumnField, iFilter);

        String[][][][] vResultTable = getData(iLogger, iConnection, iSectionField, iDataOptions, iRowField, iColumnField,
                vSectionNames, vRowNames, vColumnNames, iFilter);
        Report vReport = new Report();
        vReport.setTitle(iTitle);
        // KS: Added this section field, 2013-05-14
        vReport.setSectionField(iSectionField);

        for (int h = 0; h < vSectionNames.size(); h++) {
            Section vSection = new Section();
            vSection.setName(vSectionNames.get(h));
            vSection.setRowField(iRowField);
            vSection.setColumnField(iColumnField);
            vSection.getColumnHeader().addColumn(vColumnNames);
            vSection.getColumnHeader().addSubColumn(iDataOptions);
            for (int i = 0; i < vRowNames.size(); i++) {
                String vRowName = vRowNames.get(i);
                ReportRow vRow = new ReportRow(vRowName);
                for (int j = 0; j < vColumnNames.size(); j++) {
                    List<String> vItem = new ArrayList<String>();
                    for (int k = 0; k < iDataOptions.length; k++) {
                        vItem.add(vResultTable[k][h][i][j]);
                    }
                    vRow.addItem(vItem);
                }
                vSection.getRows().add(vRow);
            }
            if (!iOrderBy.isEmpty()) {
                String[] vStrings = iOrderBy.split("\\.");
                String vDataOption = vStrings[0];
                String vColumn = vStrings[1];
                vSection.sort(vDataOption, vColumn, iOrder);
            } else {
                vSection.sort(iOrder);
            }
            vReport.addSection(vSection);
        }
        return vReport;
    }

    public static Report getReport(
            Logger iLogger,
            Connection iConnection,
            String iTitle,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            String iFilter,
            String iOrderBy, // DataOption.Column  if empty then use RowNames
            String iOrder
    ) throws Exception {
        List<String> vRowNames = ReportDAO.getFieldNames(iLogger, iConnection, iRowField, iFilter);
        String[][] vResultTable = getData(iLogger, iConnection, iDataOptions, iRowField, vRowNames, iFilter);

        Section vSection = new Section();
        vSection.getColumnHeader().addColumn("default");
        vSection.setRowField(iRowField);
        vSection.getColumnHeader().addSubColumn(iDataOptions);


        for (int i = 0; i < vRowNames.size(); i++) {
            String vRowName = vRowNames.get(i);
            ReportRow vRow = new ReportRow(vRowName);
            List<String> vItem = new ArrayList<String>();
            for (int k = 0; k < iDataOptions.length; k++) {
                vItem.add(vResultTable[k][i]);
            }
            vRow.addItem(vItem);
            vSection.getRows().add(vRow);
        }
        if (!iOrderBy.isEmpty()) {
            String[] vStrings = iOrderBy.split("\\.");
            String vDataOption = vStrings[0];
            String vColumn = vStrings[1];
            vSection.sort(vDataOption, null, iOrder);
        } else {
            vSection.sort(iOrder);
        }

        Report vReport = new Report();
        vReport.setTitle(iTitle);
        vReport.setSectionField(null);
        vReport.addSection(vSection);
        return vReport;
    }

    public static Report getReport(
            Logger iLogger,
            Connection iConnection,
            String iTitle,
            Report.BreakDown iSectionField,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            String iFilter,
            String iOrderBy, // DataOption.Column  if empty then use RowNames
            String iOrder
    ) throws Exception {
        List<String> vSectionNames = ReportDAO.getFieldNames(iLogger, iConnection, iSectionField, iFilter);
        List<String> vRowNames = ReportDAO.getFieldNames(iLogger, iConnection, iRowField, iFilter);


        String[][][] vResultTable = getData(iLogger, iConnection, iSectionField, iDataOptions, iRowField,
                vSectionNames, vRowNames, iFilter);
        Report vReport = new Report();
        vReport.setTitle(iTitle);
        // KS: Added this section field, 2013-05-14
        vReport.setSectionField(iSectionField);

        for (int h = 0; h < vSectionNames.size(); h++) {
            Section vSection = new Section();
            vSection.setName(vSectionNames.get(h));
            vSection.setRowField(iRowField);
            vSection.getColumnHeader().addSubColumn(iDataOptions);
            for (int i = 0; i < vRowNames.size(); i++) {
                String vRowName = vRowNames.get(i);
                ReportRow vRow = new ReportRow(vRowName);
                List<String> vItem = new ArrayList<String>();
                for (int k = 0; k < iDataOptions.length; k++) {
                    vItem.add(vResultTable[k][h][i]);
                }
                vRow.addItem(vItem);
                vSection.getRows().add(vRow);
            }
            if (!iOrderBy.isEmpty()) {
                vSection.sort(iOrderBy, null, iOrder);
            } else {
                vSection.sort(iOrder);
            }
            vReport.addSection(vSection);
        }
        return vReport;
    }

    private static String[][][][] getData(
            Logger iLogger,
            Connection iConnection,
            Report.BreakDown iSectionField,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            Report.BreakDown iColumnField,
            List<String> iSectionNames,
            List<String> iRowNames,
            List<String> iColumnNames,
            String iFilter
    ) throws Exception {
        String[][][][] vResultTable = new String[iDataOptions.length][iSectionNames.size()][iRowNames.size()][iColumnNames.size()];
        for (int k = 0; k < iDataOptions.length; k++)
            for (int h = 0; h < iSectionNames.size(); h++)
                for (int i = 0; i < iRowNames.size(); i++)
                    for (int j = 0; j < iColumnNames.size(); j++)
                        vResultTable[k][h][i][j] = "";

        List<List<String>> vList = ReportDAO.getTable(iLogger, iConnection, iDataOptions, iSectionField, iRowField, iColumnField,
                iFilter);

        for (List<String> vLine : vList) {
            String vSectionName = vLine.get(0);
            String vRowName = vLine.get(1);
            String vColumnName = vLine.get(2);
            for (int k = 0; k < iDataOptions.length; k++) {
                String vResult = vLine.get(k + 3);
                int h = iSectionNames.indexOf(vSectionName);
                int i = iRowNames.indexOf(vRowName);
                int j = iColumnNames.indexOf(vColumnName);
                vResultTable[k][h][i][j] = vResult;
            }
        }
        return vResultTable;
    }

    private static String[][][] getData(
            Logger iLogger,
            Connection iConnection,
            Report.BreakDown iSectionField,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            List<String> iSectionNames,
            List<String> iRowNames,
            String iFilter
    ) throws Exception {
        String[][][] vResultTable = new String[iDataOptions.length][iSectionNames.size()][iRowNames.size()];
        for (int k = 0; k < iDataOptions.length; k++)
            for (int h = 0; h < iSectionNames.size(); h++)
                for (int i = 0; i < iRowNames.size(); i++)
                    vResultTable[k][h][i] = "";

        List<List<String>> vList = ReportDAO.getTable(iLogger, iConnection, iDataOptions, iSectionField, iRowField,
                iFilter);

        for (List<String> vLine : vList) {
            String vSectionName = vLine.get(0);
            String vRowName = vLine.get(1);
            for (int k = 0; k < iDataOptions.length; k++) {
                String vResult = vLine.get(k + 2);
                int h = iSectionNames.indexOf(vSectionName);
                int i = iRowNames.indexOf(vRowName);
                vResultTable[k][h][i] = vResult;
            }
        }
        return vResultTable;
    }

    private static String[][][] getData(
            Logger iLogger,
            Connection iConnection,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            Report.BreakDown iColumnField,
            List<String> iRowNames,
            List<String> iColumnNames,
            String iFilter
    ) throws Exception {
        String[][][] vResultTable = new String[iDataOptions.length][iRowNames.size()][iColumnNames.size()];
        for (int k = 0; k < iDataOptions.length; k++) {
            for (int i = 0; i < iRowNames.size(); i++) {
                for (int j = 0; j < iColumnNames.size(); j++) {
                    vResultTable[k][i][j] = "";
                }
            }
        }
        List<List<String>> vList = ReportDAO.getTable(iLogger, iConnection, iDataOptions, iRowField, iColumnField,
                iFilter);

        for (List<String> vLine : vList) {
            String vRowName = vLine.get(0);
            String vColumnName = vLine.get(1);
            for (int k = 0; k < iDataOptions.length; k++) {
                String vResult = vLine.get(k + 2);
                int i = iRowNames.indexOf(vRowName);
                int j = iColumnNames.indexOf(vColumnName);
                vResultTable[k][i][j] = vResult;
            }
        }
        return vResultTable;
    }

    private static String[][] getData(
            Logger iLogger,
            Connection iConnection,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            List<String> iRowNames,
            String iFilter
    ) throws Exception {
        String[][] vResultTable = new String[iDataOptions.length][iRowNames.size()];
        for (int k = 0; k < iDataOptions.length; k++) {
            for (int i = 0; i < iRowNames.size(); i++) {
                vResultTable[k][i] = "";
            }
        }
        List<List<String>> vList = ReportDAO.getTable(iLogger, iConnection, iDataOptions, iRowField,
                iFilter);

        for (List<String> vLine : vList) {
            String vRowName = vLine.get(0);
            for (int k = 0; k < iDataOptions.length; k++) {
                String vResult = vLine.get(k + 1);
                int i = iRowNames.indexOf(vRowName);
                vResultTable[k][i] = vResult;
            }
        }
        return vResultTable;
    }

    private static List<List<String>> getTable(
            Logger iLogger,
            Connection iConnection,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            String iFilter
    ) throws Exception {
        PreparedStatement
                vStatement = null;
        ResultSet
                vResultSet = null;
        String vFunc = generateFunctions(iDataOptions);
        List<List<String>> vResultList = new ArrayList<List<String>>();
        String str = String.format("SELECT %s, %s FROM (%s) as TMP GROUP BY 1",
                iRowField.name(), //no necessary to execute sql injection because var type is enum
                vFunc, //no necessary to execute sql injection because var is given by function 'generateFunctions'
                DAILY_REPORT + iFilter); // for iFilter, sql injection has been executed.
        vStatement = iConnection.prepareStatement(str);

        vResultSet = vStatement.executeQuery();
        while (vResultSet.next()) {
            List<String> vList = new ArrayList<String>();
            vList.add(vResultSet.getString(iRowField.name()));
            for (Report.DataOption vDataOption : iDataOptions)
                vList.add(vResultSet.getString(vDataOption.name()));
            vResultList.add(vList);
        }
        SqlUtils.close(vResultSet);
        SqlUtils.close(vStatement);
        return vResultList;
    }

    private static List<List<String>> getTable(
            Logger iLogger,
            Connection iConnection,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iRowField,
            Report.BreakDown iColumnField,
            String iFilter
    ) throws Exception {
        PreparedStatement
                vStatement = null;
        ResultSet
                vResultSet = null;
        String vFunc = generateFunctions(iDataOptions);
        List<List<String>> vResultList = new ArrayList<List<String>>();
        String str = String.format("SELECT %s, %s, %s FROM (%s) as TMP GROUP BY 1,2",
                iRowField.name(), //no necessary to execute sql injection because var type is enum
                iColumnField.name(), //no necessary to execute sql injection because var type is enum
                vFunc, //no necessary to execute sql injection because var is given by function 'generateFunctions'
                DAILY_REPORT + iFilter); // for iFilter, sql injection has been executed.
        vStatement = iConnection.prepareStatement(str);

        vResultSet = vStatement.executeQuery();
        while (vResultSet.next()) {
            List<String> vList = new ArrayList<String>();
            vList.add(vResultSet.getString(iRowField.name()));
            vList.add(vResultSet.getString(iColumnField.name()));
            for (Report.DataOption vDataOption : iDataOptions)
                vList.add(vResultSet.getString(vDataOption.name()));
            vResultList.add(vList);
        }
        SqlUtils.close(vResultSet);
        SqlUtils.close(vStatement);
        return vResultList;
    }

    private static List<List<String>> getTable(
            Logger iLogger,
            Connection iConnection,
            Report.DataOption[] iDataOptions,
            Report.BreakDown iSectionField,
            Report.BreakDown iRowField,
            Report.BreakDown iColumnField,
            String iFilter
    ) throws Exception {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        String vFunc = generateFunctions(iDataOptions);
        List<List<String>> vResultList = new ArrayList<List<String>>();
        String str = String.format("SELECT %s, %s, %s, %s FROM (%s) as TMP GROUP BY 1,2,3",
                iSectionField.name(), //no necessary to execute sql injection because var type is enum
                iRowField.name(), //no necessary to execute sql injection because var type is enum
                iColumnField.name(), //no necessary to execute sql injection because var type is enum
                vFunc, //no necessary to execute sql injection because var is given by function 'generateFunctions'
                DAILY_REPORT + iFilter); // for iFilter, sql injection has been executed.
        vStatement = iConnection.prepareStatement(str);

        vResultSet = vStatement.executeQuery();
        while (vResultSet.next()) {
            List<String> vList = new ArrayList<String>();
            vList.add(vResultSet.getString(iSectionField.name()));
            vList.add(vResultSet.getString(iRowField.name()));
            vList.add(vResultSet.getString(iColumnField.name()));
            for (Report.DataOption vDataOption : iDataOptions)
                vList.add(vResultSet.getString(vDataOption.name()));
            vResultList.add(vList);
        }
        SqlUtils.close(vResultSet);
        SqlUtils.close(vStatement);
        return vResultList;
    }

    private static String generateFunctions(Report.DataOption[] iDataOptions
    ) throws Exception {
            List<String> vFunctions = new ArrayList<String>();
        for (Report.DataOption vDataOption : iDataOptions) {
            switch (vDataOption) {
                case impression:
                    vFunctions.add("SUM(impression) as impression"); //int counts
                    break;
                case click:
                    vFunctions.add("SUM(click) as click"); //int counts
                    break;
                case arrival:
                    vFunctions.add("SUM(arrival) as arrival"); //int counts
                    break;
                case cost:
                    vFunctions.add("SUM(cost) as cost"); //addable double
                    break;
                case adpos:
                    vFunctions.add("SUM(adpos*impression)/SUM(impression) as adpos"); //weighted average
                    break;
                case ctr:
                    vFunctions.add("SUM(click)/SUM(impression) as ctr"); //ratio
                    break;
                case conversion:
                    vFunctions.add("SUM(conversion) as conversion"); //int counts
                    break;
                case crate:
                    vFunctions.add("SUM(conversion)/SUM(click) as crate"); //ratio
                    break;
                case lead:
                    vFunctions.add("SUM(lead) as lead"); //int counts
                    break;
                case clickout:
                    vFunctions.add("SUM(clickout) as clickout"); //int counts
                    break;
                case corate:
                    vFunctions.add("SUM(clickout)/SUM(adimp) as corate"); //ratio
                    break;
                case value:
                    vFunctions.add("SUM(value) as `value`"); //addable double
                    break;
                case profit:
                    vFunctions.add("SUM(value)-SUM(cost) as profit"); //addable double
                    break;
                case vpc:
                    vFunctions.add("SUM(value)/SUM(click) as vpc"); //ratio
                    break;
                case cpc:
                    vFunctions.add("SUM(cost)/SUM(click) as cpc"); //ratio
                    break;
                case vpl:
                    vFunctions.add("SUM(total_lead_revenue)/SUM(lead) as vpl"); //ratio
                    break;
                case vpoc:
                    vFunctions.add("SUM(total_ad_click_revenue)/SUM(clickout) as vpoc"); //ratio
                    break;
                case vpcon:
                    vFunctions.add("SUM(value)/SUM(conversion) as vpoc"); //ratio
                    break;
                case adimp:
                    vFunctions.add("SUM(adimp) as adimp"); //int counts
                    break;
                default:
                    break;
            }
        }

        //Finalize the functions
        String vFunction = "";
        if (vFunctions.size() > 0)
            vFunction = StringUtils.join(vFunctions, ", ");

        return vFunction;
    }
}
