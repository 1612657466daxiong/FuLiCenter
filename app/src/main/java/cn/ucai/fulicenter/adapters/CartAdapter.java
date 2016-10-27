package cn.ucai.fulicenter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CartAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<CartBean> mCartlist;
    ArrayList<GoodsDetailsBean> mGoodslist;

    public CartAdapter(ArrayList<CartBean> mCartbean, Context context) {
        this.mCartlist = mCartbean;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(context, R.layout.item_cart_goods, null);
        CartViewHolder viewHolder = new CartViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartViewHolder viewHolder = (CartViewHolder) holder;
        CartBean cart = mCartlist.get(position);
        int count = cart.getCount();
        viewHolder.tvCartCount.setText(String.valueOf(count));
        viewHolder.tvCartName.setText(cart.getGoods().getGoodsName());
        ImageLoader.downloadImg(context,viewHolder.ivCartGood,cart.getGoods().getGoodsThumb());
    }

    @Override
    public int getItemCount() {
        return mCartlist != null ? mCartlist.size() : 0;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.cart_ischeck)
        RadioButton ischecked;
        @Bind(R.id.iv_cart_good)
        ImageView ivCartGood;
        @Bind(R.id.tv_cart_name)
        TextView tvCartName;
        @Bind(R.id.add_cart)
        ImageView changeCart;
        @Bind(R.id.tv_cart_count)
        TextView tvCartCount;
        @Bind(R.id.del_cart)
        ImageView delCart;
        @Bind(R.id.tv_item_price)
        TextView tvItemPrice;



        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
