package presenter;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import bean.News;
import http.OkhtttpUtils;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class NewsPresenter {
    public void requestPresenter(String url, Map<String,String> map, final HttpPresenter httpPresenter){
        OkhtttpUtils instance = OkhtttpUtils.getInstance();
        instance.doPost(url, map, new OkhtttpUtils.OkCallback() {
            @Override
            public void onFailure(Exception e) {
                httpPresenter.failed();
            }

            @Override
            public void onResponse(String json) {
                News news = new Gson().fromJson(json, News.class);
                List<News.DataBean> data = news.getData();
                httpPresenter.success(data);
            }
        });
    }

    public interface HttpPresenter{
        void success(List<News.DataBean> data);
        void failed();
    }
}


