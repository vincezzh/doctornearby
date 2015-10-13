package com.akhaltech.business;

import com.akhaltech.model.Bookmark;
import com.akhaltech.model.Doctor;
import com.akhaltech.model.Medicine;
import com.akhaltech.service.DoctorService;
import com.akhaltech.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserService userService;
    @Autowired
    private DoctorService doctorService;

    public List<Doctor> getBookmarkDoctors(String userId) throws Exception {
        log.info("UserBD.getBookmarkDoctors()");

        List<Bookmark> bookmarkList = getBookmarks(userId);
        List<Doctor> doctorList = null;
        if(bookmarkList != null && bookmarkList.size() > 0) {
            List<String> doctorIdList = new ArrayList<String>();
            for(Bookmark tempBookmark : bookmarkList) {
                doctorIdList.add(tempBookmark.getDoctorId());
            }

            List<String> doctorJSonList = doctorService.getDoctors(doctorIdList);
            if (doctorJSonList != null && doctorJSonList.size() > 0) {
                doctorList = new ArrayList<Doctor>();
                for (String doctorJson : doctorJSonList) {
                    doctorList.add(mapper.readValue(doctorJson, Doctor.class));
                }
            }
        }

        return doctorList;
    }

    public List<Bookmark> getBookmarks(String userId) throws Exception {
        log.info("UserBD.getBookmarks()");

        List<String> bookmarkJSonList = userService.getBookmarks(userId);
        List<Bookmark> bookmarkList = null;
        if(bookmarkJSonList != null && bookmarkJSonList.size() > 0) {
            bookmarkList = new ArrayList<Bookmark>();
            for(String bookmarkJSon : bookmarkJSonList) {
                bookmarkList.add(mapper.readValue(bookmarkJSon, Bookmark.class));
            }
        }

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

        if(needToAdd)
            userService.addBookmark(bookmark.getUserId(), bookmark.getDoctorId());
    }

    public void deleteBookmark(Bookmark bookmark) {
        log.info("UserBD.deleteBookmark()");

        userService.deleteBookmark(bookmark.getUserId(), bookmark.getDoctorId());
    }

    public List<Medicine> getMedicines(String userId) throws Exception {
        log.info("UserBD.getMedicines()");

        List<String> medicineJSonList = userService.getMedicines(userId);
        List<Medicine> medicineList = null;
        if(medicineJSonList != null && medicineJSonList.size() > 0) {
            medicineList = new ArrayList<Medicine>();
            for(String medicineJSon : medicineJSonList) {
                medicineList.add(mapper.readValue(medicineJSon, Medicine.class));
            }
        }

        if(medicineList != null && medicineList.size() > 0) {
            Iterator<Medicine> i = medicineList.iterator();
            while(i.hasNext()) {
                Medicine medicine = i.next();
                if(medicine.getLeftMinutes() == 0) {
                    i.remove();
                    deleteMedicine(medicine);
                }
            }
        }

        Collections.sort(medicineList, new Comparator<Medicine>() {
            @Override
            public int compare(Medicine o1, Medicine o2) {
                return o1.getLeftMinutes() <= o2.getLeftMinutes()? -1 : 1;
            }
        });

        return medicineList;
    }

    public void addMedicine(Medicine medicine) throws Exception {
        log.info("UserBD.addMedicine()");

        userService.addMedicine(medicine);
    }

    public void deleteMedicine(Medicine medicine) {
        log.info("UserBD.deleteMedicine()");

        userService.deleteMedicine(medicine);
    }

}
