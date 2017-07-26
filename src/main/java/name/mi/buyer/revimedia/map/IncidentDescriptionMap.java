package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.IncidentDescription;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IncidentDescriptionMap
{
    private static final Map<IncidentDescription, String> mMap;
    private static final String DEFAULT = "Other Unlisted Claim";

    static {
        Map<IncidentDescription, String> vMap = new HashMap<IncidentDescription, String>();

        vMap.put(IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER, "Other Unlisted Moving Violation");
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER, "Other Unlisted Moving Violation");
        vMap.put(IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER, "Other Unlisted Moving Violation");

        vMap.put(IncidentDescription._TICKET_CARELESS_DRIVING,"Careless driving");
        vMap.put(IncidentDescription._TICKET_CARPOOL_LANE_VIOLATION,"Carpool lane violation");
        vMap.put(IncidentDescription._TICKET_CHILD_NOT_IN_CAR_SEAT,"Child not in car seat");
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_EQUIPMENT,"Defective Equipment");
        vMap.put(IncidentDescription._TICKET_DEFECTIVE_VEHICLE_REDUCED_VIOLATION,"Defective vehicle (reduced violation)");
        vMap.put(IncidentDescription._TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS,"Driving too fast for conditions");

        vMap.put(IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE,"Driving without a license");
        vMap.put(IncidentDescription._TICKET_EXCESSIVE_NOISE,"Excessive noise");
        vMap.put(IncidentDescription._TICKET_EXHIBITION_DRIVING,"Exhibition driving");
        vMap.put(IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE,"Expired drivers license");
        vMap.put(IncidentDescription._TICKET_EXPIRED_EMISSIONS,"Expired emmissions");
        vMap.put(IncidentDescription._TICKET_EXPIRED_REGISTRATION,"Expired Registration");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL,"Failure to obey traffic signal");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_SIGNAL,"Failure to signal");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_STOP,"Failure to stop");
        vMap.put(IncidentDescription._TICKET_FAILURE_TO_YIELD,"Failure to yield");
        vMap.put(IncidentDescription._TICKET_FOLLOWING_TOO_CLOSE,"Following too close");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_LANE_CHANGE,"Illegal lane change");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_PASSING,"Illegal passing");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN,"Illegal turn");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_TURN_ON_RED,"Illegal turn on red");
        vMap.put(IncidentDescription._TICKET_ILLEGAL_U_TURN,"Illegal U Turn");
        vMap.put(IncidentDescription._TICKET_INATTENTIVE_DRIVING,"Inattentive driving");
        vMap.put(IncidentDescription._TICKET_NO_INSURANCE,"No insurance");
        vMap.put(IncidentDescription._TICKET_NO_SEATBELT,"No seatbelt");
        vMap.put(IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION, "Other Unlisted Moving Violation");
        vMap.put(IncidentDescription._TICKET_PASSING_A_SCHOOL_BUS, "Passing a school bus");
        vMap.put(IncidentDescription._TICKET_PASSING_IN_A_NO_PASSING_ZONE, "Passing in a no-passing zone");
        vMap.put(IncidentDescription._TICKET_PASSING_ON_SHOULDER, "Passing on shoulder");
        vMap.put(IncidentDescription._TICKET_RAN_A_RED_LIGHT, "Ran a red light");
        vMap.put(IncidentDescription._TICKET_RAN_A_STOP_SIGN, "Ran a stop sign");
        vMap.put(IncidentDescription._TICKET_RECKLESS_DRIVING, "Reckless driving");
        vMap.put(IncidentDescription._TICKET_RECKLESS_ENDANGERMENT, "Reckless endangerment");
        vMap.put(IncidentDescription._TICKET_WRONG_WAY_ON_A_ONE_WAY, "Wrong way on a one way");

        vMap.put(IncidentDescription._ACCIDENT_COLLIDED_WITH_ANOTHER_CAR, "Collided with another car");
        vMap.put(IncidentDescription._ACCIDENT_HIT_WHILE_STOPPED, "Hit while stopped");
        vMap.put(IncidentDescription._ACCIDENT_HIT_BY_ANOTHER_PERSON, "Hit by another person");
        vMap.put(IncidentDescription._ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON, "Rearended by another person");
        vMap.put(IncidentDescription._ACCIDENT_SINGLE_CAR_ACCIDENT, "Hit an obstruction. Single car accident");
        vMap.put(IncidentDescription._ACCIDENT_OTHER, "Other Unlisted Accident");  //Other accident

        vMap.put(IncidentDescription._CLAIM_ACT_OF_NATURE,"Act of Nature");
        vMap.put(IncidentDescription._CLAIM_CAR_FIRE,"Car fire");
        vMap.put(IncidentDescription._CLAIM_FLOOD_DAMAGE,"Flood damage");
        vMap.put(IncidentDescription._CLAIM_HAIL_DAMAGE,"Hail damage");
        vMap.put(IncidentDescription._CLAIM_HIT_AN_ANIMAL,"Hit an animal");
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_STEREO,"Theft of stereo");
        vMap.put(IncidentDescription._CLAIM_THEFT_OF_VEHICLE,"Theft of vehicle");
        vMap.put(IncidentDescription._CLAIM_TOWING_SERVICE,"Towing service");
        vMap.put(IncidentDescription._CLAIM_VANDALISM,"Vandalism");
        vMap.put(IncidentDescription._CLAIM_WINDSHIELD_REPLACEMENT,"Windshield Replacement");
        vMap.put(IncidentDescription._CLAIM_OTHER,"Other Unlisted Claim"); //Other claim

        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DRUG_POSSESSION, "Drug possession");
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_MINOR_IN_POSSESSION, "Minor in possession");
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_OPEN_CONTAINER, "Open container");
        vMap.put(IncidentDescription._TICKET_ALCOHOL_RELATED_DUI_DWAI, "DUI/DWAI");
        vMap.put(IncidentDescription._DUI, "DUI/DWAI");
        vMap.put(IncidentDescription._SUSPENSION, "License was suspended");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(IncidentDescription iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
