package com.demo.deliveryapp.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.data.Product;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentActivity extends BaseActivity {
    Button btn_place;
    RadioButton rb_card, rb_cash;
    RelativeLayout ll_card, ll_cash;
    TextView tvAmount, tvTotal;
    View cardView6;
    View cardDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btn_place = findViewById(R.id.btn_place);
        ll_card = findViewById(R.id.ll_card);

        //
        cardView6 = findViewById(R.id.cardView6);
        cardDetails = findViewById(R.id.cardDetails);
        rb_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked)
                    showCardDetails();
            }
        });
        rb_cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked)
                    hideCardDetails();
            }
        });

        final EditText etExpiry = findViewById(R.id.etExpiry);
        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence p0, int start, int removed, int added) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
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
        //

        btn_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), R.anim.slide_right_in, R.anim.slide_out_left);
                Intent myIntent = new Intent(PaymentActivity.this, OrderConfirmedActivity.class);
                startActivity(myIntent, options.toBundle());
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_out_left);
            }
        });

        rb_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_card.setChecked(true);
                rb_cash.setChecked(false);
            }
        });
        rb_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_card.setChecked(false);
                rb_cash.setChecked(true);
            }
        });

        ll_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_card.setChecked(true);
                rb_cash.setChecked(false);
            }
        });
        ll_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_card.setChecked(false);
                rb_cash.setChecked(true);
            }
        });

        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAmount = findViewById(R.id.tvAmount);
        tvTotal = findViewById(R.id.tvTotal);

        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    private void hideCardDetails() {
        cardDetails.setVisibility(View.GONE);
        cardView6.setBackgroundResource(R.drawable.btn_round_white);
    }

    private void showCardDetails() {
        cardDetails.setVisibility(View.VISIBLE);
        cardView6.setBackgroundResource(R.drawable.btn_top_round_white);
    }

    List<Product> cartItems;

    @Override
    public void onResume() {
        super.onResume();
        cartItems = StorageHelper.getCartItems();
        fillDataFooter();

    }

    double delivery = 4.0;

    private void fillDataFooter() {

        double price = 0;
        int quantity = 0;
        for (Product product : cartItems) {
            price += product.getPrice();
            quantity += product.getQuantity();
        }
        tvAmount.setText(String.valueOf(new DecimalFormat("##.##").format(price)));
        tvTotal.setText(String.valueOf(new DecimalFormat("##.##").format(price + delivery)));

    }
}
