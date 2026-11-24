package com.burhanstore.bubblereward.activity.includ;

import static com.burhanstore.bubblereward.AppController.fetchUserData;
import static com.burhanstore.bubblereward.config.API.LOGIN_API;
import static com.burhanstore.bubblereward.utils.Constant.hideKeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.config.API;
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.utils.ApiResponse;
import com.burhanstore.bubblereward.utils.ApiService;
import com.burhanstore.bubblereward.utils.Constant;
import com.burhanstore.bubblereward.utils.NetworkUtils;
import com.burhanstore.bubblereward.utils.RetrofitClient;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Developer by Burhan Store
 */
public class Login extends AppCompatActivity {

    private ImageView topImage;
    private Dialog dialog;
    //
    private Activity activity;
    private CheckBox cbShowPwd;
    private int showp = R.drawable.ic_see_light;
    private int hidep = R.drawable.ic_not_see;
    private LinearLayout LoginBtn;
    private EditText email_edit_text, password_edit_text;
    private TextView ForgotPassword;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        topImage = findViewById(R.id.topImage);
        Glide.with(activity).load("file:///android_asset/security_app.gif").into(topImage);

        cbShowPwd = findViewById(R.id.cbShowPwd);
        LoginBtn = findViewById(R.id.LoginBtn);
        ForgotPassword = findViewById(R.id.ForgotPassword);
        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_text = findViewById(R.id.PasswordInput);

        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    cbShowPwd.setBackgroundResource(hidep);
                    password_edit_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    cbShowPwd.setBackgroundResource(showp);
                    password_edit_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, ResetPasActivity.class);
                startActivity(i);
            }
        });


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constant.isNetworkAvailable(activity)) {
                    String email = email_edit_text.getText().toString().trim();
                    String passwordInput = password_edit_text.getText().toString().trim();
                    if (email.length() == 0) {
                        email_edit_text.setError(getResources().getString(R.string.account_enter_email));
                        email_edit_text.requestFocus();
                    } else if (!Constant.isValidEmailAddress(email)) {
                        email_edit_text.setError(getResources().getString(R.string.signup_enter_valid_email));
                        email_edit_text.requestFocus();
                    } else if (passwordInput.length() == 0) {
                        password_edit_text.setError(getResources().getString(R.string.signup_enter_password));
                        password_edit_text.requestFocus();

                    } else if (passwordInput.length() < 6) {
                        password_edit_text.setError(getResources().getString(R.string.signup_enter_valid_password));
                        password_edit_text.requestFocus();
                    } else {
                        hideKeyboard((Activity) activity);
                        showProgressDialog();
                        LoginAPP(email, passwordInput);

                    }
                } else {
                    Constant.showInternetErrorDialog(activity);
                }


            }
        });
    }

    public void showProgressDialog() {

        //
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }

    //app
    private void LoginAPP(String email, String passwordInput) {

        if (NetworkUtils.isSlowConnection(this)) {
            Toast.makeText(activity, "internet connection slow", Toast.LENGTH_SHORT).show();
        } else {
            apiService = RetrofitClient.getLogin().create(ApiService.class);
            Call<ApiResponse> call = apiService.login(email, passwordInput);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse apiResponse = response.body();
                        dialog.dismiss();
                        if (apiResponse.isSuccess()) {
                            // Login successful
                            Toast.makeText(Login.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            fetchUserData(email, activity);
                        } else {
                            // Login failed
                            Toast.makeText(Login.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    // Handle failure
                    Toast.makeText(Login.this, "Server Problem " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


}