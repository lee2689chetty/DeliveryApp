package com.demo.deliveryapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.demo.deliveryapp.Helper.StorageHelper;
import com.demo.deliveryapp.Helper.Tools;
import com.demo.deliveryapp.data.Adress;
import com.demo.deliveryapp.fragment.FragmentViewCart;
import com.demo.deliveryapp.listener.CartCountListener;
import com.demo.deliveryapp.listener.MessageEvent;
import com.demo.deliveryapp.model.RoleSelectDialog;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;

import com.demo.deliveryapp.R;
import com.demo.deliveryapp.fragment.FragmentContactus;
import com.demo.deliveryapp.fragment.FragmentExploreMenu;
import com.demo.deliveryapp.fragment.FragmentMapLocation;
import com.demo.deliveryapp.fragment.FragmentMyOrders;
import com.demo.deliveryapp.fragment.FragmentMyProfile;
import com.demo.deliveryapp.fragment.FragmentOffersDiscounts;
import com.demo.deliveryapp.fragment.FragmentTrackOrder;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, CartCountListener {
    private NavigationView navigationView;
    Fragment m_selectedFragment = null;
    BottomNavigationView bottomNavigationView;
    ImageView searchToggle;
    ViewGroup searchBar;
    ImageView searchButton;
    public EditText search;
    TextView tvLogout;
    ImageView contactus;
    ImageView backButton;
    boolean clearAll, dontLoad;
    int selectedTabId = R.id.nav_exploremenu;
    Handler backHandler;
    Runnable backRunnable;

    public void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        if (search.hasFocus())
            search.clearFocus();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isSearchBarOpen()) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideSoftKeyboard(MainActivity.this);
                            hideSearchBar();
                        }
                    }, 100);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isSearchBarOpen() {
        return searchBar.isShown();
    }

    public void showSearchToggle() {
        searchToggle.setVisibility(View.VISIBLE);
    }

    public void hideSearchToggle() {
        searchToggle.setVisibility(View.GONE);
    }

    public void hideSearchBar() {
        searchToggle.setVisibility(View.VISIBLE);
        searchBar.setVisibility(View.GONE);
    }

    public void showSearchBar() {
        searchToggle.setVisibility(View.GONE);
        searchBar.setVisibility(View.VISIBLE);
        search.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard(MainActivity.this);
                    hideSearchBar();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        getSupportActionBar().setElevation(2f);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        View customView = LayoutInflater.from(this).inflate(R.layout.custom_menu, null);
        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);

        contactus = customView.findViewById(R.id.contact_us_image);
        backButton = customView.findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                int entries = fm.getBackStackEntryCount();
                if (entries > 0) {
                    fm.popBackStack();
                }
                else{
                    MainActivity.this.loadFragment(new FragmentExploreMenu(), "FragmentExploreMenu");
                    hideBackButton();
                }
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager fm = getSupportFragmentManager();
                int entries = fm.getBackStackEntryCount();
                Log.e("tag", "entries: " + entries);
                if (entries > 0) {
                    FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(entries - 1);
                    String tag = backEntry.getName();
                    Log.e("tag", "FRAG: " + tag);
                    if (tag != null) {
                        if (tag.equals("FragmentExploreMenu") || tag.equals("FragmentOffersDiscounts") || tag.equals("FragmentMyOrders") || tag.equals("FragmentMyProfile") || tag.equals("FragmentViewCart")) {
                            hideBackButton();
                            return;
                        }
                    }
                }

                if (entries > 0) {
                    showBackButton();
                } else {
                    hideBackButton();
                }
            }
        });

        searchBar = customView.findViewById(R.id.searchBar);
        searchToggle = customView.findViewById(R.id.action_search);
        searchButton = customView.findViewById(R.id.searchButton);
        search = customView.findViewById(R.id.search);
        tvLogout = customView.findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.start(MainActivity.this);
            }
        });

        searchToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchBar();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hideSearchBar();
            }
        });
