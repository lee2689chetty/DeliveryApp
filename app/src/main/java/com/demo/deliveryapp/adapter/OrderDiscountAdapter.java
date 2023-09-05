package com.demo.deliveryapp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;

import java.util.List;

public class OrderDiscountAdapter extends RecyclerView.Adapter<OrderDiscountAdapter.ViewHolder> {
    private List<String> mTitle, mContent, mCode;
    private LayoutInflater mInflater;
    private Context mContext;

    // data is passed into the constructor
    public OrderDiscountAdapter(Context context, List<String> Title, List<String> Content, List<String> Code) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mTitle = Title;
        this.mContent = Content;
        this.mCode = Code;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_orderdiscount, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.ll_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.copyToClipBoard(mContext,"Discount Code", mCode.get(holder.getAdapterPosition()));

            }
        });
        holder.cardDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder builder = new StringBuilder();
                builder.append(mTitle.get(holder.getAdapterPosition()));
                builder.append("\n");
                builder.append(mContent.get(holder.getAdapterPosition()));
                builder.append("\n");
                builder.append(mCode.get(holder.getAdapterPosition()));
                Tools.copyToClipBoard(mContext,"Discount Code",builder.toString());
            }
        });
        holder.txt_title.setText(mTitle.get(position));
        holder.txt_desc.setText(mContent.get(position));
        holder.txt_code.setText(mCode.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout ll_copy;

       View cardDiscount;
       TextView txt_title, txt_desc, txt_code;

        ViewHolder(View itemView) {
            super(itemView);
            ll_copy = itemView.findViewById(R.id.ll_copy);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_code = itemView.findViewById(R.id.txt_code);
            cardDiscount = itemView.findViewById(R.id.cardDiscount);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
