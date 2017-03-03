package com.disword.diswordlib.demo.repository;

import android.content.Context;
import android.text.TextUtils;

import com.disword.diswordlib.core.network.RxHttp;
import com.disword.diswordlib.core.network.util.RetryWithDelay;
import com.disword.diswordlib.demo.entity.UserInfo;

import java.util.concurrent.Executors;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by disword on 17/3/3.
 */

public class ApiManager {
    private final int retryTime = 2 * 1000;
    private final int retryCount = 5;
    private boolean isTokenExpired;
    private String token;
    final Scheduler scheduler = Schedulers.from(Executors.newSingleThreadExecutor());

    private synchronized Observable<String> getRefreshToken(Context context) {
        if (!isTokenExpired && !TextUtils.isEmpty(token))
            return Observable.just(token);
        return RxHttp.createPostJSON("", "")
                .retryWhen(new RetryWithDelay(context, retryCount, retryTime))
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        /**
                         * 处理结果返回token
                         */
                        token = "abcToken";
                        isTokenExpired = false;
                        return token;
                    }
                });
    }


    public Observable<UserInfo> getUserInfo(final Context context) {
        return RxHttp.createPostJSON("", "").flatMap(new Func1<String, Observable<UserInfo>>() {
            @Override
            public Observable<UserInfo> call(String s) {
                boolean isTokenValid = IsTokenValid(s);
                isTokenExpired = !isTokenValid;
                return isTokenValid ?
                        Observable.just(new UserInfo()) :
                        Observable.<UserInfo>error(new NullPointerException("Token is null"));
            }
        }).compose(this.<UserInfo>applySchedulers()).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable instanceof NullPointerException)
                            return getRefreshToken(context).subscribeOn(scheduler);
                        return Observable.error(throwable);
                    }
                });
            }
        }).retryWhen(new RetryWithDelay(context, retryCount, retryTime));
    }

    /**
     * 检测返回数据token是否有效
     *
     * @param s
     * @return
     */
    private boolean IsTokenValid(String s) {
        return true;
    }

    private <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
