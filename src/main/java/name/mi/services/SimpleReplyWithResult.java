package name.mi.services;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * implementation of reply with result
 */
public class SimpleReplyWithResult
    implements ReplyWithResult
{
    @JsonIgnore
    private ReplyStatus
        mStatus;

    @JsonIgnore
    private String
        mMessage,
        mComment;

    @JsonIgnore
    private Object
        mResult;

    public static SimpleReplyWithResult createFailedReplyWithResult(String iMessage, String iComment)
    {
        return new SimpleReplyWithResult(
            ReplyStatus.Failed,
            iMessage,
            iComment,
            null
        );
    }

    public static SimpleReplyWithResult createFailedReplyWithResult(Exception iException, String iComment)
    {
        return new SimpleReplyWithResult(
            ReplyStatus.Failed,
            iException.getMessage(),
            iComment,
            null
        );
    }

    @JsonCreator
    public SimpleReplyWithResult(
        @JsonProperty("Reply_Status")  ReplyStatus iStatus,
        @JsonProperty("Reply_Message") String iMessage,
        @JsonProperty("Reply_Comment") String iComment,
        @JsonProperty("Reply_Result")  Object iResult
    )
    {
        mStatus  = iStatus;
        mMessage = iMessage;
        mComment = iComment;
        mResult  = iResult;
    }

    @JsonProperty("Reply_Status")
    public final ReplyStatus getStatus()
    {
        return mStatus;
    }

    @JsonProperty("Reply_Message")
    public final String getMessage()
    {
        return mMessage;
    }

    @JsonProperty("Reply_Comment")
    public final String getComment()
    {
        return mComment;
    }

    @JsonProperty("Reply_Result")
    public final Object getReplyResult()
    {
        return mResult;
    }

    @Override
    public String toString()
    {
        return "SimpleReplyWithResult{" +
            "mStatus=" + mStatus +
            ", mMessage='" + mMessage + '\'' +
            ", mComment='" + mComment + '\'' +
            ", mResult=" + mResult +
            '}';
    }
}
