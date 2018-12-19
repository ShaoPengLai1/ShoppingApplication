package com.example.peng.shoppingapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.peng.shoppingapplication.R;
import com.example.peng.shoppingapplication.bean.ShopTypeProductBean;

import java.util.ArrayList;
import java.util.List;

public class ShopTypeProductLinearAdapter extends RecyclerView.Adapter<ShopTypeProductLinearAdapter.MyViewHolder> {
    private Context context;
    private List<ShopTypeProductBean.Data.ProductData> mList=new ArrayList<>();

    public ShopTypeProductLinearAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopTypeProductBean.Data.ProductData> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopTypeProductLinearAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(context,R.layout.shop_type_product_linear_adapter,null);
        ShopTypeProductLinearAdapter.MyViewHolder myViewHolder=new ShopTypeProductLinearAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopTypeProductLinearAdapter.MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(mList.get(i).getIcon()).into(myViewHolder.mImage);
        myViewHolder.mName.setText(mList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private ImageView mImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.tv_shop_type_product_linear);
            mImage=itemView.findViewById(R.id.iv_shop_type_product_linear);
        }
    }
}
