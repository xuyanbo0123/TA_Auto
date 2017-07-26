package name.mi.micore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Weixiong on 12/14/13.
 */
public class NegativeRegexTest
{
    public static void main(String[] iArgs)
    {
        String vTestSub = "commerce west";
        Pattern vPtn = Pattern.compile("commerce(\\s{0,})(?!west)");
        Matcher vMt;

        vMt = vPtn.matcher(vTestSub);
        if(vMt.find())
        {
            System.out.println(vMt.group(0));
        }
        else
        {
            System.out.println("Not found");
        }
    }
}
