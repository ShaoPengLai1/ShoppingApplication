package com.example.peng.shoppingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.shoppingapplication.adapter.BaseRecycleAdapter;
import com.example.peng.shoppingapplication.adapter.SeachRecordAdapter;
import com.example.peng.shoppingapplication.db.DbDao;

public class MainActivity extends AppCompatActivity {
    private Button mbtn_serarch;
    private EditText met_search;
    private RecyclerView mRecyclerView;
    private TextView mtv_deleteAll;
    private SeachRecordAdapter mAdapter;
    private DbDao mDbDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDbDao=new DbDao(this);
        mbtn_serarch=findViewById(R.id.btn_serarch);
        met_search=findViewById(R.id.et_search);
        mtv_deleteAll=findViewById(R.id.tv_deleteAll);
        mtv_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbDao.deleteData();
                mAdapter.updata(mDbDao.queryData(""));
            }
        });
        mRecyclerView=findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new SeachRecordAdapter(mDbDao.queryData(""),this);
        mAdapter.setRvItemOnclickListener(new BaseRecycleAdapter.RvItemOnclickListener() {
            @Override
            public void RvItemOnclick(int position) {
                mDbDao.delete(mDbDao.queryData("").get(position));
                mAdapter.updata(mDbDao.queryData(""));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mbtn_serarch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (met_search.getText().toString().trim().length()!=0){
                    boolean hasData = mDbDao.hasData(met_search.getText().toString().trim());
                    if (!hasData){
                        mDbDao.insertData(met_search.getText().toString().trim());
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "该内容已在历史记录中", Toast.LENGTH_SHORT).show();
                    }
                    mAdapter.updata(mDbDao.queryData(""));
                }else {
                    Toast.makeText(MainActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
