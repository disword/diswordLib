package com.disword.diswordlib.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.disword.diswordlib.R;
import com.disword.diswordlib.core.util.ViewHolderUtil;

import java.util.List;

/**
 * Created by disword on 17/1/13.
 */

public class SwipeAdapter extends BaseSwipeAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;

    public SwipeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
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
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return mInflater.inflate(R.layout.listview_item, null);
    }

    @Override
    public void fillValues(final int position, View convertView) {
        SwipeLayout swipeLayout = ViewHolderUtil.get(convertView, getSwipeLayoutResourceId(position));
        TextView text_data = ViewHolderUtil.get(convertView, R.id.text_data);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {

            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click delete "+ position, Toast.LENGTH_SHORT).show();
            }
        });
        text_data.setText(list.get(position));

    }



}
