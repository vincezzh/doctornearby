package com.akhaltech.business;

import com.akhaltech.model.Doctor;
import com.akhaltech.model.DoctorSearch;
import com.akhaltech.service.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vince on 2015-09-15.
 */
@Component
public class DoctorBD {

    private final static Logger log = Logger.getLogger(DoctorBD.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DoctorService doctorService;

    public List<Doctor> search(DoctorSearch search) throws Exception {
        log.info("DoctorBD.search()");

        List<String> doctorJSonList = doctorService.search(search);
        List<Doctor> doctorList = null;
        if(doctorJSonList != null) {
            doctorList = new ArrayList<Doctor>();
            for(String doctorJson : doctorJSonList) {
                doctorList.add(mapper.readValue(doctorJson, Doctor.class));
            }
        }

        return doctorList;
    }

    public Doctor getDoctorById(String id) throws Exception {
        log.info("DoctorBD.getDoctorById()");

        String doctorJson = doctorService.getDoctorById(id);
        Doctor doctor = null;
        if(doctorJson != null)
            doctor = mapper.readValue(doctorJson, Doctor.class);

        return doctor;
    }
}
