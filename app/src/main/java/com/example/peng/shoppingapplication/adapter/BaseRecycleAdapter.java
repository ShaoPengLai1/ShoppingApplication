package com.example.peng.shoppingapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peng.shoppingapplication.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRecycleAdapter <T> extends RecyclerView.Adapter<BaseRecycleAdapter.BaseViewHolder> {
    protected List<T> datas;
    private Context mContext;

    public BaseRecycleAdapter(List<T> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }
    //头部控件
    private View mHeaderView;
    //底部控件
    private View mFooterView;
    // item 的三种类型
    // 正常的item类型
    public static final int ITEM_TYPE_NORMAL = 0X1111;
    // header
    public static final int ITEM_TYPE_HEADER = 0X1112;
    // footer
    public static final int ITEM_TYPE_FOOTER = 0X1113;

    private boolean isHasHeader=false;
    private boolean isHasFooter=false;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_TYPE_FOOTER){
            return new BaseViewHolder(mFooterView);
        }
        if (viewType==ITEM_TYPE_HEADER){
            return new BaseViewHolder(mHeaderView);
        }
        View view=LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),parent,false);
        return new BaseViewHolder(view);
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(BaseRecycleAdapter.BaseViewHolder holder, int position) {
        if (isHasHeader&&isHasFooter){
            // 有头布局和底部时，向前便宜一个，且最后一个不能绑定数据
            if (position==0||position==datas.size()+1){
                return;
            }
            bindData(holder,position-1);
        }
        if (position!=0&&isHasHeader&&!isHasFooter){
            // 有顶部，但没有底部
            bindData(holder,position-1);
        }
        if(!isHasHeader&&isHasFooter){
            // 没有顶部，但有底部
            if(position==datas.size()){
                return;
            }
            bindData(holder,position);
        }
        if(!isHasHeader&&!isHasFooter){
            // 没有顶部，没有底部
            bindData(holder,position);
        }

    }
    /**
     * 添加头部视图
     * @param header
     */
    public void setHeaderView(View header){
        this.mHeaderView = header;
        isHasHeader = true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 根据索引获取当前View的类型，以达到复用的目的

        // 根据位置的索引，获取当前position的类型
        if (isHasHeader&&position==0){
            return ITEM_TYPE_HEADER;
        }
        if (isHasHeader&&isHasFooter&&position==datas.size()+1){
            // 有头部和底部时，最后底部的应该等于size+!
            return ITEM_TYPE_FOOTER;
        }else if (!isHasHeader&&isHasFooter&&position==datas.size()){
            // 没有头部，有底部，底部索引为size
            return ITEM_TYPE_FOOTER;
        }
        return ITEM_TYPE_NORMAL;
    }

    /**
     * 添加底部视图
     * @param footer
     */
    public void setFooterView(View footer){
        this.mFooterView = footer;
        isHasFooter = true;
        notifyDataSetChanged();
    }
    /**
     * 刷新数据
     * @param datas
     */
    public void refresh(List<T> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     *      刷新数据
     * @param data
     */
    public   void updata(List<T> data){
        this.datas=data;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param datas
     */
    public void addData(List<T> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }
    /**
     * 移除数据
     *
     * @param position
     */
    public void remove(int position) {
        if (position >= 0 && position < datas.size()) {
            datas.remove(position);
            notifyDataSetChanged();
        }
    }

    protected abstract void bindData(BaseViewHolder holder, int i);

    @Override
    public int getItemCount() {
        int size=datas.size();
        if (isHasFooter){
            size++;
        }
        if (isHasHeader){
            size++;
        }
        return size;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private Map<Integer,View> mViewMap;
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            mViewMap=new HashMap<>();
        }

        public View getView(int id){
            View view=mViewMap.get(id);
            if (view==null){
                view=itemView.findViewById(id);
                mViewMap.put(id,view);
            }
            return view;
        }
    }
    public void setItemText(View view,String text){
        if(view instanceof TextView){
            ((TextView) view).setText(text);
        }
    }


    public RvItemOnclickListener getRvItemOnclickListener() {
        return mRvItemOnclickListener;
    }

    public void setRvItemOnclickListener(RvItemOnclickListener rvItemOnclickListener) {
        mRvItemOnclickListener = rvItemOnclickListener;
    }

    protected   RvItemOnclickListener mRvItemOnclickListener;


    public interface  RvItemOnclickListener{

        void RvItemOnclick(int position);
    }
}
