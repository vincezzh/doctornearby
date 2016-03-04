package com.akhaltech.business;

import com.akhaltech.constant.GlobalConstant;
import com.akhaltech.dao.DoctorDAO;
import com.akhaltech.model.Doctor;
import com.akhaltech.model.DoctorSearch;
import com.akhaltech.model.HTMLTemplate;
import com.akhaltech.util.PropertyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by vince on 2015-09-15.
 */
@Component
public class DoctorBD {

    private final static Logger log = Logger.getLogger(DoctorBD.class);
    private ObjectMapper mapper = new ObjectMapper();

//    @Autowired
//    private DoctorService doctorService;

    @Autowired
    private DoctorDAO doctorDAO;

    @Autowired
    private final VelocityEngine velocityEngine = null;

    public List<Doctor> search(DoctorSearch search) throws Exception {
        log.info("DoctorBD.search()");

//        List<String> doctorJSonList = doctorService.search(search);
//        List<Doctor> doctorList = null;
//        if(doctorJSonList != null && doctorJSonList.size() > 0) {
//            doctorList = new ArrayList<Doctor>();
//            for(String doctorJson : doctorJSonList) {
//                doctorList.add(mapper.readValue(doctorJson, Doctor.class));
//            }
//        }
        List<Doctor> doctorList = doctorDAO.search(search);

        return doctorList;
    }

    public Doctor getDoctorById(String id) throws Exception {
        log.info("DoctorBD.getDoctorById()");

//        String doctorJson = doctorService.getDoctorById(id);
//        Doctor doctor = null;
//        if(doctorJson != null)
//            doctor = mapper.readValue(doctorJson, Doctor.class);

        Doctor doctor = doctorDAO.getDoctorById(id);

        return doctor;
    }

    public HTMLTemplate generateDoctorProfileHtml(Doctor doctor) {
        log.info("DoctorBD.generateDoctorProfileHtml()");

        HTMLTemplate htmlTemplate = new HTMLTemplate();
        final Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/htmlTemplate.properties"));
            String path = properties.getProperty("doctor.profile.template");
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("doctor", doctor);
            model.put("baseURL", GlobalConstant.APP_SERVER_URL + "/doctornearby");
            try {
                PropertyUtil props = new PropertyUtil();
                model.put("baseURL", props.getProperty("application.server.url") + "/doctornearby");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            String html = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, path, "UTF-8", model);
            htmlTemplate.setHtml(html);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlTemplate;
    }
}
