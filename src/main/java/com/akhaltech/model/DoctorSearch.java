package com.akhaltech.model;

import com.akhaltech.constant.GlobalConstant;

import java.util.ArrayList;
import java.util.List;

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
    private String phoneNumber;
    private String address;
    private int skip = 0;
    private int limit = GlobalConstant.DEFAULT_PAGE_SIZE;

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
        if(address != null && !"".equals(address.trim())) {
            String sql = "select d._id from doctor d left join location l on l.id=d.location_id where l.address_summary like ('%" + address.trim() + "%')";
            conditionList.add(sql);
        }
        if(phoneNumber != null && !"".equals(phoneNumber.trim())) {
            String cleanPN = phoneNumber.trim().replace(" ", "").replace("(", "").replace(")", "").replace("-", "");
            if(cleanPN.length() > 0) {
                if(cleanPN.startsWith("1")) {
                    cleanPN = cleanPN.substring(1);
                }
            }

            String pn1 = "";
            String pn2 = "";
            String pn3 = "";
            if(cleanPN.length() >= 3) {
                pn1 = cleanPN.substring(0, 3);
                pn2 = cleanPN.substring(0, 3) + "-";
                pn3 = "(" + cleanPN.substring(0, 3) + ") ";
            }
            if(cleanPN.length() >= 6) {
                pn1 = cleanPN.substring(0, cleanPN.length());
                pn2 = pn2 + cleanPN.substring(3, 6) + "-" + cleanPN.substring(6, cleanPN.length());
                pn3 = pn3 + cleanPN.substring(3, 6) + "-" + cleanPN.substring(6, cleanPN.length());
            }
            String sql = "select d._id from doctor d left join location l on l.id=d.location_id where l.contact_summary like ('%" + pn1 + "%') or l.contact_summary like ('%" + pn2 + "%') or l.contact_summary like ('%" + pn3 + "%')";
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
        allCondition.append(" ) zz ");

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
