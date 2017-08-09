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
import com.idreamsky.appstore.bean.CategoryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.idreamsky.appstore.common.Constant.ICON_BASE_URL;

/**
 * Created by idreamsky on 2017/8/9.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {



    private Context mContext;
    private LayoutInflater mInflate;
    private List<CategoryBean> mData;

    public CategoryAdapter(Context context) {
        this.mContext = context;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.recycleview_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textName.setText(mData.get(position).getName());
        Glide.with(mContext).load(ICON_BASE_URL+mData.get(position).getIcon()).into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public void setData(List<CategoryBean> mData) {
        this.mData = mData;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.textName)
        TextView textName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
