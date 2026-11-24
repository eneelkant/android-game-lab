package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.user_main_points;
import static com.burhanstore.bubblereward.config.API.GET_PAYMENT_LIST;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.model.Reward_model;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.adapter.WalletReward_adapter;

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
public class WalletA extends AppCompatActivity {
    RecyclerView recyclerView;

    private ArrayList<Reward_model> wallet_modelArrayList;
    Activity activity;
    private TextView user_points_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Wallet");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //
        setPointsText();

        recyclerView = findViewById(R.id.recyclerView);
        user_points_textView = findViewById(R.id.user_points_textView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            AppController.lodingDialog(activity);
            getData();
        } else {
            Toast.makeText(activity, "Network Not Available", Toast.LENGTH_LONG).show();
        }

    }

    private void setPointsText() {

        Handler handler;
        Runnable refreshRunnable;
        handler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                user_points_textView.setText("0");
                user_main_points(user_points_textView);
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(refreshRunnable);


    }

    private void getData() {
        wallet_modelArrayList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "getWallet")
                .build();

        okhttp3.Request request = new Request.Builder()
                .url(GET_PAYMENT_LIST) // Replace with the actual URL
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
                    final String responseBody = response.body().string();

                    try {
                        JSONObject obj = new JSONObject(responseBody);
                        JSONArray array = obj.getJSONArray("response");


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject rlist = array.getJSONObject(i);

                            wallet_modelArrayList.add(new Reward_model(
                                    rlist.getString("id"),
                                    rlist.getString("name"),
                                    rlist.getString("image"),
                                    rlist.getString("symbol"),
                                    rlist.getString("hint"),
                                    rlist.getString("input_type"),
                                    rlist.getString("details")
                            ));
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppController.DismissLoding();

                                if (!wallet_modelArrayList.isEmpty()) {
                                    WalletReward_adapter walletReward_adapter = new WalletReward_adapter(activity, wallet_modelArrayList);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(walletReward_adapter);
                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                    //  Toast.makeText(activity, "Oops! Nothing found.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
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