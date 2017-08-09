package com.idreamsky.appstore.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.idreamsky.appstore.ui.fragment.CategoryAppFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idreamsky on 2017/8/9.
 */

public class CategoryFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> titles = new ArrayList<>(3);
    private List<Fragment> fragments = new ArrayList<>(3);


    public CategoryFragmentAdapter(FragmentManager manager, int categoryId) {
        super(manager);


        titles.add("精品");
        titles.add("排名");
        titles.add("新品");

        for (int i = 1; i <= 3; i++) {
            CategoryAppFragment fragment = new CategoryAppFragment();
            fragment.setType(i);
            fragment.setCategoryId(categoryId);
            fragments.add(fragment);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
