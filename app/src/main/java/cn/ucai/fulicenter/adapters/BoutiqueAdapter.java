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

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * Created by Administrator on 2016/10/19.
 */
public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mcontext;
    ArrayList<BoutiqueBean> mlist;


    public BoutiqueAdapter(Context mcontext, ArrayList<BoutiqueBean> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }
    public void initData(ArrayList<BoutiqueBean> list){
        if (mlist!=null){
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void  addData(ArrayList<BoutiqueBean> list){
        mlist.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder
                = new BoutiqueHolder(LayoutInflater.from(mcontext).inflate(R.layout.boutique_layout,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            BoutiqueBean boutique = mlist.get(position);
            BoutiqueHolder boutiqueholder = (BoutiqueHolder) holder;
            boutiqueholder.boutiqueDes.setText(boutique.getDescription());
            boutiqueholder.boutiqueName.setText(boutique.getName());
            boutiqueholder.boutiqueTitle.setText(boutique.getTitle());
            ImageLoader.downloadImg(mcontext,boutiqueholder.ivBoutiqueGoods,boutique.getImageurl());

    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
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
