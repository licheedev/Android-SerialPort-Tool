package com.licheedev.serialtool.util;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewHolder {

    private SparseArray<View> mViewArray;
    public View itemView;
    public int position;

    public ListViewHolder(View itemView) {
        this.itemView = itemView;
        mViewArray = new SparseArray<>();
        this.itemView.setTag(this);
    }

    public ListViewHolder(int layoutId, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        this.itemView = view;
        mViewArray = new SparseArray<>();
        this.itemView.setTag(this);
    }

    public View getItemView() {
        return itemView;
    }

    public void bindPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public <V extends View> V getView(int resId) {
        View view = mViewArray.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            mViewArray.put(resId, view);
        }
        return (V) view;
    }

    public void setText(int resId, CharSequence text) {
        TextView textView = getView(resId);
        textView.setText(text);
    }

    public TextView getText(int id) {
        return getView(id);
    }

    public ImageView getImage(int id) {
        return getView(id);
    }
}
    
    

