package name.mi.auto.test;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Iterator;

public class JsonNodeTest {

    public static void main(String... a) {
        try {
            String vJsonString = "{\n" +
                    "    \"prefix\": \"NOT\",\n" +
                    "    \"operator\": \"AND\",\n" +
                    "    \"children\": [\n" +
                    "        \"state==MA\",\n" +
                    "        {\n" +
                    "            \"prefix\": \"NOT\",\n" +
                    "            \"operator\": \"AND\",\n" +
                    "            \"children\": [\n" +
                    "                \"state==MA\",\n" +
                    "                \"Marital!=_MARRIED\",\n" +
                    "                \"education==_BACHELORS_DEGREE|_MASTERS_DEGREE|_DOCTORATE_DEGREE\"\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"education==_BACHELORS_DEGREE|_MASTERS_DEGREE|_DOCTORATE_DEGREE\"\n" +
                    "    ]\n" +
                    "}";
            JsonNode vNode = new ObjectMapper().readTree(vJsonString);
            JsonNode vChildren = vNode.path("children");
            Iterator<JsonNode> vIterator = vChildren.getElements();

            while (vIterator.hasNext())
            {
                JsonNode vJsonNode = vIterator.next();
                if (vJsonNode.isValueNode())
                {
                    String vExpression = vJsonNode.getTextValue();
                    System.out.println(vExpression);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}