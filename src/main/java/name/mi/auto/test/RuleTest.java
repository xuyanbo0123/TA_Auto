package name.mi.auto.test;

import name.mi.auto.basic.*;
import name.mi.auto.enumerate.*;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.rule.Evaluator;
import name.mi.auto.rule.RuleJsonNode;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Date;

public class RuleTest {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(RuleTest.class);
    public static void main(String... a) {
//        try {
//            String mJSON = "{\"children\": [\"State==TX\",\"City==DALLAS|HOUSTON\"]}";

//            AutoForm vAutoForm = new AutoForm(
//                    1,
//                    new Date(),
//                    new Date(),
//                    1,
//                    1,
//                    Coverage._BASIC_COVERAGE,
//                    true,
//                    Carrier._AAA_AUTO_CLUB,
//                    new Date(),
//                    YearsInsured._2_3_YEARS,
//                    Liability._20K_50K,
//                    new ZipCode("02139"),
//                    "330 mass ave",
//                    "HOUSTON",
//                    "TX",
//                    new Email("SPLABC@test.com"),
//                    new Phone("61744588958"),
//                    YearsLived._3_4_YEARS
//            );
//            Driver vDriver = new Driver(
//                    1,
//                    new Date(),
//                    new Date(),
//                    1,
//                    Relationship._CHILD,
//                    NameOfPerson.parseNameOfPerson("Test"),
//                    NameOfPerson.parseNameOfPerson("Test"),
//                    new Date(),
//                    Gender._MALE,
//                    Marital._MARRIED,
//                    Occupation._BUSINESS_OWNER,
//                    Education._SOME_COLLEGE,
//                    Residence._OWN,
//                    Credit._EXCELLENT,
//                    AgeLicenced.parseAgeLicenced(18),
//                    false,
//                    false
//            );
//            Evaluator vEvaluator = new Evaluator(vAutoForm, vDriver);
//            JsonNode vJsonNode = new ObjectMapper().readTree(mJSON);
//            if (vEvaluator.evaluate(new RuleJsonNode(vJsonNode)))
//                System.out.println("PASSED");
//            else
//                System.out.println("FAILED");
//
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }

    }
}
