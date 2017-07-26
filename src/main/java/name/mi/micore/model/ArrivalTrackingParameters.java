package name.mi.micore.model;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 5/19/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrivalTrackingParameters {

        private long mID;
        private String mUrlName;
        private String mDBName;
        private String mComment;

    /**
     *
     * @param iID
     * @param iUrlName
     * @param iDBName
     * @param iComment
     */
        public ArrivalTrackingParameters(
                long iID,
                String iUrlName,
                String iDBName,
                String iComment
        ) {
            mID = iID;
            mUrlName = iUrlName;
            mDBName = iDBName;
            mComment = iComment;
        }

        public final long getID() {
            return mID;
        }

        public final String getUrlName() {
            return mUrlName;
        }

        public final String getDBName() {
            return mDBName;
        }

        public final String getComment() {
            return mComment;
        }

        @Override
        public String toString() {
            return "ArrivalTrackingParameters{" +
                    "mID=" + mID +
                    ", mUrlName='" + mUrlName + "'" +
                    ", mDBName='" + mDBName + "'" +
                    ", mComment='" + mComment + "'" +
                    "}";
        }
    }
