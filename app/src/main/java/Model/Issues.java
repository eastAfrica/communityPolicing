package Model;

import android.location.Location;

/**
 * Created by nyismaw on 11/24/2017.
 */

public class Issues {
    private String id;
    private String txt;
    private myLocation location;
    private User userid;
    private String categoryOfIssues;
    private Accident accident;
    private String details;
    private String imageId;
    public Issues(){


    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public myLocation getLocation() {
        return location;
    }

    public void setLocation(myLocation location) {
        this.location = location;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public String getCategoryOfIssues() {
        return categoryOfIssues;
    }

    public void setCategoryOfIssues(String categoryOfIssues) {
        this.categoryOfIssues = categoryOfIssues;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}