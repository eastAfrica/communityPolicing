package com.example.nyismaw.communitypolicing.controller.filters;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUserPreferences;
import com.example.nyismaw.communitypolicing.model.Enums.Category;
import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/9/2017.
 */

public class FallenTressFilter extends FilterAbstractClass {

    private FilterAbstractClass nextFilter;

    public  FallenTressFilter (){

    }


    public FallenTressFilter(FilterAbstractClass nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {
        if (!CurrentUserPreferences.isShowFALLEN_TREES()) {
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

    @Override
    public void setNextPipe(FilterAbstractClass filterPipeInterface) {
        this.nextFilter= filterPipeInterface;
    }
}
