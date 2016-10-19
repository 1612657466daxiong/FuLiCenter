package cn.ucai.fulicenter.adapters;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NewGoodsAdapter extends RecyclerView.Adapter {


    Context mcontext;
    ArrayList<NewGoodsBean> mgoodlist;
    @Bind(R.id.im_newgoods)
    ImageView imNewgoods;
    @Bind(R.id.tv_goodsname)
    TextView tv_goodsname;
    @Bind(R.id.tv_price)
    TextView tvPrice;

    RecyclerView parent;

    String FooterText;

    public void setFooterText(String footerText) {
        FooterText = footerText;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    boolean isMore;

    public void setYibu(boolean yibu) {
        this.yibu = yibu;
    }

    boolean yibu = false;

    public void addData(ArrayList<NewGoodsBean> list){
        mgoodlist.addAll(list);
        notifyDataSetChanged();
    }

    public void initData(ArrayList<NewGoodsBean> list){
        if (mgoodlist!=null){
            mgoodlist.clear();
        }
        mgoodlist.addAll(list);
        notifyDataSetChanged();
    }

    public NewGoodsAdapter(Context mcontext, ArrayList<NewGoodsBean> mgoodlist) {
        this.mcontext = mcontext;
        this.mgoodlist = mgoodlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent= (RecyclerView) parent;
        RecyclerView.ViewHolder holder = null;
        View view = null;

        if (viewType == I.TYPE_FOOTER) {
            view = View.inflate(mcontext, R.layout.footer_layout, null);
            holder = new FooterViewHolder(view);
        } else {
            view = View.inflate(mcontext, R.layout.new_goods_layout, null);
            holder = new NewGoodsViewHolder(view);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int goodsid = (int) v.getTag();
                     MFGT.gotoGoodsDetailsActivity(mcontext,goodsid);
                }
            });
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)== I.TYPE_FOOTER){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(FooterText);
            return;
        }
        NewGoodsViewHolder newGoodsViewHolder = (NewGoodsViewHolder) holder;
        NewGoodsBean newgood = mgoodlist.get(position);
        newGoodsViewHolder.tv_goodsname.setText(newgood.getGoodsName());
        newGoodsViewHolder.tvPrice.setText(newgood.getCurrencyPrice());
        ImageLoader.downloadImg(mcontext,newGoodsViewHolder.imNewgoods,newgood.getGoodsThumb());
        newGoodsViewHolder.itemView.setTag(newgood.getGoodsId());

    }

    @Override
    public int getItemCount() {
        return mgoodlist == null ? 1 : mgoodlist.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() -1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }


    static class NewGoodsViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.im_newgoods)
        ImageView imNewgoods;
        @Bind(R.id.tv_goodsname)
        TextView tv_goodsname;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        NewGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
