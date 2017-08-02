package com.idreamsky.appstore.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idreamsky.appstore.R;
import com.idreamsky.appstore.bean.AppInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhaojiuzhou on 2017/7/25.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {


    private Context mContext;
    private List<AppInfo> mDatas;
    private LayoutInflater mInflater;

    public RecommendAdapter(Context mContext, List<AppInfo> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recycleitem_recommend, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNameText.setText(mDatas.get(position).getDisplayName());
        holder.mSizeText.setText(mDatas.get(position).getApkSize() / 1024 / 1024 + "M");
        String imageUrl = "http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";
        Glide.with(mContext).load(imageUrl+mDatas.get(position).getIcon()).into(holder.mIconImage);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iconImage)
        ImageView mIconImage;
        @BindView(R.id.nameText)
        TextView mNameText;
        @BindView(R.id.sizeText)
        TextView mSizeText;
        @BindView(R.id.downloadBtn)
        Button mDownloadBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
