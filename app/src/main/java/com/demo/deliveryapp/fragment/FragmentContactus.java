package com.demo.deliveryapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.R;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.invoke.ConstantCallSite;

public class FragmentContactus extends Fragment implements OnMapReadyCallback{
    private View fragmentLayout;
    ConstraintLayout btnCall, btnDirection, contactDetail;
    CardView cardView3;
    /* access modifiers changed from: private */
    public GoogleMap mMap;
    private SupportMapFragment mMapFragment;
    Context m_context;

    public static FragmentContactus newInstance() {
        return new FragmentContactus();
    }

    public void onCreate(Bundle savedInstanceState) {
        this.m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            fragmentLayout = inflater.inflate(R.layout.fragment_contactus, container, false);
            this.mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (this.mMapFragment != null) {
                this.mMapFragment.getMapAsync(this);
            }
            else{
                Log.e("tag", "mMapFragment null");
            }
            this.btnDirection = fragmentLayout.findViewById(R.id.btn_direction);
            this.btnCall = fragmentLayout.findViewById(R.id.btn_call);
            this.contactDetail = fragmentLayout.findViewById(R.id.contact_detail);
            this.cardView3 = (CardView) fragmentLayout.findViewById(R.id.cardView3);
            this.btnDirection.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    LatLng loc = new LatLng(53.478162d, -2.244666d);
                    FragmentContactus.this.mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Selected Location"));
                    FragmentContactus.this.mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                    FragmentContactus.this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.latitude, loc.longitude), 18.0f));
                }
            });
            this.btnCall.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String str = "android.intent.action.DIAL";
                    if(getActivity() != null)
                        getActivity().startActivity(new Intent(str, Uri.fromParts("tel", "+18000001234", null)));
                }
            });
            this.cardView3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.putExtra("android.intent.extra.EMAIL", "help@pizzamenia.com");
                    intent.putExtra("android.intent.extra.SUBJECT", "Subject");
                    intent.putExtra("android.intent.extra.TEXT", "I'm email body.");
                    if(getActivity() != null)
                        getActivity().startActivity(Intent.createChooser(intent, "Send Email"));
                }
            });
        }
        catch (InflateException e) {
            e.printStackTrace();
        }

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final SkeletonScreen skeletonScreen = Skeleton.bind(contactDetail)
                .load(R.layout.veil_listitem_contact)
                .color(R.color.white)
                .show();
        Handler veilHandler = new Handler();
        veilHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        },2000);
        return fragmentLayout;
    }

    public void onMapReady(GoogleMap googleMap) {
        Log.e("tag", "onMapReady");
        this.mMap = googleMap;
        LatLng loc = new LatLng(53.478162d, -2.244666d);
        FragmentContactus.this.mMap.addMarker(new MarkerOptions().position(loc).title("Marker in Selected Location"));
        FragmentContactus.this.mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        FragmentContactus.this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.latitude, loc.longitude), 18.0f));
    }
}
