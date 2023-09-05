package com.demo.deliveryapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.deliveryapp.R;
import com.demo.deliveryapp.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private ArrayList<Card> cardList = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public int selected = -1;

    // data is passed into the constructor
    public CardAdapter(Context context, ArrayList<Card> data) {
        this.mInflater = LayoutInflater.from(context);
        this.cardList = data;
        Log.e("size", cardList.size() + "");
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.owner.setText(cardList.get(position).getOwner());
        holder.cardNumber.setText(cardList.get(position).getNumber());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = holder.getAdapterPosition();
                mClickListener.onItemClick(view, holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        if(position==selected)
            holder.container.setBackgroundResource(R.drawable.btn_round_white_sel);
        else
            holder.container.setBackgroundResource(R.drawable.btn_round_white);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView owner, cardNumber;
        TextView remove;
        ConstraintLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            remove = itemView.findViewById(R.id.btn_remove);
            owner = itemView.findViewById(R.id.txt_card_owner);
            cardNumber = itemView.findViewById(R.id.txt_card_num);
            container = itemView.findViewById(R.id.container);
        }

        @Override
        public void onClick(View view) {

        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return cardList.get(id).getNumber();
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
