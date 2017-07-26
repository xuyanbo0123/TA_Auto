package name.mi.miemail.service;

import name.mi.miemail.dao.EmailRecordDAO;
import name.mi.miemail.dao.EmailRecordUniqueDAO;
import name.mi.miemail.model.EmailRecord;
import name.mi.miemail.model.EmailRecordUnique;
import name.mi.util.dao.EmailCorrectionVariationDAO;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static name.mi.util.UtilityFunctions.*;

public class EmailRecordSaver {
    public EmailRecordSaver()
    {
    }

    public static final String
            P_EMAIL_ADDRESS = "email_address";

    public EmailRecordUnique saveEmailRecordUnique(Logger iLogger, Connection iConnection, String iEmailAddress, EmailRecordUnique.Status iStatus)
    {
        EmailRecordUnique vEmailRecordUnique = null;

        try
        {

            // verify EmailAddress Format and generate EmailDomain
            String vEmailDomain = null;

            Pattern p = Pattern.compile("^((([a-z]|[0-9]|!|#|$|%|&|'|\\*|\\+|\\-|\\/|=|\\?|\\^|_|`|\\{|\\||\\}|~)+" +
                    "(\\.([a-z]|[0-9]|!|#|$|%|&|'|\\*|\\+|\\-|\\/|=|\\?|\\^|_|`|\\{|\\||\\}|~)+)*)" +
                    "@((((([a-z]|[0-9])([a-z]|[0-9]|\\-){0,61}([a-z]|[0-9])\\.))*([a-z]|[0-9])([a-z]|[0-9]|\\-){0,61}([a-z]|[0-9])" +
                    "\\.(af|ax|al|dz|as|ad|ao|ai|aq|ag|ar|am|aw|au|at|az|bs|bh|bd|bb|by|be|bz|bj|bm|bt|bo|ba|bw|bv|br|io|bn|bg|bf|bi|kh" +
                    "|cm|ca|cv|ky|cf|td|cl|cn|cx|cc|co|km|cg|cd|ck|cr|ci|hr|cu|cy|cz|dk|dj|dm|do|ec|eg|sv|gq|er|ee|et" +
                    "|fk|fo|fj|fi|fr|gf|pf|tf|ga|gm|ge|de|gh|gi|gr|gl|gd|gp|gu|gt| gg|gn|gw|gy|ht|hm|va|hn|hk|hu" +
                    "|is|in|id|ir|iq|ie|im|il|it|jm|jp|je|jo|kz|ke|ki|kp|kr|kw|kg|la|lv|lb|ls|lr|ly|li|lt|lu" +
                    "|mo|mk|mg|mw|my|mv|ml|mt|mh|mq|mr|mu|yt|mx|fm|md|mc|mn|ms|ma|mz|mm|na|nr|np|nl|an|nc|nz|ni|ne|ng|nu|nf|mp|no" +
                    "|om|pk|pw|ps|pa|pg|py|pe|ph|pn|pl|pt|pr|qa|re|ro|ru|rw|sh|kn|lc|pm|vc|ws|sm|st|sa|sn|cs|sc|sl|sg|sk|si|sb|so" +
                    "|za|gs|es|lk|sd|sr|sj|sz|se|ch|sy|tw|tj|tz|th|tl|tg|tk|to|tt|tn|tr|tm|tc|tv|ug|ua|ae|gb|us|um|uy|uz" +
                    "|vu|ve|vn|vg|vi|wf|eh|ye|zm|zw|com|edu|gov|int|mil|net|org|biz|info|name|pro|aero|coop|museum|arpa))" +
                    "|(((([0-9]){1,3}\\.){3}([0-9]){1,3}))|(\\[((([0-9]){1,3}\\.){3}([0-9]){1,3})\\])))$");
            Matcher m = p.matcher(iEmailAddress.toLowerCase());
            if (m.find())
            {
                Pattern pSimple = Pattern.compile("@(.+)");
                Matcher mSimple = pSimple.matcher(iEmailAddress);
                if (mSimple.find())
                {
                    vEmailDomain = mSimple.group(1);
                }
            }
            else
            {
                iLogger.error("EmailRecordSaver.saveEmailRecordUnique: Invalid Parameter: " + P_EMAIL_ADDRESS + "=" + iEmailAddress);
            }

            String vEmailAddress = EmailCorrectionVariationDAO.getEmailCorrection(iConnection, iEmailAddress);
            if(vEmailAddress == null || vEmailAddress.isEmpty())
            {
                vEmailAddress = iEmailAddress;
            }

            if(!vEmailAddress.equals(iEmailAddress))
            {
                iLogger.info("EmailRecordSaver.saveEmailRecordUnique: email updated by EmailCorrection. From " + iEmailAddress + " to " + vEmailAddress);
            }

            // insert EmailRecordUnique
            vEmailRecordUnique = EmailRecordUniqueDAO.insertOrUpdateEmailRecordUnique(iLogger, iConnection, vEmailAddress, vEmailDomain, iStatus);

            return vEmailRecordUnique;

        }
        catch (Exception ex)
        {
            iLogger.error("EmailRecordSaver.saveEmailRecordUnique: exception occurred: ", ex);
            return vEmailRecordUnique;
        }
    }

