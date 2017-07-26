package name.mi.buyer.webjuice.derivative;

import name.mi.buyer.webjuice.map.*;
import name.mi.buyer.util.DateFormat;
import name.mi.buyer.webjuice.util.WordFilter;
import name.mi.auto.model.AutoForm;

import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

public class WebJuiceDriver {
    private Driver mDriver;
    private AutoForm mAutoForm;
    private int mIndex;
    private List<Incident> mIncidents;

    public WebJuiceDriver(int iIndex, Driver iDriver, AutoForm iAutoForm)
    {
        mIndex = iIndex;
        mDriver = iDriver;
        mAutoForm = iAutoForm;
    }

    public WebJuiceDriver(
            int iIndex,
            Driver iDriver,
            AutoForm iAutoForm,
            List<Incident> iIncidents)
    {
        mIndex = iIndex;
        mDriver = iDriver;
        mAutoForm = iAutoForm;
        mIncidents = iIncidents;
    }

    //Required, Have, Convert
    public final String getRelationshipToApplicant()
    {
        if (mIndex == 1)
            return "Self";
        return RelationshipMap.valueOf(mDriver.getRelationship());
    }

    public final String getFirstName()
    {
        return WordFilter.trimName(mDriver.getFirstName().getName());
    }

    public final String getLastName()
    {
        return WordFilter.trimName(mDriver.getLastName().getName());
    }

    public final String getGender()
    {
        return GenderMap.valueOf(mDriver.getGender());
    }

    public final String getDOBYear()
    {
        Date vNow = new Date();
        int vYearDob = DateFormat.parseYear(mDriver.getBirthday());
        int vYearNow = DateFormat.parseYear(vNow);

        if (vYearNow - vYearDob > 74)
            return "" + (vYearNow - 74);

        if (vYearNow - vYearDob < 16)
            return "" + (vYearNow - 16);

        return "" + vYearDob;
    }

    public final String getDOBMonth()
    {
        return DateFormat.parse_MM(mDriver.getBirthday());
    }

    public final String getDOBDay()
    {
        return DateFormat.parse_dd(mDriver.getBirthday());
    }

    public final String getMarital()
    {
        return MaritalMap.valueOf(mDriver.getMaritalStatus());
    }

    public final String getEducation()
    {
        return EducationMap.valueOf(mDriver.getEducation());
    }

    public final String getOccupation()
    {
        return OccupationMap.valueOf(mDriver.getOccupation());
    }

    public final String getYearsInField()
    {
        return "1";
    }

    public final String getUSResident()
    {
        return "true";
    }

    public final String getMatureCourse()
    {
        return "false";
    }

    public final String getYearsAtResidence()
    {
        return YearsLivedMap.valueOf(mAutoForm.getYearsLived());
    }

    public final String getMonthsAtResidence()
    {
        return Integer.toString((int) (Math.random() * 12));
    }

    public final String getLicenseStatus()
    {
        return "Active";
    }

    public final String getLicenseState()
    {
        return mAutoForm.getState();
    }

    public final String getAgeLicensed()
    {
        Integer vAge = mDriver.getAgeLic().getAge();
        if (vAge != null && vAge < 76)
        {
            return Long.toString(vAge);
        }
        return "16";
    }

    public final String getLicensedSuspendedRevoked()
    {
        return Boolean.toString(!mDriver.isLicActive());
    }

    public final String getRequiredSR22()
    {
        return Boolean.toString(mDriver.getIsSR22Required());
    }

    public final String getGPA()
    {
        return "false";
    }

    public final String getBehindWheelTraining()
    {
        return "false";
    }

    public boolean isTicketsAccidentsClaims()
    {
        if (mIndex == 2)
            //return false;
            return getNumOfIncidentsNumber() > 4;
        return getNumOfIncidentsNumber() > 0;
    }

    public final int getNumOfIncidentsNumber()
    {
        return mIncidents.size();
    }

    public final String getIsTicketsAccidentsClaims()
    {
        return Boolean.toString(isTicketsAccidentsClaims());
    }

