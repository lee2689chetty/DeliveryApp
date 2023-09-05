package com.demo.deliveryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.model.LoadingDialog;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    EditText et_user_name, et_password;
    TextInputLayout lo_name, lo_password;
    ConstraintLayout loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogIn = (Button) findViewById(R.id.btn_login);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                final LoadingDialog dlg = new LoadingDialog(LoginActivity.this);
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dlg.dismiss();
//                        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(myIntent);
//                        LoginActivity.this.finish();
//                    }
//                },2000);
//                dlg.show();
                if(Tools.isValidEmail(et_user_name.getText().toString())) {
                    if(et_password.getText().toString().length()>0) {
                        final LoadingDialog dlg = new LoadingDialog(LoginActivity.this);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dlg.dismiss();
                                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(myIntent);
                                LoginActivity.this.finish();
                            }
                        },2000);
                        dlg.show();
                    }
                    else{
                        et_password.requestFocus();
                        et_password.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                        lo_password.setHintTextColor(getResources().getColorStateList(R.color.red));
                    }
                }
                else{
                    et_user_name.requestFocus();
                    et_user_name.setTextColor(getResources().getColor(R.color.red));
                    et_user_name.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    lo_name.setHintTextColor(getResources().getColorStateList(R.color.red));
                    lo_name.setHint(getResources().getString(et_user_name.getText().toString().length()>0?R.string.invalid_email:R.string.input_email));
                }
            }
        });
        View btn_register = (TextView) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        TextView txtForgot = (TextView) findViewById(R.id.text_forgot);
        txtForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(myIntent);
            }
        });
        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);
        lo_name = findViewById(R.id.tl_user_name);
        lo_password = findViewById(R.id.lo_password);
        loader = findViewById(R.id.loading);
        loader.setVisibility(View.GONE);
        et_user_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_user_name.setHint("");
                }
            }
        });
        et_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_user_name.setBackgroundTintList(getResources().getColorStateList(Tools.isValidEmail(charSequence) ? R.color.black : R.color.red));
                et_user_name.setTextColor(getResources().getColor(Tools.isValidEmail(charSequence) ? R.color.black : R.color.red));
                lo_name.setHintTextColor(getResources().getColorStateList(Tools.isValidEmail(charSequence) ? R.color.black : R.color.red));
                lo_name.setHint(getResources().getString(Tools.isValidEmail(charSequence) ? R.string.email_address : R.string.invalid_email));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
