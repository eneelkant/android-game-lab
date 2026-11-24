package com.burhanstore.bubblereward.activity.includ;

import static com.burhanstore.bubblereward.AppController.TAG;
import static com.burhanstore.bubblereward.config.API.RESET_PASS_API;
import static com.burhanstore.bubblereward.config.API.RESET_PASS_API_PASS;
import static com.burhanstore.bubblereward.utils.Constant.hideKeyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Developer by Burhan Store
 */


public class ResetPasActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    String uNumberApp;
    Activity activity;
    CountryCodePicker ccp;
    private EditText mobile_number, verify_code;
    private AppCompatButton subMit;
    private LinearLayout CheckBtn;
    String otpid;
    private ConstraintLayout otpLayout;
    private TextView titleID, titleID2;
    private CheckBox cbShowPwd;
    private ImageView forgotCloseBtn;
    private int showp = R.drawable.ic_see_light;
    private int hidep = R.drawable.ic_not_see;

    ImageView topImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pas);

        activity = this;

        topImage = findViewById(R.id.CircleImageViewImage);
        Glide.with(activity).load("file:///android_asset/security_app.gif").into(topImage);

        mobile_number = findViewById(R.id.mobile_number);
        CheckBtn = findViewById(R.id.CheckBtn);
        otpLayout = findViewById(R.id.otpLayout);
        verify_code = findViewById(R.id.verify_code);
        titleID2 = findViewById(R.id.titleID2);
        titleID = findViewById(R.id.titleID);
        forgotCloseBtn = findViewById(R.id.forgotCloseBtn);
        cbShowPwd = findViewById(R.id.cbShowPwd);
        subMit = findViewById(R.id.subMit);


        //Phone Number
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(mobile_number);

        // Initialize Firebase
        FirebaseApp.initializeApp(ResetPasActivity.this);
        mAuth = FirebaseAuth.getInstance();

        CheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(activity)) {
                    String Number = ccp.getFullNumberWithPlus().replace(" ", "");
                    String Numberc = mobile_number.getText().toString();

                    if (Number.length() == 0) {
                        mobile_number.setError("Select Country Code");
                        mobile_number.requestFocus();
                    } else if (Numberc.length() == 0) {
                        Toast.makeText(activity, "Type registered Number ", Toast.LENGTH_SHORT).show();
                    } else {
                        hideKeyboard((Activity) activity);

                        AppController.lodingDialog(activity);

                        check_app_user(Number);

                    }
                } else {
                    Constant.showInternetErrorDialog(activity);
                }
            }
        });


        subMit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constant.isNetworkAvailable(activity)) {
                    if (verify_code.getText().toString().isEmpty())
                        Toast.makeText(activity, "Blank Field can not be processed", Toast.LENGTH_LONG).show();
                    else if (verify_code.getText().toString().length() != 6)
                        Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_LONG).show();
                    else {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, verify_code.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                    }
                }
            }
        });

        forgotCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpLayout.setVisibility(View.GONE);
            }
        });

        cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    cbShowPwd.setBackgroundResource(hidep);
                    verify_code.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    cbShowPwd.setBackgroundResource(showp);
                    verify_code.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
    }

    public void check_app_user(String number) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("number", number)
                .add("method", "ResetPass")
                .build();
        Request request = new Request.Builder()
                .url(RESET_PASS_API) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ResetPasActivity.this, "  failed", Toast.LENGTH_SHORT).show();
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
                        if (responseData.contains("Error!")) {
                            Log.d(TAG, "run: " + responseData);
                            Toast.makeText(ResetPasActivity.this, "Type registered Number.", Toast.LENGTH_SHORT).show();

                        } else if (responseData.contains("user successful!")) {
                            Log.d(TAG, "run: " + responseData);

                            //  getUserData(Email);
                            otpLayout.setVisibility(View.VISIBLE);
                            initiateotp(number);
                            uNumberApp = number;


                        } else {
                            Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
            }
        });


    }

    private void initiateotp(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                        Toast.makeText(activity, "code send success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                        otpLayout.setVisibility(View.VISIBLE);

                        titleID.setText("OTP Verification");
                        titleID2.setText("OTP Code");
                        verify_code.setHint("Type Otp");
                        subMit.setText("Check");


                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

        //  Toast.makeText(this, String.valueOf(verify_code.getDrawingTime()), Toast.LENGTH_SHORT).show();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasActivity.this, "Account verified successfully!", Toast.LENGTH_SHORT).show();

                            verify_code.setText("");
                            upPass();
                            otpLayout.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Code is not valid!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    //update pas
    private void upPass() {

        titleID.setText("Change Your Password");
        titleID2.setText("Enter min 6 digit new password");
        verify_code.setHint("Enter min 6 digit new password");
        subMit.setText("Change Password");
        verify_code.setInputType(InputType.TYPE_CLASS_TEXT);
        EditText passd = findViewById(R.id.verify_code);

        subMit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(activity)) {

                    String pass = passd.getText().toString();

                    if (pass.length() == 0) {
                        passd.setError(getResources().getString(R.string.signup_enter_password));
                        passd.requestFocus();
                    } else if (pass.length() < 6) {
                        passd.setError(getResources().getString(R.string.signup_enter_valid_password));
                        passd.requestFocus();
                    } else {
                        hideKeyboard((Activity) activity);
                        AppController.lodingDialog(activity);
                        passUpdate(passd.getText().toString());

                    }
                } else {
                    Constant.showInternetErrorDialog(activity);
                }
            }
        });


    }

    private void passUpdate(String password) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("number", uNumberApp)
                .add("password", password)
                .add("method", "passUpdate")
                .build();
        Request request = new Request.Builder()
                .url(RESET_PASS_API_PASS) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ResetPasActivity.this, "  failed", Toast.LENGTH_SHORT).show();
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

                        if (responseData.contains("successful dd")) {
                            Log.d(TAG, "run: " + responseData);
                            Toast.makeText(ResetPasActivity.this, "Password Change Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPasActivity.this, Login.class));
                            finish();

                        } else {
                            Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
            }
        });

    }
}