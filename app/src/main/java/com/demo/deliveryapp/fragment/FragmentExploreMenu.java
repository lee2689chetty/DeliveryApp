package com.demo.deliveryapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.data.Product;
import com.demo.deliveryapp.listener.AddToCartListener;
import com.demo.deliveryapp.listener.CartCountListener;
import com.demo.deliveryapp.listener.MessageEvent;
import com.demo.deliveryapp.model.SharedData;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.demo.deliveryapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentExploreMenu extends Fragment implements AddToCartListener {
    Context m_context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView btn_viewcart;
    TextView txtOrderPrice;

    public static FragmentExploreMenu newInstance() {
        return new FragmentExploreMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    View cardFooter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_exploremenu, container, false);
        viewPager = (ViewPager) fragmentLayout.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) fragmentLayout.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        btn_viewcart = fragmentLayout.findViewById(R.id.btn_viewcart);
        cardFooter = fragmentLayout.findViewById(R.id.cardFooter);
        cardFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                activity.loadFragment(FragmentViewCart.newInstance(), true);
                activity.showViewCart();
            }
        });
        txtOrderPrice = (TextView) fragmentLayout.findViewById(R.id.txt_order_price);

        showPrice(getActivity());
        //
        //

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Product> cartItems = StorageHelper.getCartItems();
        if (cartItems.isEmpty())
            cardFooter.setVisibility(View.GONE);
        else {
            cardFooter.setVisibility(View.VISIBLE);
            fillDataFooter(cartItems);
        }

        MainActivity activity = (MainActivity)getActivity();
        activity.showExplore();
        activity.showSearchToggle();

    }

    private void fillDataFooter( List<Product> cartItems) {

        double price = 0;
        int quantity =0;
        for (Product product:cartItems) {
            price += product.getPrice();
            quantity += product.getQuantity();
        }
        txtOrderPrice.setText(quantity+" Items: $" + String.valueOf(new DecimalFormat("##.##").format( price)));

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        adapter.addFragment(new FragmentExploreMenuPizza(this), "Pizza");
        adapter.addFragment(new FragmentExploreMenuPizza(this), "Combo");
        adapter.addFragment(new FragmentExploreMenuPizza(this), "Sides");
        adapter.addFragment(new FragmentExploreMenuPizza(this), "Veberage");
        adapter.addFragment(new FragmentExploreMenuPizza(this), "Drinks");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        showPrice(getActivity());
    }

    @Override
    public void addToCardCallback(int count, float price) {
        Tools.showMsg(m_context,"Added to cart!");
        cartCountListener.changeCountCallback(0);
        onResume();
    }

    CartCountListener cartCountListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.cartCountListener = (CartCountListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.cartCountListener = (CartCountListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartCountListener = null;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void showPrice(Context context){
        SharedData sharedData = new SharedData(context);
        txtOrderPrice.setText(sharedData.getFormartedString());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        onResume();
    }
}
