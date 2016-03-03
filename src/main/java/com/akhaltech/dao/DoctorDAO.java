package com.akhaltech.dao;

import com.akhaltech.model.Doctor;
import com.akhaltech.model.DoctorSearch;

import java.util.List;

/**
 * Created by vzhang on 03/03/2016.
 */
public interface DoctorDAO {

    List<Doctor> search(DoctorSearch search);
    List<Doctor> getDoctors(List<String> idList);
    Doctor getDoctorById(String id);

}
