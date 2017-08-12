package liu.zhan.jun.simplerequest.bean;

public class FoodAttributeBean implements Cloneable {
    /**
     * food_attribute_name : 香菜
     * food_attribute_price : 0.00
     */

    private String food_attribute_name;
    private String food_attribute_price;
    private String printer_ip;
    public String refuse_reason;

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public String getFood_attribute_name() {
        return food_attribute_name;
    }

    public void setFood_attribute_name(String food_attribute_name) {
        this.food_attribute_name = food_attribute_name;
    }

    public String getFood_attribute_price() {
        return food_attribute_price;
    }

    public void setFood_attribute_price(String food_attribute_price) {
        this.food_attribute_price = food_attribute_price;
    }

    public String getPrinter_ip() {
        return printer_ip;
    }

    public void setPrinter_ip(String printer_ip) {
        this.printer_ip = printer_ip;
    }

    @Override
    public String toString() {
        return "FoodAttributeBean{" +
                "food_attribute_name='" + food_attribute_name + '\'' +
                ", food_attribute_price='" + food_attribute_price + '\'' +
                ", printer_ip='" + printer_ip + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
