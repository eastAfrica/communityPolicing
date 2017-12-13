package com.example.nyismaw.communitypolicing.controller.filters;

import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUser;
import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/13/2017.
 */

public class FIlterbyUserId implements FilterChainInterface {


    private FilterChainInterface nextFilter;
    private boolean filterOn;


    public FIlterbyUserId() {
    }

    public FIlterbyUserId(FilterChainInterface nextFilter) {
        this.nextFilter = nextFilter;
    }

    public FIlterbyUserId(boolean filterOn) {
        this.filterOn = filterOn;
    }

    public FIlterbyUserId(FilterChainInterface nextFilter, boolean filterOn) {
        this.nextFilter = nextFilter;
        this.filterOn = filterOn;
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {


        if (this.filterOn) {
            List<Issues> filteredIssues = new ArrayList();

            for (Issues iss : issues) {
                if (CurrentUser.user != null) {
                    if(iss.getUserid().getId()==null){
                        filteredIssues.add(iss);
                        continue;
                    }

                    if (!iss.getUserid().getId().equals(CurrentUser.user.getId()))
                        filteredIssues.add(iss);

                }
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
