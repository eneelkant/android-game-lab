package com.burhanstore.bubblereward;

import static com.burhanstore.bubblereward.config.API.ADD_POINT;
import static com.burhanstore.bubblereward.config.API.REMOVE_POINT;
import static com.burhanstore.bubblereward.config.API.UP_WORK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.burhanstore.bubblereward.activity.ContactA;
import com.burhanstore.bubblereward.activity.includ.LoginView;
import com.burhanstore.bubblereward.utils.ApiService;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.utils.Constant;
import com.burhanstore.bubblereward.utils.RetrofitClient;
import com.burhanstore.bubblereward.utils.UserData;
import com.burhanstore.bubblereward.utils.UserDataApiClient;
import com.onesignal.OneSignal;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Callback;


public class AppController extends Application {
    private static AppController mInstance;
    public static final String TAG = AppController.class.getSimpleName();
    static String Userpoints;
    public static Dialog dialog;
    public static UserDataApiClient userDataApiClient;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        OneSignal.initWithContext(this);
        OneSignal.setAppId(App_Settings.getString(this, App_Settings.ONE_SIGNAL_APP_ID));

        userDataApiClient = new UserDataApiClient();

    }

    private static void setWindowFlag(final int bits, boolean on, AppCompatActivity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    //

    public static Context getContext() {
        return mInstance;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static void lodingDialog(Activity activity) {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public static void DismissLoding() {
        dialog.dismiss();
    }


    public static void user_main_points(final TextView t) {
        final String userID = Constant.getString(getInstance(), Constant.USER_ID);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        retrofit2.Call<Integer> call = apiService.retrieveUserPoints(userID);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(retrofit2.Call<Integer> call, retrofit2.Response<Integer> response) {
                if (response.isSuccessful()) {
                    int points = response.body();
                    t.setText(String.valueOf(points));
                    Constant.setString(getInstance(), Constant.USER_POINTS, String.valueOf(points));
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Integer> call, Throwable t) {

            }


        });

    }

    public static void addP(Activity activity, String addPoint, String TypeEarn) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "addPointInUSer")
                .add("user_id", Constant.getString(activity, Constant.USER_ID))
                .add("add_point", addPoint)
                .add("type_earn", TypeEarn)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(ADD_POINT) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Constant.showInternetErrorDialog(activity);
                        Log.d(TAG, "run: addP failed" + call);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (responseData.contains("Success")) {
                            Log.d(TAG, "run: " + responseData);
                            //  Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
            }
        });
    }


    public static void updateWork(Activity activity, String count_work, String work_time_store, String cloum_Name_count, String cloum_name_time_store) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "updateWork")
                .add("user_id", Constant.getString(activity, Constant.USER_ID))
                .add("count_work", count_work)
                .add("work_time_store", work_time_store)
                .add("cloum_count_name", cloum_Name_count)
                .add("cloum_time_store", cloum_name_time_store)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(UP_WORK) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Constant.showInternetErrorDialog(activity);
                        Log.d(TAG, "failed sss " + call);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (responseData.contains("Success")) {
                            Log.d(TAG, "run: " + responseData);
                            //  Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
            }
        });
    }


    public static void removePoint(Activity activity, String addPoint, String TypeEarn) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "removePoint")
                .add("user_id", Constant.getString(activity, Constant.USER_ID))
                .add("add_point", addPoint)
                .add("type_earn", TypeEarn)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(REMOVE_POINT) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Constant.showInternetErrorDialog(activity);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (responseData.contains("Success")) {
                            Log.d(TAG, "run: " + responseData);
                            //  Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
            }
        });
    }


    //get user data
    public static void fetchUserData(String email, Activity activity) {

        userDataApiClient.getUserData(email, new Callback<UserData>() {
            @Override
            public void onResponse(retrofit2.Call<UserData> call, retrofit2.Response<UserData> response) {
                if (response.isSuccessful()) {
                    UserData userData = response.body();
                    if (userData != null) {
                        // set user data

                        String id = userData.getId();
                        String name = userData.getName();
                        String phone = userData.getPhone();
                        String image = userData.getImage();
                        String username = userData.getUsername();
                        String password = userData.getPassword();
                        String refer = userData.getRefer();
                        String email = userData.getEmail();
                        String device = userData.getDevice_id();
                        String userCountry = userData.getUser_country();
                        String createdDate = userData.getDate_registered();
                        String points = userData.getPoints();
                        String userId = userData.getUser_id();
                        String total_refer = userData.getTotal_refer();
                        String notice_badge = userData.getNotice_badge();
                        String status = userData.getStatus();
                        String daily_date_store = userData.getDaily_date_store();
                        String spin_count = userData.getSpin_count();
                        String last_date_spin = userData.getLast_date_spin();

                        //store user data
                        Constant.setString(getInstance(), Constant.ID, id);
                        Constant.setString(getInstance(), Constant.USER_NAME, name);
                        Constant.setString(getInstance(), Constant.USER_PHONE, phone);
                        Constant.setString(getInstance(), Constant.USER_IMAGE, image);
                        Constant.setString(getInstance(), Constant.USER_FULL_NAME, username);
                        Constant.setString(getInstance(), Constant.USER_PASSWORD, password);
                        Constant.setString(getInstance(), Constant.USER_REFER, refer);
                        Constant.setString(getInstance(), Constant.USER_EMAIL, email);
                        Constant.setString(getInstance(), Constant.USER_DEVICE, device);
                        Constant.setString(getInstance(), Constant.USER_COUNTRY, userCountry);
                        Constant.setString(getInstance(), Constant.USER_CREATED_DATE, createdDate);
                        Constant.setString(getInstance(), Constant.USER_POINTS, points);
                        Constant.setString(getInstance(), Constant.USER_ID, userId);
                        Constant.setString(getInstance(), Constant.TOTAL_REFER, total_refer);
                        Constant.setString(getInstance(), Constant.NOTICE_BADGE, notice_badge);
                        Constant.setString(getInstance(), Constant.USER_STATUS, status);
                        Constant.setString(getInstance(), Constant.DAILY_CHECK_DATE_STORE, daily_date_store);
                        Constant.setString(getInstance(), Constant.SPIN_COUNT, spin_count);
                        Constant.setString(getInstance(), Constant.LAST_DATE_SPIN, last_date_spin);


                        if (status.equals("active")) {
                            Intent i = new Intent(getInstance(), MainActivity.class);
                            activity.startActivity(i);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.finish();
                        } else {
                            blockOp(activity);
                        }


                        Log.d("User Data", "Name: " + userData.getName() + ", Email: " + userData.getEmail());
                    } else {
                        Log.d("user Error", "error");
                    }
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UserData> call, Throwable t) {

                // Handle
                Intent i = new Intent(activity, LoginView.class);
                activity.startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.finish();
                // Handle user not found case or empty JSON array
                Log.d(ContentValues.TAG, "User not found.");
            }
        });


    }


    public static void blockOp(Activity activity) {
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.user_block_dialog, null);
        alert.setView(dialogView);
        alert.setCancelable(false);
        AlertDialog dialogwindow;
        dialogwindow = alert.create();
        dialogwindow.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        AppCompatButton YesBtn = (AppCompatButton) dialogView.findViewById(R.id.YesBtn);
        final TextView deatialsText = (TextView) dialogView.findViewById(R.id.deatialsText);
        deatialsText.setText("your account blocked for some time, please wait and again use our app. thanks you");
        YesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();

            }
        });
        AppCompatButton sendMessage = (AppCompatButton) dialogView.findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ContactA.class);
                activity.startActivity(i);
            }
        });
        alert.setCancelable(false);
        dialogwindow.show();
    }



}
