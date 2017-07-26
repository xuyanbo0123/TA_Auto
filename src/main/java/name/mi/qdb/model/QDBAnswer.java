package name.mi.qdb.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 6/30/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */

@JsonPropertyOrder(value = {"Question_ID", "Correct_Index", "Explanation"})
public class QDBAnswer {
    private long
            mID;

    private int
            mCorrectIdx;

    private String
            mExplanation;

    public QDBAnswer(
            long iID,
            int iCorrectIdx,
            String iExplanation
            )
    {
        mID = iID;
        mCorrectIdx = iCorrectIdx;
        mExplanation = iExplanation;
    }

    @JsonProperty("Question_ID")
    public long getID() {
        return mID;
    }

    @JsonProperty("Correct_Index")
    public int getCorrectIdx() {
        return mCorrectIdx;
    }

    @JsonProperty("Explanation")
    public String getExplanation() {
        return mExplanation;
    }
}
