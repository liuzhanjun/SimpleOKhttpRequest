package liu.zhan.jun.simplerequest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.DropBoxManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;


import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import liu.zhan.jun.simplerequest.adapter.RefundAdapter;
import liu.zhan.jun.simplerequest.bean.DataBean;
import liu.zhan.jun.simplerequest.comerequest.ComeRequest;
import liu.zhan.jun.simplerequest.comerequest.RequestCallBack;
import liu.zhan.jun.simplerequest.listener.RefundListener;
import liu.zhan.jun.simplerequest.parameter.GetOrderParameter;
import liu.zhan.jun.simplerequest.parameter.RefuseParameter;
import liu.zhan.jun.simplerequest.respons.BaseResponse;
import liu.zhan.jun.simplerequest.respons.GetOrderResponse;
import liu.zhan.jun.simplerequest.respons.RefundResponse;
import liu.zhan.jun.simplerequest.utils.StoreSp;
import liu.zhan.jun.simplerequest.view.EditDialog;
import liu.zhan.jun.simplerequest.view.LoadProgressDialog;

public class RefundActivity extends AppCompatActivity {

    private static final String TAG ="RefundActivity" ;

    MaterialSpinner query_type;//查询类型
    MaterialSpinner query_time;//查询时间
    Button btn_query;//查询按钮
    EditText query_parameter;//查询的条件输入框
    private Dialog dialog;
    RecyclerView recycler_refund_list;//查询到的数据展示
    private Context mContext;
    private EditDialog reasonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        mContext=this;
        recycler_refund_list= (RecyclerView) findViewById(R.id.recycler_refund_list);
        recycler_refund_list.setOverScrollMode(View.OVER_SCROLL_NEVER);
        initView();


        dialog = LoadProgressDialog.createDialog(this, "正在查询");
        reasonDialog=new EditDialog(this);
        reasonDialog.setTitle("请输入退款原因");

    }

    private void initView() {
        //选择列表
        query_type= (MaterialSpinner) findViewById(R.id.query_type);
        query_type.setItems("订单号", "支付号");
        //查询时间
        query_time= (MaterialSpinner) findViewById(R.id.query_time);
        query_time.setItems("2小时内", "1小时内", "30分钟内", "10分钟内", "5分钟内");
        //查询条件
        query_parameter= (EditText) findViewById(R.id.query_parameter);


        //查询按钮
        btn_query= (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestData();
            }
        });

    }

    /**
     * 查询数据
     */
    private void RequestData() {
        GetOrderParameter parameter=new GetOrderParameter();
        parameter.setDevice_cdoe(StoreSp.getMacAdress(getApplicationContext()));
        int type=query_type.getSelectedIndex();
        //获得输入框的内容
        String content=getEditContent();
        switch (type){
            case 0:
                //查询条件是订单号
                parameter.setOrder_sn(content);
                break;
            case 1:
                //查询条件是支付号
                parameter.setZhifuhao(content);
                break;
            case 2:
                //查询所有
                break;
        }

        DateTime currentTime=new DateTime();
        int time=query_time.getSelectedIndex();
        Long time2=null;
        switch (time){
            case 0:
                //两个小时内
                time2= currentTime.minusMinutes(120).getMillis()/1000;
                Log.i(TAG, "onClick: 两个小时前"+time2);
                break;
            case 1:
                //一个小时内
                time2 = currentTime.minusMinutes(60).getMillis()/1000;
                Log.i(TAG, "onClick: 一个小时内"+time2);
                parameter.setStart_time(String.valueOf(time2));
                break;
            case 2:
                //30分钟内
                time2 = currentTime.minusMinutes(30).getMillis()/1000;
                Log.i(TAG, "onClick: 30分钟内"+time2);

                break;
            case 3:
                //十分钟内
                time2 = currentTime.minusMinutes(10).getMillis()/1000;
                Log.i(TAG, "onClick: 10分钟内"+time2);
                break;
            case 4:
                //5分钟内
                time2 = currentTime.minusMinutes(5).getMillis()/1000;
                Log.i(TAG, "onClick: 5分钟内"+time2);
                break;

        }
        parameter.setStart_time(String.valueOf(time2));
        //网络请求查询退款信息
        getData(parameter);
    }

    private String getEditContent() {
        if (!TextUtils.isEmpty(query_parameter.getText().toString())){
            return query_parameter.getText().toString();
        }
        return null;

    }

    /**
     * 网络请求查询退款信息
     */
    private void getData(GetOrderParameter parameter) {
        Log.i(TAG, "getData: "+parameter.toString());
        ComeRequest.request.setTag(MyConfig.get_order).requestPost(MyConfig.BASE_URL + MyConfig.get_order, parameter, new RequestCallBack<GetOrderResponse>() {
            @Override
            public void before() {
                Log.i(TAG, "before: ");
                LoadProgressDialog.show(dialog);
            }

            @Override
            public void success(GetOrderResponse result) {
//                GetOrderResponse
//                Log.i(TAG, "success: "+result);
                if (result!=null) {

//                    Gson gson=new Gson();
//                    Log.i(TAG, "success: "+gson.toJson(result.getData()));
                    RefundAdapter adapter;
                    if (result.getData()!=null){
                        adapter=new RefundAdapter(result.getData(),mContext);
                    }else{
                        adapter=new RefundAdapter(new ArrayList<DataBean>(),mContext);
                    }

                    adapter.notifyDataSetChanged();
                    //退款监听
                    adapter.setRefundListener(new RefundListener() {
                        @Override
                        public void refund(final RefuseParameter parameter) {

                            reasonDialog.show();
                            reasonDialog.setOnPosNegClickListener(new EditDialog.OnPosNegClickListener() {
                                @Override
                                public void posClickListener(String value) {
                                    Log.i(TAG, "posClickListener: "+value);
                                    //确定
                                    parameter.setRefuse_reason(value);
                                    refundRequest(parameter);
                                }

                                @Override
                                public void negCliclListener(String value) {

                                }
                            });
//
                        }
                    });
                    recycler_refund_list.setAdapter(adapter);
                    recycler_refund_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                }
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Log.i(TAG, "failure: "+error.getMessage());
                LoadProgressDialog.close(dialog);
            }

            @Override
            public void finish() {
                LoadProgressDialog.close(dialog);
            }
        });
    }

    /**
     * 退款请求
     * @param parameter
     */
    private void refundRequest(RefuseParameter parameter) {
        parameter.setDevice_cdoe(StoreSp.getMacAdress(getApplicationContext()));
        Log.i(TAG, "refundRequest: 退款参数"+parameter.toString());
        ComeRequest.request.setTag(MyConfig.refuse).requestPost(MyConfig.BASE_URL + MyConfig.refuse, parameter, new RequestCallBack<RefundResponse>() {
            @Override
            public void before() {
                Log.i(TAG, "before: 开始退单");
            }

            @Override
            public void success(RefundResponse result) {
                Toast.makeText(RefundActivity.this,result.getMsg(),Toast.LENGTH_SHORT).show();
                Log.i(TAG, "success: "+new Gson().toJson(result));
            }

            @Override
            public void failure(ComeRequest.NetThrowable error) {
                Toast.makeText(RefundActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void finish() {
                RequestData();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (reasonDialog != null&&reasonDialog.isShowing()) {
            reasonDialog.dismiss();
        }
        LoadProgressDialog.close(dialog);
        ComeRequest.request.cancel(MyConfig.get_order);
        ComeRequest.request.cancel(MyConfig.refuse);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