    public final String getNumOfIncidents()
    {
        if (isTicketsAccidentsClaims())
            return Integer.toString(getNumOfIncidentsNumber());
        else
            return null;
    }


    public final String getPrefix()
    {
        return "driver" + mIndex + "_";
    }

    private final WebJuiceIncident getIncident(int iListIndex)
    {
        int vIncidentIndex = iListIndex > 3 ? iListIndex - 3 : iListIndex + 1;
        return new WebJuiceIncident(vIncidentIndex, mIncidents.get(iListIndex), mAutoForm);
    }

    public final List<NameValuePair> toNameValuePairList()
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        String vPrefix = getPrefix();

        //required
        vList.add(new BasicNameValuePair(vPrefix + "relationshipToApplicant", getRelationshipToApplicant()));
        if (mIndex > 1)
        {
            vList.add(new BasicNameValuePair(vPrefix + "firstName", getFirstName()));
            vList.add(new BasicNameValuePair(vPrefix + "lastName", getLastName()));
        }
        vList.add(new BasicNameValuePair(vPrefix + "gender", getGender()));
        vList.add(new BasicNameValuePair(vPrefix + "dobYear", getDOBYear()));
        vList.add(new BasicNameValuePair(vPrefix + "dobMonth", getDOBMonth()));
        vList.add(new BasicNameValuePair(vPrefix + "dobDay", getDOBDay()));
        vList.add(new BasicNameValuePair(vPrefix + "maritalStatus", getMarital()));
        vList.add(new BasicNameValuePair(vPrefix + "education", getEducation()));
        vList.add(new BasicNameValuePair(vPrefix + "occupation", getOccupation()));
        vList.add(new BasicNameValuePair(vPrefix + "yearsInField", getYearsInField()));
        vList.add(new BasicNameValuePair(vPrefix + "USResident", getUSResident()));
        vList.add(new BasicNameValuePair(vPrefix + "matureCourse", getMatureCourse()));
        vList.add(new BasicNameValuePair(vPrefix + "yearsAtResidence", getYearsAtResidence()));
        vList.add(new BasicNameValuePair(vPrefix + "monthsAtResidence", getMonthsAtResidence()));
        vList.add(new BasicNameValuePair(vPrefix + "licenseStatus", getLicenseStatus()));
        vList.add(new BasicNameValuePair(vPrefix + "licenseState", getLicenseState()));
        vList.add(new BasicNameValuePair(vPrefix + "ageLicensed", getAgeLicensed()));
        vList.add(new BasicNameValuePair(vPrefix + "licensedSuspendedRevoked", getLicensedSuspendedRevoked()));
        vList.add(new BasicNameValuePair(vPrefix + "requiredSR22", getRequiredSR22()));
        vList.add(new BasicNameValuePair(vPrefix + "GPA", getGPA()));
        vList.add(new BasicNameValuePair(vPrefix + "behindWheelTraining", getBehindWheelTraining()));
        vList.add(new BasicNameValuePair(vPrefix + "isTicketsAccidentsClaims", getIsTicketsAccidentsClaims()));
        //
        if (!isTicketsAccidentsClaims())
            return vList;

        //Incidents
        if (mIndex == 1)
        {
            int vCount = getNumOfIncidentsNumber() > 4 ? 4 : getNumOfIncidentsNumber();
            vList.add(new BasicNameValuePair(vPrefix + "numOfIncidents", Integer.toString(vCount)));
            for (int i = 0; i < vCount; i++)
            {
                vList.addAll(getIncident(i).toNameValuePairList(vPrefix));
            }
        }
        if (mIndex == 2)
        {
            vList.add(new BasicNameValuePair(vPrefix + "numOfIncidents", Integer.toString(getNumOfIncidentsNumber() - 4)));
            for (int i = 4; i < getNumOfIncidentsNumber(); i++)
            {
                vList.addAll(getIncident(i).toNameValuePairList(vPrefix));
            }
        }

        //
        return vList;
    }
}
