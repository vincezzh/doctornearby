package com.akhaltech.controller;

import com.akhaltech.business.UserBD;
import com.akhaltech.model.Doctor;
import com.akhaltech.model.User;
import com.akhaltech.rest.RestResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by vince on 2015-07-14.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserBD userBD;

    @ResponseBody
    @RequestMapping(value = "/bookmark/list", method = RequestMethod.POST, produces = "application/json")
    public RestResponse<List<Doctor>> getUserBookmarks(@RequestBody User user) {
        log.info("UserController.getUserBookmarks()");

        try {
            List<Doctor> bookmarkDoctorsList = userBD.getBookmarkDoctors(user.getBookmark().getUserId());
            return new RestResponse<List<Doctor>>(true, null, bookmarkDoctorsList);
        }catch (Exception e) {
            log.error(e.getStackTrace());
            return new RestResponse<List<Doctor>>(false, e.getMessage(), null);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/bookmark/add", method = RequestMethod.POST, produces = "application/json")
    public RestResponse<String> addUserBookmark(@RequestBody User user) {
        log.info("UserController.addUserBookmark()");

        try {
            userBD.addBookmark(user.getBookmark());
            return new RestResponse<String>(true, null, null);
        }catch (Exception e) {
            log.error(e.getStackTrace());
            return new RestResponse<String>(false, e.getMessage(), null);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/bookmark/delete", method = RequestMethod.POST, produces = "application/json")
    public RestResponse<String> deleteUserBookmark(@RequestBody User user) {
        log.info("UserController.deleteUserBookmark()");

        try {
            userBD.deleteBookmark(user.getBookmark());
            return new RestResponse<String>(true, null, null);
        }catch (Exception e) {
            log.error(e.getStackTrace());
            return new RestResponse<String>(false, e.getMessage(), null);
        }

    }

}
