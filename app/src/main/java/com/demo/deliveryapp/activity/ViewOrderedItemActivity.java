package com.demo.deliveryapp.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.adapter.OrderedItemAdapter;
import com.demo.deliveryapp.data.Product;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderedItemActivity extends BaseActivity {
    Button img_close;
    RecyclerView rv_items;
    OrderedItemAdapter adapter;
    List<Product> orderlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ordered_item);
        rv_items = findViewById(R.id.rv_items);
        ImageView img_close;
        img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setData();
        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    void setData() {
        int count = (int) (Math.random()*100%10);
        for (int i = 0; i< count; i++){
            orderlist.add(new Product(true));
        }
        rv_items.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new OrderedItemAdapter(getBaseContext(), orderlist);
        rv_items.setAdapter(adapter);

    }
}
