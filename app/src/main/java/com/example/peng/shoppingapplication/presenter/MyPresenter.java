package com.example.peng.shoppingapplication.presenter;

import com.example.peng.shoppingapplication.bean.CartBean;
import com.example.peng.shoppingapplication.callback.MyCallBack;
import com.example.peng.shoppingapplication.model.ModelCallBack;
import com.example.peng.shoppingapplication.model.MyModel;
import com.example.peng.shoppingapplication.view.ViewCallBack;

import java.util.Map;

public class MyPresenter {
    MyModel myModel=new MyModel();
    ViewCallBack viewCallBack;
    public MyPresenter(ViewCallBack viewCallBack){
        this.viewCallBack=viewCallBack;
    }
    public void getData(){
        myModel.getData(new ModelCallBack() {
            @Override
            public void success(CartBean cartBean) {
                if (viewCallBack!=null){
                    viewCallBack.success(cartBean);
                }
            }

            @Override
            public void failure(Exception e) {
                if (viewCallBack!=null){
                    viewCallBack.failure(e);
                }
            }


        });
    }

    public void requestData(String url, Map<String, String> params, Class clazz) {
        myModel.requestData(url, params, clazz, new MyCallBack() {
            @Override
            public void success(Object data) {
                viewCallBack.success(data);
            }

            @Override
            public void failed(Exception e) {
                viewCallBack.failure(e);
            }
        });
    }

    public void Detach(){
        if (myModel!=null){
            myModel=null;
        }
        if (viewCallBack!=null){
            viewCallBack=null;
        }
    }
}
