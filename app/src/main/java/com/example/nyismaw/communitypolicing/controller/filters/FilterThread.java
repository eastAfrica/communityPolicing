package com.example.nyismaw.communitypolicing.controller.filters;

import com.example.nyismaw.communitypolicing.model.Issues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyismaw on 12/13/2017.
 */

public class FilterThread implements Runnable {

    private FilterChainInterface filterChainInterface;
    private List<Issues> issues = null;
    private List<Issues> issuesToBeFiltered;


    public FilterThread(FilterChainInterface filterChainInterface, List<Issues> issuesToBeFiltered) {
        this.filterChainInterface = filterChainInterface;
        this.issuesToBeFiltered = issuesToBeFiltered;
    }

    @Override
    public void run() {

        issues = filterChainInterface.filter(issuesToBeFiltered);
    }

    public List<Issues> getIssues() {
        return issues;
    }

    public void setIssues(List<Issues> issues) {
        this.issues = issues;
    }
}
