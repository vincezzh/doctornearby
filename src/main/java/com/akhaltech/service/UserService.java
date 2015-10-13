package com.akhaltech.service;

import com.akhaltech.model.Medicine;

import java.util.List;

/**
 * Created by vince on 2015-07-14.
 */
public interface UserService {

    String getBookmark(String userId, String doctorId);
    List<String> getBookmarks(String userId);
    void addBookmark(String userId, String doctorId);
    void deleteBookmark(String userId, String doctorId);

    List<String> getMedicines(String userId);
    void addMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);

}