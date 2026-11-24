package com.burhanstore.bubblereward.utils;

import static android.content.ContentValues.TAG;
import static com.burhanstore.bubblereward.config.API.APP_SETTINGS_API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.utils.shPR.PrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App_Settings {
    private static PrefManager prefManager;

    //
    public static final String SIGN_UP_BONUS = "signup_bonus";
    public static final String REFER_BONUS = "refer_bonus";
    public static final String APP_PRIVACY_URL = "privacy_policy";
    public static final String ONE_SIGNAL_APP_ID = "one_signal_app_id";
    public static final String DAILY_CHECK_REWARD = "daily_check_rewards";
    public static final String INT_ADS_COUNT = "int_ads_count";
    public static final String MINAS_REWARD_POINT = "minas_reward_point";


    //Fraud Prevention:
    public static final String BLOCK_ROOTED_DEVICE = "block_rooted_devices";
    public static final String BLOCK_VPN_ACCESS = "block_vpn_access";
    public static final String BLOCK_APP_CLONE = "block_app_clone";


    public static final String REFER_PAGE_TEXT = "refer_page_text";

    //
    public static final String STARTAPP_ID = "startapp_id";
    public static final String ADMOB_VIDEO_ADS_ID = "admob_video_ads_id";
    public static final String VUNGLE_KEY = "vungle_key";
    public static final String VUNGLE_INT_ADS_ID = "vungle_int_ads_id";
    public static final String ADCOLONY_APP_ID = "adcolony_app_id";
    public static final String ADCOLONY_REWARD_ZONE_ID = "adcolony_reward_zone_id";
    public static final String ADCOLONY_INT_ZONE_ID = "adcolony_int_zone_id";
    public static final String INT_ADMOB_ADS_ID = "admob_int_ads_id";
    public static final String IRONSOURCE_APP_KEY = "ironsource_app_key";
    public static final String UNITY_GAME_ID = "unity_game_id";


    public static final String SPIN_INT_ADS = "spin_int_ads";
    public static final String SPIN_VIDEO_ADS = "spin_video_ads";


    //
    public static final String MAINTENANCE_POP_SHOW = "maintenance_popup";
    public static final String MAINTENANCE_POP_MESSAGE = "maintenance_popup_message";
    public static final String MAINTENANCE_POP_URL = "maintenance_popup_url";
    public static final String MAINTENANCE_POP_URL_CONTROL = "maintenance_popup_url_control";

    //
    public static final String HOME_DIALOG_SHOW = "home_dialog_show";
    public static final String HOME_DIALOG_MESSAGE = "home_dialog_message";
    public static final String HOME_DIALOG_URL = "home_dialog_url";
    public static final String HOME_DIALOG_URL_CONTROL = "home_dialog_url_control";


    //game complete to show int ad or video ad
    public static final String GAME_COMPLETE_SHOW_VI_OR_INT_ADS = "game_complete_to_show";
    public static final String GAME_PLAY_TO_SHOW_ADS_INT = "game_lvl_show_ads_int";
    public static final String GAME_PLAY_TO_SHOW_ADS_VIDEO = "game_lvl_show_ads_video";
    public static final String SPIN_AVA_CHA = "spin_ava_ch";
    public static final String GAME_COMPLETE_REWARD = "game_level_com_re";
    public static final String ADMOB_BANNER_ID = "admob_banner_id";
    public static final String DAILY_CHECKING_VIDEO_ADS = "daily_checking_video_ads";

    public static void setString(Context context, String preKey, String setString) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        prefManager.setString(preKey, setString);
    }

    public static String getString(Context context, String prefKey) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager.getString(prefKey);
    }


    public static void GetAppSettingsDataTask() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APP_SETTINGS_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<AppSettingsResponse> call = apiService.getAppSettingsData("1");
        call.enqueue(new Callback<AppSettingsResponse>() {
            @Override
            public void onResponse(Call<AppSettingsResponse> call, Response<AppSettingsResponse> response) {
                if (response.isSuccessful()) {
                    AppSettingsResponse appSettings = response.body();
                    if (appSettings != null) {
                        // Handle app settings here


                        String signup_bonus = appSettings.getSignupBonus();
                        String refer_bonus = appSettings.getReferBonus();
                        String privacy_policy = appSettings.getPrivacyPolicy();
                        String one_signal_app_id = appSettings.getOneSignalAppId();
                        String daily_check_rewards = appSettings.getDailyCheckRewards();
                        String int_ads_count = appSettings.getIntAdsCount();
                        String minas_reward_point = appSettings.getMinasRewardPoint();
                        String block_rooted_devices = appSettings.getBlockRootedDevices();
                        String block_vpn_access = appSettings.getBlockVpnAccess();
                        String block_app_clone =  appSettings.getBlockAppClone();
                        String refer_page_text =  appSettings.getReferPageText();
                        String startapp_id =  appSettings.getStartAppId();
                        String admob_video_ads_id =  appSettings.getAdmobVideoAdsId();
                        String vungle_key = appSettings.getVungleKey();
                        String vungle_int_ads_id =  appSettings.getVungleIntAdsId();
                        String adcolony_app_id =  appSettings.getAdcolonyAppId();
                        String adcolony_reward_zone_id =  appSettings.getAdcolonyRewardZoneId();
                        String adcolony_int_zone_id =  appSettings.getAdcolonyIntZoneId();
                        String admob_int_ads_id = appSettings.getAdmobIntAdsId();
                        String ironsource_app_key =  appSettings.getIronsourceAppKey();
                        String unity_game_id =  appSettings.getUnityGameId();
                        String spin_int_ads =  appSettings.getSpinIntAds();
                        String spin_video_ads =  appSettings.getSpinVideoAds();
                        String maintenance_popup =  appSettings.getMaintenancePopup();
                        String maintenance_popup_message =  appSettings.getMaintenancePopupMessage();
                        String maintenance_popup_url =  appSettings.getMaintenancePopupUrl();
                        String maintenance_popup_url_control =  appSettings.getMaintenancePopupUrlControl();
                        String home_dialog_show =  appSettings.getHomeDialogShow();
                        String home_dialog_message =  appSettings.getHomeDialogMessage();
                        String home_dialog_url =  appSettings.getHomeDialogUrl();
                        String home_dialog_url_control =  appSettings.getHomeDialogUrlControl();
                        String game_complete_to_show =  appSettings.getGameCompleteToShow();
                        String game_lvl_show_ads_int =  appSettings.getGameLevelShowAdsInt();
                        String spin_ava_ch =  appSettings.getSpinAvailableChannels();
                        String game_level_com_re = appSettings.getGameLevelCompleteReward();
                        String admob_banner_id =  appSettings.getAdmobBannerId();

                        //store user data
                        App_Settings.setString(AppController.getInstance(), App_Settings.SIGN_UP_BONUS, signup_bonus);
                        App_Settings.setString(AppController.getInstance(), App_Settings.REFER_BONUS, refer_bonus);
                        App_Settings.setString(AppController.getInstance(), App_Settings.APP_PRIVACY_URL, privacy_policy);
                        App_Settings.setString(AppController.getInstance(), App_Settings.ONE_SIGNAL_APP_ID, one_signal_app_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.DAILY_CHECK_REWARD, daily_check_rewards);
                        App_Settings.setString(AppController.getInstance(), App_Settings.INT_ADS_COUNT, int_ads_count);
                        App_Settings.setString(AppController.getInstance(), App_Settings.MINAS_REWARD_POINT, minas_reward_point);
                        App_Settings.setString(AppController.getInstance(), App_Settings.BLOCK_ROOTED_DEVICE, block_rooted_devices);
                        App_Settings.setString(AppController.getInstance(), App_Settings.BLOCK_VPN_ACCESS, block_vpn_access);
                        App_Settings.setString(AppController.getInstance(), App_Settings.BLOCK_APP_CLONE, block_app_clone);
                        App_Settings.setString(AppController.getInstance(), App_Settings.REFER_PAGE_TEXT, refer_page_text);
                        App_Settings.setString(AppController.getInstance(), App_Settings.STARTAPP_ID, startapp_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.ADMOB_VIDEO_ADS_ID, admob_video_ads_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.VUNGLE_KEY, vungle_key);
                        App_Settings.setString(AppController.getInstance(), App_Settings.VUNGLE_INT_ADS_ID, vungle_int_ads_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.ADCOLONY_APP_ID, adcolony_app_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.ADCOLONY_REWARD_ZONE_ID, adcolony_reward_zone_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.ADCOLONY_INT_ZONE_ID, adcolony_int_zone_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.INT_ADMOB_ADS_ID, admob_int_ads_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.IRONSOURCE_APP_KEY, ironsource_app_key);
                        App_Settings.setString(AppController.getInstance(), App_Settings.UNITY_GAME_ID, unity_game_id);
                        App_Settings.setString(AppController.getInstance(), App_Settings.SPIN_INT_ADS, spin_int_ads);
                        App_Settings.setString(AppController.getInstance(), App_Settings.SPIN_VIDEO_ADS, spin_video_ads);
                        App_Settings.setString(AppController.getInstance(), App_Settings.MAINTENANCE_POP_SHOW, maintenance_popup);
                        App_Settings.setString(AppController.getInstance(), App_Settings.MAINTENANCE_POP_MESSAGE, maintenance_popup_message);
                        App_Settings.setString(AppController.getInstance(), App_Settings.MAINTENANCE_POP_URL, maintenance_popup_url);
                        App_Settings.setString(AppController.getInstance(), App_Settings.MAINTENANCE_POP_URL_CONTROL, maintenance_popup_url_control);
                        App_Settings.setString(AppController.getInstance(), App_Settings.HOME_DIALOG_SHOW, home_dialog_show);
                        App_Settings.setString(AppController.getInstance(), App_Settings.HOME_DIALOG_MESSAGE, home_dialog_message);
                        App_Settings.setString(AppController.getInstance(), App_Settings.HOME_DIALOG_URL, home_dialog_url);
                        App_Settings.setString(AppController.getInstance(), App_Settings.HOME_DIALOG_URL_CONTROL, home_dialog_url_control);
                        App_Settings.setString(AppController.getInstance(), App_Settings.GAME_COMPLETE_SHOW_VI_OR_INT_ADS, game_complete_to_show);
                        App_Settings.setString(AppController.getInstance(), App_Settings.GAME_PLAY_TO_SHOW_ADS_INT, game_lvl_show_ads_int);
                        App_Settings.setString(AppController.getInstance(), App_Settings.SPIN_AVA_CHA, spin_ava_ch);
                        App_Settings.setString(AppController.getInstance(), App_Settings.GAME_COMPLETE_REWARD, game_level_com_re);
                        App_Settings.setString(AppController.getInstance(), App_Settings.ADMOB_BANNER_ID, admob_banner_id);

                        Log.d("AppSettings", "data list " + appSettings.getSignupBonus());
                    } else {
                        Log.d("AppSettings error", "Signup Bonus: " + appSettings.getSignupBonus());
                    }
                } else {
                    Log.d("AppSettings err", "Signup Bonus: ");
                }
            }

            @Override
            public void onFailure(Call<AppSettingsResponse> call, Throwable t) {
                // Handle network failure
            }
        });
    }
}
