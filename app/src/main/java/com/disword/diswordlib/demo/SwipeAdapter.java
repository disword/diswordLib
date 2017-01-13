package com.disword.diswordlib.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.disword.diswordlib.R;
import com.disword.diswordlib.core.util.ViewHolderUtil;

import java.util.List;

/**
 * Created by disword on 17/1/13.
 */

public class SwipeAdapter extends BaseAdapter {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = mInflater.inflate(R.layout.listview_item, null);
        TextView tv = ViewHolderUtil.get(view, R.id.content);
        Button button = ViewHolderUtil.get(view, R.id.btnDelete);

        tv.setText(list.get(i));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click " + i, Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "delete " + i, Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }


}
