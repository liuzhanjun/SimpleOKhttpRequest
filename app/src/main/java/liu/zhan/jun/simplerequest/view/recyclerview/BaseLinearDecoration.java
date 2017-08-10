package liu.zhan.jun.simplerequest.view.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by 刘展俊 on 2016/6/11.
 */
public class BaseLinearDecoration extends RecyclerView.ItemDecoration {


    private final int[] attr=new int[]{
            android.R.attr.listDivider,
    };
    private Drawable mDivider;
    private int oratation= LinearLayoutManager.VERTICAL;
    private Context mContext;

    public BaseLinearDecoration(Context context, int oratation) {
        super();
        TypedArray typeArray=context.obtainStyledAttributes(attr);
        mDivider=typeArray.getDrawable(0);
        typeArray.recycle();
        mContext=context;
//        mDivider=context.getResources().getDrawable(R.drawable.itme_divider);
        this.oratation=oratation;

    }

    public void setmDivider(int drawalbeRes) {
        mDivider=mContext.getResources().getDrawable(drawalbeRes);
    }

    public void setOratation(int oratation){
        if (oratation!= LinearLayoutManager.VERTICAL&&oratation!= LinearLayoutManager.HORIZONTAL){
            throw new IllegalArgumentException("设置错误的oratation");
        }

        this.oratation=oratation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (oratation == LinearLayoutManager.VERTICAL) {
            DrawVertical(c, parent);
        }else if(oratation == LinearLayoutManager.HORIZONTAL){
            DrawHorizontal(c, parent);
        }
        super.onDraw(c, parent, state);

    }

    private void DrawHorizontal(Canvas c, RecyclerView parent) {
        int top=parent.getPaddingTop();
        int bottom=parent.getHeight()-parent.getPaddingBottom();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) view.getLayoutParams();
            int left=view.getRight()+params.rightMargin+ Math.round(ViewCompat.getTranslationX(view));
            int right=left+mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    private void DrawVertical(Canvas c, RecyclerView parent) {
        int left=parent.getPaddingLeft();
        int right=parent.getWidth()-parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) view.getLayoutParams();

            int top=view.getBottom()+params.bottomMargin+ Math.round(ViewCompat.getTranslationY(view));
            int bottom=top+mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (oratation== LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }else if(oratation== LinearLayoutManager.HORIZONTAL){
            outRect.set(0, 0, mDivider.getIntrinsicWidth(),0);
        }
    }
}
