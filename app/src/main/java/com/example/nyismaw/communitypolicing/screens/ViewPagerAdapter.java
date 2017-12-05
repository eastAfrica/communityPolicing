package com.example.nyismaw.communitypolicing.screens;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.nyismaw.communitypolicing.controller.maps.MapFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) // if the position is 0 we are returning the First tab
        {
            ReportingTab reportingTab = new ReportingTab();
            return reportingTab;
        } else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            MapFragment mapFragment = new MapFragment();
            return mapFragment;
        } else         // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            EmergencyContactFragment emergencyContactFragment = new EmergencyContactFragment();
            return emergencyContactFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

}

