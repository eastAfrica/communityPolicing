package com.example.nyismaw.communitypolicing.controller.issueConfig;

import android.graphics.Bitmap;

import com.example.nyismaw.communitypolicing.ApiWrapper.FireBaseAPI;
import com.example.nyismaw.communitypolicing.ApiWrapper.ReprotedIssuesInterface;

import java.io.InputStream;
import java.util.List;

/**
 * Created by nyismaw on 12/14/2017.
 */

public class IssueConfigProxy {

    ReprotedIssuesInterface manageReportedIssues;

    public IssueConfigProxy() {
        this.manageReportedIssues = new FireBaseAPI();
    }

    public void reportIssue(Bitmap bitmap, String description, String category, String severity, List<String> vehicles, InputStream stream) {
        manageReportedIssues.reportIssue(bitmap, description, category,
                severity, vehicles,stream);
    }

    public void getReportedIssues() {
        manageReportedIssues.getReportedIssues();
    }
}
