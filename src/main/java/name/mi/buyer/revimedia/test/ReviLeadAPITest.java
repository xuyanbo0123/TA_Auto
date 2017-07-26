package name.mi.buyer.revimedia.test;

import name.mi.buyer.revimedia.download.DownloadAPIReportsReviLead;

/**
 * Created by weixiong on 1/18/14.
 */
public class ReviLeadAPITest
{
    public static void main(String ... iArgs)
    {
        DownloadAPIReportsReviLead vDL = new DownloadAPIReportsReviLead(null, true);
        try
        {
            if(vDL.execute())
            {
                System.out.println(vDL.getFinalResult());
            }
            else
            {
                System.out.println("Error");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
