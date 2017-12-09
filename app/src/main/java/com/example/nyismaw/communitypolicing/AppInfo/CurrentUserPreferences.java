package com.example.nyismaw.communitypolicing.AppInfo;

/**
 * Created by nyismaw on 12/9/2017.
 */

public class CurrentUserPreferences {
    private static boolean showACCIDENTS;
    private static boolean showPOTHOLES;
    private static boolean showFALLEN_TREES;
    private static boolean showBlockedRoads;
    private static boolean showOTHER;
    private static boolean soundNotification;
    private static boolean vibrateNotification;
    private static boolean notifyMeofIssuesReproted;
    private static boolean notifyMeofIssuesResolved;
    private static double notificationDistance;

    public static boolean isShowACCIDENTS() {
        return showACCIDENTS;
    }

    public static void setShowACCIDENTS(boolean showACCIDENTS) {
        CurrentUserPreferences.showACCIDENTS = showACCIDENTS;
    }

    public static boolean isShowPOTHOLES() {
        return showPOTHOLES;
    }

    public static void setShowPOTHOLES(boolean showPOTHOLES) {
        CurrentUserPreferences.showPOTHOLES = showPOTHOLES;
    }

    public static boolean isShowFALLEN_TREES() {
        return showFALLEN_TREES;
    }

    public static void setShowFALLEN_TREES(boolean showFALLEN_TREES) {
        CurrentUserPreferences.showFALLEN_TREES = showFALLEN_TREES;
    }

    public static boolean isShowOTHER() {
        return showOTHER;
    }

    public static void setShowOTHER(boolean showOTHER) {
        CurrentUserPreferences.showOTHER = showOTHER;
    }

    public static boolean isSoundNotification() {
        return soundNotification;
    }

    public static void setSoundNotification(boolean soundNotification) {
        CurrentUserPreferences.soundNotification = soundNotification;
    }

    public static boolean isVibrateNotification() {
        return vibrateNotification;
    }

    public static void setVibrateNotification(boolean vibrateNotification) {
        CurrentUserPreferences.vibrateNotification = vibrateNotification;
    }

    public static boolean isNotifyMeofIssuesReproted() {
        return notifyMeofIssuesReproted;
    }

    public static void setNotifyMeofIssuesReproted(boolean notifyMeofIssuesReproted) {
        CurrentUserPreferences.notifyMeofIssuesReproted = notifyMeofIssuesReproted;
    }

    public static boolean isNotifyMeofIssuesResolved() {
        return notifyMeofIssuesResolved;
    }

    public static void setNotifyMeofIssuesResolved(boolean notifyMeofIssuesResolved) {
        CurrentUserPreferences.notifyMeofIssuesResolved = notifyMeofIssuesResolved;
    }

    public static double getNotificationDistance() {
        return notificationDistance;
    }

    public static void setNotificationDistance(double notificationDistance) {
        CurrentUserPreferences.notificationDistance = notificationDistance;
    }

    public static boolean isShowBlockedRoads() {
        return showBlockedRoads;
    }

    public static void setShowBlockedRoads(boolean showBlockedRoads) {
        CurrentUserPreferences.showBlockedRoads = showBlockedRoads;
    }
}
