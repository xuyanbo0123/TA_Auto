package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.IncidentDescription;
import name.mi.buyer.moss.derivative.MossDrivingRecord.IncidentType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IncidentTypeMap {
    private static final Map<IncidentDescription, IncidentType> mMap;
    private static final IncidentType DEFAULT = IncidentType._CLAIM;

    static
    {
        Map<IncidentDescription, IncidentType> vMap = new HashMap<IncidentDescription, IncidentType>();

        vMap.put(IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_CARELESS_DRIVING, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_CARPOOL_LANE_VIOLATION, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_CHILD_NOT_IN_CAR_SEAT, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_EQUIPMENT, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_VEHICLE_REDUCED_VIOLATION, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_EXCESSIVE_NOISE, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_EXHIBITION_DRIVING, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_EXPIRED_EMISSIONS, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_EXPIRED_REGISTRATION, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_SIGNAL, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_STOP, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_YIELD, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_FOLLOWING_TOO_CLOSE, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_LANE_CHANGE, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_PASSING, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN_ON_RED, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_U_TURN, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_INATTENTIVE_DRIVING, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_NO_INSURANCE, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_NO_SEATBELT, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_PASSING_A_SCHOOL_BUS, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_PASSING_IN_A_NO_PASSING_ZONE, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_PASSING_ON_SHOULDER, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_RAN_A_RED_LIGHT, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_RAN_A_STOP_SIGN, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_RECKLESS_DRIVING, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_RECKLESS_ENDANGERMENT, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_WRONG_WAY_ON_A_ONE_WAY, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DRUG_POSSESSION, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_MINOR_IN_POSSESSION, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_OPEN_CONTAINER, IncidentType._TICKET);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DUI_DWAI, IncidentType._TICKET);


        vMap.put(IncidentDescription._ACCIDENT_COLLIDED_WITH_ANOTHER_CAR, IncidentType._ACCIDENT);
        vMap.put(IncidentDescription._ACCIDENT_HIT_WHILE_STOPPED, IncidentType._ACCIDENT);
        vMap.put(IncidentDescription._ACCIDENT_HIT_BY_ANOTHER_PERSON, IncidentType._ACCIDENT);
        vMap.put(IncidentDescription._ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON, IncidentType._ACCIDENT);
        vMap.put(IncidentDescription._ACCIDENT_SINGLE_CAR_ACCIDENT, IncidentType._ACCIDENT);
        vMap.put(IncidentDescription._ACCIDENT_OTHER, IncidentType._ACCIDENT); //Other accident

        vMap.put(IncidentDescription._CLAIM_ACT_OF_NATURE, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_CAR_FIRE, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_FLOOD_DAMAGE, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_HAIL_DAMAGE, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_HIT_AN_ANIMAL, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_STEREO, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_VEHICLE, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_TOWING_SERVICE, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_VANDALISM, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_WINDSHIELD_REPLACEMENT, IncidentType._CLAIM);
        vMap.put(IncidentDescription._CLAIM_OTHER, IncidentType._CLAIM); //Other claim


        vMap.put(IncidentDescription._DUI, IncidentType._DUI);


        vMap.put(IncidentDescription._SUSPENSION, IncidentType._SUSPENSION);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentType valueOf(IncidentDescription iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
