package com.demo.deliveryapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotActivity extends BaseActivity {

    EditText et_email;
    TextInputLayout lo_email;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        et_email = findViewById(R.id.et_forgot);
        lo_email = findViewById(R.id.layout_email);
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

        Button btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (!Tools.isValidEmail(et_email.getText().toString())) {
                    et_email.requestFocus();
                    et_email.setTextColor(getResources().getColor(R.color.red));
                    et_email.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    lo_email.setHintTextColor(getResources().getColorStateList(R.color.red));
                    lo_email.setHint(getResources().getString(et_email.getText().toString().length() > 0 ? R.string.invalid_email : R.string.input_email));
                    lo_email.setHintTextColor(getResources().getColorStateList(R.color.red));
                    return;
                }
            }
        });

        View btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(myIntent);
                ForgotActivity.this.finish();
            }
        });

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