    public EmailRecord saveEmailRecord(Logger iLogger, Connection iConnection, EmailRecordUnique iEmailRecordUnique, long iArrivalID, long iUserID, Map<String, String> iNameValueMap)
    {

        EmailRecord vEmailRecord = null;

        try
        {

            // verify EmailAddress Format and generate EmailDomain
            String vEmailAddress = iEmailRecordUnique.getEmailAddress();
            String vEmailDomain = null;

            Pattern p = Pattern.compile("^((([a-z]|[0-9]|!|#|$|%|&|'|\\*|\\+|\\-|\\/|=|\\?|\\^|_|`|\\{|\\||\\}|~)+" +
                    "(\\.([a-z]|[0-9]|!|#|$|%|&|'|\\*|\\+|\\-|\\/|=|\\?|\\^|_|`|\\{|\\||\\}|~)+)*)" +
                    "@((((([a-z]|[0-9])([a-z]|[0-9]|\\-){0,61}([a-z]|[0-9])\\.))*([a-z]|[0-9])([a-z]|[0-9]|\\-){0,61}([a-z]|[0-9])" +
                    "\\.(af|ax|al|dz|as|ad|ao|ai|aq|ag|ar|am|aw|au|at|az|bs|bh|bd|bb|by|be|bz|bj|bm|bt|bo|ba|bw|bv|br|io|bn|bg|bf|bi|kh" +
                    "|cm|ca|cv|ky|cf|td|cl|cn|cx|cc|co|km|cg|cd|ck|cr|ci|hr|cu|cy|cz|dk|dj|dm|do|ec|eg|sv|gq|er|ee|et" +
                    "|fk|fo|fj|fi|fr|gf|pf|tf|ga|gm|ge|de|gh|gi|gr|gl|gd|gp|gu|gt| gg|gn|gw|gy|ht|hm|va|hn|hk|hu" +
                    "|is|in|id|ir|iq|ie|im|il|it|jm|jp|je|jo|kz|ke|ki|kp|kr|kw|kg|la|lv|lb|ls|lr|ly|li|lt|lu" +
                    "|mo|mk|mg|mw|my|mv|ml|mt|mh|mq|mr|mu|yt|mx|fm|md|mc|mn|ms|ma|mz|mm|na|nr|np|nl|an|nc|nz|ni|ne|ng|nu|nf|mp|no" +
                    "|om|pk|pw|ps|pa|pg|py|pe|ph|pn|pl|pt|pr|qa|re|ro|ru|rw|sh|kn|lc|pm|vc|ws|sm|st|sa|sn|cs|sc|sl|sg|sk|si|sb|so" +
                    "|za|gs|es|lk|sd|sr|sj|sz|se|ch|sy|tw|tj|tz|th|tl|tg|tk|to|tt|tn|tr|tm|tc|tv|ug|ua|ae|gb|us|um|uy|uz" +
                    "|vu|ve|vn|vg|vi|wf|eh|ye|zm|zw|com|edu|gov|int|mil|net|org|biz|info|name|pro|aero|coop|museum|arpa))" +
                    "|(((([0-9]){1,3}\\.){3}([0-9]){1,3}))|(\\[((([0-9]){1,3}\\.){3}([0-9]){1,3})\\])))$");
            Matcher m = p.matcher(vEmailAddress.toLowerCase());
            if (m.find())
            {
                Pattern pSimple = Pattern.compile("@(.+)");
                Matcher mSimple = pSimple.matcher(vEmailAddress);
                if (mSimple.find())
                {
                    vEmailDomain = mSimple.group(1);
                }
            }
            else
            {
                iLogger.error("EmailRecordSaver.saveEmailRecord: Invalid Parameter: " + P_EMAIL_ADDRESS + "=" + vEmailAddress);
            }

            // it is a new email_address
            if (iEmailRecordUnique.getCreatedTS() == iEmailRecordUnique.getUpdatedTS())
            {
                vEmailRecord = EmailRecordDAO.insertEmailRecord(iLogger, iConnection, vEmailAddress, vEmailDomain, EmailRecord.Status.active, iArrivalID, iUserID);
                EmailRecordDAO.insertEmailRecordProperties(iLogger, iConnection, vEmailRecord.getID(), iNameValueMap);
            }
            // it is an existed email_address
            else
            {
                // disable all the old information for the existed email_address
                EmailRecordDAO.updateEmailRecordByEmailAddressAndStatus(iLogger, iConnection, vEmailAddress, EmailRecord.Status.active, EmailRecord.Status.closed);
                vEmailRecord = EmailRecordDAO.insertEmailRecord(iLogger, iConnection, vEmailAddress, vEmailDomain, EmailRecord.Status.active, iArrivalID, iUserID);
                EmailRecordDAO.insertEmailRecordProperties(iLogger, iConnection, vEmailRecord.getID(), iNameValueMap);
            }

            return vEmailRecord;

        }
        catch (Exception ex)
        {
            iLogger.error("EmailRecordSaver.saveEmailRecord: exception occurred: ", ex);
            return vEmailRecord;
        }
    }

    // XYB:
    // This function is ONLY used for AutoFormServlet
    public Map<String, String> generateEmailRecordPropertyMap(List<String> iEmailRecordPropertyField, JsonNode iDataNode)
    {
        Map<String, String> vNameValueMap = new HashMap<String, String>();
        for (String vField : iEmailRecordPropertyField)
        {
            // vField should not be null or empty
            if (vField != null && !vField.isEmpty())
            {
                String vValue = getValuesByNameFromJsonNode(iDataNode, vField.toLowerCase()).get(0);
                // to guaranty vValue for vField.toLowerCase() is not null
                if (vValue != null)
                {
                    vNameValueMap.put(vField, vValue);
                }
            }
        }
        return vNameValueMap;
    }
}
