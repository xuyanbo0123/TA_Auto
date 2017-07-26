package name.mi.source.insurancestep.map;

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

        vMap.put("DUI", IncidentDescription._DUI);

        vMap.put("Hit and Run", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("Hit Pedestrian", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("Moving Car Collision", IncidentDescription._ACCIDENT_COLLIDED_WITH_ANOTHER_CAR);
        vMap.put("Other Accident", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("Property Collision", IncidentDescription._ACCIDENT_OTHER);
        vMap.put("Rear-ended", IncidentDescription._ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON);
        vMap.put("Stopped Car Collision", IncidentDescription._ACCIDENT_HIT_WHILE_STOPPED);


        vMap.put("Child Seat", IncidentDescription._TICKET_CHILD_NOT_IN_CAR_SEAT);
        vMap.put("Careless Driving", IncidentDescription._TICKET_CARELESS_DRIVING);
        vMap.put("Defective Equipment", IncidentDescription._TICKET_DEFECTIVE_EQUIPMENT);
        vMap.put("Failure to Stop", IncidentDescription._TICKET_FAILURE_TO_STOP);
        vMap.put("Safety Violation", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Ignored Traffic Signal", IncidentDescription._TICKET_FAILURE_TO_SIGNAL);
        vMap.put("Illegal U-Turn", IncidentDescription._TICKET_ILLEGAL_U_TURN);
        vMap.put("Seat Belt", IncidentDescription._TICKET_NO_SEATBELT);
        vMap.put("Speeding Under 10 Over", IncidentDescription._TICKET_SPEEDING_LESS_THAN_10_MPH_OVER);
        vMap.put("Speeding 10 to 20 Over", IncidentDescription._TICKET_SPEEDING_MORE_THAN_10_MPH_OVER);
        vMap.put("Speeding 20 Over", IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER);
        vMap.put("Speeding 100 Over", IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER);
        vMap.put("Speeding Other", IncidentDescription._TICKET_SPEEDING_MORE_THAN_20_MPH_OVER);
        vMap.put("No Insurance", IncidentDescription._TICKET_NO_INSURANCE);
        vMap.put("Driving While Suspended/Revoked", IncidentDescription._TICKET_DRIVING_WITHOUT_A_LICENSE);
        vMap.put("Other Ticket", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Drunk Driving - Injury", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Drunk Driving - No Injury", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Hit and Run - Injury", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Hit and Run - No Injury", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);
        vMap.put("Reckless Driving - Injury", IncidentDescription._TICKET_RECKLESS_DRIVING);
        vMap.put("Reckless Driving - No Injury", IncidentDescription._TICKET_RECKLESS_DRIVING);
        vMap.put("Speed Contest/Exhibition", IncidentDescription._TICKET_OTHER_UNLISTED_MOVING_VIOLATION);

        vMap.put("Defective Equipment", IncidentDescription._CLAIM_OTHER);
        vMap.put("Hail Damage", IncidentDescription._CLAIM_HAIL_DAMAGE);
        vMap.put("Hit Animal", IncidentDescription._CLAIM_HIT_AN_ANIMAL);
        vMap.put("Vandalism", IncidentDescription._CLAIM_VANDALISM);
        vMap.put("Vehicle Fire", IncidentDescription._CLAIM_CAR_FIRE);
        vMap.put("Vehicle Theft", IncidentDescription._CLAIM_THEFT_OF_VEHICLE);
        vMap.put("Windshield Damage", IncidentDescription._CLAIM_WINDSHIELD_REPLACEMENT);
        vMap.put("Other Claim", IncidentDescription._CLAIM_OTHER);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentDescription valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
