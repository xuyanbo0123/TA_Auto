package name.mi.services;

/**
 * replay to http result
 */
public interface ReplyWithResult
{
    /**
     * @return reply status
     */
    public ReplyStatus getStatus();

    /**
     * @return reply message
     */
    public String getMessage();

    /**
     * @return reply comment
     */
    public String getComment();

    /**
     * @return the result
     */
    public Object getReplyResult();
}
