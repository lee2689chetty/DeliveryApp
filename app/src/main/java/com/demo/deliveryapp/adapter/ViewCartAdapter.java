package com.demo.deliveryapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.deliveryapp.R;
import com.demo.deliveryapp.listener.CartCountListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class ViewCartAdapter extends RecyclerView.Adapter<ViewCartAdapter.ViewHolder> {
    private List<String> mTitle, mContent, mCode;
    private List<Integer> countArr;
    private LayoutInflater mInflater;
    private Context mContext;
    private CartCountListener mListener;


    // data is passed into the constructor
    public ViewCartAdapter(Context context, List<String> Title, List<String> Content, List<String> Code, CartCountListener mListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mTitle = Title;
        this.mContent = Content;
        this.mCode = Code;
        this.mListener = mListener;
        countArr = new ArrayList<>();
        for(int i= 0; i<mTitle.size(); i++)
            countArr.add(new Integer(1));

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listview_cart, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.btn_count_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = countArr.get(position).intValue();
                if(count == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("WARNING");
                    builder.setMessage("Are you sure you want to remove this item?");
                    builder.setPositiveButton(Html.fromHtml("<font color='#00FF00'>Yes</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            countArr.remove(position);
                            mTitle.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mTitle.size());
                        }
                    });
                    builder.setNegativeButton(Html.fromHtml("<font color='#FF0000'>No</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {

                        }
                    });
                    builder.create();
                    builder.show();
                } else {
                    count--;
                    holder.tv_count.setText(String.valueOf(count));
                    holder.tv_price.setText("$"+String.valueOf(new DecimalFormat("##.##").format(count * 5.99)));
                    countArr.set(position, new Integer(count));
                }
            }
        });
        holder.btn_count_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = countArr.get(position).intValue();
                count++;
                holder.tv_count.setText(String.valueOf(count));
                holder.tv_price.setText("$"+String.valueOf(new DecimalFormat("##.##").format(count * 5.99)));
                countArr.set(position, new Integer(count));
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        mListener.changeCountCallback(mTitle.size());
        return mTitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn_count_minus, btn_count_plus;
        TextView tv_count, tv_price;

        ViewHolder(View itemView) {
            super(itemView);
            btn_count_minus = itemView.findViewById(R.id.btn_count_minus);
            btn_count_plus = itemView.findViewById(R.id.btn_count_plus);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
