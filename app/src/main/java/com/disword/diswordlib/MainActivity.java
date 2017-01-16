package com.disword.diswordlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.disword.diswordlib.core.base.BaseActivity;

import java.util.HashMap;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String url = "http://www.baiqweqweqwdu.com";
//        HashMap<String,String> map = new HashMap<>();
//        map.put("aa","bb");
//        map.put("cc","dd");
//        get(url, map);

        String url = "https://cieupas-a85f6.firebaseio.com/bug.json";
        HashMap<String,String> map = new HashMap<>();
        map.put("aa","bb");
        map.put("cc","dd");
        postJSON(url, map,0);
    }

    @Override
    protected void httpCallback(String result, int code,int taskId) {
        System.out.println("code = " + code);
        System.out.println("result = " + result);
        System.out.println("id = " + Thread.currentThread().getId());
    }

    public void swipeListView(View view) {
        Intent intent = new Intent(this,SwipeListActivity.class);
        startActivity(intent);
    }

    public void rxbinding(View view) {
        Intent intent = new Intent(this,RxbindingActivity.class);
        startActivity(intent);
    }
}
