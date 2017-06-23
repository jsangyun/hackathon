package com.example.jsy.hackathon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by RYAN on 2017. 6. 24..
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        return PageFragment.create(getCount());
        switch (position){
            case 0:
                return new weatherfrag();
            case 1:
                //return new selectfrag();
            case 2:
                return new third_flag();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
