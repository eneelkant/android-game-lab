package com.burhanstore.bubblereward.adapter;

import static com.burhanstore.bubblereward.config.API.IMAGE_PATH;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.burhanstore.bubblereward.model.Reward_model;
import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.activity.RedeemA;

import java.util.List;

public class WalletReward_adapter extends RecyclerView.Adapter<WalletReward_adapter.CategoryViewHolder> {

    private Context mCtx;
    private List<Reward_model> rewardModelList;

    public WalletReward_adapter(Context mCtx, List<Reward_model> reward_models) {
        this.mCtx = mCtx;
        this.rewardModelList = reward_models;
    }

    @NonNull
    @Override
    public WalletReward_adapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.new_wallet_item, null);
        return new WalletReward_adapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletReward_adapter.CategoryViewHolder holder, int position) {
        final Reward_model reward_model = rewardModelList.get(position);

        holder.title.setText(reward_model.getName());
        holder.point.setText(reward_model.getSymbol());
        Glide.with(mCtx).load(IMAGE_PATH + reward_model.getImage()).into(holder.icon_image);
        holder.redeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, RedeemA.class);
                intent.putExtra("id", reward_model.getId());
                intent.putExtra("hint", reward_model.getHint());
                intent.putExtra("details", reward_model.getDetails());
                intent.putExtra("symbol", reward_model.getSymbol());
                intent.putExtra("name", reward_model.getName());
                intent.putExtra("image", reward_model.getImage());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView icon_image;
        TextView title, point;
        RelativeLayout redeemBtn;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            icon_image = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            point = itemView.findViewById(R.id.points);
            redeemBtn = itemView.findViewById(R.id.redeemBtn);

        }
    }
}
