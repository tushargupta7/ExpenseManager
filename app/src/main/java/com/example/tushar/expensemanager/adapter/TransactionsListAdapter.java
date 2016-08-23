package com.example.tushar.expensemanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tushar.expensemanager.R;
import com.example.tushar.expensemanager.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tushar on 18/8/16.
 */

public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.ViewHolder> {
    private static List<Transaction> productEntries;
    private static Context context;
    private AdapterInterface clickListener;

    public TransactionsListAdapter(List<Transaction> Transaction, Context context,AdapterInterface clickListener) {
        this.productEntries = Transaction;
        this.context = context;
        this.clickListener=clickListener;
    }

    @Override
    public TransactionsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(TransactionsListAdapter.ViewHolder holder, final int position) {
        Transaction Transaction = productEntries.get(position);
        holder.tTitle.setText(Transaction.getTitle());
        holder.tAmount.setText(Transaction.getAmount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.itemCLicked(productEntries.get(position));
            }
        });
     }

    @Override
    public int getItemCount() {
        return productEntries.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //public ImageView productImage;
        public TextView tTitle;
        public TextView tAmount;

        @Override
        public void onClick(View v) {

        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            tTitle = (TextView) view.findViewById(R.id.title_disp);
            tAmount = (TextView) view.findViewById(R.id.amount_disp);

        }
    }
    public interface AdapterInterface{
        public void itemCLicked(Transaction item);
    }
}
