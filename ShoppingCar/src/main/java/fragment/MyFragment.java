package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.shoppingcar.MainActivity;
import com.bwei.shoppingcar.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.MyAdapter;
import app.MyApp;
import base.MainView;
import bean.Goods;
import bean.News;

import presenter.MainPresenter;
import presenter.NewsPresenter;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private View view;

    private ListView listView;
    String url = "http://www.zhaoapi.cn/product/getCarts";
    private ExpandableListView el_cart;
    private CheckBox cb_cart_all_select;
    private TextView tv_cart_total_price;
    private Button btn_cart_pay;
    private MyAdapter mMyAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_layout,null);
        initViews();
        initData();
         return view;
    }

    private void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","71");
        NewsPresenter newsPresenter = new NewsPresenter();
        newsPresenter.requestPresenter(url, map, new NewsPresenter.HttpPresenter() {
            @Override
            public void success(List<News.DataBean> data) {
                //创建adapter
                mMyAdapter = new MyAdapter(MyApp.getContext(), data);
                mMyAdapter.setOnCartListChangeListener(new MyAdapter.onCartListChangeListener() {
                    @Override
                    public void onSellerCheckedChange(int groupPosition) {
                        //B.判断当前商家所有商品是否被选中 (一种是所有的都被选中,一种是没有所有的都选中)
                        boolean currentSellerAllProductSelected = mMyAdapter.isCurrentSellerAllProductSelected(groupPosition);
                        //提示为什么!currentSellerAllProductSelected取反,因为像灯一样,亮为true,暗为false,我先看灯是亮的,true,那么
                        //我要把他关了,取反把true改为false,然后设置进去,那么每一次我按钮的时候,都是拿到当前的状态,取反,再设置进去
                        //C.当商家的全选框点击时,更新所有商品全选的状态
                        mMyAdapter.changeCurrentSellerAllProductsStatus(groupPosition,!currentSellerAllProductSelected);
                        mMyAdapter.notifyDataSetChanged();
                        //刷新底部
                        refreshSelectedAndTotalPriceAndTotalNumber();
                    }

                    @Override
                    public void onProductCheckedChange(int groupPosition, int childPosition) {
                        //C.当商品的全选框选中时,更新商家选框的状态
                        mMyAdapter.changeCurrentProductStatus(groupPosition,childPosition);
                        mMyAdapter.notifyDataSetChanged();
                        refreshSelectedAndTotalPriceAndTotalNumber();
                    }

                    @Override
                    public void onProducNumberChange(int groupPosition, int childPosition, int number) {
                        //C.当加减器被点击时,改变里面当前商品的数量
                        mMyAdapter.changeCurrentProductNumber(groupPosition,childPosition,number);
                        mMyAdapter.notifyDataSetChanged();
                        refreshSelectedAndTotalPriceAndTotalNumber();
                    }
                });


                el_cart.setAdapter(mMyAdapter);
                //展开二级列表
                //展开二级列表
                for(int x=0; x<data.size(); x++){
                    el_cart.expandGroup(x);
                }
            }

            @Override
            public void failed() {

            }
        });


    }



    private void initViews() {
        el_cart = (ExpandableListView) view.findViewById(R.id.el_cart);
        cb_cart_all_select = (CheckBox) view.findViewById(R.id.cb_cart_all_select);
        tv_cart_total_price = (TextView)view. findViewById(R.id.tv_cart_total_price);
        btn_cart_pay = (Button) view.findViewById(R.id.btn_cart_pay);

        cb_cart_all_select.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_cart_all_select:
                //B.底部有一个全选按钮的逻辑,也要判断,不过范围更大, 是所有的商品是否被选中
                boolean allProductsSelected = mMyAdapter.isAllProductsSelected();
                //C.当最底部的全选框选中时,更新所有商品全选框的状态
                mMyAdapter.changeAllProductStatus(!allProductsSelected);
                mMyAdapter.notifyDataSetChanged();
                //刷新底部的数据显示
                refreshSelectedAndTotalPriceAndTotalNumber();
                break;
        }
    }

    //B.刷新底部全选框,textVIew和Button的UI,改变了CheckBox状态,总价,总数量
    private void refreshSelectedAndTotalPriceAndTotalNumber(){
        //去判断是否所有的商品都被选中
        //B.底部有一个全选按钮的逻辑,也要判断,不过范围更大, 是所有的商品是否被选中
        boolean allProductsSelected = mMyAdapter.isAllProductsSelected();
        //把这个值设置给checkBox
        cb_cart_all_select.setChecked(allProductsSelected);

        //计算总计
        //B.计算所有商品总的价格
        float totalPrice = mMyAdapter.calculateTotalPrice();
        tv_cart_total_price.setText("总计"+totalPrice);

        //计算总数量
        //B.计算商品总的数量
        int totalNumber = mMyAdapter.calculateTotalNumber();
        btn_cart_pay.setText("去结算("+totalNumber+")");

    }



}
