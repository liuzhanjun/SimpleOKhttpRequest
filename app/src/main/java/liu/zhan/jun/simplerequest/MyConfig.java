package liu.zhan.jun.simplerequest;

import android.os.Environment;

import java.io.File;

/**
 * Created by yunniu on 2017/4/18.
 */

public class MyConfig {
    //正式环境
    public final static String BASE_URL = "http://shop.founpad.com/index.php/";
    public final static String BASE_Image_URL = "http://shop.founpad.com/";

    // 云牛测试环境
//    public final static String BASE_URL = "http://yunniutest.cloudabull.com/index.php/";
//    public final static String BASE_Image_URL = "http://yunniutest.cloudabull.com/index.php/";

// 测试环境
//    public final static String BASE_URL = "http://wq9qga.natappfree.cc/diancan/index.php/";
//    public final static String BASE_Image_URL = "http://wq9qga.natappfree.cc/diancan/";
    //启动http服务的状态码  来判断进行什么请求
    public final static String SERVICE_HTTP_POST_STATUS = "status";
    public final static int SERVICE_HTTP_POST_PAY_QR_NUMBER  = 1;//发送支付码
    public final static int SERVICE_HTTP_POST_GET_MENU_MSG  = 2;//获取菜单的json数据

    /**
     * 判断是否激活地址
     */
    public  final  static String THE_ACTIVATION=BASE_URL+"allAgent/Activate/qrcActivateStatus";
    /**
     * 注册地址
     */
    public final  static String zcAddress =  "allAgent/activate/activate/";
    /**
     * 获取登录信息
     */
    public final static String get_users_info = BASE_Image_URL+"api/login/getCashierInfo";

    /**
     * 获取是否积分核销的数据
     */
    public  final  static  String if_cancel = "api/client/if_cancel";

    /**
     * 获取是否开启余额支付的数据
     */
    public  final  static  String get_If_Openpre = "api/client/getIfOpenpre";

    /**
     * 获取是店铺信息数据
     */
    public  final  static  String  restaurantAddress="api/client/restaurantAddress" ;
/**
 * 清除服务器数据请求
 * 注册
 * http://shop.founpad.com/index.php/component/Login/clearDeviceRecord
 * http://shop.founpad.com/index.php/component/Login/clearDeviceRecord
 * http://shop.founpad.com/
 */
//public  final  static  String  clearDeviceRecord = "index.php/component/Login/clearDeviceRecord" ;

    public  final  static  String  clearDeviceRecord = "component/Login/clearDeviceRecord" ;


    /**
     * 打印机(获取打印机的信息)
     */

    public  final  static  String  getPrinterByDeviceCode="api/printer/getPrinterByDeviceCode" ;
    /**
     *
     * 获取点餐页的数据
     */
    public  final  static  String  getOrderPageInfo = "api/order/getOrderPageInfo" ;
    /**
     * 获取该店铺菜品图片
     */
    public  final  static  String  getAllImg="api/client/getAllImg" ;

    /**
     * 店铺后台是否开启餐桌号、折扣
     */
    public  final  static  String  getIfOpenInfo="api/client/getIfOpenInfo" ;
    /**
     * 3代理公众号链接入口
     */
    public  final  static  String  public_num_url="api/client/public_num_url";

    /**
     * 获取广告的数据
     */
    public  final  static  String  getAdvertisementList="api/advertisement/getAdvertisementList" ;



    /**
     * 该店铺菜品属性数据接口
     *
     */
    public  final  static  String  allAttr="api/client/allAttr" ;


    /**
     * 获取支付类型
     */
    public final  static String pay_type="api/client/pay_type";
    /**
     * 客户端按下单时，生成订单信息（同步订单）
     */
    public final  static String placeOrder="api/client/placeOrder";
    /**
     * 现金支付，我要到收银台支付，生成支付号
     */

