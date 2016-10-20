package cn.ucai.fulicenter.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activitys.MainActivity;
import cn.ucai.fulicenter.adapters.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.net.GoodsDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {


    @Bind(R.id.eplv_category)
    ExpandableListView meplvCategory;

    MainActivity mcontext;
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> mgrouplist;
    ArrayList<ArrayList<CategoryChildBean>> mchildlist;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {
        mcontext= (MainActivity) getContext();
        mgrouplist = new ArrayList<>();
        mchildlist= new ArrayList<ArrayList<CategoryChildBean>>();
        mAdapter=new CategoryAdapter(mcontext,mgrouplist,mchildlist);
        meplvCategory.setGroupIndicator(null);
        meplvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        downlaodGroupData();
    }
    public int groupcount=0;
    private void downlaodGroupData() {
        GoodsDao.downloadGroupList(mcontext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {

            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result!=null){
                    groupcount++;
                    ArrayList<CategoryGroupBean> categoryGroupBeen = ConvertUtils.array2List(result);
                    mgrouplist.addAll(categoryGroupBeen);
                    for (int i =0;i<categoryGroupBeen.size();i++){
                        mchildlist.add(i,new ArrayList<CategoryChildBean>());
                        downloadChildData(categoryGroupBeen.get(i).getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void downloadChildData(int id, final int index) {
        GoodsDao.downloadChildList(mcontext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                if (result!=null){
                    ArrayList<CategoryChildBean> categoryChildBeen = ConvertUtils.array2List(result);
                    mchildlist.set(index,categoryChildBeen);
                    if (groupcount==mgrouplist.size()){
                        mAdapter.initdata(mgrouplist,mchildlist);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
