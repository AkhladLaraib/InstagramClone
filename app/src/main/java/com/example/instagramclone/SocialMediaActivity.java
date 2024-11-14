package com.example.instagramclone;

import static androidx.viewpager2.widget.ViewPager2.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayoutMediator;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Social Media App");

        try {

            Toolbar toolbar = findViewById(R.id.myToolbar);
            setSupportActionBar(toolbar);

            ViewPager2 viewPager = findViewById(R.id.viewPager);
            TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle());
            viewPager.setAdapter(tabAdapter);

            TabLayout tabLayout = findViewById(R.id.tabLayout);
            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> {
                        switch (position) {
                            case 0:
                                tab.setText("PROFILE");
                                break;
                            case 1:
                                tab.setText("USERS");
                                break;
                            case 2:
                                tab.setText("SHARE PICTURES");
                                break;
                        }
                    }).attach();
        } catch (NullPointerException e) {

            e.getMessage();
        }


//
//        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager.registerOnPageChangeCallback(new OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });


    }

}