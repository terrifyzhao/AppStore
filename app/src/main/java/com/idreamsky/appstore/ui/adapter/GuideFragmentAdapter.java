package com.idreamsky.appstore.ui.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.idreamsky.appstore.ui.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojiuzhou on 2017/7/31.
 */

public class GuideFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public GuideFragmentAdapter(FragmentManager fm) {
        super(fm);
        GuideFragment fragment = GuideFragment.newInstance(Color.RED);
        fragments.add(fragment);
        GuideFragment fragment1 = GuideFragment.newInstance(Color.YELLOW);
        fragments.add(fragment1);
        GuideFragment fragment2 = GuideFragment.newInstance(Color.GREEN);
        fragments.add(fragment2);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
