package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.config.API.REQUEST_CONTACT;
import static com.burhanstore.bubblereward.utils.ADS_CLASS.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.utils.Constant;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ContactA extends AppCompatActivity {


    private TextInputEditText subject, message;
    private AppCompatButton btn;
    private ImageView back;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        btn = findViewById(R.id.send_btn);
        back = findViewById(R.id.back_img_contact);
        intView();


    }

    private void intView() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subject.getText().toString().length() == 0) {
                    subject.setError("Enter Subject");
                    subject.requestFocus();
                } else if (message.getText().toString().length() == 0) {
                    message.setError("Please Type Message");
                    message.requestFocus();
                } else {
                    if (ContactA.this == null) {
                        return;
                    }

                    String Subject = subject.getText().toString();
                    String Message = message.getText().toString();

                    AppController.lodingDialog(activity);

                    makeReques(Subject, Message, Constant.getString(activity, Constant.USER_NAME), Constant.getString(activity, Constant.USER_ID));
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContactA.this == null) {
                    return;
                }
                ContactA.this.onBackPressed();
            }
        });

    }


    private void makeReques(String subject, String message, String user_name, String user_id) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("method", "requestContact")
                .add("user_id", Constant.getString(activity, Constant.USER_ID))
                .add("subject", subject)
                .add("message", message)
                .add("user_name", user_name)
                .build();
        okhttp3.Request request = new Request.Builder()
                .url(REQUEST_CONTACT) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppController.DismissLoding();
                        Toast.makeText(activity, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppController.DismissLoding();

                        Log.d(TAG, "data show: " + responseData);
                        if (responseData.contains("Success")) {
                            Log.d(TAG, "run: " + responseData);

                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });

    }

}
