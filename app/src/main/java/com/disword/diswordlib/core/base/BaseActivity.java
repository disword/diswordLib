package com.disword.diswordlib.core.base;

import android.support.v7.app.AppCompatActivity;

import com.disword.diswordlib.core.network.ResponseCallback;

import java.util.HashMap;

/**
 * Created by disword on 17/1/10.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private BaseHttpUtil baseHttpUtil;
    private synchronized BaseHttpUtil getHttpUtil(){
        if(baseHttpUtil == null) {
            baseHttpUtil = new BaseHttpUtil();
            callback = new ResponseCallback() {
                @Override
                public void result(String result, int code) {
                    httpCallback(result,code);
                }
            };
        }

        return baseHttpUtil;
    }
    public final void get(String baseUrl, HashMap<String, String> params) {
        getHttpUtil().get(getApplicationContext(),baseUrl,params, callback);
    }

    public final void postJSON(String baseUrl, HashMap<String, String> params) {
        getHttpUtil().postJSON(getApplicationContext(),baseUrl,params, callback);
    }

    private ResponseCallback callback;

    protected abstract void httpCallback(String result, int code);

    @Override
    protected void onDestroy() {
        if(baseHttpUtil != null)
            baseHttpUtil.release();
        super.onDestroy();
    }
}
