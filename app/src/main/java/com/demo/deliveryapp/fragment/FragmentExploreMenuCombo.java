package com.demo.deliveryapp.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.adapter.PizzaItemAdapter;
import com.demo.deliveryapp.model.Pizza;

import java.util.ArrayList;

public class FragmentExploreMenuCombo extends Fragment implements PizzaItemAdapter.ItemClickListener {
    PizzaItemAdapter adapter;

    ArrayList<Pizza> animalNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_exploremenu_pizza, container, false);

        // set up the RecyclerView
        RecyclerView recyclerView = fragmentLayout.findViewById(R.id.rv_pizza);

        setData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PizzaItemAdapter(getContext(), animalNames, null, recyclerView);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    void setData() {
        animalNames.clear();
        animalNames.add(new Pizza(1,
                "Farm House 1",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                5.99f, true, true, 1, 5.99f)
        );
        animalNames.add(new Pizza(2,
                "Farm House 2",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                6.99f, false, false, 1, 6.99f)
        );
        animalNames.add(new Pizza(3,
                "Farm House 3",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                7.99f, true, true, 1, 7.99f)
        );
        animalNames.add(new Pizza(4,
                "Farm House 4",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                8.99f, false, false, 1, 8.99f)
        );
        animalNames.add(new Pizza(5,
                "Farm House 5",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                9.99f, true, false, 1, 9.99f)
        );
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
