package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabAdapter extends FragmentStateAdapter {


    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
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
}
