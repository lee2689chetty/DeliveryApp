package com.demo.deliveryapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.adapter.ViewCartAdapter;
import com.demo.deliveryapp.custom.CartItemsAdapter;
import com.demo.deliveryapp.data.Product;
import com.demo.deliveryapp.fragment.FragmentPayment;
import com.demo.deliveryapp.listener.CartCountListener;
import com.demo.deliveryapp.model.SharedData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends BaseActivity implements CartCountListener {

    LinearLayout ll_payment;
    RecyclerView rv_viewcart;
    ArrayList<String> vc_titles, vc_subtitles, vc_contents;
    CartItemsAdapter adapter;
    EditText et_coupon, et_nodes;
    public CardView card_empty;
    TextView tvAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        vc_titles = new ArrayList<>();
        vc_subtitles = new ArrayList<>();
        vc_contents = new ArrayList<>();
        card_empty = findViewById(R.id.card_empty);

        tvAmount = findViewById(R.id.tvAmount);

        // set up the RecyclerView
        rv_viewcart = findViewById(R.id.rv_view_cart);
        footerText = findViewById(R.id.footerText);


        ll_payment = findViewById(R.id.ll_payment);

        ll_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Intent myIntent = new Intent(ViewCartActivity.this, DeliveryActivity.class);
                startActivity(myIntent);
//                Intent myIntent = new Intent(activity, PaymentActivity.class);
//                activity.startActivity(myIntent);
            }
        });

        et_coupon = findViewById(R.id.et_coupon);
        et_coupon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    et_coupon.setHint("");
                else
                    et_coupon.setHint(R.string.coupon_code_hint);
            }
        });

        et_nodes = findViewById(R.id.et_notes);
        et_nodes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    et_nodes.setHint("");
                else
                    et_nodes.setHint(R.string.notes_hint);
            }
        });

        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    List<Product> cartItems;

    @Override
    public void onResume() {
        super.onResume();
        cartItems = StorageHelper.getCartItems();
        setData();
        fillDataFooter();

    }

    void setData() {


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_viewcart.setLayoutManager(layoutManager);
        rv_viewcart.requestDisallowInterceptTouchEvent(true);
        adapter = new CartItemsAdapter(this, cartItems);
        adapter.setClickListener(new CartItemsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, int event) {
                if (event == 0) {
                    Product product = cartItems.get(position);
                    if (product.getQuantity() > 1)
                        StorageHelper.reduceQuantity(product);
                    else {
                        confirmDeletedAt(position);
                        return;
                    }
                } else
                    StorageHelper.increaseQuantity(cartItems.get(position));
                fillDataFooter();
                adapter.notifyDataSetChanged();
            }
        });
        rv_viewcart.setAdapter(adapter);
    }

    private void confirmDeletedAt(final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage(getString(R.string.remove_item));
        alertDialog.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(position);
                        Tools.showMsg(ViewCartActivity.this, "Item Removed");
                        dialog.dismiss();
                    }
                });
        alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    void removeItem(int position) {
        StorageHelper.reduceQuantity(cartItems.get(position));
        adapter.notifyDataSetChanged();
        fillDataFooter();
    }


    TextView footerText;

    private void fillDataFooter() {

        double price = 0;
        int quantity = 0;
        for (Product product : cartItems) {
            price += product.getPrice();
            quantity += product.getQuantity();
        }
        tvAmount.setText(String.valueOf(new DecimalFormat("##.##").format(price)));
        footerText.setText(quantity + " Items: $" + String.valueOf(new DecimalFormat("##.##").format(price+4)));
        changeCountCallback(cartItems.size());

    }

    @Override
    public void changeCountCallback(int count) {
        if (count != 0) {
            card_empty.setVisibility(View.GONE);
            ll_payment.setVisibility(View.VISIBLE);
        } else {
            card_empty.setVisibility(View.VISIBLE);
            ll_payment.setVisibility(View.GONE);
        }
    }
}
