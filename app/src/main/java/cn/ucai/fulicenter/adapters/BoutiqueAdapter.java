package cn.ucai.fulicenter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mcontext;
    ArrayList<BoutiqueBean> mlist;


    public void setFooterText(String footerText) {
        FooterText = footerText;
    }

    String FooterText;
    public BoutiqueAdapter(Context mcontext, ArrayList<BoutiqueBean> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new NewGoodsAdapter.FooterViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.footer_layout, parent, false));
        } else {
            holder= new BoutiqueHolder(LayoutInflater.from(mcontext).inflate(R.layout.boutique_layout,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewGoodsAdapter.FooterViewHolder){
            NewGoodsAdapter.FooterViewHolder footerViewHolder = (NewGoodsAdapter.FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(FooterText);
            return;
        }
        if (holder instanceof  BoutiqueHolder){
            BoutiqueBean boutique = mlist.get(position);
            BoutiqueHolder boutiqueholder = (BoutiqueHolder) holder;
            boutiqueholder.boutiqueDes.setText(boutique.getDescription());
            boutiqueholder.boutiqueName.setText(boutique.getName());
            boutiqueholder.boutiqueTitle.setText(boutique.getTitle());
            ImageLoader.downloadImg(mcontext,boutiqueholder.ivBoutiqueGoods,boutique.getImageurl());
        }
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 1 : mlist.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }

    }

    static class BoutiqueHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_boutique_goods)
        ImageView ivBoutiqueGoods;
        @Bind(R.id.boutique_title)
        TextView boutiqueTitle;
        @Bind(R.id.boutique_des)
        TextView boutiqueDes;
        @Bind(R.id.boutique_name)
        TextView boutiqueName;

        BoutiqueHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
