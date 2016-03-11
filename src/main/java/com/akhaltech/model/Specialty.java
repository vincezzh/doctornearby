package com.akhaltech.model;

/**
 * Created by vince on 2015-09-08.
 */
public class Specialty {
    private String name;
    private String issueOn;
    private String type;
    private String doctorId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssueOn() {
        return issueOn;
    }

    public void setIssueOn(String issueOn) {
        this.issueOn = issueOn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
