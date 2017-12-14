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
                    if(iss.getUserid()==null){
                        filteredIssues.add(iss);
                        continue;
                    }
                    Log.e("e","asdfasdfasdfa  "+(iss.getUserid().getId()!=null  & CurrentUser.user.getId()!=null));
                    if(iss.getUserid().getId()!=null  & CurrentUser.user.getId()!=null){
                         if (iss.getUserid().getId().equals(CurrentUser.user.getId())){
                            Log.e("e","asdfasdfasdfasdfasdf asdfasdfasdfasdfasdf asdfasdfasdf");
                             filteredIssues.add(iss);
                         }


                     }

                }
            }

            issues.remove(filteredIssues);
            Log.e("1","+ ()()()()()()()()()()(   , "+filteredIssues.size());

            if (nextFilter == null)
                return issues;
            return nextFilter.filter(issues);
        } else {
            if (nextFilter == null)
                return issues;
            return nextFilter.filter(issues);
        }

    }
}
