package name.mi.analysis.service;

import name.mi.micampaign.util.FieldMap;
import name.mi.util.*;
import name.mi.analysis.dao.ReportDAO;
import name.mi.analysis.derivative.Report;
import name.mi.services.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static name.mi.util.UtilityFunctions.generateSqlStringArrayReplacement;
import static name.mi.util.UtilityFunctions.generateSqlStringReplacement;

@WebServlet(asyncSupported = false, name = "ReportServlet", urlPatterns = {"/Report"})
public class ReportServlet extends HttpServlet {

    public enum Operator {
        eq, leq, geq, lt, gt
    }

    private static final Logger
            LOGGER = LogManager.getLogger(ReportServlet.class);

    //    public static final String[] mEnumFields = {
//            "provider_status","match_type"
//    };
    public static final String
            P_SECTION = "section",
            P_ROW = "row",
            P_COLUMN = "column",
            P_DATA_OPTION = "data_option",
            P_START = "start",
            P_END = "end",
            P_NUM = "num",
            P_FIELD = "field",
            P_OPERAND = "operand",
            P_VALUE = "value",
            P_ORDER_BY = "order_by",  // KS: Added for order by, distinguish from the field used by filter. 2013-05-27
            P_ORDER = "order";

    private ObjectMapper
            mMapper;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Reporter";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        LOGGER.info("ReportServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter
                vWriter = iResponse.getWriter();

        try {
            ReplyWithResult
                    vReplyWithResult = processRequest(iRequest);

            String
                    vReplyMessage = mMapper.writeValueAsString(vReplyWithResult);

            vWriter.write(vReplyMessage);
            vWriter.flush();
        } finally {
            vWriter.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        LOGGER.info("ReportServlet.processRequest: done...");
    }

    /**
     * NOTE! The 3rd parameter must be hard-coded (at least, not taken directly from user input)
     *
     * @param iConnection
     * @param iNames
     * @param iField
     * @param iFilters
     * @throws SQLException
     */
    private void generateStringLikeFilter(Connection iConnection, List<String> iNames, String iField, List<String> iFilters) throws SQLException {
        if (iNames != null && iNames.size() > 0) {
            List<String> vNameFilters = new ArrayList<String>();
            for (String vName : iNames)
                // KS: the first %s doesn't need sql-injection prevention, as it is hard coded in the caller (3rd parameter) as a know table.fieldname format
                vNameFilters.add(String.format("%s LIKE %s",
                        iField, generateSqlStringReplacement(iConnection, vName)));
            if (vNameFilters.size() > 0)
                iFilters.add("(" + StringUtils.join(vNameFilters, " OR ") + ")");
        }
    }

    /**
     * NOTE! The 3rd parameter must be hard-coded (at least, not taken directly from user input)
     *
     * @param iConnection
     * @param iNames
     * @param iField
     * @param iFilters
     * @throws SQLException
     */
    private void generateStringEqualFilter(Connection iConnection, String[] iNames, String iField, List<String> iFilters) throws SQLException {
        if (iNames != null && iNames.length > 0) {
            List<String> vNameFilters = new ArrayList<String>();
            for (String vName : iNames)
                // KS: the first %s doesn't need sql-injection prevention, as it is hard coded in the caller (3rd parameter) as a know table.fieldname format
                vNameFilters.add(String.format("%s = %s",
                        iField,
                        generateSqlStringReplacement(iConnection, vName)));
            if (vNameFilters.size() > 0)
                iFilters.add("(" + StringUtils.join(vNameFilters, " OR ") + ")");
        }
    }

    private void generateIDFilter(Connection iConnection, String[] iFields, String[] iOperands, String[] iValues, List<String> iFilters
    ) throws SQLException {
        int j = 0;
        List<String> vValueList = new ArrayList<String>();
        while (j < iFields.length) {
            String vRealIDField = FieldMap.getField(iFields[j]);
            if (vRealIDField != null && iOperands[j].equals(Operator.eq.name())) {
                vValueList.add(iValues[j]);
            }
            j++;
            if (j >= iFields.length || !iFields[j].equals(iFields[j - 1]))
            {
                if (vValueList != null && vValueList.size() > 0)
                    iFilters.add(String.format("%s IN (%s)", vRealIDField, generateSqlStringArrayReplacement(iConnection, vValueList)));
                vValueList.clear();
            }
        }
    }

    private void generateStrFilter(Connection iConnection, String[] iFields, String[] iOperands, String[] iValues, List<String> iFilters
    ) throws SQLException {
        int j = 0;
        List<String> vValueList = new ArrayList<String>();
        while (j < iFields.length) {
            String vRealStrField = FieldMap.getField(iFields[j]);
            if (vRealStrField != null && iOperands[j].equals(Operator.eq.name())) {
                vValueList.add(iValues[j]);
            }
            j++;
            if (j >= iFields.length || !iFields[j].equals(iFields[j - 1]))
            {
                if (vValueList != null && vValueList.size() > 0)
                    generateStringLikeFilter(iConnection, vValueList, vRealStrField, iFilters);
                vValueList.clear();
            }
        }
    }

