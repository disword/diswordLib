package com.disword.diswordlib;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.disword.diswordlib.core.base.BaseActivity;
import com.disword.diswordlib.core.data.ListManager;
import com.disword.diswordlib.core.util.anim.TweenAnimUtil;
import com.disword.diswordlib.core.widget.swipetoloadlayout.OnLoadMoreListener;
import com.disword.diswordlib.core.widget.swipetoloadlayout.OnRefreshListener;
import com.disword.diswordlib.core.widget.swipetoloadlayout.SwipeToLoadLayout;
import com.disword.diswordlib.demo.SwipeAdapter;
import com.disword.diswordlib.demo.SwipeListManager;

import java.util.ArrayList;
import java.util.List;

public class SwipeListActivity extends BaseActivity implements ListManager.ListCallback {

    private SwipeToLoadLayout swipeToLoadLayout;
    private SwipeAdapter mAdapter;
    private ListView listView;
    private TextView tvEmpty;
    private List<String> list = new ArrayList<>();
    private SwipeListManager listManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_list);

        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        listView = (ListView) findViewById(R.id.swipe_target);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        initListView();
    }

    private void initListView() {
        listManager = new SwipeListManager(1, this) {
            @Override
            public void onDataLoaded(String data) {
                list.add(data);
            }
        };

        mAdapter = new SwipeAdapter(this, list);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(SwipeListActivity.this,"click "+position,Toast.LENGTH_SHORT).show();
            }
        });
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
                listManager.refresh();
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                listManager.loadMore();
            }
        });
        swipeToLoadLayout.setRefreshing(true);

    }



    @Override
    public void refreshList() {
        if(list.size() > 0 && tvEmpty.getVisibility() == View.VISIBLE) {
            TweenAnimUtil.hideAnim(tvEmpty,300);
        }
        mAdapter.notifyDataSetChanged();
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void showNoData() {
        TweenAnimUtil.showAnim(tvEmpty,300);
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void reset() {
        list.clear();
    }

    @Override
    protected void httpCallback(String result, int code, int taskId) {

    }
}
