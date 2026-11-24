package com.burhanstore.bubblereward.utils;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;

import com.burhanstore.bubblereward.AppController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JOB_ADD {
    public static void work_AddClass(Activity activity) {

        // Get current date and time
        Date formattedDateTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH);
        String currentDate = dateFormat.format(formattedDateTime);

        Log.e(TAG, "current Date: " + currentDate);

        String spinCount = Constant.getString(activity, Constant.SPIN_COUNT);
        if (spinCount.equals("0")) {
            if (Constant.getString(activity, Constant.LAST_DATE_SPIN).equals(currentDate)) {
                //
            } else {
                String countHere = App_Settings.getString(activity, App_Settings.SPIN_AVA_CHA);
                AppController.updateWork(activity, countHere, currentDate, "spin_count", "last_date_spin");
                Log.e(TAG, "count Here  " + countHere);
            }
        }


    }
}
