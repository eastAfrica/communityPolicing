package com.example.nyismaw.communitypolicing.controller.filters;

import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 11/28/2017.
 */

public class FetchedIssues {
    private static List<Issues> issues= new ArrayList();

    public static List<Issues> getIssues() {
        return issues;
    }

    public static void setIssues(List<Issues> issues) {
        FetchedIssues.issues = issues;
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
