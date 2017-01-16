package com.disword.diswordlib.core.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.disword.diswordlib.core.network.ResponseCallback;

import java.util.HashMap;

/**
 * Created by disword on 17/1/10.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private BaseHttpUtil baseHttpUtil;
    private Toast mToast;

    private synchronized BaseHttpUtil getHttpUtil() {
        if (baseHttpUtil == null) {
            baseHttpUtil = new BaseHttpUtil();
            callback = new ResponseCallback() {
                @Override
                public void result(String result, int code, int taskId) {
                    httpCallback(result, code, taskId);
                }
            };
        }

        return baseHttpUtil;
    }

    public final void get(String baseUrl, HashMap<String, String> params, int taskId) {
        getHttpUtil().get(getApplicationContext(), baseUrl, params, taskId, callback);
    }

    public final void postJSON(String baseUrl, HashMap<String, String> params, int taskId) {
        getHttpUtil().postJSON(getApplicationContext(), baseUrl, params, taskId, callback);
    }

    private ResponseCallback callback;

    protected abstract void httpCallback(String result, int code, int taskId);

    @Override
    protected void onDestroy() {
        if (baseHttpUtil != null)
            baseHttpUtil.release();
        cancelToast();
        super.onDestroy();
    }


    public void showToast(String text) {
        if(mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }


}
