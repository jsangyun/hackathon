package com.example.jsy.hackathon;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.vp);
//        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setAdapter(new com.example.ryan.view_pager_test.PagerAdapter(getSupportFragmentManager()));


    }

}
