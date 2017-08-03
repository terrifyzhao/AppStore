package com.idreamsky.appstore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.Banner;
import com.idreamsky.appstore.bean.IndexBean;
import com.idreamsky.appstore.ui.widget.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaojiuzhou on 2017/8/3.
 */

public class IndexMutilAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_ICON = 2;
    private static final int TYPE_APPS = 3;
    private static final int TYPE_GAMES = 4;


    private LayoutInflater mInflater;
    private IndexBean mIndexBeen;

    public IndexMutilAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(IndexBean indexBeen){
        this.mIndexBeen = indexBeen;
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
        View view;
        switch (viewType) {
            case TYPE_BANNER:
                view = mInflater.inflate(R.layout.template_banner, parent, false);
                return new BannerViewHolder(view);
            case TYPE_ICON:
                view = mInflater.inflate(R.layout.template_nav_icon, parent, false);
                return new IconViewHolder(view);
            case TYPE_GAMES:
            case TYPE_APPS:
                view = mInflater.inflate(R.layout.template_appinfo, parent, false);
                return new AppViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;

            List<Banner> banners = mIndexBeen.getBanners();
            List<String> urls = new ArrayList<>(banners.size());
            for (Banner banner : banners){
                urls.add(banner.getThumbnail());
            }
            bannerViewHolder.banner.setViewUrls(urls);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        BannerLayout banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class IconViewHolder extends RecyclerView.ViewHolder {

        public IconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }

    class AppViewHolder extends RecyclerView.ViewHolder {

        public AppViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
