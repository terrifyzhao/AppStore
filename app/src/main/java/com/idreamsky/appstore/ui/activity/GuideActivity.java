package com.idreamsky.appstore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.ui.adapter.GuideFragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.firstDot)
    ImageView firstDot;
    @BindView(R.id.SecondDot)
    ImageView SecondDot;
    @BindView(R.id.thirdDot)
    ImageView thirdDot;
    @BindView(R.id.startBtn)
    Button startBtn;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        viewPager.setAdapter(new GuideFragmentAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dotChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void dotChange(int position) {
        switch (position) {
            case 0:
                firstDot.setImageDrawable(getDrawable(R.drawable.circle_white));
                SecondDot.setImageDrawable(getDrawable(R.drawable.circle_gray));
                thirdDot.setImageDrawable(getDrawable(R.drawable.circle_gray));
                startBtn.setVisibility(View.GONE);
                break;
            case 1:
                firstDot.setImageDrawable(getDrawable(R.drawable.circle_gray));
                SecondDot.setImageDrawable(getDrawable(R.drawable.circle_white));
                thirdDot.setImageDrawable(getDrawable(R.drawable.circle_gray));
                startBtn.setVisibility(View.GONE);
                break;
            case 2:
                firstDot.setImageDrawable(getDrawable(R.drawable.circle_gray));
                SecondDot.setImageDrawable(getDrawable(R.drawable.circle_gray));
                thirdDot.setImageDrawable(getDrawable(R.drawable.circle_white));
                startBtn.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.startBtn)
    public void onViewClicked() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
