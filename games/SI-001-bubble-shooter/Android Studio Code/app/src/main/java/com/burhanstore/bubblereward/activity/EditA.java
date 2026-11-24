package com.burhanstore.bubblereward.activity;

import static com.burhanstore.bubblereward.config.API.API;
import static com.burhanstore.bubblereward.config.API.UPDATE_PR;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.burhanstore.bubblereward.AppController;
import com.burhanstore.bubblereward.MainActivity;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditA extends AppCompatActivity {


    private EditText NameIM, PasswordImp;
    private CheckBox my_checkbox;
    Activity activity;
    private LinearLayout SubmitBtn;
    private final int PICK_IMAGE_REQUEST = 22;
    private ArrayList<String> filePaths;
    private AppCompatImageView screenShotView1;
    private TextView uploTextOTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        activity = this;
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.parseColor("#5D7195"));
        }

        my_checkbox = findViewById(R.id.my_checkbox);
        screenShotView1 = findViewById(R.id.screenShotView1);
        SubmitBtn = findViewById(R.id.SubmitBtn);
        uploTextOTwo = findViewById(R.id.uploTextOTwo);
        NameIM = findViewById(R.id.NameIM);
        PasswordImp = findViewById(R.id.PasswordImp);

        my_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Do something when the checkbox is checked
                    my_checkbox.setChecked(true);

                } else {
                    // Do something when the checkbox is unchecked

                }
            }
        });
        filePaths = new ArrayList<>();

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeNmae = NameIM.getText().toString().trim();
                String PasswordIType = PasswordImp.getText().toString().trim();

                if (my_checkbox.isChecked()) {
                    AppController.lodingDialog(activity);

                    updatePrpdasd(typeNmae, PasswordIType);

                } else {
                    Toast.makeText(activity, "Please accept Terms and Conditions to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });


        uploTextOTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    pickImage();
                }
            }
        });

        AppCompatImageView name_Back_btn = findViewById(R.id.name_Back_btn);
        name_Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        NameIM.setHint(Constant.getString(activity, Constant.USER_NAME));
    }

    //Permission
    public boolean isStoragePermissionGranted() {
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            Log.e("TAG", "onActivityResult: selectedImage" + selectedImage);
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            Log.e("TAG", "onActivityResult: bmp" + bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (filePaths.size() < 3) {
                    filePaths.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
                    screenShotView1.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void updatePrpdasd(String name, String passwor) {

        StringRequest stringReq = new StringRequest(Request.Method.POST, UPDATE_PR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response here
                       AppController.DismissLoding();
                        Toast.makeText(activity, "Update Success", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(activity, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response here

                AppController.DismissLoding();
                Toast.makeText(activity, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // Add your parameters here
                params.put("updateProfileHEtre", "any");
                params.put("user_id", Constant.getString(activity, Constant.USER_ID));
                params.put("name", name);
                params.put("password", passwor);
                params.put("admin_url", API);


                for (int i = 0; i < filePaths.size(); i++) {
                    String key = "imgs" + (i + 1);
                    String imageString = filePaths.get(i);
                    if (imageString == null) {
                        params.put(key, "");
                    } else {
                        params.put(key, imageString);
                    }
                }

                return params;
            }
        };

        // Make sure to initialize and use the appropriate Volley RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringReq);


    }

}