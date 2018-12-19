package com.example.peng.shoppingapplication.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.example.peng.shoppingapplication.bean.CartBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils instance;
    private final OkHttpClient httpClient;
    private Handler handler=new Handler(Looper.getMainLooper());

    public static OkHttpUtils getInstance(){
        if (instance==null){
            synchronized (OkHttpUtils.class){
                instance=new OkHttpUtils();
            }
        }
        return instance;
    }
    public OkHttpUtils(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient=new OkHttpClient.Builder()
                .connectTimeout(5000,TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .writeTimeout(5000,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }
    public String getSynchronous(String url,Class clazz,ICallBack callBack) throws IOException {
        Request request=new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call=httpClient.newCall(request);
        Response response=call.execute();
        return stream2String(response.body().bytes());
    }

    private String stream2String(byte[] bytes) {
        return new String(bytes);
    }
    public void getAsynchronization(String url, final Class clazz, final ICallBack<CartBean> callBack){
        Request request=new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call=httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.failed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result=response.body().string();
                    Gson gson=new Gson();
                    final Object o = gson.fromJson(result, clazz);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success((CartBean) o);
                        }
                    });
                }catch (Exception e){
                    callBack.failed(e);
                }
            }
        });
    }
    public void postAsynchronization(String url, Map<String,String> params, final Class clazz, final IcallBacks callBack){
        FormBody.Builder builder=new FormBody.Builder();
        for (Map.Entry<String,String> entry:params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body=builder.build();
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call=httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.failed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result=response.body().string();
                    Gson gson=new Gson();
                    final Object o = gson.fromJson(result, clazz);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(o);
                }
            });
                }catch (Exception e){
                    callBack.failed(e);
                }
            }
        });
    }
}
