package com.demo.deliveryapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.data.Crust;
import com.demo.deliveryapp.data.Product;
import com.demo.deliveryapp.data.Size;
import com.demo.deliveryapp.data.Topic;
import com.demo.deliveryapp.model.SharedData;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentViewItem extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    Button btn_count_minus, btn_count_plus, btn_viewcart, btn_add_cart;
    TextView tv_count, tv_price, tv_order_price;
    TextView tv_kind_small1, tv_kind_small2, tv_kind_medium1,
            tv_kind_medium2, tv_kind_large1, tv_kind_large2, tv_kind_title_4, tv_kind_price_4, tv_kind_title_5, tv_kind_price_5;
    ConstraintLayout cl_kind_small, cl_kind_medium, cl_kind_large, cl_card_4, cl_card_5;
    ImageView img_close;

    int count = 1;
    float price = 5.99f;

    Product product;
    LinearLayout cardFooter;

    TextView tv_quantity_1, tv_quantity_2, tv_quantity_3, tv_quantity_4, tv_quantity_5;

    public static FragmentViewItem newInstance() {
        return new FragmentViewItem();
    }
    Handler handler;
    Runnable runnable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.activity_view_item, container, false);
        viewPager = (ViewPager) fragmentLayout.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(1);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) fragmentLayout.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        btn_count_minus = (Button) fragmentLayout.findViewById(R.id.btn_count_minus);
        btn_count_plus = (Button) fragmentLayout.findViewById(R.id.btn_count_plus);
        btn_viewcart = (Button) fragmentLayout.findViewById(R.id.btn_viewcart);
        btn_add_cart = (Button) fragmentLayout.findViewById(R.id.btn_add_cart);

        tv_count = (TextView) fragmentLayout.findViewById(R.id.tv_count);
        tv_price = (TextView) fragmentLayout.findViewById(R.id.tv_price);
        tv_order_price = (TextView) fragmentLayout.findViewById(R.id.tv_order_price);
        tv_kind_small1 = (TextView) fragmentLayout.findViewById(R.id.tv_kind_small1);
        tv_kind_small2 = (TextView) fragmentLayout.findViewById(R.id.tv_kind_small2);
        tv_kind_medium1 = (TextView) fragmentLayout.findViewById(R.id.tv_kind_medium1);
        tv_kind_medium2 = (TextView) fragmentLayout.findViewById(R.id.tv_kind_medium2);
        tv_kind_large1 = (TextView) fragmentLayout.findViewById(R.id.tv_kind_large1);
        tv_kind_large2 = (TextView) fragmentLayout.findViewById(R.id.tv_kind_large2);
        cl_kind_small = (ConstraintLayout) fragmentLayout.findViewById(R.id.cl_kind_small);
        cl_kind_medium = (ConstraintLayout) fragmentLayout.findViewById(R.id.cl_kind_medium);
        cl_kind_large = (ConstraintLayout) fragmentLayout.findViewById(R.id.cl_kind_large);
        //
        cl_card_4 = fragmentLayout.findViewById(R.id.cl_card_4);
        tv_kind_title_4 = fragmentLayout.findViewById(R.id.tv_kind_title_4);
        tv_kind_price_4 = fragmentLayout.findViewById(R.id.tv_kind_price_4);

        cl_card_5 = fragmentLayout.findViewById(R.id.cl_card_5);
        tv_kind_title_5 = fragmentLayout.findViewById(R.id.tv_kind_title_5);
        tv_kind_price_5 = fragmentLayout.findViewById(R.id.tv_kind_price_5);


        tv_quantity_1 = fragmentLayout.findViewById(R.id.tv_quantity_1);
        tv_quantity_2 = fragmentLayout.findViewById(R.id.tv_quantity_2);
        tv_quantity_3 = fragmentLayout.findViewById(R.id.tv_quantity_3);
        tv_quantity_4 = fragmentLayout.findViewById(R.id.tv_quantity_4);
        tv_quantity_5 = fragmentLayout.findViewById(R.id.tv_quantity_5);


        //

        img_close = fragmentLayout.findViewById(R.id.img_close);

        btn_count_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 1) {

                } else {
                    count--;
                    tv_count.setText(String.valueOf(count));
                    setPrice();
                }
            }
        });

        btn_count_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                tv_count.setText(String.valueOf(count));
                setPrice();
            }
        });

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData sharedData = new SharedData(getContext());
                int count1 = count + sharedData.getCountShardDB();
                float price1 = count * price + sharedData.getPriceShardDB();
                tv_order_price.setText(sharedData.getFormartedString(count1, price1));
                sharedData.setSharedDB(count1, price1);
            }
        });


        cl_kind_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentItem = viewPager.getCurrentItem();
                if (currentItem == 2) {
                    product.addTopic(Topic.TOMATO);
                    updateUiForCard5();
                } else {
                    cl_card_4.setBackgroundResource(R.drawable.btn_round_white);
                    tv_kind_title_4.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_price_4.setTextColor(getResources().getColor(R.color.green));

                    cl_kind_small.setBackgroundResource(R.drawable.btn_round_red);
                    cl_kind_medium.setBackgroundResource(R.drawable.btn_round_white);
                    cl_kind_large.setBackgroundResource(R.drawable.btn_round_white);
                    tv_kind_small1.setTextColor(getResources().getColor(R.color.white));
                    tv_kind_small2.setTextColor(getResources().getColor(R.color.white));
                    tv_kind_medium1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_medium2.setTextColor(getResources().getColor(R.color.green));
                    tv_kind_large1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_large2.setTextColor(getResources().getColor(R.color.green));
                }
                if (currentItem == 0) {
                    product.setSize(new Size(Size.SMALL));
                } else if (currentItem == 1) {
                    product.setCrust(new Crust(Crust.CHEESY));
                }

                setPrice();
            }
        });

        cl_kind_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentItem = viewPager.getCurrentItem();
                if (currentItem == 2) {
                    product.addTopic(Topic.CORN);
                    updateUiForCard5();
                } else {
                    cl_card_4.setBackgroundResource(R.drawable.btn_round_white);
                    tv_kind_title_4.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_price_4.setTextColor(getResources().getColor(R.color.green));


                    cl_kind_small.setBackgroundResource(R.drawable.btn_round_white);
                    cl_kind_medium.setBackgroundResource(R.drawable.btn_round_red);
                    cl_kind_large.setBackgroundResource(R.drawable.btn_round_white);
                    tv_kind_small1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_small2.setTextColor(getResources().getColor(R.color.green));
                    tv_kind_medium1.setTextColor(getResources().getColor(R.color.white));
                    tv_kind_medium2.setTextColor(getResources().getColor(R.color.white));
                    tv_kind_large1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_large2.setTextColor(getResources().getColor(R.color.green));
                }
                if (currentItem == 0) {
                    product.setSize(new Size(Size.MEDIAM));
                } else if (currentItem == 1) {
                    product.setCrust(new Crust(Crust.NORMAL));
                }

                setPrice();
            }
        });


        cl_kind_large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem == 2) {
                    product.addTopic(Topic.JALAPENOS);
                    updateUiForCard5();
                } else {
                    cl_kind_small.setBackgroundResource(R.drawable.btn_round_white);
                    cl_kind_medium.setBackgroundResource(R.drawable.btn_round_white);
                    cl_kind_large.setBackgroundResource(R.drawable.btn_round_red);

                    cl_card_4.setBackgroundResource(R.drawable.btn_round_white);
                    tv_kind_title_4.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_price_4.setTextColor(getResources().getColor(R.color.green));


                    tv_kind_small1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_small2.setTextColor(getResources().getColor(R.color.green));
                    tv_kind_medium1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_medium2.setTextColor(getResources().getColor(R.color.green));
                    tv_kind_large1.setTextColor(getResources().getColor(R.color.white));
                    tv_kind_large2.setTextColor(getResources().getColor(R.color.white));
                }
                if (currentItem == 0) {
                    product.setSize(new Size(Size.LARGE));
                } else if (currentItem == 1) {
                    product.setCrust(new Crust(Crust.SAUSSAGE));
                }

                setPrice();
