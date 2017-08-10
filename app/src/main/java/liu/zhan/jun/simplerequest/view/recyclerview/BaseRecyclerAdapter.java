package liu.zhan.jun.simplerequest.view.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 刘展俊 on 2016/6/7.
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter{
    protected OnRecyclerItemClick onRecyclerItemClick;

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }


}
