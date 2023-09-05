package com.demo.deliveryapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.adapter.OrderedItemAdapter;
import com.demo.deliveryapp.data.Product;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragmentViewOrderedItems extends Fragment {
    Button img_close;
    RecyclerView rv_items;
    TextView tx_total, tx_subtotal;
    OrderedItemAdapter adapter;

    List<Product> orderlist = new ArrayList<>();
    CardView detailView;
    TextView note;


    public static FragmentViewOrderedItems newInstance() {
        return new FragmentViewOrderedItems();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.activity_view_ordered_item, container, false);

        rv_items = fragmentLayout.findViewById(R.id.rv_items);
        tx_total = fragmentLayout.findViewById(R.id.tvAmountTotal);
        tx_subtotal = fragmentLayout.findViewById(R.id.tvAmount);
        detailView = fragmentLayout.findViewById(R.id.detail);
        note = fragmentLayout.findViewById(R.id.note);
//        ImageView img_close;
//        img_close = fragmentLayout.findViewById(R.id.img_close);
//        img_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        setData();

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    void setData() {
        orderlist = StorageHelper.getCartItems();
        rv_items.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderedItemAdapter(getActivity(), orderlist);
        rv_items.setAdapter(adapter);
        float price = 0;
        for(int i = 0; i < orderlist.size(); i ++ ){
            price += orderlist.get(i).getPrice()*orderlist.get(i).getQuantity();
        }
        tx_subtotal.setText(String.valueOf(price));
        tx_total.setText(String.valueOf(price + 4.0f));
        final SkeletonScreen skeletonScreen = Skeleton.bind(rv_items)
                .adapter(adapter)
                .load(R.layout.veil_listitem_cart)
                .duration(1000)
                .color(R.color.white)
                .show();


        final SkeletonScreen skeletonScreenDetail = Skeleton.bind(detailView)
                .load(R.layout.veil_listitem_detail)
                .color(R.color.white)
                .show();
        final SkeletonScreen skeletonScreenNote = Skeleton.bind(note)
                .load(R.layout.veil_listitem_card)
                .color(R.color.white)
                .show();
        Handler veilHandler = new Handler();
        veilHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
                skeletonScreenDetail.hide();
                skeletonScreenNote.hide();
            }
        },2000);
    }
}
