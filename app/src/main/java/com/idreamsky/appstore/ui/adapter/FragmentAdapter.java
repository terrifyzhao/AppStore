package com.idreamsky.appstore.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.FragmentInfo;
import com.idreamsky.appstore.ui.fragment.CategoryFragment;
import com.idreamsky.appstore.ui.fragment.GameFragment;
import com.idreamsky.appstore.ui.fragment.RankFragment;
import com.idreamsky.appstore.ui.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaojiuzhou on 2017/7/21.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);

        initFragment();
    }

    private void initFragment() {
        mFragments.add(new FragmentInfo(new RecommendFragment(),"推荐"));
        mFragments.add(new FragmentInfo(new RankFragment(),"排名"));
        mFragments.add(new FragmentInfo(new GameFragment(),"游戏"));
        mFragments.add(new FragmentInfo(new CategoryFragment(),"分类"));

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getName();
    }
}
