package com.bluestrom.gao.explorersouhupics.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Gao on 09/01/2017.
 */

public class MainContentViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> listView;

    public MainContentViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        listView = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return listView.size();
    }
}
