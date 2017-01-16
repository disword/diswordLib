package com.disword.diswordlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.disword.diswordlib.core.network.RxHttp;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxbindingActivity extends AppCompatActivity {
    private List<Subscription> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbinding);
        Button button = (Button) findViewById(R.id.request);
        /**
         * 防止重复点击 1.5秒后才执行最近的请求
         */
        Subscription subscribe = RxView.clicks(button)
                .debounce(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return RxHttp.createGet("https://www.baidu.com");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("result = " + s);
                    }
                });
        list.add(subscribe);

        EditText editText = (EditText) findViewById(R.id.editText);
        /**
         * 防止重复输入 0.5秒后才执行最近的请求
         */
        RxTextView.textChanges(editText)
                .debounce(500,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        String s = charSequence.toString();
                        Toast.makeText(RxbindingActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Subscription subscription : list) {
            if (subscription != null)
                subscription.unsubscribe();
        }
    }
}
