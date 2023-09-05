package com.demo.deliveryapp.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.data.Adress;
import java.util.List;

public class AdressAdapter extends RecyclerView.Adapter<AdressAdapter.ViewHolder> {
    private List<Adress> mData;
    private LayoutInflater mInflater;
    int selected = 0;
    boolean canSelect;
    boolean canRemove;

    public AdressAdapter(Context context, List<Adress> data, boolean canSelect, boolean canRemove) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.canSelect = canSelect;
        this.canRemove = canRemove;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_adress, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Adress product = mData.get(position);
        holder.tvTitle.setText(product.getTitle());
        holder.tvDescription.setText(product.getAdress());

        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener!=null)
                    mClickListener.onItemClick(position,0);
            }
        });

        if(!canSelect){
            //
        }
        else{
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected = position;
                    notifyDataSetChanged();
                }
            });
            if(position==selected)
                holder.parent.setBackgroundResource(R.drawable.btn_round_white_sel);
            else
                holder.parent.setBackgroundResource(R.drawable.btn_round_white);
        }

        if(!canRemove){
            holder.tvRemove.setVisibility(View.GONE);
        }
        else{
            holder.tvRemove.setVisibility(View.VISIBLE);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewGroup parent;
        TextView tvTitle, tvDescription,tvRemove;
        View root;
        ViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvRemove = itemView.findViewById(R.id.tvRemove);
            root = itemView;
        }

        @Override
        public void onClick(View view) {

        }
    }
    private AdressAdapter.ItemClickListener mClickListener;
    public void setClickListener(AdressAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(int position, int event);
    }

}
