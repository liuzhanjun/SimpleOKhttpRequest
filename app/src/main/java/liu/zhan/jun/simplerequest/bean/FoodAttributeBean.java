package liu.zhan.jun.simplerequest.bean;

public  class FoodAttributeBean {
                /**
                 * food_attribute_name : 香菜
                 * food_attribute_price : 0.00
                 */

                private String food_attribute_name;
                private String food_attribute_price;

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

    @Override
    public String toString() {
        return "FoodAttributeBean{" +
                "food_attribute_name='" + food_attribute_name + '\'' +
                ", food_attribute_price='" + food_attribute_price + '\'' +
                '}';
    }
}
