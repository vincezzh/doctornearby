package com.akhaltech.model;

import com.akhaltech.constant.GlobalConstant;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.and;

/**
 * Created by vince on 2015-09-15.
 */
public class DoctorSearch {
    private String doctorId;
    private String name;
    private String gender;
    private String language;
    private String physicianType;
    private String registrationStatus;
    private String location;
    private String postcode;
    private String hospital;
    private int skip = 0;
    private int limit = GlobalConstant.DEFAULT_PAGE_SIZE;

    public Bson getSearchConditions() {
        List<Bson> conditionList = new ArrayList<Bson>();

        if(doctorId != null) {
            Bson condition = eq("_id", doctorId);
            conditionList.add(condition);
        }
        if(name != null) {
            Bson condition = or(
                eq("profile.surname", Pattern.compile(name, Pattern.CASE_INSENSITIVE)),
                eq("profile.givenName", Pattern.compile(name, Pattern.CASE_INSENSITIVE))
            );
            conditionList.add(condition);
        }
        if(gender != null) {
            Bson condition = eq("profile.gender", gender);
            conditionList.add(condition);
        }
        if(language != null) {
            Bson condition = eq("profile.languageList", language);
            conditionList.add(condition);
        }
        if(physicianType != null) {
            Bson condition = eq("specialtyList.type", Pattern.compile(physicianType, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(registrationStatus != null) {
            Bson condition = eq("registration.registrationStatus", Pattern.compile(registrationStatus, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(location != null) {
            Bson condition = eq("location.addressSummary", Pattern.compile(location, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(postcode != null) {
            Bson condition = eq("location.addressSummary", Pattern.compile(postcode, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(hospital != null) {
            Bson condition = eq("privilegeList.hospitalDetail", Pattern.compile(hospital, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }

        Bson allCondition = null;
        if(conditionList.size() > 0) {
            allCondition = and(conditionList);
        }
        return allCondition;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhysicianType() {
        return physicianType;
    }

    public void setPhysicianType(String physicianType) {
        this.physicianType = physicianType;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
