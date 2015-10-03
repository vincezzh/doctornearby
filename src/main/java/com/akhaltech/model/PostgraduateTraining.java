package com.akhaltech.model;

/**
 * Created by vince on 2015-09-09.
 */
public class PostgraduateTraining {
    private String type;
    private String discipline;
    private String medicalSchool;
    private String from;
    private String to;
    private String fromYear;
    private String fromMonth;

    public String getFromYear() {
        String fromYear = "";
        if(from != null && from.split(" ").length == 3) {
            fromYear = from.split(" ")[2];
        }
        return fromYear;
    }

    public String getFromMonth() {
        String fromMonth = "";
        if(from != null && from.split(" ").length == 3) {
            fromMonth = from.split(" ")[1];
        }
        return fromMonth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getMedicalSchool() {
        return medicalSchool;
    }

    public void setMedicalSchool(String medicalSchool) {
        this.medicalSchool = medicalSchool;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
