package com.akhaltech.startup;

import com.akhaltech.constant.GlobalConstant;
import com.akhaltech.model.Medicine;
import com.akhaltech.model.Notification;
import com.akhaltech.service.UserService;
import com.akhaltech.service.impl.UserServiceImpl;
import com.akhaltech.util.NotificationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by vince on 15-12-01.
 */
public class MedicineScheduledTask extends TimerTask {

    private final static Logger log = Logger.getLogger(MedicineScheduledTask.class);
    private ObjectMapper mapper = new ObjectMapper();

    private UserService userService;

    public MedicineScheduledTask() {
        userService = new UserServiceImpl();
    }

    public void run() {
        try {
            log.info("MedicineScheduledTask: run()");

            List<String> medicineJSonList = userService.getMedicines();
            List<Medicine> medicineList = null;
            if (medicineJSonList != null && medicineJSonList.size() > 0) {
                medicineList = new ArrayList<Medicine>();
                for (String medicineJSon : medicineJSonList) {
                    medicineList.add(mapper.readValue(medicineJSon, Medicine.class));
                }
            }

            if (medicineList != null && medicineList.size() > 0) {
                Map<String, Notification> notificationMap = new HashMap<String, Notification>();

                Iterator<Medicine> i = medicineList.iterator();
                while (i.hasNext()) {
                    Medicine medicine = i.next();
                    if (medicine.getLeftMinutes() == 0) {
                        i.remove();
                        userService.deleteMedicine(medicine);
                    }else if(medicine.getLeftMinutes() > GlobalConstant.NOTIFICATION_PERIOD_MINUTES) {
                        i.remove();
                    }else {
                        if(medicine.getDeviceToken() != null) {
                            if (notificationMap.get(medicine.getDeviceToken()) == null) {
                                Notification notification = new Notification();
                                notification.setToken(medicine.getDeviceToken());
                                notificationMap.put(medicine.getDeviceToken(), notification);
                            }

                            if (notificationMap.get(medicine.getDeviceToken()).getMessage() == null) {
                                notificationMap.get(medicine.getDeviceToken()).setMessage("Please eat/apply the following medicine(s) in " + GlobalConstant.NOTIFICATION_PERIOD_MINUTES + " minutes: " + medicine.getName());
                            } else {
                                notificationMap.get(medicine.getDeviceToken()).setMessage(notificationMap.get(medicine.getDeviceToken()).getMessage() + ", " + medicine.getName());
                            }
                        }
                    }
                }

                if(notificationMap.values() != null && notificationMap.values().size() > 0) {
                    for(Notification n : notificationMap.values()) {
                        NotificationUtil.pushNotification(n);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}