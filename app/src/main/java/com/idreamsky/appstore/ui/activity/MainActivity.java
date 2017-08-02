package com.idreamsky.appstore.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.ui.adapter.FragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity{

    @BindView(R.id.navigationView)
    NavigationView mNavigationView;
    @BindView(R.id.drawLayout)
    DrawerLayout mDrawLayout;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        initDrawLayout();
        initTabLayout();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActivityComponent(AppComponent appComponent) {

    }



    private void initDrawLayout() {
        View headerView = mNavigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "headerView click", Toast.LENGTH_SHORT).show();
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuUpdate:
                        Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuMessage:
                        Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuSetting:
                        Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        mToolBar.inflateMenu(R.menu.menu_toolbar);
        //toolBar与DrawLayout的结合
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawLayout, mToolBar, R.string.open,
                R.string.close);
        toggle.syncState();
        mDrawLayout.addDrawerListener(toggle);
    }

    private void initTabLayout() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary,null));
    }
}
