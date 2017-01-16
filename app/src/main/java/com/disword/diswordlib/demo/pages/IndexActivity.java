package com.disword.diswordlib.demo.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.disword.diswordlib.R;
import com.disword.diswordlib.core.util.MySimpleAdapter;
import com.disword.diswordlib.demo.entity.IndexData;
import com.disword.diswordlib.demo.pages.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {
    private MySimpleAdapter simpleAdapter;
    private List<IndexData> list = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        listView = (ListView) findViewById(R.id.listView);

        addData();
        simpleAdapter = new MySimpleAdapter<IndexData>(this, R.layout.index_list_item, list) {
            @Override
            public void setView(int position, View convertView, SimpleViewHolder viewHolder) {
                viewHolder.setText(R.id.item, list.get(position).name);
            }
        };

        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(IndexActivity.this, list.get(i).cls));
            }
        });
    }

    private void addData() {
        Class<?>[] clsGroup = {LoginActivity.class};
        String[] clsNames = {"登陆"};
        for (int i = 0; i < clsGroup.length; i++) {
            IndexData indexData = new IndexData();
            indexData.cls = clsGroup[i];
            indexData.name = clsNames[i];
            list.add(indexData);
        }
    }


}
