package com.demo.deliveryapp.adapter;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.data.Product;
import com.demo.deliveryapp.fragment.FragmentViewItem;
import com.demo.deliveryapp.listener.AddToCartListener;
import com.demo.deliveryapp.model.Pizza;

import java.util.List;

import static android.content.ContentValues.TAG;

public class PizzaItemAdapter extends RecyclerView.Adapter<PizzaItemAdapter.ViewHolder> {

    private List<Pizza> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private boolean isKeyboardShowing = false;
    private View container;
    AddToCartListener mListener;

    // data is passed into the constructor
    public PizzaItemAdapter(Context context, List<Pizza> data, AddToCartListener mListener, View v) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mListener = mListener;
        this.container = v;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_pizza, parent, false);

        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        container.getWindowVisibleDisplayFrame(r);
                        int screenHeight = container.getRootView().getHeight();

                        // r.bottom is the position above soft keypad or device button.
                        // if keypad is shown, the r.bottom is smaller than that before.
                        int keypadHeight = screenHeight - r.bottom;

                        Log.d(TAG, "keypadHeight = " + keypadHeight);

                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            // keyboard is opened
                            if (!isKeyboardShowing) {
                                isKeyboardShowing = true;
                            }
                        } else {
                            // keyboard is closed
                            if (isKeyboardShowing) {
                                isKeyboardShowing = false;
                            }
                        }
                    }
                });

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.price.setText(new StringBuilder().append("£ ").append(mData.get(position).getPrice()).toString());
        holder.name.setText(mData.get(position).getName());
        holder.desc.setText(mData.get(position).getDesc());
        if(position>1){
            holder.product_img.setVisibility(View.GONE);
        }
        /* comment by Fasa */
//        final Button button = holder.btnViewItem;
//        if (position == 0) {
//            button.setText("View Item");
//            button.setBackground(ContextCompat.getDrawable(button.getContext(), R.drawable.btn_round_grey));
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MainActivity activity = (MainActivity) v.getContext();
//                    if (isKeyboardShowing) {
//                        Tools.hideSoftKeyboard(activity, button);
//                    } else {
//                        activity.loadFragmentOnTopWithAnim(FragmentViewItem.newInstance());
//                    }
//                }
//            });
//            /*holder.root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MainActivity activity = (MainActivity) view.getContext();
//                    activity.loadFragmentOnTop(FragmentViewItem.newInstance());
//                }
//            });*/
//        } else {
//            button.setText("Add To Cart");
//            button.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    MainActivity activity = (MainActivity) v.getContext();
//                    if (isKeyboardShowing) {
//                        Tools.hideSoftKeyboard(activity, button);
//                    } else {
//                        Product product = new Product(true);
//                        product.finalzie();
//                        StorageHelper.addToCart(product);
//                        mClickListener.onItemClick(v, 0);
//                    }
//                }
//            });
//        }

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnViewItem;
        View root;
        TextView name, desc, price;
        ConstraintLayout product_img;
        ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            btnViewItem = itemView.findViewById(R.id.btn_viewitem);
            name = itemView.findViewById(R.id.product_name);
            desc = itemView.findViewById(R.id.product_desc);
            price = itemView.findViewById(R.id.product_price);
            product_img = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id).getName();
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
