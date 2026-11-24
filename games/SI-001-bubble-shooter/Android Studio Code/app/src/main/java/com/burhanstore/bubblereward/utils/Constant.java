package com.burhanstore.bubblereward.utils;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.burhanstore.bubblereward.MainActivity;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.activity.ContactA;
import com.burhanstore.bubblereward.activity.includ.LoginView;
import com.burhanstore.bubblereward.config.API;
import com.burhanstore.bubblereward.utils.shPR.PrefManager;
import com.burhanstore.bubblereward.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class Constant {
    private static PrefManager prefManager;

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "username";
    public static final String USER_FULL_NAME = "name";
    public static final String USER_PHONE = "phone";
    public static final String USER_IMAGE = "image";
    public static final String USER_PASSWORD = "password";
    public static final String USER_REFER = "refer";
    public static final String USER_EMAIL = "email";
    public static final String USER_DEVICE = "device";
    public static final String USER_COUNTRY = "user_country";
    public static final String USER_CREATED_DATE = "created_date";
    public static final String USER_POINTS = "points";
    public static final String TOTAL_REFER = "total_refer";
    public static final String NOTICE_BADGE = "notice_badge";
    public static final String USER_STATUS = "status";
    public static final String DAILY_CHECK_DATE_STORE = "daily_date_store";

    public static final String SPIN_COUNT = "spin_count";
    public static final String LAST_DATE_SPIN = "last_date_spin";

    public static void setString(Context context, String preKey, String setString) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        prefManager.setString(preKey, setString);
    }

    public static String getString(Context context, String prefKey) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager.getString(prefKey);
    }


    public static boolean isValidEmailAddress(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean isMatches = pattern.matcher(email).matches();
        Log.e("Boolean Value", "" + isMatches);
        return isMatches;
    }

    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void showInternetErrorDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.internet_error_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        imageView.setImageResource(R.drawable.ic_internet);
        title_text.setText("No Internet Connection please check your Internet Connection and Try again.");
        points_text.setVisibility(View.GONE);
        add_btn.setText(context.getResources().getString(R.string.ok_text));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
