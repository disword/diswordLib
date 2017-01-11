package com.disword.diswordlib.core.network;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by disword on 17/1/10.
 */

public class RxHttp {
    public static Observable<String> createGet(final String url){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = HttpRequest.get(url);
                    subscriber.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<String> createPostJSON(final String url,final String json){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String result = HttpRequest.postJSON(url,json);
                    subscriber.onNext(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
