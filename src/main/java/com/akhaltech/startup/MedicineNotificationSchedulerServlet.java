package com.akhaltech.startup;

import com.akhaltech.constant.GlobalConstant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Timer;

/**
 * Created by vince on 15-12-01.
 */
public class MedicineNotificationSchedulerServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();

        Timer time = new Timer();
        MedicineScheduledTask mst = new MedicineScheduledTask();
        time.schedule(mst, 0, 1000 * 60 * GlobalConstant.NOTIFICATION_PERIOD_MINUTES);
    }

}
