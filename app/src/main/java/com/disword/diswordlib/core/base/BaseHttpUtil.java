package com.disword.diswordlib.core.base;

import android.content.Context;

import com.disword.diswordlib.core.network.ResponseCallback;
import com.disword.diswordlib.core.network.RxHttp;
import com.disword.diswordlib.core.network.util.FormatUtil;
import com.disword.diswordlib.core.network.util.RetryWithDelay;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by disword on 17/1/10.
 */

public class BaseHttpUtil {
    private CompositeSubscription mCompositeSubscription;
    private final int retryTime = 2 * 1000;
    private final int retryCount = 5;

    public final void get(Context context, String baseUrl, HashMap<String, String> params, final int taskId,
                          final ResponseCallback callback) {
        String url = baseUrl + FormatUtil.combineURL(params);
        System.out.println("url = " + url);
        add(RxHttp.createGet(url)
                .compose(applySchedulers())
                .retryWhen(new RetryWithDelay(context, retryCount, retryTime))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (callback != null)
                            callback.result(s, ResponseCallback.SUCCESS,taskId);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (callback != null)
                            callback.result("", ResponseCallback.FAIL,taskId);
                    }
                }));
    }


    public final void postJSON(Context context, String baseUrl, HashMap<String, String> params,final int taskId,
                               final ResponseCallback callback) {
        System.out.println("url = " + baseUrl);
        String json = FormatUtil.combineJSON(params);
        add(RxHttp.createPostJSON(baseUrl, json)
                .compose(applySchedulers())
                .retryWhen(new RetryWithDelay(context, retryCount, retryTime))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (callback != null)
                            callback.result(s, ResponseCallback.SUCCESS,taskId);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (callback != null)
                            callback.result("", ResponseCallback.FAIL,taskId);
                    }
                }));
    }

    private synchronized void add(Subscription subscription) {
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscription);
    }


    private Observable.Transformer<String, String> applySchedulers() {
        return new Observable.Transformer<String, String>() {
            @Override
            public Observable<String> call(Observable<String> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 取消Subscription
     */
    public void release() {
        if (mCompositeSubscription != null)
            mCompositeSubscription.unsubscribe();
    }
}
