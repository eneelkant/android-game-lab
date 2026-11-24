package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.config.API.LEADER_API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.adapter.LeaderboardAdapter;
import com.burhanstore.bubblereward.model.Leaderboard_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderA extends AppCompatActivity {

    Activity activity;
    private Dialog dialogLodding;
    private CircleImageView leader_user_one_img, leader_three_img, leader_two_img;
    RecyclerView list;
    List<Leaderboard_model> model = new ArrayList<>();
    LeaderboardAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);

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

        leader_user_one_img = findViewById(R.id.leader_user_one_img);
        leader_three_img = findViewById(R.id.leader_three_img);
        leader_two_img = findViewById(R.id.leader_two_img);
        AppController.lodingDialog(activity);

        list = findViewById(R.id.RecyclerView);

        fetchDataFromServer();


    }

    private void fetchDataFromServer() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, LEADER_API, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the JSON response here
                        processJsonResponse(response);
                        AppController.DismissLoding();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });


        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }


    private void processJsonResponse(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");
            JSONArray ranksArray = response.getJSONArray("ranks");

            // Get references to your TextViews in the app UI
            TextView rankOneTextView = findViewById(R.id.rankOneTextView);
            TextView rankTwoTextView = findViewById(R.id.rankTwoTextView);
            TextView rankThreeTextView = findViewById(R.id.rankThreeTextView);

            // Iterate through the JSON data
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = feedArray.getJSONObject(i);
                int rank = ranksArray.getInt(i);
                String name = feedObj.getString("name");
                String image = feedObj.getString("image");
                String points = feedObj.getString("points");


                if (activity == null) {
                    return;
                } else {
                    if (rank == 1) {
                        //
                        rankOneTextView.setText(name);
                        Glide.with(activity).load(image).placeholder(R.drawable.app_icon).into(leader_user_one_img);


                    } else if (rank == 2) {
                        //
                        rankTwoTextView.setText(name);
                        Glide.with(activity).load(image).placeholder(R.drawable.app_icon).into(leader_two_img);

                    } else if (rank == 3) {
                        rankThreeTextView.setText(name);
                        Glide.with(activity).load(image).placeholder(R.drawable.app_icon).into(leader_three_img);
                        //
                    } else {

                        Leaderboard_model item = new Leaderboard_model(name, points, image, rank);
                        model.add(item);
                    }
                }


            }


            adapter = new LeaderboardAdapter(model, activity);
            list.setLayoutManager(new LinearLayoutManager(activity));
            list.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}