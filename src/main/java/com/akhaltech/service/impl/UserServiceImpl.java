package com.akhaltech.service.impl;

import com.akhaltech.constant.GlobalConstant;
import com.akhaltech.service.UserService;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by vince on 2015-10-04.
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger log = Logger.getLogger(UserServiceImpl.class);
    private MongoClient mongoClient = null;
    private MongoDatabase db = null;

    private void init() {
        mongoClient = new MongoClient(GlobalConstant.DB_SERVER_URL , GlobalConstant.DB_PORT);
        db = mongoClient.getDatabase(GlobalConstant.DB_NAME);
    }

    @Override
    public String getBookmark(String userId, String doctorId) {
        log.info("UserServiceImpl.getBookmark()");
        init();

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
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public List<String> getBookmarks(String userId) {
        log.info("UserServiceImpl.getBookmarks()");
        init();

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
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public void addBookmark(String userId, String doctorId) {
        log.info("UserServiceImpl.addBookmark()");
        init();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_BOOKMARK);

            Document newBookmark = new Document("userId", userId).append("doctorId", doctorId);
            collection.insertOne(newBookmark);
        }finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public void deleteBookmark(String userId, String doctorId) {
        log.info("UserServiceImpl.deleteBookmark()");
        init();

        try {
            MongoCollection<Document> collection = db.getCollection(GlobalConstant.COLLECTION_USER_BOOKMARK);
            collection.deleteOne(and(
                eq("userId", userId),
                eq("doctorId", doctorId)
            ));
        }finally {
            if(mongoClient != null)
                mongoClient.close();
        }
    }
}
