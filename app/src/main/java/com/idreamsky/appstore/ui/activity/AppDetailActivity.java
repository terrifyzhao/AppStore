package com.idreamsky.appstore.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.common.Constant;
import com.idreamsky.appstore.common.util.DensityUtil;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.ui.fragment.AppDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by idreamsky on 2017/8/10.
 */

public class AppDetailActivity extends BaseActivity {


    @BindView(R.id.contentLayout)
    FrameLayout contentLayout;

    @BindView(R.id.ivHead)
    ImageView ivHead;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.ivBack)
//    ImageView ivBack;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_app_detail;
    }

    @Override
    protected void setActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initAppBar();

        loadHeadAndFragment();

//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

//        viewAnimation();
    }

    private void initAppBar() {

        final ColorStateList textColors = tvName.getTextColors();

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                float rate = (float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange();


                if (rate <= 0.4) {
                    tvName.setTextColor(textColors);
                    toolbar.setNavigationIcon(R.drawable.back_button_black);
                } else {
                    tvName.setTextColor(Color.argb(255, (int) (255 * rate), (int) (255 * rate), (int) (255 * rate)));
                    toolbar.setNavigationIcon(R.drawable.back_button);
                }


                Log.i("app", "onOffsetChanged: " + rate + "----" + appBarLayout.getTotalScrollRange() + "--" + verticalOffset);

            }
        });
    }

    private void loadHeadAndFragment() {

        AppInfo data = (AppInfo) getIntent().getExtras().get("appInfo");
        Glide.with(this).load(Constant.ICON_BASE_URL + data.getIcon()).into(ivHead);
        tvName.setText(data.getDisplayName());

        AppDetailFragment fragment = new AppDetailFragment();
        fragment.setArguments(getIntent().getExtras());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contentLayout, fragment);
        transaction.commit();
    }

    private void viewAnimation() {
        //获取点击的view视图
        View view = appApplication.getmView();
        //把view转换成Bitmap
        Bitmap bitmap = getViewImageCache(view);

        if (bitmap != null) {
            contentLayout.setBackground(new BitmapDrawable(null, bitmap));
        }
        int[] position = new int[2];
        view.getLocationOnScreen(position);

        int left = position[0];
        int top = position[1];

        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.topMargin = top - DensityUtil.getStatusBarH(this);
        margin.leftMargin = left;
        margin.height = view.getHeight();
        margin.width = view.getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(margin);
        contentLayout.setLayoutParams(params);

        open();
    }

    //打开详情页面的动画
    private void open() {

        int h = DensityUtil.getScreenH(this);

        ObjectAnimator animator = ObjectAnimator.ofFloat(contentLayout, "scaleY", 1f, (float) h);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationStart(Animator animation) {
//                contentLayout.setBackgroundColor(getResources().getColor(R.color.colorWhit,null));
                loadHeadAndFragment();
            }
        });
        animator.setStartDelay(500);
        animator.setDuration(10000);
        animator.start();
    }


    //获取View的bitmap
    private Bitmap getViewImageCache(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap == null) {
            return null;
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
        view.destroyDrawingCache();
        return bitmap;
    }

}
