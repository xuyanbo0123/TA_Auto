package name.mi.qdb.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 6/30/13
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonPropertyOrder(value = {"User_ID", "QSet_ID", "Number", "User_Answer", "Is_Correct", "Elapsed_Time", "QDB_Question", "QDB_Answer","Is_Answered"})
public class QDBRecord {
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mUserID;

    private long
            mQSetID;

    private long
            mQuestionID;

    private int
            mUserAnswer;

    private boolean
            mIsCorrect;

    private long
            mElapsedTime;

    private Date
            mDisplayedTS;

    private QDBQuestion
            mQDBQuestion;

    private QDBAnswer
            mQDBAnswer;

    private int
            mNumber;

    public QDBRecord(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iUserID,
            long iQSetID,
            long iQuestionID,
            int iUserAnswer,
            boolean iIsCorrect,
            long iElapsedTime,
            Date iDisplayedTS,
            int iNumber
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mUserID = iUserID;
        mQSetID = iQSetID;
        mQuestionID = iQuestionID;
        mUserAnswer = iUserAnswer;
        mIsCorrect = iIsCorrect;
        mElapsedTime = iElapsedTime;
        mDisplayedTS = iDisplayedTS;
        mQDBQuestion = null;
        mQDBAnswer = null;
        mNumber = iNumber;
    }

//    @JsonProperty("Record_ID")
    @JsonIgnore
    public long getID() {
        return mID;
    }

    @JsonIgnore
    public Date getCreatedTS() {
        return mCreatedTS;
    }

//    @JsonProperty("Created_TS")
//    public String getCreatedTSString() {
//        return UtilityFunctions.dateToString(mCreatedTS);
//    }

    @JsonIgnore
    public Date getUpdatedTS() {
        return mUpdatedTS;
    }

//    @JsonProperty("Updated_TS")
//    public String getUpdatedTSString() {
//        return UtilityFunctions.dateToString(mUpdatedTS);
//    }

    @JsonProperty("User_ID")
    public long getUserID() {
        return mUserID;
    }

    @JsonProperty("QSet_ID")
    public long getQSetID() {
        return mQSetID;
    }

    @JsonProperty("Number")
    public int getNumber() {
        return mNumber;
    }
    @JsonIgnore
    public long getQuestionID() {
        return mQuestionID;
    }

    @JsonProperty("User_Answer")
    public int getUserAnswer() {
        return mUserAnswer;
    }

    @JsonProperty("Is_Correct")
    public boolean isCorrect() {
        return mIsCorrect;
    }

    @JsonProperty("Elapsed_Time")
    public long getElapsedTime() {
        return mElapsedTime;
    }

    @JsonIgnore
    public Date getDisplayedTS() {
        return mDisplayedTS;
    }

    @JsonIgnore
    public String getDisplayedTSString() {
        return UtilityFunctions.dateToString(mDisplayedTS);
    }

    @JsonProperty("QDBQuestion")
    public QDBQuestion getQDBQuestion() {
        return mQDBQuestion;
    }

    @JsonProperty("QDBAnswer")
    public QDBAnswer getQDBAnswer() {
        return mQDBAnswer;
    }

    @JsonProperty("Is_Answered")
    public boolean isAnswered()
    {
        return mUserAnswer>=0;
    }



    public void setNumber(int iNumber) {
        mNumber = iNumber;
    }

    public void setID(long iID) {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS) {
        mCreatedTS = iCreatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS) {
        mUpdatedTS = iUpdatedTS;
    }

    public void setUserID(long iUserID) {
        mUserID = iUserID;
    }

    public void setQSetID(long iQSetID) {
        mQSetID = iQSetID;
    }

    public void setQuestionID(long iQuestionID) {
        mQuestionID = iQuestionID;
    }

    public void setUserAnswer(int iUserAnswer) {
        mUserAnswer = iUserAnswer;
    }

    public void setIsCorrect(boolean iIsCorrect) {
        mIsCorrect = iIsCorrect;
    }

    public void setElapsedTime(long iElapsedTime) {
        mElapsedTime = iElapsedTime;
    }

    public void setDisplayedTS(Date iDisplayedTS) {
        mDisplayedTS = iDisplayedTS;
    }

    public void setQDBQuestion(QDBQuestion iQDBQuestion) {
        mQDBQuestion = iQDBQuestion;
    }

    public void setQDBAnswer(QDBAnswer iQDBAnswer) {
        mQDBAnswer = iQDBAnswer;
    }

}
