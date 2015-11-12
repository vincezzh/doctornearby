package com.akhaltech.model;

import com.akhaltech.constant.GlobalConstant;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by vince on 2015-09-15.
 */
public class DoctorSearch {
    private String doctorId;
    private String surname;
    private String givenname;
    private String gender;
    private String language;
    private String physicianType;
    private String registrationStatus;
    private String location;
    private String province;
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
        if(province != null && !"".equals(province.trim())) {
            Bson condition = eq("province", province);
            conditionList.add(condition);
        }
        if(surname != null && !"".equals(surname.trim())) {
            Bson condition = eq("profile.surname", Pattern.compile(surname, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(givenname != null && !"".equals(givenname.trim())) {
            Bson condition = eq("profile.givenName", Pattern.compile(givenname, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(location != null && !"".equals(location.trim())) {
            Bson condition = eq("location.addressSummary", Pattern.compile(location, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(physicianType != null && !"".equals(physicianType.trim())) {
            Bson condition = eq("specialtyList.name", Pattern.compile(physicianType, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(language != null && !"".equals(language.trim())) {
            Bson condition = eq("profile.languageList", language);
            conditionList.add(condition);
        }
        if(gender != null && !"".equals(gender.trim())) {
            Bson condition = eq("profile.gender", gender);
            conditionList.add(condition);
        }
        if(registrationStatus != null && !"".equals(registrationStatus.trim())) {
            Bson condition = eq("registration.registrationStatus", Pattern.compile(registrationStatus, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(postcode != null && !"".equals(postcode.trim())) {
            Bson condition = eq("location.addressSummary", Pattern.compile(postcode, Pattern.CASE_INSENSITIVE));
            conditionList.add(condition);
        }
        if(hospital != null && !"".equals(hospital.trim())) {
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }
}
