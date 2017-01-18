package com.disword.diswordlib.demo.pages.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.disword.diswordlib.R;
import com.disword.diswordlib.core.util.MySimpleAdapter;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class SearchListActivity extends AppCompatActivity {

    @InjectView(R.id.etSearch)
    EditText etSearch;
    @InjectView(R.id.listView)
    ListView listView;
    private MySimpleAdapter<String> mAdapter;
    private List<String> list = new ArrayList<>();
    private List<String> backupList = new ArrayList<>();
    private String lastKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.inject(this);

        list.add("c++");
        list.add("c#");
        list.add("java");
        list.add("javascript");
        list.add("php");

        backupList.addAll(list);
        mAdapter = new MySimpleAdapter<String>(this, R.layout.index_list_item, list) {
            @Override
            public void setView(int position, View convertView, SimpleViewHolder viewHolder) {
                viewHolder.setText(R.id.item, list.get(position));
            }
        };
        listView.setAdapter(mAdapter);
        RxTextView.textChanges(etSearch)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(new Func1<CharSequence, String>() {
                    @Override
                    public String call(CharSequence charSequence) {
                        return charSequence.toString();
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        if (s.equals(lastKeyword))
                            return false;
                        else {
                            lastKeyword = s;
                            return true;
                        }
                    }
                })
                .flatMap(new Func1<String, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(String s) {
                        if (TextUtils.isEmpty(s)) {
                            List<String> resultList = new ArrayList<>(backupList);
                            return Observable.just(resultList);
                        } else
                            return searchKeyword(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        list.clear();
                        list.addAll(strings);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private Observable<List<String>> searchKeyword(final String keyword) {
        return Observable.from(backupList).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                if (TextUtils.isEmpty(s))
                    return false;
                return s.toLowerCase().contains(keyword.toLowerCase());
            }
        }).toList();
    }
}
