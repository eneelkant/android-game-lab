package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.user_main_points;
import static com.burhanstore.bubblereward.config.API.EARNING_HISTORY_API;
import static com.burhanstore.bubblereward.utils.ADS_CLASS.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.model.Earning_History;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.adapter.EarningH_Adapter;
import com.burhanstore.bubblereward.utils.Constant;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Developer by Burhan Store
 */
public class EarningHistoryA extends AppCompatActivity {

    Activity activity;
    private Dialog dialog;

    private RecyclerView recyclerView;
    private TextView user_points_text_view;
    ArrayList<Earning_History> earning_historyArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning_history);

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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        user_points_text_view = findViewById(R.id.user_points_text_view);

        setPointsText();

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        earning_historyArrayList = new ArrayList<>();

        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loadData();
        } else {
            Toast.makeText(activity, "Network Not Available", Toast.LENGTH_LONG).show();

        }

        AppController.lodingDialog(activity);

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


    }

    private void loadData() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "getAllHistory")
                .add("user_id", Constant.getString(activity, Constant.USER_ID))
                .build();

        okhttp3.Request request = new Request.Builder()
                .url(EARNING_HISTORY_API) // Replace with the actual URL
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Request failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        JSONArray jsonArray = jsonResponse.getJSONArray("response");
                        earning_historyArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject rlist = jsonArray.getJSONObject(i);
                            earning_historyArrayList.add(new Earning_History(
                                    rlist.getString("id"),
                                    rlist.getString("points"),
                                    rlist.getString("type_earn"),
                                    rlist.getString("created_date"),
                                    rlist.getString("minas")
                            ));
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppController.DismissLoding();
                                if (!earning_historyArrayList.isEmpty()) {
                                    EarningH_Adapter earningH_adapter = new EarningH_Adapter(activity, earning_historyArrayList);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(earningH_adapter);
                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                    //  Toast.makeText(activity, "Oops! Nothing found.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Error parsing JSON Ads: " + e.getMessage());
                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Response not successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });

    }
}