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
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NewGoodsAdapter extends RecyclerView.Adapter {
    static final int TYPE_FOOTER = 0;
    static final int TYPE_NEWGOODS = 1;

    Context mcontext;
    ArrayList<NewGoodsBean> mgoodlist;
    @Bind(R.id.im_newgoods)
    ImageView imNewgoods;
    @Bind(R.id.tv_introduction)
    TextView tvIntroduction;
    @Bind(R.id.tv_price)
    TextView tvPrice;


    public NewGoodsAdapter(Context mcontext, ArrayList<NewGoodsBean> mgoodlist) {
        this.mcontext = mcontext;
        this.mgoodlist = mgoodlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        if (viewType == TYPE_FOOTER) {
            view = View.inflate(mcontext, R.layout.footer_layout, null);
            holder = new FooterViewHolder(view);
            return holder;
        } else {
            view = View.inflate(mcontext, R.layout.new_goods_layout, null);
            holder = new NewGoodsViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mgoodlist == null ? 1 : mgoodlist.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NEWGOODS;
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
        @Bind(R.id.tv_introduction)
        TextView tvIntroduction;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        NewGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
