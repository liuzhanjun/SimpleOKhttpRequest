package liu.zhan.jun.simplerequest.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import liu.zhan.jun.simplerequest.MyConfig;
import liu.zhan.jun.simplerequest.bean.DataBean;
import liu.zhan.jun.simplerequest.bean.FoodAttributeBean;
import liu.zhan.jun.simplerequest.bean.FoodListBean;
import liu.zhan.jun.simplerequest.respons.GetOrderResponse;
import liu.zhan.jun.simplerequest.respons.RefundResponse;

/**
 * 打印机打印数据
 */

public class RefundPrintUtils {
    public static final String TAG = "RefundPrintUtils";
    private Socket socket;
    public int Net_ReceiveTimeout = 500;
    public Gson gson = new Gson();

    public RefundPrintUtils() {
    }

    public void printMsg(RefundResponse response) {
        DataBean bean = response.getData();
        //
        String title = "";
        if (Integer.valueOf(bean.getRefuse()) == 0) {
            title = "退菜";
            //遍历要打印的菜品
            listPrint(bean, title, 0);
        } else {
            title = "退单";
            listPrint(bean, title, 1);

        }


    }

    /**
     * @param bean
     * @param title
     */
    private void listPrint(DataBean bean, String title, int type) {
        //获得主菜ip列表
        ConcurrentHashMap<String, List<FoodListBean>> foodsList = new ConcurrentHashMap<>();
        //菜品属性ip列表
        ConcurrentHashMap<String, List<FoodAttributeBean>> attrsList = new ConcurrentHashMap<>();

        //主菜ip集合
        ConcurrentSkipListSet<String> z_ips = new ConcurrentSkipListSet<>();
        //属性ip集合
        ConcurrentSkipListSet<String> attr_ips = new ConcurrentSkipListSet<>();
        //获得主菜和属性的ip集合
        for (FoodListBean food : bean.getFood_list()) {
            String ip = food.getPrinter_ip();
            z_ips.add(ip);
            for (FoodAttributeBean foodAttributeBean : food.getFood_attribute()) {


                attr_ips.add(foodAttributeBean.getPrinter_ip());

            }
        }
        //初始主菜列表和菜品属性列表
        Iterator<String> z_ips_it = z_ips.iterator();
        while (z_ips_it.hasNext()) {
            String zip = z_ips_it.next();
            foodsList.put(zip, new ArrayList<FoodListBean>());
        }

        Iterator<String> attr_ips_it = attr_ips.iterator();
        while (attr_ips_it.hasNext()) {
            String zip = attr_ips_it.next();
            attrsList.put(zip, new ArrayList<FoodAttributeBean>());
        }

        Iterator<String> keys = foodsList.keySet().iterator();
        while (keys.hasNext()) {
            String ip = keys.next();
            List<FoodListBean> listfood = foodsList.get(ip);
            Log.i(TAG, "listPrint: " + ip);
            //根据当前ip填充数据源
            for (FoodListBean foods : bean.getFood_list()) {
                //遍历属于当前ip的食物，
                if (foods.getPrinter_ip().equals(ip)) {
                    //获得属于这个ip的属性
                    FoodListBean tempfoods = foods.cloneOnew();
                    for (int i = 0; i < foods.getFood_attribute().size(); i++) {
                        FoodAttributeBean attr = foods.getFood_attribute().get(i);
                        attr.setRefuse_reason(foods.getRefuse_reason());
                        String attip = attr.getPrinter_ip();
                        Log.i(TAG, "listPrint: attip=" + attip);
                        if (attip.equals(ip)) {
                            //添加到主菜打印的属性中
                            tempfoods.getFood_attribute().add(attr);
                        } else {
                            //如果属性和打印机的ip不相同则加入到属性打印列表
                            Iterator<String> attrkeys = attrsList.keySet().iterator();
                            while (attrkeys.hasNext()) {
                                String aip = attrkeys.next();
                                if (attip.equals(aip)) {
                                    attrsList.get(aip).add(attr);
                                }
                            }
                        }
                    }
                    //将处理后的食物添加到打印列表
                    listfood.add(tempfoods);
                }
            }
        }

        //清空掉两个ip容器
        z_ips.clear();
        attr_ips.clear();
        z_ips = null;
        attr_ips = null;
        //最后获得两个打印队列主菜的，属性的
        Iterator<String> foodsListKeys = foodsList.keySet().iterator();
        while (foodsListKeys.hasNext()) {
            String key = foodsListKeys.next();
            List<FoodListBean> list = foodsList.get(key);
            Log.i(TAG, "主菜打印列表: key=" + key + "list=" + gson.toJson(list));

            boolean isconn = connection(key, MyConfig.port);
            Log.i(TAG, "listPrint: cccccccccc" + socket.isConnected());
            Log.i(TAG, "listPrint: isconn=" + isconn);
            if (isconn) {
                //开始打印
                print(type, title, bean, list);
            }

        }
        Iterator<String> attrsListKeys = attrsList.keySet().iterator();
        while (attrsListKeys.hasNext()) {
            String key = attrsListKeys.next();
            List<FoodAttributeBean> list = attrsList.get(key);
            Log.i(TAG, "属性打印列表: key=" + key + "list=" + gson.toJson(list));
            boolean isconn = connection(key, MyConfig.port);
            if (isconn) {
                //开始打印
                print2(type, title, bean, list);
            }
        }


    }
    public static String dataStringSet3(String food_name, String food_num, String food_price, int num, int size) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        char[] name_arr = food_name.toCharArray();//字符數
        Log.d("abcd", "name_arr: "+name_arr.length);
        char[] num_arr = food_num.toCharArray();
        Log.d("abcd", "num_arr: "+num_arr.length);
        char[] price_arr = food_price.toCharArray();
        Log.d("abcd", "num_arr: "+price_arr.length);

