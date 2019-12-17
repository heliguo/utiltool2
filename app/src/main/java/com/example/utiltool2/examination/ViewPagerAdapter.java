package com.example.utiltool2.examination;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @创建者 lgh
 * @创建时间 2019-08-16 16:16
 * @描述
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    //FragmentPagerAdapter 与 FragmentStatePagerAdapter

    private ArrayList<Fragment> mFragments;
    private String[]            mTitles;

    ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
