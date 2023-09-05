//package com.demo.deliveryapp.activity;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import com.demo.deliveryapp.R;
//import com.demo.deliveryapp.fragment.FragmentContactus;
//import com.demo.deliveryapp.fragment.FragmentExploreMenu;
//import com.demo.deliveryapp.fragment.FragmentMapLocation;
//import com.demo.deliveryapp.fragment.FragmentMyOrders;
//import com.demo.deliveryapp.fragment.FragmentMyProfile;
//import com.demo.deliveryapp.fragment.FragmentOffersDiscounts;
//import com.demo.deliveryapp.fragment.FragmentTrackOrder;
//import com.google.android.material.navigation.NavigationView;
//
//public class MapsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
//
//    private NavigationView navigationView;
//    Fragment m_selectedFragment = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
////        Toolbar toolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
//
////        DrawerLayout drawer = findViewById(R.id.drawer_layout);
////        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
////                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        drawer.addDrawerListener(toggle);
////        toggle.syncState();
////
////        navigationView = findViewById(R.id.nav_view);
////        navigationView.setNavigationItemSelectedListener(this);
////        Menu nav_Menu = navigationView.getMenu();
////
////        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        transaction.replace(R.id.frame_layout, FragmentContactus.newInstance());
////        transaction.commit();
////        getSupportActionBar().setTitle("Contact us");
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//
////    @Override
////    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////        int id = item.getItemId();
////        if (id == R.id.nav_address){
////            if (!getSupportActionBar().getTitle().toString().equals("Contact us")){
////                Intent myIntent = new Intent(this, MapsActivity.class);
////                startActivity(myIntent);
////                this.finish();
////            }
////        } else if (id == R.id.nav_phone) {
////            String phone = "+18000001234";
////            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
////            startActivity(intent);
////        } else if (id == R.id.nav_openingweek1) {
////            m_selectedFragment = FragmentMapLocation.newInstance();
////            getSupportActionBar().setTitle("132 Cherry Street, NN3, 2HF");
////        } else if (id == R.id.nav_openingweek2) {
////            m_selectedFragment = FragmentMapLocation.newInstance();
////            getSupportActionBar().setTitle("132 Cherry Street, NN3, 2HF");
////        } else if (id == R.id.nav_trackorder) {
////            m_selectedFragment = FragmentTrackOrder.newInstance();
////            getSupportActionBar().setTitle(item.getTitle());
////        } else if (id == R.id.nav_exploremenu) {
////            m_selectedFragment = FragmentExploreMenu.newInstance();
////            getSupportActionBar().setTitle(item.getTitle());
////        } else if (id == R.id.nav_offers) {
////            m_selectedFragment = FragmentOffersDiscounts.newInstance();
////            getSupportActionBar().setTitle(item.getTitle());
////        } else if (id == R.id.nav_myorders) {
////            m_selectedFragment = FragmentMyOrders.newInstance();
////            getSupportActionBar().setTitle(item.getTitle());
////        } else if (id == R.id.nav_myprofile) {
////            m_selectedFragment = FragmentMyProfile.newInstance();
////            getSupportActionBar().setTitle(item.getTitle());
////        } else if (id == R.id.nav_contactus) {
////            if (!getSupportActionBar().getTitle().toString().equals(item.getTitle())){
////                Intent myIntent = new Intent(this, MapsActivity.class);
////                startActivity(myIntent);
////                this.finish();
////            }
////        } else if (id == R.id.nav_logout) {
////            Intent myIntent = new Intent(this, LoginActivity.class);
////            startActivity(myIntent);
////            this.finish();
////        }
////        if (m_selectedFragment != null) {
////            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////            transaction.replace(R.id.frame_layout, m_selectedFragment).addToBackStack(null);
////            transaction.commit();
////        }
//////
//////        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//////        drawer.closeDrawer(GravityCompat.START);
////
////        return true;
////    }
//}
