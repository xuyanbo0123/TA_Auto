package name.mi.source.ftq.model;

import name.mi.auto.model.Incident;
import name.mi.source.ftq.map.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FTQIncident {

    private String mDriverId;
    private String mIncidentDate;
    private String mIncidentKind;
    private String mIncidentDetail;

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/yyyy");

    public FTQIncident() {
    }

    @JsonProperty("driver_id")
    public void setDriverId(String iDriverId)
    {
        mDriverId = iDriverId;
    }

    @JsonProperty("incident_date")
    public void setIncidentDate(String iIncidentDate)
    {
        mIncidentDate = iIncidentDate;
    }

    @JsonProperty("incident_kind")
    public void setIncidentKind(String iIncidentKind)
    {
        mIncidentKind = iIncidentKind;
    }

    @JsonProperty("incident_detail")
    public void setIncidentDetail(String iIncidentDetail)
    {
        mIncidentDetail = iIncidentDetail;
    }

    public Date getIncidentDate()
            throws Exception
    {
        return sDateFormat.parse(mIncidentDate);
    }

    public String getDriverId()
    {
        return mDriverId;
    }
    public Incident toIncident(long iDriverID)
            throws Exception
    {
        return new Incident(
                -1,
                new Date(),
                new Date(),
                -1,
                IncidentTypeMap.valueOf(mIncidentKind),
                iDriverID,
                getIncidentDate(),
                IncidentDescriptionMap.valueOf(mIncidentDetail),
                null, //Damage
                null, //Amount
                null, //AtFault
                null //State
        );
    }
}
