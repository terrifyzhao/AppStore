package com.idreamsky.appstore.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerAppInfoComponent;
import com.idreamsky.appstore.di.module.AppInfoModule;
import com.idreamsky.appstore.presenter.AppInfoPresenter;
import com.idreamsky.appstore.presenter.contract.AppInfoContract;
import com.idreamsky.appstore.ui.activity.AppDetailActivity;
import com.idreamsky.appstore.ui.adapter.AppInfoAdapter;
import com.idreamsky.appstore.ui.decoration.DividerItemDecoration;

import butterknife.BindView;

import static com.idreamsky.appstore.data.AppInfoModel.RANKTYPE;

public class RankFragment extends ProgressFragment<AppInfoPresenter> implements AppInfoContract.View {

    public int page = 0;

    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    private AppInfoAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.template_recyleview;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppInfoComponent.builder()
                .appComponent(appComponent)
                .appInfoModule(new AppInfoModule(this))
                .build()
                .injectRank(this);
    }

    @Override
    protected void init() {
        initRecycleView();
        mPresenter.RequestData(RANKTYPE, page);
    }

    private void initRecycleView() {
        adapter = new AppInfoAdapter.Builder(getActivity(),mAppApplication)
                .showNum(true)
                .showBrief(false)
                .showCategory(true)
                .build();
        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setAutoMeasureEnabled(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(adapter);



        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    mPresenter.RequestData(RANKTYPE, ++page);
                }
            }
        });

    }

    @Override
    protected void errorLayoutClick() {
        page = 0;
        mPresenter.RequestData(RANKTYPE, page);
    }

    @Override
    public void showResult(final PageBean<AppInfo> data) {
        adapter.setData(data.getDatas());

        adapter.notifyDataSetChanged();
    }

}
