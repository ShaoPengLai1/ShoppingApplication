package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwei.shoppingcar.R;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import adapter.StaggerAdapter;
import app.MyApp;
import banner.BannerIamge;
import base.MainView;
import bean.Goods;

import presenter.MainPresenter;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class FirstFragment extends Fragment implements MainView {
    private static final String TAG = "FirstFragment+++++++++";
    private View view;
    private RecyclerView recy;
    private MainPresenter mainPresenter=new MainPresenter(this);
    private int page=1;
    private List<Goods.DataBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.first_layout,null);
        //轮播图
         initBaners();
         initView();
         mainPresenter.showGoods(page);
         return view;

    }

    private void initView() {
        recy = view.findViewById(R.id.recy);
    }

    private void initBaners() {
        //http://www.zhaoapi.cn/images/quarter/ad1.png
        Banner banner= (Banner) view.findViewById(R.id.banner);
        // banner.setImageLoader(new BannerIamge());
        banner.setImageLoader(new BannerIamge());
        List<String> imgList = new ArrayList<>();
        imgList.add("http://www.zhaoapi.cn/images/quarter/ad1.png");
        imgList.add("http://www.zhaoapi.cn/images/quarter/ad2.png");
        imgList.add("http://www.zhaoapi.cn/images/quarter/ad3.png");
        imgList.add("http://www.zhaoapi.cn/images/quarter/ad4.png");
        banner.setImages(imgList);
        banner.setDelayTime(2000);
        banner.start();

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void ShowSuccess(Goods goods) {
        Log.d(TAG, "ShowSuccess: ++++++"+goods.toString());
        list = goods.getData();
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recy.setLayoutManager(staggeredGridLayoutManager);
        StaggerAdapter normalAdapter = new StaggerAdapter(list);
       /* normalAdapter.addItem(goods);*/
        recy.setAdapter(normalAdapter);
    }

    @Override
    public void ShowError(String error) {
         
    }


}
