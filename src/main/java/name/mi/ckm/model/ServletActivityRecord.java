package name.mi.ckm.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 7/15/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServletActivityRecord {

    private long mID;
    private Date mCreatedTS;
    private String mServletName;
    private String mOperator;
    private String mTask;              
    private boolean mIsSucceed;
    private String mComment;

    public ServletActivityRecord(long iID, Date iCreatedTS, String iServletName, String iOperator, String iTask, boolean iIsSucceed, String iComment)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mServletName = iServletName;
        mOperator = iOperator;
        mTask = iTask;
        mIsSucceed = iIsSucceed;
        mComment = iComment;
    }

    public final long getID()
    {
        return mID;
    }

    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public final String getServletName()
    {
        return mServletName;
    }

    public final String getOperator()
    {
        return mOperator;
    }

    public final String getTask()
    {
        return mTask;
    }

    public final boolean getIsSucceed()
    {
        return mIsSucceed;
    }

    public final String getComment()
    {
        return mComment;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public void setServletName(String iServletName)
    {
        mServletName = iServletName;
    }

    public void setOperator(String iOperator)
    {
        mOperator = iOperator;
    }

    public void setTask(String iTask)
    {
        mTask = iTask;
    }

    public void setIsSucceed(boolean iIsSucceed)
    {
        mIsSucceed = iIsSucceed;
    }

    public void setComment(String iComment)
    {
        mComment = iComment;
    }

    @Override
    public String toString()
    {
        return "ServletActivityRecord{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mServletName='" + mServletName + '\'' +
                ", mOperator='" + mOperator + '\'' +
                ", mTask='" + mTask + '\'' +
                ", mIsSucceed=" + mIsSucceed +
                ", mComment='" + mComment + '\'' +
                '}';
    }
}
