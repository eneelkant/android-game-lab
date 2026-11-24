package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.activity.includ.LoginView;
import com.burhanstore.bubblereward.utils.ADS_CLASS;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.utils.Constant;
import com.burhanstore.bubblereward.utils.JOB_ADD;
import com.burhanstore.bubblereward.utils.Unity_Work_Ads;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MyprofileActivity extends AppCompatActivity {

    private ImageView userpp, imageView78;
    private TextView textView29, user_points_text_view, TotalRefer;
    private Activity activity;
    private LinearLayout DailyCheckingBtn;
    private TextView daily_check_in_textViewa, textView30;
    private LinearLayout nav_my_profile, nav_spinBtn;
    private CardView nav_leaderboard, WalletBtn, Nav_Contact, Nav_Refer, Nav_Privacy, Nav_Share, profile_logout, TransactionHistoryBtn, EarningHistoryBtn;
    private Unity_Work_Ads unity_work_ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        TextView badge = findViewById(R.id.badge);
        userpp = findViewById(R.id.userpp);
        imageView78 = findViewById(R.id.imageView78);
        textView29 = findViewById(R.id.textView29);
        user_points_text_view = findViewById(R.id.user_points_text_view);
        TotalRefer = findViewById(R.id.textView32);
        DailyCheckingBtn = findViewById(R.id.DailyCheckingBtn);
        daily_check_in_textViewa = findViewById(R.id.daily_check_in_textView);
        nav_my_profile = findViewById(R.id.nav_my_profile);
        nav_leaderboard = findViewById(R.id.nav_leaderboard);
        WalletBtn = findViewById(R.id.WalletBtn);
        Nav_Contact = findViewById(R.id.Nav_Contact);
        Nav_Refer = findViewById(R.id.Nav_Refer);
        Nav_Privacy = findViewById(R.id.Nav_Privacy);
        textView30 = findViewById(R.id.textView30);
        Nav_Share = findViewById(R.id.Nav_Share);
        profile_logout = findViewById(R.id.profile_logout);
        TransactionHistoryBtn = findViewById(R.id.TransactionHistoryBtn);
        EarningHistoryBtn = findViewById(R.id.EarningHistoryBtn);
        nav_spinBtn = findViewById(R.id.nav_spinBtn);
        daily_check_in_textViewa.setText(App_Settings.getString(activity, App_Settings.DAILY_CHECK_REWARD));

        userDataInt();


        unity_work_ads = new Unity_Work_Ads(activity);
        String unityGameId = App_Settings.getString(activity, App_Settings.UNITY_GAME_ID);
        unity_work_ads.initializeUnityAds(unityGameId);


        imageView78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                }
                if (clipboard != null) {
                    ClipData clip = ClipData.newPlainText("Referral Link", textView29.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(activity, getString(R.string.ref_code_copied), Toast.LENGTH_SHORT).show();
                }

            }
        });


        badge.setText(Constant.getString(activity, Constant.NOTICE_BADGE));

        RelativeLayout bell = findViewById(R.id.bell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, NotificationA.class);
                startActivity(i);
            }
        });


        final HashMap<String, String> subids = new HashMap<String, String>();
        subids.put("s2", "my sub id");


        DailyCheckingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDailyAP();
            }
        });
        nav_spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SpinA.class);
                startActivity(intent);
            }
        });

        oneClickV();


    }

    private void userDataInt() {

        TextView FullNameUser = findViewById(R.id.FullNameUser);
        FullNameUser.setText(Constant.getString(activity, Constant.USER_NAME));
        textView29.setText(Constant.getString(activity, Constant.USER_REFER));

        TotalRefer.setText(Constant.getString(activity, Constant.TOTAL_REFER));

        Glide.with(activity).load(Constant.getString(activity, Constant.USER_IMAGE))
                .apply(new RequestOptions().placeholder(R.drawable.app_icon))
                .into(userpp);

        Handler handler;
        Runnable refreshRunnable;
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
                user_main_points(user_points_text_view);
                textView30.setText(Constant.getString(activity, Constant.USER_POINTS));
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(refreshRunnable);


    }

    private void oneClickV() {
        nav_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, MyProfile.class);
                startActivity(i);
            }
        });

        nav_leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, LeaderA.class);
                startActivity(i);
            }
        });
        WalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, WalletA.class);
                startActivity(i);
            }
        });
        Nav_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, ContactA.class);
                startActivity(i);
            }
        });
        Nav_Refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, ReferAndEarnA.class);
                startActivity(i);
            }
        });
        Nav_Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, PrvacyA.class);
                startActivity(i);
            }
        });
        Nav_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_invite_friends_description) + getApplicationContext().getPackageName() + "\n Refer Code " + Constant.getString(activity, Constant.USER_REFER));
                startActivity(Intent.createChooser(intent, getString(R.string.app_invite_friends_title)));
            }
        });
        TransactionHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, TransactionsH.class);
                startActivity(i);
            }
        });
        EarningHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyprofileActivity.this, EarningHistoryA.class);
                startActivity(i);
            }
        });
        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constant.setString(activity, Constant.USER_EMAIL, "555");
                Toast.makeText(activity, "Logout Successfully.!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(activity, LoginView.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });


    }

    private void checkDailyAP() {
        JOB_ADD.work_AddClass((Activity) activity);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if (Constant.getString(activity, Constant.DAILY_CHECK_DATE_STORE).equals(currentDate)) {
            Toast.makeText(activity, (getResources().getString(R.string.already_collected)), Toast.LENGTH_SHORT).show();
        } else {
            checkDaily();
        }

    }

    private void checkDaily() {
        if (Constant.isNetworkAvailable(activity)) {

            final Dialog dialogs = new Dialog(activity);
            dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogs.setContentView(R.layout.dailog_dialy);
            dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogs.setCancelable(false);

            TextView points = dialogs.findViewById(R.id.points);
            AppCompatButton add_btn = dialogs.findViewById(R.id.add_btn);
            AppCompatButton CancelBtn = dialogs.findViewById(R.id.CancelBtn);
            TextView title_text_points = dialogs.findViewById(R.id.title_text_points);
            points.setText(App_Settings.getString(activity, App_Settings.DAILY_CHECK_REWARD));

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (Constant.getString(activity, Constant.DAILY_CHECK_DATE_STORE).equals(currentDate)) {

                add_btn.setVisibility(View.GONE);
                title_text_points.setText(getResources().getString(R.string.already_collected));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogs.dismiss();
                    }
                });

                points.setVisibility(View.GONE);
            } else {
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //videoAdsShow();
                        String typeOfAds = App_Settings.getString(activity, App_Settings.DAILY_CHECKING_VIDEO_ADS);
                        switch (typeOfAds) {
                            case "Admob":
                                ADS_CLASS.ShowAdmobVideo(activity);
                                break;

                            case "Vungle":
                                ADS_CLASS.playAdVungle(activity);
                                break;

                            case "UnityAds":
                                //Unity
                                unity_work_ads.loadRewardedAd();
                                break;
                            case "AdColony":
                                ADS_CLASS.ShowAdcolonyAds(activity);

                                break;
                            case "Startapp":
                                ADS_CLASS.StartappRewardedVideo(activity);

                                break;
                            case "applovin":
                                ADS_CLASS.ApplovinShowAds(activity);
                                break;
                        }

                        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialogs.dismiss();
                                }
                            });
                            AppController.addP(activity, App_Settings.getString(activity, App_Settings.DAILY_CHECK_REWARD), "Daily Reward");
                            AppController.updateWork(activity, "10", currentDate, "date_to", "daily_date_store");

                            Constant.setString(activity, Constant.DAILY_CHECK_DATE_STORE, currentDate);
                            // Toast.makeText(activity, getResources().getString(R.string.added_success), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Network Not Available", Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }

            CancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogs.dismiss();
                }
            });
            dialogs.show();


        } else {
            Constant.showInternetErrorDialog(activity);
        }
    }


    @Override
    protected void onResume() {

        Handler handler;
        Runnable refreshRunnable;
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                user_points_text_view.setText(Constant.getString(activity, Constant.USER_POINTS));
                user_main_points(user_points_text_view);
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(refreshRunnable);

        super.onResume();
    }


}