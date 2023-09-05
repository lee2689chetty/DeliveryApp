package com.demo.deliveryapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.fragment.FragmentMyOrders;

public class OrderConfirmedActivity extends BaseActivity {
    Button btn_myorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_out_left);
        btn_myorder = findViewById(R.id.btn_myorder);
        btn_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderConfirmedActivity.this, MainActivity.class);
                i.putExtra("from_ordered", 1);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
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
}
