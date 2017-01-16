package com.bluestrom.gao.explorersouhupics.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bluestrom.gao.explorersouhupics.R;
import com.bluestrom.gao.explorersouhupics.adapter.MainContentViewPagerAdapter;
import com.bluestrom.gao.explorersouhupics.fragment.FunnyFragment;
import com.bluestrom.gao.explorersouhupics.fragment.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;
import static android.support.v4.widget.ViewDragHelper.STATE_SETTLING;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FunnyFragment.OnListFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ViewPager contentViewPager;

    private MainContentViewPagerAdapter viewPagerAdapter;

    private BottomNavigationView navigationView;

    private List<Fragment> fragmentList;

    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        fragmentList = new ArrayList<>();
        fragmentList.add(FunnyFragment.newInstance(1));
        fragmentList.add(FunnyFragment.newInstance(1));
        fragmentList.add(FunnyFragment.newInstance(1));
        initView();
        viewPagerAdapter = new MainContentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        // 缓存两个页面
        contentViewPager.setOffscreenPageLimit(2);
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
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initView() {
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);
        contentViewPager = (ViewPager) findViewById(R.id.main_content_viewpager);
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_nav_view));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_beauty:
                break;
            case R.id.bottom_nav_art:
                break;
            case R.id.bottom_nav_funny:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.PhotoBean item) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_button:
                Log.i(TAG, "操作开始SheetBehavior.State" + mBottomSheetBehavior.getState());
                if (mBottomSheetBehavior.getState() == STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(STATE_COLLAPSED);
                } else if (mBottomSheetBehavior.getState() == STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(STATE_EXPANDED);
                }
                Log.i(TAG, "操作结束SheetBehavior.State" + mBottomSheetBehavior.getState());
                break;
            default:
                break;
        }
    }
}
