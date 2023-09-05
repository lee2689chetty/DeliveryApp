package com.demo.deliveryapp.model;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.data.Crust;
import com.demo.deliveryapp.data.Product;
import com.demo.deliveryapp.data.Sauces;
import com.demo.deliveryapp.data.Size;
import com.demo.deliveryapp.data.Topping;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;

public class CartDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public Context context;
    private Pizza pizza;
    int count = 0;
    double unitCost = 0;
    double originalCost = 0;
    TextView countView, price, name, description, descNoOption, imgNoOption;
    Button btnDecraese, btnIncrease;
    ConstraintLayout btnAdd, btnClose, noOptionView, optionView;
    BottomSheetDialog bottomSheetDialog;
    ScrollView optoinScrollView;
    private onAddCartListener addCartListener;
    OrderData data;
    private Integer sauce, toppingSm = 0, toppingLg = 0, size;
    private LinearLayout btnSizeSmall, btnSizeLarge, btnSauceSm, btnSauceMd, btnSauceLg, btnSauceXl, btnToppingSm, btnToppingLg;
    private ImageView radioSizeSmall, radioSizeLarge, radioSauceSm, radioSauceMd, radioSauceLg, radioSauceXl;
    private TextView countToppingSm, countToppingLg, requireSize, requireTopping;

    private final float[] sizeBonus = new float[]{5.99f, 9.99f};
    private final float[] sauceBonus = new float[]{5.99f, 9.99f, 19.99f, 29.99f};
    private final float[] toppingBonus = new float[]{299f, 199f};

    public CartDialog(Context context, Pizza pizza) {
        this.context = context;
        this.pizza = pizza;
        this.unitCost = pizza.price;
        originalCost = unitCost;
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.add_to_cart_dialog);
        name = bottomSheetDialog.findViewById(R.id.product_name);
        description = bottomSheetDialog.findViewById(R.id.product_desc);
        descNoOption = bottomSheetDialog.findViewById(R.id.product_desc_no_option);
        countView = bottomSheetDialog.findViewById(R.id.txt_count);
        btnDecraese = bottomSheetDialog.findViewById(R.id.btn_decrease);
        btnIncrease = bottomSheetDialog.findViewById(R.id.btn_increase);
        btnAdd = bottomSheetDialog.findViewById(R.id.btn_add_cart);
        price = bottomSheetDialog.findViewById(R.id.txt_price);
        btnClose = bottomSheetDialog.findViewById(R.id.btn_close);
        noOptionView = bottomSheetDialog.findViewById(R.id.no_option_view);
        optionView = bottomSheetDialog.findViewById(R.id.option_view);
        btnSizeSmall = bottomSheetDialog.findViewById(R.id.size_small);
        btnSizeLarge = bottomSheetDialog.findViewById(R.id.size_large);
        btnSauceSm = bottomSheetDialog.findViewById(R.id.sauce_sm);
        btnSauceMd = bottomSheetDialog.findViewById(R.id.sauce_md);
        btnSauceLg = bottomSheetDialog.findViewById(R.id.sauce_lg);
        btnSauceXl = bottomSheetDialog.findViewById(R.id.sauce_xl);
        btnToppingSm = bottomSheetDialog.findViewById(R.id.topping_small);
        btnToppingLg = bottomSheetDialog.findViewById(R.id.topping_large);
        radioSizeSmall = bottomSheetDialog.findViewById(R.id.radio_size_small);
        radioSizeLarge = bottomSheetDialog.findViewById(R.id.radio_size_large);
        radioSauceSm = bottomSheetDialog.findViewById(R.id.radio_sauce_sm);
        radioSauceMd = bottomSheetDialog.findViewById(R.id.radio_sauces_md);
        radioSauceLg = bottomSheetDialog.findViewById(R.id.radio_sauces_lg);
        radioSauceXl = bottomSheetDialog.findViewById(R.id.radio_sauces_xl);
        countToppingSm = bottomSheetDialog.findViewById(R.id.count_topping_small);
        countToppingLg = bottomSheetDialog.findViewById(R.id.count_topping_large);
        requireSize = bottomSheetDialog.findViewById(R.id.require_size);
        requireTopping = bottomSheetDialog.findViewById(R.id.require_topping);
        optoinScrollView = bottomSheetDialog.findViewById(R.id.option_scroll);
        btnDecraese.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnSizeLarge.setOnClickListener(this);
        btnSizeSmall.setOnClickListener(this);
        btnSauceSm.setOnClickListener(this);
        btnSauceMd.setOnClickListener(this);
        btnSauceLg.setOnClickListener(this);
        btnSauceXl.setOnClickListener(this);
        btnToppingSm.setOnClickListener(this);
        btnToppingLg.setOnClickListener(this);

        noOptionView.setVisibility(pizza.hasOption ? View.GONE : View.VISIBLE);
        optionView.setVisibility(pizza.hasOption ? View.VISIBLE : View.GONE);
        name.setText(pizza.name);
        description.setText(pizza.desc);
        descNoOption.setText(pizza.desc);

        try {
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setupFullHeight(bottomSheetDialog);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = pizza.hasOption ? windowHeight : windowHeight * 4 / 5;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_decrease:
                count = count > 0 ? count - 1 : count;
                updateView();
                break;
            case R.id.btn_increase:
                count++;
                updateView();
                break;
            case R.id.btn_add_cart:

                if (pizza.hasOption) {
                    if (toppingSm == 0 && toppingLg == 0) {
                        validate();
                        break;
                    }
                    if (size == null) {
                        validate();
                        break;
                    }
                }
                if (count > 0) {
                    addProduct();
                    if (addCartListener != null)
                        addCartListener.add(data);
                } else {
                    Tools.showMsg(context, "Please select number");
                }
                break;
            case R.id.btn_close:
                bottomSheetDialog.dismiss();
                break;
            case R.id.size_small:
                radioSizeLarge.setVisibility(View.GONE);
                radioSizeSmall.setVisibility(View.VISIBLE);
                size = 0;
                break;
            case R.id.size_large:
                radioSizeSmall.setVisibility(View.GONE);
                radioSizeLarge.setVisibility(View.VISIBLE);
                size = 1;
                break;
            case R.id.sauce_sm:
                radioSauceMd.setVisibility(View.GONE);
                radioSauceLg.setVisibility(View.GONE);
                radioSauceXl.setVisibility(View.GONE);
                radioSauceSm.setVisibility(View.VISIBLE);
                sauce = 0;
                break;
            case R.id.sauce_md:
                radioSauceSm.setVisibility(View.GONE);
                radioSauceLg.setVisibility(View.GONE);
                radioSauceXl.setVisibility(View.GONE);
                radioSauceMd.setVisibility(View.VISIBLE);
                sauce = 1;
                break;
            case R.id.sauce_lg:
                radioSauceMd.setVisibility(View.GONE);
                radioSauceSm.setVisibility(View.GONE);
                radioSauceXl.setVisibility(View.GONE);
                radioSauceLg.setVisibility(View.VISIBLE);
                sauce = 2;
                break;
            case R.id.sauce_xl:
                radioSauceMd.setVisibility(View.GONE);
                radioSauceLg.setVisibility(View.GONE);
                radioSauceSm.setVisibility(View.GONE);
                radioSauceXl.setVisibility(View.VISIBLE);
                sauce = 3;
                break;
            case R.id.topping_small:
                toppingSm = toppingSm == 2 ? 0 : toppingSm + 1;
                countToppingSm.setVisibility(toppingSm == 0 ? View.GONE : View.VISIBLE);
                countToppingSm.setText(String.valueOf(toppingSm));
                break;
            case R.id.topping_large:
                toppingLg = toppingLg == 2 ? 0 : toppingLg + 1;
                countToppingLg.setVisibility(toppingLg == 0 ? View.GONE : View.VISIBLE);
                countToppingLg.setText(String.valueOf(toppingLg));
                break;
        }
        getPrice();
    }

    public void getPrice() {
        float bonusSize = 0;
        float bonusSauce = 0;
        if (size != null)
            bonusSize = sizeBonus[size];
        if (sauce != null)
            bonusSauce = sauceBonus[sauce];
        unitCost = originalCost + bonusSize + bonusSauce + toppingBonus[0] * toppingSm / 100 + toppingBonus[1] * toppingLg / 100;
        updateView();
    }

    public void validate() {
        requireSize.setTextColor(context.getResources().getColor(size == null ? R.color.red : R.color.black));
        requireSize.setBackgroundResource(size == null ? R.drawable.border_error : R.drawable.border);
        requireTopping.setTextColor(context.getResources().getColor(toppingLg == 0 && toppingSm == 0 ? R.color.red : R.color.black));
        requireTopping.setBackgroundResource(toppingLg == 0 && toppingSm == 0 ? R.drawable.border_error : R.drawable.border);
        if (toppingLg == 0 && toppingSm == 0 && size != null) {
            optoinScrollView.fullScroll(View.FOCUS_DOWN);
        }
    }

    public void show() {
        bottomSheetDialog.show();
    }

    public void updateView() {
        countView.setText(String.valueOf(count));
        float totalprice = ((int) (unitCost * 100) * count) / 100f;
        price.setText("£" + String.valueOf(totalprice));
    }

    public void addProduct() {
        Product product;
        if (pizza.hasOption) {
            Size mSize = new Size(size == 0 ? Size.SMALL : Size.LARGE);
            Sauces crust = null;
            if (sauce != null)
                crust = new Sauces(sauce == 0 ? Sauces.SM : sauce == 1 ? Sauces.LG : sauce == 2 ? Sauces.XL : Sauces.XXL);
            Topping topping = new Topping(toppingSm, toppingLg);
            product = new Product(pizza.name, mSize, crust, topping);
        } else {
            product = new Product(true);
        }
        product.setQuantity(count);
        product.finalzie();
        StorageHelper.addToCart(product);
        bottomSheetDialog.dismiss();
        try {
            MainActivity instance = (MainActivity) context;
            instance.changeCountCallback(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClickListener(onAddCartListener addCartListener) {
        this.addCartListener = addCartListener;
    }

    public interface onAddCartListener {
        void add(OrderData data);
    }

}
