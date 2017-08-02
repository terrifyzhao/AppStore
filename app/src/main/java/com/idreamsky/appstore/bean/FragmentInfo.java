package com.idreamsky.appstore.bean;

import android.support.v4.app.Fragment;

/**
 * Created by zhaojiuzhou on 2017/7/21.
 */

public class FragmentInfo {

    private Fragment fragment;
    private String name;

    public FragmentInfo(Fragment fragment, String name) {
        this.fragment = fragment;
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
