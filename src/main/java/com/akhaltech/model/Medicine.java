package com.akhaltech.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by vince on 2015-10-13.
 */
public class Medicine extends Base {
    private String userId;
    private String name;
    private int periodMinutes;
    private String startTime;
    private int leftMinutes;
    private String deviceToken;

    public int getLeftMinutes() {
        leftMinutes = 0;
        Date now = new Date();
        Date startTimeDate = new Date(Long.valueOf(startTime));

        if(now.compareTo(startTimeDate) == -1) {
            leftMinutes = (int) ((startTimeDate.getTime() - now.getTime()) / 1000 / 60);
            if(leftMinutes == 0) {
                leftMinutes = 1;
            }
        }else {
            if (periodMinutes > 0) {
                Calendar c = Calendar.getInstance();
                c.setTime(startTimeDate);
                while (now.compareTo(c.getTime()) == 1) {
                    c.add(Calendar.MINUTE, periodMinutes);
                }
                leftMinutes = (int) ((c.getTime().getTime() - now.getTime()) / 1000 / 60);
            }
        }

        return leftMinutes;
    }

    public void setLeftMinutes(int leftMinutes) {
        this.leftMinutes = leftMinutes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriodMinutes() {
        return periodMinutes;
    }

    public void setPeriodMinutes(int periodMinutes) {
        this.periodMinutes = periodMinutes;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
