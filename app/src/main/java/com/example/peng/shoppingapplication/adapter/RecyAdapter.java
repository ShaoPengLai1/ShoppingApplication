package com.example.peng.shoppingapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.peng.shoppingapplication.CustomJiaJian;
import com.example.peng.shoppingapplication.R;
import com.example.peng.shoppingapplication.bean.CartBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> {
    private Context context;
    private List<CartBean.DataBean.ListBean> list;
    private Map<String,String> map=new HashMap<>();

    public RecyAdapter(Context context) {
        this.context = context;
    }
    public void add(CartBean cartBean){
        if (list==null){
            list=new ArrayList<>();
        }
        for (CartBean.DataBean shop:cartBean.getData()){
            map.put(shop.getSellerid(),shop.getSellerName());
            for (int i=0;i<shop.getList().size();i++){
                list.add(shop.getList().get(i));
            }
        }
        setFirst(list);
        notifyDataSetChanged();

    }

    private void setFirst(List<CartBean.DataBean.ListBean> list) {
        if (list.size()>0){
            list.get(0).setIsFirst(1);
            for (int i=1;i<list.size();i++){
                if (list.get(i).getSellerid()==list.get(i-1).getSellerid()){
                    list.get(i).setIsFirst(2);
                }else {
                    list.get(i).setIsFirst(1);
                    if (list.get(i).isItem_check()==true){
                        list.get(i).setShop_check(list.get(i).isItem_check());
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO 引入布局
        View view=LayoutInflater.from(context).inflate(R.layout.recy_cart_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        if (list.get(i).getIsFirst()==1){
            holder.shop_checkbox.setVisibility(View.VISIBLE);
            holder.shop_name.setVisibility(View.VISIBLE);
            holder.shop_checkbox.setChecked(list.get(i).isShop_check());
            holder.shop_name.setText(map.get(String.valueOf(list.get(i).getSellerid())));
        }else {
            holder.shop_name.setVisibility(View.GONE);
            holder.shop_checkbox.setVisibility(View.GONE);
        }
        String[] split = list.get(i).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.item_face);
        holder.item_checkbox.setChecked(list.get(i).isItem_check());
        holder.item_name.setText(list.get(i).getTitle());
        holder.item_price.setText(list.get(i).getPrice()+"");
        holder.customJiaJian.setEditText(list.get(i).getNum());
        holder.shop_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).setShop_check(holder.shop_checkbox.isChecked());

                    if (list.get(i).getSellerid()==list.get(i).getSellerid()){
                        list.get(i).setItem_check(holder.shop_checkbox.isChecked());
                    }

                notifyDataSetChanged();
                sum(list);
            }
        });
        holder.item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).setItem_check(holder.item_checkbox.isChecked());
                for (int i=0;i<list.size();i++){
                    for (int j=0;j<list.size();j++){
                        if (list.get(i).getSellerid()==list.get(j).getSellerid()&&!list.get(j).isItem_check()){
                            list.get(i).setShop_check(false);
                            break;
                        }else {
                            list.get(i).setShop_check(true);
                        }
                    }
                }
                notifyDataSetChanged();
                sum(list);
            }
        });
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(i);
                setFirst(list);
                sum(list);
                notifyDataSetChanged();
            }
        });
        holder.customJiaJian.setCustomListener(new CustomJiaJian.CustomListener() {
            @Override
            public void jiajian(int count) {
                list.get(i).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }

            @Override
            public void shuRuZhi(int count) {
                list.get(i).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null){
                    clickListener.onItemClick(v,i);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longItemClickListener!=null){
                    longItemClickListener.onItemLongClick(v,i);
                }
                return true;
            }
        });
    }

    private void sum(List<CartBean.DataBean.ListBean> list) {
        int totalNum=0;
        float totalMoney=0.0f;
        boolean allCheck=true;
        for (int i=0;i<list.size();i++){
            if (list.get(i).isItem_check()){
                totalNum+=list.get(i).getNum();
                totalMoney+=list.get(i).getNum()*list.get(i).getPrice();
            }else {
                allCheck=false;
            }
        }
        updateListener.setTotal(totalMoney+"",totalNum+"",allCheck);
    }
    public void quanxuan(boolean checked){
        for (int i=0;i<list.size();i++){
            list.get(i).setShop_check(checked);
            list.get(i).setItem_check(checked);
        }
        notifyDataSetChanged();
        sum(list);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox shop_checkbox;
        private final TextView shop_name;
        private final CheckBox item_checkbox;
        private final TextView item_name;
        private final TextView item_price;
        private final CustomJiaJian customJiaJian;
        private final ImageView item_delete;
        private final ImageView item_face;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_checkbox=itemView.findViewById(R.id.shop_checkbox);
            shop_name=itemView.findViewById(R.id.shop_name);
            item_checkbox=itemView.findViewById(R.id.item_checkbox);
            item_name=itemView.findViewById(R.id.item_name);
            item_price=itemView.findViewById(R.id.item_price);
            customJiaJian=itemView.findViewById(R.id.custom_jiajian);
            item_delete=itemView.findViewById(R.id.item_delete);
            item_face=itemView.findViewById(R.id.item_face);
        }
    }
    UpdateListener updateListener;
    public void setUpdateListener(UpdateListener updateListener){
        this.updateListener = updateListener;
    }
    public interface UpdateListener{
        public void setTotal(String total, String num, boolean allCheck);
    }
    /**
     * 定义条目点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    /**
     * 定义条目长按事件的接口
     */
    public interface OnLongItemClickListener {
        void onItemLongClick(View itemView, int position);
    }
    private OnItemClickListener clickListener;
    private OnLongItemClickListener longItemClickListener;

    /**
     * 点击事件回调
     * @param clickListener
     */
    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 长按事件回调
     * @param longItemClickListener
     */
    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }
}
