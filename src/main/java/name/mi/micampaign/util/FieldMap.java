package name.mi.micampaign.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FieldMap {
    private static final Map<String, String> mMap;
    static {
        Map<String, String> vMap = new HashMap<String, String>();
        vMap.put("keyword_id","Ad_Group_Keyword.id");
        vMap.put("match_type","Ad_Group_Keyword.match_type");
        vMap.put("keyword_l_status","Ad_Group_Keyword.`local_status`");
        vMap.put("keyword_ps_id","Ad_Group_Keyword.provider_supplied_id");
        vMap.put("keyword_ps_status","Ad_Group_Keyword.`provider_status`");
        vMap.put("keyword_uploaded_ts","Ad_Group_Keyword.uploaded_ts");
        vMap.put("keyword_text","Keyword.text");
        vMap.put("keyword_text_id","Keyword.id");
        vMap.put("ad_id","Ad_Group_Ad.id");
        vMap.put("ad_l_status","Ad_Group_Ad.`local_status`");
        vMap.put("ad_ps_id","Ad_Group_Ad.provider_supplied_id");
        vMap.put("ad_ps_status","Ad_Group_Ad.`provider_status`");
        vMap.put("ad_uploaded_ts","Ad_Group_Ad.uploaded_ts");
        vMap.put("text_ad_id","Text_Ad.id");
        vMap.put("headline","Text_Ad.headline");
        vMap.put("description1","Text_Ad.description1");
        vMap.put("description2","Text_Ad.description2");
        vMap.put("display_url","Text_Ad.displayUrl");
        vMap.put("action_url","Text_Ad.actionUrl");
        vMap.put("ad_group_id","Ad_Group.id");
        vMap.put("ad_group_name","Ad_Group.`name`");
        vMap.put("ad_group_l_status","Ad_Group.`local_status`");
        vMap.put("ad_group_ps_id","Ad_Group.provider_supplied_id");
        vMap.put("ad_group_ps_status","Ad_Group.`provider_status`");
        vMap.put("campaign_id","Traffic_Campaign.id");
        vMap.put("campaign_name","Traffic_Campaign.`name`");
        vMap.put("campaign_ps_id","Traffic_Campaign.provider_supplied_id");
        vMap.put("campaign_l_status","Traffic_Campaign.`local_status`");
        vMap.put("campaign_ps_status","Traffic_Campaign.`provider_status`");
        vMap.put("source_id","Traffic_Source.id");
        vMap.put("source_name","Traffic_Source.`name`");
        vMap.put("traffic_type","Traffic_Source.traffic_type");
        vMap.put("provider_id","Traffic_Provider.id");
        vMap.put("provider_name","Traffic_Provider.`name`");
        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String getField(String iKey)
    {
        return mMap.get(iKey.toLowerCase());
    }
}
