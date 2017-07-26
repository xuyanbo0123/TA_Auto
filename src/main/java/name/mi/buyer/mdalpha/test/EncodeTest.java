package name.mi.buyer.mdalpha.test;

import name.mi.buyer.mdalpha.map.EncodeMap;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EncodeTest {

    public static void main(String[] args) throws Exception
    {
        String vResult = "{vIDr~5B6ZjlJ1eRBzz5u5uRVi_ZYTpEutqA~vK17V{vM90210}vD~Test_Sub_1~k14}";
        System.out.println(getClickPostMessage("90210", "Test_Sub_1"));
        System.out.println(vResult);

    }
    public static String getClickPostMessage(String iZipCode, String iToken)
    {
        /*Example:
          {
            "type": "ad_unit",
            "placement_id": "5B6ZjlJ1eRBzz5u5uRVi_ZYTpEutqA",
            "version": "17",
            "data":
             {
                "zip": "90210"
             }
          }
        */
        //http://insurance.mediaalpha.com/js/serve_.js?z=
        //{vIDr~5B6ZjlJ1eRBzz5u5uRVi_ZYTpEutqA~vK17V{vM90210}vD~Test_Sub_1~k14}&_=28075352;
        String vPlacementID = "5B6ZjlJ1eRBzz5u5uRVi_ZYTpEutqA";
        String vMessage =
                "{"+
                  EncodeMap.valueOf("type")+
                  EncodeMap.valueOf("ad_unit")+
                  EncodeMap.valueOf("placement_id")+
                  "~"+vPlacementID+"~"+
                  EncodeMap.valueOf("version")+
                  EncodeMap.valueOf("17")+
                  EncodeMap.valueOf("data")+
                  "{"+
                   EncodeMap.valueOf("zip")+
                   iZipCode+
                  "}"+
                  EncodeMap.valueOf("sub_1")+
                  "~"+iToken+"~"+
                        EncodeMap.valueOf("local_hour")+
                        getLocalHour()+
                "}";
        return vMessage;


    }
    private static String getLocalHour()
    {
        Date vNow = new Date();
        SimpleDateFormat vHH = new SimpleDateFormat("HH");
        return vHH.format(vNow);
    }

}
