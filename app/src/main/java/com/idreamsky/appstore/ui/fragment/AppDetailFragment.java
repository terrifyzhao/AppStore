package com.idreamsky.appstore.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.common.Constant;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerAppDetailComponent;
import com.idreamsky.appstore.di.module.AppDetailModule;
import com.idreamsky.appstore.presenter.AppDetailPresenter;
import com.idreamsky.appstore.presenter.contract.AppInfoContract;
import com.idreamsky.appstore.ui.adapter.SameAppAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


public class AppDetailFragment extends ProgressFragment<AppDetailPresenter> implements AppInfoContract.DetailView {

    @BindView(R.id.view_gallery)
    LinearLayout viewGallery;

    @BindView(R.id.view_introduction)
    ExpandableTextView viewIntroduction;

    @BindView(R.id.txt_update_time)
    TextView txtUpdateTime;

    @BindView(R.id.txt_version)
    TextView txtVersion;

    @BindView(R.id.txt_apk_size)
    TextView txtApkSize;

    @BindView(R.id.txt_publisher)
    TextView txtPublisher;

    @BindView(R.id.recycler_view_same_dev)
    RecyclerView recyclerViewSameDev;

    @BindView(R.id.recycler_view_relate)
    RecyclerView recyclerViewRelate;

    @BindView(R.id.sameLayout)
    LinearLayout sameLayout;

    @BindView(R.id.relateLayout)
    LinearLayout relateLayout;



    @Override
    protected int setLayoutId() {
        return R.layout.fragment_app_detail;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppDetailComponent.builder()
                .appComponent(appComponent)
                .appDetailModule(new AppDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        AppInfo data = (AppInfo) getArguments().get("appInfo");
        mPresenter.RequestAppDetail(data.getId());
    }

    @Override
    protected void errorLayoutClick() {

    }

    @Override
    public void showResult(AppInfo data) {
        Log.i("App", "showResult-------------- " + data);

        //加载图片
        showGallery(data.getScreenshot());

        viewIntroduction.setText(data.getIntroduction());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(data.getUpdateTime()));
        txtUpdateTime.setText(date);



        txtVersion.setText(data.getVersionName());
        txtApkSize.setText(data.getApkSize() / 1024 / 1024 + "M");
        txtPublisher.setText(data.getPublisherName());

        //相同开发者应用
        sameDev(data);

        relate(data);


    }

    private void relate(AppInfo data) {
        if (data.getRelateAppInfoList().size() == 0) {
            relateLayout.setVisibility(View.GONE);
            return;
        }
        SameAppAdapter adapter = new SameAppAdapter(getActivity());
        adapter.setData(data.getRelateAppInfoList());
        recyclerViewRelate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        recyclerViewRelate.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void sameDev(AppInfo data) {
        if (data.getSameDevAppInfoList().size() == 0) {
            sameLayout.setVisibility(View.GONE);
        }
        SameAppAdapter adapter = new SameAppAdapter(getActivity());
        adapter.setData(data.getSameDevAppInfoList());
        recyclerViewSameDev.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        recyclerViewSameDev.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void showGallery(String urls) {

        List<String> list = Arrays.asList(urls.split(","));
        for (String url : list) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ImageView imageView = (ImageView) inflater.inflate(R.layout.template_imageview, viewGallery, false);
            Glide.with(getActivity()).load(Constant.ICON_BASE_URL + url).into(imageView);
            viewGallery.addView(imageView);
        }

    }

}
