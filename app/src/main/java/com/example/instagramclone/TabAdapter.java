package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabAdapter extends FragmentStateAdapter {

    private final String fragmentsTitle;


    public TabAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, String fragmentsTitle) {
        super(fragmentManager, lifecycle);

        this.fragmentsTitle = fragmentsTitle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int tabPosition) {

        switch (tabPosition) {

            case 0:
                return new ProfileTab();

            case 1:
                return new UsersTab();

            case 2:
                return new SharePicturesTab();

            default:
                return null;


        }
    }

    @Override
    public int getItemCount() {

        return 3;
    }

    public CharSequence getFragmentsTitle(int position) {

        switch (position) {

            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Share Picture";
            default:
                return null;
        }


    }

    //
//    @Override
//    public long getItemId(int position) {
//
//        switch (position) {
//
//            case 0:
//                return Integer.parseInt("Profile");
//
//            case 1:
//                return Integer.parseInt("Users");
//
//            case 2:
//                return Integer.parseInt("Share Picture");
//
//            default:
//                return 0;
//
//        }
//    }

    



}
