package com.idreamsky.appstore.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idreamsky.appstore.R;

import butterknife.ButterKnife;

/**
 * Created by zhaojiuzhou on 2017/8/3.
 */

public class IndexMutilAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_ICON = 2;
    private static final int TYPE_APPS = 3;
    private static final int TYPE_GAMES = 4;

    //    private Context mContext;
    private LayoutInflater mInflater;

    public IndexMutilAdapter(Context context) {
//        this.mContext = mContext;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_BANNER;
            case 1:
                return TYPE_ICON;
            case 2:
                return TYPE_APPS;
            case 3:
                return TYPE_GAMES;
            default:
                return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.fragment_guide, parent, false);


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
