package com.burhanstore.bubblereward.adapter;

import static com.burhanstore.bubblereward.config.API.IMAGE_PATH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.burhanstore.bubblereward.model.Payment_Package;
import com.burhanstore.bubblereward.R;

import java.util.List;

public class Payment_PackageAdapter extends RecyclerView.Adapter<Payment_PackageAdapter.CategoryViewHolder> {

    private Context mCtx;
    private List<Payment_Package> rewardModelList;

    public Payment_PackageAdapter(Context mCtx, List<Payment_Package> reward_models) {
        this.mCtx = mCtx;
        this.rewardModelList = reward_models;
    }

    @NonNull
    @Override
    public Payment_PackageAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.new_wallet_item_package, null);
        return new Payment_PackageAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Payment_PackageAdapter.CategoryViewHolder holder, int position) {
        final Payment_Package reward_model = rewardModelList.get(position);

        holder.amount.setText(reward_model.getAmount() + reward_model.getSymbol());
        Glide.with(mCtx).load(IMAGE_PATH + reward_model.getImage()).into(holder.icon_image);
        holder.coins.setText(reward_model.getCoins());
        holder.redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(reward_model);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView icon_image;
        TextView amount, coins;
        RelativeLayout redeemBtn;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            icon_image = itemView.findViewById(R.id.img);
            amount = itemView.findViewById(R.id.amount);
            coins = itemView.findViewById(R.id.coins);
            redeemBtn = itemView.findViewById(R.id.redeemBtn);

        }
    }

    private Payment_PackageAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Payment_Package wallet_model);
    }

    public void setOnItemClickListener(Payment_PackageAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}