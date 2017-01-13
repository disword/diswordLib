package com.disword.diswordlib;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import com.disword.diswordlib.core.widget.swipetoloadlayout.OnLoadMoreListener;
import com.disword.diswordlib.core.widget.swipetoloadlayout.OnRefreshListener;
import com.disword.diswordlib.core.widget.swipetoloadlayout.SwipeToLoadLayout;
import com.disword.diswordlib.demo.SwipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class SwipeListActivity extends AppCompatActivity {

    private SwipeToLoadLayout swipeToLoadLayout;
    private SwipeAdapter mAdapter;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_list);

        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView = (ListView) findViewById(R.id.swipe_target);


        mAdapter = new SwipeAdapter(this, list);
        listView.setAdapter(mAdapter);
        /**无效*/
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(SwipeListActivity.this,"position = " +i,Toast.LENGTH_SHORT).show();
//            }
//        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1 && !ViewCompat.canScrollVertically(view, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        for (int i = 0; i < 30; i++) {
                            list.add("item" + i);
                        }
                        mAdapter.notifyDataSetChanged();
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int len = list.size() + 1;
                        for (int i = len; i < len + 30; i++) {
                            list.add("item" + i);
                        }
                        mAdapter.notifyDataSetChanged();
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
        swipeToLoadLayout.setRefreshing(true);

    }
}
