package com.example.peng.shoppingapplication.model;

import com.example.peng.shoppingapplication.bean.CartBean;
import com.example.peng.shoppingapplication.callback.MyCallBack;

import java.util.Map;

public interface ModelCallBack {
    void success(CartBean cartBean);
    void failure(Exception e);
    //void requestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
}
