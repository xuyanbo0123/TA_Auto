package name.mi.source.freequotes.map;

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

        vMap.put("Less than 10 MPH over", IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER);
        vMap.put("More than 10 MPH over", IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER);
        vMap.put("More than 20 MPH over", IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER);
        vMap.put("Drug possession", IncidentDescription._TICKET_ALCOHOL_RELATED_DRUG_POSSESSION);
        vMap.put("Minor in possession", IncidentDescription._TICKET_ALCOHOL_RELATED_MINOR_IN_POSSESSION);
        vMap.put("Open container", IncidentDescription._TICKET_ALCOHOL_RELATED_OPEN_CONTAINER);
        vMap.put("DUI/DWAI", IncidentDescription._TICKET_ALCOHOL_RELATED_DUI_DWAI);
        vMap.put("Careless driving", IncidentDescription._TICKET_CARELESS_DRIVING);
        vMap.put("Carpool lane violation", IncidentDescription._TICKET_CARPOOL_LANE_VIOLATION);
        vMap.put("Child not in car seat", IncidentDescription._TICKET_CHILD_NOT_IN_CAR_SEAT);
        vMap.put("Defective Equipment", IncidentDescription._TICKET_DEFECTIVE_EQUIPMENT);
        vMap.put("Defective vehicle (reduced violation)", IncidentDescription._TICKET_DEFECTIVE_VEHICLE_REDUCED_VIOLATION);
        vMap.put("Driving too fast for conditions", IncidentDescription._TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS);
        vMap.put("Driving without a license", IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE);
        vMap.put("Excessive noise", IncidentDescription._TICKET_EXCESSIVE_NOISE);
        vMap.put("Exhibition driving", IncidentDescription._TICKET_EXHIBITION_DRIVING);
        vMap.put("Expired drivers license", IncidentDescription._TICKET_EXPIRED_DRIVERS_LICENSE);
        vMap.put("Expired emissions", IncidentDescription._TICKET_EXPIRED_EMISSIONS);
        vMap.put("Expired Registration", IncidentDescription._TICKET_EXPIRED_REGISTRATION);
        vMap.put("Failure to obey traffic signal", IncidentDescription._TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL);
        vMap.put("Failure to signal", IncidentDescription._TICKET_FAILURE_TO_SIGNAL);
        vMap.put("Failure to stop", IncidentDescription._TICKET_FAILURE_TO_STOP);
        vMap.put("Failure to yield", IncidentDescription._TICKET_FAILURE_TO_YIELD);
        vMap.put("Following too close", IncidentDescription._TICKET_FOLLOWING_TOO_CLOSE);
        vMap.put("Illegal lane change", IncidentDescription._TICKET_ILLEGAL_LANE_CHANGE);
        vMap.put("Illegal passing", IncidentDescription._TICKET_ILLEGAL_PASSING);
        vMap.put("Illegal turn", IncidentDescription._TICKET_ILLEGAL_TURN);
        vMap.put("Illegal turn on red", IncidentDescription._TICKET_ILLEGAL_TURN_ON_RED);
        vMap.put("Illegal U Turn", IncidentDescription._TICKET_ILLEGAL_U_TURN);
        vMap.put("Inattentive driving", IncidentDescription._TICKET_INATTENTIVE_DRIVING);
        vMap.put("No insurance", IncidentDescription._TICKET_NO_INSURANCE);
        vMap.put("No seatbelt", IncidentDescription._TICKET_NO_SEATBELT);
        vMap.put("Other Unlisted Moving Violation", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Passing a school bus", IncidentDescription._TICKET_PASSING_A_SCHOOL_BUS);
        vMap.put("Passing in a no-passing zone", IncidentDescription._TICKET_PASSING_IN_A_NO_PASSING_ZONE);
        vMap.put("Passing on shoulder", IncidentDescription._TICKET_PASSING_ON_SHOULDER);
        vMap.put("Ran a red light", IncidentDescription._TICKET_RAN_A_RED_LIGHT);
        vMap.put("Ran a stop sign", IncidentDescription._TICKET_RAN_A_STOP_SIGN);
        vMap.put("Reckless driving", IncidentDescription._TICKET_RECKLESS_DRIVING);
        vMap.put("Reckless endangerment", IncidentDescription._TICKET_RECKLESS_ENDANGERMENT);
        vMap.put("Wrong way on a one way", IncidentDescription._TICKET_WRONG_WAY_ON_A_ONE_WAY);
        vMap.put("Collided with another car", IncidentDescription._ACCIDENT_COLLIDED_WITH_ANOTHER_CAR);
        vMap.put("Hit while stopped", IncidentDescription._ACCIDENT_HIT_WHILE_STOPPED);
        vMap.put("Hit by another person", IncidentDescription._ACCIDENT_HIT_BY_ANOTHER_PERSON);
        vMap.put("Rearended by another person", IncidentDescription._ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON);
        vMap.put("Hit an obstruction. Single car accident", IncidentDescription._ACCIDENT_SINGLE_CAR_ACCIDENT);
        vMap.put("Accident Other", IncidentDescription._ACCIDENT_OTHER);  //Other accident
        vMap.put("Act of Nature", IncidentDescription._CLAIM_ACT_OF_NATURE);
        vMap.put("Car fire", IncidentDescription._CLAIM_CAR_FIRE);
        vMap.put("Flood damage", IncidentDescription._CLAIM_FLOOD_DAMAGE);
        vMap.put("Hail damage", IncidentDescription._CLAIM_HAIL_DAMAGE);
        vMap.put("Hit an animal", IncidentDescription._CLAIM_HIT_AN_ANIMAL);
        vMap.put("Theft of stereo", IncidentDescription._CLAIM_THEFT_OF_STEREO);
        vMap.put("Theft of vehicle", IncidentDescription._CLAIM_THEFT_OF_VEHICLE);
        vMap.put("Towing service", IncidentDescription._CLAIM_TOWING_SERVICE);
        vMap.put("Vandalism", IncidentDescription._CLAIM_VANDALISM);
        vMap.put("Windshield Replacement", IncidentDescription._CLAIM_WINDSHIELD_REPLACEMENT);
        vMap.put("Claim Other", IncidentDescription._CLAIM_OTHER); //Other claim

        vMap.put("DUI", IncidentDescription._DUI);
        vMap.put("suspension", IncidentDescription._SUSPENSION);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentDescription valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
