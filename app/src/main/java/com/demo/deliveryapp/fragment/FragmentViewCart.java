package com.demo.deliveryapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.DeliveryActivity;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.custom.CartItemsAdapter;
import com.demo.deliveryapp.data.Product;
import com.demo.deliveryapp.listener.CartCountListener;
import com.demo.deliveryapp.model.LoadingDialog;
import com.demo.deliveryapp.model.RoleSelectDialog;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.androidveil.VeilRecyclerFrameView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import co.ceryle.segmentedbutton.SegmentedButton;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class FragmentViewCart extends Fragment implements CartCountListener {
    Context m_context;
    LinearLayout ll_payment;
    RecyclerView rv_viewcart;
    ArrayList<String> vc_titles, vc_subtitles, vc_contents;
    CartItemsAdapter adapter;
    EditText et_coupon, et_nodes;
    public CardView card_empty;
    TextView tvAmount, tvAmountTotal, hint_coupon;
    RoleSelectDialog typeSelector;
    SegmentedButton btnDelivery, btnCollection;
    SegmentedButtonGroup segmentedButtonGroup;
    ImageButton btn_cuopon;
    TextInputLayout lo_cuopon;
    ConstraintLayout badgeCuopon, discount_area;
    boolean cuoponSet = false;

    public static FragmentViewCart newInstance() {
        return new FragmentViewCart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_viewcart, container, false);

        tvAmount = fragmentLayout.findViewById(R.id.tvAmount);
        tvAmountTotal = fragmentLayout.findViewById(R.id.tvAmountTotal);

        // set up the RecyclerView
        vc_titles = new ArrayList<>();
        vc_subtitles = new ArrayList<>();
        vc_contents = new ArrayList<>();
        card_empty = fragmentLayout.findViewById(R.id.card_empty);

        // set up the RecyclerView
        rv_viewcart = fragmentLayout.findViewById(R.id.rv_view_cart);
        footerText = fragmentLayout.findViewById(R.id.footerText);
        ll_payment = fragmentLayout.findViewById(R.id.ll_payment);
        btnDelivery = fragmentLayout.findViewById(R.id.btn_delivery);
        btnCollection = fragmentLayout.findViewById(R.id.btn_collection);
        btn_cuopon = fragmentLayout.findViewById(R.id.btn_cuopon);
        hint_coupon = fragmentLayout.findViewById(R.id.cuopon_hint);
        lo_cuopon = fragmentLayout.findViewById(R.id.layout_cuopon);
        badgeCuopon = fragmentLayout.findViewById(R.id.claimed_badge);
        discount_area = fragmentLayout.findViewById(R.id.discount_area);
        /* Fasa */
        typeSelector = new RoleSelectDialog(m_context);
        segmentedButtonGroup = (SegmentedButtonGroup) fragmentLayout.findViewById(R.id.type_selector);
        segmentedButtonGroup.setPosition(Tools.deliverySelected ? 0 : 1, false);
        segmentedButtonGroup.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                typeSelector.show(position);
            }
        });
        typeSelector.setClickListener(new RoleSelectDialog.onConfirmListener() {
            @Override
            public void setType(int type) {
                segmentedButtonGroup.setPosition(type, true);
                Tools.deliverySelected = type == 0;
            }
        });
        ll_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.loadFragmentOnTopWithTag(FragmentDelivery.newInstance(), "FragmentDelivery");
            }
        });

        et_coupon = fragmentLayout.findViewById(R.id.et_coupon);
        et_coupon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    hint_coupon.setVisibility(View.GONE);
