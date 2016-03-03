package com.akhaltech.dao;

import com.akhaltech.model.Bookmark;
import com.akhaltech.model.Medicine;

import java.util.List;

/**
 * Created by vzhang on 03/03/2016.
 */
public interface UserDAO {

    Bookmark getBookmark(String userId, String doctorId);
    List<Bookmark> getBookmarks(String userId);
    void addBookmark(String userId, String doctorId, String province);
    void deleteBookmark(String userId, String doctorId, String province);

    List<Medicine> getMedicines(String userId);
    void addMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    List<Medicine> getMedicines();

}
