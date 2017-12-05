package com.example.nyismaw.communitypolicing.controller.filters;

import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class FetchedIssues {
    private static List<Issues> issues = new ArrayList();
    private static List<String> issuesNotified = new ArrayList();
    private static List<String> policeId = new ArrayList();

    public static List<Issues> getIssues() {
        return issues;
    }

    public static List<Issues> getUnResolvedIssues() {
        List<Issues> i = new ArrayList();
        for (Issues iss : FetchedIssues.issues) {
            if (iss.isResolved() == false)
                i.add(iss);

        }
        return i;
    }

    public static void setIssues(List<Issues> issues) {
        FetchedIssues.issues = issues;
    }

    public static List<String> getIssuesNotified() {
        return issuesNotified;
    }

    public static void setIssuesNotified(List<String> issuesNotified) {
        FetchedIssues.issuesNotified = issuesNotified;
    }

    public static List<String> getPoliceId() {
        return policeId;
    }

    public static void setPoliceId(List<String> policeId) {
        FetchedIssues.policeId = policeId;
    }

    public static void addIssue(Issues issue) {

        if (issues != null) {

            for (Issues iss : issues) {

                if (iss.getId().equals(issue.getId()))

                    return;
            }
            issues.add(issue);

        }


    }
}
