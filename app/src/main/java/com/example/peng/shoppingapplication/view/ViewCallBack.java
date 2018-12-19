package com.example.peng.shoppingapplication.view;

public interface ViewCallBack <T>{
    void success(T data);
    void failure(Exception e);
}
