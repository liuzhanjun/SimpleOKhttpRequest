package liu.zhan.jun.simplerequest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import liu.zhan.jun.simplerequest.respons.RefundResponse;
import liu.zhan.jun.simplerequest.utils.RefundPrintUtils;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        RefundPrintUtils utils=new RefundPrintUtils();
        String json="{\n" +
                "  \"data\":{\n" +
                "    \"add_time\":\"1502440017\",\n" +
                "    \"food_list\":[\n" +
                "      {\n" +
                "        \"food_attribute\":[\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"可乐\",\n" +
                "            \"food_attribute_price\":\"0.01\",\n" +
                "            \"printer_ip\":\"192.168.1.203\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"薯条\",\n" +
                "            \"food_attribute_price\":\"0.02\",\n" +
                "            \"printer_ip\":\"192.168.1.203\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"辣条\",\n" +
                "            \"food_attribute_price\":\"0.03\",\n" +
                "            \"printer_ip\":\"192.168.1.203\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"32度\",\n" +
                "            \"food_attribute_price\":\"0.04\",\n" +
                "            \"printer_ip\":\"192.168.1.203\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"250ml\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"food_name\":\"XCZ\",\n" +
                "        \"food_num\":\"1\",\n" +
                "        \"food_price2\":\"0.01\",\n" +
                "        \"order_food_id\":\"295401\",\n" +
                "        \"printer_ip\":\"192.168.1.202\",\n" +
                "        \"refuse_num\":\"1\",\n" +
                "        \"refuse_reason\":\"ppppp\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"food_attribute\":[\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"可乐\",\n" +
                "            \"food_attribute_price\":\"0.05\",\n" +
                "            \"printer_ip\":\"192.168.1.203\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"薯条\",\n" +
                "            \"food_attribute_price\":\"0.06\",\n" +
                "            \"printer_ip\":\"192.168.1.203\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"辣条\",\n" +
                "            \"food_attribute_price\":\"0.07\",\n" +
                "            \"printer_ip\":\"192.168.1.203\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"32度\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"250ml\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"food_name\":\"XCZ\",\n" +
                "        \"food_num\":\"1\",\n" +
                "        \"food_price2\":\"0.01\",\n" +
                "        \"order_food_id\":\"295401\",\n" +
                "        \"printer_ip\":\"192.168.1.202\",\n" +
                "        \"refuse_num\":\"1\",\n" +
                "        \"refuse_reason\":\"ppppp\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"food_attribute\":[\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"可乐\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"薯条\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"辣条\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"32度\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"250ml\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"food_name\":\"XCZ\",\n" +
                "        \"food_num\":\"1\",\n" +
                "        \"food_price2\":\"0.01\",\n" +
                "        \"order_food_id\":\"295401\",\n" +
                "        \"printer_ip\":\"192.168.1.202\",\n" +
                "        \"refuse_num\":\"1\",\n" +
                "        \"refuse_reason\":\"ppppp\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"food_attribute\":[\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"可乐\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"薯条\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"辣条\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"32度\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"250ml\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"food_name\":\"XCZ\",\n" +
                "        \"food_num\":\"1\",\n" +
                "        \"food_price2\":\"0.01\",\n" +
                "        \"order_food_id\":\"295401\",\n" +
                "        \"printer_ip\":\"192.168.1.202\",\n" +
                "        \"refuse_num\":\"1\",\n" +
                "        \"refuse_reason\":\"ppppp\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"food_attribute\":[\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"可乐\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"薯条\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"辣条\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"32度\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"food_attribute_name\":\"250ml\",\n" +
                "            \"food_attribute_price\":\"0.00\",\n" +
                "            \"printer_ip\":\"192.168.1.202\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"food_name\":\"XCZ\",\n" +
                "        \"food_num\":\"1\",\n" +
                "        \"food_price2\":\"0.01\",\n" +
                "        \"order_food_id\":\"295401\",\n" +
                "        \"printer_ip\":\"192.168.1.202\",\n" +
                "        \"refuse_num\":\"1\",\n" +
                "        \"refuse_reason\":\"ppppp\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"order_id\":\"147123\",\n" +
                "    \"order_sn\":\"CD1ccae334b6ec20170811162639\",\n" +
                "    \"pay_type\":\"1\",\n" +
                "    \"refuse\":\"2\",\n" +
                "    \"take_num\":\"25003\",\n" +
                "    \"total_amount\":\"0.02\",\n" +
                "\t\"refuse_reason\":\"上菜太慢\",\n" +
                "    \"zhifuhao\":\"25003\"\n" +
                "  },\n" +
                "  \"code\":\"1\",\n" +
                "  \"msg\":\"菜品退单成功\"\n" +
                "}";
        Gson gson=new Gson();
        RefundResponse ref = gson.fromJson(json, RefundResponse.class);
        utils.printMsg(ref);
        assertEquals(0, 0);
    }
}
