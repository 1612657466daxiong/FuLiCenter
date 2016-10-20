package cn.ucai.fulicenter.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mcontext;
    ArrayList<CategoryGroupBean> mgrouplist;
    ArrayList<ArrayList<CategoryChildBean>> mchildlist;


    public CategoryAdapter(Context mcontext, ArrayList<CategoryGroupBean> mgrouplist, ArrayList<ArrayList<CategoryChildBean>> mchildlist) {
        this.mcontext = mcontext;
        this.mgrouplist = mgrouplist;
        this.mchildlist = mchildlist;
    }

    @Override
    public int getGroupCount() {
        return mgrouplist != null ? mgrouplist.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mchildlist != null && mchildlist.get(groupPosition) != null ? mchildlist.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mgrouplist != null ? mgrouplist.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mgrouplist != null && mchildlist != null ? mchildlist.get(groupPosition).get(childPosition) : null;
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

    public void initdata(ArrayList<CategoryGroupBean> grouplist, ArrayList<ArrayList<CategoryChildBean>> childlist) {
        mgrouplist.clear();
        mgrouplist.addAll(grouplist);
        mchildlist.clear();
        mchildlist.addAll(childlist);
        notifyDataSetChanged();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.group_view_layout, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        CategoryGroupBean categoryGroupBean = getGroup(groupPosition);
        if (categoryGroupBean != null) {
            holder.tvGroupKind.setText(categoryGroupBean.getName());
            holder.ivGroupDownorup.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
            ImageLoader.downloadImg(mcontext, holder.ivGroup, categoryGroupBean.getImageUrl());
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.child_view_layout, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        CategoryChildBean categoryChildBean = getChild(groupPosition, childPosition);

        if (categoryChildBean != null) {
            holder.tvChild.setText(categoryChildBean.getName());
            ImageLoader.downloadImg(mcontext, holder.ivChild, categoryChildBean.getImageUrl());
            final int id = categoryChildBean.getId();
            Log.i("main",id+"============================");
            final String name =  mgrouplist.get(groupPosition).getName();
            Log.i("main",name+"============================");

           holder.childRe.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                MFGT.gotoCategory2Activity(mcontext,name,id);
               }
           });
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    static class GroupViewHolder {
        @Bind(R.id.iv_group)
        ImageView ivGroup;
        @Bind(R.id.tv_group_kind)
        TextView tvGroupKind;
        @Bind(R.id.iv_group_downorup)
        ImageView ivGroupDownorup;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @Bind(R.id.child_Re)
        RelativeLayout childRe;
        @Bind(R.id.iv_child)
        ImageView ivChild;
        @Bind(R.id.tv_child)
        TextView tvChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }

}
