package com.example.peng.shoppingapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peng.shoppingapplication.R;
import com.example.peng.shoppingapplication.bean.ShopTypeProductBean;

import java.util.ArrayList;
import java.util.List;

public class ShopTypeProductAdapter extends RecyclerView.Adapter<ShopTypeProductAdapter.MyViewHolder> {
    private Context context;
    private List<ShopTypeProductBean.Data> mList=new ArrayList<>();

    public ShopTypeProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopTypeProductBean.Data> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.shop_type_product_adapter,null);
        ShopTypeProductAdapter.MyViewHolder myViewHolder=new ShopTypeProductAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mName.setText(mList.get(i).getName());
        //TODO
        ShopTypeProductLinearAdapter shopTypeProductLinearAdapter=new ShopTypeProductLinearAdapter(context);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        myViewHolder.mRecyclerView.setLayoutManager(linearLayoutManager);
        myViewHolder.mRecyclerView.setAdapter(shopTypeProductLinearAdapter);
        myViewHolder.mRecyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        shopTypeProductLinearAdapter.setData(mList.get(i).getList());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        RecyclerView mRecyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.tv_shop_type_product_name);
            mRecyclerView=itemView.findViewById(R.id.recyclerview_shop_product);
        }
    }
}
