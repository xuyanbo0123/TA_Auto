package name.mi.util.dao;

import name.mi.util.SqlUtils;
import name.mi.util.model.EmailCorrectionVariation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weixiong on 1/16/14.
 */
public class EmailCorrectionVariationDAO
{
    private static final Map<String, String>
        QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailCorrectionVariationDAO.class));

    private static final String
        GET_EMAIL_CORRECTION_VARIATION_DETAILS_BY_HOSTNAME_VARIATION = QUERY_MAP.get("GET_EMAIL_CORRECTION_VARIATION_DETAILS_BY_HOSTNAME_VARIATION");


    public static EmailCorrectionVariation getCorrectionByHostnameVariation(Connection iConnection, String iHostname, String iExtension) throws Exception
    {
        if(iHostname == null || iHostname.isEmpty() || iConnection == null)
        {
            return null;
        }

        PreparedStatement vPreparedStatement = null;
        String vQuery = GET_EMAIL_CORRECTION_VARIATION_DETAILS_BY_HOSTNAME_VARIATION;

        vPreparedStatement = iConnection.prepareStatement(vQuery, Statement.RETURN_GENERATED_KEYS);
        ResultSet vResultSet = null;

        try
        {
            int vColumnIndex = 0;
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iHostname);

            vResultSet = vPreparedStatement.executeQuery();

            while(vResultSet.next())
            {
                if(vResultSet.getString("variation_type") == null || vResultSet.getString("variation") == null || vResultSet.getString("variation").isEmpty())
                {
                    continue;
                }

                EmailCorrectionVariation vECV = new EmailCorrectionVariation(
                    vResultSet.getLong("id"),
                    vResultSet.getString("variation"),
                    EmailCorrectionVariation.VariationType.valueOf(vResultSet.getString("variation_type")),
                    vResultSet.getLong("hostname_id"),
                    vResultSet.getString("hostname"),
                    vResultSet.getLong("extension_id"),
                    vResultSet.getString("extension")
                );

                if(vECV.getExtension() != null && (vECV.getExtension().isEmpty() || iExtension == null || iExtension.isEmpty() || vECV.getExtension().equals(iExtension)))
                {
                    return vECV;
                }
            }

            return null;

        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * Returns an EmailCOrrectionVariation object if a correct email host is found. If not found (could be new variation, or correct host name), returns null
     * @param iConnection
     * @param iEmail
     * @return
     * @throws Exception
     */
    public static EmailCorrectionVariation correctEmail(Connection iConnection, String iEmail) throws Exception
    {
        if(iEmail == null || iEmail.isEmpty() || iConnection == null)
        {
            return null;
        }

        Pattern vEmailPtn = Pattern.compile("[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@([a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9])\\.([a-zA-Z][a-zA-Z\\.]*[a-zA-Z])");
        Matcher vEmailMch = vEmailPtn.matcher(iEmail);
        String vHost = null;
        String vExt = null;

        if(vEmailMch.find() && vEmailMch.groupCount() == 2)
        {
            vHost = vEmailMch.group(1);
            vExt = vEmailMch.group(2);

            return correctEmail(iConnection, vHost, vExt);
        }

        return null;
    }

    public static EmailCorrectionVariation correctEmail(Connection iConnection, String iHost, String iExt) throws Exception
    {
        if(iConnection == null)
        {
            return null;
        }
        EmailCorrectionVariation vECV = null;

        vECV = getCorrectionByHostnameVariation(iConnection, iHost, iExt);

        return vECV;
    }

    /**
     * A more convenient function to correct email: will return original if no correction is done, and will return corrected email if any correction is done
     * @param iConnection
     * @param iEmail
     * @return
     * @throws Exception
     */
    public static String getEmailCorrection(Connection iConnection, String iEmail) throws Exception
    {
        if(iConnection == null || iEmail == null || iEmail.isEmpty())
        {
            return null;
        }

        Pattern vEmailPtn = Pattern.compile("([a-zA-Z][\\w\\.-]*[a-zA-Z0-9])@([a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9])\\.([a-zA-Z][a-zA-Z\\.]*[a-zA-Z])");
        Matcher vEmailMch = vEmailPtn.matcher(iEmail);
        String vHost = null;
        String vExt = null;
        String vLeading = null;
        EmailCorrectionVariation vECV = null;
        String vResultEmail;

        if(vEmailMch.find() && vEmailMch.groupCount() == 3)
        {
            vLeading = vEmailMch.group(1);
            vHost = vEmailMch.group(2);
            vExt = vEmailMch.group(3);

            vECV = correctEmail(iConnection, vHost, vExt);

            if(vECV == null)
            {
                return iEmail;
            }
            else if(vECV.getHostname() != null && !vECV.getHostname().isEmpty() && vECV.getExtension() != null)
            {
                vResultEmail = vLeading + "@" + vECV.getHostname() + "." + (vECV.getExtension().isEmpty() ? vExt : vECV.getExtension());
                return vResultEmail;
            }
        }

        return iEmail;
    }

}