        //菜品
        sb.append(food_name);
        int znum = 0;
        for (int i = 0; i < name_arr.length; i++) {
            if (isZhongWen(name_arr[i])){
                znum++;//中文個數
            }
        }

        int kong = 28- 2*znum - (name_arr.length - znum)*1;

        for (int i = 0; i < kong; i++) {
            sb.append(" ");
        }

        //数量
        znum = 0;
        kong = 0;
        for (int i = 0; i < num_arr.length; i++) {
            if (isZhongWen(num_arr[i])){
                znum++;//中文個數
            }
        }
        kong = 8- 2*znum - (num_arr.length - znum)*1;

        for (int i = 0; i < kong; i++) {
            sb.append(" ");
        }
        sb.append(food_num);

        //价格
        znum = 0;
        kong = 0;
        for (int i = 0; i < price_arr.length; i++) {
            if (isZhongWen(price_arr[i])){
                znum++;//中文個數
            }
        }
        kong = 10- 2*znum - (price_arr.length - znum)*1;

        for (int i = 0; i < kong; i++) {
            sb.append(" ");
        }
        sb.append(price_arr);

        result = sb.toString();
        return result;
    }


    public static boolean  isZhongWen(char name){
        if((name >= 0x4e00)&&(name <= 0x9fbb)) {
            return  true;
        }
        return false;
    }

    public boolean connection(String ipaddress, int netport) {
        SocketAddress ipe = new InetSocketAddress(ipaddress, netport);
        boolean isConn;
        if (socket == null) {
            try {
                socket = new Socket();
                socket.connect(ipe);
                socket.setSoTimeout(Net_ReceiveTimeout);
                isConn = true;
            } catch (IOException e) {
                e.printStackTrace();
                isConn = false;
            }
        } else {

            try {
                socket.close();
                socket = new Socket();  //Socket(ipaddress, netport,true);
                socket.connect(ipe);
                socket.setSoTimeout(Net_ReceiveTimeout);
                isConn = true;
            } catch (IOException e) {
                e.printStackTrace();
                isConn = false;
            }

        }

        return isConn;
    }


    public void print(int type, String title, DataBean bean, List<FoodListBean> foods) {
        Log.i(TAG, "print: c" + gson.toJson(bean));
        DateTime time = new DateTime(Long.valueOf(bean.getAdd_time()) * 1000);
        FoodListBean foodt = null;
        if (foods != null) {
            foodt = foods.get(0);
        }
        PrintUtils printUtils = new PrintUtils();
        printUtils.clearAllCMD()//清空命令
                .PrintMusic()//打印声
                .PrintEnter()//换行
                .PrintTextLine(title, 1, 3, 0)//标题
                .PrintTextLine("下单时间：" + time.toString("yyyy-MM-dd HH:mm:ss") )
                .PrintTextLine("订单号  ：" + bean.getOrder_sn() )
                .PrintTextLine("----------------------------------------------" );

        String reason = "";
        if (type == 0) {
            if (foodt != null) {
                reason = foodt.getRefuse_reason();
            }

        } else {
            reason = bean.getRefuse_reason();
        }
        printUtils.PrintTextLine("原因:" + reason , 1, 3, 0)
                .PrintTextLine("----------------------------------------------" );
        printUtils.PrintText("  菜品                          份数      价格  ",0,0,0);

        //打印主菜以及这个打印机主菜的属性
        for (FoodListBean food : foods) {
            //打印主菜
            printUtils.PrintText(printUtils.dataStringSet3(food.getFood_name(),food.getFood_num(),food.getFood_price2(),48,0),0,1,0).PrintEnter();
            StringBuffer attrs = new StringBuffer();
            for (FoodAttributeBean foodAttributeBean : food.getFood_attribute()) {

                attrs.append("<" + foodAttributeBean.getFood_attribute_name() + ">");

            }
            printUtils.PrintTextLine(attrs.toString() );

        }
        printUtils.PrintNewLines(3).CutPage(3);
        if (socket != null) {
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
                os.write(printUtils.getAllCMD());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void print2(int type, String title, DataBean bean, List<FoodAttributeBean> attr) {
        if (attr==null||attr.size()<=0){
            return;
        }
        Log.i(TAG, "print: c" + gson.toJson(bean));
        DateTime time = new DateTime(Long.valueOf(bean.getAdd_time()) * 1000);
        PrintUtils printUtils = new PrintUtils();
        printUtils.clearAllCMD()//清空命令
                .PrintMusic()//打印声
                .PrintEnter()//换行
                .PrintTextLine(title, 1, 3, 0)//标题
                .PrintTextLine("下单时间：" + time.toString("yyyy-MM-dd HH:mm:ss") )
                .PrintTextLine("订单号  ：" + bean.getOrder_sn() )
                .PrintTextLine("----------------------------------------------" );
        String reason = "";
        if (type==0){
            if (attr!=null&&attr.size()>0) {
                reason = attr.get(0).getRefuse_reason();
            }
        }else{
            reason = bean.getRefuse_reason();
        }


        printUtils.PrintTextLine("原因:" + reason,0,3,0 )
                .PrintTextLine("----------------------------------------------" );

        //统计同样的属性
        ConcurrentHashMap<String,Integer> valuse=new ConcurrentHashMap<>();
        float totlePrice=0;//总价
        for (FoodAttributeBean foodAttributeBean : attr) {
            String name = foodAttributeBean.getFood_attribute_name();
            totlePrice+=Float.valueOf(foodAttributeBean.getFood_attribute_price());
            //先查看value中是否有这个属性名称
            Integer num=valuse.get(name);
            if (num==null){
                valuse.put(name,1);
            }else {
                ++num;
                valuse.put(name,num);
            }


        }
        Log.i(TAG, "print2: =================json="+gson.toJson(valuse));
        Log.i(TAG, "print2: =================总价="+totlePrice);
        Iterator<String> vit = valuse.keySet().iterator();
        while (vit.hasNext()) {
            String name = vit.next();
            Integer num=valuse.get(name);
            printUtils.PrintTextLine(name+"          "+num ,0,3,0);
        }
        printUtils .PrintTextLine("----------------------------------------------")
                   .PrintTextLine("                                   总价-"+totlePrice);

        printUtils.PrintNewLines(3).CutPage(3);
        if (socket != null) {
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
                os.write(printUtils.getAllCMD());
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
