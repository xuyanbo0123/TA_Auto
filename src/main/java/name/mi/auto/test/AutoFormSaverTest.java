package name.mi.auto.test;

import name.mi.auto.model.AutoForm;
import name.mi.source.ftq.model.FTQAutoForm;
import org.codehaus.jackson.map.ObjectMapper;

public class AutoFormSaverTest {

    public static void main(String... iArgs) throws Exception
    {
        String vRawRequest = "{\"arrival_id\":21,\"site_name\":\"FetchTheQuote\",\"form_name\":\"default3\",\"leadid_token\":\"E7E95325-A8EA-0892-E792-384B2C77F988\",\"incidents\":[{\"driver_id\":\"26be7668-af2a-4c51-ac9f-911d3dd3704e\",\"incident_date\":\"11/1111\",\"incident_kind\":\"DUI\",\"incident_detail\":\"DUI Narcotics\"},{\"driver_id\":\"26be7668-af2a-4c51-ac9f-911d3dd3704e\",\"incident_date\":\"11/1111\",\"incident_kind\":\"Moving Violation\",\"incident_detail\":\"Driving on Wrong Side of Road\"},{\"driver_id\":\"342355e5-9b87-4b66-9b47-7b32a4d1816d\",\"incident_date\":\"23/3323\",\"incident_kind\":\"Other Violation\",\"incident_detail\":\"Driving w/out License in Possession\"}],\"vehicles\":[{\"year\":\"2011\",\"make\":\"CHRYSLER\",\"model\":\"200 LX\",\"style\":\"SEDAN 4 DOOR\",\"parking\":\"Garage\",\"ownership\":\"Own\",\"primaryUse\":\"commute\",\"estimatedAnnualMileage\":\"6000-10000\",\"driver\":[{\"name\":\"asedfaef adsfadsf\",\"primary\":true,\"driver_id\":\"26be7668-af2a-4c51-ac9f-911d3dd3704e\"}]},{\"year\":\"2013\",\"make\":\"DODGE\",\"model\":\"AVENGER R/T\",\"style\":\"SEDAN 4 DOOR\",\"parking\":\"Garage\",\"ownership\":\"Own\",\"primaryUse\":\"commute\",\"estimatedAnnualMileage\":\"6000-10000\",\"driver\":[{\"name\":\"qwer qwer\",\"primary\":true,\"driver_id\":\"342355e5-9b87-4b66-9b47-7b32a4d1816d\"}]}],\"drivers\":[{\"firstName\":\"asedfaef\",\"lastName\":\"adsfadsf\",\"birthDate\":\"11/11/1111\",\"gender\":\"Male\",\"driver_id\":\"26be7668-af2a-4c51-ac9f-911d3dd3704e\"},{\"firstName\":\"qwer\",\"lastName\":\"qwer\",\"birthDate\":\"11/11/1111\",\"gender\":\"Female\",\"driver_id\":\"342355e5-9b87-4b66-9b47-7b32a4d1816d\"}],\"driver_discounts\":[{\"driver_name\":\"asedfaef adsfadsf\",\"driver_id\":\"26be7668-af2a-4c51-ac9f-911d3dd3704e\",\"career_status\":\"Employed\",\"occupation\":\"Manager Supervisor\",\"marital_status\":\"married\",\"education_completed\":\"Some College\",\"license_status\":\"Valid\",\"credit_rating\":\"Good\",\"residence_length\":\"3 to 5 Years\",\"carrier_id\":\"Landmark American Insurance\",\"current_insured_duration\":\"2 years\",\"primary_applicant\":\"on\",\"age_first_licensed\":\"16\",\"homeowner\":\"off\",\"currently_insured\":\"on\",\"expiration_date\":\"11/1111\",\"currently_pay\":\"\"},{\"driver_name\":\"qwer qwer\",\"driver_id\":\"342355e5-9b87-4b66-9b47-7b32a4d1816d\",\"career_status\":\"Full-Time Student\",\"marital_status\":\"married\",\"education_completed\":\"Some College\",\"license_status\":\"Valid\",\"credit_rating\":\"Good\",\"relation_to_primary\":\"Other\",\"primary_applicant\":\"off\",\"good_student\":\"off\",\"age_first_licensed\":\"16\",\"same_address_as_primary\":\"on\"}],\"primary_driver_id\":\"26be7668-af2a-4c51-ac9f-911d3dd3704e\",\"street\":\"111 stname st\",\"apt\":\"444\",\"email\":\"asdfasdf@gmail.com\",\"phone\":\"(617) 494-9585\",\"city\":\"CAMBRIDGE\",\"state\":\"MA\",\"zip\":\"02139\",\"coverage_type\":\"Standard Coverage\",\"sr22\":\"No\"}";

        ObjectMapper mapper = new ObjectMapper();

        AutoForm vRawAutoForm = null;

        FTQAutoForm vFTQAutoForm = mapper.readValue(vRawRequest, FTQAutoForm.class);
        vRawAutoForm = vFTQAutoForm.toAutoForm();
        System.out.println(mapper.writeValueAsString(vRawAutoForm));


    }
}
