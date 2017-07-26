package name.mi.ckm.model;

import name.mi.util.UtilityFunctions;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

@JsonPropertyOrder(value = {"Text_Ad_ID", "Created_TS", "Updated_TS", "Headline", "Description1", "Description2", "Display_Url", "Action_Url"})
public class TextAd {
    public static final String[] HEADERS = {
            "Text_Ad_ID", "Created_TS", "Updated_TS", "Headline", "Description1", "Description2", "Display_Url", "Action_Url"
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

    public static TextAd parseGoogleTextAd(com.google.api.ads.adwords.axis.v201306.cm.TextAd iGoogleTextAd)
    {
        Date vNow = new Date();
        return new TextAd(
                -1,
                vNow,
                vNow,
                iGoogleTextAd.getHeadline(),
                iGoogleTextAd.getDescription1(),
                iGoogleTextAd.getDescription2(),
                iGoogleTextAd.getDisplayUrl(),
                iGoogleTextAd.getUrl()
        );

    }

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iHeadline
     * @param iDescription1
     * @param iDescription2
     * @param iDisplayUrl
     * @param iActionUrl
     */
    public TextAd(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mHeadline = iHeadline;
        mDescription1 = iDescription1;
        mDescription2 = iDescription2;
        mDisplayUrl = iDisplayUrl;
        mActionUrl = iActionUrl;
    }

    /**
     * @return ID of the TextAd
     */
    @JsonProperty("Text_Ad_ID")
    public final long getID() {
        return mID;
    }

    /**
     * @return created time stamp
     */
    @JsonIgnore
    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    @JsonProperty("Created_TS")
    public final String getCreatedTSString() {
        return UtilityFunctions.dateToString(mCreatedTS);
    }


    /**
     * @return updated time stamp
     */
    @JsonIgnore
    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString() {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    /**
     * @return Headline
     */
    @JsonProperty("Headline")
    public final String getHeadline() {
        return mHeadline;
    }

    /**
     * @return Description1
     */
    @JsonProperty("Description1")
    public final String getDescription1() {
        return mDescription1;
    }

    /**
     * @return Description2
     */
    @JsonProperty("Description2")
    public final String getDescription2() {
        return mDescription2;
    }

    /**
     * @return DisplayUrl
     */
    @JsonProperty("Display_Url")
    public final String getDisplayUrl() {
        return mDisplayUrl;
    }

    /**
     * @return ActionUrl
     */
    @JsonProperty("Action_Url")
    public final String getActionUrl() {
        return mActionUrl;
    }

    @Override
    public String toString() {
        return "TextAd{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mHeadline='" + mHeadline + '\'' +
                ", mDescription1='" + mDescription1 + '\'' +
                ", mDescription2='" + mDescription2 + '\'' +
                ", mDisplayUrl='" + mDisplayUrl + '\'' +
                ", mActionUrl='" + mActionUrl + '\'' +
                '}';
    }

    @JsonIgnore
    public boolean isValid()
    {
        if (mHeadline.length()>25 || mHeadline.contains("##"))
        {
            return false;
        }
        if (mDescription1.length()>35 || mDescription1.contains("##"))
        {
            return false;
        }
        if (mDescription2.length()>35 || mDescription2.contains("##"))
        {
            return false;
        }
        if (mDisplayUrl.length()>35 || mDisplayUrl.contains("##"))
        {
            return false;
        }
        if (mActionUrl.length()>60 || mActionUrl.contains("##"))
        {
            return false;
        }

        return true;
    }

    @JsonIgnore
    public boolean textEquals(TextAd iCompareTo)
    {
        if(iCompareTo == null)
        {
            return false;
        }

        if(
            this.getHeadline().equals(iCompareTo.getHeadline()) &&
            this.getDescription1().equals(iCompareTo.getDescription1()) &&
            this.getDescription2().equals(iCompareTo.getDescription2()) &&
            this.getDisplayUrl().equals(iCompareTo.getDisplayUrl()) &&
            this.getActionUrl().equals(iCompareTo.getActionUrl())
        )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
