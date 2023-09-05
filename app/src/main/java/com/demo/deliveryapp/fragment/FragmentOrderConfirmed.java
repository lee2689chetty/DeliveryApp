package com.demo.deliveryapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;

public class FragmentOrderConfirmed extends Fragment {
    Context m_context;
    Button btn_myorder;


    public static FragmentOrderConfirmed newInstance() {
        return new FragmentOrderConfirmed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("tag", "FragmentOrderConfirmed onCreateView");
        View fragmentLayout = inflater.inflate(R.layout.fragment_orderconfirmed, container, false);
        btn_myorder = fragmentLayout.findViewById(R.id.btn_myorder);
        btn_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                int entries = fm.getBackStackEntryCount();
                for(int i = 0; i < entries; ++i) {
                    fm.popBackStack();
                }
                final MainActivity activity = (MainActivity) getActivity();
                activity.loadFragmentWithAnim(FragmentMyOrders.newInstance(), "FragmentMyOrders");
                //activity.clearAll();
            }
        });

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

}
