package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.config.API.NOTIFICATION_API;
import static com.burhanstore.bubblereward.utils.ADS_CLASS.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.adapter.Notification_Adapter;
import com.burhanstore.bubblereward.model.Notification_Model;

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

public class NotificationA extends AppCompatActivity {

    Activity activity;
    private Dialog dialog;

    private RecyclerView recyclerView;
    ArrayList<Notification_Model> notificationModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
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

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        notificationModelArrayList = new ArrayList<>();

        ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loadData();
        } else {
            Toast.makeText(activity, "Network Not Available", Toast.LENGTH_LONG).show();

        }

        AppController.lodingDialog(activity);


    }

    private void loadData() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "getAllNotification")
                .build();

        okhttp3.Request request = new Request.Builder()
                .url(NOTIFICATION_API) // Replace with the actual URL
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
                        notificationModelArrayList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject rlist = jsonArray.getJSONObject(i);
                            notificationModelArrayList.add(new Notification_Model(
                                    rlist.getString("id"),
                                    rlist.getString("title"),
                                    rlist.getString("url"),
                                    rlist.getString("date"),
                                    rlist.getString("message")
                            ));
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppController.DismissLoding();
                                if (!notificationModelArrayList.isEmpty()) {
                                    Notification_Adapter notification_adapter = new Notification_Adapter(activity, notificationModelArrayList);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(notification_adapter);

                                    notification_adapter.setOnItemClickListener(new Notification_Adapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(Notification_Model notification_model) {

                                            Notice_details(notification_model.getTitle(), notification_model.getMessage(), notification_model.getUrl());
                                        }
                                    });

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

    public void Notice_details(String title, String message, String url) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_notification_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView messageText = dialog.findViewById(R.id.message);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.notifications_active);
        title_text.setText(title);
        messageText.setText(message);
        add_btn.setText(activity.getResources().getString(R.string.ok_text));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                dialog.dismiss();
            }
        });
        dialog.show();


    }


}
