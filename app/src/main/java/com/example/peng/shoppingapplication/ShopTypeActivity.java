package com.example.peng.shoppingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.peng.shoppingapplication.adapter.ShopTypeAdapter;
import com.example.peng.shoppingapplication.adapter.ShopTypeProductAdapter;
import com.example.peng.shoppingapplication.bean.ShopTypeBean;
import com.example.peng.shoppingapplication.bean.ShopTypeProductBean;
import com.example.peng.shoppingapplication.presenter.MyPresenter;
import com.example.peng.shoppingapplication.view.ViewCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peng
 */
public class ShopTypeActivity extends AppCompatActivity implements ViewCallBack {

    private MyPresenter mIPresenterImpl;
    private ShopTypeAdapter mShopTypeAdapter;
    private ShopTypeProductAdapter mShopTypeProductAdapter;
    private RecyclerView mRecyclerViewShopType, mRecyclerViewShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_type);
        mIPresenterImpl=new MyPresenter(this);
        initShopTypeView();
        initShopTypeProductView();
        getTypeData();
    }

    private void getTypeData() {
        Map<String, String> map = new HashMap<>();
        mIPresenterImpl.requestData(Apis.URL_PRODUCT_GET_CATAGORY, map, ShopTypeBean.class);
    }

    private void initShopTypeProductView() {
        mRecyclerViewShopType = findViewById(R.id.recyclerview_shop);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShopType.setLayoutManager(linearLayoutManager);
        mRecyclerViewShopType.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mShopTypeProductAdapter = new ShopTypeProductAdapter(this);
        mRecyclerViewShopType.setAdapter(mShopTypeProductAdapter);
    }

    private void initShopTypeView() {
        mRecyclerViewShop = findViewById(R.id.recyclerview_shop_type);
        LinearLayoutManager linearLayoutManagerLeft = new LinearLayoutManager(this);
        linearLayoutManagerLeft.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShop.setLayoutManager(linearLayoutManagerLeft);
        mRecyclerViewShop.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mShopTypeAdapter = new ShopTypeAdapter(this);
        mRecyclerViewShop.setAdapter(mShopTypeAdapter);
        mShopTypeAdapter.setOnClickListener(new ShopTypeAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String cid) {
                getShopData(cid);
            }
        });
    }

    private void getShopData(String cid) {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.MAP_KEY_PRODUCT_GET_CATAGORY_CID, cid);
        mIPresenterImpl.requestData(Apis.URL_PRODUCT_GET_PRODUCT_CATAGORY, map, ShopTypeProductBean.class);
    }

    @Override
    public void success(Object data) {
        if (data instanceof ShopTypeBean) {
            //获取数据后，展示左侧列表
            ShopTypeBean shopTypeBean = (ShopTypeBean) data;
            mShopTypeAdapter.setData(shopTypeBean.getData());
        } else if (data instanceof ShopTypeProductBean) {
            //获取数据后，展示右侧列表
            ShopTypeProductBean shopTypeProductBean = (ShopTypeProductBean) data;
            mShopTypeProductAdapter.setData(shopTypeProductBean.getData());

            //将右侧列表滚到顶部(这行不加也无所谓)
            mRecyclerViewShopType.scrollToPosition(0);
        }
    }

    @Override
    public void failure(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPresenterImpl.Detach();
    }
}
