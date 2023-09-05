package com.demo.deliveryapp.Helper;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.deliveryapp.R;

public class Tools {
    public static boolean deliverySelected = true;

    public static void copyToClipBoard(Context context, String title, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(title, text);
        clipboard.setPrimaryClip(clip);
        showMsg(context, "Copied to Keyboard");
    }

    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                .show();
    }

    public static void setupUI(View view, final Activity activity) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager imm = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);

                    if (imm.isAcceptingText()) {
                        Tools.hideSoftKeyboard(activity, activity.getCurrentFocus());
                    }
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                Tools.setupUI(innerView, activity);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        if (view == null) {
            return;
        }

        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void editTextSetContentMemorizeSelection(EditText editText, CharSequence charSequence) {
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();
        editText.setText(charSequence.toString());

        if (selectionStart > charSequence.toString().length()) {
            selectionStart = charSequence.toString().length();
        }
        if (selectionStart < 0) {
            selectionStart = 0;
        }

        if (selectionEnd > charSequence.toString().length()) {
            selectionEnd = charSequence.toString().length();
        }
        if (selectionEnd < 0) {
            selectionEnd = 0;
        }

        editText.setSelection(selectionStart, selectionEnd);
    }

    public static String formatStrWithSpaces(CharSequence can) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < can.length(); i++) {
            if (i != 0 && i % 4 == 0) {
                sb.append(' ');
            }
            sb.append(can.charAt(i));
        }

        return sb.toString();
    }

}
