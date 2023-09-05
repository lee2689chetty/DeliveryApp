package com.demo.deliveryapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.model.LoadingDialog;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends BaseActivity {
    EditText et_full_name, et_email, et_password, et_phone;
    ImageView img_back;
    TextInputLayout lo_email, lo_name, lo_password, lo_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_full_name = findViewById(R.id.et_full_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_phone = findViewById(R.id.et_phone);
        lo_email = findViewById(R.id.layout_email);
        lo_name = findViewById(R.id.layout_name);
        lo_password = findViewById(R.id.layout_password);
        lo_phone = findViewById(R.id.layout_phone);
        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_email.setBackgroundTintList(getResources().getColorStateList(Tools.isValidEmail(charSequence) ? R.color.black : R.color.red));
                et_email.setTextColor(getResources().getColor(Tools.isValidEmail(charSequence) ? R.color.black : R.color.red));
                lo_email.setHintTextColor(getResources().getColorStateList(Tools.isValidEmail(charSequence) ? R.color.black : R.color.red));
                lo_email.setHint(getResources().getString(Tools.isValidEmail(charSequence) ? R.string.email_address : R.string.invalid_email));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void handleClick(View view) {
        if (view.getId() == R.id.btn_register ) {
            lo_name.setHintTextColor(getResources().getColorStateList(R.color.black));
            lo_email.setHintTextColor(getResources().getColorStateList(R.color.black));
            lo_password.setHintTextColor(getResources().getColorStateList(R.color.black));
            lo_phone.setHintTextColor(getResources().getColorStateList(R.color.black));
            et_full_name.setBackgroundTintList(getResources().getColorStateList(R.color.black));
            et_email.setBackgroundTintList(getResources().getColorStateList(R.color.black));
            et_password.setBackgroundTintList(getResources().getColorStateList(R.color.black));
            et_phone.setBackgroundTintList(getResources().getColorStateList(R.color.black));
            if (et_full_name.getText().toString().length() == 0) {
                et_full_name.requestFocus();
                et_full_name.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                lo_name.setHintTextColor(getResources().getColorStateList(R.color.red));
                return;
            }
            if (!Tools.isValidEmail(et_email.getText().toString())) {
                et_email.requestFocus();
                et_email.setTextColor(getResources().getColor(R.color.red));
                et_email.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                lo_email.setHintTextColor(getResources().getColorStateList(R.color.red));
                lo_email.setHint(getResources().getString(et_email.getText().toString().length() > 0 ? R.string.invalid_email : R.string.input_email));
                lo_email.setHintTextColor(getResources().getColorStateList(R.color.red));
                return;
            }
            if (et_password.getText().toString().length() == 0) {
                et_password.requestFocus();
                et_password.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                lo_password.setHintTextColor(getResources().getColorStateList(R.color.red));
                return;
            }
            if (et_phone.getText().toString().length() == 0) {
                et_phone.requestFocus();
                et_phone.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                lo_phone.setHintTextColor(getResources().getColorStateList(R.color.red));
                return;
            }
        }
        final LoadingDialog dlg = new LoadingDialog(RegisterActivity.this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(myIntent);
                dlg.dismiss();
                RegisterActivity.this.finish();
            }
        },1000);
        dlg.show();


    }
}
