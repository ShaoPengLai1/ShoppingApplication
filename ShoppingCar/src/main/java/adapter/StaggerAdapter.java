package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.shoppingcar.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import app.MyApp;
import bean.Goods;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class StaggerAdapter extends RecyclerView.Adapter<StaggerAdapter.MyViewHolder> {


    private List<Goods.DataBean> list;
   public StaggerAdapter(List<Goods.DataBean> list){
       this.list=list;
   }

    /*public void addItem(Goods goods) {
        if (goods != null)
            mDatas.add(goods);
    }*/

    //② 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ((MyViewHolder )holder).title.setText(list.get(position).getTitle());
        //加载图片ImageView一定要加上android:adjustViewBounds="true"
        String imageurl = "https" + list.get(position).getImages().split("https")[1];
        Log.i("dt", "imageUrl: " + imageurl);
        imageurl = imageurl.substring(0, imageurl.lastIndexOf(".jpg") + ".jpg".length());
        ImageLoader.getInstance().displayImage(imageurl,((MyViewHolder) holder).avatar, MyApp.getOptions());
       // Glide.with(context).load(user.getAvatar()).into(holder.avatar);
//        holder.avatar.setImageResource(user.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_stagger_item, parent, false);
        return new MyViewHolder(view);
    }

    //③ 创建ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView title;
        public final ImageView avatar;

        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            avatar = v.findViewById(R.id.avatar);
        }
    }
}
