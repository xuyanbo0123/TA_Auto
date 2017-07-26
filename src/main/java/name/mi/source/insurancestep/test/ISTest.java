package name.mi.source.insurancestep.test;

import name.mi.auto.model.AutoForm;
import name.mi.source.insurancestep.model.ISAutoForm;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.map.ObjectMapper;

public class ISTest {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(ISTest.class);

    public static void main(String... a) throws Exception {
        String vRawRequest = "{\n" +
                "  \"vehicles\": [\n" +
                "    {\n" +
                "      \"car-year-select\": \"2011\",\n" +
                "      \"car-make-select\": \"DODGE\",\n" +
                "      \"car-model-select\": \"CALIBER SE\",\n" +
                "      \"car-series-select\": \"SEDAN 4 DOOR\",\n" +
                "      \"car-own-select\": \"true\",\n" +
                "      \"car-park-select\": \"GARAGE\",\n" +
                "      \"car-use-select\": \"COMMUTE_WORK\",\n" +
                "      \"car-mileage-select\": \"12500\",\n" +
                "      \"vehicle-id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"car-year-select\": \"2009\",\n" +
                "      \"car-make-select\": \"FORD\",\n" +
                "      \"car-model-select\": \"F150\",\n" +
                "      \"car-series-select\": \"PICKUP\",\n" +
                "      \"car-own-select\": \"false\",\n" +
                "      \"car-park-select\": \"CAR_PORT\",\n" +
                "      \"car-use-select\": \"COMMUTE_WORK\",\n" +
                "      \"car-mileage-select\": \"12500\",\n" +
                "      \"vehicle-id\": 2\n" +
                "    }\n" +
                "  ],\n" +
                "  \"drivers\": [\n" +
                "    {\n" +
                "      \"first-name-input\": \"Xavier\",\n" +
                "      \"last-name-input\": \"Ke\",\n" +
                "      \"gender-select\": \"MALE\",\n" +
                "      \"driver-birth-month-select\": \"03\",\n" +
                "      \"driver-birth-day-select\": \"4\",\n" +
                "      \"driver-birth-year-select\": \"1994\",\n" +
                "      \"license-date-select\": \"16\",\n" +
                "      \"marital-status-select\": \"MARRIED\",\n" +
                "      \"education-select\": \"HIGH_SCHOOL_DIPLOMA\",\n" +
                "      \"owns-home-select\": \"true\",\n" +
                "      \"occupation-select\": \"STUDENT_NOT_LIVING_W_PARENTS\",\n" +
                "      \"relationship-select\": \"SELF\",\n" +
                "      \"gpa-select\": \"true\",\n" +
                "      \"credit-select\": \"GOOD\",\n" +
                "      \"license-status-select\": \"ACTIVE\",\n" +
                "      \"primary-vehicle-select\": \"1\",\n" +
                "      \"incident-select\": \"true\",\n" +
                "      \"accident-count-input\": \"2\",\n" +
                "      \"license-revoked-select\": \"false\",\n" +
                "      \"incident-month-select\": \"03\",\n" +
                "      \"incident-day-select\": \"15\",\n" +
                "      \"incident-year-select\": \"2012\",\n" +
                "      \"incident-type-select\": \"4\",\n" +
                "      \"accident-type-select\": \"HIT_AND_RUN\",\n" +
                "      \"amount-select\": \"500\",\n" +
                "      \"at-fault-select\": \"false\",\n" +
                "      \"dui-state-select\": \"AR\",\n" +
                "      \"sr-select\": \"true\",\n" +
                "      \"violation-type-select\": \"CHILD_SEAT\",\n" +
                "      \"claim-type-select\": \"DEFECTIVE_EQUIPMENT\",\n" +
                "      \"claim-amount-select\": \"500\",\n" +
                "      \"driver-id\": 1,\n" +
                "      \"incidents\": [\n" +
                "        {\n" +
                "          \"incident-month-select\": \"03\",\n" +
                "          \"incident-day-select\": \"6\",\n" +
                "          \"incident-year-select\": \"2011\",\n" +
                "          \"incident-type-select\": \"1\",\n" +
                "          \"accident-type-select\": \"OTHER_ACCIDENT\",\n" +
                "          \"amount-select\": \"2500\",\n" +
                "          \"at-fault-select\": \"true\",\n" +
                "          \"dui-state-select\": \"AL\",\n" +
                "          \"sr-select\": \"false\",\n" +
                "          \"violation-type-select\": \"CHILD_SEAT\",\n" +
                "          \"claim-type-select\": \"DEFECTIVE_EQUIPMENT\",\n" +
                "          \"claim-amount-select\": \"500\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"incident-month-select\": \"03\",\n" +
                "          \"incident-day-select\": \"15\",\n" +
                "          \"incident-year-select\": \"2012\",\n" +
                "          \"incident-type-select\": \"4\",\n" +
                "          \"accident-type-select\": \"HIT_AND_RUN\",\n" +
                "          \"amount-select\": \"500\",\n" +
                "          \"at-fault-select\": \"false\",\n" +
                "          \"dui-state-select\": \"AR\",\n" +
                "          \"sr-select\": \"true\",\n" +
                "          \"violation-type-select\": \"CHILD_SEAT\",\n" +
                "          \"claim-type-select\": \"DEFECTIVE_EQUIPMENT\",\n" +
                "          \"claim-amount-select\": \"500\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"first-name-input\": \"Test\",\n" +
                "      \"last-name-input\": \"Ke2\",\n" +
                "      \"gender-select\": \"MALE\",\n" +
                "      \"driver-birth-month-select\": \"03\",\n" +
                "      \"driver-birth-day-select\": \"16\",\n" +
                "      \"driver-birth-year-select\": \"1984\",\n" +
                "      \"license-date-select\": \"16\",\n" +
                "      \"marital-status-select\": \"MARRIED\",\n" +
                "      \"education-select\": \"BACHELORS_DEGREE\",\n" +
                "      \"owns-home-select\": \"true\",\n" +
                "      \"occupation-select\": \"TRUCK_DRIVER\",\n" +
                "      \"relationship-select\": \"SPOUSE\",\n" +
                "      \"gpa-select\": \"true\",\n" +
                "      \"license-status-select\": \"ACTIVE\",\n" +
                "      \"primary-vehicle-select\": \"1\",\n" +
                "      \"incident-select\": \"true\",\n" +
                "      \"accident-count-input\": \"2\",\n" +
                "      \"license-revoked-select\": \"true\",\n" +
                "      \"incident-month-select\": \"05\",\n" +
                "      \"incident-day-select\": \"8\",\n" +
                "      \"incident-year-select\": \"2012\",\n" +
                "      \"incident-type-select\": \"3\",\n" +
                "      \"accident-type-select\": \"HIT_AND_RUN\",\n" +
                "      \"amount-select\": \"500\",\n" +
                "      \"at-fault-select\": \"false\",\n" +
                "      \"dui-state-select\": \"AL\",\n" +
                "      \"sr-select\": \"false\",\n" +
                "      \"violation-type-select\": \"CHILD_SEAT\",\n" +
                "      \"claim-type-select\": \"VANDALISM\",\n" +
                "      \"claim-amount-select\": \"8000\",\n" +
                "      \"driver-id\": 2,\n" +
                "      \"incidents\": [\n" +
                "        {\n" +
                "          \"incident-month-select\": \"02\",\n" +
                "          \"incident-day-select\": \"2\",\n" +
                "          \"incident-year-select\": \"2011\",\n" +
                "          \"incident-type-select\": \"2\",\n" +
                "          \"accident-type-select\": \"HIT_AND_RUN\",\n" +
                "          \"amount-select\": \"500\",\n" +
                "          \"at-fault-select\": \"false\",\n" +
                "          \"dui-state-select\": \"AL\",\n" +
                "          \"sr-select\": \"false\",\n" +
                "          \"violation-type-select\": \"FAILURE_TO_STOP\",\n" +
                "          \"claim-type-select\": \"DEFECTIVE_EQUIPMENT\",\n" +
                "          \"claim-amount-select\": \"500\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"incident-month-select\": \"05\",\n" +
                "          \"incident-day-select\": \"8\",\n" +
                "          \"incident-year-select\": \"2012\",\n" +
                "          \"incident-type-select\": \"3\",\n" +
                "          \"accident-type-select\": \"HIT_AND_RUN\",\n" +
                "          \"amount-select\": \"500\",\n" +
                "          \"at-fault-select\": \"false\",\n" +
                "          \"dui-state-select\": \"AL\",\n" +
                "          \"sr-select\": \"false\",\n" +
                "          \"violation-type-select\": \"CHILD_SEAT\",\n" +
                "          \"claim-type-select\": \"VANDALISM\",\n" +
                "          \"claim-amount-select\": \"8000\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"requestedCoverage\": {\n" +
                "    \"coverage-type-select\": \"STANDARD\",\n" +
                "    \"collision-amount-select\": \"50000\",\n" +
                "    \"insured-select\": \"true\",\n" +
                "    \"current-insurer-select\": \"The Hartford\",\n" +
                "    \"months-with-company-select\": \"60\",\n" +
                "    \"expiration-month-select\": \"5\",\n" +
                "    \"expiration-day-select\": \"5\",\n" +
                "    \"expiration-year-select\": \"2015\"\n" +
                "  },\n" +
                "  \"contact\": {\n" +
                "    \"phone-input\": \"(617) 849-5840\",\n" +
                "    \"email-input\": \"abc@test.com\",\n" +
                "    \"zip-input\": \"02139\",\n" +
                "    \"address-input\": \"123 main st.\"\n" +
                "  }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        ISAutoForm vISAutoForm = mapper.readValue(vRawRequest, ISAutoForm.class);
        //AutoForm vRawAutoForm = vFQAutoForm.toAutoForm();
        //System.out.println(mapper.writeValueAsString(vRawAutoForm));
    }
}
