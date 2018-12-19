package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.shoppingcar.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import app.MyApp;
import bean.News;
import custom.MyAddSubView;

/**
 * Created by Administrator on 2018/12/18 0018.
 */

public class MyAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<News.DataBean> mSellerData;

    public MyAdapter(Context context, List<News.DataBean> sellerData) {
        mContext = context;
        mSellerData = sellerData;
    }


    @Override
    public int getGroupCount() {
        return mSellerData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mSellerData.get(groupPosition).getList().size();
    }


    //商家
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        News.DataBean dataBean = mSellerData.get(groupPosition);
        ParentViewHolder parentViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_parent, null);
            parentViewHolder = new ParentViewHolder(convertView);
            convertView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        //设置商家名称
        parentViewHolder.seller_name_tv.setText(dataBean.getSellerName());
        //B.判断当前商家所有商品是否被选中 (一种是所有的都被选中,一种是没有所有的都选中)
        boolean currentSellerAllProductSeleted = isCurrentSellerAllProductSelected(groupPosition);
        parentViewHolder.seller_cb.setChecked(currentSellerAllProductSeleted);

        //设置点击事件
        parentViewHolder.seller_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 当商家的checkBox点击时回调
                 */
                mOnCartListChangeListener.onSellerCheckedChange(groupPosition);
            }
        });
        return convertView;
    }

    //商品
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        News.DataBean dataBean = mSellerData.get(groupPosition);
        List<News.DataBean.ListBean> list = dataBean.getList();
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //设置图片并剪切
        String images = list.get(childPosition).getImages();
        String[] image = images.split("!");
        ImageLoader.getInstance().displayImage(image[0],childViewHolder.product_icon_iv, MyApp.getOptions());
       // Picasso.with(mContext).load(image[0]).into(childViewHolder.product_icon_iv);
        //设置商家名称
        childViewHolder.product_title_name_tv.setText(list.get(childPosition).getTitle());
        //设置单价
        childViewHolder.product_price_tv.setText(list.get(childPosition).getPrice() + "");
        //设置复选框是否选中 1是选中
        childViewHolder.child_cb.setChecked(list.get(childPosition).getSelected() == 1);
        //设置内部的数量
        childViewHolder.add_remove_view.setNumber(list.get(childPosition).getNum());

        childViewHolder.child_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 当点击子条目商品的CheckBox回调
                 */
                mOnCartListChangeListener.onProductCheckedChange(groupPosition,childPosition);
            }
        });
        childViewHolder.add_remove_view.setOnNumberChangeListener(new MyAddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int num) {
                mOnCartListChangeListener.onProducNumberChange(groupPosition,childPosition,num);
            }
        });
        return convertView;
    }
    ////////////////////////B.写一些购物车的特有的方法//////////////////////////
    /**
     * B.判断当前商家所有商品是否被选中
     * (一种是所有的都被选中,一种是没有所有的都选中)
     */
    public boolean isCurrentSellerAllProductSelected(int groupPosition) {
        //拿到对应组的数据
        News.DataBean dataBean = mSellerData.get(groupPosition);
        //拿到商家的所有商品数据,是一个集合
        List<News.DataBean.ListBean> list = dataBean.getList();

        for (News.DataBean.ListBean listBean : list) {
            //判断这个组的所有商品是否被选中,如果有一个没有被选中,那么商家就是未选中状态
            if (listBean.getSelected() == 0) {
                return false;
            }
        }
        return true;
    }
    /**
     * B.底部有一个全选按钮的逻辑,也要判断,不过范围更大,
     * 是所有的商品是否被选中
     * (一种是所有的都被选中,一种是没有所有的都选中)
     */
    public boolean isAllProductsSelected(){
        for (int i = 0; i < mSellerData.size(); i++) {
            News.DataBean dataBean = mSellerData.get(i);
            List<News.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getSelected() == 0){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * B.计算商品总的数量
     */
    public int calculateTotalNumber(){
        int totalNumber = 0;
        for (int i = 0; i < mSellerData.size(); i++) {
            News.DataBean dataBean = mSellerData.get(i);
            List<News.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                //商品数量只算选中
                if (list.get(j).getSelected() == 1){
                    //拿到了商品的数量
                    int num = list.get(j).getNum();
                    totalNumber +=num;
                }
            }
        }
        return totalNumber;
    }
    /**
     * B.计算所有商品总的价格
     */
    public float calculateTotalPrice(){
        float totalPrice = 0;
        for (int i = 0; i < mSellerData.size(); i++) {
            News.DataBean dataBean = mSellerData.get(i);
            List<News.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                //商品价格只算选中
                if (list.get(j).getSelected() == 1){
                    //拿到商品的价格
                    float price = (float) list.get(j).getPrice();
                    //拿到了商品的数量
                    int num = list.get(j).getNum();
                    totalPrice +=price * num;
                }
            }
        }
        return totalPrice;
    }

    /////////////////////////////////////C.根据用户的选择,改变选框里的状态///////////////////////////////////
    /**
     * C.当商家的全选框点击时,更新所有商品全选的状态
     */
    public void changeCurrentSellerAllProductsStatus(int groupPosition , boolean isSelected){
        News.DataBean dataBean = mSellerData.get(groupPosition);
        List<News.DataBean.ListBean> list = dataBean.getList();
        for (int i = 0; i < list.size(); i++) {
            News.DataBean.ListBean listBean = list.get(i);
            listBean.setSelected(isSelected ? 1 : 0);
        }
    }
    /**
     * C.当商品的全选框选中时,更新商家选框的状态
     */
    public void changeCurrentProductStatus(int groupPosition , int childPositon){
        News.DataBean dataBean = mSellerData.get(groupPosition);
        List<News.DataBean.ListBean> list = dataBean.getList();
        News.DataBean.ListBean listBean = list.get(childPositon);
        listBean.setSelected(listBean.getSelected() == 0 ? 1 : 0);
    }
    /**
     * C.当最底部的全选框选中时,更新所有商品全选框的状态
     */
    public void changeAllProductStatus(boolean isSelected){
        for (int i = 0; i < mSellerData.size(); i++) {
            News.DataBean dataBean = mSellerData.get(i);
            List<News.DataBean.ListBean> list = dataBean.getList();
            for (int j = 0; j < list.size(); j++) {
                list.get(j).setSelected(isSelected ? 1 : 0);
            }
        }
    }
    /**
     * C.当加减器被点击时,改变里面当前商品的数量
     * 参数1定位那个商家
     * 参数2定位哪个商品
     * 参数3定位改变具体的数量是多少
     */
    public void changeCurrentProductNumber(int groupPosition,int childPositon,int number){
        News.DataBean dataBean = mSellerData.get(groupPosition);
        List<News.DataBean.ListBean> list = dataBean.getList();
        News.DataBean.ListBean listBean = list.get(childPositon);
        listBean.setNum(number);
    }

    /////////////////////////D.定义接口的固定形式////////////////////

    public interface onCartListChangeListener{

        /**
         *当商家的checkBox点击时回调
         */
        void onSellerCheckedChange(int groupPosition);

        /**
         * 当点击子条目商品的CheckBox回调
         */
        void onProductCheckedChange(int groupPosition ,int childPosition);

        /**
         * 当点击加减按钮的回调
         */
        void onProducNumberChange(int groupPosition , int childPosition , int number);

    }
    //D.
    onCartListChangeListener mOnCartListChangeListener;
    //D.设置一个监听加减按钮,商家的复选框,子条目的复选框
    public void setOnCartListChangeListener(onCartListChangeListener onCartListChangeListener){
        mOnCartListChangeListener = onCartListChangeListener ;
    }

    /**
     *一些没有用的数据
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public static class ParentViewHolder {
        public View rootView;
        public CheckBox seller_cb;
        public TextView seller_name_tv;

        public ParentViewHolder(View rootView) {
            this.rootView = rootView;
            this.seller_cb = (CheckBox) rootView.findViewById(R.id.seller_cb);
            this.seller_name_tv = (TextView) rootView.findViewById(R.id.seller_name_tv);
        }

    }

    public static class ChildViewHolder {
        public View rootView;
        public CheckBox child_cb;
        public ImageView product_icon_iv;
        public TextView product_title_name_tv;
        public TextView product_price_tv;
        public MyAddSubView add_remove_view;

        public ChildViewHolder(View rootView) {
            this.rootView = rootView;
            this.child_cb = (CheckBox) rootView.findViewById(R.id.child_cb);
            this.product_icon_iv = (ImageView) rootView.findViewById(R.id.product_icon_iv);
            this.product_title_name_tv = (TextView) rootView.findViewById(R.id.product_title_name_tv);
            this.product_price_tv = (TextView) rootView.findViewById(R.id.product_price_tv);
            this.add_remove_view = (MyAddSubView) rootView.findViewById(R.id.add_remove_view);
        }

    }
}


