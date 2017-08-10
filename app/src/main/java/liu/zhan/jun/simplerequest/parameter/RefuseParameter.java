package liu.zhan.jun.simplerequest.parameter;

import java.util.List;

import liu.zhan.jun.simplerequest.bean.FoodListBean;

/**
 * Created by yunniu on 2017/8/10.退单接口参数
 */

public class RefuseParameter extends BaseParameter{
    public String order_sn;
    public String refuse_type;
    public String refuse_food_info;
    public String order_id ;//
    public String refuse_reason;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getRefuse_type() {
        return refuse_type;
    }

    public void setRefuse_type(String refuse_type) {
        this.refuse_type = refuse_type;
    }

    public String getRefuse_food_info() {
        return refuse_food_info;
    }

    public void setRefuse_food_info(String refuse_food_info) {
        this.refuse_food_info = refuse_food_info;
    }

    @Override
    public String toString() {
        return "RefuseParameter{" +
                "order_sn='" + order_sn + '\'' +
                ", refuse_type='" + refuse_type + '\'' +
                ", refuse_food_info='" + refuse_food_info + '\'' +
                ", order_id='" + order_id + '\'' +
                ", refuse_reason='" + refuse_reason + '\'' +
                '}';
    }
}
