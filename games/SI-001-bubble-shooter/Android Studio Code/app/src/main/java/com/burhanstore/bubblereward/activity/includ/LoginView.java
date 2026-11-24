package com.burhanstore.bubblereward.activity.includ;

import static android.content.ContentValues.TAG;

import static com.burhanstore.bubblereward.AppController.fetchUserData;
import static com.burhanstore.bubblereward.BaseUrl.ADMIN_URL;
import static com.burhanstore.bubblereward.config.API.REFER_API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.config.API;
import com.burhanstore.bubblereward.utils.App_Settings;
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.BuildConfig;
import com.burhanstore.bubblereward.utils.Constant;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

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
public class LoginView extends AppCompatActivity {

    private LinearLayout createAccountBtn, LoginBtn, google_signin, GuestBtn;
    Activity activity;
    GoogleSignInClient mGoogleSignInClient;
    String androidId, randomNumberString;
    private static final int PERMISSION_REQUEST_PHONE_STATE = 1001;
    private static final int RC_SIGN_IN = 1001;
    private ConstraintLayout referDialog;
    private ImageView skipss;
    private AppCompatButton subMit;
    private EditText referCodeH;
    String user_email;
    private ImageView topImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        StartAppSDK.init(this, App_Settings.getString(activity, App_Settings.STARTAPP_ID), true);
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        StartAppAd.disableSplash();
        StartAppAd.enableConsent(this, false);
        StartAppSDK.setTestAdsEnabled(false);

        createAccountBtn = findViewById(R.id.createAccountBtn);
        LoginBtn = findViewById(R.id.LoginBtn);
        google_signin = findViewById(R.id.google_signin);
        GuestBtn = findViewById(R.id.GuestBtn);
        referDialog = findViewById(R.id.referDialog);
        skipss = findViewById(R.id.skipRefr);
        subMit = findViewById(R.id.subMit);
        referCodeH = findViewById(R.id.referCodeH);

        topImage = findViewById(R.id.topImage);
        Glide.with(activity).load("file:///android_asset/security_app.gif").into(topImage);


        //google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

        Random random = new Random();
        int randomNumber = random.nextInt(90000000) + 10000000;
        randomNumberString = String.valueOf(randomNumber);


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, Signup.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, Login.class);
                startActivity(i);
            }
        });

        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppController.lodingDialog(activity);
                signInGoogle();
            }
        });

        GuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.lodingDialog(activity);
                TelephonyManager teleMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String localeCountry = teleMgr.getNetworkCountryIso();
                Locale l = new Locale("", localeCountry);
                String user_country = l.getDisplayCountry();
                Uri photoUrl = Uri.parse(ADMIN_URL + "api/images/ic_avatar_default_1.png");

                GuestAPI("Guest", "guest@gmail.com", photoUrl, user_country);
            }
        });


    }


    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resulrCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        super.onActivityResult(requestCode, resulrCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            googleSignIn();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void googleSignIn() {
        TelephonyManager teleMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String localeCountry = teleMgr.getNetworkCountryIso();
        Locale l = new Locale("", localeCountry);
        String user_country = l.getDisplayCountry();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(LoginView.this);
        user_email = acct.getEmail();
        googleAPI(acct.getDisplayName(), user_email, acct.getPhotoUrl(), user_country, randomNumberString);
    }

    private void googleAPI(String name, String email, Uri photo_url, String user_country, String user_id) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)//
                .add("method", "regUser")
                .add("password", random())
                .add("username", random())
                .add("name", name)
                .add("phone", randomNumberString)
                .add("user_country", user_country)
                .add("device_id", androidId)
                .add("refer", random())
                .add("user_id", user_id)
                .add("image", String.valueOf(photo_url))
                .add("admin_url", ADMIN_URL)
                .build();

        Request request = new Request.Builder()
                .url(API.GOOGLE_SIGNUP_API)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginView.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run:  app" + responseData);


                        if (responseData.equals("Registration Success")) {

                            ReferDialog(email, user_id);
                            AppController.DismissLoding();
                            Toast.makeText(activity, responseData, Toast.LENGTH_SHORT).show();
                        } else if (responseData.equals("email already registered")) {

                            fetchUserData(email, activity);

                            Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "run: Registration failed");
                            AppController.DismissLoding();
                            Toast.makeText(activity, responseData, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(Signup.this, "Registration failed 2", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });
    }

    private void ReferDialog(String email, String user_id) {
        referDialog.setVisibility(View.VISIBLE);
        skipss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUserData(email, activity);
            }
        });

        subMit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String referS = referCodeH.getText().toString().trim();

                if (referS.length() == 0) {
                    referCodeH.setError(getResources().getString(R.string.type_here));
                    referCodeH.requestFocus();
                } else {
                    AppController.lodingDialog(activity);

                    referReqeust(referS, email, user_id);
                }

            }
        });

    }

    private void referReqeust(String refer_code, String email, String user_id) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("refer_code", refer_code)
                .add("user_id", user_id)
                .add("method", "referRequest")
                .build();
        Request request = new Request.Builder()
                .url(REFER_API) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginView.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();

                Log.d(TAG, "run app: " + responseData);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (responseData.contains("Points added successfully!")) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    fetchUserData(email, activity);
                                    AppController.DismissLoding();
                                }
                            });


                        } else if (responseData.contains("invalid refer")) {

                            AppController.DismissLoding();
                            Toast.makeText(LoginView.this, "Invalid Refer Code", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });


    }


    public static String random() {
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        String generatedString = sb1.toString();
        return generatedString;
    }


    public static String randomPass() {
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++) {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        String generatedString = sb1.toString();
        return generatedString;
    }


    private void GuestAPI(String name, String email, Uri photo_url, String user_country) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)//
                .add("method", "regUser")
                .add("password", random())
                .add("username", random())
                .add("name", name)
                .add("phone", randomNumberString)
                .add("user_country", user_country)
                .add("device_id", androidId)
                .add("refer", random())
                .add("user_id", randomNumberString)
                .add("image", String.valueOf(photo_url))
                .add("admin_url", ADMIN_URL)
                .build();
        Request request = new Request.Builder()
                .url(API.GUEST_API)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginView.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run:  app" + responseData);


                        if (responseData.equals("Registration Success")) {

                            fetchUserData(email, activity);

                            Toast.makeText(activity, responseData, Toast.LENGTH_SHORT).show();
                        } else if (responseData.equals("email already registered")) {

                            fetchUserData(email, activity);
                            Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "run: Registration failed");
                            AppController.DismissLoding();
                            Toast.makeText(activity, responseData, Toast.LENGTH_SHORT).show();
                            // Toast.makeText(Signup.this, "Registration failed 2", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }
        });
    }



}