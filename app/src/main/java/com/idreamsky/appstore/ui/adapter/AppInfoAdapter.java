package com.idreamsky.appstore.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.idreamsky.appstore.AppApplication;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;
import com.idreamsky.appstore.ui.activity.AppDetailActivity;
import com.idreamsky.appstore.ui.widget.DownloadController;
import com.idreamsky.appstore.ui.widget.DownloadProgressButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zlc.season.rxdownload2.RxDownload;

import static com.idreamsky.appstore.common.Constant.ICON_BASE_URL;

/**
 * Created by zhaojiuzhou on 2017/7/25.
 */

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {



    private Context mContext;
    private List<AppInfo> mData;
    private LayoutInflater mInflater;
    private Builder mBuilder;
    private AppApplication mApplication;

    private DownloadController mDownloadController;

    private AppInfoAdapter(Context mContext, Builder builder, AppApplication application, RxDownload download) {
        this.mContext = mContext;
        this.mBuilder = builder;
        this.mApplication = application;

        mDownloadController = new DownloadController(download,mContext);
    }

    public void setData(List<AppInfo> mData) {
        if (this.mData != null) {
            this.mData.addAll(mData);
        } else {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //应用名字
        holder.nameText.setText(mData.get(position).getDisplayName());
        //应用icon
        Glide.with(mContext).load(ICON_BASE_URL + mData.get(position).getIcon()).into(holder.iconImage);
        //简介
        holder.briefText.setVisibility(mBuilder.isShowBrief ? View.VISIBLE : View.GONE);
        holder.briefText.setText(mData.get(position).getBriefShow());
        //分类
        holder.categoryText.setVisibility(mBuilder.isShowCategory ? View.VISIBLE : View.GONE);
        holder.categoryText.setText(mData.get(position).getLevel1CategoryName());
        //排名
        holder.textNum.setVisibility(mBuilder.isShowNum ? View.VISIBLE : View.GONE);
        holder.textNum.setText(mData.get(position).getPosition() + 1 + ".");
//        holder.itemView.setTag(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApplication.setmView(v);
                Intent intent = new Intent(mContext, AppDetailActivity.class);
                intent.putExtra("appInfo", mData.get(position));
                mContext.startActivity(intent);
            }
        });

        mDownloadController.handleClick(holder.btnClick,mData.get(position));
        holder.btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "toast : "+position, Toast.LENGTH_SHORT).show();
                mDownloadController.bindClick(holder.btnClick,mData.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
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
        @BindView(R.id.btnClick)
        DownloadProgressButton btnClick;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class Builder {

        private boolean isShowNum;
        private boolean isShowCategory;
        private boolean isShowBrief;
        private Context context;
        private AppApplication appApplication;
        private RxDownload download;

        public Builder(Context context, AppApplication appApplication) {
            this.context = context;
            this.appApplication = appApplication;
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

        public Builder rxDownload(RxDownload download) {
            this.download = download;
            return this;
        }

        public AppInfoAdapter build() {
            return new AppInfoAdapter(context, this, appApplication, download);
        }

    }
}
