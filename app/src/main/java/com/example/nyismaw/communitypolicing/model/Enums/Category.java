package com.example.nyismaw.communitypolicing.model.Enums;

/**
 * Created by jarigye on 12/4/2017.
 */

public enum Category {

    ACCIDENTS("Accidents"),
    POTHOLES("Potholes"),
    BLOCKED_ROADS("Blocked Roads"),
    FALLEN_TREES("Fallen Trees"),
    OTHER("Other") ;

    private String _text;

    Category (String cat)
    {
        _text = cat;
    }

    public String toString()
    {
        return _text;
    }

    public static Category fromString(String stringValue)
    {
        for (Category option : Category.values())
        {
            if (stringValue.equalsIgnoreCase(option._text))
                return option;
        }
        if (stringValue.equalsIgnoreCase("Accidents"))
            return ACCIDENTS;
        if (stringValue.equalsIgnoreCase("Potholes"))
            return POTHOLES;
        if (stringValue.equalsIgnoreCase("Blocked Roads"))
            return BLOCKED_ROADS;
        if (stringValue.equalsIgnoreCase("Fallen Trees"))
            return FALLEN_TREES;

        return OTHER;
    }

    public static boolean isValid(String strValue)
    {
        if (fromString(strValue) != OTHER)
            return true;
        return false;
    }
}
