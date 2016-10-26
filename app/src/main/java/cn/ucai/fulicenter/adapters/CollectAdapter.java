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
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/26.
 */
public class CollectAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<CollectBean> mCollectList;

    public CollectAdapter(ArrayList<CollectBean> mCollectList, Context context) {
        this.mCollectList = mCollectList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_collect_goods, null);
        CollectViewHolder viewHolder = new CollectViewHolder(view);


        viewHolder.ivCollectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectBean indext = (CollectBean) v.getTag();
                L.e("获取删除的收藏商品信息"+indext.toString());
                delete2net(indext);
                mCollectList.remove(indext);
                notifyDataSetChanged();
            }
        });
        viewHolder.ivCollectGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectBean indext = (CollectBean) v.getTag();
                MFGT.gotoGoodsDetailsActivity(context,indext.getGoodsId());
            }
        });
        return viewHolder;
    }

    private void delete2net(CollectBean indext) {
        GoodsDao.deletecollectbyid(context, indext.getUserName(), String.valueOf(indext.getGoodsId()), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
              if (result!=null){
                  if (result.isSuccess()){
                      CommonUtils.showShortToast(R.string.deletecollectsuccess);
                  }else {
                      CommonUtils.showShortToast(R.string.deletecollectfail);
                  }
              }else {
                  CommonUtils.showShortToast(R.string.deletecollectexception);
              }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CollectBean collect = mCollectList.get(position);
        CollectViewHolder viewHolder = (CollectViewHolder) holder;
        viewHolder.tvCollectName.setText(collect.getGoodsName());
        ImageLoader.downloadImg(context,viewHolder.ivCollectGoods,collect.getGoodsThumb());
        viewHolder.itemView.setTag(collect);
    }

    @Override
    public int getItemCount() {
        return mCollectList != null ? mCollectList.size() : 0;
    }

    static class CollectViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_collect_goods)
        ImageView ivCollectGoods;
        @Bind(R.id.tv_collect_name)
        TextView tvCollectName;
        @Bind(R.id.iv_collect_delete)
        ImageView ivCollectDelete;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
