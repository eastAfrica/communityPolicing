package com.example.nyismaw.communitypolicing.screens.Components;

import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.example.nyismaw.communitypolicing.AppInfo.CurrentUserPreferences;
import com.example.nyismaw.communitypolicing.R;

/**
 * Created by nyismaw on 12/12/2017.
 */

public class NavigationMenus {
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    ViewPager pager;

    public NavigationMenus(NavigationView navigationView, DrawerLayout drawerLayout, ViewPager viewPager) {
        this.mDrawerLayout = drawerLayout;
        this.pager = viewPager;
        this.navigationView = navigationView;
        setMenuListeners();
    }

    private void setMenuListeners() {

        final Switch accidentsSwitch = navigationView.getMenu().findItem(R.id.accidents).getActionView().findViewById(R.id.switch_item);
        accidentsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("msg", "filtering accidents only");
                pager.setCurrentItem(1);
                mDrawerLayout.closeDrawers();
                CurrentUserPreferences.setShowACCIDENTS(accidentsSwitch.isChecked());
            }
        });

        final Switch potholesSwitch = navigationView.getMenu().findItem(R.id.potholes).getActionView().findViewById(R.id.switch_item);
        potholesSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("msg", "filtering pot holes only");
                pager.setCurrentItem(1);
                mDrawerLayout.closeDrawers();
                CurrentUserPreferences.setShowPOTHOLES(potholesSwitch.isChecked());
            }
        });

        final Switch blockRoadSwitch = navigationView.getMenu().findItem(R.id.blocked_roads).getActionView().findViewById(R.id.switch_item);
        blockRoadSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("msg", "filtering blocked roads only");
                pager.setCurrentItem(1);
                mDrawerLayout.closeDrawers();
                CurrentUserPreferences.setShowBlockedRoads(blockRoadSwitch.isChecked());
            }
        });

        final Switch fallenTreesSwitch = navigationView.getMenu().findItem(R.id.fallen_trees).getActionView().findViewById(R.id.switch_item);
        fallenTreesSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("msg", "filtering fallen trees only");
                pager.setCurrentItem(1);
                mDrawerLayout.closeDrawers();
                CurrentUserPreferences.setShowFALLEN_TREES(fallenTreesSwitch.isChecked());
            }
        });

        final Switch othersSwitch = navigationView.getMenu().findItem(R.id.others).getActionView().findViewById(R.id.switch_item);
        othersSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("msg", "filtering others only");
                pager.setCurrentItem(1);
                mDrawerLayout.closeDrawers();
                CurrentUserPreferences.setShowOTHER(othersSwitch.isChecked());
            }
        });


    }
}
