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

public class FallenTressFilter implements FilterChainInterface {

    private FilterChainInterface nextFilter;

    public FallenTressFilter() {

    }


    public FallenTressFilter(FilterChainInterface nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {
        if (!CurrentUserPreferences.isShowFALLEN_TREES()) {
        //    Log.e("pot hole filter"," plot !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! filter beofre "+issues.size());

            List<Issues> filteredIssues = new ArrayList();
            for (Issues currentIssues : issues) {
                String categoryOfIssues = currentIssues.getCategoryOfIssues();
                if (categoryOfIssues != null) {
                    if (!categoryOfIssues.equals(Category.FALLEN_TREES.toString())) {
                        filteredIssues.add(currentIssues);
                    }
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
