package com.akhaltech.util;

import com.akhaltech.constant.GlobalConstant;
import com.akhaltech.model.Notification;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import org.apache.log4j.Logger;

/**
 * Created by vince on 15-12-01.
 */
public class NotificationUtil {

    private final static Logger log = Logger.getLogger(NotificationUtil.class);

    public void pushNotification(Notification notification) {
        String token = notification.getToken();
        String message = notification.getMessage();
        PropertyUtil props = new PropertyUtil();
        String type = props.getProperty("app.type");
        String p12Password = props.getProperty("p12.password");
        ApnsServiceBuilder serviceBuilder = APNS.newService();

        if (GlobalConstant.TYPE_PROD.equals(type)) {
            String certPath = getClass().getClassLoader().getResource("prod_cert.p12").getPath();
            serviceBuilder.withCert(certPath, p12Password).withProductionDestination();
        } else if (GlobalConstant.TYPE_DEV.equals(type)) {
            String certPath = getClass().getClassLoader().getResource("dev_cert.p12").getPath();
            serviceBuilder.withCert(certPath, p12Password).withSandboxDestination();
        } else {
            log.error("unknown API type " + type);
            return;
        }

        ApnsService service = serviceBuilder.build();

        String payload = APNS.newPayload()
                .alertBody(message)
                .alertTitle("Medicine Reminder")
                .sound("default").build();

        log.info("payload: " + payload);
        service.push(token, payload);
    }

}
