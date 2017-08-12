package liu.zhan.jun.simplerequest.bean;

import java.util.List;

public class DataBean {
    /**
     * order_id : 12081
     * order_sn : DC0005217030221482500002
     * add_time : 1488462505
     * total_amount : 0.01
     * zhifuhao : 0
     * take_num : 0
     * food_list : [{"order_food_id":"14971","food_num":"1","food_price2":"0.01","food_name":"面面","food_attribute":[{"food_attribute_name":"香菜","food_attribute_price":"0.00"}]}]
     */

    private String order_id;
    private String order_sn;
    private String add_time;
    private String total_amount;
    private String zhifuhao;
    private String take_num;
    public String refuse_reason;
    private List<FoodListBean> food_list;
    private String pay_type;//:"1",支付方式（0现金，1支付宝，2微信，3未支付,4余额，5第四方支付）
    private String refuse;//":"0",0(未进行过退单)，1整单退款，2选择菜品退款

    public String getPay_type() {
        return pay_type;
    }


    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getZhifuhao() {
        return zhifuhao;
    }

    public void setZhifuhao(String zhifuhao) {
        this.zhifuhao = zhifuhao;
    }

    public String getTake_num() {
        return take_num;
    }

    public void setTake_num(String take_num) {
        this.take_num = take_num;
    }

    public List<FoodListBean> getFood_list() {
        return food_list;
    }

    public void setFood_list(List<FoodListBean> food_list) {
        this.food_list = food_list;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "order_id='" + order_id + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", add_time='" + add_time + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", zhifuhao='" + zhifuhao + '\'' +
                ", take_num='" + take_num + '\'' +
                ", food_list=" + food_list +
                '}';
    }
}