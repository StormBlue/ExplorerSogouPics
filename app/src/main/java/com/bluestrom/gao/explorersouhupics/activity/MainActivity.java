package com.bluestrom.gao.explorersouhupics.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bluestrom.gao.explorersouhupics.R;
import com.bluestrom.gao.explorersouhupics.adapter.MainContentViewPagerAdapter;
import com.bluestrom.gao.explorersouhupics.fragment.FunnyFragment;
import com.bluestrom.gao.explorersouhupics.fragment.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, FunnyFragment.OnListFragmentInteractionListener {

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
        fragmentList.add(FunnyFragment.newInstance(1));
        fragmentList.add(FunnyFragment.newInstance(1));
        fragmentList.add(FunnyFragment.newInstance(1));
        initView();
        viewPagerAdapter = new MainContentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        contentViewPager.setAdapter(viewPagerAdapter);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_beauty:
                        contentViewPager.setCurrentItem(0,false);
                        break;
                    case R.id.bottom_nav_funny:
                        contentViewPager.setCurrentItem(1,false);
                        break;
                    case R.id.bottom_nav_art:
                        contentViewPager.setCurrentItem(2,false);
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
}
