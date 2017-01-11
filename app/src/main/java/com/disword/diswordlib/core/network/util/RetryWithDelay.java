package com.disword.diswordlib.core.network.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by disword on 17/1/10.
 * http://www.tuicool.com/articles/zyeMz2
 */

public class RetryWithDelay implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;
    private final Context context;

    public RetryWithDelay(Context context, int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.context = context;
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (!isConnectedToInternet())
                            return Observable.error(throwable);

                        if (++retryCount <= maxRetries) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            System.out.println("get error, it will try after " + retryDelayMillis * Math.pow(2, retryCount - 1)
                                    + " millisecond, retry count " + retryCount);
                            return Observable.timer((long) (retryDelayMillis * Math.pow(2, retryCount - 1)),
                                    TimeUnit.MILLISECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }
                });
    }
}
