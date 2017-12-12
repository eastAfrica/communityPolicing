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

public class AccidentFilter extends FilterAbstractClass  {



    private FilterAbstractClass nextFilter;

    public AccidentFilter(FilterAbstractClass nextFilter) {
        this.nextFilter = nextFilter;
        try {
            pos.connect(nextFilter.pis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public AccidentFilter() {

    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public List<Issues> filter(List<Issues> issues) {
        if (!CurrentUserPreferences.isShowACCIDENTS()) {
            List<Issues> filteredIssues = new ArrayList();
            for (Issues currentIssues : issues) {
                String categoryOfIssues = currentIssues.getCategoryOfIssues();
                if (categoryOfIssues != null) {
                    if (!categoryOfIssues.equals(Category.ACCIDENTS)) {



                        filteredIssues.add(currentIssues);
                    }
                }
            }

            Log.e("Location filter","Accident filter "+filteredIssues.size());
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
        this.nextFilter = filterPipeInterface;
    }
}
