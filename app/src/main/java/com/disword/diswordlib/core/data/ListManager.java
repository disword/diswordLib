package com.disword.diswordlib.core.data;

/**
 * Created by disword on 2017/1/14.
 */

public abstract class ListManager<T> {


    private ListCallback listCallback;
    protected int defaultPageIndex;
    protected int currentPageIndex;
    private boolean isRefreshing;
    private boolean isInit;
    private boolean isCancel;
    protected boolean isLoadMore;

    public abstract void onDataLoaded(T data);

    public interface ListCallback {
        void refreshList();

        /**
         * 没有数据时UI提示
         */
        void showNoData();

        /**
         * 获取不到数据时网络提示
         */
        void showNetworkError();

        /**
         * 刷新重置 例如list清空之类的操作
         */
        void reset();
    }

    /**
     * @param defaultPageIndex 初始页数
     * @param listCallback
     */
    public ListManager(int defaultPageIndex, ListCallback listCallback) {
        this.listCallback = listCallback;
        this.defaultPageIndex = defaultPageIndex;
        this.currentPageIndex = defaultPageIndex;
    }

    protected abstract void loadData(int page);

    public synchronized void loadMore() {
        if (isRefreshing || !isInit)
            return;
        isRefreshing = true;
        isLoadMore = true;
        loadData(currentPageIndex + 1);
    }

    public synchronized void refresh() {
        if (isRefreshing)
            return;
        this.isInit = false;
        this.currentPageIndex = defaultPageIndex;
        isRefreshing = true;
        clear();
        loadData(defaultPageIndex);
    }

    protected final synchronized void refreshComplete(boolean hasData) {
        if (isInit && hasData && isLoadMore) currentPageIndex++;
        if (!isInit) isInit = true;
        isRefreshing = false;
        isLoadMore = false;
        listCallback.refreshList();
    }

    protected final void setError() {
        if (!isInit)
            listCallback.showNetworkError();
    }

    protected final void setNoData() {
        if (!isInit)
            listCallback.showNoData();
    }

    public void stopTask() {
        isCancel = true;
    }

    public boolean isStop() {
        return isCancel;
    }

    protected final void clear() {
        listCallback.reset();
    }
}
