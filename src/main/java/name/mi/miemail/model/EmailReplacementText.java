package name.mi.miemail.model;

public class EmailReplacementText {

    private long mID;
    private String mParameterName;     
    private String mReplacementText;

    public EmailReplacementText(
            long iID,
            String iParameterName,
            String iReplacementText
    ) {
        mID = iID;
        mParameterName = iParameterName;
        mReplacementText = iReplacementText;
    }

    public final long getID() {
        return mID;
    }

    public final String getParameterName() {
        return mParameterName;
    }

    public final String getReplacementText() {
        return mReplacementText;
    }

    @Override
    public String toString() {
        return "EmailRecord{" +
                "mID=" + mID +
                ", mParameterName='" + mParameterName + "'" +
                ", mReplacementText='" + mReplacementText + "'" +
                "}";
    }
}

