package com.example.peng.shoppingapplication.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.peng.shoppingapplication.R;

import java.util.List;

public class SeachRecordAdapter extends BaseRecycleAdapter<String> {
    public SeachRecordAdapter(List<String> datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_item;
    }

    @Override
    protected void bindData(final BaseRecycleAdapter.BaseViewHolder holder, final int i) {
        TextView textView= (TextView) holder.getView(R.id.tv_record);
        textView.setText(datas.get(i));
        holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=mRvItemOnclickListener){
                    mRvItemOnclickListener.RvItemOnclick(i);
                }
            }
        });
    }
}
