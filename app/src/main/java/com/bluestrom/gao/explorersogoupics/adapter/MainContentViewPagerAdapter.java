package com.bluestrom.gao.explorersogoupics.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

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
        return listView.get(position);
    }

    @Override
    public int getCount() {
        return listView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
