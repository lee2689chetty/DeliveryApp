package com.demo.deliveryapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;

public class FragmentTrackOrder extends Fragment {
    Button btn_viewordereditems;
    CardView cardView01, cardView02, cardView03, cardView04;
    ImageView circleImg11, circleImg12, circleImg21, circleImg22;
    ImageView circleImg31, circleImg32, circleImg41, circleImg42;
    View line01, line02, line03;

    public static FragmentTrackOrder newInstance() {
        return new FragmentTrackOrder();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.activity_track_order, container, false);
        btn_viewordereditems = fragmentLayout.findViewById(R.id.btn_viewordereditems);
        cardView01 = fragmentLayout.findViewById(R.id.cardView1);
        cardView02 = fragmentLayout.findViewById(R.id.cardView2);
        cardView03 = fragmentLayout.findViewById(R.id.cardView3);
        cardView04 = fragmentLayout.findViewById(R.id.cardView4);

        circleImg11 = fragmentLayout.findViewById(R.id.imageView11);
        circleImg12 = fragmentLayout.findViewById(R.id.imageView12);
        circleImg21 = fragmentLayout.findViewById(R.id.imageView21);
        circleImg22 = fragmentLayout.findViewById(R.id.imageView22);
        circleImg31 = fragmentLayout.findViewById(R.id.imageView31);
        circleImg32 = fragmentLayout.findViewById(R.id.imageView32);
        circleImg41 = fragmentLayout.findViewById(R.id.imageView41);
        circleImg42 = fragmentLayout.findViewById(R.id.imageView42);

        line01 = fragmentLayout.findViewById(R.id.lineview12);
        line02 = fragmentLayout.findViewById(R.id.lineview23);
        line03 = fragmentLayout.findViewById(R.id.lineview34);

        btn_viewordereditems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.loadFragmentOnTop(FragmentViewOrderedItems.newInstance());
            }
        });

//        ImageView img_close = fragmentLayout.findViewById(R.id.img_close);
//        img_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        setUIThrid();

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    public void setUI(){
        cardView01.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        cardView02.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        cardView03.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        cardView04.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        circleImg11.setImageResource(R.drawable.gray);
        circleImg12.setImageResource(R.drawable.gray);
        circleImg21.setImageResource(R.drawable.gray);
        circleImg22.setImageResource(R.drawable.gray);
        circleImg31.setImageResource(R.drawable.gray);
        circleImg32.setImageResource(R.drawable.gray);
        circleImg41.setImageResource(R.drawable.gray);
        circleImg42.setImageResource(R.drawable.gray);
        line01.setBackgroundColor(0xffb3b3b3); //0xff24A802
        line02.setBackgroundColor(0xffb3b3b3);
        line03.setBackgroundColor(0xffb3b3b3);
    }

    public void setUIFirst(){
        cardView01.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView02.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        cardView03.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        cardView04.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        circleImg11.setImageResource(R.drawable.green);
        circleImg12.setImageResource(R.drawable.green);
        circleImg21.setImageResource(R.drawable.gray);
        circleImg22.setImageResource(R.drawable.gray);
        circleImg31.setImageResource(R.drawable.gray);
        circleImg32.setImageResource(R.drawable.gray);
        circleImg41.setImageResource(R.drawable.gray);
        circleImg42.setImageResource(R.drawable.gray);
        line01.setBackgroundColor(0xffb3b3b3); //0xff24A802
        line02.setBackgroundColor(0xffb3b3b3);
        line03.setBackgroundColor(0xffb3b3b3);
    }

    public void setUISecond(){
        cardView01.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView02.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView03.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        cardView04.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        circleImg11.setImageResource(R.drawable.green);
        circleImg12.setImageResource(R.drawable.green);
        circleImg21.setImageResource(R.drawable.green);
        circleImg22.setImageResource(R.drawable.green);
        circleImg31.setImageResource(R.drawable.gray);
        circleImg32.setImageResource(R.drawable.gray);
        circleImg41.setImageResource(R.drawable.gray);
        circleImg42.setImageResource(R.drawable.gray);
        line01.setBackgroundColor(0xff24A802); //0xff24A802
        line02.setBackgroundColor(0xffb3b3b3);
        line03.setBackgroundColor(0xffb3b3b3);
    }

    public void setUIThrid(){
        cardView01.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView02.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView03.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        //cardView04.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.transparent, null));
        cardView04.setBackground(null);

        circleImg11.setImageResource(R.drawable.green);
        circleImg12.setImageResource(R.drawable.green);
        circleImg21.setImageResource(R.drawable.green);
        circleImg22.setImageResource(R.drawable.green);
        circleImg31.setImageResource(R.drawable.green);
        circleImg32.setImageResource(R.drawable.green);
        circleImg41.setImageResource(R.drawable.gray);
        circleImg42.setImageResource(R.drawable.gray);
        line01.setBackgroundColor(Color.parseColor("#26b50e")); //0xff24A802
        line02.setBackgroundColor(Color.parseColor("#26b50e"));
        line03.setBackgroundColor(Color.parseColor("#80CCCCCC"));

    }

    public void setUIFourth(){
        cardView01.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView02.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView03.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        cardView04.setCardBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        circleImg11.setImageResource(R.drawable.green);
        circleImg12.setImageResource(R.drawable.green);
        circleImg21.setImageResource(R.drawable.green);
        circleImg22.setImageResource(R.drawable.green);
        circleImg31.setImageResource(R.drawable.green);
        circleImg32.setImageResource(R.drawable.green);
        circleImg41.setImageResource(R.drawable.green);
        circleImg42.setImageResource(R.drawable.green);
        line01.setBackgroundColor(0xff24A802); //0xff24A802
        line02.setBackgroundColor(0xff24A802);
        line03.setBackgroundColor(0xff24A802);
    }
}
