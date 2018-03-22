package com.licheedev.serialtool.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mData;

    public BaseListAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getData() {
        return mData;
    }

    public void setNewData(List<? extends T> data) {
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 追加数据
     *
     * @param data
     */
    public void appendData(List<? extends T> data) {
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewHolder holder;
        if (convertView != null) {
            holder = (ListViewHolder) convertView.getTag();
        } else {
            int viewType = getItemViewType(position);
            convertView = LayoutInflater.from(parent.getContext())
                .inflate(getItemLayoutId(viewType), parent, false);
            holder = new ListViewHolder(convertView);
            convertView.setTag(holder);
        }
        inflateItem(holder, position);
        return convertView;
    }

    /**
     * 填充布局
     *
     * @param holder
     * @param position
     */
    protected abstract void inflateItem(ListViewHolder holder, int position);

    /**
     * 获取item布局
     *
     * @return
     */
    public abstract int getItemLayoutId(int viewType);
}
