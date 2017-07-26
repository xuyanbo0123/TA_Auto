package name.mi.buyer.surehits.test;

import java.util.List;

import name.mi.buyer.surehits.SureHitsClickResponse;
import name.mi.micore.model.ClickAd;
import org.codehaus.jackson.map.ObjectMapper;

public class JSON2ObjectTest {
    public static void main(String[] args) throws Exception {

        String vJson = "{\n" +
                "  \"listings\": [\n" +
                "    {\n" +
                "      \"sourceID\": \"580126\",\n" +
                "      \"buyerID\": \"2504\",\n" +
                "      \"company\": \"Liberty Mutual\",\n" +
                "      \"listingTitle\": \"Fast, Free Quote for Massachusetts Drivers\",\n" +
                "      \"listingDescription\": \"<ul><li>Official Website of Liberty Mutual Insurance</li><li>Providing Insurance Since 1912</li><li>See How Much YOU Could Be Saving!</li></ul>\",\n" +
                "      \"url\": \"http://www.nextinsure.com/ListingDisplay/Click/?C=NTU2&T=MA%3d%3d&L=MzAwOTYyMg%3d%3d&I=ezM4OTRmY2Y4LTg0NzgtNDFkOS04ODhlLTI0NGEwNjgxZDE5N30%3d&U=aHR0cDovL3dlbGNvbWUubGliZXJ0eW11dHVhbC5jb20vY2FtcGFpZ25zL2FnZ3JlZ2F0b3JzL3YxL2F1dG8taW5zdXJhbmNlLmh0bWw%2fY21wZ25jZGU9Mjc5JmtleUNvZGU9SUFTSEEwMDAmc3JjPWltLWRhZ2ctYXV0LXNyaE1BMTMwMzEwMDUwOCZ6aXBDb2RlPTAyMTM5JnBpZD0kcHZhciQmaW5zdXJlZD0kc2VnY2kkJmFnZT0kc2VnYWdlJCZtYXJyaWVkPSRzZWdtJCZob21lb3duZXI9JHNlZ2hvJCZzdGF0ZT1NYXNzYWNodXNldHRzJmFkY29weT0%3d&S=NTgwMTI2&CAT=MQ%3d%3d&SDT=OC8xLzIwMTMgMTI6NDA6MDIgUE0%3d&ST=MQ%3d%3d&SI=ezdjY2ZkMGEyLWUzYjctNDYwOC1hODE0LTg5ZjVkMWQ4Nzg5OH0%3d\",\n" +
                "      \"logo\": \"//cdn.nextinsure.com/images/accounts/11320301.gif\",\n" +
                "      \"category\": \"Auto Insurance\",\n" +
                "      \"stateCode\": \"MA\",\n" +
                "      \"state\": \"Massachusetts\",\n" +
                "      \"city\": \"Cambridge\",\n" +
                "      \"sitehost\": \"www.libertymutual.com\",\n" +
                "      \"linkbtn\": {\n" +
                "        \"btnclickurl\": \"http://www.nextinsure.com/ListingDisplay/Click/?C=NTU2&T=MA%3d%3d&L=MzAwOTYyMg%3d%3d&I=ezM4OTRmY2Y4LTg0NzgtNDFkOS04ODhlLTI0NGEwNjgxZDE5N30%3d&U=aHR0cDovL3dlbGNvbWUubGliZXJ0eW11dHVhbC5jb20vY2FtcGFpZ25zL2FnZ3JlZ2F0b3JzL3YxL2F1dG8taW5zdXJhbmNlLmh0bWw%2fY21wZ25jZGU9Mjc5JmtleUNvZGU9SUFTSEEwMDAmc3JjPWltLWRhZ2ctYXV0LXNyaE1BMTMwMzEwMDUwOCZ6aXBDb2RlPTAyMTM5JnBpZD0kcHZhciQmaW5zdXJlZD0kc2VnY2kkJmFnZT0kc2VnYWdlJCZtYXJyaWVkPSRzZWdtJCZob21lb3duZXI9JHNlZ2hvJCZzdGF0ZT1NYXNzYWNodXNldHRzJmFkY29weT0%3d&S=NTgwMTI2&CAT=MQ%3d%3d&SDT=OC8xLzIwMTMgMTI6NDA6MDIgUE0%3d&ST=MQ%3d%3d&SI=ezdjY2ZkMGEyLWUzYjctNDYwOC1hODE0LTg5ZjVkMWQ4Nzg5OH0%3d\",\n" +
                "        \"btnurl\": \"//cdn.nextinsure.com/images/gobutton/GetQuote-GreenArrow.png\",\n" +
                "        \"btnwidth\": \"125\",\n" +
                "        \"btnrolloverurl\": \"\",\n" +
                "        \"btnalt\": \"Get Quote\",\n" +
                "        \"btnonclick\": \"punder()\",\n" +
                "        \"btntarget\": \"_new\"\n" +
                "      },\n" +
                "      \"ishighlighted\": false,\n" +
                "      \"isfeatured\": false,\n" +
                "      \"sid\": \"{7ccfd0a2-e3b7-4608-a814-89f5d1d87898}\",\n" +
                "      \"segp\": {\n" +
                "        \"SegmentAge<=21\": \"1\",\n" +
                "        \"SegmentAge>=50\": \"1\",\n" +
                "        \"SegmentAge21-24\": \"1\",\n" +
                "        \"SegmentAge25-49\": \"1\",\n" +
                "        \"SegmentAgeUnknown\": \"1\",\n" +
                "        \"SegmentCurrentlyInsuredNo\": \"0\",\n" +
                "        \"SegmentCurrentlyInsuredUnknown\": \"\",\n" +
                "        \"SegmentCurrentlyInsuredYes\": \"1\",\n" +
                "        \"SegmentHomeownerNo\": \"1\",\n" +
                "        \"SegmentHomeownerUnknown\": \"\",\n" +
                "        \"SegmentHomeownerYes\": \"1\",\n" +
                "        \"SegmentMarriedNo\": \"1\",\n" +
                "        \"SegmentMarriedUnknown\": \"\",\n" +
                "        \"SegmentMarriedYes\": \"1\"\n" +
                "      },\n" +
                "      \"segicons\": [\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Under21-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Under21-off.png\",\n" +
                "          \"iconText\": \"Under 21 years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Senior-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Senior-off.png\",\n" +
                "          \"iconText\": \"50+ years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-2124-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-2124-off.png\",\n" +
                "          \"iconText\": \"21-24 years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-2549-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-2549-off.png\",\n" +
                "          \"iconText\": \"25-49 years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-CI-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-CI-off.png\",\n" +
                "          \"iconText\": \"Currently Insured\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Homeowner-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Homeowner-off.png\",\n" +
                "          \"iconText\": \"Homeowner\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Married-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Married-off.png\",\n" +
                "          \"iconText\": \"Married\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        }\n" +
                "      ],\n" +
                "      \"relevancerating\": 5,\n" +
                "      \"advprogramkey\": \"0\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"sourceID\": \"580126\",\n" +
                "      \"buyerID\": \"259275\",\n" +
                "      \"company\": \"HighPoint Insurance\",\n" +
                "      \"listingTitle\": \"We specialize in Massachusetts car insurance\",\n" +
                "      \"listingDescription\": \"<ul><li>Great rates for all types of drivers</li><li>#1 in claims satisfaction</li><li>Get your fast, free quote now!</li></ul>\",\n" +
                "      \"url\": \"http://www.nextinsure.com/ListingDisplay/Click/?C=NTc1ODUzMA%3d%3d&T=MA%3d%3d&L=NTQyODYwODY%3d&I=ezNjYjVlYTU1LTI0NWQtNGZmOS05NmY3LWFjMWZlYWRhMjdkYX0%3d&U=aHR0cHM6Ly9jaS5wcmFjLmNvbS9wcmFjL1BSQy9pbmRleC5qc3A%2fUExGX0NvZGU9UFJBQyZpUmVmaWQ9U1VSRSZpUmVmY2xpY2tpZD1jbGljazAwMiZ1dG1fc291cmNlPVNVUkUmdXRtX2NhbXBhaWduPWNsaWNrMDAyJnppcGNvZGU9&S=NTgwMTI2&CAT=MQ%3d%3d&SDT=OC8xLzIwMTMgMTI6NDA6MDIgUE0%3d&ST=MQ%3d%3d&SI=ezdjY2ZkMGEyLWUzYjctNDYwOC1hODE0LTg5ZjVkMWQ4Nzg5OH0%3d\",\n" +
                "      \"logo\": \"//cdn.nextinsure.com/images/accounts/11327247.gif\",\n" +
                "      \"category\": \"Auto Insurance\",\n" +
                "      \"stateCode\": \"MA\",\n" +
                "      \"state\": \"Massachusetts\",\n" +
                "      \"city\": \"Cambridge\",\n" +
                "      \"sitehost\": \"www.prac.com\",\n" +
                "      \"linkbtn\": {\n" +
                "        \"btnclickurl\": \"http://www.nextinsure.com/ListingDisplay/Click/?C=NTc1ODUzMA%3d%3d&T=MA%3d%3d&L=NTQyODYwODY%3d&I=ezNjYjVlYTU1LTI0NWQtNGZmOS05NmY3LWFjMWZlYWRhMjdkYX0%3d&U=aHR0cHM6Ly9jaS5wcmFjLmNvbS9wcmFjL1BSQy9pbmRleC5qc3A%2fUExGX0NvZGU9UFJBQyZpUmVmaWQ9U1VSRSZpUmVmY2xpY2tpZD1jbGljazAwMiZ1dG1fc291cmNlPVNVUkUmdXRtX2NhbXBhaWduPWNsaWNrMDAyJnppcGNvZGU9&S=NTgwMTI2&CAT=MQ%3d%3d&SDT=OC8xLzIwMTMgMTI6NDA6MDIgUE0%3d&ST=MQ%3d%3d&SI=ezdjY2ZkMGEyLWUzYjctNDYwOC1hODE0LTg5ZjVkMWQ4Nzg5OH0%3d\",\n" +
                "        \"btnurl\": \"//cdn.nextinsure.com/images/gobutton/GetQuote-GreenArrow.png\",\n" +
                "        \"btnwidth\": \"125\",\n" +
                "        \"btnrolloverurl\": \"\",\n" +
                "        \"btnalt\": \"Get Quote\",\n" +
                "        \"btnonclick\": \"punder()\",\n" +
                "        \"btntarget\": \"_new\"\n" +
                "      },\n" +
                "      \"ishighlighted\": false,\n" +
                "      \"isfeatured\": false,\n" +
                "      \"sid\": \"{7ccfd0a2-e3b7-4608-a814-89f5d1d87898}\",\n" +
                "      \"segp\": {\n" +
                "        \"SegmentAge<=21\": \"0\",\n" +
                "        \"SegmentAge>=50\": \"1\",\n" +
                "        \"SegmentAge21-24\": \"1\",\n" +
                "        \"SegmentAge25-49\": \"1\",\n" +
                "        \"SegmentAgeUnknown\": \"1\",\n" +
                "        \"SegmentCurrentlyInsuredNo\": \"0\",\n" +
                "        \"SegmentCurrentlyInsuredUnknown\": \"\",\n" +
                "        \"SegmentCurrentlyInsuredYes\": \"1\",\n" +
                "        \"SegmentHomeownerNo\": \"1\",\n" +
                "        \"SegmentHomeownerUnknown\": \"\",\n" +
                "        \"SegmentHomeownerYes\": \"1\",\n" +
                "        \"SegmentMarriedNo\": \"1\",\n" +
                "        \"SegmentMarriedUnknown\": \"\",\n" +
                "        \"SegmentMarriedYes\": \"1\"\n" +
                "      },\n" +
                "      \"segicons\": [\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Under21-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Under21-off.png\",\n" +
                "          \"iconText\": \"Under 21 years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Senior-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Senior-off.png\",\n" +
                "          \"iconText\": \"50+ years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-2124-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-2124-off.png\",\n" +
                "          \"iconText\": \"21-24 years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-2549-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-2549-off.png\",\n" +
                "          \"iconText\": \"25-49 years old\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-CI-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-CI-off.png\",\n" +
                "          \"iconText\": \"Currently Insured\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Homeowner-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Homeowner-off.png\",\n" +
                "          \"iconText\": \"Homeowner\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"iconImgOn\": \"SHSeg-Married-on.png\",\n" +
                "          \"iconImgOff\": \"SHSeg-Married-off.png\",\n" +
                "          \"iconText\": \"Married\",\n" +
                "          \"isVisible\": false,\n" +
                "          \"isPreferred\": true\n" +
                "        }\n" +
                "      ],\n" +
                "      \"relevancerating\": 5,\n" +
                "      \"advprogramkey\": \"0\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"listingheader\": \"Auto Insurance Providers in Massachusetts\",\n" +
                "  \"listingfooter\": \"<span>Listings provided by:  <img id=\\\"shLogo\\\" src=\\\"//cdn.nextinsure.com//overlay/images/surehits_logo_overlay.gif\\\" alt=\\\"SureHits\\\" border=\\\"0\\\" /><br class=\\\"clearIt\\\">  </span>\",\n" +
                "  \"iconlegend\": [\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-Married-Legend.png\",\n" +
                "      \"iconText\": \"Married\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-CI-Legend.png\",\n" +
                "      \"iconText\": \"Currently Insured\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-Homeowner-Legend.png\",\n" +
                "      \"iconText\": \"Homeowner\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-Senior-Legend.png\",\n" +
                "      \"iconText\": \"50+ years old\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-Under21-Legend.png\",\n" +
                "      \"iconText\": \"Under 21 years old\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-2124-Legend.png\",\n" +
                "      \"iconText\": \"21-24 years old\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-2549-Legend.png\",\n" +
                "      \"iconText\": \"25-49 years old\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-Medical-Legend.png\",\n" +
                "      \"iconText\": \"Medical Condition\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"iconLegendImg\": \"SHSeg-Senior-Legend.png\",\n" +
                "      \"iconText\": \"Senior\",\n" +
                "      \"iconShowText\": true,\n" +
                "      \"isVisible\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"altfltrsrc\": 0,\n" +
                "  \"origSearchID\": \"{7ccfd0a2-e3b7-4608-a814-89f5d1d87898}\",\n" +
                "  \"listingfiltertype\": 1,\n" +
                "  \"customervals\": {\n" +
                "    \"ni_str_state_code\": \"\",\n" +
                "    \"ni_zc\": \"02139\"\n" +
                "  },\n" +
                "  \"req\": false,\n" +
                "  \"cdb\": \"sh-lda-db1\",\n" +
                "  \"version\": \"\"\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();

        // read from file, convert it to user class
        //SureHitsData vData = mapper.readValue(vJson, SureHitsData.class);
        List<ClickAd> vList = SureHitsClickResponse.getAds(vJson, -1, "test token");
        // display to console
        System.out.println(vList);
    }
}
