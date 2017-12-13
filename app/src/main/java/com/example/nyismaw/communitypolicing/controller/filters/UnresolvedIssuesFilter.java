package com.example.nyismaw.communitypolicing.controller.filters;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUserPreferences;
import com.example.nyismaw.communitypolicing.model.Enums.Category;
import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/13/2017.
 */

public class UnresolvedIssuesFilter implements FilterChainInterface {

    private FilterChainInterface nextFilter;
    private boolean filterOn;


    public UnresolvedIssuesFilter() {
    }

    public UnresolvedIssuesFilter(FilterChainInterface nextFilter) {
        this.nextFilter = nextFilter;
    }

    public UnresolvedIssuesFilter(boolean filterOn) {
        this.filterOn = filterOn;
    }

    public UnresolvedIssuesFilter(FilterChainInterface nextFilter, boolean filterOn) {
        this.nextFilter = nextFilter;
        this.filterOn = filterOn;
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {

        if (this.filterOn) {
            List<Issues> filteredIssues = new ArrayList();

            for (Issues iss : issues) {
                if (iss.isNotificationIsSent() == false)
                    filteredIssues.add(iss);
            }
            if (nextFilter == null)
                return filteredIssues;
            return nextFilter.filter(filteredIssues);
        } else {
            if (nextFilter == null)
                return issues;
            return nextFilter.filter(issues);
        }

    }
}
