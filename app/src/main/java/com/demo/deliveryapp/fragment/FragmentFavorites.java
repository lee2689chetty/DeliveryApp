package com.demo.deliveryapp.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.demo.deliveryapp.Helper.Tools;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.demo.deliveryapp.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorites extends Fragment {
    Context m_context;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static FragmentFavorites newInstance() {
        return new FragmentFavorites();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_context = getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_favorties, container, false);
        viewPager = (ViewPager) fragmentLayout.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) fragmentLayout.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Tools.setupUI(fragmentLayout.findViewById(R.id.parent_container), getActivity());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return fragmentLayout;
    }
    private void setupViewPager(ViewPager viewPager) {
        AppCompatActivity activity = (AppCompatActivity) m_context;
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        adapter.addFragment(new FragmentFavoritesPizza(), "Pizza");
        adapter.addFragment(new FragmentFavoritesPizza(), "Combo");
        adapter.addFragment(new FragmentFavoritesPizza(), "Sides");
        adapter.addFragment(new FragmentFavoritesPizza(), "Veberage");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