    public final  static String cashPay= "api/client/cashPay";
    /**
     * 获取是否缴费成功
     *
     */
    public final  static String getOrderStatus= "api/client/getOrderStatus";
    /**
     * 现金支付，我要到收银台支付，生成支付号

     */
    public final  static String order_tongbu= "api/client/order_tongbu";

    /**
     * 现金支付，完成下单
     */
    public final  static String cash_pay_only= "api/order/cash_pay_only";

    /**
     * 订单同步
     */
    public final static String pay_url = BASE_Image_URL+"api/order/orderSynchronization";

    //final static String BASE_URL = "http://192.168.31.101/index.php/";
    //final static String BASE_URL = "http://lbaobin.com/";
    public final static String Url_Clear_IF_Regist = BASE_URL+"component/Login/clearDeviceRecord";

    public final static String  POST_MACADRESS_PATH = BASE_URL+"manager/activate/activate/device_code/";

    //http://shop.founya.com/index.php/Component/Interface/versionInfo_vertical
    /**
     * 版本更新
     */

    //版本更新：http://shop.founpad.com/Component/Interface/rotate_yuansheng
    public  final static String URL_GET_VERSION_CODE = BASE_URL+"Component/Interface/rotate_yuansheng";

  public   final  static String REGIST_URL = BASE_URL+"allAgent/activate/showDQrcode/";
    //系统加载地址
    //点餐veritical
    public final  static String DIANCAN_LOAD_VERTICAL_URL = BASE_URL+"component/login/vertical_login";
    //点餐
    public final static String DIANCAN_LOAD_URL  =BASE_URL+"component/login/login";
    //收银http://shop.founya.com/ccccc
    public final static String SHOUYIN_LOAD_URL  =BASE_URL+"component/login/checkStandLogin";
    //取号http://shop.founya.com/component/login/showNum
    public final static String QUHAO_LOAD_URL  =BASE_URL+"component/login/showNum";
    //核销
    public final static String HEXIAO_LOAD_URL  =BASE_URL+"component/login/clerk";

    public final static String ACTION_PRIN_STRING = "com.example.condemo.printString";

    public final static String JSON_ITEM_BILL = "bill";//json字符串中的名字 --获取推送账单信息

    public final static String URL_POST_QR_NUMBER = BASE_URL+"home/WxChat/microPay";


    //获取打印小票信息
    public final static String ACTION_GET_BILL_MSG = "print_bill";
    public final static String ACTION_GET_BILL_BACK_MSG = "print_bill_back";

    public final static String URL_POST_GET_PRINT_MSG = BASE_URL+"Component/Interface/Info";


    //网络打印机
    public final static String ip = "192.168.31.200";
    public final static int port = 9100;
    /**
     *
     * 下载地址
     */
    public final static String download_path = ""+ Environment.getExternalStorageDirectory()+
            File.separator+"fabuxitongdownload";
    //发送打印机状态地址
    public final static String URL_POST_PRINTER_STATUS=BASE_URL+"component/interface/printerStatus";


    /**
     * 收银员登录
     */
    public final static String user_login = BASE_Image_URL+"api/login/cashierLogin";

    /**
     * 扫码枪
     */
    //"http://shop.founpad.com/"
    public final static String SAO_MA_QIANG = BASE_Image_URL+"api/order/client_saoMa";

    /**
     * 3、接收微光积分商城核销
     * http://shop.founpad.com/api/client/vip_score_cancel
     */
    public final static String JI_FEN_HE_XIAO = BASE_Image_URL+"api/client/vip_score_cancel";

    /**
     * 2、接收微光折扣支付
     *http://shop.founpad.com/api/client/vip_discount
     */
    public final static String ZHE_KOU_ZHI_FU = BASE_Image_URL + "api/client/vip_discount";

    /**
     * 1、接收微光余额支付
     *http://shop.founpad.com/api/client/balance
     */

    public final static String YU_E_ZHI_FU = BASE_Image_URL + "api/client/balance";

    /**
     * 营业额统计
     * http://yunniutest.cloudabull.com/index.php/api/client/statis
     */
    public final static String TJ_YING_YE_E = BASE_Image_URL + "api/client/statis";

