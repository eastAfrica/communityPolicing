package com.example.nyismaw.communitypolicing.controller.filters;

import android.util.Log;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUserPreferences;
import com.example.nyismaw.communitypolicing.model.Enums.Category;
import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/9/2017.
 */

public class BlockedRoadsFilter implements FilterPipeInterface {

    private FilterPipeInterface nextFilter;
    public BlockedRoadsFilter() {

    }

    public BlockedRoadsFilter(FilterPipeInterface nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {
        if (!CurrentUserPreferences.isShowBlockedRoads()) {
            List<Issues> filteredIssues = new ArrayList();
            for (Issues currentIssues : issues) {
                String categoryOfIssues = currentIssues.getCategoryOfIssues();
                if (categoryOfIssues != null) {
                    if (!categoryOfIssues.equals(Category.BLOCKED_ROADS)) {
                        filteredIssues.add(currentIssues);
                    }
                }
            }

            Log.e("Location filter","Blocked road filter is  "+filteredIssues.size());
            if (nextFilter == null)
                return filteredIssues;
            return nextFilter.filter(filteredIssues);
        } else {
            if (nextFilter == null)
                return issues;
            return nextFilter.filter(issues);
        }
    }

    @Override
    public void setNextPipe(FilterPipeInterface filterPipeInterface) {
        this.nextFilter= filterPipeInterface;
    }
}

