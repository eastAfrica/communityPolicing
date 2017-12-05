package com.example.nyismaw.communitypolicing.ApiWrapper;

import java.util.List;

/**
 * Created by nyismaw on 12/2/2017.
 */

public interface ReprotedIssuesInterface  {
    public void createObject(Object object, String description);
    public List<Object> getReportedIssues();
    public void fireBasePoliceId();
}
