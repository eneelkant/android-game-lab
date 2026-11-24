package com.burhanstore.bubblereward.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanstore.bubblereward.R;
import com.burhanstore.bubblereward.model.Notification_Model;

import java.util.List;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ApiHolder> {

    private Context context;
    private List<Notification_Model> notification_modelList;


    public Notification_Adapter(Context context, List<Notification_Model> notification_models) {
        this.context = context;
        notification_modelList = notification_models;
    }

    @NonNull
    @Override
    public Notification_Adapter.ApiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new Notification_Adapter.ApiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notification_Adapter.ApiHolder holder, int position) {

        final Notification_Model notification_model = notification_modelList.get(position);


        holder.title.setText(notification_model.getTitle());
        holder.date.setText(notification_model.getDate());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(notification_model);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return notification_modelList.size();
    }

    public class ApiHolder extends RecyclerView.ViewHolder {


        TextView status, title, date;
        CardView main_layout;

        public ApiHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            main_layout = itemView.findViewById(R.id.main_layout);

        }
    }



    private Notification_Adapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Notification_Model notification_model);
    }

    public void setOnItemClickListener(Notification_Adapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


}
