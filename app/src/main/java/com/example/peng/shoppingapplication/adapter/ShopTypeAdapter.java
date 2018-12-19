package com.example.peng.shoppingapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peng.shoppingapplication.R;
import com.example.peng.shoppingapplication.bean.ShopTypeBean;

import java.util.ArrayList;
import java.util.List;

public class ShopTypeAdapter extends RecyclerView.Adapter<ShopTypeAdapter.MyViewHolder> {

    private Context context;
    private List<ShopTypeBean.Data> mList=new ArrayList<>();

    public ShopTypeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopTypeBean.Data> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopTypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //TODO
        View view=View.inflate(context,R.layout.shop_type_adapter,null);
        ShopTypeAdapter.MyViewHolder myViewHolder=new ShopTypeAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopTypeAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.mName.setText(mList.get(i).getName());
        myViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.onClick(i,mList.get(i).getCid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLinearLayout;
        private TextView mName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.tv_shop_type_name);
            mLinearLayout=itemView.findViewById(R.id.ll_shop_type);
        }
    }
    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(int position, String cid);
    }
}