    /**
     * 菜品统计
     * http://yunniutest.cloudabull.com/index.php/api/client/statis_dishes
     */
    public final static String TJ_CAI_PIN  = BASE_Image_URL + "api/client/statis_dishes";

    /**
     * 支付号
     * http://shop.founpad.com/index.php/api/client/zhifuhao_pay
     */
    public final static String ZHIFUHAO_PAY = BASE_URL +"api/client/zhifuhao_pay";

    /**
     * 改变支付状态
     * http://shop.founpad.com/index.php/api/client/callback_after_zhifuhao
     */
    public final static String CALLBACK_AFTER_ZHIFUBAO = BASE_URL + "api/client/callback_after_zhifuhao";

    /**
     * 取消订单
     * http://shop.founpad.com/index.php/api/client/clear_order
     */
    public final static String CLEAR_ORDER = BASE_URL + "api/client/clear_order";

    /**
     * 美团接单
     *http://shop.founpad.com/index.php/api/Push/meituan_order_confirm
     */
    public final static String MT_CONFIRM = BASE_URL + "api/Push/meituan_order_confirm";


    /**
     * 美团取消订单
     * http://shop.founpad.com/index.php/api/Push/restaurant_cancel_order
     */
    public final static String MT_CANCEL_ORDER = BASE_URL + "api/Push/restaurant_cancel_order";


    /**
     * 美团接单
     *http://shop.founpad.com/index.php/api/Push/jubaopen_order_confirm
     */
    public final static String jubaopen_order_confirm = BASE_URL + "api/Push/jubaopen_order_confirm";


    /**
     * 美团取消订单
     * http://shop.founpad.com/index.php/api/Push/jubaopen_restaurant_cancel_order
     */
    public final static String jubaopen_restaurant_cancel_order = BASE_URL + "api/Push/jubaopen_restaurant_cancel_order";

    /**
     * 阿里，获取device_id
     * http://shop.founpad.com/index.php/api/Push/DeviceId_relation_aliPush
     */
    public final static String DeviceId_relation_aliPush = BASE_URL + "api/Push/DeviceId_relation_aliPush";

    /**
     * 美团，获取新订单数据
     * http://shop.founpad.com/index.php/api/Push/get_data_by_orderId
     */
    public final static String get_data_by_orderId = BASE_URL + "api/Push/get_data_by_orderId";

    /**
     * 官方、第四方切换
     * http://shop.founpad.com/index.php/api/client/pay_mode
     */
    public final static String DI4_PAY_MODE = BASE_URL + "api/client/pay_mode";

    /**
     * 获取第四方支付的账号和密码
     * http://shop.founpad.com/index.php/api/client/fourth
     */
    public final static String DI4_FOURTH = BASE_URL + "api/client/fourth";

    /**
     * 民生银行客户端回调更改后台订单状态接口
     * http://shop.founpad.com/index.php/api/client/minsheng_bank_callback
     */
    public final static String minsheng_bank_callback = BASE_URL + "api/client/minsheng_bank_callback";

    /**
     * 饿了么，获取数据接口
     * http://shop.founpad.com/index.php/api/Eleme/eleme_get_data_by_orderId
     */
    public final static String eleme_get_data_by_orderId = BASE_URL + "api/Eleme/eleme_get_data_by_orderId";

    /**
     * 饿了么，确认接单
     * http://shop.founpad.com/index.php/api/Eleme/eleme_confirm_order
     */
    public final static String eleme_confirm_order = BASE_URL + "api/Eleme/eleme_confirm_order";

    /**
     * 饿了么，取消订单
     * http://shop.founpad.com/index.php/api/Eleme/eleme_cancel_order
     */
    public final static String eleme_cancel_order = BASE_URL + "api/Eleme/eleme_cancel_order";
    /*获得退单信息
    http://host/index.php/Api/Refuse/get_order
         */
    public static final String get_order="Api/Refuse/get_order";
}
