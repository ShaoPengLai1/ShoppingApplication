package model;

import android.util.Log;

import com.google.gson.Gson;

import bean.Goods;

import bean.News;
import http.HttpConfig;
import http.OkHttpUtils;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class MainModel {
    private static final String TAG = "MainModel+++++++";
    public void showGoods(int page,final MainmodelCallback mainmodelCallback){
        OkHttpUtils okHttpUtils = OkHttpUtils.getoKhttpUtils();
        okHttpUtils.doGet(HttpConfig.url + page, new OkHttpUtils.IOKhttpUtilsCallback() {
            @Override
            public void onFailure(String error) {
                if(mainmodelCallback!=null){
                    mainmodelCallback.getFaid(error);
                }
            }

            @Override
            public void onResponse(String json) {
                Goods goods = new Gson().fromJson(json, Goods.class);
                if(goods.getCode().equals("0")){
                    if(mainmodelCallback!=null){
                        mainmodelCallback.getSuccess(goods);
                    }
                }else{
                    if(mainmodelCallback!=null){
                        mainmodelCallback.getFaid("请求失败");
                    }
                }

            }
        });

    }




    public interface MainmodelCallback{
        void getSuccess(Goods goods);
        void getFaid(String  error);
    }


}
