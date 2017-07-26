package name.mi.qdb.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 6/30/13
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonPropertyOrder(value = {"Question_ID", "State", "Question", "Answers", "Hint", "Pic"})
public class QDBQuestion {
    private long mID;
    private String
            mState,      //may move to an enum
            mQuestion;
    private String[] mAnswers = new String[4];
    private String
            mHint;
    private String
            mPic;

    public QDBQuestion(
            long iID,
            String iState,
            String iQuestion,
            String iAnswer0,
            String iAnswer1,
            String iAnswer2,
            String iAnswer3,
            String iHint,
            String iPic
            )
    {
        mID = iID;
        mState = iState;
        mQuestion = iQuestion;
        mAnswers[0] = iAnswer0;
        mAnswers[1] = iAnswer1;
        mAnswers[2] = iAnswer2;
        mAnswers[3] = iAnswer3;
        mHint = iHint;
        mPic = iPic;
    }

    @JsonProperty("Question_ID")
    public long getID() {
        return mID;
    }

    @JsonProperty("State")
    public String getState() {
        return mState;
    }

    @JsonProperty("Question")
    public String getQuestion() {
        return mQuestion;
    }

    @JsonProperty("Answers")
    public String[] getAnswers() {
        return mAnswers;
    }

    @JsonProperty("Hint")
    public String getHint() {
        return mHint;
    }

    @JsonProperty("Pic")
    public String getPic() {
        return mPic;
    }

}
