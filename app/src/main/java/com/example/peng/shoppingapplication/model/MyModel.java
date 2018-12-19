package com.example.peng.shoppingapplication.model;

import com.example.peng.shoppingapplication.bean.CartBean;
import com.example.peng.shoppingapplication.callback.MyCallBack;
import com.example.peng.shoppingapplication.okhttp.ICallBack;
import com.example.peng.shoppingapplication.okhttp.IcallBacks;
import com.example.peng.shoppingapplication.okhttp.OkHttpUtils;

import java.util.Map;

public class MyModel{
    public void getData(final ModelCallBack modelCallBack){
        String path="http://120.27.23.105/product/getCarts?uid=100";
        OkHttpUtils.getInstance().getAsynchronization(path, CartBean.class, new ICallBack<CartBean>() {
            @Override
            protected void success(CartBean cartBean) {
                modelCallBack.success(cartBean);
            }

            @Override
            protected void failed(Exception e) {
                modelCallBack.failure(e);
            }
        });

    }


    public void requestData(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttpUtils.getInstance().postAsynchronization(url, params, clazz, new IcallBacks() {

            @Override
            public void success(Object obj) {
                callBack.success(obj);
            }

            @Override
            public void failed(Exception e) {
                callBack.failed(e);
            }
        });
    }
}
