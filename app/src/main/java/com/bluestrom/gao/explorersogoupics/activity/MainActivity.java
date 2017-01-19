package com.bluestrom.gao.explorersogoupics.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.bluestrom.gao.explorersogoupics.R;
import com.bluestrom.gao.explorersogoupics.adapter.MainContentViewPagerAdapter;
import com.bluestrom.gao.explorersogoupics.fragment.FunnyFragment;
import com.bluestrom.gao.explorersogoupics.pojo.SogouPicPojo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FunnyFragment.OnListFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ViewPager contentViewPager;

    private MainContentViewPagerAdapter viewPagerAdapter;

    private BottomNavigationView navigationView;

    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new FunnyFragment());
        fragmentList.add(new FunnyFragment());
        fragmentList.add(new FunnyFragment());
        fragmentList.add(new FunnyFragment());
        initView();
        viewPagerAdapter = new MainContentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        // 缓存两个页面
        contentViewPager.setOffscreenPageLimit(3);
        contentViewPager.setAdapter(viewPagerAdapter);
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private void initView() {
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);
        contentViewPager = (ViewPager) findViewById(R.id.main_content_viewpager);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_beauty:
                contentViewPager.setCurrentItem(0, false);
                break;
            case R.id.bottom_nav_funny:
                contentViewPager.setCurrentItem(1, false);
                break;
            case R.id.bottom_nav_art:
                contentViewPager.setCurrentItem(2, false);
                break;
            case R.id.bottom_nav_offline:
                contentViewPager.setCurrentItem(3, false);
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onListFragmentInteraction(SogouPicPojo item) {

    }
}