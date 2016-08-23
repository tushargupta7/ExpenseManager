package com.example.tushar.expensemanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tushar.expensemanager.R;

import java.util.List;



public class CategoryTagsSelectionAdapter extends RecyclerView.Adapter<CategoryTagsSelectionAdapter.ViewHolder> {


    private List<String> mItemsList;
    private final CategoryTagsSelectionAdapter.AdapterInterface mListener;

    public CategoryTagsSelectionAdapter(List<String> itemsList,CategoryTagsSelectionAdapter.AdapterInterface listener) {
        mListener = listener;
        this.mItemsList=itemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.categoryName.setText(mItemsList.get(position));
        holder.selectionCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                   // selectedList.add(holder.categoryName.getText().toString());
                    mListener.itemCLicked(holder.categoryName.getText().toString(),"add");
                }
                else mListener.itemCLicked(holder.categoryName.getText().toString(),"delete");
            }
        });

    /*    holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.itemCLicked();
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView categoryName;
        public final CheckBox selectionCheck;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            categoryName = (TextView) view.findViewById(R.id.category_name);
            selectionCheck = (CheckBox) view.findViewById(R.id.category_checkbox);
        }
    }

    public interface AdapterInterface{
        public void itemCLicked(String string,String action);
    }
}
