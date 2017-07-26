package name.mi.manager.test;

import name.mi.manager.model.SystemVariable;

public class SystemVariableTest {
    public static void main(String... iArgs) throws Exception{
        System.out.println(SystemVariable.getSiteName());
        SystemVariable.setSiteName(SystemVariable.SiteName.FetchTheQuote);
        System.out.println(SystemVariable.getSiteName());
        SystemVariable.setSiteName(SystemVariable.SiteName.Quotes2Compare);
        System.out.println(SystemVariable.getSiteName());

    }
}
