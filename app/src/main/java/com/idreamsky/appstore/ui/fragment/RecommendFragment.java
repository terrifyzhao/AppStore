package com.idreamsky.appstore.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.idreamsky.appstore.AppApplication;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerRecommendComponent;
import com.idreamsky.appstore.di.module.RecommendModule;
import com.idreamsky.appstore.presenter.RecommendPresenter;
import com.idreamsky.appstore.presenter.contract.RecommendContract;
import com.idreamsky.appstore.ui.adapter.RecommendAdapter;
import com.idreamsky.appstore.ui.decoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class RecommendFragment extends ProgressFragment<RecommendPresenter> implements RecommendContract.View {

    private static final String TAG = "App";

    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerRecommendComponent.builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        mPresenter.requestData();
    }

    @Override
    protected void errorLayoutClick() {
        mPresenter.requestData();
    }

    @Override
    public void showResult(List<AppInfo> data) {
        initRecycle(data);
    }

    private void initRecycle(List<AppInfo> datas) {
        RecommendAdapter mAdapter = new RecommendAdapter(getContext(), datas);
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(mAdapter);
    }
}
