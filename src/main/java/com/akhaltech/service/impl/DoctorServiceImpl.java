package com.akhaltech.service.impl;

import com.akhaltech.constant.GlobalConstant;
import com.akhaltech.model.DoctorSearch;
import com.akhaltech.service.BaseServiceImpl;
import com.akhaltech.service.DoctorService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Sorts.ascending;

/**
 * Created by vince on 2015-07-14.
 */
@Service
public class DoctorServiceImpl extends BaseServiceImpl implements DoctorService {

    private final static Logger log = Logger.getLogger(DoctorServiceImpl.class);

    @Override
    public List<String> search(DoctorSearch search) {
        log.info("DoctorServiceImpl.search()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_DOCTOR);
            Bson allConditions = search.getSearchConditions();
            MongoCursor<Document> cursor = null;

            List<String> sortingList = new ArrayList<String>();
            sortingList.add("profile.surname");
            sortingList.add("profile.givenName");
            Date now = new Date();
            if(allConditions == null) {
                cursor = collection.find().sort(ascending(sortingList)).skip(search.getSkip()).limit(search.getLimit()).iterator();
            }else {
                cursor = collection.find(allConditions).sort(ascending(sortingList)).skip(search.getSkip()).limit(search.getLimit()).iterator();
            }
            log.info("DoctorServiceImpl.search() -> " + (new Date().getTime() - now.getTime()) + "ms");

            List<String> doctorJsonList = null;
            if(cursor != null) {
                try {
                    while (cursor.hasNext()) {
                        if(doctorJsonList == null)
                            doctorJsonList = new ArrayList<String>();

                        doctorJsonList.add(cursor.next().toJson());
                    }
                }finally {
                    cursor.close();
                }
            }

            return doctorJsonList;
        }finally {
            close();
        }
    }

    @Override
    public List<String> getDoctors(List<String> idList) {
        log.info("DoctorServiceImpl.getDoctorById()");
        MongoDatabase db = initialize();

        try {
            List<String> sortingList = new ArrayList<String>();
            sortingList.add("profile.surname");
            sortingList.add("profile.givenName");

            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_DOCTOR);
            MongoCursor<Document> cursor = collection.find(
                    in(GlobalConstant.DEFAULT_ID_KEY, idList)
            ).sort(ascending(sortingList)).iterator();

            List<String> doctorJsonList = null;
            if(cursor != null) {
                try {
                    while (cursor.hasNext()) {
                        if(doctorJsonList == null)
                            doctorJsonList = new ArrayList<String>();

                        doctorJsonList.add(cursor.next().toJson());
                    }
                }finally {
                    cursor.close();
                }
            }

            return doctorJsonList;
        }finally {
            close();
        }

    }

    @Override
    public String getDoctorById(String id) {
        log.info("DoctorServiceImpl.getDoctorById()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_DOCTOR);
            Document doctor = collection.find(
                eq(GlobalConstant.DEFAULT_ID_KEY, id)
            ).first();

            String doctorJson = null;
            if(doctor != null)
                doctorJson = doctor.toJson();

            return doctorJson;
        }finally {
            close();
        }

    }
}
