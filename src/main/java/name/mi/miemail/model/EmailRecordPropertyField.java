package name.mi.miemail.model;

public class EmailRecordPropertyField {
    
    private long mID;
    private String mEmailRecordPropertyField;

    /**
     *
     * @param iID
     * @param iEmailRecordPropertyField
     */
    public EmailRecordPropertyField(
            long iID,
            String iEmailRecordPropertyField
    ) {
        mID = iID;
        mEmailRecordPropertyField = iEmailRecordPropertyField;
    }

    /**
     * @return ID of the EmailRecord
     */
    public final long getID() {
        return mID;
    }

    /**
     * @return EmailRecordPropertyField
     */
    public final String getEmailRecordPropertyField() {
        return mEmailRecordPropertyField;
    }

    @Override
    public String toString() {
        return "EmailRecord{" +
                "mID=" + mID +
                ", mEmailRecordPropertyField='" + mEmailRecordPropertyField + "'" +
                "}";
    }
}
