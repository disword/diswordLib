package com.disword.diswordlib.core.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by disword on 17/1/16.
 */

public abstract class MySimpleAdapter<T> extends BaseAdapter {

    private List<T> list;
    private Context mContext;
    private LayoutInflater mInflater;
    private final int layoutRes;

    public MySimpleAdapter(Context mContext, int layoutRes, List<T> list) {
        this.mContext = mContext;
        this.layoutRes = layoutRes;
        this.list = list;

        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (list == null)
            throw new NullPointerException("list is null");
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        SimpleViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(layoutRes, null);
            viewHolder = new SimpleViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (SimpleViewHolder) convertView.getTag();

        setView(position, convertView, viewHolder);
        return convertView;
    }

    public abstract void setView(int position, View convertView, SimpleViewHolder viewHolder);


    public static class SimpleViewHolder {
        final View itemView;
        SparseArray<View> viewHolder;

        public SimpleViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public void setText(int res, String info) {
            View view = findView(res);
            if (view instanceof TextView) {
                ((TextView) view).setText(info);
            }
        }

        public void setImageResource(int res, int img){
            View view = findView(res);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(img);
            }
        }

        public void setBackground(int res, int bg){
            View view = findView(res);
            if (view != null) {
                view.setBackgroundResource(bg);
            }
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T findView(int id) {
            if(viewHolder == null)
                viewHolder = new SparseArray<>();

            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = itemView.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
