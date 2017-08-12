package liu.zhan.jun.simplerequest.bean;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import liu.zhan.jun.simplerequest.utils.Entity;

public class FoodListBean extends Entity implements Cloneable {
    /**
     * order_food_id : 14971
     * food_num : 1
     * food_price2 : 0.01
     * food_name : 面面
     * food_attribute : [{"food_attribute_name":"香菜","food_attribute_price":"0.00"}]
     */

    private String order_food_id;
    private String food_num;
    private String food_price2;
    private String food_name;
    private String refuse_num;
    private String printer_ip;//打印机ip
    public String refuse_reason;
    private List<FoodAttributeBean> food_attribute;

    public String getOrder_food_id() {
        return order_food_id;
    }

    public void setOrder_food_id(String order_food_id) {
        this.order_food_id = order_food_id;
    }


    public String getPrinter_ip() {
        return printer_ip;
    }

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public void setPrinter_ip(String printer_ip) {
        this.printer_ip = printer_ip;
    }

    public String getRefuse_num() {
        return refuse_num;
    }

    public void setRefuse_num(String refuse_num) {
        this.refuse_num = refuse_num;
    }

    public String getFood_num() {
        return food_num;
    }

    public void setFood_num(String food_num) {
        this.food_num = food_num;
    }

    public String getFood_price2() {
        return food_price2;
    }

    public void setFood_price2(String food_price2) {
        this.food_price2 = food_price2;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public List<FoodAttributeBean> getFood_attribute() {
        return food_attribute;
    }

    public void setFood_attribute(List<FoodAttributeBean> food_attribute) {
        this.food_attribute = food_attribute;
    }

    @Override
    public String toString() {
        return "FoodListBean{" +
                "order_food_id='" + order_food_id + '\'' +
                ", food_num='" + food_num + '\'' +
                ", food_price2='" + food_price2 + '\'' +
                ", food_name='" + food_name + '\'' +
                ", refuse_num='" + refuse_num + '\'' +
                ", printer_ip='" + printer_ip + '\'' +
                ", food_attribute=" + food_attribute +
                '}';
    }

    public FoodListBean cloneOnew() {
        FoodListBean bean=new FoodListBean();
       bean.order_food_id=this.order_food_id;
       bean.food_num=this.food_num;
       bean.food_price2=this.food_price2;
       bean.food_name=this.food_name;
       bean. refuse_num=this.refuse_num;
       bean. printer_ip=this.printer_ip;//打印机ip
       bean.refuse_reason=this.refuse_reason;
        bean.food_attribute=new ArrayList<>();
        return bean;
    }
}