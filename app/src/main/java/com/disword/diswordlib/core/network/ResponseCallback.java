package com.disword.diswordlib.core.network;

/**
 * Created by disword on 17/1/10.
 */

public interface ResponseCallback {
    int SUCCESS = 666;
    int FAIL = 500;
    void result(String result,int code);
}
