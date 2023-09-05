package com.demo.deliveryapp.fragment;

import android.app.Dialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.adapter.CardAdapter;
import com.demo.deliveryapp.custom.AdressAdapter;
import com.demo.deliveryapp.data.Adress;
import com.demo.deliveryapp.data.Product;
import com.demo.deliveryapp.model.Card;
import com.demo.deliveryapp.model.LoadingDialog;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.skydoves.androidveil.VeilRecyclerFrameView;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentDelivery extends Fragment implements AdressAdapter.ItemClickListener {
    ViewGroup card_delivery, card_collection;
    TextView addAddress, totalPrice;
    ConstraintLayout btn_confirm;
    String[] timeList = {"ASAP", "    06:00      ", "    06:30      ", "    07:00      ", "    07:30      ", "    08:00      ", "    08:30      ", "    09:00      ", "    09:30      ", "    10:00      ",
              "    10:30      ", "    11:00      ", "    11:30      ", "    12:00      ", "    12:30      "};
    BetterSpinner spinner_timer1;
    BetterSpinner spinner_timer2;
    RecyclerView rcAdress;
    RecyclerView rcCard;
    boolean timeOne = true, timeTwo = true;
    //    int type = 1; // delivery
    List<Product> cartItems;
    AdressAdapter adressAdapter;
    List<Adress> adressList;
    ArrayList<Card> cardList = new ArrayList<>();
    ViewGroup collectionAddress;
    TextView whereTo;
    ViewGroup ll_card, ll_cash;
    View cardDetails;
    NestedScrollView addressList;
    ScrollView containerScroll;
    CardAdapter cardAdapter;
    public static FragmentDelivery newInstance() {
        return new FragmentDelivery();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.activity_delivery, container, false);
        card_delivery = fragmentLayout.findViewById(R.id.card_delivery);
        card_collection = fragmentLayout.findViewById(R.id.card_collection);
        whereTo = fragmentLayout.findViewById(R.id.whereTo);
        rcAdress = fragmentLayout.findViewById(R.id.rcAdress);
        collectionAddress = fragmentLayout.findViewById(R.id.collectionAddress);
        spinner_timer1 = fragmentLayout.findViewById(R.id.spinner_timer1);
        spinner_timer2 = fragmentLayout.findViewById(R.id.spinner_timer2);
        totalPrice = fragmentLayout.findViewById(R.id.total_price);
        ll_card = fragmentLayout.findViewById(R.id.card_parent);
        ll_cash = fragmentLayout.findViewById(R.id.cash_parent);
        cardDetails = fragmentLayout.findViewById(R.id.cardDetails);
        addressList = fragmentLayout.findViewById(R.id.address_list);
        containerScroll = fragmentLayout.findViewById(R.id.container_scroll);
        rcCard = fragmentLayout.findViewById(R.id.card_list);
        ll_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelected(1);
            }
        });
        ll_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelected(2);
            }
        });
        spinner_timer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                timeOne = true;
            }
        });

        spinner_timer2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                timeTwo = true;
            }
        });

        this.spinner_timer1.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.dropdown_menu_popup_item, this.timeList));
        this.spinner_timer2.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.dropdown_menu_popup_item, this.timeList));

        addAddress = fragmentLayout.findViewById(R.id.tv_change);

        btn_confirm = fragmentLayout.findViewById(R.id.btn_confirm);

//        card_delivery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSelected(1);
//            }
//        });
//
//        card_collection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSelected(2);
//            }
//        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInput()) {
                    /* Fasa */
