package liu.zhan.jun.simplerequest.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 刘展俊 on 2016/6/7.
 */
public  class BaseViewHolder extends RecyclerView.ViewHolder implements /*View.OnClickListener,*/View.OnTouchListener,GestureDetector.OnGestureListener{

    private OnRecyclerItemClick onRecyclerItemClick;

    private GestureDetector gestureDetector;

    private View touchView;
    private int clickIndex=0;
    private ChooseListener chooseListener;

    public BaseViewHolder(View itemView, OnRecyclerItemClick onRecyclerItemClick, Context mContext) {
        super(itemView);
        this.onRecyclerItemClick=onRecyclerItemClick;
//        itemView.setOnClickListener(this);
        //这里的itemView设置touch的话，如果是竖直方向排列的item，宽度是wrap的话可能会导致itemVIew的宽度
        //不一样，所以这样做可能会有问题
        itemView.setOnTouchListener(this);
        gestureDetector=new GestureDetector(mContext,this);
    }

    public int getClickIndex() {
        return clickIndex;
    }

    public void setChooseListener(ChooseListener listener) {
        this.chooseListener = listener;
    }
    //    @Override
//    public void onClick(View v) {
//        if (onRecyclerItemClick != null) {
//            onRecyclerItemClick.onClick(v, this.getAdapterPosition());
//        }
//
//
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touchView=v;
        gestureDetector.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (onRecyclerItemClick != null) {
            onRecyclerItemClick.onClick(touchView, this.getAdapterPosition());
            clickIndex=this.getAdapterPosition();
            if (chooseListener!=null){
                chooseListener.update(clickIndex);
            }
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (onRecyclerItemClick != null) {
            onRecyclerItemClick.LongPress(touchView, this.getAdapterPosition());
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public interface ChooseListener{
        public void update(int position);
    }
}
