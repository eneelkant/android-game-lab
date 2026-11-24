package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.utils.Constant;

/**
 * Developer by Burhan Store
 */
public class ReferAndEarnA extends AppCompatActivity {

    Activity activity;
    private TextView user_points_text_view, referText, ReferCode, ReferShare, txtcopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Play Games");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back_color));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        user_points_text_view = findViewById(R.id.user_points_text_view);
        referText = findViewById(R.id.referText);
        ReferCode = findViewById(R.id.ReferCode);
        ReferShare = findViewById(R.id.ReferShare);
        txtcopy = findViewById(R.id.txtcopy);
        setPointsText();


    }

    private void setPointsText() {

        Handler handler;
        Runnable refreshRunnable;
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                user_points_text_view.setText("0");
                user_main_points(user_points_text_view);
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(refreshRunnable);


        referText.setText(App_Settings.getString(activity, App_Settings.REFER_PAGE_TEXT));
        ReferCode.setText(Constant.getString(activity, Constant.USER_REFER));
        ReferShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_invite_friends_description) + getApplicationContext().getPackageName() + "\n Refer Code " + ReferCode.getText().toString());
                startActivity(Intent.createChooser(intent, getString(R.string.app_invite_friends_title)));
            }
        });
        txtcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ReferralCode", ReferCode.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ReferAndEarnA.this, "Code Copied.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}