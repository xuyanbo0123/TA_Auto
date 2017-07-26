package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.IncidentDescription;
import name.mi.buyer.webjuice.derivative.WebJuiceIncident.IncidentType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IncidentTypeMap {
    private static final Map<IncidentDescription, IncidentType> mMap;
    private static final IncidentType DEFAULT = IncidentType.Claim;

    static
    {
        Map<IncidentDescription, IncidentType> vMap = new HashMap<IncidentDescription, IncidentType>();

        vMap.put(IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DRUG_POSSESSION, IncidentType.Violation);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_MINOR_IN_POSSESSION, IncidentType.Violation);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_OPEN_CONTAINER, IncidentType.Violation);
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DUI_DWAI, IncidentType.Violation);
        vMap.put(IncidentDescription._TICKET_CARELESS_DRIVING, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_CARPOOL_LANE_VIOLATION, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_CHILD_NOT_IN_CAR_SEAT, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_EQUIPMENT, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_VEHICLE_REDUCED_VIOLATION, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_EXCESSIVE_NOISE, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_EXHIBITION_DRIVING, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_EXPIRED_EMISSIONS, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_EXPIRED_REGISTRATION, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_SIGNAL, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_STOP, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_YIELD, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_FOLLOWING_TOO_CLOSE, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_LANE_CHANGE, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_PASSING, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN_ON_RED, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_ILLEGAL_U_TURN, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_INATTENTIVE_DRIVING, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_NO_INSURANCE, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_NO_SEATBELT, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_PASSING_A_SCHOOL_BUS, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_PASSING_IN_A_NO_PASSING_ZONE, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_PASSING_ON_SHOULDER, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_RAN_A_RED_LIGHT, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_RAN_A_STOP_SIGN, IncidentType.Ticket);
        vMap.put(IncidentDescription._TICKET_RECKLESS_DRIVING, IncidentType.Violation);
        vMap.put(IncidentDescription._TICKET_RECKLESS_ENDANGERMENT, IncidentType.Violation);
        vMap.put(IncidentDescription._TICKET_WRONG_WAY_ON_A_ONE_WAY, IncidentType.Ticket);
        vMap.put(IncidentDescription._ACCIDENT_COLLIDED_WITH_ANOTHER_CAR, IncidentType.Accident);
        vMap.put(IncidentDescription._ACCIDENT_HIT_WHILE_STOPPED, IncidentType.Accident);
        vMap.put(IncidentDescription._ACCIDENT_HIT_BY_ANOTHER_PERSON, IncidentType.Accident);
        vMap.put(IncidentDescription._ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON, IncidentType.Accident);
        vMap.put(IncidentDescription._ACCIDENT_SINGLE_CAR_ACCIDENT, IncidentType.Accident);
        vMap.put(IncidentDescription._ACCIDENT_OTHER, IncidentType.Accident); //Other accident
        vMap.put(IncidentDescription._CLAIM_ACT_OF_NATURE, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_CAR_FIRE, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_FLOOD_DAMAGE, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_HAIL_DAMAGE, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_HIT_AN_ANIMAL, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_STEREO, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_VEHICLE, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_TOWING_SERVICE, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_VANDALISM, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_WINDSHIELD_REPLACEMENT, IncidentType.Claim);
        vMap.put(IncidentDescription._CLAIM_OTHER, IncidentType.Claim); //Other claim

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentType valueOf(IncidentDescription iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
