package com.jungle.insta;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int tabPosition) {
        switch (tabPosition){
            case 0:
                Profile_tab profile_tab = new Profile_tab();
                return profile_tab;
            case 1:
                return new User_tab();

            case 2:
                return new Share_picture_tab();
                default:
                    return new Profile_tab();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Share";
                default:
                    return null;

        }
    }
}
