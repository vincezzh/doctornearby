package com.akhaltech.controller;

import com.akhaltech.business.DoctorBD;
import com.akhaltech.model.Doctor;
import com.akhaltech.model.DoctorSearch;
import com.akhaltech.model.HTMLTemplate;
import com.akhaltech.rest.RestResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 2015-07-14.
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final static Logger log = Logger.getLogger(DoctorController.class);

    @Autowired
    private DoctorBD doctorBD;

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
    public RestResponse<List<Doctor>> search(@RequestBody DoctorSearch search) {
        log.info("DoctorController.search()");

        try {
            List<Doctor> doctorList = doctorBD.search(search);
            if(doctorList == null)
                doctorList = new ArrayList<Doctor>();

            return new RestResponse<List<Doctor>>(true, null, doctorList);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace());
            return new RestResponse<List<Doctor>>(false, e.getMessage(), null);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<Doctor> getDoctor(@PathVariable("id") String id) {
        log.info("DoctorController.getDoctor()");

        try {
            Doctor doctor = doctorBD.getDoctorById(id);
            return new RestResponse<Doctor>(true, null, doctor);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace());
            return new RestResponse<Doctor>(false, e.getMessage(), null);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/{id}/profile", method = RequestMethod.GET, produces = "application/json")
    public RestResponse<HTMLTemplate> getDoctorProfile(@PathVariable("id") String id) {
        log.info("DoctorController.getDoctorProfile()");

        try {
            Doctor doctor = doctorBD.getDoctorById(id);
            HTMLTemplate htmlTemplate = doctorBD.generateDoctorProfileHtml(doctor);
            return new RestResponse<HTMLTemplate>(true, null, htmlTemplate);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getStackTrace());
            return new RestResponse<HTMLTemplate>(false, e.getMessage(), null);
        }

    }

}
