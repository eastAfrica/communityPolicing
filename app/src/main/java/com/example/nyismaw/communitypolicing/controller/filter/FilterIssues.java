package com.example.nyismaw.communitypolicing.controller.filter;

import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.List;

/**
 * Created by nyismaw on 11/29/2017.
 */

public class FilterIssues {

    public Issues filterIssueById(String id) {

        List<Issues> issues = FetchedIssues.getIssues();
        for (Issues iss : issues) {
            if (iss.getId().equals(id))
                return iss;
        }

        return null;
    }
}
