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
            Bson condition = eq("profile.id", doctorId);
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

    public String getSearchConditionsSQL() {
        List<String> conditionList = new ArrayList<String>();

        if(doctorId != null) {
            String sql = "select d._id from doctor d where d._id='" + doctorId + "'";
            conditionList.add(sql);
        }
        if(province != null && !"".equals(province.trim())) {
            String sql = "select d._id from doctor d where d.province='" + province.trim() + "'";
            conditionList.add(sql);
        }
        if(surname != null && !"".equals(surname.trim())) {
            String sql = "select d._id from doctor d left join profile p on p.id=d.profile_id where p.surname like ('" + surname.trim() + "%')";
            conditionList.add(sql);
        }
        if(givenname != null && !"".equals(givenname.trim())) {
            String sql = "select d._id from doctor d left join profile p on p.id=d.profile_id where p.given_name like ('" + givenname.trim() + "%')";
            conditionList.add(sql);
        }
        if(location != null && !"".equals(location.trim())) {
            String sql = "select d._id from doctor d left join location l on l.id=d.location_id where l.address_summary like ('%" + location.trim() + "%')";
            conditionList.add(sql);
        }
        if(physicianType != null && !"".equals(physicianType.trim())) {
            String sql = "select d._id from doctor d left join specialty s on s.doctor_id=d._id where s.name like ('" + physicianType.trim() + "%')";
            conditionList.add(sql);
        }
        if(language != null && !"".equals(language.trim())) {
            String sql = "select d._id from doctor d left join profile p on p.id=d.profile_id left join language l on l.profile_id=p.id where l.language = '" + language.trim() + "'";
            conditionList.add(sql);
        }
        if(gender != null && !"".equals(gender.trim())) {
            String sql = "select d._id from doctor d left join profile p on p.id=d.profile_id where p.gender='" + gender.trim() + "'";
            conditionList.add(sql);
        }
        if(registrationStatus != null && !"".equals(registrationStatus.trim())) {
            String sql = "select d._id from doctor d left join registration r on r.id=d.registration_id where r.registration_status like ('" + registrationStatus.trim() + "%')";
            conditionList.add(sql);
        }
        if(postcode != null && !"".equals(postcode.trim())) {
            String sql = "select d._id from doctor d left join location l on l.id=d.location_id where l.address_summary like ('%" + postcode.trim() + "%')";
            conditionList.add(sql);
        }
        if(hospital != null && !"".equals(hospital.trim())) {
            String sql = "select d._id from doctor d left join privilege p on p.doctor_id=d._id where p.hospital_detail like ('%" + hospital.trim() + "%')";
            conditionList.add(sql);
        }

        StringBuffer allCondition = new StringBuffer(" ( ");
        for(int i=0; i<conditionList.size(); i++) {
            String currentAlian = "table" + i;
            String previousAlian = "table" + (i-1);

            if(i == 0) {
                allCondition.append(" select " + currentAlian + "._id from ( ");
                allCondition.append(" " + conditionList.get(i) + " ");
                allCondition.append(" ) " + currentAlian + " ");
            }else {
                allCondition.append(" inner join ( ");
                allCondition.append(" " + conditionList.get(i) + " ");
                allCondition.append(" ) " + currentAlian + " on " + currentAlian + "._id=" + previousAlian + "._id ");
            }
        }
        allCondition.append(" ) ");

        return allCondition.toString();
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