//
//        actionSearch.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                    MainActivity.this.searchDialog();
//
//            }
//        });
        contactus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.loadFragment(new FragmentContactus(), "FragmentContactus");
                showBackButton();
            }
        });

        setContentView(R.layout.activity_main);
        this.bottomNavigationView = findViewById(R.id.nav_view);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        this.bottomNavigationView.setSelectedItemId(R.id.nav_exploremenu);


        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.cart_count_layout, bottomNavigationMenuView, false);
        tvCount = badge.findViewById(R.id.tvUnreadChats);
        itemView.addView(badge);


        if (getIntent().hasExtra("from_ordered"))
            bottomNavigationView.setSelectedItemId(R.id.nav_myorders);

        backHandler = new Handler();
        backRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getSupportFragmentManager();
                if (fm == null)
                    return;
                int entries = fm.getBackStackEntryCount();
                if (entries > 0) {
                    FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(entries - 1);
                    String tag = backEntry.getName();
                    Log.e("tag", "FRAG: " + tag);
                    if (tag != null) {
                        if (tag.equals("FragmentDelivery") || tag.equals("FragmentPayment") || tag.equals("FragmentOrderConfirmed")) {
                            showViewCart();
                        }
                    }
                }
            }
        };

        Tools.setupUI(findViewById(R.id.parent_container), this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        /* Fasa */
        RoleSelectDialog roleSelectDialog = new RoleSelectDialog(this);
        roleSelectDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomNavigationView);
        roleSelectDialog.setClickListener(new RoleSelectDialog.onConfirmListener() {
            @Override
            public void setType(int type) {
                Tools.deliverySelected = type == 0;
            }
        });
        roleSelectDialog.show();
        addDummyAddress();
    }

    public void addDummyAddress(){
        if(StorageHelper.getAdresses().size()==0){
            StorageHelper.saveAdress(new Adress("Home","Home","Ghost street","circle City","123123"));
            StorageHelper.saveAdress(new Adress("Home","Home","Ghost street","circle City","123123"));
            StorageHelper.saveAdress(new Adress("Home","Home","Ghost street","circle City","123123"));
            StorageHelper.saveAdress(new Adress("Home","Home","Ghost street","circle City","123123"));
        }
    }

    public void hideBackButton() {
        backButton.setVisibility(View.GONE);
        contactus.setVisibility(View.VISIBLE);
    }

    public void showBackButton() {
        backButton.setVisibility(View.VISIBLE);
        contactus.setVisibility(View.GONE);
    }

    public void showExplore() {
        Log.e("tag", "showExplore");
        dontLoad = true;
        bottomNavigationView.setSelectedItemId(R.id.nav_exploremenu);
    }

    public void clearAll() {
        Log.e("tag", "clearAll");
        clearBackStack(true);
        //bottomNavigationView.setSelectedItemId(R.id.nav_myorders);
    }

    public void showMyOrders() {
        Log.e("tag", "showMyOrders");
        dontLoad = true;
        bottomNavigationView.setSelectedItemId(R.id.nav_myorders);
    }

    public void showCoupons() {
        Log.e("tag", "showCoupons");
        dontLoad = true;
        bottomNavigationView.setSelectedItemId(R.id.nav_offers);
    }

    public void showViewCart() {
        Log.e("tag", "showViewCart");
        dontLoad = true;
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
    }

    public void showProfile() {
        Log.e("tag", "showProfile");
        dontLoad = true;
        bottomNavigationView.setSelectedItemId(R.id.nav_myprofile);
    }

    public void loadFragment(Fragment fragment, String tag) {
        boolean clear = clearBackStack(false);
        if (!clear) {
            if (fragment instanceof FragmentViewCart) {
                return;
            }
            loadFragmentOnTopWithTag(fragment, tag);
            return;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_out_left, R.anim.slide_left_in, R.anim.slide_out_right);
            transaction.replace(R.id.frame_layout, fragment, tag);
            transaction.commit();
            handleHeaderIcons(fragment);
        }
    }

    public void loadFragmentWithAnim(Fragment fragment, String tag) {
        boolean clear = clearBackStack(false);
        if (!clear) {
            if (fragment instanceof FragmentViewCart) {
                return;
            }
            loadFragmentOnTopWithTagAndAnim(fragment, tag);
            return;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_out_left, R.anim.slide_left_in, R.anim.slide_out_right);
            transaction.replace(R.id.frame_layout, fragment, tag);
            transaction.commit();
            handleHeaderIcons(fragment);
        }
    }

    public void loadFragment(final Fragment fragment, boolean clearAll) {
        clearBackStack(clearAll);
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_out_left, R.anim.slide_left_in, R.anim.slide_out_right);
            transaction.replace(R.id.frame_layout, fragment);
            transaction.commit();
            handleHeaderIcons(fragment);
        }
    }

    public void loadFragmentOnTop(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_out_left, R.anim.slide_left_in, R.anim.slide_out_right);
            transaction.replace(R.id.frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            handleHeaderIcons(fragment);
        }
    }

    public void loadFragmentOnTopWithAnim(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_out_left, R.anim.slide_left_in, R.anim.slide_out_right);
            transaction.replace(R.id.frame_layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            handleHeaderIcons(fragment);
        }
    }

    public void loadFragmentOnTopWithTag(Fragment fragment, String tag) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_out_left, R.anim.slide_left_in, R.anim.slide_out_right);
            transaction.replace(R.id.frame_layout, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
            handleHeaderIcons(fragment);
        }
    }

    public void loadFragmentOnTopWithTagAndAnim(Fragment fragment, String tag) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_out_left, R.anim.slide_left_in, R.anim.slide_out_right);
            transaction.replace(R.id.frame_layout, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
            handleHeaderIcons(fragment);
        }
    }

    public boolean clearBackStack(boolean clearAll) {
        FragmentManager fm = getSupportFragmentManager();
        int entries = fm.getBackStackEntryCount();
        boolean clear = true;
        for (int i = 0; i < entries; ++i) {
            FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(i);
            // clear all
            if (clearAll) {
                fm.popBackStack();
                continue;
            }
            // clear not all
            String tag = backEntry.getName();
            if (tag != null) {
                if (tag.equals("FragmentDelivery") || tag.equals("FragmentAddAddress") || tag.equals("FragmentPayment") || tag.equals("FragmentOrderConfirmed")) {
                    Log.e("tag", "do not clear back stack");
                    clear = false;
                } else {
                    fm.popBackStack();
                }
            } else {
                fm.popBackStack();
            }
        }
        return clear;
    }

    private void handleHeaderIcons(Fragment fragment) {
        if (fragment instanceof FragmentMyProfile) {
            tvLogout.setVisibility(View.VISIBLE);
            Log.e("tag", "handleHeaderIcons FragmentMyProfile");
        } else {
            tvLogout.setVisibility(View.GONE);
        }

        if (fragment instanceof FragmentExploreMenu) {
            searchToggle.setVisibility(View.VISIBLE);
            Log.e("tag", "handleHeaderIcons FragmentExploreMenu");
        } else {
            searchToggle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearchBarOpen()) {
            hideSearchBar();
            return;
        }
        super.onBackPressed();
        if (backHandler != null) {
            backHandler.postDelayed(backRunnable, 500);
        }
    }

    Dialog dialog;

    public void searchDialog() {
        this.dialog = new Dialog(this);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setContentView(R.layout.stop_recording_dialog);
        this.dialog.setCancelable(false);
        this.dialog.show();
        Button searchButton = (Button) this.dialog.findViewById(R.id.search_button);
//        ((EditText) this.dialog.findViewById(R.id.search_edit_text)).setOnClickListener();
        searchButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (selectedTabId == id) {
            if (id == R.id.nav_cart) {
                Log.e("tag", "cart double press");
                if (dontLoad) {
                    dontLoad = false;
                } else {
                    loadFragment(FragmentViewCart.newInstance(), true);
                }
                return true;
            }
        }

        selectedTabId = id;

        String tag = "";
        if (id == R.id.nav_address) {
//            if (!getSupportActionBar().getTitle().toString().equals("Contact us")){
//                Intent myIntent = new Intent(this, MapsActivity.class);
//                startActivity(myIntent);
//                this.finish();
//            }
        } else if (id == R.id.nav_phone) {
            String phone = "+18000001234";
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        } else if (id == R.id.nav_openingweek1) {
            tag = "FragmentMapLocation";
            m_selectedFragment = FragmentMapLocation.newInstance();
            getSupportActionBar().setTitle("132 Cherry Street, NN3, 2HF");
        } else if (id == R.id.nav_openingweek2) {
            tag = "FragmentMapLocation";
            m_selectedFragment = FragmentMapLocation.newInstance();
            getSupportActionBar().setTitle("132 Cherry Street, NN3, 2HF");
        } else if (id == R.id.nav_trackorder) {
            tag = "FragmentTrackOrder";
            m_selectedFragment = FragmentTrackOrder.newInstance();
        } else if (id == R.id.nav_exploremenu) {
            tag = "FragmentExploreMenu";
            m_selectedFragment = FragmentExploreMenu.newInstance();
        } else if (id == R.id.nav_offers) {
            tag = "FragmentOffersDiscounts";
            m_selectedFragment = FragmentOffersDiscounts.newInstance();
        } else if (id == R.id.nav_myorders) {
            tag = "FragmentMyOrders";
            m_selectedFragment = FragmentMyOrders.newInstance();
        } else if (id == R.id.nav_myprofile) {
            tag = "FragmentMyProfile";
            m_selectedFragment = FragmentMyProfile.newInstance();
        } else if (id == R.id.nav_contactus) {
            tag = "FragmentContactus";
            m_selectedFragment = FragmentContactus.newInstance();
        } else if (id == R.id.nav_logout) {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
            this.finish();
        } else if (id == R.id.nav_cart) {
            tag = "FragmentViewCart";
            m_selectedFragment = FragmentViewCart.newInstance();
        }

        if (dontLoad) {
            dontLoad = false;
        } else {
            loadFragment(m_selectedFragment, tag);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new MessageEvent("Change Price"));

        updateCartSize();
    }

    public void updateCartSize() {
        long count = StorageHelper.getCartSize();
        tvCount.setText(count > 0 ? String.valueOf(count) : null);
        if (count == 0)
            tvCount.setVisibility(View.GONE);
        else
            tvCount.setVisibility(View.VISIBLE);
    }

    //
    TextView tvCount;
    //

    @Override
    public void changeCountCallback(int count) {
        updateCartSize();
    }



}
