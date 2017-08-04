package com.idreamsky.appstore.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.bean.PageBean;
import com.idreamsky.appstore.di.component.AppComponent;
import com.idreamsky.appstore.di.component.DaggerRankComponent;
import com.idreamsky.appstore.di.module.RankModule;
import com.idreamsky.appstore.presenter.RankPresenter;
import com.idreamsky.appstore.presenter.contract.RankContract;
import com.idreamsky.appstore.ui.adapter.AppAdapter;
import com.idreamsky.appstore.ui.decoration.DividerItemDecoration;

import butterknife.BindView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class RankFragment extends ProgressFragment<RankPresenter> implements RankContract.View {

    public int page = 0;

    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    private AppAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.template_recyleview;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerRankComponent.builder()
                .appComponent(appComponent)
                .rankModule(new RankModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        initRecycleView();
        mPresenter.RequestData(page);
    }

    private void initRecycleView() {
        adapter = new AppAdapter.Builder(getActivity())
                .showNum(true)
                .showBrief(false)
                .showCategory(true)
                .build();
        mRecycleView.setAdapter(adapter);
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
                    mPresenter.RequestData(++page);
                }
            }
        });

    }

    @Override
    protected void errorLayoutClick() {

    }

    @Override
    public void showResult(PageBean<AppInfo> data) {
        adapter.setData(data.getDatas());
        Log.i("appStore", "showResult");
        adapter.notifyDataSetChanged();
    }

}
