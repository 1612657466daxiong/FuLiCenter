package cn.ucai.fulicenter.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildLayoutPosition(view);
        if(parent.getChildPosition(view) != 0)
            outRect.top = space;

        // 设置左右间距
        outRect.set(space / 2, 0, space / 2, 0);

        // 从第二行开始 top = mSpace
//            if (pos >= I.COLUM_NUM) {
        outRect.top = space;
//            } else {
//                outRect.top = 0;
//            }
    }
}
