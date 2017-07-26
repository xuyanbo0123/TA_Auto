package name.mi.auto.test;

import name.mi.auto.enumerate.IncidentType;

public class TypeTest {
    public static void main(String... a) {
        for (IncidentType vValue :IncidentType.values())
        {
            System.out.print(vValue.name());
        }
    }
}
