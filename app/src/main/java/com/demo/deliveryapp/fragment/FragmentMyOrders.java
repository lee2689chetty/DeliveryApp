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
import com.demo.deliveryapp.adapter.MyOrderInAdapter;
import com.demo.deliveryapp.adapter.MyOrderPastAdapter;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;

public class FragmentMyOrders extends Fragment {
    Context m_context;
    RecyclerView rv_orderin, rv_orderpast;
    ArrayList<String> orderin, orderpast;
    MyOrderInAdapter adapterin;
    MyOrderPastAdapter adapterpast;

    public static FragmentMyOrders newInstance() {
        return new FragmentMyOrders();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_myorders, container, false);

        rv_orderin = fragmentLayout.findViewById(R.id.rv_orderin);
        rv_orderpast = fragmentLayout.findViewById(R.id.rv_orderpast);

        orderin = new ArrayList<>();
        orderpast = new ArrayList<>();

        setData();

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    void setData() {
        orderin.clear();
        orderpast.clear();
        orderin.add("1");
        orderpast.add("2");
        orderpast.add("3");
        rv_orderin.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterin = new MyOrderInAdapter(getContext(), orderin);
        rv_orderin.setAdapter(adapterin);

        rv_orderpast.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterpast = new MyOrderPastAdapter(getContext(), orderpast);
        rv_orderpast.setAdapter(adapterpast);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rv_orderin)
                .adapter(adapterin)
                .load(R.layout.veil_listitem_order)
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
        final SkeletonScreen skeletonScreen_past = Skeleton.bind(rv_orderpast)
                .adapter(adapterpast)
                .load(R.layout.veil_listitem_order)
                .duration(1000)
                .color(R.color.white)
                .show();
        veilHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen_past.hide();
            }
        },3000);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity)getActivity();
        activity.showMyOrders();
        activity.hideSearchToggle();
    }
}
