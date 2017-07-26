package name.mi.micore.model;

import name.mi.auto.model.AutoForm;
import name.mi.source.freequotes.model.FQAutoForm;
import name.mi.source.insurancestep.model.ISAutoForm;
import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Date;

public class LeadRequest{

    public enum RequestStatus
    {
        received, saved, resaved, processing, processed
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mRawRequest;
    private RequestStatus mRequestStatus;
    private long mArrivalID;
    private long mLeadTypeID;
    private String mToken;
    private String mLeadID;

    public LeadRequest(long iID, Date iCreatedTS, Date iUpdatedTS, String iRawRequest, RequestStatus iRequestStatus, long iArrivalID, long iLeadTypeID, String iToken, String iLeadID)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mRawRequest = iRawRequest;
        mRequestStatus = iRequestStatus;
        mArrivalID = iArrivalID;
        mLeadTypeID = iLeadTypeID;
        mToken = iToken;
        mLeadID = iLeadID;
    }

    public final long getID() {
        return mID;
    }

    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    public final String getRawRequest() {
        return mRawRequest;
    }

    public final RequestStatus getRequestStatus() {
        return mRequestStatus;
    }

    public final long getArrivalID() {
        return mArrivalID;
    }

    public final long getLeadTypeID() {
        return mLeadTypeID;
    }

    public final String getToken()
    {
        return mToken;
    }

    public final String getLeadID()
    {
        return mLeadID;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS)
    {
        mUpdatedTS = iUpdatedTS;
    }

    public void setRawRequest(String iRawRequest)
    {
        mRawRequest = iRawRequest;
    }

    public void setRequestStatus(RequestStatus iRequestStatus)
    {
        mRequestStatus = iRequestStatus;
    }

    public void setArrivalID(long iArrivalID)
    {
        mArrivalID = iArrivalID;
    }

    public void setLeadTypeID(long iLeadTypeID)
    {
        mLeadTypeID = iLeadTypeID;
    }

    public void setToken(String iToken)
    {
        mToken = iToken;
    }

    public void setLeadID(String iLeadID)
    {
        mLeadID = iLeadID;
    }

    public AutoForm getRawAutoForm()
            throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode vDataNode = new ObjectMapper().readTree(mRawRequest);
        String vFormName = UtilityFunctions.getValueByNameFromJsonNode(vDataNode, "form_name");

        // FQ Form
        if (vFormName.equals("default"))
        {
            FQAutoForm vFQAutoForm = mapper.readValue(mRawRequest, FQAutoForm.class);
            return vFQAutoForm.toAutoForm();
        }
        else if (vFormName.equals("default2"))
        {
            ISAutoForm vISAutoForm = mapper.readValue(mRawRequest, ISAutoForm.class);
            return vISAutoForm.toAutoForm();
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "LeadRequest{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mRawRequest='" + mRawRequest + '\'' +
                ", mRequestStatus=" + mRequestStatus +
                ", mArrivalID=" + mArrivalID +
                ", mLeadTypeID=" + mLeadTypeID +
                ", mToken='" + mToken + '\'' +
                ", mLeadID='" + mLeadID + '\'' +
                '}';
    }

    public String toCSV()
    {
        return  mID +
                ", \"" + mToken + '"' +
                ", \"" + mLeadID + '"'
                ;
    }
}
