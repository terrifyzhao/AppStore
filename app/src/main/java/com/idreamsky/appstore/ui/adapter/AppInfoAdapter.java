package com.idreamsky.appstore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhaojiuzhou on 2017/7/25.
 */

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {


    private Context mContext;
    private List<AppInfo> mData;
    private LayoutInflater mInflater;
    private Builder mBuilder;

    private AppInfoAdapter(Context mContext, Builder builder) {
        this.mContext = mContext;
        this.mBuilder = builder;

    }

    public void setData(List<AppInfo> mData) {
        if (this.mData != null){
            this.mData.addAll(mData);
        }else{
            this.mData = mData;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recycleview_item_app, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //应用名字
        holder.nameText.setText(mData.get(position).getDisplayName());
        //应用icon
        String imageUrl = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";
        Glide.with(mContext).load(imageUrl + mData.get(position).getIcon()).into(holder.iconImage);
        //简介
        holder.briefText.setVisibility(mBuilder.isShowBrief ? View.VISIBLE : View.GONE);
        holder.briefText.setText(mData.get(position).getBriefShow());
        //分类
        holder.categoryText.setVisibility(mBuilder.isShowCategory ? View.VISIBLE : View.GONE);
        holder.categoryText.setText(mData.get(position).getLevel1CategoryName());
        //排名
        holder.textNum.setVisibility(mBuilder.isShowNum ? View.VISIBLE : View.GONE);
        holder.textNum.setText(mData.get(position).getPosition()+1+".");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textNum)
        TextView textNum;
        @BindView(R.id.iconImage)
        ImageView iconImage;
        @BindView(R.id.nameText)
        TextView nameText;
        @BindView(R.id.categoryText)
        TextView categoryText;
        @BindView(R.id.briefText)
        TextView briefText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class Builder {

        private boolean isShowNum;
        private boolean isShowCategory;
        private boolean isShowBrief;
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder showNum(boolean showNum) {
            isShowNum = showNum;
            return this;
        }

        public Builder showCategory(boolean showCategory) {
            isShowCategory = showCategory;
            return this;
        }

        public Builder showBrief(boolean showBrief) {
            isShowBrief = showBrief;
            return this;
        }

        public AppInfoAdapter build() {
            return new AppInfoAdapter(context,this);
        }

    }
}
