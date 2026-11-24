package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.fetchUserData;
import static com.burhanstore.bubblereward.AppController.user_main_points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.luckywheel.LuckyWheelView;
import com.burhanstore.bubblereward.luckywheel.model.LuckyItem;
import com.burhanstore.bubblereward.utils.ADS_CLASS;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.utils.Constant;
import com.burhanstore.bubblereward.utils.Unity_Work_Ads;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SpinA extends AppCompatActivity {

    private TextView user_points_text_view;
    LuckyWheelView luckyWheelView;
    List<LuckyItem> data = new ArrayList<>();
    //List<> list = new ArrayList<>();
    LinearLayout spin_btn;
    Boolean isSpin = false;

    private TextView collectReward_count_textView, points_textView, points_text;
    private ImageView backBtn;

    boolean first_time = true, collectReward_first = true;
    private final String TAG = "LuckyNumber";
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;

    private int collectReward_count = 1;
    Activity activity;
    private Unity_Work_Ads unity_work_ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        user_points_text_view = findViewById(R.id.user_points_text_view);
        luckyWheelView = findViewById(R.id.viewLuckyWheel);
        spin_btn = findViewById(R.id.spin_btn);
        points_text = findViewById(R.id.points_text);
        collectReward_count_textView = findViewById(R.id.limit_text);
        backBtn = findViewById(R.id.backBtn);

        setPointsText();

        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {

                String poss = "" + index;
                spin_btn.setEnabled(true);
                isSpin = false;
                String DAILY_TYPE = "Spin Win Reward";
                switch (poss) {
                    case "0":

                        addPointOption(getString(R.string.app_spin_coin_1));

                        break;
                    case "1":
                        addPointOption(getString(R.string.app_spin_coin_2));

                        break;
                    case "2":
                        addPointOption(getString(R.string.app_spin_coin_3));

                        break;

                    case "3":

                        addPointOption(getString(R.string.app_spin_coin_4));

                        break;

                    case "4":

                        addPointOption(getString(R.string.app_spin_coin_5));
                        break;

                    case "5":
                        addPointOption(getString(R.string.app_spin_coin_6));
                        break;

                    case "6":

                        addPointOption(getString(R.string.app_spin_coin_7));
                        break;

                    case "7":
                        addPointOption(getString(R.string.app_spin_coin_8));
                        break;

                    case "8":

                        addPointOption(getString(R.string.app_spin_coin_9));
                        break;

                    case "9":
                        addPointOption(getString(R.string.app_spin_coin_10));
                        break;

                    default:
                        Toast.makeText(SpinA.this, "error", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // luckyWheelView.setData();
        create(0, getString(R.string.app_spin_coin_1) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_1), new int[]{10}, 1, ContextCompat.getColor(this, R.color.wheel_b));
        create(1, getString(R.string.app_spin_coin_2) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_2), new int[]{10}, 2, ContextCompat.getColor(this, R.color.wheel_b));
        create(2, getString(R.string.app_spin_coin_3) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_3), new int[]{10}, 3, ContextCompat.getColor(this, R.color.wheel_b));
        create(3, getString(R.string.app_spin_coin_4) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_4), new int[]{10}, 20, ContextCompat.getColor(this, R.color.wheel_b));
        create(4, getString(R.string.app_spin_coin_5) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_5), new int[]{10}, 30, ContextCompat.getColor(this, R.color.wheel_b));
        create(5, getString(R.string.app_spin_coin_6) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_6), new int[]{10}, 40, ContextCompat.getColor(this, R.color.wheel_b));
        create(6, getString(R.string.app_spin_coin_7) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_7), new int[]{10}, 50, ContextCompat.getColor(this, R.color.wheel_b));
        create(7, getString(R.string.app_spin_coin_8) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_8), new int[]{10}, 60, ContextCompat.getColor(this, R.color.wheel_b));
        create(8, getString(R.string.app_spin_coin_9) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_9), new int[]{10}, 70, ContextCompat.getColor(this, R.color.wheel_b));
        create(9, getString(R.string.app_spin_coin_10) + " coins   ", R.drawable.coin_ic_m, ContextCompat.getColor(this, R.color.wheel_8), new int[]{10}, 80, ContextCompat.getColor(this, R.color.wheel_b));
        luckyWheelView.setData(data);
        spin_btn.setEnabled(false);

        spin_btn.setEnabled(true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //
        spin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spin_btn.setEnabled(false);
                isSpin = true;
                final int random = new Random().nextInt((9 - 0) + 1) + 0;
                luckyWheelView.startLuckyWheelWithTargetIndex(random);
            }
        });


        workInt();
        IntData();

        ADS_CLASS.AdmobVideoAdLoad(activity);
        ADS_CLASS.initVungle(activity);
        ADS_CLASS.loadAdVungle(activity);
        ADS_CLASS.initRewardedAdAdColony(activity);
        ADS_CLASS.AdcolonyInitInterstitialAd();
        ADS_CLASS.LoadStartAppInterstitial(activity);
        ADS_CLASS.loadAdmobAdINT(activity);
        ADS_CLASS.initVungleInterstitial();
        ADS_CLASS.loadVungleAdInterstitial();
        ADS_CLASS.IronSourceInterstitialInt(activity);

        unity_work_ads = new Unity_Work_Ads(activity);
        String unityGameId = App_Settings.getString(activity, App_Settings.UNITY_GAME_ID);
        unity_work_ads.initializeUnityAds(unityGameId);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void workInt() {

        if (collectReward_first) {
            collectReward_first = false;
            Log.e(TAG, "onCollectRewardStarted: " + random_points);
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onCollectRewardStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity);
            }
        }

    }

    private void setPointsText() {

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

    }


    public void create(int index, String partTitle, int partIcon, int partColor, int[] itemChance, int itemReward, int bColor) {
        LuckyItem item = new LuckyItem(partTitle, partIcon, partColor, itemChance, itemReward, bColor);
        data.add(item);

    }

    private void addPointOption(String point) {


        if (first_time) {
            first_time = false;
            Log.e("onCollectReward", "Complete");
            Log.e("onCollectReward", "Complete" + random_points);

            int counter = Integer.parseInt(collectReward_count_textView.getText().toString());
            if (counter == 0) {
                showDialogPoints(0, "0", counter, point);

            } else {
                showDialogPoints(1, points_text.getText().toString(), counter, point);
            }
        }

    }

    private void showDialogPoints(final int addOrNoAddValue, final String points, final int counter, String point) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_add_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        if (Constant.isNetworkAvailable(activity)) {
            if (addOrNoAddValue == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "showDialogPoints: 0 points");
                    imageView.setImageResource(R.drawable.sad_ic);
                    title_text.setText(getResources().getString(R.string.better_luck_next_time));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.ok_text));
                } else {
                    Log.e("TAG", "showDialogPoints: points");

                    title_text.setText(getResources().getString(R.string.you_win));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.account_add_to_wallet));
                }
            } else {
                Log.e("TAG", "showDialogPoints: chance over");
                imageView.setImageResource(R.drawable.donee);
                title_text.setText(getResources().getString(R.string.today_work_is_over));
                points_text.setVisibility(View.GONE);
                add_btn.setText(getResources().getString(R.string.ok_text));
                //
            }
            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Interstitial Ads Show
                    InterstitialAds();

                    String DAILY_TYPE = "Spin Win Reward";
                    addPOint(point, DAILY_TYPE);

                    first_time = true;
                    collectReward_first = true;
                    poiints = 0;
                    if (addOrNoAddValue == 1) {
                        if (points.equals("0")) {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.SPIN_COUNT, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.SPIN_COUNT));
                            dialog.dismiss();
                        } else {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.SPIN_COUNT, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.SPIN_COUNT));
                            try {
                                int finalPoint;
                                if (points.equals("")) {
                                    finalPoint = 0;
                                } else {
                                    finalPoint = Integer.parseInt(points);
                                }
                                poiints = finalPoint;

                            } catch (NumberFormatException ex) {
                                Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                            }
                            dialog.dismiss();
                        }
                    } else {
                        dialog.dismiss();
                    }
                    if (collectReward_count == Integer.parseInt(App_Settings.getString(activity, App_Settings.INT_ADS_COUNT))) {
                        if (rewardShow) {
                            Log.e(TAG, "onReachTarget: rewaded ads showing method");
                            showDailog();
                            rewardShow = false;
                            interstitialShow = true;
                            collectReward_count = 1;
                        } else if (interstitialShow) {
                            Log.e(TAG, "onReachTarget: interstital ads showing method");
                            rewardShow = true;
                            interstitialShow = false;
                            collectReward_count = 1;
                        }
                    } else {
                        collectReward_count += 1;
                    }
                }
            });
        } else {
            Constant.showInternetErrorDialog(activity);
        }
        dialog.show();
    }


    private void setScratchCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            collectReward_count_textView.setText("" + string);

        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            Date formattedDateTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH);
            String currentDate = dateFormat.format(formattedDateTime);

            UserWorkUpdate(string, currentDate);
        } else {
            Toast.makeText(activity, "Network Not Available", Toast.LENGTH_LONG).show();
        }


        //


    }

    public void showDailog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog_video_ads_show);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn_video_ads);
        AppCompatButton cancel_btn = dialog.findViewById(R.id.cancel_btn_video_ads);
        cancel_btn.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.ic_playtime);
        add_btn.setText("Yes");
        title_text.setText("Watch Full Video");
        points_text.setText("if you not watching this video you will lose 20 rewards");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //  Video Ads
                videoAds();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String DAILY_TYPE = "Spin Win Point";

                AppController.removePoint(activity, App_Settings.getString(activity, App_Settings.MINAS_REWARD_POINT), DAILY_TYPE);
            }
        });

        dialog.show();
    }

    private void UserWorkUpdate(String count, String currentDate) {
        AppController.updateWork(activity, count, currentDate, "spin_count", "last_date_spin");
    }


    private void addPOint(String point, String DAILY_TYPE) {

        if (collectReward_count_textView.getText().toString().equals("0")) {
            finish();
            Toast.makeText(activity, getResources().getString(R.string.today_work_is_over), Toast.LENGTH_SHORT).show();
        } else {
            AppController.addP(activity, point, DAILY_TYPE);
        }

    }

    private void IntData() {

        String Count = Constant.getString(activity, Constant.SPIN_COUNT);
        if (Count.equals("0")) {
            Count = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (Count.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            Date formattedDateTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH);
            String currentDate = dateFormat.format(formattedDateTime);


            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_SPIN);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                //

                Log.e("TAG", "onInit: last date empty part");
                setScratchCount(Constant.getString(activity, Constant.SPIN_COUNT));
                Constant.setString(activity, Constant.LAST_DATE_SPIN, currentDate);

            } else {
                Log.e("TAG", "onInit: last date not empty part");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date current_date = sdf.parse(currentDate);
                    Date lastDate = sdf.parse(last_date);
                    long diff = current_date.getTime() - lastDate.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Difference" + difference_In_Days);
                    if (difference_In_Days > 0) {

                        //
                        Constant.setString(activity, Constant.LAST_DATE_SPIN, currentDate);
                        //
                        //     Constant.setString(activity, Constant.Collect_Reward_COUNT, someEarn_controller.getCollectRewardCount());
                        setScratchCount(Constant.getString(activity, Constant.SPIN_COUNT));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);

                    } else {
                        setScratchCount("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: scracth card in preference part");
            setScratchCount(Count);
        }


    }

    private void videoAds() {
        // Video Ads
        String typeOfAds = App_Settings.getString(activity, App_Settings.SPIN_VIDEO_ADS);
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


    }


    private void InterstitialAds() {

        //  Interstitial Ads Show
        String typeOfAds = App_Settings.getString(activity, App_Settings.SPIN_INT_ADS);
        switch (typeOfAds) {
            case "Admob":
                ADS_CLASS.ShowAdmobAdsINT(activity);
                break;
            case "Vungle":
                ADS_CLASS.playVungleAdInterstitial();
                break;
            case "AdColony":
                ADS_CLASS.AdcolonyDisplayInterstitialAd();

                break;
            case "Startapp":
                ADS_CLASS.startAppInterstitialShow();

                break;
            case "IronSource":
                ADS_CLASS.IronSourceInterstitialShow();

                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoAds();
    }
}