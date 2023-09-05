package com.demo.deliveryapp.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.adapter.FavPizzaItemAdapter;

import java.util.ArrayList;

public class FragmentFavoritesPizza extends Fragment {
    FavPizzaItemAdapter adapter;

    ArrayList<String> pizzaNames = new ArrayList<>();

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_favorites_pizza, container, false);

        // set up the RecyclerView
        recyclerView = fragmentLayout.findViewById(R.id.rv_pizza);

        setData();

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    void setData() {
        pizzaNames.clear();
        pizzaNames.add("1");
        pizzaNames.add("2");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavPizzaItemAdapter(getContext(), pizzaNames);
        recyclerView.setAdapter(adapter);
    }
}
