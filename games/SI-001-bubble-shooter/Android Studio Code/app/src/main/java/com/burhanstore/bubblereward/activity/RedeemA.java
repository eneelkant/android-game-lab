package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.AppController.user_main_points;
import static com.burhanstore.bubblereward.config.API.GET_PAYMENT_PACKGE_API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.model.Payment_Package;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.adapter.Payment_PackageAdapter;
import com.burhanstore.bubblereward.fragment.Dialog_Reward_Wallet;

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

public class RedeemA extends AppCompatActivity {
    Activity activity;
    String id, hint, details, symbol, name,image;
    private RecyclerView recyclerView;
    private Dialog dialog;
    private ArrayList<Payment_Package> payment_packageArrayList;
    Payment_PackageAdapter payment_package;
    private TextView user_points_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_back));
        int titleColor = ContextCompat.getColor(this, R.color.white);
        toolbar.setTitleTextColor(titleColor);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        hint = bundle.getString("hint");
        details = bundle.getString("details");
        symbol = bundle.getString("symbol");
        name = bundle.getString("name");
        image = bundle.getString("image");


        recyclerView = findViewById(R.id.recyclerview);
        user_points_textView = findViewById(R.id.user_points_textView);

        //dialog
        AppController.lodingDialog(activity);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getData();
        } else {
            Toast.makeText(activity, "Network Not Available", Toast.LENGTH_LONG).show();

        }
        setPointsText();

    }

    private void getData() {

        payment_packageArrayList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "getPaymentPackage")
                .add("payment_id", id)
                .build();

        okhttp3.Request request = new Request.Builder()
                .url(GET_PAYMENT_PACKGE_API) // Replace with the actual URL
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

                            payment_packageArrayList.add(new Payment_Package(
                                    rlist.getString("id"),
                                    rlist.getString("payment_id"),
                                    rlist.getString("amount"),
                                    rlist.getString("coins"),
                                    rlist.getString("symbol"),
                                    image
                            ));
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                AppController.DismissLoding();

                                if (!payment_packageArrayList.isEmpty()) {
                                    payment_package = new Payment_PackageAdapter(activity, payment_packageArrayList);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(payment_package);

                                    payment_package.setOnItemClickListener(new Payment_PackageAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(Payment_Package wallet_model) {

                                            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                                            Dialog_Reward_Wallet newFragment = new Dialog_Reward_Wallet();
                                            Bundle args = new Bundle();
                                            args.putString("amount", wallet_model.getAmount());
                                            args.putString("coins", wallet_model.getCoins());
                                            args.putString("symbol", symbol);
                                            args.putString("hint", hint);
                                            args.putString("name", name);


                                            newFragment.setArguments(args);
                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                            transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

                                        }
                                    });

                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                    Toast.makeText(activity, "Oops! Nothing found.", Toast.LENGTH_SHORT).show();
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
}