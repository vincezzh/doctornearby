package com.akhaltech.service;

import com.akhaltech.constant.GlobalConstant;
import com.akhaltech.util.PropertyUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;

/**
 * Created by vince on 2015-10-15.
 */
public class BaseServiceImpl {

    private final static Logger log = Logger.getLogger(BaseServiceImpl.class);
    protected MongoClient mongoClient;

    protected MongoDatabase initialize() {
        String dbServerURL = GlobalConstant.DB_SERVER_URL;
        int dbPort = GlobalConstant.DB_PORT;
        String dbName = GlobalConstant.DB_NAME;

        try {
            PropertyUtil props = new PropertyUtil();
            dbServerURL = props.getProperty("mongodb.server.url");
            dbPort = Integer.parseInt(props.getProperty("mongodb.server.port"));
            dbName = props.getProperty("mongodb.database.name");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        mongoClient = new MongoClient(dbServerURL, dbPort);
        return mongoClient.getDatabase(dbName);
    }

    protected void close() {
        if(mongoClient != null) {
            mongoClient.close();
        }
    }
}
