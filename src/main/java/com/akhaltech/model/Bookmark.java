package com.akhaltech.model;

/**
 * Created by vince on 2015-10-04.
 */
public class Bookmark extends Base {
    private String userId;
    private String doctorId;
    private String province;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
