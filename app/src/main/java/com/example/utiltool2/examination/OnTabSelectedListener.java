package com.example.utiltool2.examination;

import com.google.android.material.tabs.TabLayout;

/**
 * @author hugeterry(http : / / hugeterry.cn)
 */

public interface OnTabSelectedListener {

    void onTabSelected(TabLayout.Tab tab);

    void onTabUnselected(TabLayout.Tab tab);

    void onTabReselected(TabLayout.Tab tab);
}
