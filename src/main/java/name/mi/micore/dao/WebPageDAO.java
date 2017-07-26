package name.mi.micore.dao;

import name.mi.micore.model.WebPage;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class WebPageDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(WebPageDAO.class));

    private static final String
            INSERT_INTO_WEB_PAGE = QUERY_MAP.get("INSERT_INTO_WEB_PAGE"),
            GET_WEB_PAGE_BY_ID = QUERY_MAP.get("GET_WEB_PAGE_BY_ID"),
            GET_WEB_PAGE_ID_BY_URI = QUERY_MAP.get("GET_WEB_PAGE_ID_BY_URI");

    /**
     * save a new web page
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iURI
     * @return
     * @throws java.sql.SQLException
     */
    public static WebPage insertWebPage(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iURI
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_WEB_PAGE;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iURI);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("WebPageDAO.insertWebPage: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("WebPageDAO.insertWebPage: more than one row inserted: " + vResult);
            }

            long vWebPageID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("WebPageDAO.insertWebPage: created WebPage: " + vWebPageID);

            Date vNow = new Date();

            return
                    new WebPage(
                            vWebPageID,
                            vNow,
                            vNow,
                            iURI
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get web page by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iWebPageID
     * @return WebPage
     * @throws SQLException
     */
    public static WebPage getWebPageByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iWebPageID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_WEB_PAGE_BY_ID);

            vStatement.setLong(1, iWebPageID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                WebPage vWebPage = new WebPage(
                        iWebPageID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("uri")
                );

                return vWebPage;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get web page by uri, return 1 if uri not found
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iUri
     * @return WebPageID
     * @throws SQLException
     */
    public static long getWebPageIDByUri(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iUri
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_WEB_PAGE_ID_BY_URI);

            vStatement.setString(1, iUri);

            vResultSet = vStatement.executeQuery();

            vResultSet.last();
            int vRowCount = vResultSet.getRow();
            if (vRowCount == 1){
                return vResultSet.getLong("id");
            }else{
                return 1;
            }
            /* another method
            ArrayList<Long> vArrayList = new ArrayList<Long>();

            while (vResultSet.next()) {
                    vArrayList.add(vResultSet.getLong("id"));
            }
            if (vArrayList.size() == 1){
                return vArrayList.get(0);
            }else{
                return 0;
            }
            */
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}