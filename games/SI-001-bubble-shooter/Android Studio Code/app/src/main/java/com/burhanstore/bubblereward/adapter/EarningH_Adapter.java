package com.burhanstore.bubblereward.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanstore.bubblereward.model.Earning_History;
import com.burhanstore.bubblereward.R;

import java.util.List;

public class EarningH_Adapter extends RecyclerView.Adapter<EarningH_Adapter.ApiHolder> {

    private Context context;
    private List<Earning_History> earningHistoryList;


    public EarningH_Adapter(Context context, List<Earning_History> earning_histories) {
        this.context = context;
        earningHistoryList = earning_histories;
    }

    @NonNull
    @Override
    public EarningH_Adapter.ApiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.earning_his_item, parent, false);
        return new EarningH_Adapter.ApiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarningH_Adapter.ApiHolder holder, int position) {

        final Earning_History earning_history = earningHistoryList.get(position);


        if (earning_history.getMinas().equals("true")) {
            holder.rewards.setText("- " + earning_history.getPoints() + " Point");
            holder.rewards.setTextColor(Color.parseColor("#E91E63")); // This sets the text color to red

        }else {
            holder.rewards.setText( earning_history.getPoints() + " Point");
        }
        holder.title.setText(earning_history.getType_earn());
        holder.date.setText(earning_history.getCreated_date());



    }

    @Override
    public int getItemCount() {
        return earningHistoryList.size();
    }

    public class ApiHolder extends RecyclerView.ViewHolder {


        TextView rewards, title, date;

        public ApiHolder(@NonNull View itemView) {
            super(itemView);

            rewards = itemView.findViewById(R.id.rewards);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

        }
    }
}
