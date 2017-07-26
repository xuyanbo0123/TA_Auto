package name.mi.auto.enumerate;

public enum IncidentDescription {
    _TICKET_SPEEDING_LESS_THAN_10_MPH_OVER("Less than 10 MPH over"),
    _TICKET_SPEEDING_MORE_THAN_10_MPH_OVER("More than 10 MPH over"),
    _TICKET_SPEEDING_MORE_THAN_20_MPH_OVER("More than 20 MPH over"),
    _TICKET_ALCOHOL_RELATED_DRUG_POSSESSION("Drug possession"),
    _TICKET_ALCOHOL_RELATED_MINOR_IN_POSSESSION("Minor in possession"),
    _TICKET_ALCOHOL_RELATED_OPEN_CONTAINER("Open container"),
    _TICKET_ALCOHOL_RELATED_DUI_DWAI("DUI/DWAI"),
    _TICKET_CARELESS_DRIVING("Careless driving"),
    _TICKET_CARPOOL_LANE_VIOLATION("Carpool lane violation"),
    _TICKET_CHILD_NOT_IN_CAR_SEAT("Child not in car seat"),
    _TICKET_DEFECTIVE_EQUIPMENT("Defective Equipment"),
    _TICKET_DEFECTIVE_VEHICLE_REDUCED_VIOLATION("Defective vehicle (reduced violation)"),
    _TICKET_DRIVING_TOO_FAST_FOR_CONDITIONS("Driving too fast for conditions"),
    _TICKET_DRIVING_WITHOUT_A_LICENSE("Driving without a license"),
    _TICKET_EXCESSIVE_NOISE("Excessive noise"),
    _TICKET_EXHIBITION_DRIVING("Exhibition driving"),
    _TICKET_EXPIRED_DRIVERS_LICENSE("Expired drivers license"),
    _TICKET_EXPIRED_EMISSIONS("Expired emissions"),
    _TICKET_EXPIRED_REGISTRATION("Expired Registration"),
    _TICKET_FAILURE_TO_OBEY_TRAFFIC_SIGNAL("Failure to obey traffic signal"),
    _TICKET_FAILURE_TO_SIGNAL("Failure to signal"),
    _TICKET_FAILURE_TO_STOP("Failure to stop"),
    _TICKET_FAILURE_TO_YIELD("Failure to yield"),
    _TICKET_FOLLOWING_TOO_CLOSE("Following too close"),
    _TICKET_ILLEGAL_LANE_CHANGE("Illegal lane change"),
    _TICKET_ILLEGAL_PASSING("Illegal passing"),
    _TICKET_ILLEGAL_TURN("Illegal turn"),
    _TICKET_ILLEGAL_TURN_ON_RED("Illegal turn on red"),
    _TICKET_ILLEGAL_U_TURN("Illegal U Turn"),
    _TICKET_INATTENTIVE_DRIVING("Inattentive driving"),
    _TICKET_NO_INSURANCE("No insurance"),
    _TICKET_NO_SEATBELT("No seatbelt"),
    _TICKET_OTHER_UNLISTED_MOVING_VIOLATION("Other Unlisted Moving Violation"),
    _TICKET_PASSING_A_SCHOOL_BUS("Passing a school bus"),
    _TICKET_PASSING_IN_A_NO_PASSING_ZONE("Passing in a no-passing zone"),
    _TICKET_PASSING_ON_SHOULDER("Passing on shoulder"),
    _TICKET_RAN_A_RED_LIGHT("Ran a red light"),
    _TICKET_RAN_A_STOP_SIGN("Ran a stop sign"),
    _TICKET_RECKLESS_DRIVING("Reckless driving"),
    _TICKET_RECKLESS_ENDANGERMENT("Reckless endangerment"),
    _TICKET_WRONG_WAY_ON_A_ONE_WAY("Wrong way on a one way"),
    _ACCIDENT_COLLIDED_WITH_ANOTHER_CAR("Collided with another car"),
    _ACCIDENT_HIT_WHILE_STOPPED("Hit while stopped"),
    _ACCIDENT_HIT_BY_ANOTHER_PERSON("Hit by another person"),
    _ACCIDENT_REAR_ENDED_BY_ANOTHER_PERSON("Rearended by another person"),
    _ACCIDENT_SINGLE_CAR_ACCIDENT("Hit an obstruction. Single car accident"),
    _ACCIDENT_OTHER("Other"),  //Other accident
    _CLAIM_ACT_OF_NATURE("Act of Nature"),
    _CLAIM_CAR_FIRE("Car fire"),
    _CLAIM_FLOOD_DAMAGE("Flood damage"),
    _CLAIM_HAIL_DAMAGE("Hail damage"),
    _CLAIM_HIT_AN_ANIMAL("Hit an animal"),
    _CLAIM_THEFT_OF_STEREO("Theft of stereo"),
    _CLAIM_THEFT_OF_VEHICLE("Theft of vehicle"),
    _CLAIM_TOWING_SERVICE("Towing service"),
    _CLAIM_VANDALISM("Vandalism"),
    _CLAIM_WINDSHIELD_REPLACEMENT("Windshield Replacement"),
    _CLAIM_OTHER("Other"), //Other claim

    _DUI("DUI"),
    _SUSPENSION("suspension");

    private String mValueName;

    private IncidentDescription(String iValueName)
    {
        mValueName = iValueName;
    }

    public String getValueName()
    {
        return mValueName;
    }

    public static IncidentDescription getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
