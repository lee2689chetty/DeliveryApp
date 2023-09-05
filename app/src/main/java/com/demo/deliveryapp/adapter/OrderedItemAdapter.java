package com.demo.deliveryapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.deliveryapp.R;
import com.demo.deliveryapp.data.Product;

import java.util.List;

public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.ViewHolder>{
    private List<Product> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public OrderedItemAdapter(Context context, List<Product> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_ordereditem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Product product = mData.get(position);
        holder.name.setText(product.getTitle());
        holder.count. setText("1");
        holder.price.setText("£45.96");
        holder.option.setText("Size: Large Sauce: Large+++ Toppings: Corm(x1)");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewGroup line1, line2;
        TextView name, count, price, option;
        ViewHolder(View itemView) {
            super(itemView);
            line1 = itemView.findViewById(R.id.line1);
            name = itemView.findViewById(R.id.txt_name);
            count = itemView.findViewById(R.id.tv_count);
            price = itemView.findViewById(R.id.tv_price);
            option = itemView.findViewById(R.id.txt_option);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    public Product getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
