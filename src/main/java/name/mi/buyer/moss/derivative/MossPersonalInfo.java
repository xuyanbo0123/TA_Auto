package name.mi.buyer.moss.derivative;

import name.mi.auto.model.Driver;
import name.mi.buyer.moss.map.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {
        "maritalStatus",
        "relationshipToApplicant",
        "gender",
        "firstName",
        "lastName",
        "birthDate",
        "occupation",
        "militaryExperience",
        "education",
        "creditRating"}
)
public class MossPersonalInfo {

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

    Driver mDriver;

    public MossPersonalInfo() {
    }

    public MossPersonalInfo(Driver iDriver) {
        mDriver = iDriver;
    }

    @XmlAttribute(name = "MaritalStatus")
    public String getMaritalStatus() {
        return MaritalStatusMap.valueOf(mDriver.getMaritalStatus());
    }

    @XmlAttribute(name = "RelationshipToApplicant")
    public String getRelationshipToApplicant() {
        return RelationshipMap.valueOf(mDriver.getRelationship());
    }

    @XmlAttribute(name = "Gender")
    public String getGender() {
        return mDriver.getGender().getDisplayName();
    }

    @XmlElement(name = "FirstName")
    public String getFirstName() {
        return mDriver.getFirstName().getName();
    }

    @XmlElement(name = "LastName")
    public String getLastName() {
        return mDriver.getLastName().getName();
    }

    @XmlElement(name = "BirthDate")
    public String getBirthDate() {
        return sFormat.format(mDriver.getBirthday());
    }

    @XmlElement(name = "Occupation")
    public String getOccupation() {
        return OccupationMap.valueOf(mDriver.getOccupation());
    }

    @XmlElement(name = "MilitaryExperience")
    public String getMilitaryExperience() {
        return "No Military Experience";
    }

    // KS: the GoodStudentDiscount attribute is required. So changing Education to a separate class. 02/20/2014
    @XmlElement(name = "Education")
    public MossEducation getEducation() {
        /*return EducationMap.valueOf(mDriver.getEducation());*/
        return new MossEducation(mDriver);
    }

    // KS: the Bankruptcy attribute is required. So changing CreditRating to a separate class. 02/20/2014
    @XmlElement(name = "CreditRating")
    public MossCreditRating getCreditRating() {
        /*return CreditMap.valueOf(mDriver.getCredit());*/
        return new MossCreditRating(mDriver);
    }
}
