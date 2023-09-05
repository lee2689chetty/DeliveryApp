package com.demo.deliveryapp.model;

import static com.demo.deliveryapp.Helper.Tools.editTextSetContentMemorizeSelection;
import static com.demo.deliveryapp.Helper.Tools.formatStrWithSpaces;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.LoginActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;

public class RoleSelectDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public Context context;
    private onConfirmListener onConfirmListener;
    public BottomSheetDialog bottomSheetDialog;
    private ConstraintLayout deliveryLayout, collectionLayout;
    LinearLayout btnDelivery, btnCollection;
    Button btnConfirm, btnContinue;
    private EditText edtPostcode;
    public int mode = 0;
    int count = 0;
    public RoleSelectDialog(Context context) {
        this.context = context;

        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.role_select_dialog);
        btnDelivery = bottomSheetDialog.findViewById(R.id.btn_delivery);
        btnCollection = bottomSheetDialog.findViewById(R.id.btn_collection);
        deliveryLayout = bottomSheetDialog.findViewById(R.id.delivery_layout);
        collectionLayout = bottomSheetDialog.findViewById(R.id.collection_layout);
        edtPostcode = bottomSheetDialog.findViewById(R.id.et_postcode);
        btnConfirm = bottomSheetDialog.findViewById(R.id.btn_confirm);
        btnContinue = bottomSheetDialog.findViewById(R.id.btn_continue);
        btnDelivery.setOnClickListener(this);
        btnCollection.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

        edtPostcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String origin = charSequence.toString().replaceAll(" ", "");
                String formatStr = formatStrWithSpaces(origin);
                if (!charSequence.toString().equals(formatStr)) {
                    editTextSetContentMemorizeSelection(edtPostcode, formatStr);
                    if (i1 == 0 && count == 1 && formatStr.charAt(edtPostcode.getSelectionEnd() - 1) == ' ') {
                        edtPostcode.setSelection(edtPostcode.getSelectionEnd() + 1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
            layoutParams.height = windowHeight / 2;
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
        final LoadingDialog dlg = new LoadingDialog((Activity) context);
        Handler handler = new Handler();
        switch (view.getId()) {
            case R.id.btn_delivery:
                btnDelivery.setBackgroundColor(context.getResources().getColor(R.color.back_secondary));
                btnCollection.setBackgroundColor(context.getResources().getColor(R.color.white));
                deliveryLayout.setVisibility(View.VISIBLE);
                collectionLayout.setVisibility(View.GONE);
                mode = 1;
                break;
            case R.id.btn_collection:
                btnCollection.setBackgroundColor(context.getResources().getColor(R.color.back_secondary));
                btnDelivery.setBackgroundColor(context.getResources().getColor(R.color.white));
                deliveryLayout.setVisibility(View.GONE);
                collectionLayout.setVisibility(View.VISIBLE);
                mode = 2;
                break;
            case R.id.btn_confirm:
                if (onConfirmListener != null)
                    onConfirmListener.setType(1);
                bottomSheetDialog.dismiss();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dlg.dismiss();
                    }
                },2000);
                dlg.show();
                break;
            case R.id.btn_continue:
                if (edtPostcode.getText().length() != 9)
                    edtPostcode.setError("Please input postcode");
                else {
                    if (onConfirmListener != null)
                        onConfirmListener.setType(0);
                    bottomSheetDialog.dismiss();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dlg.dismiss();
                        }
                    },2000);
                    dlg.show();
                }
                break;
        }
    }

    public void show() {
        bottomSheetDialog.show();
    }
    public void show(int selected) {
        if(selected == 0)
        {
            btnDelivery.performClick();
        }
        else{
            btnCollection.performClick();
        }
            bottomSheetDialog.show();
    }


    public void setClickListener(onConfirmListener confirmListener) {
        this.onConfirmListener = confirmListener;
    }

    public interface onConfirmListener {
        void setType(int type);
    }

}
