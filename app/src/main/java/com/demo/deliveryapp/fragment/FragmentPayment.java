package com.demo.deliveryapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.data.Product;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class FragmentPayment extends Fragment {
    Button btn_place;
    ViewGroup ll_card, ll_cash;
    TextView tvAmount,tvAmountTotal, tvTotal;
    View cardView6;
    View cardDetails;
    int type = 1; // card
    EditText cardNo, etExpiry, cvv;

    public static FragmentPayment newInstance() {
        return new FragmentPayment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.activity_payment, container, false);

        if(!Tools.deliverySelected) {
            LinearLayout layoutDelivery = fragmentLayout.findViewById(R.id.delivery_layout);
            LinearLayout layoutTotal = fragmentLayout.findViewById(R.id.total_layout);
            layoutDelivery.setVisibility(View.GONE);
            layoutTotal.setVisibility(View.GONE);

            TextView txtMiddle = fragmentLayout.findViewById(R.id.txt_middle);
            txtMiddle.setText("Total");
        }
        btn_place = fragmentLayout.findViewById(R.id.btn_place);
        ll_card = fragmentLayout.findViewById(R.id.card_parent);
        ll_cash = fragmentLayout.findViewById(R.id.cash_parent);

        cardView6 = fragmentLayout.findViewById(R.id.cardView6);
        cardDetails = fragmentLayout.findViewById(R.id.cardDetails);

        cardNo = fragmentLayout.findViewById(R.id.cardNumber);
        etExpiry = fragmentLayout.findViewById(R.id.etExpiry);
        cvv = fragmentLayout.findViewById(R.id.cvv);

        cardNo.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 19){
                    etExpiry.requestFocus();
                    return;
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });

        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence p0, int start, int removed, int added) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() == 5){
                    cvv.requestFocus();
                    return;
                }
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    final char c = editable.charAt(editable.length() - 1);
                    if ('/' == c) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }
                if (editable.length() > 0 && (editable.length() % 3) == 0) {
                    char c = editable.charAt(editable.length() - 1);
                    if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf("/")).length <= 2) {
                        editable.insert(editable.length() - 1, String.valueOf("/"));
                    }
                }
            }
        });

        btn_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.loadFragmentOnTopWithTagAndAnim(FragmentOrderConfirmed.newInstance(), "FragmentOrderConfirmed");
            }
        });

        showSelected(1);

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

        tvAmount = fragmentLayout.findViewById(R.id.tvAmount);
        tvAmountTotal = fragmentLayout.findViewById(R.id.tvAmountTotal);
        tvTotal = fragmentLayout.findViewById(R.id.tvTotal);

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    void showSelected(int type){
        if(type == 1){
            ll_card.setBackgroundResource(R.drawable.btn_round_white_sel);
            ll_cash.setBackgroundResource(R.drawable.btn_round_white);
            showCardDetails();
        }
        else{
            ll_card.setBackgroundResource(R.drawable.btn_round_white);
            ll_cash.setBackgroundResource(R.drawable.btn_round_white_sel);
            hideCardDetails();
        }
    }

    private void hideCardDetails() {
        cardDetails.setVisibility(View.GONE);
    }

    private void showCardDetails() {
        cardDetails.setVisibility(View.VISIBLE);
    }

    List<Product> cartItems;
    @Override
    public void onResume() {
        super.onResume();
        cartItems = StorageHelper.getCartItems();
        fillDataFooter();
//        MainActivity activity = (MainActivity)getActivity();
//        activity.showViewCart();
    }

    double delivery= 4.0;
    private void fillDataFooter() {
        double price = 0;
        int quantity =0;
        for (Product product:cartItems) {
            price += product.getPrice();
            quantity += product.getQuantity();
        }
        tvAmount.setText("$"+String.valueOf(new DecimalFormat("##.##").format( price)));
        tvAmountTotal.setText("$"+String.valueOf(new DecimalFormat("##.##").format( price+delivery)));
        tvTotal.setText("$"+String.valueOf(new DecimalFormat("##.##").format( price+delivery)));
    }
}