    private String generateFilter(HttpServletRequest iRequest, Connection iConnection
    ) throws SQLException {
        List<String> vFilters = new ArrayList<String>();

        //Time filter
        String vStartTime = iRequest.getParameter(P_START);
        if (vStartTime == null || vStartTime.isEmpty())
            vStartTime = DBConstants.DB_DEFAULT_TIMESTAMP_STRING_GMT;

        String vEndTime = iRequest.getParameter(P_END);
        if (vEndTime == null || vEndTime.isEmpty()) {
            Date vNow = new Date();
            vEndTime = UtilityFunctions.dateToDay(vNow) + " 23:59:59";  // move 23:59:59 there caused by vPreparedStatement.setString()
        }
        String vTimeFilter = String.format(" Ad_Group_Keyword_Daily_Spending.`day` BETWEEN %s AND %s",// convert '%s' to %s caused by vPreparedStatement.setString()
                generateSqlStringReplacement(iConnection, vStartTime),
                generateSqlStringReplacement(iConnection, vEndTime));
        vFilters.add(vTimeFilter);

        String[] vFields = iRequest.getParameterValues(P_FIELD);
        String[] vOperands = iRequest.getParameterValues(P_OPERAND);
        String[] vValues = iRequest.getParameterValues(P_VALUE);

        // KS: added this null checkpoint, otherwise, if these parameters omitted, will return null. Not checked yet. 2013-05-27
        if (vFields == null) {
            vFields = new String[0];
        }
        if (vOperands == null) {
            vOperands = new String[0];
        }
        if (vValues == null) {
            vValues = new String[0];
        }
        ///////////////////////////////////////////////////////////////////////////////////////

        if (vFields.length != vOperands.length || vFields.length != vValues.length)
            return null;

        generateIDFilter(iConnection, vFields, vOperands, vValues, vFilters);
        generateStrFilter(iConnection, vFields, vOperands, vValues, vFilters);

        //Finalize the filter

        String vFilter = "";

        if (vFilters.size() > 0)
            vFilter = " WHERE " + StringUtils.join(vFilters, " AND ");

        return vFilter;
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest) {
        Connection vConnection = null;
        try {
            vConnection = DBUtils.getMIDatabaseConnection();//for servlet

            ServletUtils vServletUtils = new ServletUtils(iRequest,LOGGER);

            Report.BreakDown vSection = (Report.BreakDown)vServletUtils.getEnumParameterWithDefault(P_SECTION,Report.BreakDown.class, null);
            Report.BreakDown vRow =(Report.BreakDown)vServletUtils.getEnumParameter(P_ROW,Report.BreakDown.class);
            Report.BreakDown vColumn = (Report.BreakDown)vServletUtils.getEnumParameterWithDefault(P_COLUMN,Report.BreakDown.class, null);
            String[] vDataOptionStrings = vServletUtils.getStringParameters(P_DATA_OPTION);
            Report.DataOption[] vDataOptions = Report.parseDataOptionsFromStrings(vDataOptionStrings);

            String vFilter = generateFilter(iRequest, vConnection);
            String vOrderBy = vServletUtils.getStringParameterWithDefault(P_ORDER_BY, "");
            String vOrder = vServletUtils.getStringParameterWithDefault(P_ORDER_BY, "ASC");

            Report vReport = null;
            String vTitle = "report";

            if (vSection == null) {
                if (vColumn == null)
                    vReport = ReportDAO.getReport(LOGGER, vConnection, vTitle, vDataOptions, vRow, vFilter, vOrderBy, vOrder);
                else
                    vReport = ReportDAO.getReport(LOGGER, vConnection, vTitle, vDataOptions, vRow, vColumn, vFilter, vOrderBy, vOrder);
            } else {
                if (vColumn == null)
                    vReport = ReportDAO.getReport(LOGGER, vConnection, vTitle, vSection, vDataOptions, vRow, vFilter, vOrderBy, vOrder);
                else
                    vReport = ReportDAO.getReport(LOGGER, vConnection, vTitle, vSection, vDataOptions, vRow, vColumn, vFilter, vOrderBy, vOrder);
            }
            return new SimpleReplyWithResult(ReplyStatus.Succeed,
                    "Get report, data options : ", "", vReport);
        } catch (Exception ex) {
            LOGGER.error("processRequest: exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        } finally {
            SqlUtils.close(vConnection);
        }
    }
}
