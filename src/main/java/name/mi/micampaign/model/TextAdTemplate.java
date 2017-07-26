package name.mi.micampaign.model;

import name.mi.micampaign.dao.TextAdTemplateDAO;
import name.mi.ckm.model.TextAd;
import name.mi.util.UtilityFunctions;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Date;
import java.util.List;


@JsonPropertyOrder(value = {"Text_Ad_Template_ID", "Created_TS", "Updated_TS", "Headline", "Description1", "Description2", "Display_Url", "Action_Url", "Priority", "Group"})
public class TextAdTemplate {
    public static final String[] HEADERS = {
        "Text_Ad_ID", "Created_TS", "Updated_TS", "Headline", "Description1", "Description2", "Display_Url", "Action_Url", "Priority", "Group"
    };
    private long
        mID;

    private Date
        mCreatedTS,
        mUpdatedTS;

    private String
        mHeadline,
        mDescription1,
        mDescription2,
        mDisplayUrl,
        mActionUrl;

    private long mPriority;

    private String mGroup;

    private static final Logger LOGGER = LogManager.getLogger(TextAdTemplate.class);

    public TextAdTemplate(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl,
            long iPriority,
            String iGroup
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mHeadline = iHeadline;
        mDescription1 = iDescription1;
        mDescription2 = iDescription2;
        mDisplayUrl = iDisplayUrl;
        mActionUrl = iActionUrl;
        mPriority = iPriority;
        mGroup = iGroup;
    }

    @JsonProperty("Text_Ad_Template_ID")
    public final long getID() {
        return mID;
    }

    @JsonIgnore
    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    @JsonProperty("Created_TS")
    public final String getCreatedTSString() {
        return UtilityFunctions.dateToString(mCreatedTS);
    }


    @JsonIgnore
    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString() {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    @JsonProperty("Headline")
    public final String getHeadline() {
        return mHeadline;
    }

    @JsonProperty("Description1")
    public final String getDescription1() {
        return mDescription1;
    }

    @JsonProperty("Description2")
    public final String getDescription2() {
        return mDescription2;
    }

    @JsonProperty("Display_Url")
    public final String getDisplayUrl() {
        return mDisplayUrl;
    }

    @JsonProperty("Action_Url")
    public final String getActionUrl() {
        return mActionUrl;
    }

    @JsonProperty("Priority")
    public long getPriority() {
        return mPriority;
    }

    @JsonProperty("Group")
    public String getGroup() {
        return mGroup;
    }

    @Override
    public String toString() {
        return "TextAdTemplate{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mHeadline='" + mHeadline + '\'' +
                ", mDescription1='" + mDescription1 + '\'' +
                ", mDescription2='" + mDescription2 + '\'' +
                ", mDisplayUrl='" + mDisplayUrl + '\'' +
                ", mActionUrl='" + mActionUrl + '\'' +
                ", mPriority=" + mPriority +
                ", mGroup='" + mGroup + '\'' +
                '}';
    }

    private String convert(String iStr, List<BasicNameValuePair> iList)
    {
        String vResult = iStr;
        for (BasicNameValuePair vPair : iList)
        {
            String vName = "##"+vPair.getName()+"##";
            if (vResult.contains(vName))
            {
                vResult = vResult.replace(vName, vPair.getValue());
            }
        }
        return vResult;
    }

    private String convert(String iStr, List<BasicNameValuePair> iList, String iProc)
    {
        String vResult = iStr;
        String vReplacement;
        for (BasicNameValuePair vPair : iList)
        {
            String vName = "##"+vPair.getName()+"##";
            if (vResult.contains(vName))
            {
                vReplacement = vPair.getValue();
                if(iProc != null && !iProc.isEmpty())
                {
                    switch (iProc)
                    {
                        case "no_space":
                        {
                            vReplacement = vReplacement.replaceAll(" ", "");
                            break;
                        }
                        case "url_encode":
                        {
                            try
                            {
                                vReplacement = URLEncoder.encode(vPair.getValue(), "UTF-8");
                            }
                            catch (UnsupportedEncodingException e)
                            {
                                System.err.println("TextAdTemplate.convert: Exception: " + e);
                                e.printStackTrace();
                                LOGGER.error("TextAdTemplate.convert: Exception: " + e);
                            }
                            break;
                        }
                        case "no_special":
                        {
                            vReplacement = vReplacement.replaceAll("[^-a-zA-Z0-9_]", "");
                            break;
                        }
                    }
                }

                vResult = vResult.replaceAll(vName, vReplacement);
            }
        }
        return vResult;
    }

    public TextAd generateTextAd(List <BasicNameValuePair> iList)
    {
        String vHeadline = convert(mHeadline,iList);
        String vDescription1 = convert(mDescription1,iList);
        String vDescription2 = convert(mDescription2,iList);
        String vDisplayUrl = convert(mDisplayUrl,iList, "no_special");
        String vActionUrl = convert(mActionUrl,iList);
        Date vNow = new Date();
        return new TextAd(-1, vNow, vNow, vHeadline, vDescription1,vDescription2,vDisplayUrl,vActionUrl);
    }

    public static TextAd getTextAd(Logger iLogger, Connection iConnection, String iGroup, List<BasicNameValuePair> iList)
            throws Exception
    {
        List<TextAdTemplate> vTemplates = TextAdTemplateDAO.getTextAdTemplatesByGroupOrderByPriority(iLogger, iConnection, iGroup);
        for (TextAdTemplate vTemplate:vTemplates)
        {
            TextAd vTextAd = vTemplate.generateTextAd(iList);
            if (vTextAd.isValid())
                return vTextAd;
        }
        return null;
    }
}


