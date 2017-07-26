package name.mi.miemail.service;

import name.mi.miemail.dao.EmailRecordDAO;
import name.mi.miemail.dao.EmailReplacementTextDAO;
import name.mi.miemail.dao.EmailSendDAO;
import name.mi.miemail.model.EmailSend;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailSender {

    private String mEmailAddress;
    private String mEmailSubject;
    private String mEmailContent;
    private String mFromName;
    private String mFromAddress;
    private String mFromText;

    public EmailSender()
    {
    }

    // DEFAULT BCC Email Array
    public static final String[] P_BCC_EMAILS = new String[]{"g.white.20130829@gmail.com"};

    // Email Sender Information
    public static final String
            P_EMAIL_SENDER_ADDRESS = "support@quotes2compare.com",
            P_EMAIL_SENDER_PASSWORD = "dC]e^H7C~T0gZGE";

    // for Tables related to Email_Send Table
    public static final String
            P_EMAIL_ADDRESS = "email_address",
            P_EMAIL_SUBJECT = "subject",
            P_EMAIL_CONTENT = "content",
            P_FROM_NAME = "name",
            P_FROM_ADDRESS = "from_address",
            P_FROM_TEXT = "from_text";

    public boolean sendEmail(Logger iLogger, Connection iDatabaseConnection, EmailSend iEmailSend) throws MessagingException, UnsupportedEncodingException, SQLException
    {
        return sendEmail(iLogger, iDatabaseConnection, iEmailSend, P_BCC_EMAILS);
    }

    public boolean sendEmail(Logger iLogger, Connection iDatabaseConnection, EmailSend iEmailSend, String[] iBccEmails) throws MessagingException, UnsupportedEncodingException, SQLException
    {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(P_EMAIL_SENDER_ADDRESS, P_EMAIL_SENDER_PASSWORD);
                    }
                });

        // get customer information required by email
        Map<String, String> vMap = EmailRecordDAO.getAllEmailRecordProperties(iLogger, iDatabaseConnection, iEmailSend.getEmailRecordID());

        // get MavenIntelligence information required by email
        Map<String, String> vMapForMI = EmailReplacementTextDAO.getAllEmailReplacementText(iLogger, iDatabaseConnection);

        // join two map to get whole information required by email
        vMap.putAll(vMapForMI);

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {

            vStatement = iDatabaseConnection.prepareStatement("SELECT ER.email_address, ET.`subject`, ET.content, EF.`name`, EF.from_address, EF.from_text\n" +
                    "FROM Email_Record AS ER, Email_Template AS ET, Email_From AS EF\n" +
                    "WHERE ER.`status` = 'active' AND ET.`status` = 'active'\n" +
                    "AND ER.id = ?\n" +
                    "AND ET.id = ?\n" +
                    "AND EF.id = ?");

            vStatement.setLong(1, iEmailSend.getEmailRecordID());
            vStatement.setLong(2, iEmailSend.getEmailTemplateID());
            vStatement.setLong(3, iEmailSend.getEmailFromID());

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                mEmailAddress = vResultSet.getString(P_EMAIL_ADDRESS);
                mEmailSubject = vResultSet.getString(P_EMAIL_SUBJECT);
                mEmailContent = vResultSet.getString(P_EMAIL_CONTENT);
                mFromName = vResultSet.getString(P_FROM_NAME);
                mFromAddress = vResultSet.getString(P_FROM_ADDRESS);
                mFromText = vResultSet.getString(P_FROM_TEXT);
            }
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }

        // set Email basic information
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mFromAddress, mFromName));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mEmailAddress));
        message.setSubject(mEmailSubject);

        // set Bcc Emails
        for (String vBccEmail : iBccEmails)
        {
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(vBccEmail));
        }

        // set EmailContent
        Pattern p = Pattern.compile("\\[\\[(.+?)\\]\\]");
        Matcher m = p.matcher(mEmailContent);
        while (m.find())
        {
            String vStr = vMap.get(m.group(1));
            if (vStr == null || vStr.isEmpty())
            {
                iLogger.error("EmailSender.sendEmail: Missing Parameter: " + m.group(1));
                return false;
            }
            else
            {
                mEmailContent = mEmailContent.replaceFirst(m.group(1), vStr);
            }
        }
        mEmailContent = mEmailContent.replace("[[", "");
        mEmailContent = mEmailContent.replace("]]", "");

        StringBuilder vBuffer = new StringBuilder();

        vBuffer.append(mEmailContent).append("<BR/>");

        message.setContent(vBuffer.toString(), "text/html");

        Transport.send(message);

        // update sent_ts
        EmailSendDAO.updateEmailSendSentTS(iLogger, iDatabaseConnection, iEmailSend.getID());

        iLogger.info("Email for EmailSendID = " + iEmailSend.getID() + ", has been sent!");

        return true;
    }

    public boolean sendEmailToBuyer(Logger iLogger, String iBuyerEmail, String iEmailContent, String[] iBccEmails) throws MessagingException, UnsupportedEncodingException
    {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(P_EMAIL_SENDER_ADDRESS, P_EMAIL_SENDER_PASSWORD);
                    }
                });

        mEmailAddress = iBuyerEmail;
        mEmailSubject = "New Lead";
        mEmailContent = iEmailContent;
        mFromName = "quotes2compare";
        mFromAddress = "support@quotes2compare.com";

        // set Email basic information
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mFromAddress, mFromName));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mEmailAddress));

        for (String vBccEmail : iBccEmails)
        {
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(vBccEmail));
        }

        message.setSubject(mEmailSubject);

        // set EmailContent
        StringBuilder vBuffer = new StringBuilder();

        vBuffer.append(mEmailContent).append("<BR/>");

        message.setContent(vBuffer.toString(), "text/html");

        Transport.send(message);

        return true;
    }

    public boolean sendEmailToBuyer(Logger iLogger, String iBuyerEmail, String iEmailContent) throws MessagingException, UnsupportedEncodingException
    {
        return sendEmailToBuyer(iLogger, iBuyerEmail, iEmailContent, null);
    }
}
