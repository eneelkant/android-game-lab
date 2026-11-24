package com.burhanstore.bubblereward.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burhanstore.bubblereward.model.Transaction_Histroy;
import com.burhanstore.bubblereward.R;

import java.util.List;

public class Transaction_History extends RecyclerView.Adapter<Transaction_History.ApiHolder> {

    private Context context;
    private List<Transaction_Histroy> transaction_histroyList;


    public Transaction_History(Context context, List<Transaction_Histroy> transaction_histroys) {
        this.context = context;
        transaction_histroyList = transaction_histroys;
    }

    @NonNull
    @Override
    public Transaction_History.ApiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tran_item, parent, false);
        return new Transaction_History.ApiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Transaction_History.ApiHolder holder, int position) {

        final Transaction_Histroy transaction_histroy = transaction_histroyList.get(position);


        if (transaction_histroy.getStatus().equals("0")) {
            holder.status.setText("Pending");
        } else if (transaction_histroy.getStatus().equals("1")) {
            holder.status.setText("Paid");
        } else if (transaction_histroy.getStatus().equals("2")) {
            holder.status.setText("Rejected");
        } else if (transaction_histroy.getStatus().equals("3")) {
            holder.status.setText("Completed");
        }


        holder.title.setText(transaction_histroy.getPayment_method());
        holder.date.setText(transaction_histroy.getCreated_date() + " Amount : " + transaction_histroy.getPackage_amount());

    }

    @Override
    public int getItemCount() {
        return transaction_histroyList.size();
    }

    public class ApiHolder extends RecyclerView.ViewHolder {


        TextView status, title, date;

        public ApiHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

        }
    }
}
