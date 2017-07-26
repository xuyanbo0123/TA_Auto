package name.mi.micore.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder(value = {
        "ID", "Created_TS", "Updated_TS", "Click_Impression_ID", "Token", "Position",
        "Head_Line", "Display_Text", "Logo_Link", "Click_Link", "Display_Link", "Company"
})
public class ClickAd {
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mClickImpressionID;

    private String
            mToken;

    private long
            mPosition;

    private String
            mHeadLine,
            mDisplayText,
            mLogoLink,
            mClickLink,
            mDisplayLink,
            mCompany;

    public ClickAd(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iClickImpressionID,
            String iToken,
            long iPosition,
            String iHeadLine,
            String iDisplayText,
            String iLogoLink,
            String iClickLink,
            String iDisplayLink,
            String iCompany)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mClickImpressionID = iClickImpressionID;
        mToken = iToken;
        mPosition = iPosition;
        mHeadLine = iHeadLine;
        mDisplayText = iDisplayText;
        mLogoLink = iLogoLink;
        mClickLink = iClickLink;
        mDisplayLink = iDisplayLink;
        mCompany = iCompany;
    }

    @JsonProperty("ID")
    public final long getID()
    {
        return mID;
    }

    @JsonIgnore
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    @JsonProperty("Created_TS")
    public final String getCreatedTSString()
    {
        return UtilityFunctions.dateToString(mCreatedTS);
    }

    @JsonIgnore
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString()
    {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    @JsonProperty("Click_Impression_ID")
    public final long getClickImpressionID()
    {
        return mClickImpressionID;
    }

    @JsonProperty("Token")
    public String getToken()
    {
        return mToken;
    }

    @JsonProperty("Position")
    public final long getPosition()
    {
        return mPosition;
    }

    @JsonProperty("Head_Line")
    public String getHeadLine()
    {
        return mHeadLine;
    }

    @JsonProperty("Display_Text")
    public String getDisplayText()
    {
        return mDisplayText;
    }

    @JsonProperty("Logo_Link")
    public String getLogoLink()
    {
        return mLogoLink;
    }

    @JsonIgnore
    public String getClickLink()
    {
        return mClickLink;
    }

    @JsonProperty("Click_Link")
    public String getClickLink64()
    {
        return UtilityFunctions.encode64(mClickLink);
    }

    @JsonProperty("Display_Link")
    public String getDisplayLink()
    {
        return mDisplayLink;
    }

    @JsonProperty("Company")
    public String getCompany()
    {
        return mCompany;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS)
    {
        mUpdatedTS = iUpdatedTS;
    }

    public void setClickImpressionID(long iClickImpressionID)
    {
        mClickImpressionID = iClickImpressionID;
    }

    public void setToken(String iToken)
    {
        mToken = iToken;
    }

    public void setPosition(long iPosition)
    {
        mPosition = iPosition;
    }

    public void setHeadLine(String iHeadLine)
    {
        mHeadLine = iHeadLine;
    }

    public void setDisplayText(String iDisplayText)
    {
        mDisplayText = iDisplayText;
    }

    public void setLogoLink(String iLogoLink)
    {
        mLogoLink = iLogoLink;
    }

    public void setClickLink(String iClickLink)
    {
        mClickLink = iClickLink;
    }

    public void setDisplayLink(String iDisplayLink)
    {
        mDisplayLink = iDisplayLink;
    }

    public void setCompany(String iCompany)
    {
        mCompany = iCompany;
    }

    @Override
    public String toString()
    {
        return "ClickAd{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mClickImpressionID=" + mClickImpressionID +
                ", mToken='" + mToken + '\'' +
                ", mPosition=" + mPosition +
                ", mHeadLine='" + mHeadLine + '\'' +
                ", mDisplayText='" + mDisplayText + '\'' +
                ", mLogoLink='" + mLogoLink + '\'' +
                ", mClickLink='" + mClickLink + '\'' +
                ", mDisplayLink='" + mDisplayLink + '\'' +
                ", mCompany='" + mCompany + '\'' +
                '}';
    }
}
