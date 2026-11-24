package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.fetchUserData;
import static com.burhanstore.bubblereward.BaseUrl.CREATED_BY;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.BuildConfig;
import com.burhanstore.bubblereward.MainActivity;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.activity.includ.LoginView;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.utils.Constant;
import com.burhanstore.bubblereward.utils.JOB_ADD;
import com.burhanstore.bubblereward.utils.RootChecker;
import com.burhanstore.bubblereward.utils.UserData;
import com.burhanstore.bubblereward.utils.UserDataApiClient;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Developer by Burhan Store
 */
public class SplashActivity extends AppCompatActivity {
    String TAG = "SplashActivity";
    private AppUpdateManager appUpdateManager;
    public static final int RC_APP_UPDATE = 101;
    SplashActivity activity;
    public static AlertDialog dialogwindow;
    //app Clone
    private static final String DUAL_APP_ID_999 = "apps.clone.cloud.multiple.space.bit64";
    private static final int APP_PACKAGE_DOT_COUNT = 3; // number of dots present in package name
    private static final char DOT = '.';
    //app Clone
    private CountDownTimer timer;
    private TextView Developer_name;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetwork;
    private UserDataApiClient userDataApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        userDataApiClient = new UserDataApiClient();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("TAG", "onCreate:if part activarte ");
            appUpdateManager = AppUpdateManagerFactory.create(this);
            UpdateApp();
        } else {
            Log.e("TAG", "onCreate:else part activarte ");
            intappdata();
        }
        intappdata();

        Developer_name = findViewById(R.id.Developer_name);
        Developer_name.setText(CREATED_BY);


        StartAppSDK.init(this, App_Settings.getString(activity, App_Settings.STARTAPP_ID), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(this, false);
        StartAppSDK.setTestAdsEnabled(false);


        App_Settings.GetAppSettingsDataTask();

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


    }

    public void UpdateApp() {
        try {
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                            && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                        try {
                            appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo, IMMEDIATE, activity, RC_APP_UPDATE);
                            Log.e("TAG", "onCreate:startUpdateFlowForResult part activarte ");
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", "onCreate:startUpdateFlowForResult else part activarte ");
                        intappdata();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "onCreate:addOnFailureListener else part activarte ");
                    intappdata();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, IMMEDIATE, activity, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                intappdata();
            } else {
                intappdata();
            }
        }
    }


    public void intappdata() {
        fetchUserData(Constant.getString(activity, Constant.USER_EMAIL), activity);
        JOB_ADD.work_AddClass((Activity) activity);

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


}