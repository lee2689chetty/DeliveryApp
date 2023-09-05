package com.demo.deliveryapp.custom;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.deliveryapp.R;
import com.demo.deliveryapp.data.Product;

import java.text.DecimalFormat;
import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {
    private List<Product> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public CartItemsAdapter(Context context, List<Product> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_cart, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Product product = mData.get(position);
        holder.title.setText(product.getTitle());
        holder.description.setText(product.getDescription());
//        holder.flavour.setText(product.getFlavour());
        holder.flavour.setText(product.getOptionString()); //pizza fasa
        holder.tv_count.setText(product.getQuantity()+"");
        holder.tv_price.setText("$" + String.valueOf(new DecimalFormat("##.##").format( product.getPrice())));


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description, flavour, tv_count, tv_price;
        Button btn_count_minus, btn_count_plus;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView60);
            description = itemView.findViewById(R.id.textView61);
            flavour = itemView.findViewById(R.id.textView62);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv_price = itemView.findViewById(R.id.tv_price);
            btn_count_minus = itemView.findViewById(R.id.btn_count_minus);
            btn_count_plus = itemView.findViewById(R.id.btn_count_plus);

            btn_count_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(getLayoutPosition(),0);
                }
            });

            btn_count_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(getLayoutPosition(),1);
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
//    public String getItem(int id) {
//        return mData.get(id);
//    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(int position, int event);
    }
}
