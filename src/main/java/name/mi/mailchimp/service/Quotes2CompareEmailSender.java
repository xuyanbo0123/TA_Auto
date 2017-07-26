package name.mi.mailchimp.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by XYB on 3/28/2014.
 */
public class Quotes2CompareEmailSender {
        // DEFAULT BCC Email Array
        public static final String[] P_BCC_EMAILS = new String[]{"g.white.20130829@gmail.com"};

        // Email Sender Information
        public static final String
                P_EMAIL_SENDER_ADDRESS = "support@quotes2compare.com",
                P_EMAIL_SENDER_PASSWORD = "dC]e^H7C~T0gZGE";

        // Content Replacement
        public static final String
                AUTO_INSURANCE_LOGO_IMG_URL = "http://www.quotes2compare.com/images/neo/quotes2compare-logo.png",
                AUTO_INSURANCE_CONTACT_US_URL = "http://www.quotes2compare.com/contact-us",
                AUTO_INSURANCE_HOME_PAGE_URL = "http://www.quotes2compare.com",
                AUTO_INSURANCE_INSURANCE_LIBRARY_URL = "http://www.quotes2compare.com/insurance-library",
                AUTO_INSURANCE_PRIVACY_STATEMENT_URL = "http://www.quotes2compare.com/privacy-policy";

        private String mEmailAddress;
        private String mFirstName;
        private String mLastName;
        private String mEmailSubject;
        private String mEmailContent;
        private String mFromAddress;
        private String mFromName;

        public Quotes2CompareEmailSender(String iEmailAddress, String iFirstName, String iLastName, String iEmailSubject, String iEmailContent, String iFromAddress, String iFromName)
        {
            mEmailAddress = iEmailAddress;
            mFirstName = iFirstName;
            mLastName = iLastName;
            mEmailSubject = iEmailSubject;
            mEmailContent = iEmailContent;
            mFromAddress = iFromAddress;
            mFromName = iFromName;
        }

        public boolean sendEmail() throws MessagingException, UnsupportedEncodingException, SQLException
        {
            return sendEmail(P_BCC_EMAILS);
        }

        public boolean sendEmail(String[] iBccEmails)
        {
            try
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
                        }
                );

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
                mEmailContent = mEmailContent.replaceAll("\\[\\[FIRST_NAME\\]\\]", mFirstName);
                mEmailContent = mEmailContent.replaceAll("\\[\\[LAST_NAME\\]\\]", mLastName);

                mEmailContent = mEmailContent.replaceAll("\\[\\[AUTO_INSURANCE_LOGO_IMG_URL\\]\\]", AUTO_INSURANCE_LOGO_IMG_URL);
                mEmailContent = mEmailContent.replaceAll("\\[\\[AUTO_INSURANCE_CONTACT_US_URL\\]\\]", AUTO_INSURANCE_CONTACT_US_URL);
                mEmailContent = mEmailContent.replaceAll("\\[\\[AUTO_INSURANCE_HOME_PAGE_URL\\]\\]", AUTO_INSURANCE_HOME_PAGE_URL);
                mEmailContent = mEmailContent.replaceAll("\\[\\[AUTO_INSURANCE_INSURANCE_LIBRARY_URL\\]\\]", AUTO_INSURANCE_INSURANCE_LIBRARY_URL);
                mEmailContent = mEmailContent.replaceAll("\\[\\[AUTO_INSURANCE_PRIVACY_STATEMENT_URL\\]\\]", AUTO_INSURANCE_PRIVACY_STATEMENT_URL);

                StringBuilder vBuffer = new StringBuilder();

                vBuffer.append(mEmailContent).append("<BR/>");

                message.setContent(vBuffer.toString(), "text/html");

                // send email
                Transport.send(message);

                return true;
            }
            catch (MessagingException | UnsupportedEncodingException e)
            {
                return false;
            }
        }
}
