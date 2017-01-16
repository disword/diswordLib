package com.disword.diswordlib.demo;

import com.disword.diswordlib.core.data.ListManager;

import java.util.List;

/**
 * Created by disword on 2017/1/14.
 */

public abstract class SwipeListManager extends ListManager<String> {
    /**
     * @param defaultPageIndex 初始页数
     * @param listCallback
     */
    public SwipeListManager(int defaultPageIndex, ListCallback listCallback) {
        super(defaultPageIndex, listCallback);
    }

    @Override
    public void loadData(final int page) {
        DataSource.getInstance().getSwipeList(page, new DataSource.HttpCallback<List<String>>() {
            @Override
            public void result(List<String> strings) {
                if (isStop()) return;
                if (strings == null || strings.size() == 0) {
                    setNoData();
                    refreshComplete(false);
                } else {
                    for (String data : strings) {
                        onDataLoaded(data);
                    }
                    refreshComplete(true);
                }


            }

            @Override
            public void fail(String error, int code) {
                setError();
                refreshComplete(false);
            }
        });
    }

}
