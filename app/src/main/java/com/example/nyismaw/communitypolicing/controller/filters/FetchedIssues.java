package com.example.nyismaw.communitypolicing.controller.filters;

import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class FetchedIssues {
    private static List<Issues> issues = Collections.synchronizedList(new ArrayList<Issues>());
    // private static List<String> issuesNotified = Collections.synchronizedList(new ArrayList<String>());
    private static List<String> policeId = Collections.synchronizedList(new ArrayList<String>());

    public static List<Issues> getIssues() {
        return issues;
    }

    public static List<Issues> getUnResolvedIssues(List<Issues> issues) {
        List<Issues> i = new ArrayList();
        for (Issues iss : issues) {
            if (iss.isResolved() == false)
                i.add(iss);

        }
        return i;
    }

    public static List<Issues> getUnNotifiedIssues(List<Issues> issues) {
        List<Issues> i = new ArrayList();
        for (Issues iss : issues) {
            if (iss.isNotificationIsSent() == false)
                i.add(iss);

        }
        return i;
    }

    public static List<Issues> getIssuesbyUserId(List<Issues> issues) {
        List<Issues> i = new ArrayList();
        for (Issues iss : issues) {
            if (CurrentUser.user != null) {
                if (iss.getUserid().getId().equals(CurrentUser.user.getId()))
                    i.add(iss);

            }


        }
        return i;
    }

    public static void setIssues(List<Issues> issues) {
        FetchedIssues.issues = issues;
    }


    public static List<String> getPoliceId() {
        return policeId;
    }

    public static void setPoliceId(List<String> policeId) {
        Log.e("alrnate", "alternate now ");
        FetchedIssues.policeId = policeId;
    }

    public static void addIssue(Issues issue) {
        synchronized (issues) {
            if (issues != null) {
                for (Issues iss : issues) {
                    if (iss.getId().equals(issue.getId()))
                        return;
                }
                issues.add(issue);
            }
        }
    }

    public static void removeIssue(Issues issue) {
        synchronized (issues) {
         //   Log.e("CHile is removed", "////////////////////////////////////// child removed");
            Issues issueToBeRemoved = null;
            for (Issues iss : issues) {
                if (iss.getId().equals(issue.getId())) {

                    Log.e("CHile is removed", ".............................................");

                    issueToBeRemoved = (iss);
                }
            }
            issues.remove(issueToBeRemoved);
        }
    }

    public static Issues getIssueById(String id) {

        List<Issues> issues = FetchedIssues.getIssues();
        for (Issues iss : issues) {
            if (iss.getId().equals(id))
                return iss;
        }

        return null;
    }
}
