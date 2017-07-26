package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Incident;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {"year", "month", "state"})
public class MossDUI {

    private static SimpleDateFormat sFormatYear = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat sFormatMonth = new SimpleDateFormat("MM");

    private Incident mIncident;
    private AutoForm mAutoForm;

    public MossDUI() {
        mIncident = null;
        mAutoForm = null;
    }

    public MossDUI(Incident iIncident, AutoForm iAutoForm)
    {
        mIncident = iIncident;
        mAutoForm = iAutoForm;
    }

    @XmlAttribute(name = "Year")
    public String getYear()
    {
        if(mIncident != null)
        {
            return sFormatYear.format(mIncident.getEstimatedDate());
        }
        else
        {
            return "";
        }
    }

    @XmlAttribute(name = "Month")
    public String getMonth()
    {
        if(mIncident != null)
        {
            return sFormatMonth.format(mIncident.getEstimatedDate());
        }
        else
        {
            return "";
        }
    }

    @XmlElement(name = "State")
    public String getState()
    {
        if(mIncident != null)
        {
            if(mIncident.getHappenedState() == null)
            {
                return mAutoForm.getState();
            }
            else
            {
                return mIncident.getHappenedState().getValueName();
            }
        }
        else
        {
            return "";
        }
    }
}
