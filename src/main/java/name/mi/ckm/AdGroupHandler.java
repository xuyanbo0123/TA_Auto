package name.mi.ckm;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.model.AdGroup;

import java.util.List;

public interface AdGroupHandler extends CKMHandler {
    public List<AdGroup> uploadAdGroups(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, AdGroup... iAdGroups) throws Exception;

    public List<AdGroup> downloadAdGroups(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws Exception;
}
