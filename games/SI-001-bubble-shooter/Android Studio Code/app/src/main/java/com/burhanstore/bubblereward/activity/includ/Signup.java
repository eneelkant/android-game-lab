package com.burhanstore.bubblereward.activity.includ;

import static android.content.ContentValues.TAG;

import static com.burhanstore.bubblereward.AppController.fetchUserData;
import static com.burhanstore.bubblereward.BaseUrl.ADMIN_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
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
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.config.API;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.utils.Constant;

import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

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
public class Signup extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_PHONE_STATE = 1001;
    private ImageView topImage;
    Activity activity;
    private EditText first_name_type, Surname_type, type_email, type_password, type_referCode, type_number;
    String first_nameStrings, surname_Strings, fullNumberString, passwordString, user_country, androidId, User_app_id, referCodeString, emailStrings;
    CountryCodePicker ccp;
    private LinearLayout signUpBtn;
    private CheckBox cbPShowPwd;
    private int showp = R.drawable.ic_see_light;
    private int hidep = R.drawable.ic_not_see;
    private CheckBox myCheckbox;
    private TextView TermsConditionsBtn;
    private AppCompatImageView name_Back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        topImage = findViewById(R.id.topImage);
        ccp = findViewById(R.id.ccp);
        first_name_type = findViewById(R.id.first_name_type);
        Surname_type = findViewById(R.id.Surname_type);
        type_email = findViewById(R.id.type_email);
        type_password = findViewById(R.id.type_password);
        type_referCode = findViewById(R.id.type_referCode);
        signUpBtn = findViewById(R.id.signUpBtn);
        cbPShowPwd = findViewById(R.id.cbPShowPwd);
        type_number = findViewById(R.id.type_number);
        name_Back_btn = findViewById(R.id.name_Back_btn);

        Glide.with(activity).load("file:///android_asset/security_app.gif").into(topImage);


        cbPShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    cbPShowPwd.setBackgroundResource(hidep);
                    type_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    cbPShowPwd.setBackgroundResource(showp);
                    type_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });

        //
        myCheckbox = findViewById(R.id.my_checkbox);

        TermsConditionsBtn = findViewById(R.id.TermsConditionsBtn);
        TermsConditionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(App_Settings.getString(activity, App_Settings.APP_PRIVACY_URL)));
                startActivity(myIntent);
            }
        });

        myCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Do something when the checkbox is checked
                    myCheckbox.setChecked(true);

                } else {
                    // Do something when the checkbox is unchecked

                }
            }
        });

        name_Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Random random = new Random();
        int randomNumber = random.nextInt(90000000) + 10000000;
        User_app_id = String.valueOf(randomNumber);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myCheckbox.isChecked()) {
                    if (Constant.isNetworkAvailable(activity)) {


                        first_nameStrings = first_name_type.getText().toString().trim();
                        surname_Strings = Surname_type.getText().toString().trim();
                        ccp.registerCarrierNumberEditText(type_number);
                        String numebr = type_number.getText().toString();
                        fullNumberString = ccp.getFullNumberWithPlus().replace(" ", "");
                        emailStrings = type_email.getText().toString().trim();
                        passwordString = type_password.getText().toString().trim();

                        if (first_nameStrings.length() == 0) {
                            first_name_type.setError(getResources().getString(R.string.type_your_first_name));
                            first_name_type.requestFocus();
                        } else if (surname_Strings.length() == 0) {
                            Surname_type.setError(getResources().getString(R.string.type_your_surname));
                            Surname_type.requestFocus();
                        } else if (numebr.length() == 0) {
                            type_number.setError(getResources().getString(R.string.type_your_number));
                            type_number.requestFocus();
                        } else if (emailStrings.length() == 0) {
                            type_email.setError(getResources().getString(R.string.type_your_email_address));

                        } else if (!Constant.isValidEmailAddress(emailStrings)) {
                            type_email.setError(getResources().getString(R.string.signup_enter_valid_email));
                            type_email.requestFocus();
                        } else if (passwordString.length() < 6) {
                            type_password.setError(getResources().getString(R.string.signup_enter_password_6));
                            type_password.requestFocus();
                        } else {
                            AppController.lodingDialog(activity);
                            regOption();

                        }


                    }else {
                        Constant.showInternetErrorDialog(activity);
                    }
                } else {
                    Toast.makeText(activity, "Please accept Terms and Conditions to continue", Toast.LENGTH_SHORT).show();
                }

            }
        });


        TelephonyManager teleMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String localeCountry = teleMgr.getNetworkCountryIso();
        Locale l = new Locale("", localeCountry);
        user_country = l.getDisplayCountry();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            if (androidId == null) {
                androidId = " ";
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (mTelephony.getDeviceId() != null) {
                    androidId = mTelephony.getDeviceId();
                    if (androidId == null) {
                        androidId = " ";
                    }
                } else {
                    androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    if (androidId == null) {
                        androidId = " ";
                    }
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_PHONE_STATE);
            }
        }



    }

    private void regOption() {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email", emailStrings)//
                .add("method", "regUser")
                .add("password", passwordString)
                .add("username", surname_Strings + random())
                .add("name", first_nameStrings + " " + surname_Strings)
                .add("phone", fullNumberString)
                .add("user_country", user_country)
                .add("device_id", androidId)
                .add("refer", random())
                .add("user_id", User_app_id)
                .add("refer_code", type_referCode.getText().toString().trim())
                .add("admin_url", ADMIN_URL)
                .build();

        Request request = new Request.Builder()
                .url(API.SIGNUP_API)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run:  " + responseData);

                        Toast.makeText(activity, responseData, Toast.LENGTH_SHORT).show();

                        if (responseData.equals("Registration Success")) {

                            fetchUserData(Constant.getString(activity, Constant.USER_EMAIL), activity);
                        } else {
                            Log.d(TAG, "run: Registration failed");
                            AppController.DismissLoding();
                            // Toast.makeText(Signup.this, "Registration failed 2", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });

    }


    public static String random() {
        char[] chars1 = "qwertyuioplkjhgfdsazxcvbnm0987654321".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        String generatedString = sb1.toString();
        return generatedString;
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(activity, LoginView.class);
        startActivity(i);
        finish();
    }
}