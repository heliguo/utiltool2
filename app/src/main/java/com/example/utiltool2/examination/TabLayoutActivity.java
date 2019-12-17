package com.example.utiltool2.examination;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.utiltool2.R;
import com.example.utiltool2.exam.ExamViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


/**
 * @创建者 lgh
 * @创建时间 2019-08-15 09:58
 * @描述
 */
public class TabLayoutActivity extends AppCompatActivity {

    private CoordinatorTabLayout mCoordinatorTabLayout;
    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"Android", "iOS", "Web", "Other", "Android", "iOS", "Web", "Other"};
    private ExamViewPager mViewPager;
    private boolean isScroll = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        initFragments();
        initViewPager();

        mImageArray = new int[]{
                R.drawable.bg_android,
                R.drawable.bg_ios,
                R.drawable.bg_js,
                R.drawable.bg_other,
                R.drawable.bg_android,
                R.drawable.bg_ios,
                R.drawable.bg_js,
                R.drawable.bg_other};
        mColorArray = new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light};
        mCoordinatorTabLayout = findViewById(R.id.coordinatortablyout);
        mCoordinatorTabLayout.setCenterTitle("标题")
                .setTabMode(TabLayout.MODE_SCROLLABLE)
                .setBackEnable(true)//返回键是否可用
                .getCollapsedState()//监听折叠状态
                .setImageColorArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);


    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : mTitles) {
            mFragments.add(TabFragment.getInstance(title));
        }
    }

    private void initViewPager() {
        mViewPager = findViewById(R.id.ll_vv_content);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        mViewPager.setCurrentItem(3, false);//设置默认显示页
    }


    public void scroll() {
        isScroll = !isScroll;
        Log.e(TabLayoutActivity.class.getSimpleName(), "scroll: " + isScroll);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        switch (item.getItemId()) {
            case R.id.action_about:
                scroll();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
