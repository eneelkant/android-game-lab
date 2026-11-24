package com.burhanstore.bubblereward.fragment;

import static com.burhanstore.bubblereward.config.API.PAYMENT_REUQEST;
import static com.burhanstore.bubblereward.utils.ADS_CLASS.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.burhanstore.bubblereward.MainActivity;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.utils.Constant;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Dialog_Reward_Wallet extends DialogFragment {
    View view;
    EditText pdReward;
    TextView valueReward;
    String p_detailsReward;
    AppCompatButton closeRewardR, redeemBtn;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.dialog_reward_row, container, false);

        pdReward = view.findViewById(R.id.edit_text_redeem);
        closeRewardR = view.findViewById(R.id.close_redeem);
        redeemBtn = view.findViewById(R.id.redeemBtn);
        valueReward = view.findViewById(R.id.value_redeem);

        String amount = getArguments().getString("amount");
        String coins = getArguments().getString("coins");
        String symbol = getArguments().getString("symbol");
        String hint = getArguments().getString("hint");
        String name = getArguments().getString("name");


        pdReward.setHint(hint);
        valueReward.setText(coins + " = " + symbol + " " + amount);


        closeRewardR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Context activity = getContext();
        redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payment_info = pdReward.getText().toString().trim();

                if (payment_info.length() == 0) {
                    pdReward.setError(getResources().getString(R.string.type_here));
                    pdReward.requestFocus();
                } else {
                    int myPoit = Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS));
                    int pay_blace = Integer.parseInt(coins);

                    if (myPoit > pay_blace) {
                        //
                        dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_loading);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        redemReepa(coins, amount + symbol, payment_info, name);


                    } else {
                        Toast.makeText(activity, "insufficient balance", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        return view;
    }


    private void redemReepa(String payment_coin, String package_amount, String payment_info, String payment_method) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("user_id", Constant.getString(getActivity(), Constant.USER_ID))
                .add("method", "UserPaymentR")
                .add("payment_coin", payment_coin)
                .add("package_amount", package_amount)
                .add("payment_info", payment_info)
                .add("payment_method", payment_method)
                .add("user_name", Constant.getString(getActivity(), Constant.USER_NAME))
                .build();
        Request request = new Request.Builder()
                .url(PAYMENT_REUQEST) // Replace with the actual URL
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Log.d(TAG, "run: " + responseData);
                        if (responseData.contains("successful")) {
                            Log.d(TAG, "run: " + responseData);
                            Toast.makeText(requireActivity(), "Successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        }


                    }
                });
            }
        });

    }


}