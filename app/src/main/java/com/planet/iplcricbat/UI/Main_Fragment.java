package com.planet.iplcricbat.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.planet.iplcricbat.Adapter.ViewPager_Adapter;
import com.planet.iplcricbat.R;


public class Main_Fragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPager_Adapter pagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_, container, false);
        tabLayout = view.findViewById(R.id.master_tabLayout);
        viewPager = view.findViewById(R.id.master_viewPager);
        pagerAdapter = new ViewPager_Adapter(getFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
      return view;
    }
}