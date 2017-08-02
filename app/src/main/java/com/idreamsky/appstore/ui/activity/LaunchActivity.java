package com.idreamsky.appstore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.idreamsky.appstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity {

    @BindView(R.id.pathView)
    PathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        mPathView.getPathAnimator()
                .delay(100)
                .duration(2000)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        jump();

                    }
                })
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
        mPathView.setFillAfter(true);

    }

    private void jump() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("AppStore", Context.MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirst",true);
        if (isFirst){
            startActivity(new Intent(LaunchActivity.this,GuideActivity.class));
        }else{
            startActivity(new Intent(LaunchActivity.this,MainActivity.class));
        }
        finish();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirst",false);
        editor.apply();
    }

}
