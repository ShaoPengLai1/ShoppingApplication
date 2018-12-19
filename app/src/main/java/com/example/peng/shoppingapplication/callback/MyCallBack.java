package com.example.peng.shoppingapplication.callback;

public interface MyCallBack<T> {
    void success(T data);
    void failed(Exception e);
}
