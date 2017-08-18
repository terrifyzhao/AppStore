package com.idreamsky.appstore.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.IndexBean;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerRecommendComponent;
import com.idreamsky.appstore.di.module.RecommendModule;
import com.idreamsky.appstore.presenter.RecommendPresenter;
import com.idreamsky.appstore.presenter.contract.RecommendContract;
import com.idreamsky.appstore.ui.adapter.IndexMultiAdapter;

import butterknife.BindView;

public class RecommendFragment extends ProgressFragment<RecommendPresenter> implements RecommendContract.View {

    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;

    @Override
    protected int setLayoutId() {
        return R.layout.template_recyleview;
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
    public void showResult(IndexBean data) {
        initRecycle(data);
    }

    private void initRecycle(IndexBean data) {
        IndexMultiAdapter adapter = new IndexMultiAdapter(getContext(),mAppApplication);
        adapter.setData(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setAutoMeasureEnabled(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(adapter);

    }
}
