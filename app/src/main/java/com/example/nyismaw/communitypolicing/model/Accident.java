package com.example.nyismaw.communitypolicing.model;

import java.util.List;

/**
 * Created by nyismaw on 11/24/2017.
 */

public class Accident {

    private String id;
    private String severity;
    private List<String> vehicles;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }
}
