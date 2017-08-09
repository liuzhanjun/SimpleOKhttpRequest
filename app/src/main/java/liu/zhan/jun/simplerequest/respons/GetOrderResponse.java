package liu.zhan.jun.simplerequest.respons;

import java.util.List;

import liu.zhan.jun.simplerequest.bean.DataBean;
import liu.zhan.jun.simplerequest.bean.FoodAttributeBean;
import liu.zhan.jun.simplerequest.bean.FoodListBean;

/**
 * Created by yunniu on 2017/8/9.
 */

public class GetOrderResponse {


    /**
     * code : 1
     * msg : 查询成功
     * data : [{"order_id":"12081","order_sn":"DC0005217030221482500002","add_time":"1488462505","total_amount":"0.01","zhifuhao":"0","take_num":"0","food_list":[{"order_food_id":"14971","food_num":"1","food_price2":"0.01","food_name":"面面","food_attribute":[{"food_attribute_name":"香菜","food_attribute_price":"0.00"}]}]}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetOrderResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
