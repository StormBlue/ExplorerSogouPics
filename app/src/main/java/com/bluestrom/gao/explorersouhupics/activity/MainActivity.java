package com.bluestrom.gao.explorersouhupics.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bluestrom.gao.explorersouhupics.R;
import com.bluestrom.gao.explorersouhupics.adapter.MainContentViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager contentView;

    private MainContentViewPagerAdapter viewPagerAdapter;

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        viewPagerAdapter = new MainContentViewPagerAdapter(getSupportFragmentManager(),);
        contentView.setAdapter(viewPagerAdapter);
    }

    private void initView() {
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);
//        navigationView.setItemIconTintList((ColorStateList)getResources().getColorStateList(R.color.cardview_dark_background));
        contentView = (ViewPager) findViewById(R.id.main_content_viewpager);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_data:
                break;
            case R.id.bottom_nav_media:
                break;
            case R.id.bottom_nav_net:
                break;
            default:
                break;
        }
        return true;
    }
}
