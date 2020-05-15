package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager categoryViewPager = findViewById(R.id.miwok_viewpager);

        /* Create an adapter that knows which fragment should be shown on each page */
        final MiwokCategoryPagerAdapter categoryPagerAdapter =
                new MiwokCategoryPagerAdapter(this, getSupportFragmentManager(),
                        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        /* Set the adapter onto the view pager */
        categoryViewPager.setAdapter(categoryPagerAdapter);

        final TabLayout miwokTabLayout = findViewById(R.id.miwok_tabs);
        miwokTabLayout.setupWithViewPager(categoryViewPager);
    }
}
