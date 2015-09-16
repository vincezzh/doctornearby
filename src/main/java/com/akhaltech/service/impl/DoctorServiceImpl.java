package com.akhaltech.service.impl;

import com.akhaltech.model.DoctorSearch;
import com.akhaltech.service.DoctorService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

/**
 * Created by vince on 2015-07-14.
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private final static Logger log = Logger.getLogger(DoctorServiceImpl.class);
    private MongoClient mongoClient = null;
    private MongoDatabase db = null;

    private void init() {
        mongoClient = new MongoClient("localhost" , 27017);
        db = mongoClient.getDatabase("doctornearme");
    }

    @Override
    public List<String> search(DoctorSearch search) {
        log.info("DoctorServiceImpl.search()");
        init();

        try {
            MongoCollection<Document> collection = db.getCollection("doctor");
            MongoCursor<Document> cursor = collection.find(
                or(
                    eq("profile.surname", Pattern.compile(search.getName(), Pattern.CASE_INSENSITIVE)),
                    eq("profile.givenName", Pattern.compile(search.getName(), Pattern.CASE_INSENSITIVE))
                )
            ).iterator();

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
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public String getDoctorById(String id) {
        log.info("DoctorServiceImpl.getDoctorById()");
        init();

        try {
            MongoCollection<Document> collection = db.getCollection("doctor");
            Document doctor = collection.find(
                eq("_id", id)
            ).first();

            String doctorJson = null;
            if(doctor != null)
                doctorJson = doctor.toJson();

            return doctorJson;
        }finally {
            if(mongoClient != null)
                mongoClient.close();
        }

    }
}
