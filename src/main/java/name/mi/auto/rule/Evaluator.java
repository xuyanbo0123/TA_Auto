package name.mi.auto.rule;

import name.mi.auto.dao.AutoFormDAO;
import name.mi.auto.dao.DriverDAO;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.micore.model.LeadRequest;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class Evaluator {
    private AutoForm mAutoForm;
    private Driver mDriver;

    public static boolean evaluate(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest, RuleJsonNode iRuleJsonNode)
            throws Exception
    {
        AutoForm vAutoForm = AutoFormDAO.getAutoFormByLeadRequestID(iLogger, iConnection, iLeadRequest.getID());
        List<Driver> vDrivers = DriverDAO.getDriversByAutoFormID(iLogger, iConnection, vAutoForm.getID());
        Evaluator vEvaluator = new Evaluator(vAutoForm, vDrivers.get(0));
        return vEvaluator.evaluate(iRuleJsonNode);
    }
    public Evaluator(AutoForm iAutoForm, Driver iDriver) {
        mAutoForm = iAutoForm;
        mDriver = iDriver;
    }

    public boolean evaluate(RuleJsonNode iRuleJsonNode)
            throws Exception {
        Boolean vResult = null;
        if (iRuleJsonNode.isValueNode())
            vResult = evaluate(iRuleJsonNode.getJsonNode().getTextValue());
        if (iRuleJsonNode.isObject()) {
            List<RuleJsonNode> vChildren = iRuleJsonNode.getChildren();
            vResult = evaluate(vChildren.get(0));
            for (int i = 1; i < vChildren.size(); i++) {
                switch (iRuleJsonNode.getOperator()) {
                    case AND:
                        vResult = vResult && evaluate(vChildren.get(i));
                        break;
                    case OR:
                        vResult = vResult || evaluate(vChildren.get(i));
                        break;
                    default:
                        throw new IllegalStateException("evaluate Error : Invalid Operator");
                }
            }
        }

        if (vResult == null)
            throw new IllegalStateException("evaluate Error : Invalid node type.");
        else if (iRuleJsonNode.isPrefix())
            return vResult;
        else
            return !vResult;
    }

    public boolean evaluate(String vExpression)
            throws Exception {
        String[] vOperators = {"==", "!=", "<=", ">="};
        for (String vOperator : vOperators) {
            if (vExpression.contains(vOperator)) {
                String vKey = vExpression.substring(0, vExpression.indexOf(vOperator));
                String vValue = vExpression.substring(vExpression.indexOf(vOperator) + 2);
                return evaluate(vOperator, vKey, vValue);
            }
        }
        return Boolean.parseBoolean(vExpression.toLowerCase());
    }

    public boolean evaluate(String iOperator, String iKey, String iValue)
            throws Exception {

        if (iOperator.equals("==")) {
            String[] vValues = iValue.split("\\|");
            for (String vValue : vValues)
                if (getString(iKey).equals(vValue))
                    return true;
            return false;
        }
        if (iOperator.equals("!=")) {
            String[] vValues = iValue.split("\\|");
            for (String vValue : vValues)
                if (getString(iKey).equals(vValue))
                    return false;
            return true;
        }
        if (iOperator.equals(">="))
            return getInt(iKey) >= Integer.parseInt(iValue);
        if (iOperator.equals("<="))
            return getInt(iKey) <= Integer.parseInt(iValue);
        throw new IllegalStateException("AutoForm evaluate error invalid operator " + iOperator);
    }

    public int getInt(String iKey) {
        if (iKey.equals("yearswithcompany"))
            return mAutoForm.getYearsWithCompany().getYears();
        if (iKey.equals("yearslived"))
            return mAutoForm.getYearsLived().getYears();
        if (iKey.equals("age"))
            return mDriver.getAge();
        if (iKey.equals("agelic"))
            return mDriver.getAgeLic().getAge();
        throw new IllegalStateException("AutoForm getInt invalid key :" + iKey);

    }

    public String getString(String iKey)
            throws Exception {
        iKey = iKey.toLowerCase();

        //---------AutoForm--------------
        if (iKey.equals("coveragetype"))
            return mAutoForm.getCoverageType().name();
        if (iKey.equals("iscurrentinsured"))
            return "" + mAutoForm.isCurrentlyInsured();
        if (iKey.equals("currentcompany"))
            return mAutoForm.getCurrentCompany().name();
        if (iKey.equals("yearswithcompany"))
            return mAutoForm.getYearsWithCompany().name();
        if (iKey.equals("isowned"))
            return ""+mAutoForm.isOwned();
        if (iKey.equals("zip"))
            return mAutoForm.getZip().getCode();
        if (iKey.equals("street"))
            return mAutoForm.getStreet();
        if (iKey.equals("city"))
            return mAutoForm.getCity();
        if (iKey.equals("state"))
            return mAutoForm.getState();
        if (iKey.equals("email"))
            return mAutoForm.getEmail().getEmailAddress();
        if (iKey.equals("phone"))
            return mAutoForm.getPhone().getNum();
        if (iKey.equals("yearslived"))
            return mAutoForm.getYearsLived().name();

        //----------Driver------------
        if (iKey.equals("relationship"))
            return mDriver.getRelationship().name();
        if (iKey.equals("age"))
            return "" + mDriver.getAge();
        if (iKey.equals("gender"))
            return mDriver.getGender().name();
        if (iKey.equals("maritalstatus"))
            return mDriver.getMaritalStatus().name();
        if (iKey.equals("occupation"))
            return mDriver.getOccupation().name();
        if (iKey.equals("education"))
            return mDriver.getEducation().name();
        if (iKey.equals("agelic"))
            return "" + mDriver.getAgeLic();
        if (iKey.equals("licstatus"))
            return mDriver.getLicStatus().name();
        if (iKey.equals("issr22required"))
            return "" + mDriver.getIsSR22Required();

        throw new IllegalStateException("evaluator getString invalid key :" + iKey);
    }
}
