package name.mi.auto.rule;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuleJsonNode {

    public enum Operator {
        AND, OR
    }

    private JsonNode mJsonNode;
    private boolean mPrefix = true;
    private Operator mOperator = null;
    private List<RuleJsonNode> mChildren = new ArrayList<RuleJsonNode>();

    public JsonNode getJsonNode()
    {
        return mJsonNode;
    }

    public List<RuleJsonNode> getChildren()
    {
        return mChildren;
    }

    public Operator getOperator()
    {
        return mOperator;
    }

    public boolean isObject()
    {
        return mJsonNode.isObject();
    }

    public boolean isValueNode()
    {
        return mJsonNode.isValueNode();
    }

    public boolean isPrefix()
    {
        return mPrefix;
    }

    public RuleJsonNode(String iJsonNodeStr)
            throws Exception
    {
        mJsonNode = new ObjectMapper().readTree(iJsonNodeStr);
        if (mJsonNode.isObject())
            parseTree();
    }

    public RuleJsonNode(JsonNode iJsonNode)
            throws Exception
    {
        mJsonNode = iJsonNode;
        if (iJsonNode.isObject())
            parseTree();
    }

    private void parseTree()
            throws Exception
    {
        JsonNode vChildren = mJsonNode.path("children");
        if (vChildren.isMissingNode())
            throw new IllegalStateException("Error : Invalid tree format, must have children.");

        JsonNode vOperator = mJsonNode.path("operator");
        if (vOperator.isMissingNode())
            mOperator = Operator.AND;
        else
            mOperator = Operator.valueOf(vOperator.getTextValue().toUpperCase());

        JsonNode vPrefix = mJsonNode.path("prefix");
        if (!vPrefix.isMissingNode() && vPrefix.getTextValue().toUpperCase().equals("NOT"))
            mPrefix = false;


        Iterator<JsonNode> vIterator = vChildren.getElements();
        while (vIterator.hasNext())
        {
            RuleJsonNode vChild = new RuleJsonNode(vIterator.next());
            mChildren.add(vChild);
        }
    }
}
