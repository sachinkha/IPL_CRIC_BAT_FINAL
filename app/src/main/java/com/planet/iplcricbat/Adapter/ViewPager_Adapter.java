package com.planet.iplcricbat.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.planet.iplcricbat.UI.Batting;
import com.planet.iplcricbat.UI.Bowling;

public class ViewPager_Adapter extends FragmentPagerAdapter {



    public ViewPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       // return fragmentArrayList.get(position);
        switch (position) {
            case 0:

                Batting pagerFragment = new Batting();
                return pagerFragment;

            case 1:

                Bowling rechargeFragment = new Bowling();
                return rechargeFragment;

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
        //return fragmentArrayList.size();
    }
  /*  public void addFragment(Fragment fragment,String title){

        fragmentArrayList.add(fragment);
        fragmentTitle.add(title);
    }*/

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Batting";
            case 1:
                return "Bowling";


        }
        return null;
    }
}
