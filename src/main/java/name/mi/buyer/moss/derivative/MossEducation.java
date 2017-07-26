package name.mi.buyer.moss.derivative;

import name.mi.auto.enumerate.Education;
import name.mi.auto.model.Driver;
import name.mi.buyer.moss.map.EducationMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

public class MossEducation
{
    Driver mDriver;

    public MossEducation()
    {

    }

    public MossEducation(Driver iDriver)
    {
        mDriver = iDriver;
    }

    @XmlAttribute(name = "GoodStudentDiscount")
    public String getGoodStudentDiscount()
    {
        return "No";
    }

    @XmlValue
    public String getValue()
    {
        if(mDriver != null)
        {
            return EducationMap.valueOf(mDriver.getEducation());
        }
        else
        {
            return EducationMap.valueOf(Education._SOME_COLLEGE);
        }
    }
}
