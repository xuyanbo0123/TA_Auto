package name.mi.source.ftq.map;

import name.mi.auto.enumerate.IncidentDescription;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IncidentDescriptionMap {
    private static final Map<String, IncidentDescription> mMap;
    private static final IncidentDescription DEFAULT = null;

    static
    {
        Map<String, IncidentDescription> vMap = new HashMap<>();

        vMap.put("At Fault (Less than $500 damage)", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("At Fault (Less than $750 damage)", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("At Fault (Less than State Min. damage)", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("At Fault", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("At Fault (Bodily Injury)", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("Not At Fault", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("Other", IncidentDescription._ACCIDENT_OTHER);

        vMap.put("Comp Loss Claim", IncidentDescription._CLAIM_THEFT_OF_STEREO);
        vMap.put("Comp Loss Claim (Hail/Animal/Falling Object)", IncidentDescription._CLAIM_HAIL_DAMAGE);
        vMap.put("Comp Loss Claim (Less than $500 damage)", IncidentDescription._CLAIM_OTHER);
        vMap.put("Personal Injury Protection (PIP) Claim", IncidentDescription._CLAIM_OTHER);
        vMap.put("Other", IncidentDescription._CLAIM_OTHER);

        vMap.put("DUI Alcohol", IncidentDescription._DUI);
        vMap.put("DUI Narcotics", IncidentDescription._DUI);
        vMap.put("DUI Other", IncidentDescription._DUI);

        vMap.put("Careless Driving", IncidentDescription._TICKET_CARELESS_DRIVING);
        vMap.put("Driving on Wrong Side of Road", IncidentDescription._TICKET_WRONG_WAY_ON_A_ONE_WAY);
        vMap.put("Failure to Control Vehicle", IncidentDescription._TICKET_FAILURE_TO_STOP);
        vMap.put("Failure to Yield", IncidentDescription._TICKET_FAILURE_TO_YIELD);
        vMap.put("Following too Close", IncidentDescription._TICKET_FOLLOWING_TOO_CLOSE);
        vMap.put("Illegal Pass", IncidentDescription._TICKET_ILLEGAL_PASSING);
        vMap.put("Illegal Turn", IncidentDescription._TICKET_ILLEGAL_TURN);
        vMap.put("Impeding Traffic", IncidentDescription._TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL);
        vMap.put("Passed a Stopped School Bus", IncidentDescription._TICKET_PASSING_A_SCHOOL_BUS);
        vMap.put("Ran a Red Light", IncidentDescription._TICKET_RAN_A_RED_LIGHT);
        vMap.put("Ran a Stop Sign", IncidentDescription._TICKET_RAN_A_STOP_SIGN);
        vMap.put("Reckless Driving", IncidentDescription._TICKET_RECKLESS_DRIVING);
        vMap.put("Too Fast for the Conditions", IncidentDescription._TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS);
        vMap.put("Wrong Way on 1 Way Street", IncidentDescription._TICKET_WRONG_WAY_ON_A_ONE_WAY);

        vMap.put("Alcohol Possession", IncidentDescription._TICKET_ALCOHOL_RELATED_DRUG_POSSESSION);
        vMap.put("Drag Racing", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Driving w/out Insurance", IncidentDescription._TICKET_NO_INSURANCE);
        vMap.put("Driving w/out License", IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE);
        vMap.put("Driving w/out License in Possession", IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE);
        vMap.put("Driving w/Revoked License", IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE);
        vMap.put("Driving w/Suspended License", IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE);
        vMap.put("Eluding a Police Officer", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Felony Involving a Vehicle", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Hit and Run/Failure to Stop at Accident Scene", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Homicide/Manslaughter w/Vehicle", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Obstructed View", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Poor Maintenance", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Revoked License", IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE);
        vMap.put("Seatbelt Violation", IncidentDescription._TICKET_NO_SEATBELT);
        vMap.put("Test Refusal", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Unsafe Operation", IncidentDescription._TICKET_RECKLESS_DRIVING);
        vMap.put("Unsatisfied Judgement", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Unverifiable Driving Record", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Other", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);

        vMap.put("1-10 MPH Over Limit", IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER);
        vMap.put("1-10 MPH Over Limit (in 35-55 Zone)", IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER);
        vMap.put("1-10 MPH Over Limit (in School Zone)", IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER);
        vMap.put("10-20 MPH Over Limit", IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER);
        vMap.put("10-20 MPH Over Limit (in School Zone)", IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER);
        vMap.put("20-30 MPH Over Limit", IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER);
        vMap.put("30+ MPH Over Limit", IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentDescription valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
