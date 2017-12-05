package com.example.nyismaw.communitypolicing.model.Enums;

/**
 * Created by jarigye on 12/4/2017.
 */

public enum Severity {
    CRITICAL("Critical"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("low"),
    OTHER("Other") ;

    private String _text;

    Severity (String sev)
    {
        _text = sev;
    }

    public String toString()
    {
        return _text;
    }

    public static Severity fromString(String stringValue)
    {
        for (Severity option : Severity.values())
        {
            if (stringValue.equalsIgnoreCase(option._text))
                return option;
        }
        if (stringValue.equalsIgnoreCase("Critical"))
            return CRITICAL;
        if (stringValue.equalsIgnoreCase("High"))
            return HIGH;
        if (stringValue.equalsIgnoreCase("Medium"))
            return MEDIUM;
        if (stringValue.equalsIgnoreCase("Low"))
            return LOW;

        return OTHER;
    }

    public static boolean isValid(String strValue)
    {
        if (fromString(strValue) != OTHER)
            return true;
        return false;
    }
}
