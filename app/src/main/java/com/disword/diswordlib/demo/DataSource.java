package com.disword.diswordlib.demo;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来模拟http数据请求
 */

public class DataSource {

    private Handler handler = new Handler(Looper.getMainLooper());

    private static DataSource dataSource;
    private int times;
    private DataSource(){

    }

    public static synchronized DataSource getInstance() {
        if (dataSource == null)
            dataSource = new DataSource();
        return dataSource;
    }

    /**
     * 模拟http网络请求
     * @param page
     * @param httpCallback
     */
    public void getSwipeList(final int page, final HttpCallback<List<String>> httpCallback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                times++;
                /**
                 * 模拟拿不到数据的情况
                 */
                if(times > 1 && times % 2 == 0) {
                    httpCallback.result(null);
                    return;
                }
                List<String> temp = new ArrayList<>();
                for (int i = (page - 1) * 20; i < page * 20; i++) {
                    temp.add("item" + i);
                }
                httpCallback.result(temp);
            }
        }, 1000);
    }

    public interface HttpCallback<T> {
        void result(T t);

        void fail(String error, int code);
    }
}
