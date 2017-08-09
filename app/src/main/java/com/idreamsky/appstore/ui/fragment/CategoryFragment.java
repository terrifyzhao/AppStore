package com.idreamsky.appstore.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.CategoryBean;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerCategoryComponent;
import com.idreamsky.appstore.di.module.CategoryModule;
import com.idreamsky.appstore.presenter.CategoryPresenter;
import com.idreamsky.appstore.presenter.contract.CategoryContract;
import com.idreamsky.appstore.ui.adapter.CategoryAdapter;
import com.idreamsky.appstore.ui.decoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;

public class CategoryFragment extends ProgressFragment<CategoryPresenter> implements CategoryContract.CategoryView {

    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;

    private CategoryAdapter adapter;

    @Override
    public void showResult(List<CategoryBean> data) {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.template_recyleview;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerCategoryComponent.builder()
                .appComponent(appComponent)
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        initRecycleView();
        mPresenter.requestData();
    }

    private void initRecycleView() {
        adapter = new CategoryAdapter(getActivity());
        mRecycleView.setAdapter(adapter);
        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setAutoMeasureEnabled(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(adapter);
    }

    @Override
    protected void errorLayoutClick() {
        mPresenter.requestData();
    }

}
