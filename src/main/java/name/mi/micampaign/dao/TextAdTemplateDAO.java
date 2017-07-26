package name.mi.micampaign.dao;

import name.mi.micampaign.model.TextAdTemplate;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class TextAdTemplateDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TextAdTemplateDAO.class));

    private static final String
            INSERT_INTO_TEXT_AD_TEMPLATE = QUERY_MAP.get("INSERT_INTO_TEXT_AD_TEMPLATE"),
            GET_TEXT_AD_TEMPLATE_BY_ID = QUERY_MAP.get("GET_TEXT_AD_TEMPLATE_BY_ID"),
            GET_TEXT_AD_TEMPLATE_BY_CONTENT = QUERY_MAP.get("GET_TEXT_AD_TEMPLATE_BY_CONTENT"),
            GET_TEXT_AD_TEMPLATES_BY_GROUP_ORDER_BY_PRIORITY = QUERY_MAP.get("GET_TEXT_AD_TEMPLATES_BY_GROUP_ORDER_BY_PRIORITY");

    public static TextAdTemplate getTextAdTemplateByContent(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_TEXT_AD_TEMPLATE_BY_CONTENT
            );
            int
                    vColumnIndex = 0;

            vStatement.setString(++vColumnIndex, iHeadline);
            vStatement.setString(++vColumnIndex, iDescription1);
            vStatement.setString(++vColumnIndex, iDescription2);
            vStatement.setString(++vColumnIndex, iDisplayUrl);
            vStatement.setString(++vColumnIndex, iActionUrl);


            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                TextAdTemplate
                        vTextAdTemplate = new TextAdTemplate(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("headline"),
                        vResultSet.getString("description1"),
                        vResultSet.getString("description2"),
                        vResultSet.getString("displayUrl"),
                        vResultSet.getString("actionUrl"),
                        SqlUtils.getLong(vResultSet, "priority"),
                        vResultSet.getString("group")
                );

                return vTextAdTemplate;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }

    }

    public static TextAdTemplate insertTextAdTemplate(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl,
            long iPriority,
            String iGroup
    )
            throws SQLException {
        TextAdTemplate vTextAdTemplate = getTextAdTemplateByContent(iLogger, iDatabaseConnection, iHeadline, iDescription1, iDescription2, iDisplayUrl, iActionUrl);
        if (vTextAdTemplate != null)
            return null;
        String
                vQueryStr = INSERT_INTO_TEXT_AD_TEMPLATE;

        PreparedStatement
                vPreparedStatement = null;

        try {

            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iHeadline);
            vPreparedStatement.setString(++vColumnIndex, iDescription1);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDescription2);
            vPreparedStatement.setString(++vColumnIndex, iDisplayUrl);
            vPreparedStatement.setString(++vColumnIndex, iActionUrl);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iPriority);
            vPreparedStatement.setString(++vColumnIndex, iGroup);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "TextAdTemplateDAO.insertTextAdTemplate: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "TextAdTemplateDAO.insertTextAdTemplate: more than one row inserted: " + vResult
                );
            }

            long
                    vTextAdTemplateID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("TextAdTemplateDAO.insertTextAdTemplate: created TextAdTemplate: " + vTextAdTemplateID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new TextAdTemplate(
                            vTextAdTemplateID,
                            vNow,
                            vNow,
                            iHeadline,
                            iDescription1,
                            iDescription2,
                            iDisplayUrl,
                            iActionUrl,
                            iPriority,
                            iGroup
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static TextAdTemplate getTextAdTemplateByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTextAdTemplateID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_TEXT_AD_TEMPLATE_BY_ID
            );

            vStatement.setLong(1, iTextAdTemplateID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                TextAdTemplate
                        vTextAdTemplate = new TextAdTemplate(
                        iTextAdTemplateID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("headline"),
                        vResultSet.getString("description1"),
                        vResultSet.getString("description2"),
                        vResultSet.getString("displayUrl"),
                        vResultSet.getString("actionUrl"),
                        SqlUtils.getLong(vResultSet, "priority"),
                        vResultSet.getString("group")
                );

                return vTextAdTemplate;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<TextAdTemplate> getTextAdTemplatesByGroupOrderByPriority(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iGroup
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_TEXT_AD_TEMPLATES_BY_GROUP_ORDER_BY_PRIORITY
            );

            vStatement.setString(1, iGroup);

            vResultSet = vStatement.executeQuery();
            List<TextAdTemplate> vList = new ArrayList<>();

            while (vResultSet.next()) {
                TextAdTemplate
                        vTextAdTemplate = new TextAdTemplate(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("headline"),
                        vResultSet.getString("description1"),
                        vResultSet.getString("description2"),
                        vResultSet.getString("displayUrl"),
                        vResultSet.getString("actionUrl"),
                        SqlUtils.getLong(vResultSet, "priority"),
                        vResultSet.getString("group")
                );
                vList.add(vTextAdTemplate);
            }

            return vList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
