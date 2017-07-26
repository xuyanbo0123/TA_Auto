package name.mi.qdb.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 6/30/13
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonPropertyOrder(value = {"User_ID", "QSet_ID", "Answered", "Count", "QSet_Type","QSet_Num", "State", "Elapsed_Time", "Records", "Record_Num", "Is_Completed"})
public class QDBQSet {
    public enum QSetType {
        fixed,random
    };

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mUserID;

    private int
            mCount;

    private String
            mState;

    private long
            mElapsedTime;

    private QSetType
            mQSetType;

    private int
            mQSetNum;

    private List<QDBRecord>
            mRecords;

    private QDBRecord
            mCurrentRecord;

    private int
            mRecordNum;

    private int
            mAnswered;

    public static QSetType parseQSetTypeFromString(String iValue)
    {
        QSetType vQSetType;
        try
        {
            vQSetType = QSetType.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vQSetType = null;
        }
        return vQSetType;
    }
    
    public QDBQSet(long iID, Date iCreatedTS, Date iUpdatedTS, long iUserID, int iCount, String iState, long iElapsedTime, QSetType iQSetType, int iQSetNum, int iRecordNum, int iAnswered) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mUserID = iUserID;
        mCount = iCount;
        mState = iState;
        mElapsedTime = iElapsedTime;
        mQSetType = iQSetType;
        mQSetNum = iQSetNum;
        mRecordNum = iRecordNum;
        mAnswered = iAnswered;
    }

    @JsonProperty("QSet_ID")
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

    @JsonProperty("Count")
    public int getCount() {
        return mCount;
    }

    @JsonProperty("State")
    public String getState() {
        return mState;
    }

    @JsonProperty("Elapsed_Time")
    public long getElapsedTime() {
        return mElapsedTime;
    }

    @JsonProperty("QSet_Type")
    public QSetType getQSetType() {
        return mQSetType;
    }

    @JsonProperty("QSet_Num")
    public int getQSetNum() {
        return mQSetNum;
    }

    @JsonProperty("Records")
    public List<QDBRecord> getRecords()
    {
        return mRecords;
    }

    @JsonProperty("Record_Num")
    public int getRecordNum()
    {
        return mRecordNum;
    }

    public void setRecordNum(int iRecordNum) {
        mRecordNum = iRecordNum;
    }

    public int getAnswered() {
        return mAnswered;
    }

    @JsonProperty("Is_Completed")
    public boolean isCompleted()
    {
        return mAnswered >= mCount;
    }

    @JsonProperty("Current_Record")
    public QDBRecord getCurrentRecord()
    {
        return mCurrentRecord;
    }

    public void setCurrentRecord(QDBRecord iCurrentRecord) {
        mCurrentRecord = iCurrentRecord;
    }

    public void setAnswered(int iAnswered) {
        mAnswered = iAnswered;
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

    public void setCount(int iCount) {
        mCount = iCount;
    }

    public void setState(String iState) {
        mState = iState;
    }

    public void setElapsedTime(long iElapsedTime) {
        mElapsedTime = iElapsedTime;
    }

    public void setQSetType(QSetType iQSetType) {
        mQSetType = iQSetType;
    }

    public void setQSetNum(int iQSetNum) {
        mQSetNum = iQSetNum;
    }

    public void setRecords(List<QDBRecord> iRecords) {
        mRecords = iRecords;
    }
}
