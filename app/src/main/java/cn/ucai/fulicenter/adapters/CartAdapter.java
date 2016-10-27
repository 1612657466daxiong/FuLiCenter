package cn.ucai.fulicenter.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/27.
 */
public class CartAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<CartBean> mCartlist;


    public CartAdapter(ArrayList<CartBean> mCartbean, Context context) {
        this.mCartlist = mCartbean;
        this.context = context;
    }
    public void initData(ArrayList<CartBean> list){
        this.mCartlist=list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = View.inflate(context, R.layout.item_cart_goods, null);
        CartViewHolder viewHolder = new CartViewHolder(layout);
        viewHolder.changeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                CartBean cartBean = mCartlist.get(position);
                int id = cartBean.getId();
                int count = cartBean.getCount();
                updatecount(id,count+1,position);
                mCartlist.get(position).setCount(count+1);
                notifyDataSetChanged();
            }
        });
        viewHolder.delCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                CartBean cartBean = mCartlist.get(position);
                int id = cartBean.getId();
                int count = cartBean.getCount();
                updatecount(id,count-1,position);
                mCartlist.get(position).setCount(count-1);
                notifyDataSetChanged();
            }
        });
      viewHolder.ischecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (isChecked){
                  int tag = (int) buttonView.getTag();
                  context.sendBroadcast(new Intent("updatecount"));
                  mCartlist.get(tag).setChecked(isChecked);
              }else {
                  int tag = (int) buttonView.getTag();
                  context.sendBroadcast(new Intent("updatecount"));
                  mCartlist.get(tag).setChecked(isChecked);
              }

          }
      });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartViewHolder viewHolder = (CartViewHolder) holder;
        CartBean cart = mCartlist.get(position);
        int count = cart.getCount();
        viewHolder.tvCartCount.setText(String.valueOf(count));
        viewHolder.tvCartName.setText(cart.getGoods().getGoodsName());
        viewHolder.tvItemPrice.setText(cart.getGoods().getCurrencyPrice());
        ImageLoader.downloadImg(context,viewHolder.ivCartGood,cart.getGoods().getGoodsThumb());
        viewHolder.changeCart.setTag(position);
        viewHolder.delCart.setTag(position);
        viewHolder.ischecked.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mCartlist != null ? mCartlist.size() : 0;
    }

    public synchronized void updatecount(int cartid, final int count, final int position){
        if (count==0){
            deleteCartbydel(cartid, position);
        }else {
            changecount(cartid, count);
        }
    }

    private void deleteCartbydel(int cartid, final int position) {
        GoodsDao.deletecart(context, cartid, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result!=null){
                    if (result.isSuccess()){
                        mCartlist.remove(position);
                        notifyDataSetChanged();
                        CommonUtils.showShortToast(R.string.deletecartsuccess);
                    }else {
                        CommonUtils.showShortToast(R.string.deletecartfail);
                    }
                }
            }
            @Override
            public void onError(String error) {

            }
        });
    }

    private void changecount(int cartid, final int count) {
        GoodsDao.updatecart(context, cartid, count, new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result!=null){
                    if (result.isSuccess()){
                        context.sendBroadcast(new Intent("updatecount"));
                        CommonUtils.showShortToast(R.string.updatecartcount);
                    }else {
                        CommonUtils.showShortToast(R.string.updatecartcountfail);
                    }
                }
            }
            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }

    static class CartViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.cart_ischeck)
        CheckBox ischecked;
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
