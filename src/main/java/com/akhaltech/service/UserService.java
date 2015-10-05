package com.akhaltech.service;

import java.util.List;

/**
 * Created by vince on 2015-07-14.
 */
public interface UserService {

    String getBookmark(String userId, String doctorId);
    List<String> getBookmarks(String userId);
    void addBookmark(String userId, String doctorId);
    void deleteBookmark(String userId, String doctorId);

}
