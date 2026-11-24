package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.getInstance;
import static com.burhanstore.bubblereward.config.API.USER_SOME_API;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.activity.includ.Login;
import com.burhanstore.bubblereward.utils.Constant;
import com.burhanstore.bubblereward.utils.shPR.PrefManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MyProfile extends AppCompatActivity {


    private ImageView backBtn, Profile_section;
    Activity activity;
    private TextView user_points_text_view, User_name, email_pf_edit_text, member_since, UserCountry, DeviceId, Upoints, TotalRefer, your_refer_code;

    private LinearLayout UserLogut;
    private ImageView editBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        backBtn = findViewById(R.id.backBtn);
        Profile_section = findViewById(R.id.Profile_section);
        User_name = findViewById(R.id.User_name);
        email_pf_edit_text = findViewById(R.id.email_pf_edit_text);
        member_since = findViewById(R.id.member_since);
        UserCountry = findViewById(R.id.UserCountry);
        DeviceId = findViewById(R.id.DeviceId);
        Upoints = findViewById(R.id.Upoints);
        textView = findViewById(R.id.totalRedeem);
        TotalRefer = findViewById(R.id.TotalRefer);
        your_refer_code = findViewById(R.id.your_refer_code);
        UserLogut = findViewById(R.id.UserLogut);
        editBtn = findViewById(R.id.editBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        setUserData();
        totalRedeem(textView);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, EditA.class);
                startActivity(i);
            }
        });


    }


    private void setUserData() {


        Handler handler;
        Runnable refreshRunnable;
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                totalRedeem(textView);
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(refreshRunnable);


        Glide.with(activity).load(Constant.getString(activity, Constant.USER_IMAGE))
                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                .into(Profile_section);

        User_name.setText(Constant.getString(activity, Constant.USER_NAME));
        email_pf_edit_text.setText(Constant.getString(activity, Constant.USER_EMAIL));
        member_since.setText(Constant.getString(activity, Constant.USER_CREATED_DATE));
        UserCountry.setText(Constant.getString(activity, Constant.USER_COUNTRY));
        DeviceId.setText(Constant.getString(activity, Constant.USER_DEVICE));
        Upoints.setText(Constant.getString(activity, Constant.USER_POINTS));
        TotalRefer.setText(Constant.getString(activity, Constant.TOTAL_REFER));
        your_refer_code.setText(Constant.getString(activity, Constant.USER_REFER));


        your_refer_code.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("Referral Link", Constant.getString(activity, Constant.USER_REFER));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(activity, getString(R.string.ref_code_copied), Toast.LENGTH_SHORT).show();
            }
        });

        UserLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(activity);
                prefManager.clearUserData();

                Constant.setString(activity, Constant.USER_EMAIL, "555");

                Toast.makeText(activity, "Logout Successfully.!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }

    private void clearAppData() {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void totalRedeem(TextView t) {
        textView = t;
        String userId = Constant.getString(getInstance(), Constant.USER_ID);
        new RetrieveUserPointsTask().execute(userId);
    }

    private void updatePoints(String points) {
        if (textView != null) {
            textView.setText(points);
        }
    }

    private class RetrieveUserPointsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String userID = params[0];
            String response = "";

            try {
                URL url = new URL(USER_SOME_API);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8");
                conn.getOutputStream().write(data.getBytes("UTF-8"));

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = reader.readLine();
                reader.close();

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    int points = Integer.parseInt(response);
                    updatePoints(String.valueOf(points));
                } catch (NumberFormatException e) {
                    // Handle the case where response is not a valid number
                }
            } else {
                // Handle the case where response is null
            }
        }
    }

}