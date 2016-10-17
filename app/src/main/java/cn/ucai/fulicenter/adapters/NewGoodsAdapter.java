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
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)== I.TYPE_FOOTER){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText("没有更多数据加载");
            return;
        }
        NewGoodsViewHolder newGoodsViewHolder = (NewGoodsViewHolder) holder;
        NewGoodsBean newgood = mgoodlist.get(position);
        newGoodsViewHolder.tv_goodsname.setText(newgood.getGoodsName());
        newGoodsViewHolder.tvPrice.setText(newgood.getCurrencyPrice());
        ImageLoader.build(I.SERVER_ROOT+I.REQUEST_DOWNLOAD_IMAGE)
                .addParam(I.IMAGE_URL,newgood.getGoodsImg())
                .imageView(newGoodsViewHolder.imNewgoods)
                .width(195)
                .height(300)
                .defaultPicture(R.drawable.nopic)
                .listener(parent)
                .showImage(mcontext);

    }

    @Override
    public int getItemCount() {
        return mgoodlist == null ? 1 : mgoodlist.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_footer)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
