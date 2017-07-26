package name.mi.miemail.service;

import name.mi.miemail.dao.EmailSendDAO;
import name.mi.miemail.model.EmailRecord;
import name.mi.miemail.model.EmailSend;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class EmailSendGenerator {
    public EmailSendGenerator() {
    }

    private class GeneratorInformation {
        private long mEmailRoleID;
        private long mEmailProgramStepID;
        private long mEmailTemplateID;
        private long mEmailFromID;
        private String mRuleName;
        private String mRuleValue;

        private GeneratorInformation(long iEmailRoleID, long iEmailProgramStepID, long iEmailTemplateID, long iEmailFromID, String iRuleName, String iRuleValue) {
            mEmailRoleID = iEmailRoleID;
            mEmailProgramStepID = iEmailProgramStepID;
            mEmailTemplateID = iEmailTemplateID;
            mEmailFromID = iEmailFromID;
            mRuleName = iRuleName;
            mRuleValue = iRuleValue;
        }
    }

    private static final Timestamp DEFAULT_TIMESTAMP = Timestamp.valueOf("1970-01-01 00:00:00");

    public ArrayList<EmailSend> generateImmediateEmailSend(Logger iLogger, Connection iConnection, EmailRecord iEmailRecord) {
        EmailSend vEmailSend = null;

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        ArrayList<GeneratorInformation> vArrayListGI = new ArrayList<GeneratorInformation>();

        ArrayList<EmailSend> vArrayListES = new ArrayList<EmailSend>();

        try {

            // get ArrayList<GeneratorInformation>
            /*
            SELECT ERRM.email_role_id, EPS.id, EPS.email_template_id, EPS.email_from_id, ERule.`name`, ERule.`value`
            FROM Email_Record AS ERecord
            JOIN Email_Record_Role_Map AS ERRM ON ERRM.email_record_id = ERecord.id
            JOIN Email_Role AS ERole ON ERole.id = ERRM.email_role_id
            JOIN Email_Program_Step AS EPS ON EPS.email_program_id = ERole.email_program_id
            JOIN Email_Program_Step_Rule_Map AS EPSRM ON EPSRM.email_program_step_id = EPS.id AND EPSRM.`status` = 'active'
            JOIN Email_Rule AS ERule ON ERule.id = EPSRM.email_rule_id
            WHERE ERecord.id = ? AND ERecord.`status` = 'active';
            */
            vStatement = iConnection.prepareStatement("SELECT ERRM.email_role_id, EPS.id, EPS.email_template_id, EPS.email_from_id, ERule.`name`, ERule.`value`\n" +
                    "FROM Email_Record AS ERecord\n" +
                    "JOIN Email_Record_Role_Map AS ERRM ON ERRM.email_record_id = ERecord.id\n" +
                    "JOIN Email_Role AS ERole ON ERole.id = ERRM.email_role_id\n" +
                    "JOIN Email_Program_Step AS EPS ON EPS.email_program_id = ERole.email_program_id\n" +
                    "JOIN Email_Program_Step_Rule_Map AS EPSRM ON EPSRM.email_program_step_id = EPS.id AND EPSRM.`status` = 'active'\n" +
                    "JOIN Email_Rule AS ERule ON ERule.id = EPSRM.email_rule_id\n" +
                    "WHERE ERecord.id = ? AND ERecord.`status` = 'active';");

            vStatement.setLong(1, iEmailRecord.getID());

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {

                GeneratorInformation vGeneratorInformation = new GeneratorInformation(
                        vResultSet.getLong("email_role_id"),
                        vResultSet.getLong("id"),
                        vResultSet.getLong("email_template_id"),
                        vResultSet.getLong("email_from_id"),
                        vResultSet.getString("name"),
                        vResultSet.getString("value"));

                vArrayListGI.add(vGeneratorInformation);
            }

            // decide EmailSend
            // this part contains the whole logic related to f: EmailRecord ----> EmailSend
            for (GeneratorInformation gi : vArrayListGI) {
                // logic for TimeRule
                if (gi.mRuleName.equals("TimeRule")) {
                    long vRuleValue = Long.parseLong(gi.mRuleValue);
                    // immediate Email
                    if (vRuleValue == 0) {
                        vEmailSend = EmailSendDAO.insertEmailSend(
                                iLogger,
                                iConnection,
                                iEmailRecord.getID(),
                                gi.mEmailRoleID,
                                gi.mEmailProgramStepID,
                                gi.mEmailTemplateID,
                                gi.mEmailFromID,
                                new Timestamp(new Date().getTime()),
                                null,
                                null);

                        vArrayListES.add(vEmailSend);

                    }
                }
                // logic for OtherRule
                // to be finished further
            }

        } catch (SQLException e) {
            iLogger.error("EmailSendGenerator.generateEmailSend error: " + e);
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
        return vArrayListES;
    }

    public ArrayList<EmailSend> generateGeneralEmailSend(Logger iLogger, Connection iConnection, EmailRecord iEmailRecord) {
        EmailSend vEmailSend = null;

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        ArrayList<GeneratorInformation> vArrayListGI = new ArrayList<GeneratorInformation>();

        ArrayList<EmailSend> vArrayListES = new ArrayList<EmailSend>();

        try {

            // get ArrayList<GeneratorInformation>
            /*
            SELECT ERRM.email_role_id, EPS.id, EPS.email_template_id, EPS.email_from_id, ERule.`name`, ERule.`value`
            FROM Email_Record AS ERecord
            JOIN Email_Record_Role_Map AS ERRM ON ERRM.email_record_id = ERecord.id
            JOIN Email_Role AS ERole ON ERole.id = ERRM.email_role_id
            JOIN Email_Program_Step AS EPS ON EPS.email_program_id = ERole.email_program_id
            JOIN Email_Program_Step_Rule_Map AS EPSRM ON EPSRM.email_program_step_id = EPS.id AND EPSRM.`status` = 'active'
            JOIN Email_Rule AS ERule ON ERule.id = EPSRM.email_rule_id
            WHERE ERecord.id = ?
            */
            vStatement = iConnection.prepareStatement("SELECT ERRM.email_role_id, EPS.id, EPS.email_template_id, EPS.email_from_id, ERule.`name`, ERule.`value`\n" +
                    "FROM Email_Record AS ERecord\n" +
                    "JOIN Email_Record_Role_Map AS ERRM ON ERRM.email_record_id = ERecord.id\n" +
                    "JOIN Email_Role AS ERole ON ERole.id = ERRM.email_role_id\n" +
                    "JOIN Email_Program_Step AS EPS ON EPS.email_program_id = ERole.email_program_id\n" +
                    "JOIN Email_Program_Step_Rule_Map AS EPSRM ON EPSRM.email_program_step_id = EPS.id AND EPSRM.`status` = 'active'\n" +
                    "JOIN Email_Rule AS ERule ON ERule.id = EPSRM.email_rule_id\n" +
                    "WHERE ERecord.id = ?");


            vStatement.setLong(1, iEmailRecord.getID());

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {

                GeneratorInformation vGeneratorInformation = new GeneratorInformation(
                        vResultSet.getLong("email_role_id"),
                        vResultSet.getLong("id"),
                        vResultSet.getLong("email_template_id"),
                        vResultSet.getLong("email_from_id"),
                        vResultSet.getString("name"),
                        vResultSet.getString("value"));

                vArrayListGI.add(vGeneratorInformation);
            }

            // decide EmailSend
            // this part contains the whole logic related to f: EmailRecord ----> EmailSend
            for (GeneratorInformation gi : vArrayListGI) {
                // logic for TimeRule
                if (gi.mRuleName.equals("TimeRule")) {
                    Date vNow = new Date();
                    Date vCreated = iEmailRecord.getCreatedTS();
                    long vDifference = Long.parseLong(gi.mRuleValue);
                    // non-immediate Email
                    if (vDifference != 0) {
                        boolean vTimeToSend = timeToSend(vCreated, vNow, vDifference);
                        // Time to Send according to the TimeRule
                        if (vTimeToSend) {
                            boolean vHasBeenAddedIntoEmailSend = hasBeenAddedIntoEmailSend(
                                    iConnection,
                                    iEmailRecord.getID(),
                                    gi.mEmailRoleID,
                                    gi.mEmailProgramStepID,
                                    gi.mEmailTemplateID,
                                    gi.mEmailFromID);
                            // Has Not Been Added Into EmailSend or Need to be Added Into EmailSend
                            if (!vHasBeenAddedIntoEmailSend) {
                                vEmailSend = EmailSendDAO.insertEmailSend(
                                        iLogger,
                                        iConnection,
                                        iEmailRecord.getID(),
                                        gi.mEmailRoleID,
                                        gi.mEmailProgramStepID,
                                        gi.mEmailTemplateID,
                                        gi.mEmailFromID,
                                        null,
                                        null,
                                        null);
                                vArrayListES.add(vEmailSend);
                            }
                        }
                    }
                }
                // logic for OtherRule
                // to be finished further
            }

        } catch (SQLException e) {
            iLogger.error("EmailSendGenerator.generateEmailSend error: " + e);
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
        return vArrayListES;
    }

    private long getDateDifferenceInMilliseconds(Date iDate1, Date iDate2) {
        long iDate1Long = iDate1.getTime();
        long iDate2Long = iDate2.getTime();
        return iDate2Long - iDate1Long;
    }

    private boolean timeToSend(Date iDate1, Date iDate2, long iDifference) {
        long iDate1Long = iDate1.getTime();
        long iDate2Long = iDate2.getTime();
        if (iDate2Long - iDate1Long < iDifference * 60 * 1000) {
            return false;
        } else {
            return true;
        }
    }

    private boolean hasBeenAddedIntoEmailSend(Connection iConnection,
                                              long iEmailRecordID,
                                              long iEmailRoleID,
                                              long iEmailProgramStepID,
                                              long iEmailTemplateID,
                                              long iEmailFromID)
            throws SQLException {

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {

            vStatement = iConnection.prepareStatement("SELECT ES.id\n" +
                    "FROM Email_Send AS ES\n" +
                    "WHERE ES.email_record_id = ?\n" +
                    "AND ES.email_role_id = ?\n" +
                    "AND ES.email_program_step_id = ?\n" +
                    "AND ES.email_template_id = ?\n" +
                    "AND ES.email_from_id = ?");

            vStatement.setLong(1, iEmailRecordID);
            vStatement.setLong(2, iEmailRoleID);
            vStatement.setLong(3, iEmailProgramStepID);
            vStatement.setLong(4, iEmailTemplateID);
            vStatement.setLong(5, iEmailFromID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                return true;
            } else {
                return false;
            }
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
