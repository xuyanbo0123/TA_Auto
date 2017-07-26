package name.mi.buyer.webjuice.derivative;

import name.mi.auto.enumerate.IncidentDescription;
import name.mi.buyer.webjuice.map.IncidentDescriptionMap;
import name.mi.buyer.webjuice.map.IncidentTypeMap;
import name.mi.buyer.util.DateFormat;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Incident;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebJuiceIncident
{
    public enum IncidentType
    {
        Ticket,
        Accident,
        Violation,
        Claim
    }

    private int mIndex;
    private Date mDate;
    private IncidentDescription mDescription;

    IncidentType mType = null;
    AutoForm mAutoForm = null;
    Incident mIncident = null;

    public WebJuiceIncident(int iIndex, Incident iIncident, AutoForm iAutoForm)
    {
        mIndex = iIndex;
        mIncident = iIncident;
        mDate = mIncident.getEstimatedDate();
        mDescription = mIncident.getDescription();
        mType = IncidentTypeMap.valueOf(mDescription);
        mAutoForm = iAutoForm;
    }

    public final String getType()
    {
        return mType.name();
    }

    public final String getDescription()
    {
        return IncidentDescriptionMap.valueOf(mDescription);
    }

    public final String getDay()
    {
        return ""+DateFormat.parseDay(mDate);
    }

    public final String getMonth()
    {
        return ""+DateFormat.parseMonth(mDate);
    }

    public final String getYear()
    {
        return ""+DateFormat.parseYear(mDate);
    }

    public final List<NameValuePair> toNameValuePairList(String iPrefix)
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        String vPrefix = iPrefix + "incident";

        //required
        vList.add(new BasicNameValuePair(vPrefix + "Type" + mIndex, getType()));
        vList.add(new BasicNameValuePair(vPrefix + "Description" + mIndex, getDescription()));
        vList.add(new BasicNameValuePair(vPrefix + "Day" + mIndex, getDay()));
        vList.add(new BasicNameValuePair(vPrefix + "Month" + mIndex, getMonth()));
        vList.add(new BasicNameValuePair(vPrefix + "Year" + mIndex, getYear()));

        switch (mType)
        {
            case Accident:
                WebJuiceAccident vAccident = new WebJuiceAccident(mIndex, mIncident);
                vList.addAll(vAccident.toNameValuePairList(iPrefix));
                break;
            case Ticket:
                break;
            case Violation:
                WebJuiceViolation vViolation = new WebJuiceViolation(mIndex, mAutoForm);
                vList.addAll(vViolation.toNameValuePairList(iPrefix));
                break;
            case Claim:
                WebJuiceClaim vClaim = new WebJuiceClaim(mIndex, mIncident);
                vList.addAll(vClaim.toNameValuePairList(iPrefix));
        }
        return vList;
    }
}
