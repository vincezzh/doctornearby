package com.akhaltech.dao.impl;

import com.akhaltech.dao.UserDAO;
import com.akhaltech.model.Bookmark;
import com.akhaltech.model.Medicine;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vzhang on 03/03/2016.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Override
    public Bookmark getBookmark(String userId, String doctorId) {
        return null;
    }

    @Override
    public List<Bookmark> getBookmarks(String userId) {
        return null;
    }

    @Override
    public void addBookmark(String userId, String doctorId, String province) {

    }

    @Override
    public void deleteBookmark(String userId, String doctorId, String province) {

    }

    @Override
    public List<Medicine> getMedicines(String userId) {
        return null;
    }

    @Override
    public void addMedicine(Medicine medicine) {

    }

    @Override
    public void deleteMedicine(Medicine medicine) {

    }

    @Override
    public List<Medicine> getMedicines() {
        return null;
    }

}
