package com.example.nyismaw.communitypolicing.controller.filters;

import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUserPreferences;
import com.example.nyismaw.communitypolicing.model.Enums.Category;
import com.example.nyismaw.communitypolicing.model.Issues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/9/2017.
 */

public class AccidentFilter implements FilterChainInterface {
    private FilterChainInterface nextFilter;

    public AccidentFilter(FilterChainInterface nextFilter) {
        this.nextFilter = nextFilter;
    }

    public AccidentFilter() {
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {
        if (!CurrentUserPreferences.isShowACCIDENTS()) {
            Log.e("Location filter", "noooot wanna see accieents Filtering with location ");
            List<Issues> filteredIssues = new ArrayList();
            for (Issues currentIssues : issues) {
                String categoryOfIssues = currentIssues.getCategoryOfIssues();
            //    Log.e("Location filter", categoryOfIssues + ", " + Category.ACCIDENTS + " filter " + filteredIssues.size() + " and showing accidents " + CurrentUserPreferences.isShowACCIDENTS());

                if (categoryOfIssues != null) {
                    if (!categoryOfIssues.equals(Category.ACCIDENTS.toString())) {
                        Log.e("Location filter", categoryOfIssues + ", " + Category.ACCIDENTS + " filter " + filteredIssues.size() + " and showing accidents " + CurrentUserPreferences.isShowACCIDENTS());
                        filteredIssues.add(currentIssues);
                    }
                }
            }
            if (nextFilter == null)
                return filteredIssues;
            return nextFilter.filter(filteredIssues);
        } else {
            Log.e("Location filter", "wanna see accieents Filtering with location "+issues.size());
            if (nextFilter == null)
                return issues;
            return nextFilter.filter(issues);
        }
    }

}
