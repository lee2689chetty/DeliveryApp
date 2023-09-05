package com.demo.deliveryapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.adapter.PizzaItemAdapter;
import com.demo.deliveryapp.listener.AddToCartListener;
import com.demo.deliveryapp.model.CartDialog;
import com.demo.deliveryapp.model.OrderData;
import com.demo.deliveryapp.model.Pizza;
import com.skydoves.androidveil.VeilRecyclerFrameView;

import java.util.ArrayList;

public class FragmentExploreMenuPizza extends Fragment implements PizzaItemAdapter.ItemClickListener {

    PizzaItemAdapter adapter;

    ArrayList<String> pizzaNames = new ArrayList<>();
    ArrayList<Pizza> pizzaArray = new ArrayList<Pizza>();
    ArrayList<Pizza> pizzaArrayClone = new ArrayList<Pizza>();
    VeilRecyclerFrameView recyclerView;

    int productAddCount = 0;
    private FragmentExploreMenu fragmentExploreMenu;
    String type = "";

    public FragmentExploreMenuPizza(FragmentExploreMenu fragmentExploreMenu) {
        this.fragmentExploreMenu = fragmentExploreMenu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_exploremenu_pizza, container, false);

        // set up the RecyclerView
        recyclerView = fragmentLayout.findViewById(R.id.rv_pizza);

        setData();

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        MainActivity activity = (MainActivity) getActivity();
        activity.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("KILL",charSequence.toString()+":"+charSequence.length());
                if(charSequence.length()==0){
                    cloneArray(pizzaArrayClone, pizzaArray);
                    adapter.notifyDataSetChanged();
                }
                else{
                    cloneArray(pizzaArrayClone, pizzaArray);
                    for(int k = pizzaArray.size()-1; k>=0; k--){
                        if(!pizzaArray.get(k).getName().contains(charSequence)){
                            pizzaArray.remove(k);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return fragmentLayout;
    }


    void setData() {
        pizzaArray.clear();
        pizzaArray.add(new Pizza(1,
                "Farm House 1",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                5.99f, true, true, 1, 5.99f)
        );
        pizzaArray.add(new Pizza(2,
                "Farm House 2",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                6.99f, false, false, 1, 6.99f)
        );
        pizzaArray.add(new Pizza(3,
                "Farm House 3",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                7.99f, true, true, 1, 7.99f)
        );
        pizzaArray.add(new Pizza(4,
                "Farm House 4",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                8.99f, false, false, 1, 8.99f)
        );
        pizzaArray.add(new Pizza(5,
                "Farm House 5",
                "A pizza that goes ballistic on veggies! Check out this mouth watering overload of crunchy, crisp capsicum, succulent, etc.ballistic on veggies! Check out this mouth watering overload of ",
                9.99f, true, false, 1, 9.99f)
        );
        cloneArray(pizzaArray, pizzaArrayClone);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PizzaItemAdapter(getContext(), pizzaArray, fragmentExploreMenu, recyclerView);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addVeiledItems(15);
        Handler veilHandler = new Handler();
        veilHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.unVeil();
            }
        },3000);
    }

    @Override
    public void onItemClick(View view, int position) {
        showAddtoCartDlg(pizzaArray.get(position));
    }

    private void showAddtoCartDlg(Pizza pizza) {
        CartDialog cartDialog = new CartDialog(getContext(), pizza);
        cartDialog.setClickListener(new CartDialog.onAddCartListener() {
            @Override
            public void add(OrderData data) {
                addToCartListener.addToCardCallback(1, 1);
            }
        });
        cartDialog.show();
    }


    AddToCartListener addToCartListener;

    public void onAttachToParentFragment(Fragment fragment) {
        try {
            addToCartListener = (AddToCartListener) fragment;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }

    public void cloneArray (ArrayList<Pizza> arrayList, ArrayList<Pizza> clone){
        clone.clear();
        for(int i = 0; i < arrayList.size(); i++){
            clone.add(arrayList.get(i));
        }
    }
}
