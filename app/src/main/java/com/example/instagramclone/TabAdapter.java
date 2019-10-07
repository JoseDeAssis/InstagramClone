package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfileTab();

            case 1:
                return new UsersTab();

            case 2:
                return new SharePictureTab();

            default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}