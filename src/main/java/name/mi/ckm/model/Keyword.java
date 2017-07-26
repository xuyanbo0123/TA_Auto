package name.mi.ckm.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 3/9/13
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonPropertyOrder(value = {"Keyword_Text_ID", "Text"})
public class Keyword {
    public static final String[] HEADERS = {
            "Keyword_Text_ID", "Text"
    };
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private String
            mText;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iText
     */
    public Keyword(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iText
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mText = iText;
    }

    /**
     * @return ID of the Keyword
     */

    @JsonProperty("Keyword_Text_ID")
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

//    @JsonProperty("Created_TS")
//         public final String getCreatedTSString() {
//        return UtilityFunctions.dateToString(mCreatedTS);
//    }


    /**
     * @return updated time stamp
     */
    @JsonIgnore
    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

//    @JsonProperty("Updated_TS")
//    public final String getUpdatedTSString() {
//        return UtilityFunctions.dateToString(mUpdatedTS);
//    }

    /**
     * @return Text
     */
    @JsonProperty("Text")
    public final String getText() {
        return mText;
    }


    @Override
    public String toString() {
        return "Keyword{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mText='" + mText + '\'' +
                '}';
    }
}

