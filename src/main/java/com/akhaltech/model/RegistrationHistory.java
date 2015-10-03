package com.akhaltech.model;

/**
 * Created by vince on 2015-09-09.
 */
public class RegistrationHistory {
    private String description;
    private String effectiveDate;
    private String fromEffectiveYear;
    private String fromEffectiveMonth;

    public String getFromEffectiveYear() {
        String fromEffectiveYear = "";
        if(effectiveDate != null && effectiveDate.split(" ").length == 3) {
            fromEffectiveYear = effectiveDate.split(" ")[2];
        }
        return fromEffectiveYear;
    }

    public String getFromEffectiveMonth() {
        String fromEffectiveMonth = "";
        if(effectiveDate != null && effectiveDate.split(" ").length == 3) {
            fromEffectiveMonth = effectiveDate.split(" ")[1];
        }
        return fromEffectiveMonth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