//                showChangePrice();
            }
        });

        cl_card_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem == 2) {
                    product.addTopic(Topic.PINEAPPLE);
                    updateUiForCard5();
                } else {
                    cl_kind_small.setBackgroundResource(R.drawable.btn_round_white);
                    cl_kind_medium.setBackgroundResource(R.drawable.btn_round_white);
                    cl_card_5.setBackgroundResource(R.drawable.btn_round_white);
                    cl_kind_large.setBackgroundResource(R.drawable.btn_round_white);

                    cl_card_4.setBackgroundResource(R.drawable.btn_round_red);

                    tv_kind_small1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_small2.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_medium1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_medium2.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_large1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_large2.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_title_5.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_price_5.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_title_4.setTextColor(getResources().getColor(R.color.white));
                    tv_kind_price_4.setTextColor(getResources().getColor(R.color.white));

                }
                if (currentItem == 0) {

                } else if (currentItem == 1) {
                    product.setCrust(new Crust(Crust.ITALIAN));
                }

                setPrice();

            }
        });

        cl_card_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem == 2) {
                    product.addTopic(Topic.PAPPERONI);
                    updateUiForCard5();
                } else {
                    cl_kind_small.setBackgroundResource(R.drawable.btn_round_white);
                    cl_kind_medium.setBackgroundResource(R.drawable.btn_round_white);

                    cl_card_5.setBackgroundResource(R.drawable.btn_round_red);

                    cl_kind_large.setBackgroundResource(R.drawable.btn_round_white);

                    cl_card_4.setBackgroundResource(R.drawable.btn_round_white);

                    tv_kind_small1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_small2.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_medium1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_medium2.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_large1.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_large2.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_title_4.setTextColor(getResources().getColor(R.color.black));
                    tv_kind_price_4.setTextColor(getResources().getColor(R.color.green));

                    tv_kind_title_5.setTextColor(getResources().getColor(R.color.white));
                    tv_kind_price_5.setTextColor(getResources().getColor(R.color.white));
                }


                if (currentItem == 0) {

                } else if (currentItem == 1) {
                    product.setCrust(new Crust(Crust.STUFFED));
                }

                setPrice();

            }
        });

        cardFooter = fragmentLayout.findViewById(R.id.cardFooter);
        cardFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                activity.loadFragment(FragmentViewCart.newInstance(), "FragmentViewCart");
                activity.showViewCart();
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        //
        product = new Product(true);


        setPrice();
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                MainActivity activity = (MainActivity)getActivity();
                if(activity != null)
                    activity.hideSearchToggle();
            }
        };

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    void setPrice() {
        tv_price.setText("$" + String.valueOf(new DecimalFormat("##.##").format(count * product.getPrice())));

    }

    private void addToCart() {

        if(!product.isTopicSet())
        {
            Tools.showMsg(getActivity(),"Must select a topic");
            return;
        }
        product.setQuantity(count);
        StorageHelper.addToCart(product);
        Tools.showMsg(getActivity(),"Added to cart!");
        onResume();
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

        handler.postDelayed(runnable, 100);
    }

    private void fillDataFooter(List<Product> cartItems) {

        double price = 0;
        int quantity = 0;
        for (Product product : cartItems) {
            price += product.getPrice();
            quantity += product.getQuantity();
        }
        tv_order_price.setText(quantity + " Items: $" + String.valueOf(new DecimalFormat("##.##").format(price)));

    }


    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentViewItemSize(), "Select Size \n Medium");
        adapter.addFragment(new FragmentViewItemSize(), "Select Crust \n New Hand tossed");
        adapter.addFragment(new FragmentViewItemSize(), "Select Topics \n Tap to choose");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                pageChangeListener.onPageSelected(viewPager.getCurrentItem());
            }
        });
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            updateUi(position);
            updateNames(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void updateNames(int position) {

        if (position == 0) {
            tv_kind_small1.setText(Size.SMALL.toString() + "");
            tv_kind_medium1.setText(Size.MEDIAM.toString() + "");
            tv_kind_large1.setText(Size.LARGE.toString() + "");

            tv_kind_small2.setText(Size.SMALL.getPrice() + "");
            tv_kind_medium2.setText(Size.MEDIAM.getPrice() + "");
            tv_kind_large2.setText(Size.LARGE.getPrice() + "");
        } else if (position == 1) {
            tv_kind_small1.setText(Crust.CHEESY.toString() + "");
            tv_kind_medium1.setText(Crust.NORMAL.toString() + "");
            tv_kind_large1.setText(Crust.SAUSSAGE.toString() + "");
            tv_kind_title_4.setText(Crust.ITALIAN.toString() + "");
            tv_kind_title_5.setText(Crust.STUFFED.toString() + "");

            tv_kind_small2.setText(Crust.CHEESY.getPrice() + "");
            tv_kind_medium2.setText(Crust.NORMAL.getPrice() + "");
            tv_kind_large2.setText(Crust.SAUSSAGE.getPrice() + "");
            tv_kind_price_4.setText(Crust.ITALIAN.getPrice() + "");
            tv_kind_price_5.setText(Crust.STUFFED.getPrice() + "");
        } else {
            tv_kind_small1.setText(Topic.TOMATO.toString() + "");
            tv_kind_medium1.setText(Topic.CORN.toString() + "");
            tv_kind_large1.setText(Topic.JALAPENOS.toString() + "");

            tv_kind_title_4.setText(Topic.PINEAPPLE.toString() + "");
            tv_kind_title_5.setText(Topic.PAPPERONI.toString() + "");

            tv_kind_small2.setText(Topic.TOMATO.getPrice() + "");
            tv_kind_medium2.setText(Topic.CORN.getPrice() + "");
            tv_kind_large2.setText(Topic.JALAPENOS.getPrice() + "");


            tv_kind_price_4.setText(Topic.PINEAPPLE.getPrice() + "");
            tv_kind_price_5.setText(Topic.PAPPERONI.getPrice() + "");
        }

    }

    private void updateUi(int position) {

        if (position == 0) {
            if (product.getSize().getKeyPair() == Size.SMALL) {
                cl_kind_small.performClick();
            } else if (product.getSize().getKeyPair() == Size.MEDIAM) {
                cl_kind_medium.performClick();
            } else {
                cl_kind_large.performClick();
            }

            cl_card_4.setVisibility(View.GONE);
            cl_card_5.setVisibility(View.GONE);

            reSetQuantity();

        } else if (position == 1) {
            if (product.getCrust().getKeyPair() == Crust.CHEESY) {
                cl_kind_small.performClick();
            } else if (product.getCrust().getKeyPair() == Crust.NORMAL) {
                cl_kind_medium.performClick();
            } else if (product.getCrust().getKeyPair() == Crust.SAUSSAGE) {
                cl_kind_large.performClick();
            } else if (product.getCrust().getKeyPair() == Crust.ITALIAN)
                cl_card_4.performClick();
            else if (product.getCrust().getKeyPair() == Crust.STUFFED)
                cl_card_5.performClick();

            cl_card_4.setVisibility(View.VISIBLE);
            cl_card_5.setVisibility(View.VISIBLE);
            reSetQuantity();
        } else {
            updateUiForCard5();
            cl_card_4.setVisibility(View.VISIBLE);
            cl_card_5.setVisibility(View.VISIBLE);
        }
    }

    private void reSetQuantity() {
        tv_quantity_1.setText("");
        tv_quantity_2.setText("");
        tv_quantity_3.setText("");
        tv_quantity_4.setText("");
        tv_quantity_5.setText("");
    }

    private void updateUiForCard5() {

        if (product.hasTopic(Topic.TOMATO)) {
            cl_kind_small.setBackgroundResource(R.drawable.btn_round_red);
            tv_kind_small1.setTextColor(getResources().getColor(R.color.white));
            tv_kind_small2.setTextColor(getResources().getColor(R.color.white));

            int count = product.topicCount(Topic.TOMATO);
            tv_quantity_1.setText(String.format("(X%d)", count));

        } else {
            cl_kind_small.setBackgroundResource(R.drawable.btn_round_white);
            tv_kind_small1.setTextColor(getResources().getColor(R.color.black));
            tv_kind_small2.setTextColor(getResources().getColor(R.color.black));
            tv_quantity_1.setText("");
        }

        if (product.hasTopic(Topic.CORN)) {
            cl_kind_medium.setBackgroundResource(R.drawable.btn_round_red);
            tv_kind_medium1.setTextColor(getResources().getColor(R.color.white));
            tv_kind_medium2.setTextColor(getResources().getColor(R.color.white));

            int count = product.topicCount(Topic.CORN);
            tv_quantity_2.setText(String.format("(X%d)", count));

        } else {
            cl_kind_medium.setBackgroundResource(R.drawable.btn_round_white);
            tv_kind_medium1.setTextColor(getResources().getColor(R.color.black));
            tv_kind_medium2.setTextColor(getResources().getColor(R.color.black));

            tv_quantity_2.setText("");
        }

        if (product.hasTopic(Topic.JALAPENOS)) {
            cl_kind_large.setBackgroundResource(R.drawable.btn_round_red);
            tv_kind_large1.setTextColor(getResources().getColor(R.color.white));
            tv_kind_large2.setTextColor(getResources().getColor(R.color.white));

            int count = product.topicCount(Topic.JALAPENOS);
            tv_quantity_3.setText(String.format("(X%d)", count));

        } else {
            cl_kind_large.setBackgroundResource(R.drawable.btn_round_white);
            tv_kind_large1.setTextColor(getResources().getColor(R.color.black));
            tv_kind_large2.setTextColor(getResources().getColor(R.color.black));

            tv_quantity_3.setText("");
        }

        if (product.hasTopic(Topic.PINEAPPLE)) {
            cl_card_4.setBackgroundResource(R.drawable.btn_round_red);
            tv_kind_title_4.setTextColor(getResources().getColor(R.color.white));
            tv_kind_price_4.setTextColor(getResources().getColor(R.color.white));

            int count = product.topicCount(Topic.PINEAPPLE);
            tv_quantity_4.setText(String.format("(X%d)", count));

        } else {
            cl_card_4.setBackgroundResource(R.drawable.btn_round_white);
            tv_kind_title_4.setTextColor(getResources().getColor(R.color.black));
            tv_kind_price_4.setTextColor(getResources().getColor(R.color.black));

            tv_quantity_4.setText("");
        }

        if (product.hasTopic(Topic.PAPPERONI)) {
            cl_card_5.setBackgroundResource(R.drawable.btn_round_red);
            tv_kind_title_5.setTextColor(getResources().getColor(R.color.white));
            tv_kind_price_5.setTextColor(getResources().getColor(R.color.white));

            int count = product.topicCount(Topic.PAPPERONI);
            tv_quantity_5.setText(String.format("(X%d)", count));

        } else {
            cl_card_5.setBackgroundResource(R.drawable.btn_round_white);
            tv_kind_title_5.setTextColor(getResources().getColor(R.color.black));
            tv_kind_price_5.setTextColor(getResources().getColor(R.color.black));

            tv_quantity_5.setText("");
        }


        setPrice();
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


}
