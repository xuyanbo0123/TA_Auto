package name.mi.ckm;

import name.mi.micore.model.TrafficProvider;

public interface CKMHandler {
    public TrafficProvider getTrafficProvider() throws CKMException;
}
