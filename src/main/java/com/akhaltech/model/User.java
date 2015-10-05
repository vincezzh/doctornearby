package com.akhaltech.model;

import java.util.List;

/**
 * Created by vince on 2015-10-02.
 */
public class User {
    private String userId;
    private Bookmark bookmark;
    private List<Bookmark> bookmarkList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    public void setBookmarkList(List<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }
}
