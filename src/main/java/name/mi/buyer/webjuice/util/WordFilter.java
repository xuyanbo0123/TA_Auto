package name.mi.buyer.webjuice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFilter {

    public static String trimName(String iValue)
    {
        Pattern p1 = Pattern.compile("[^0-9A-Za-z ]+");
        Matcher m1 = p1.matcher(iValue);
        if (m1.find())
        {
            iValue = m1.replaceAll("");
        }

        Pattern p2 = Pattern.compile(" +");
        Matcher m2 = p2.matcher(iValue);
        if (m2.find())
        {
            iValue = m2.replaceAll(" ");
        }
        return iValue;
    }

    public static boolean isMoreThanTwice(String iValue)
    {
        Pattern p1 = Pattern.compile(" +");
        Matcher m1 = p1.matcher(iValue);
        if (m1.find())
        {
            iValue = m1.replaceAll("");
        }

        Pattern p2 = Pattern.compile("([^ ])\\1\\1+");
        Matcher m2 = p2.matcher(iValue.toLowerCase());
        if (m2.find())
        {
            return true;
        }
        return false;
    }
    
    public static boolean isSubStringValid(String iValue){
        if (iValue == null)
            return true;
        Pattern p1 = Pattern.compile(" +");
        Matcher m1 = p1.matcher(iValue);
        if (m1.find())
        {
            iValue = m1.replaceAll("");
        }

        String[] vInvalidSubStrings = new String[]{
                "asdf", "null", "ass", "azz", "bastard", "basterd",
                "bitch", "b1tch", "b!tch", "butt", "cock", "crap",
                "cunt", "dick", "fuck", "fag", "fuck", "fuk", "kunt",
                "piss", "porn", "p0rn", "poop", "p00p", "shit", "sh1t", "sh!t", "slut"};

        for(String v : vInvalidSubStrings){
            if(iValue.contains(v)){
                return false;
            }
        }
        return true;
    }
}
