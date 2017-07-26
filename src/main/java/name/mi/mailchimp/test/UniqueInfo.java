package name.mi.mailchimp.test;

import com.ecwid.mailchimp.method.v2_0.lists.BatchSubscribeResult;
import name.mi.environment.model.EnvironmentVariable;
import name.mi.mailchimp.dao.MailChimpInformationDAO;
import name.mi.mailchimp.handler.CampaignHandler;
import name.mi.mailchimp.handler.ListHandler;
import name.mi.mailchimp.handler.TemplateHandler;
import name.mi.mailchimp.method.campaigns.CreateResult;
import name.mi.mailchimp.method.campaigns.SegmentTestResult;
import name.mi.mailchimp.method.campaigns.SendOrScheduleResult;
import name.mi.mailchimp.method.lists.MergeVars;
import name.mi.mailchimp.method.templates.AddResult;
import name.mi.mailchimp.method.templates.ListResult;
import name.mi.mailchimp.method.templates.UpdateResult;
import name.mi.mailchimp.method.templates.UserTemplate;
import name.mi.mailchimp.model.MailChimpInformation;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UniqueInfo {
    private static final Logger LOGGER = LogManager.getLogger(UniqueInfo.class);


    private static final String sQuotes2CompareAPIKey = "06befb07599052b4231a9ba110d4e3cb-us8";
    private static final String sQuotes2CompareTotal = "0880174369";
    private static final String sQuotes2CompareRenewTemplate = "74053";
    private static final String sQuotes2CompareRenewSubject = "Do You Like Your Current Insurance Policy?";
    private static final String sQuotes2CompareFromEmail = "support@quotes2compare.com";
    private static final String sQuotes2CompareFromName = "Quotes2Compare";
    private static final String sTestList = "f17c05ed51";


    private static final String sFetchTheQuoteAPIKey = "";
    private static final String sFetchTheQuoteTotal = "";
    private static final String sFetchTheQuoteRenewTemplate = "";
    private static final String sFetchTheQuoteRenewSubject = "Do You Like Your Current Insurance Policy?";
    private static final String sFetchTheQuoteFromEmail = "support@fetchthequote.com";
    private static final String sFetchTheQuoteFromName = "FetchTheQuote";
    

    public static void main(String... iArgs)
    {
//        testListBatchSubscribe();
//        testTemplateAdd();
//        testTemplateUpdate();
//        testTemplateList();
        testCampaignCreate();
//        testCampaignSend();
//        testCampaignCreateAndSend();
//        testSegmentTest();
//        testCampaignCreateAndSchedule();
//        OneTimeCreate();
    }

    private static void testListBatchSubscribe()
    {
        List<MergeVars> vMergeVarsList = new ArrayList<>();
        vMergeVarsList.add(new MergeVars("phoenixkarl@gmail.com", "Xavier", "Ke", "Quotes2Compare-2014-04-12-74860dae-b974-4894-be24-5e2a6406085a"));
        BatchSubscribeResult vBatchSubscribeResult = ListHandler.batchSubscribe(sQuotes2CompareAPIKey, sTestList, vMergeVarsList);
        System.out.println(vBatchSubscribeResult.add_count);
        System.out.println(vBatchSubscribeResult.update_count);
        System.out.println(vBatchSubscribeResult.error_count);
    }

    private static void testTemplateAdd()
    {
        String vHtml = "<html>\n" +
                "<body>\n" +
                "<div marginwidth=\"0\" marginheight=\"0\" style=\"margin:0;padding:0\">\n" +
                "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"100%\"><tbody><tr><td bgcolor=\"white\" style=\"font-family:helvetica,arial,sans-serif;font-size:12px;color:#222222\" valign=\"top\">   \n" +
                "    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"600\" bgcolor=\"white\" align=\"center\" style=\"font-family:helvetica,arial,sans-serif;font-size:13px;margin-bottom:4px\">\n" +
                "        <tbody>\n" +
                "    </tbody></table>\n" +
                "    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"600\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"font-family:helvetica,arial,sans-serif;font-size:13px;color:#222222;background:#f2f4f4;border-left:2px solid #f2f2f3;border-right:2px solid #f2f2f3;border-bottom:2px solid #f2f2f3\">\n" +
                "               <tbody><tr>\n" +
                "            <td bgcolor=\"#F2F4F4\" style=\"padding:10px 26px\">\n" +
                "                <a href=\"http://quotes2compare.com/sid=2&tap518=*|TOKEN|*\" target=\"_blank\"><img border=\"0\" alt=\"Quotes2Compare\" width=\"188\" height=\"40\" src=\"http://www.quotes2compare.com/images/email/quotes2compare-logo.png\" style=\"color:#222222;font-size:16px;font-family:helvetica,arial,sans-serif\"></a>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>   \n" +
                "            <td bgcolor=\"#F2F4F4\" style=\"padding-left:15px\">\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"66\">\n" +
                "                    <tbody><tr>\n" +
                "                        <td height=\"66\" width=\"51\">\n" +
                "                            <img src=\"http://www.quotes2compare.com/images/email/small.png\" height=\"66\" width=\"51\" style=\"display:block\">\n" +
                "                        </td>\n" +
                "                        <td height=\"66\" style=\"height:66px\">\n" +
                "                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"66\" style=\"font-family:helvetica,arial,sans-serif;font-size:19px;font-weight:bold;color:#222222\">\n" +
                "                                <tbody><tr height=\"8\"><td height=\"8\" bgcolor=\"#F2F4F4\"></td></tr>\n" +
                "                                <tr height=\"52\">\n" +
                "                                    <td height=\"52\" bgcolor=\"#FFFFFF\" style=\"padding-right:30px\">It is time to review your car insurance!</td>\n" +
                "                                </tr>\n" +
                "                                <tr height=\"6\"><td height=\"6\" bgcolor=\"#F2F4F4\"></td></tr>\n" +
                "                            </tbody></table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#F2F4F4\" style=\"padding:10px 26px;font-size:15px\">\n" +
                "                <p>We promised we'd email you only when you really needed and it is time now.<br>\n" +
                "\t\t\t\tGet quotes from multiple carriers and review your current insurance policy.<br>\n" +
                "\t\t\t\t<br>\n" +
                "\t\t\t\tWe hope you could always pay as less as possible for your car insurance.<br>\n" +
                "\t\t\t\t<br></p>           \n" +
                "            </td>\n" +
                "        </tr>\n" +
                "            <tr><td>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"596\" bgcolor=\"white\" align=\"center\">\n" +
                "                    <tbody><tr height=\"2\" style=\"height:2px;background:#ffffff;font-size:2px\"><td height=\"2\" style=\"height:2px\" colspan=\"2\"></td></tr>\n" +
                "                    \n" +
                "\n" +
                "<tr>\n" +
                "    <td colspan=\"2\">\n" +
                "        <table width=\"596\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tbody><tr height=\"26\" style=\"background:#fafbfb\">\n" +
                "                <td colspan=\"5\">\n" +
                "                    <img src=\"spacer.gif\" height=\"26\" width=\"100%\">\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td width=\"26\" style=\"background:#fafbfb\">\n" +
                "                    <img src=\"spacer.gif\" width=\"26\" height=\"100%\">\n" +
                "                </td>\n" +
                "                <td width=\"281\" valign=\"top\" style=\"background:#fafbfb\">\n" +
                "                    <table width=\"281\" cellspacing=\"3\" cellpadding=\"0\" border=\"0\" style=\"font-family:helvetica,arial,sans-serif\">\n" +
                "                        <tbody><tr>\n" +
                "                            <td>Check if you can reduce your auto insurance premiums now</td>\n" +
                "                        </tr>\n" +
                "\n" +
                "                        <tr height=\"2\" style=\"background:#fafbfb\"><td></td></tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"font-size:18px;color:#222222\">Quotes2Compare</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"font-size:12px;background:#fafbfb\">\n" +
                "                                \n" +
                "                                    \n" +
                "                                        Multiple quotes at one page</td>\n" +
                "                        </tr>\n" +
                "\n" +
                "                        \n" +
                "                        \n" +
                "                                               \n" +
                "                        <tr height=\"18\"><td style=\"background:#fafbfb\"></td></tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"background:#fafbfb\">\n" +
                "                                \n" +
                "                                <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"width:281px\">\n" +
                "                                    <tbody><tr>\n" +
                "                                        <td width=\"121\" valign=\"middle\" style=\"font-family:helvetica,arial,sans-serif;font-size:21px;color:#ff8600\">\n" +
                "                                            Free<br>\n" +
                "                                            Quotes</td>\n" +
                "                                        <td width=\"160\" style=\"text-align:right\" valign=\"middle\">\n" +
                "                                            <a href=\"http://quotes2compare.com?sid=2&tap518=*|TOKEN|*\" style=\"text-decoration:none;font-size:24px;color:#ffffff;display:block;min-height:40px\" target=\"_blank\"><img alt=\"View this deal\" src=\"http://www.quotes2compare.com/images/email/getquotes.png\" border=\"0\" width=\"180\" height=\"40\" style=\"display:block\"></a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </tbody></table>\n" +
                "                                \n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </tbody></table>\n" +
                "                </td>\n" +
                "                <td width=\"30\" style=\"background:#fafbfb\">\n" +
                "                    \n" +
                "                </td>\n" +
                "                <td width=\"233\" valign=\"top\" style=\"background:#fafbfb;text-align:right\">\n" +
                "                    <a href=\"http://quotes2compare.com?sid=2&tap518=*|TOKEN|*\" style=\"color:#4187cd\" target=\"_blank\">\n" +
                "                        <img alt=\"Quotes2Compare\" src=\"http://www.quotes2compare.com/images/email/q2c-girl.png\" border=\"0\" height=\"auto\" width=\"233\" align=\"right\" style=\"margin:0\">\n" +
                "                    </a>\n" +
                "                </td>\n" +
                "                <td width=\"26\" style=\"background:#fafbfb\">\n" +
                "                    \n" +
                "                </td>\n" +
                "            </tr>\n" +
                "\n" +
                "        </tbody></table>\n" +
                "    </td>\n" +
                "</tr>\n" +
                "<tr height=\"2\" style=\"height:2px;background:#ffffff;font-size:2px\"><td height=\"2\" style=\"height:2px\" colspan=\"2\"></td></tr>\n" +
                "\n" +
                "\n" +
                "                </tbody></table>\n" +
                "            </td>\n" +
                "        </tr></tbody></table></td></tr>\n" +
                "        \n" +
                "        <tr>\n" +
                "\t\t    <td style=\"padding:28px\">&nbsp;</td>\n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr>\n" +
                "            <td style=\"font-size:10px;padding:0 28px 28px\"><p></p></td>\n" +
                "        </tr>\n" +
                "\t</tbody></table>\n" +
                "    \n" +
                "    <br><br>\n" +
                "</div>\n" +
                "    </body>\n" +
                "    </html>";

        String vTemplateName = "Quotes2CompareRenewTemplate";

        AddResult vAddResult = TemplateHandler.add(sQuotes2CompareAPIKey, vTemplateName, vHtml);
        System.out.println(vAddResult.template_id);
    }

    private static void testTemplateUpdate()
    {
        String vHtml = "<html>\n" +
                "<body>\n" +
                "<div marginwidth=\"0\" marginheight=\"0\" style=\"margin:0;padding:0\">\n" +
                "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"100%\"><tbody><tr><td bgcolor=\"white\" style=\"font-family:helvetica,arial,sans-serif;font-size:12px;color:#222222\" valign=\"top\">   \n" +
                "    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"600\" bgcolor=\"white\" align=\"center\" style=\"font-family:helvetica,arial,sans-serif;font-size:13px;margin-bottom:4px\">\n" +
                "        <tbody>\n" +
                "    </tbody></table>\n" +
                "    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"600\" bgcolor=\"#FFFFFF\" align=\"center\" style=\"font-family:helvetica,arial,sans-serif;font-size:13px;color:#222222;background:#f2f4f4;border-left:2px solid #f2f2f3;border-right:2px solid #f2f2f3;border-bottom:2px solid #f2f2f3\">\n" +
                "               <tbody><tr>\n" +
                "            <td bgcolor=\"#F2F4F4\" style=\"padding:10px 26px\">\n" +
                "                <a href=\"http://quotes2compare.com/sid=2&tap518=*|TOKEN|*\" target=\"_blank\"><img border=\"0\" alt=\"Quotes2Compare\" width=\"188\" height=\"40\" src=\"http://www.quotes2compare.com/images/email/quotes2compare-logo.png\" style=\"color:#222222;font-size:16px;font-family:helvetica,arial,sans-serif\"></a>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>   \n" +
                "            <td bgcolor=\"#F2F4F4\" style=\"padding-left:15px\">\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"66\">\n" +
                "                    <tbody><tr>\n" +
                "                        <td height=\"66\" width=\"51\">\n" +
                "                            <img src=\"http://www.quotes2compare.com/images/email/small.png\" height=\"66\" width=\"51\" style=\"display:block\">\n" +
                "                        </td>\n" +
                "                        <td height=\"66\" style=\"height:66px\">\n" +
                "                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" height=\"66\" style=\"font-family:helvetica,arial,sans-serif;font-size:19px;font-weight:bold;color:#222222\">\n" +
                "                                <tbody><tr height=\"8\"><td height=\"8\" bgcolor=\"#F2F4F4\"></td></tr>\n" +
                "                                <tr height=\"52\">\n" +
                "                                    <td height=\"52\" bgcolor=\"#FFFFFF\" style=\"padding-right:30px\">It is time to review your car insurance!</td>\n" +
                "                                </tr>\n" +
                "                                <tr height=\"6\"><td height=\"6\" bgcolor=\"#F2F4F4\"></td></tr>\n" +
                "                            </tbody></table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody></table>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td bgcolor=\"#F2F4F4\" style=\"padding:10px 26px;font-size:15px\">\n" +
                "                <p>We promised we'd email you only when you really needed and it is time now.<br>\n" +
                "\t\t\t\tGet quotes from multiple carriers and review your current insurance policy.<br>\n" +
                "\t\t\t\t<br>\n" +
                "\t\t\t\tWe hope you could always pay as less as possible for your car insurance.<br>\n" +
                "\t\t\t\t<br></p>           \n" +
                "            </td>\n" +
                "        </tr>\n" +
                "            <tr><td>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"596\" bgcolor=\"white\" align=\"center\">\n" +
                "                    <tbody><tr height=\"2\" style=\"height:2px;background:#ffffff;font-size:2px\"><td height=\"2\" style=\"height:2px\" colspan=\"2\"></td></tr>\n" +
                "                    \n" +
                "\n" +
                "<tr>\n" +
                "    <td colspan=\"2\">\n" +
                "        <table width=\"596\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tbody><tr height=\"26\" style=\"background:#fafbfb\">\n" +
                "                <td colspan=\"5\">\n" +
                "                    <img src=\"http://www.quotes2compare.com/images/email/spacer.gif\" height=\"26\" width=\"100%\">\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td width=\"26\" style=\"background:#fafbfb\">\n" +
                "                    <img src=\"http://www.quotes2compare.com/images/email/spacer.gif\" width=\"26\" height=\"100%\">\n" +
                "                </td>\n" +
                "                <td width=\"281\" valign=\"top\" style=\"background:#fafbfb\">\n" +
                "                    <table width=\"281\" cellspacing=\"3\" cellpadding=\"0\" border=\"0\" style=\"font-family:helvetica,arial,sans-serif\">\n" +
                "                        <tbody><tr>\n" +
                "                            <td>Check if you can reduce your auto insurance premiums now</td>\n" +
                "                        </tr>\n" +
                "\n" +
                "                        <tr height=\"2\" style=\"background:#fafbfb\"><td></td></tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"font-size:18px;color:#222222\">Quotes2Compare</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"font-size:12px;background:#fafbfb\">\n" +
                "                                \n" +
                "                                    \n" +
                "                                        Multiple quotes at one page</td>\n" +
                "                        </tr>\n" +
                "\n" +
                "                        \n" +
                "                        \n" +
                "                                               \n" +
                "                        <tr height=\"18\"><td style=\"background:#fafbfb\"></td></tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"background:#fafbfb\">\n" +
                "                                \n" +
                "                                <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"width:281px\">\n" +
                "                                    <tbody><tr>\n" +
                "                                        <td width=\"121\" valign=\"middle\" style=\"font-family:helvetica,arial,sans-serif;font-size:21px;color:#ff8600\">\n" +
                "                                            Free<br>\n" +
                "                                            Quotes</td>\n" +
                "                                        <td width=\"160\" style=\"text-align:right\" valign=\"middle\">\n" +
                "                                            <a href=\"http://quotes2compare.com?sid=2&tap518=*|TOKEN|*\" style=\"text-decoration:none;font-size:24px;color:#ffffff;display:block;min-height:40px\" target=\"_blank\"><img alt=\"View this deal\" src=\"http://www.quotes2compare.com/images/email/getquotes.png\" border=\"0\" width=\"180\" height=\"40\" style=\"display:block\"></a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </tbody></table>\n" +
                "                                \n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </tbody></table>\n" +
                "                </td>\n" +
                "                <td width=\"30\" style=\"background:#fafbfb\">\n" +
                "                    \n" +
                "                </td>\n" +
                "                <td width=\"233\" valign=\"top\" style=\"background:#fafbfb;text-align:right\">\n" +
                "                    <a href=\"http://quotes2compare.com?sid=2&tap518=*|TOKEN|*\" style=\"color:#4187cd\" target=\"_blank\">\n" +
                "                        <img alt=\"Quotes2Compare\" src=\"http://www.quotes2compare.com/images/email/q2c-girl.png\" border=\"0\" height=\"auto\" width=\"233\" align=\"right\" style=\"margin:0\">\n" +
                "                    </a>\n" +
                "                </td>\n" +
                "                <td width=\"26\" style=\"background:#fafbfb\">\n" +
                "                    \n" +
                "                </td>\n" +
                "            </tr>\n" +
                "\n" +
                "        </tbody></table>\n" +
                "    </td>\n" +
                "</tr>\n" +
                "<tr height=\"2\" style=\"height:2px;background:#ffffff;font-size:2px\"><td height=\"2\" style=\"height:2px\" colspan=\"2\"></td></tr>\n" +
                "\n" +
                "\n" +
                "                </tbody></table>\n" +
                "            </td>\n" +
                "        </tr></tbody></table></td></tr>\n" +
                "        \n" +
                "        <tr>\n" +
                "\t\t    <td style=\"padding:28px\">&nbsp;</td>\n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr>\n" +
                "            <td style=\"font-size:10px;padding:0 28px 28px\"><p></p></td>\n" +
                "        </tr>\n" +
                "\t</tbody></table>\n" +
                "    \n" +
                "    <br><br>\n" +
                "</div>\n" +
                "    </body>\n" +
                "    </html>";
        UpdateResult vUpdateResult = TemplateHandler.update(sQuotes2CompareAPIKey, Integer.parseInt(sQuotes2CompareRenewTemplate), vHtml);
        System.out.println(vUpdateResult.complete);
    }

    private static void testTemplateList()
    {
        ListResult vListResult = TemplateHandler.list(sQuotes2CompareAPIKey);
        for (UserTemplate vUserTemplate : vListResult.user)
        {
            System.out.println(vUserTemplate.id + " " + vUserTemplate.name);
        }
    }

    private static void testCampaignCreate()
    {
        CreateResult vCreateResult = new CreateResult();
        vCreateResult = CampaignHandler.create(
                sQuotes2CompareAPIKey,
                sQuotes2CompareTotal,
                sQuotes2CompareRenewSubject,
                sQuotes2CompareFromEmail,
                sQuotes2CompareFromName,
                Integer.parseInt(sQuotes2CompareRenewTemplate),
                false,
                "eq",
                new SimpleDateFormat("yyyy-MM-dd").format(new Date())
        );

        if (vCreateResult != null)
        {
            System.out.println(vCreateResult.id);
            System.out.println(vCreateResult.list_id);
            System.out.println(vCreateResult.template_id);
        }
    }

//    private static void testCampaignSend()
//    {
//        SendOrScheduleResult vSendResult = CampaignHandler.send(sQuotes2CompareAPIKey, testSubjectCid);
//        System.out.println(vSendResult.complete);
//    }

    private static void testCampaignCreateAndSend()
    {
        CreateResult vCreateResult = new CreateResult();
        vCreateResult = CampaignHandler.create(
                sQuotes2CompareAPIKey,
                sTestList,
                sQuotes2CompareRenewSubject,
                sQuotes2CompareFromEmail,
                sQuotes2CompareFromName,
                Integer.parseInt(sQuotes2CompareRenewTemplate),
                true
        );
        if (vCreateResult != null)
        {
            SendOrScheduleResult vSendResult = CampaignHandler.send(sQuotes2CompareAPIKey, vCreateResult.id);
            System.out.println(vSendResult.complete);
        }
        else
        {
            System.out.println("create campaign error");
        }
    }

    private static void testSegmentTest()
    {
        SegmentTestResult vSegmentTestResult = new SegmentTestResult();
        vSegmentTestResult = CampaignHandler.segmentTest(sQuotes2CompareAPIKey, sTestList, "eq", "2014-04-04");
        if (vSegmentTestResult != null)
        {
            System.out.println(vSegmentTestResult.total);
        }
        else
        {
            System.out.println("create campaign error");
        }
    }

    private static void testCampaignCreateAndSchedule()
    {
        CreateResult vCreateResult = new CreateResult();
        String vCampaignName = "TestSubject_" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
        vCreateResult = CampaignHandler.create(
                sQuotes2CompareAPIKey,
                sTestList,
                vCampaignName,
                "testFromEmail@gmail.com",
                "testFromName",
                Integer.parseInt(sQuotes2CompareRenewTemplate),
                false,
                "eq",
                "2014-04-04"
        );

        if (vCreateResult != null)
        {
            SendOrScheduleResult vScheduleResult = CampaignHandler.schedule(sQuotes2CompareAPIKey, vCreateResult.id, "2014-04-04 20:30:00");
            System.out.println(vScheduleResult.complete);
        }
        else
        {
            System.out.println("schedule campaign error");
        }
    }

    private static void OneTimeCreate()
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();

            // Prepare Date Interval
            Calendar vCalendar = Calendar.getInstance();
            vCalendar.setTime(new Date());
            vCalendar.add(Calendar.MONTH, -9);
            Date vNineMonthAgoStart = vCalendar.getTime();
            vCalendar.add(Calendar.MONTH, 6);
            vCalendar.add(Calendar.DATE, 1);
            Date vThreeMonthAgoEnd = vCalendar.getTime();
            System.out.println(vNineMonthAgoStart);
            System.out.println(vThreeMonthAgoEnd);

            // Get Local Email List
            List<MailChimpInformation> vMailChimpInformationList = MailChimpInformationDAO.getMailChimpInformationByDateInterval(LOGGER, vConnection, vNineMonthAgoStart, vThreeMonthAgoEnd);
            System.out.println(vMailChimpInformationList.size());
            // BatchSubscribe, Create and Schedule Campaign
            List<MergeVars> vMergeVarsList = new ArrayList<>();
            for (MailChimpInformation vMailChimpInformation : vMailChimpInformationList)
            {
                vMergeVarsList.add(new MergeVars(vMailChimpInformation.getEmailAddress(), vMailChimpInformation.getFirstName(), vMailChimpInformation.getLastName(), vMailChimpInformation.getToken()));
            }

            EnvironmentVariable.SiteName vSiteName = EnvironmentVariable.getSiteName();

            if (vSiteName.equals(EnvironmentVariable.SiteName.Quotes2Compare))
            {
                BatchSubscribeResult vBatchSubscribeResult = ListHandler.batchSubscribe(sQuotes2CompareAPIKey, sQuotes2CompareTotal, vMergeVarsList);

                CreateResult vCreateResult = new CreateResult();
                vCreateResult = CampaignHandler.create(
                        sQuotes2CompareAPIKey,
                        sQuotes2CompareTotal,
                        sQuotes2CompareRenewSubject,
                        sQuotes2CompareFromEmail,
                        sQuotes2CompareFromName,
                        Integer.parseInt(sQuotes2CompareRenewTemplate),
                        false,
                        "eq",
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                );
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
