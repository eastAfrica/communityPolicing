package com.example.nyismaw.communitypolicing.model.Enums;

/**
 * Created by jarigye on 12/4/2017.
 */

public enum VehicleType {
    SEDAN("Sedan"),
    FOUR_WHEEL_TRUCK("Four wheel truck"),
    MOTORCYCLE("Motorcycle"),
    BICYCLE("Biycle"),
    FAMILYVAN("Family van"),
    PICKUP("Pick up"),
    OTHER("Other") ;

    private String _text;

    VehicleType (String sev)
    {
        _text = sev;
    }

    public String toString()
    {
        return _text;
    }

    public static VehicleType fromString(String stringValue)
    {
        for (VehicleType option : VehicleType.values())
        {
            if (stringValue.equalsIgnoreCase(option._text))
                return option;
        }
        if (stringValue.equalsIgnoreCase("Sedan"))
            return SEDAN;
        if (stringValue.equalsIgnoreCase("Four wheel truck"))
            return FOUR_WHEEL_TRUCK;
        if (stringValue.equalsIgnoreCase("Motorcycle"))
            return MOTORCYCLE;
        if (stringValue.equalsIgnoreCase("Bicycle"))
            return BICYCLE;
        if (stringValue.equalsIgnoreCase("Family van"))
            return FAMILYVAN;
        if (stringValue.equalsIgnoreCase("Pickup"))
            return PICKUP;

        return OTHER;
    }

    public static boolean isValid(String strValue)
    {
        if (fromString(strValue) != OTHER)
            return true;
        return false;
    }
}