//                    activity.loadFragmentOnTopWithTag(FragmentPayment.newInstance(), "FragmentPayment");
//                    activity.loadFragmentOnTopWithTag(FragmentOrderConfirmed.newInstance(), "FragmentPayment");
                    final LoadingDialog dlg = new LoadingDialog(getActivity());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dlg.dismiss();
                            MainActivity activity = (MainActivity) getActivity();
                            activity.loadFragment(FragmentMyOrders.newInstance(), true);
                        }
                    },1000);
                    dlg.show();
                }
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.loadFragmentOnTopWithTagAndAnim(FragmentAddAddress.newInstance(), "FragmentAddAddress");
            }
        });

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return fragmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        adressList = StorageHelper.getAdresses();
        showSelected();
        showSelected(2);
        cartItems = StorageHelper.getCartItems();
        double price = 0;
        double deliveryCharges = 4;
        for (Product product : cartItems) {
            price += product.getPrice();
        }
        cardList.add(new Card("9897","Renod Polikin"));
        cardList.add(new Card("5093","Orlob Nikita"));
        cardList.add(new Card("6323","Aloshya Gorshak"));
        cardList.add(new Card("2345","Sashia Belob"));
        cardList.add(new Card("3906","Pyodolsky Sergeivna"));
        totalPrice.setText("$" + (new DecimalFormat("##.##").format(price + deliveryCharges)));
    }

    void setData() {
        if (Tools.deliverySelected) {
            spinner_timer1.setHint("    Deliver Now    ");
            timeList[0] = "    Deliver Now    ";
            if (adressList == null)
                return;
            rcAdress.setVisibility(View.VISIBLE);
            collectionAddress.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            rcAdress.setLayoutManager(layoutManager);
            rcAdress.requestDisallowInterceptTouchEvent(true);
            adressAdapter = new AdressAdapter(getActivity(), adressList, true, false);
            adressAdapter.setClickListener(this);
            rcAdress.setAdapter(adressAdapter);
            final SkeletonScreen skeletonScreen = Skeleton.bind(rcAdress)
                    .adapter(adressAdapter)
                    .load(R.layout.veil_listitem_address)
                    .duration(1000)
                    .color(R.color.white)
                    .show();
            Handler veilHandler = new Handler();
            veilHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skeletonScreen.hide();
                }
            },3000);
        } else {
            spinner_timer1.setHint("    Collect Now    ");
            timeList[0] = "    Collect Now    ";
            rcAdress.setVisibility(View.GONE);
            collectionAddress.setVisibility(View.VISIBLE);
        }
        rcCard.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardAdapter = new CardAdapter(getContext(), cardList);
        cardAdapter.setClickListener(new CardAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showSelected(3);
            }
        });
        rcCard.setAdapter(cardAdapter);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rcCard)
                .adapter(cardAdapter)
                .load(R.layout.veil_listitem_card)
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

    private boolean verifyInput() {
        boolean verifiedInput = true;
        if (Tools.deliverySelected) {
            if (!timeOne) {
                verifiedInput = false;
                Tools.showMsg(getActivity(), "Must Select Delivery Time");
            }
            if (adressList == null) {
                verifiedInput = false;
                Tools.showMsg(getActivity(), "Please add new address");
            } else if (adressList.isEmpty()) {
                verifiedInput = false;
                Tools.showMsg(getActivity(), "Please add new address");
            }
        }
        if (!Tools.deliverySelected) {
            if (!timeTwo) {
                verifiedInput = false;
                Tools.showMsg(getActivity(), "Must Select Collection Time");
            }
        }
        return verifiedInput;
    }

    void showSelected(int type){
        if(type == 1){
            showCardDetails();
            ll_card.setBackgroundResource(R.drawable.border);
            ll_cash.setBackgroundResource(R.drawable.btn_round_white);
            Handler showHandler = new Handler();
            showHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    containerScroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            },50);
        }
        else if(type ==  2){
            hideCardDetails();
            ll_card.setBackgroundResource(R.drawable.btn_round_white);
            ll_cash.setBackgroundResource(R.drawable.border);
        }
        else{
            hideCardDetails();
            ll_card.setBackgroundResource(R.drawable.btn_round_white);
            ll_cash.setBackgroundResource(R.drawable.btn_round_white);
        }
    }

    private void hideCardDetails() {
        cardDetails.setVisibility(View.GONE);
    }

    private void showCardDetails() {
        cardDetails.setVisibility(View.VISIBLE);
    }


    void showSelected() {
        if (Tools.deliverySelected) {
            whereTo.setText("Where to Deliver?");
            addAddress.setVisibility(View.VISIBLE);
            addressList.setVisibility(View.VISIBLE);
            card_delivery.setBackgroundResource(R.drawable.btn_round_white_sel);
            card_collection.setBackgroundResource(R.drawable.btn_round_white);
        } else {
            whereTo.setText("Where to Collect?");
            addAddress.setVisibility(View.GONE);
            addressList.setVisibility(View.GONE);
            card_delivery.setBackgroundResource(R.drawable.btn_round_white);
            card_collection.setBackgroundResource(R.drawable.btn_round_white_sel);
        }
        setData();
    }

    private void confirmDeletedAt(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage(getString(R.string.remove_adress));
        alertDialog.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StorageHelper.removeAdress(adressList.get(position));
                        adressAdapter.notifyItemRemoved(position);
                        Tools.showMsg(getActivity(), "Removed");
                    }
                });
        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onItemClick(int position, int event) {
        confirmDeletedAt(position);
    }
}
