package name.mi.ckm;

public class CKMException extends Exception {
    public CKMException()
    {
        super();
    }

    public CKMException(String message)
    {
        super(message);
    }

    public CKMException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
