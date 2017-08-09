package com.idreamsky.appstore.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.ui.adapter.CategoryFragmentAdapter;

import butterknife.BindView;

public class CategoryAppActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_category_app;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        int id = getIntent().getIntExtra("id",0);
        initView();
        initViewPager(id);
    }

    private void initViewPager(int id) {
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(getSupportFragmentManager(),id);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary,null));
    }

    private void initView() {

    }

}
