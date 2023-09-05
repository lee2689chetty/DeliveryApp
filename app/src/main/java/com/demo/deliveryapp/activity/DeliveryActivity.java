package com.demo.deliveryapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.custom.AdressAdapter;
import com.demo.deliveryapp.custom.CartItemsAdapter;
import com.demo.deliveryapp.data.Adress;
import com.demo.deliveryapp.data.Product;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.Calendar;
import java.util.List;

public class DeliveryActivity extends BaseActivity  implements AdressAdapter.ItemClickListener {
    CardView card_delivery, card_collection;
    TextView tv_change;
    RadioButton rb_delivery, rb_collection;
    Button btn_confirm;
    String[] timeList = {"ASAP", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30"};
    BetterSpinner spinner_timer1;
    BetterSpinner spinner_timer2;

    RecyclerView rcAdress;

   boolean timeOne=true,timeTwo=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        rcAdress = findViewById(R.id.rcAdress);


        spinner_timer1 = findViewById(R.id.spinner_timer1);
        spinner_timer2 = findViewById(R.id.spinner_timer2);


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

        this.spinner_timer1.setAdapter(new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu_popup_item, this.timeList));
        this.spinner_timer2.setAdapter(new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu_popup_item, this.timeList));

        tv_change = findViewById(R.id.tv_change);

        btn_confirm = findViewById(R.id.btn_confirm);

        setRadiobutton(true);

        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rb_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadiobutton(true);
            }
        });

        rb_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadiobutton(false);
            }
        });

        card_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadiobutton(true);
            }
        });

        card_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRadiobutton(false);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInput()) {
                    Intent myIntent = new Intent(DeliveryActivity.this, PaymentActivity.class);
                    DeliveryActivity.this.startActivity(myIntent);
                }
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DeliveryActivity.this, AddAddressActivity.class);
                DeliveryActivity.this.startActivity(myIntent);
            }
        });


        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adressList = StorageHelper.getAdresses();
        setData();
    }

    AdressAdapter adressAdapter;
    List<Adress> adressList;
    void setData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcAdress.setLayoutManager(layoutManager);
        rcAdress.requestDisallowInterceptTouchEvent(true);
        adressAdapter = new AdressAdapter(this, adressList, true, false);
        adressAdapter.setClickListener(this);
        rcAdress.setAdapter(adressAdapter);
    }

    private boolean verifyInput() {

        boolean verifiedInput = true;

        if (rb_delivery.isChecked()) {
            if (!timeOne) {
                verifiedInput = false;
                Tools.showMsg(DeliveryActivity.this, "Must Select Delivery Time");
            }
        }
        if (rb_collection.isChecked()) {
            if (!timeTwo) {
                verifiedInput = false;
                Tools.showMsg(DeliveryActivity.this, "Must Select Collection Time");
            }
        }

        return verifiedInput;
    }



    void setRadiobutton(boolean flag) {
        rb_delivery.setChecked(flag);
        rb_collection.setChecked(!flag);
    }

    private void confirmDeletedAt(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage(getString(R.string.remove_adress));
        alertDialog.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       StorageHelper.removeAdress(adressList.get(position));
                       adressAdapter.notifyItemRemoved(position);
                       Tools.showMsg(DeliveryActivity.this,"Removed");
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
