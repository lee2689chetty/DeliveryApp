package com.demo.deliveryapp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.demo.deliveryapp.R;
import com.weiwangcn.betterspinner.library.BetterSpinner;
import android.widget.EditText;
import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.data.Adress;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class FragmentAddAddress extends Fragment {
    Button btn_save;
    BetterSpinner spinnerAddress;
    EditText et_postcode, et_address_title, et_address, et_address2, et_city, et_postcode02;

    public static FragmentAddAddress newInstance() {
        return new FragmentAddAddress();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.activity_add_address, container, false);
        btn_save = fragmentLayout.findViewById(R.id.btn_save);
        et_postcode = fragmentLayout.findViewById(R.id.et_postcode);
        et_address_title = fragmentLayout.findViewById(R.id.et_address_title);
        et_address = fragmentLayout.findViewById(R.id.et_address);
        et_address2 = fragmentLayout.findViewById(R.id.et_address2);
        et_city = fragmentLayout.findViewById(R.id.et_city);
        et_postcode02 = fragmentLayout.findViewById(R.id.et_postcode_second);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processData();
            }
        });


        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        btn_save.setVisibility(isOpen?View.INVISIBLE:View.VISIBLE);
                    }
                });


        spinnerAddress = fragmentLayout.findViewById(R.id.spinnerAddress);
        String[] COUNTRIES = new String[]{"Address 1", "Address 2", "Address 3", "Address 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_menu_popup_item, COUNTRIES);
        spinnerAddress.setAdapter(adapter);

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }

    Adress adress = new Adress();
    private void processData() {
        if (validInput()) {
            StorageHelper.saveAdress(adress);
            Tools.showMsg(getActivity(),"Address Added");
            ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();
        }
    }

    private boolean validInput() {

        boolean validInput = true;
        et_address_title.setError(null); et_address.setError(null); et_city.setError(null);    et_postcode02.setError(null);

        adress.setTitle(et_address_title.getText().toString().trim());
        adress.setAdressLineOne(et_address.getText().toString().trim());
        adress.setAdressLineTwo(et_address2.getText().toString().trim());
        adress.setCity(et_city.getText().toString().trim());
        adress.setPostalCode(et_postcode02.getText().toString().trim());

        if (adress.getTitle().isEmpty()) {
            et_address_title.setError("*Required");
            validInput = false;
        }

        if (adress.getAdressLineOne().isEmpty()) {
            et_address.setError("*Required");
            validInput = false;
        }
        if (adress.getCity().isEmpty()) {
            et_city.setError("*Required");
            validInput = false;
        }
        if (adress.getPostalCode().isEmpty()) {
            et_postcode02.setError("*Required");
            validInput = false;
        }

        return validInput;
    }
}

