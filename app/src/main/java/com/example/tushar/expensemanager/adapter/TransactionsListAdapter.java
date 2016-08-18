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

    public TransactionsListAdapter(List<Transaction> Transaction, Context context) {
        this.productEntries = Transaction;
        this.context = context;
    }

    @Override
    public TransactionsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(TransactionsListAdapter.ViewHolder holder, int position) {
        Transaction Transaction = productEntries.get(position);
        holder.tTitle.setText(Transaction.getTitle());
        holder.tAmount.setText(Transaction.getAmount());
       /* holder.productBrand.setText(Transaction.getBrand());
        // holder.productImage.setImageResource(R.drawable.smart);
        Picasso.with(context).load(Transaction.getPicUrl()).into(holder.productImage);
   */ }

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
           /* Intent intent = new Intent(context, ProductDetailActivity.class);
            //intent.putExtra("Transaction", (Serializable)productEntries.get(v.getVerticalScrollbarPosition()));
            intent.putExtra("product_id", productEntries.get(getPosition()).getProdId());
            intent.putExtra("product_name", productEntries.get(getPosition()).getName());
            intent.putExtra("image_url", productEntries.get(getPosition()).getPicUrl());
            context.startActivity(intent);*/
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            tTitle = (TextView) view.findViewById(R.id.title_disp);
            tAmount = (TextView) view.findViewById(R.id.amount_disp);/*
            productName = (TextView) view.findViewById(R.id.product_name);
            productBrand = (TextView) view.findViewById(R.id.product_brand);
*/

        }
    }
}
