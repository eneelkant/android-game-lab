package com.burhanstore.bubblereward;

import static com.burhanstore.bubblereward.AppController.user_main_points;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.burhanstore.bubblereward.activity.LeaderA;
import com.burhanstore.bubblereward.activity.MyprofileActivity;
import com.burhanstore.bubblereward.activity.NotificationA;
import com.burhanstore.bubblereward.utils.ADS_CLASS;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.utils.Constant;
import com.burhanstore.bubblereward.utils.JOB_ADD;
import com.burhanstore.bubblereward.utils.RootChecker;
import com.burhanstore.bubblereward.utils.Unity_Work_Ads;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity;
    private static final String DUAL_APP_ID_999 = "apps.clone.cloud.multiple.space.bit64";
    private static final int APP_PACKAGE_DOT_COUNT = 3; // number of dots present in package name
    private static final char DOT = '.';
    //app Clone
    WebView browser;
    AdView mAdView;
    boolean ads = false;
    SharedPreferences sharedpreferences;
    private Unity_Work_Ads unity_work_ads;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private TextView user_points_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#A5C5FE"));
        }

        setPointsText();

        user_points_text_view = findViewById(R.id.user_points_text_view);
        browser = findViewById(R.id.webView);
        browser.setWebChromeClient(new WebChromeClient());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setAllowFileAccessFromFileURLs(true);
        browser.getSettings().setAllowUniversalAccessFromFileURLs(true);
        browser.addJavascriptInterface(new WebAppInterface(this), "Android");
        browser.loadUrl("file:///android_asset/js/index.html");
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                browser.setVisibility(View.GONE);
                if (!urlx.contains("https")) {
                    viewx.loadUrl(urlx);
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlx));
                    startActivity(intent);
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                browser.setVisibility(View.VISIBLE);
                if (ads) {
                    browser.loadUrl("javascript:integration.itsads(1)");
                }
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
            }

        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        sharedpreferences = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (sharedpreferences.contains("ads")) {
            ads = sharedpreferences.getBoolean("ads", false);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ads) {
                    initAd();
                    browser.loadUrl("javascript:integration.itsads(0)");
                } else {
                    browser.loadUrl("javascript:integration.itsads(1)");
                }

            }
        }, 5000);


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
        JOB_ADD.work_AddClass((Activity) activity);

        //app clone
        if (App_Settings.getString(activity, App_Settings.BLOCK_APP_CLONE).equals("true")) {
            checkAppCloning();
        }
        //Block Vpn Access

        if (App_Settings.getString(activity, App_Settings.BLOCK_VPN_ACCESS).equals("true")) {
            //vpn detector
            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            checkNetworkAndVPN();
            if (is_VPN_connected()) {
                String dialogText = getResources().getString(R.string.vpn_dialog_text);
                showBlockedDialog(dialogText);
            }
            //vpn detector
        }

        if (App_Settings.getString(activity, App_Settings.BLOCK_ROOTED_DEVICE).equals("true")) {
            RootChecker.isDeviceRooted();
            if (RootChecker.isDeviceRooted()) {
                String dialogText = getResources().getString(R.string.Rooted_Devices_dialog_Text);
                showBlockedDialog(dialogText);
            }
        }

        RelativeLayout bell = findViewById(R.id.bell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NotificationA.class);
                startActivity(i);
            }
        });

        ImageView ProfileBtn = findViewById(R.id.ProfileBtn);
        ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MyprofileActivity.class);
                startActivity(i);
            }
        });
        ImageView LeaderBtn = findViewById(R.id.LeaderBtn);
        LeaderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, LeaderA.class);
                startActivity(i);
            }
        });


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


        //Home Dialog Message Control
        HomeDialog();
        //Maintenance
        MaintenanceDialog();

        mAdView = findViewById(R.id.adView);
        String adUnitId = App_Settings.getString(activity, App_Settings.ADMOB_BANNER_ID); // Replace with your actual ad unit ID
        if (mAdView.getAdUnitId() == null) {
            mAdView.setAdUnitId(adUnitId);
        }
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Ad loaded callback
            }

            @Override
            public void onAdOpened() {
                // Ad opened callback
            }

            @Override
            public void onAdClicked() {
                // Ad clicked callback
            }

            @Override
            public void onAdClosed() {
                // Ad closed callback
            }
        });


        //


    }


    private void HomeDialog() {

        if (App_Settings.getString(activity, App_Settings.HOME_DIALOG_SHOW).equals("true")) {
            RelativeLayout ExitDialog = findViewById(R.id.ExitDialog);
            ExitDialog.setVisibility(View.VISIBLE);

            TextView title_text = findViewById(R.id.title_text_points);
            TextView points_text = findViewById(R.id.points);
            AppCompatButton cancel_btn = findViewById(R.id.cancel_btn);
            AppCompatButton yes_btn = findViewById(R.id.add_btn);

            title_text.setText(App_Settings.getString(activity, App_Settings.HOME_DIALOG_MESSAGE));
            points_text.setVisibility(View.GONE);
            cancel_btn.setVisibility(View.VISIBLE);
            cancel_btn.setText(getResources().getString(R.string.no));
            yes_btn.setText(getResources().getString(R.string.yes));

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExitDialog.setVisibility(View.GONE);
                }
            });

            yes_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (App_Settings.getString(activity, App_Settings.HOME_DIALOG_URL_CONTROL).equals("true")) {
                        Uri uri = Uri.parse(App_Settings.getString(activity, App_Settings.HOME_DIALOG_URL));
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }

                }
            });
        }


    }

    private void MaintenanceDialog() {

        if (App_Settings.getString(activity, App_Settings.MAINTENANCE_POP_SHOW).equals("true")) {
            RelativeLayout ExitDialog = findViewById(R.id.ExitDialog);
            RelativeLayout Main_section = findViewById(R.id.Main_section);
            ExitDialog.setVisibility(View.VISIBLE);
            Main_section.setVisibility(View.GONE);

            TextView title_text = findViewById(R.id.title_text_points);
            TextView points_text = findViewById(R.id.points);
            AppCompatButton cancel_btn = findViewById(R.id.cancel_btn);
            AppCompatButton yes_btn = findViewById(R.id.add_btn);

            title_text.setText(App_Settings.getString(activity, App_Settings.MAINTENANCE_POP_MESSAGE));
            points_text.setVisibility(View.GONE);
            cancel_btn.setVisibility(View.VISIBLE);
            cancel_btn.setText(getResources().getString(R.string.no));
            yes_btn.setText(getResources().getString(R.string.yes));

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            yes_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (App_Settings.getString(activity, App_Settings.MAINTENANCE_POP_URL_CONTROL).equals("true")) {
                        Uri uri = Uri.parse(App_Settings.getString(activity, App_Settings.MAINTENANCE_POP_URL));
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }

                }
            });
        }


    }


    public void setPointsText() {

    }

    @Override
    protected void onResume() {
        AppController.getInstance();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        RelativeLayout ExitDialog = findViewById(R.id.ExitDialog);
        ExitDialog.setVisibility(View.VISIBLE);

        TextView title_text = findViewById(R.id.title_text_points);
        TextView points_text = findViewById(R.id.points);
        AppCompatButton cancel_btn = findViewById(R.id.cancel_btn);
        AppCompatButton yes_btn = findViewById(R.id.add_btn);

        title_text.setText(getResources().getString(R.string.exit_text));
        points_text.setVisibility(View.GONE);
        cancel_btn.setVisibility(View.VISIBLE);
        cancel_btn.setText(getResources().getString(R.string.no));
        yes_btn.setText(getResources().getString(R.string.yes));

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitDialog.setVisibility(View.GONE);
            }
        });

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitDialog.setVisibility(View.GONE);
                finish();
            }
        });
    }


    public void showBlockedDialog(String dialogText) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_app_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.ic_close);
        title_text.setText(dialogText);
        points_text.setVisibility(View.GONE);
        add_btn.setText(" Ok ");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialog.show();
    }

    //end Fraud & Prevention


    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //
    public void initAd() {

    }

    public void showBanner() {
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setVisibility(View.VISIBLE);
    }


    public void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public void launchSharer(String msg, String titlemsg) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, titlemsg);
            String shareMessage = msg + "\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
        }
    }

    public void launchTextSharer(String msg, String titlemsg) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, titlemsg);
            String shareMessage = msg;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
        }
    }

    public class WebAppInterface {

        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showAd() {


        }

        @JavascriptInterface
        public void showAndroidBanner() {
            //  bannerCanShow = true;
        }

        @JavascriptInterface
        public void hideAndroidBanner() {
            //   mustDestroyBanner = true;
        }

        @JavascriptInterface
        public void removeAds() {
            //orderRemoveAds();
        }

        @JavascriptInterface
        public void showRewardedAd() {
            //rewardedAdCanShow = true;
        }

        @JavascriptInterface
        public void successAlert() {
            sweetAlertDialog();
        }


        @JavascriptInterface
        public void showAlert(String alerttext) {
            Toast.makeText(getApplicationContext(), alerttext, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void ProfileOption() {


        }

        @JavascriptInterface
        public void portrait() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        @JavascriptInterface
        public void landscape() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @JavascriptInterface
        public void rateThisApp() {
            launchMarket();
        }

        @JavascriptInterface
        public void shareText(String txt, String title) {
            launchTextSharer(txt, title);
        }

        @JavascriptInterface
        public void shareThisApp(String txt, String title) {
            launchSharer(txt, title);
        }

    }

    private void sweetAlertDialog() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //
                AppController.addP(activity, App_Settings.getString(activity, App_Settings.GAME_COMPLETE_REWARD), "Game Level Complete");
                user_main_points(user_points_text_view);

                if (App_Settings.getString(activity, App_Settings.GAME_COMPLETE_SHOW_VI_OR_INT_ADS).equals("Interstitial")) {

                    //  Interstitial Ads Show
                    String typeOfAds = App_Settings.getString(activity, App_Settings.GAME_PLAY_TO_SHOW_ADS_INT);
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

                } else {
                    // Video Ads
                    String typeOfAds = App_Settings.getString(activity, App_Settings.GAME_PLAY_TO_SHOW_ADS_VIDEO);
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
            }
        });


    }


    //app Clone
    private void checkAppCloning() {
        String path = getFilesDir().getPath();
        if (path.contains(DUAL_APP_ID_999)) {
            killProcess();
        } else {
            int count = getDotCount(path);
            if (count > APP_PACKAGE_DOT_COUNT) {
                killProcess();
            }
        }
    }

    private int getDotCount(String path) {
        int count = 0;
        for (int i = 0; i < path.length(); i++) {
            if (count > APP_PACKAGE_DOT_COUNT) {
                break;
            }
            if (path.charAt(i) == DOT) {
                count++;
            }
        }
        return count;
    }

    private void killProcess() {
        finish();
        Process.killProcess(Process.myPid());
    }
    //app Clone


    //vpn detector
    public void checkNetworkAndVPN() {

        if (activeNetwork != null && activeNetwork.isConnected()) {

        } else {

            return;
        }
    }

    public Boolean is_VPN_connected() {
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN).isConnectedOrConnecting();
    }
    //vpn detector


}