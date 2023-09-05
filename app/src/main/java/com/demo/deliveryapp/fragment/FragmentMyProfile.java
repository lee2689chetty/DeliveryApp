package com.demo.deliveryapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.demo.deliveryapp.activity.AddAddressActivity;
import com.demo.deliveryapp.activity.MainActivity;
import com.demo.deliveryapp.adapter.AddressAdapter;
import com.demo.deliveryapp.custom.AdressAdapter;
import com.demo.deliveryapp.custom.CartItemsAdapter;
import com.demo.deliveryapp.data.Adress;
import com.demo.deliveryapp.model.LoadingDialog;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyProfile extends Fragment implements AdressAdapter.ItemClickListener {
    Context m_context;
    RecyclerView rcAdress;
    ArrayList<String> address = new ArrayList<>();
    AddressAdapter adapter;
    TextView tv_addnew;
    TextView tv_change_password;
    EditText et_phone, et_full_name, et_email;
    Button btnUpdate;
    LinearLayout infoView;

    public static FragmentMyProfile newInstance() {
        return new FragmentMyProfile();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_myprofile, container, false);
        rcAdress = fragmentLayout.findViewById(R.id.rv_address);
        tv_addnew = fragmentLayout.findViewById(R.id.tv_addnew);
        tv_change_password = fragmentLayout.findViewById(R.id.tv_change_password);
        et_phone = fragmentLayout.findViewById(R.id.et_phone);
        et_email = fragmentLayout.findViewById(R.id.et_email);
        et_full_name = fragmentLayout.findViewById(R.id.et_full_name);
        btnUpdate = fragmentLayout.findViewById(R.id.btn_update);
        infoView = fragmentLayout.findViewById(R.id.info_view);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LoadingDialog dlg = new LoadingDialog(getActivity());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dlg.dismiss();
                    }
                },1000);
                dlg.show();
            }
        });
        tv_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                activity.loadFragmentOnTopWithAnim(FragmentAddAddress.newInstance());
            }
        });

        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });

        final SkeletonScreen skeletonScreen = Skeleton.bind(infoView)
                .load(R.layout.veil_listitem_medium)
                .color(R.color.white)
                .show();
        Handler veilHandler = new Handler();
        veilHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        },2000);


        /* Fasa */
//        et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_phone.setHint("");
//                else
//                    et_phone.setHint(R.string.phone_hint);
//            }
//        });
//
//        et_full_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_full_name.setHint("");
//                else
//                    et_full_name.setHint(R.string.full_name_hint);
//            }
//        });
//
//        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    et_email.setHint("");
//                else
//                    et_email.setHint(R.string.email_hint);
//            }
//        });

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        adressList = StorageHelper.getAdresses();
        setData();
        MainActivity activity = (MainActivity)getActivity();
        activity.showProfile();
        activity.hideSearchToggle();
    }

    AdressAdapter adressAdapter;
    List<Adress> adressList;

    void setData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(m_context);
        rcAdress.setLayoutManager(layoutManager);
        rcAdress.requestDisallowInterceptTouchEvent(true);
        adressAdapter = new AdressAdapter(m_context, adressList, false, true);
        adressAdapter.setClickListener(this);
        rcAdress.setAdapter(adressAdapter);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rcAdress)
                .adapter(adressAdapter)
                .load(R.layout.veil_listitem_address)
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
        alertDialog.setMessage(getString(R.string.remove_adress));
        alertDialog.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StorageHelper.removeAdress(adressList.get(position));
                        adressAdapter.notifyItemRemoved(position);
                        adressAdapter.notifyItemRangeChanged(position, address.size());
                        dialog.dismiss();
                        Tools.showMsg(m_context, "Adress Removed");
                    }
                });
        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void showChangePasswordDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText oldPassword = dialogView.findViewById(R.id.tv_old_pass);
        final EditText newPassword = dialogView.findViewById(R.id.tv_new_pass);
        final EditText reNewPassword = dialogView.findViewById(R.id.tv_re_pass);

        dialogBuilder.setTitle("Change Password");
        dialogBuilder.setPositiveButton(Html.fromHtml("<font color='#000000'>Yes</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                final LoadingDialog dlg = new LoadingDialog(getActivity());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dlg.dismiss();
                        Tools.showMsg(m_context, "Password Updated");
                    }
                },1000);
                dlg.show();

            }
        });
        dialogBuilder.setNegativeButton(Html.fromHtml("<font color='#000000'>No</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void onItemClick(int position, int event) {
        confirmDeletedAt(position);
    }
}

