package com.demo.deliveryapp.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.adapter.OrderDiscountAdapter;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;

public class FragmentOffersDiscounts extends Fragment {
    Context m_context;

    OrderDiscountAdapter adapter;
    ArrayList<String> al_title, al_content, al_code;
    RecyclerView recyclerView;

    public static FragmentOffersDiscounts newInstance() {
        return new FragmentOffersDiscounts();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_offersdiscounts, container, false);

        al_title = new ArrayList<>();
        al_content = new ArrayList<>();
        al_code = new ArrayList<>();

        // set up the RecyclerView
        recyclerView = fragmentLayout.findViewById(R.id.rv_offersdiscounts);

        setData();

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    void setData() {
        al_title.clear();
        al_content.clear();
        al_code.clear();

        al_title.add("New User? First Pizza Free!!");
        al_title.add("Buy 2 Large Pizza & Get 1 Large for free");
        al_title.add("Combo Bononza 50% off");
        al_title.add("Buy 3 Medium & Get 2 small Pizza for free");

        al_content.add("Oh! You're new User? Order your first pizza now for Free &amp; just pay for delivery charge. Terms and Conditions apply.");
        al_content.add("Lorem ipsul dolor sit amet, consectetur adipisicing elit, sed od eiusmod temporincididuntut labore e..");
        al_content.add("Lorem ipsul dolor sit amet, consectetur adipisicing elit, sed od eiusmod temporincididuntut labore e..");
        al_content.add("Oh! You're new User? Order your first pizza now for Free &amp; just pay for delivery charge. Terms and Conditions apply.");

        al_code.add("NEW4FREE");
         al_code.add("BUY2GET1");
         al_code.add("COMBO50");
         al_code.add("BUY3GET2");


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderDiscountAdapter(getContext(), al_title, al_content, al_code);
        recyclerView.setAdapter(adapter);

        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .load(R.layout.veil_listitem_cuopon)
                .duration(1000)
                .color(R.color.white)
                .show();
        Handler veilHandler = new Handler();
        veilHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        },2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity)getActivity();
        activity.showCoupons();
        activity.hideSearchToggle();
    }
}
