package com.example.peng.shoppingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peng.shoppingapplication.adapter.RecyAdapter;
import com.example.peng.shoppingapplication.bean.CartBean;
import com.example.peng.shoppingapplication.presenter.MyPresenter;
import com.example.peng.shoppingapplication.view.ViewCallBack;

public class LoginActivity extends AppCompatActivity implements ViewCallBack {

    private RecyclerView recyclerView;
    private TextView total_price;
    private TextView total_num;
    private CheckBox quanxuan;
    private MyPresenter myPresenter;
    private RecyAdapter recyAdapter;
    private TextView Settlement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        recyclerView=findViewById(R.id.recycler_view);
        total_price=findViewById(R.id.total_price);
        total_num=findViewById(R.id.total_num);
        quanxuan=findViewById(R.id.quanxuan);
        Settlement=findViewById(R.id.settlement);


        quanxuan.setTag(1);
        LinearLayoutManager manager=new LinearLayoutManager(LoginActivity.this,LinearLayoutManager.VERTICAL,false);
        recyAdapter=new RecyAdapter(this);
        myPresenter=new MyPresenter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyAdapter);
        myPresenter.getData();
        recyAdapter.setUpdateListener(new RecyAdapter.UpdateListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                total_num.setText("共"+num+"件商品");
                total_price.setText("总价:￥"+total+"元");
                if (allCheck){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_selected);
                }else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_unselected);
                }
                quanxuan.setChecked(allCheck);
            }
        });
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag= (int) quanxuan.getTag();
                if (tag==1){
                    quanxuan.setTag(2);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_selected);
                }else {
                    quanxuan.setTag(1);
                    quanxuan.setBackgroundResource(R.drawable.ic_action_unselected);
                }
                recyAdapter.quanxuan(quanxuan.isChecked());
            }
        });

            Settlement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, ShopTypeActivity.class));
                }
            });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresenter.Detach();
    }

    @Override
    public void success(Object data) {
        CartBean cartBean= (CartBean) data;
        recyAdapter.add(cartBean);
    }

    @Override
    public void failure(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
