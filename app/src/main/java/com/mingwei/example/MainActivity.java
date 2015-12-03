package com.mingwei.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.ming.indicator.Indicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private FragmentPagerAdapter mAdapter;

    private SimpleAdapter adapter;

    private List<String> mStrings = Arrays.asList("标签1", "标签2", "标签3", "标签4", "标签5");

    private Indicator mIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    private void initView() {

        for (String tabs : mStrings) {
            SimpleFragment fragment = SimpleFragment.newInstance(tabs);
            mFragments.add(fragment);
        }
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (Indicator) findViewById(R.id.indicator);
        mIndicator.setTabs(mStrings);
        mIndicator.setViewPager(mViewPager);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
        };
        mViewPager.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
