package com.disword.diswordlib.demo.pages.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.disword.diswordlib.R;
import com.disword.diswordlib.core.util.viewpager.BaseLoopPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * https://github.com/Aspsine/SwipeToLoadLayout/blob/master/app/src/main/java/com/aspsine/swipetoloadlayout/demo/adapter/LoopViewPagerAdapter.java
 */
public class LoopPagerActivity extends AppCompatActivity {

    @InjectView(R.id.mPager)
    ViewPager mPager;
    private MyLooperAdapter looperAdapter;
    private List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_pager);
        ButterKnife.inject(this);
        list.add(Color.BLUE);
        list.add(Color.BLACK);
        list.add(Color.GREEN);
        list.add(Color.MAGENTA);
        list.add(Color.RED);
        looperAdapter = new MyLooperAdapter(mPager, list);
        /**
         * 设置自动播放时间
         */
        looperAdapter.setDelayMillis(1000);
        mPager.setAdapter(looperAdapter);
        mPager.addOnPageChangeListener(looperAdapter);
        /**
         * 必须执行这一句 才能刷新
         */
        looperAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (looperAdapter != null)
            looperAdapter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**
         * 必须执行停止 不然会内存泄露
         */
        if (looperAdapter != null)
            looperAdapter.stop();
    }

    static class MyLooperAdapter extends BaseLoopPagerAdapter {
        private List<Integer> list;

        public MyLooperAdapter(ViewPager viewPager, List<Integer> list) {
            super(viewPager);
            this.list = list;
        }

        @Override
        public int getPagerCount() {
            return list.size();
        }

        @Override
        public Integer getItem(int position) {
            return list.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("getView");
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loop_pager_item, parent, false);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(String.valueOf(position));
            holder.tvName.setBackgroundColor(list.get(position));
            return convertView;
        }

        @Override
        public void onPageItemSelected(int position) {
            System.out.println("onPageItemSelected = " + position);
        }

        public static class ViewHolder {
            TextView tvName;
        }
    }
}
