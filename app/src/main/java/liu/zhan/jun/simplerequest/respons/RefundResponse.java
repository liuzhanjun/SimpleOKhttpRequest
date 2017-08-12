package liu.zhan.jun.simplerequest.respons;

import liu.zhan.jun.simplerequest.bean.DataBean;

/**
 * Created by yunniu on 2017/8/11.
 */

public class RefundResponse extends BaseResponse{

    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RefundResponse{" +
                "data=" + data +
                '}';
    }
}
