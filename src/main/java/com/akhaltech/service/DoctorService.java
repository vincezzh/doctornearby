package com.akhaltech.service;

import com.akhaltech.model.DoctorSearch;

import java.util.List;

/**
 * Created by vince on 2015-07-14.
 */
public interface DoctorService {

    List<String> search(DoctorSearch search);
    String getDoctorById(String id);

}
