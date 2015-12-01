package com.akhaltech.service.impl;

import com.akhaltech.constant.GlobalConstant;
import com.akhaltech.model.Medicine;
import com.akhaltech.service.BaseServiceImpl;
import com.akhaltech.service.UserService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

/**
 * Created by vince on 2015-10-04.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private final static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Override
    public String getBookmark(String userId, String doctorId) {
        log.info("UserServiceImpl.getBookmark()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_BOOKMARK);
            Document bookmark = collection.find(and(
                eq("userId", userId),
                eq("doctorId", doctorId)
            )).first();

            String doctorJson = null;
            if(bookmark != null)
                doctorJson = bookmark.toJson();

            return doctorJson;
        }finally {
            close();
        }
    }

    @Override
    public List<String> getBookmarks(String userId) {
        log.info("UserServiceImpl.getBookmarks()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_BOOKMARK);
            MongoCursor<Document> cursor = collection.find(eq("userId", userId)).iterator();

            List<String> doctorIdJsonList = null;
            if(cursor != null) {
                try {
                    while (cursor.hasNext()) {
                        if(doctorIdJsonList == null)
                            doctorIdJsonList = new ArrayList<String>();

                        doctorIdJsonList.add(cursor.next().toJson());
                    }
                }finally {
                    cursor.close();
                }
            }

            return doctorIdJsonList;
        }finally {
            close();
        }
    }

    @Override
    public void addBookmark(String userId, String doctorId, String province) {
        log.info("UserServiceImpl.addBookmark()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_BOOKMARK);

            Document newBookmark = new Document("userId", userId).append("doctorId", doctorId).append("province", province);
            collection.insertOne(newBookmark);
        }finally {
            close();
        }
    }

    @Override
    public void deleteBookmark(String userId, String doctorId, String province) {
        log.info("UserServiceImpl.deleteBookmark()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_BOOKMARK);
            collection.deleteOne(and(
                    eq("userId", userId),
                    eq("doctorId", doctorId),
                    eq("province", province)
            ));
        }finally {
            close();
        }
    }

    @Override
    public List<String> getMedicines(String userId) {
        log.info("UserServiceImpl.getMedicines()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_MEDICINE);
            List<String> sortingList = new ArrayList<String>();
            sortingList.add("createdTime");
            MongoCursor<Document> cursor = collection.find(eq("userId", userId)).sort(ascending(sortingList)).iterator();

            List<String> medicineJsonList = null;
            if(cursor != null) {
                try {
                    while (cursor.hasNext()) {
                        if(medicineJsonList == null)
                            medicineJsonList = new ArrayList<String>();

                        medicineJsonList.add(cursor.next().toJson());
                    }
                }finally {
                    cursor.close();
                }
            }

            return medicineJsonList;
        }finally {
            close();
        }
    }

    @Override
    public void addMedicine(Medicine medicine) {
        log.info("UserServiceImpl.addMedicine()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_MEDICINE);

            Document newBookmark = new Document("userId", medicine.getUserId())
                    .append("name", medicine.getName())
                    .append("periodMinutes", medicine.getPeriodMinutes())
                    .append("startTime", medicine.getStartTime());
            collection.insertOne(newBookmark);
        }finally {
            close();
        }
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        log.info("UserServiceImpl.deleteMedicine()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_MEDICINE);
            collection.deleteOne(
                    eq("_id", new ObjectId(medicine.get_id().get$oid()))
            );
        }finally {
            close();
        }
    }

    @Override
    public List<String> getMedicines() {
        log.info("UserServiceImpl.getMedicines()");
        MongoDatabase db = initialize();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_MEDICINE);
            List<String> sortingList = new ArrayList<String>();
            sortingList.add("createdTime");
            MongoCursor<Document> cursor = collection.find().sort(ascending(sortingList)).iterator();

            List<String> medicineJsonList = null;
            if(cursor != null) {
                try {
                    while (cursor.hasNext()) {
                        if(medicineJsonList == null)
                            medicineJsonList = new ArrayList<String>();

                        medicineJsonList.add(cursor.next().toJson());
                    }
                }finally {
                    cursor.close();
                }
            }

            return medicineJsonList;
        }finally {
            close();
        }
    }
}
