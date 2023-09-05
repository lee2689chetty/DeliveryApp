package com.demo.deliveryapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.data.Adress;
import com.demo.deliveryapp.fragment.FragmentPayment;
import com.demo.deliveryapp.model.LoadingDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

public class AddAddressActivity extends BaseActivity {
    Button btn_save;
    BetterSpinner spinner1;
    EditText et_postcode, et_address_title, et_address, et_address2, et_city, et_postcode02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        btn_save = findViewById(R.id.btn_save);
        et_postcode = findViewById(R.id.et_postcode);
        et_address_title = findViewById(R.id.et_address_title);
        et_address = findViewById(R.id.et_address);
        et_address2 = findViewById(R.id.et_address2);
        et_city = findViewById(R.id.et_city);
        et_postcode02 = findViewById(R.id.et_postcode_second);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LoadingDialog dlg = new LoadingDialog(AddAddressActivity.this);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dlg.dismiss();
                        processData();

                    }
                },2000);
                dlg.show();
            }
        });
        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /* Fasa */
//        et_postcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_postcode.setHint("");
//                else
//                    et_postcode.setHint(R.string.postcode_hint);
//            }
//        });
//        et_address_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_address_title.setHint("");
//                else
//                    et_address_title.setHint(R.string.home_hint);
//            }
//        });
//        et_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_address.setHint("");
//                else
//                    et_address.setHint(R.string.address);
//            }
//        });
//        et_address2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_address2.setHint("");
//                else
//                    et_address2.setHint(R.string.address2);
//            }
//        });
//        et_city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_city.setHint("");
//                else
//                    et_city.setHint(R.string.city);
//            }
//        });
//
//        et_postcode02.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_postcode02.setHint("");
//                else
//                    et_postcode02.setHint(R.string.postcode_hint);
//            }
//        });


//        spinner1 = findViewById(R.id.spinner1);
//        String[] COUNTRIES = new String[]{"Address 1", "Address 2", "Address 3", "Address 4"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
//                R.layout.dropdown_menu_popup_item, COUNTRIES);
//        spinner1.setAdapter(adapter);

        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    Adress adress = new Adress();

    private void processData() {

        if (validInput()) {
            StorageHelper.saveAdress(adress);
            Tools.showMsg(this, "Address Added");
            finish();
        }
    }

    private boolean validInput() {

        boolean validInput = true;
        et_address_title.setError(null);
        et_address.setError(null);
        et_city.setError(null);
        et_postcode02.setError(null);

        adress.setTitle(et_address_title.getText().toString().trim());
        adress.setAdressLineOne(et_address.getText().toString().trim());
        adress.setAdressLineTwo(et_address2.getText().toString().trim());
        adress.setCity(et_city.getText().toString().trim());
        adress.setPostalCode(et_postcode02.getText().toString().trim());

        if (adress.getTitle().isEmpty()) {
            et_address_title.setError("*Required");
            validInput = false;
        }

        if (adress.getAdressLineOne().isEmpty()) {
            et_address.setError("*Required");
            validInput = false;
        }
        if (adress.getCity().isEmpty()) {
            et_city.setError("*Required");
            validInput = false;
        }
        if (adress.getPostalCode().isEmpty()) {
            et_postcode02.setError("*Required");
            validInput = false;
        }

        return validInput;
    }
}
