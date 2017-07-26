package name.mi.buyer.html;

import name.mi.buyer.html.derivative.HtmlLead;
import name.mi.micore.model.LeadRequest;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.util.List;

public class HtmlLeadPost {

    private static String TABLE_START = "<table align=\"center\" border=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" style=\"border-collapse:collapse;\">\r\n";
    private static String TABLE_END = "</table>\r\n";
    private static String ROW_TEMPLATE = "<tr><td>%s</td><td>%s</td><td>%s</td></tr>\r\n";
    private static String ROW_SPAN_TEMPLATE = "<tr><td colspan=\"%d\"><b>%s</b></td></tr>\r\n";

    public static String getRowHtml(String iKey, String iValue, String iDescription)
    {
        return String.format(ROW_TEMPLATE,iKey,iValue,iDescription);
    }

    public static String getHeaderHtml(List<String> iHeaders)
    {
        String vHtml = "<tr>";
        for (String vHeader : iHeaders)
        {
            vHtml += "<th>"+vHeader+"</th>";
        }
        return vHtml+"</tr>"+"\r\n";
    }
    public static String getRowSpanHtml(String iTitle, int iSpan)
    {
        return String.format(ROW_SPAN_TEMPLATE,iSpan, iTitle);
    }


    public static String getHtmlString(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest)
            throws Exception
    {

        HtmlLead vHtmlLead = new HtmlLead(iLogger, iConnection, iLeadRequest);
        return TABLE_START + vHtmlLead.toHtmlString() + TABLE_END;
    }
}
