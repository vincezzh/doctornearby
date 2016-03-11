package com.akhaltech.business;

import com.akhaltech.dao.DoctorDAO;
import com.akhaltech.dao.UserDAO;
import com.akhaltech.model.Bookmark;
import com.akhaltech.model.Doctor;
import com.akhaltech.model.Medicine;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by vince on 2015-09-15.
 */
@Component
public class UserBD {

    private final static Logger log = Logger.getLogger(UserBD.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DoctorDAO doctorDAO;

    public List<Doctor> getBookmarkDoctors(String userId) throws Exception {
        log.info("UserBD.getBookmarkDoctors()");

        List<Bookmark> bookmarkList = getBookmarks(userId);
        List<Doctor> doctorList = null;
        if(bookmarkList != null && bookmarkList.size() > 0) {
            List<String> doctorIdList = new ArrayList<String>();
            for(Bookmark tempBookmark : bookmarkList) {
                doctorIdList.add(tempBookmark.getDoctorId());
            }
            doctorList = doctorDAO.getDoctors(doctorIdList);
        }

        return doctorList;
    }

    public List<Bookmark> getBookmarks(String userId) throws Exception {
        log.info("UserBD.getBookmarks()");
        List<Bookmark> bookmarkList = userDAO.getBookmarks(userId);

        return bookmarkList;
    }

    public void addBookmark(Bookmark bookmark) throws Exception {
        log.info("UserBD.addBookmark()");

        List<Bookmark> bookmarkList = getBookmarks(bookmark.getUserId());
        boolean needToAdd = true;
        if(bookmarkList != null && bookmarkList.size() > 0) {
            for(Bookmark tempBookmark : bookmarkList) {
                if(bookmark.getDoctorId().equals(tempBookmark.getDoctorId())) {
                    needToAdd = false;
                    break;
                }
            }
        }

        if(needToAdd) {
            userDAO.addBookmark(bookmark.getUserId(), bookmark.getDoctorId(), bookmark.getProvince());
        }
    }

    public void deleteBookmark(Bookmark bookmark) {
        log.info("UserBD.deleteBookmark()");
        userDAO.deleteBookmark(bookmark.getUserId(), bookmark.getDoctorId(), bookmark.getProvince());
    }

    public List<Medicine> getMedicines(String userId) throws Exception {
        log.info("UserBD.getMedicines()");
        List<Medicine> medicineList = userDAO.getMedicines(userId);

        if(medicineList != null && medicineList.size() > 0) {
            Iterator<Medicine> i = medicineList.iterator();
            while(i.hasNext()) {
                Medicine medicine = i.next();
                if(medicine.getLeftMinutes() == 0) {
                    i.remove();
                    deleteMedicine(medicine);
                }
            }

            Collections.sort(medicineList, new Comparator<Medicine>() {
                @Override
                public int compare(Medicine o1, Medicine o2) {
                    return o1.getLeftMinutes() <= o2.getLeftMinutes()? -1 : 1;
                }
            });
        }

        return medicineList;
    }

    public void addMedicine(Medicine medicine) throws Exception {
        log.info("UserBD.addMedicine()");
        userDAO.addMedicine(medicine);
    }

    public void deleteMedicine(Medicine medicine) {
        log.info("UserBD.deleteMedicine()");
        userDAO.deleteMedicine(medicine);
    }

}
