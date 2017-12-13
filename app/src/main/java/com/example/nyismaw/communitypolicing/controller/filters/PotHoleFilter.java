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

public class PotHoleFilter implements FilterChainInterface {

    private FilterChainInterface nextFilter;

    public PotHoleFilter(FilterChainInterface nextFilter) {
        this.nextFilter = nextFilter;
    }

    public PotHoleFilter() {
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {
        if (!CurrentUserPreferences.isShowPOTHOLES()) {

            List<Issues> filteredIssues = new ArrayList();
            for (Issues currentIssues : issues) {
                String categoryOfIssues = currentIssues.getCategoryOfIssues();
                if (categoryOfIssues != null) {
                    if (!categoryOfIssues.equals(Category.POTHOLES.toString())) {
                        filteredIssues.add(currentIssues);
                    }
                }
            }

            if (nextFilter == null){
                Log.e("pot hole filter ","when off  returning "+issues.size());
                return filteredIssues;
            }

            return nextFilter.filter(filteredIssues);
        } else {
            if (nextFilter == null){
                Log.e("pot hole filter ","when on  returning "+issues.size());
                return issues;
            }

            return nextFilter.filter(issues);
        }
    }


}
