package com.demo.deliveryapp.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.demo.deliveryapp.R;

public class LoadingDialog extends Dialog {

    Dialog dialog;
    private int deviceWidth;
    public LoadingDialog(@NonNull Activity context) {
        super(context);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dlg_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = size.x;
        lp.height = size.y;
        dialog.getWindow().setAttributes(lp);
    }

    public void show(){
        if(dialog!=null && !dialog.isShowing()){
            dialog.show();
        }
    }

    public void dismiss(){
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