//                else if (et_coupon.getText().toString().length() == 0) {
//                    hint_coupon.setVisibility(View.VISIBLE);
//                }
            }
        });
        et_coupon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hint_coupon.setVisibility(View.GONE);
                if (charSequence.length() == 0) {
                    hint_coupon.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_cuopon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cuoponSet) {
                    cuoponSet = false;
                } else {
//                    if (et_coupon.getText().toString().length() != 7) {
//                        et_coupon.requestFocus();
//                        lo_cuopon.setHint("PLEASE INPUT CORRECT CODE");
//                        lo_cuopon.setHintTextColor(getResources().getColorStateList(R.color.red));
//                    } else {
                        lo_cuopon.setHint("CUOPON CODE");
                        lo_cuopon.setHintTextColor(getResources().getColorStateList(R.color.black));
                        cuoponSet = true;
//                    }
                }
                updateCoupon();
            }
        });

        /* Fasa */

//        et_nodes = fragmentLayout.findViewById(R.id.et_notes);
//        et_nodes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus)
//                    et_nodes.setHint("");
//                else
//                    et_nodes.setHint(R.string.notes_hint);
//            }
//        });

//        ImageView img_close = fragmentLayout.findViewById(R.id.img_close);
//        img_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return fragmentLayout;
    }

    public void updateCoupon() {
        if (cuoponSet) {
            final LoadingDialog dlg = new LoadingDialog(getActivity());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dlg.dismiss();
                    btn_cuopon.setBackgroundResource(R.drawable.ic_close);
                    et_coupon.setVisibility(View.GONE);
                    badgeCuopon.setVisibility(View.VISIBLE);
                    et_coupon.setText(" ");
                    discount_area.setVisibility(View.VISIBLE);
                }
            },1000);
            dlg.show();
        } else {
            btn_cuopon.setBackgroundResource(R.drawable.ic_arrow);
            et_coupon.setVisibility(View.VISIBLE);
            et_coupon.setText("");
            badgeCuopon.setVisibility(View.GONE);
            discount_area.setVisibility(View.GONE);
        }
        fillDataFooter();
    }


    List<Product> cartItems;

    @Override
    public void onResume() {
        super.onResume();
        cartItems = StorageHelper.getCartItems();
        changeCountCallback(cartItems.size());
        setData();
        fillDataFooter();

        MainActivity activity = (MainActivity) getActivity();
        activity.showViewCart();
        activity.hideSearchToggle();
    }

    void setData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(m_context);
        rv_viewcart.setLayoutManager(layoutManager);
        rv_viewcart.requestDisallowInterceptTouchEvent(true);
        adapter = new CartItemsAdapter(m_context, cartItems);
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
        final SkeletonScreen skeletonScreen = Skeleton.bind(rv_viewcart)
                .adapter(adapter)
                .load(R.layout.veil_listitem_cart)
                .duration(1000)
                .color(R.color.white)
                .show();
        Handler veilHandler = new Handler();
        veilHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        },2000);
    }

    private void confirmDeletedAt(final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage(getString(R.string.remove_item));
        alertDialog.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(position);
                        Tools.showMsg(m_context, "Item Removed");
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

    double deliveryCharges = 4;
    TextView footerText;

    private void fillDataFooter() {
        double price = 0;
        int quantity = 0;
        for (Product product : cartItems) {
            price += product.getPrice();
            quantity += product.getQuantity();
        }
        tvAmount.setText("$" + (new DecimalFormat("##.##").format(price)));
        if (cuoponSet) {
            price = price - (price * 0.05);
        }
        tvAmountTotal.setText("$" + (new DecimalFormat("##.##").format(price + deliveryCharges)));
        footerText.setText(quantity + " Items: $" + String.valueOf(new DecimalFormat("##.##").format(price + 4)));
        changeCountCallback(0);
    }

    @Override
    public void changeCountCallback(int count) {
        if (cartItems.size() != 0) {
            card_empty.setVisibility(View.GONE);
            ll_payment.setVisibility(View.VISIBLE);
        } else {
            card_empty.setVisibility(View.VISIBLE);
            ll_payment.setVisibility(View.GONE);
        }
        cartCountListener.changeCountCallback(0);
    }

    CartCountListener cartCountListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.cartCountListener = (CartCountListener) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.cartCountListener = (CartCountListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartCountListener = null;
    }

}
