package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.IncidentDescription;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IncidentDescriptionMap
{
    private static final Map<IncidentDescription, String> mMap;
    private static final String DEFAULT = "Loss Claim Not Listed";

    static {
        Map<IncidentDescription, String> vMap = new HashMap<IncidentDescription, String>();

        vMap.put(IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER, "Speeding Under 10 Over");
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER, "Speeding 10 to 20 Over");
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER, "Speeding 20 Over");
        vMap.put(IncidentDescription._TICKET_CARELESS_DRIVING,"Careless Driving");
        vMap.put(IncidentDescription._TICKET_CARPOOL_LANE_VIOLATION,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_CHILD_NOT_IN_CAR_SEAT,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_EQUIPMENT,"Defective Equipment");
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_VEHICLE_REDUCED_VIOLATION,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE,"No License");
        vMap.put(IncidentDescription._TICKET_EXCESSIVE_NOISE,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_EXHIBITION_DRIVING,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE,"Invalid License");
        vMap.put(IncidentDescription._TICKET_EXPIRED_EMISSIONS,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_EXPIRED_REGISTRATION,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL,"Failure To Obey Signal");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_SIGNAL,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_STOP,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_YIELD,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_FOLLOWING_TOO_CLOSE,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_LANE_CHANGE,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_PASSING,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN_ON_RED,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_U_TURN,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_INATTENTIVE_DRIVING,"Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_NO_INSURANCE,"No Insurance");
        vMap.put(IncidentDescription._TICKET_NO_SEATBELT,"No Seatbelt");
        vMap.put(IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_PASSING_A_SCHOOL_BUS, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_PASSING_IN_A_NO_PASSING_ZONE, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_PASSING_ON_SHOULDER, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_RAN_A_RED_LIGHT, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_RAN_A_STOP_SIGN, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_RECKLESS_DRIVING, "Reckless Driving");
        vMap.put(IncidentDescription._TICKET_RECKLESS_ENDANGERMENT, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_WRONG_WAY_ON_A_ONE_WAY, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DRUG_POSSESSION, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_MINOR_IN_POSSESSION, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_OPEN_CONTAINER, "Ticket Violation Not Listed");
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DUI_DWAI, "Ticket Violation Not Listed");

        vMap.put(IncidentDescription._ACCIDENT_COLLIDED_WITH_ANOTHER_CAR, "Not At Fault Accident Not Listed");
        vMap.put(IncidentDescription._ACCIDENT_HIT_WHILE_STOPPED, "Not At Fault Accident Not Listed");
        vMap.put(IncidentDescription._ACCIDENT_HIT_BY_ANOTHER_PERSON, "Other Vehicle Hit Yours");
        vMap.put(IncidentDescription._ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON, "Rear Ended");
        vMap.put(IncidentDescription._ACCIDENT_SINGLE_CAR_ACCIDENT, "Vehicle Damaged Avoiding Accident");
        vMap.put(IncidentDescription._ACCIDENT_OTHER, "Not Listed");

        vMap.put(IncidentDescription._CLAIM_ACT_OF_NATURE,"Loss Claim Not Listed");
        vMap.put(IncidentDescription._CLAIM_CAR_FIRE,"Fire Hail Water Damage");
        vMap.put(IncidentDescription._CLAIM_FLOOD_DAMAGE,"Fire Hail Water Damage");
        vMap.put(IncidentDescription._CLAIM_HAIL_DAMAGE,"Fire Hail Water Damage");
        vMap.put(IncidentDescription._CLAIM_HIT_AN_ANIMAL,"Vehicle Hit Animal");
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_STEREO,"Loss Claim Not Listed");
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_VEHICLE,"Vehicle Stolen");
        vMap.put(IncidentDescription._CLAIM_TOWING_SERVICE,"Loss Claim Not Listed");
        vMap.put(IncidentDescription._CLAIM_VANDALISM,"Vandalism Damage");
        vMap.put(IncidentDescription._CLAIM_WINDSHIELD_REPLACEMENT,"Windshield Damage");
        vMap.put(IncidentDescription._CLAIM_OTHER,"Loss Claim Not Listed");

        vMap.put(IncidentDescription._DUI, "DUI");
        vMap.put(IncidentDescription._SUSPENSION, "Ticket Violation Not Listed");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(IncidentDescription iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
