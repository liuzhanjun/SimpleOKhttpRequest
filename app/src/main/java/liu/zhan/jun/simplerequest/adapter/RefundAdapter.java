package liu.zhan.jun.simplerequest.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

import liu.zhan.jun.simplerequest.R;
import liu.zhan.jun.simplerequest.bean.DataBean;
import liu.zhan.jun.simplerequest.bean.FoodAttributeBean;
import liu.zhan.jun.simplerequest.bean.FoodListBean;
import liu.zhan.jun.simplerequest.listener.RefundListener;
import liu.zhan.jun.simplerequest.parameter.RefuseParameter;
import liu.zhan.jun.simplerequest.utils.StoreSp;
import liu.zhan.jun.simplerequest.view.recyclerview.BaseRecyclerAdapter;
import liu.zhan.jun.simplerequest.view.recyclerview.BaseViewHolder;

/**
 * Created by yunniu on 2017/8/9.
 */

public class RefundAdapter extends BaseRecyclerAdapter{
    private static final String TAG = "RefundAdapter";
    List<DataBean> datas=new ArrayList<>();
    Context mContext;
    RefundListener refundListener;
    private Gson gson=new Gson();

    public RefundAdapter(List<DataBean> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }



    public void setRefundListener(RefundListener refundListener) {
        this.refundListener = refundListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.refund_list_item,parent,false);
        return new BaseViewHolder(view,onRecyclerItemClick,mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initView(holder,position);
    }

    private void initView(RecyclerView.ViewHolder holder, int position) {
        View view=holder.itemView;
        final DataBean bean = datas.get(position);
        //订单号
        TextView rf_order_sn= (TextView) view.findViewById(R.id.rf_order_sn);
        rf_order_sn.setText(bean.getOrder_sn());

        //时间
        TextView rf_time= (TextView) view.findViewById(R.id.rf_time);
        DateTime time=new DateTime(Long.valueOf(bean.getAdd_time())*1000);
        rf_time.setText(time.toString("yyyy-MM-dd HH:mm:ss"));
         //总价
        TextView rf_totle_price= (TextView) view.findViewById(R.id.rf_totle_price);
        rf_totle_price.setText(bean.getTotal_amount());

        //z支付类型
        ImageView zf_state= (ImageView) view.findViewById(R.id.zf_state);
        int type=Integer.valueOf(bean.getPay_type());
//        0现金，1支付宝，2微信，3未支付,4余额，5第四方支付
        switch (type) {
            case 0:
                zf_state.setImageResource(R.mipmap.cash_icon);
                break;
            case 1:
                zf_state.setImageResource(R.mipmap.alipay_icon);
                break;
            case 2:
                zf_state.setImageResource(R.mipmap.wechat);
                break;
            case 3:
//                zf_state.setImageResource(null);
                break;
            case 4:
                zf_state.setImageResource(R.mipmap.vip);
                break;
            case 5:
//                zf_state.setImageResource(R.mipmap.wechat);
                break;
        }

        //单个订单的食物列表
        final List<FoodListBean> foolist = bean.getFood_list();
        RecyclerView recycler_foods= (RecyclerView) view.findViewById(R.id.recycler_foods);

        //判断整单退款
        int refuse=Integer.valueOf( bean.getRefuse());
        boolean isrefAll;
        if (refuse==1){
            //已经整单退款
            isrefAll=true;
        }else {
            isrefAll=false;
        }
        //初始化食物列表
        initFoodList(recycler_foods,foolist,bean.getOrder_sn(),bean.getOrder_id(),isrefAll);
        //整单退款按钮
        Button rf_btn_td_all = (Button) view.findViewById(R.id.rf_btn_td_all);

        boolean isfoodlistRefus=false;
        for (FoodListBean foodListBean : bean.getFood_list()) {
            //遍历如果退过单个菜则不能再整单的退款
            int reNum=Integer.valueOf(foodListBean.getRefuse_num());
            if (reNum!=0){
                isfoodlistRefus=true;
            }

        }
        if (refuse!=0||isfoodlistRefus){
            //不能整单退款
            rf_btn_td_all.setEnabled(false);
        }else {
            rf_btn_td_all.setEnabled(true);
        }

        rf_btn_td_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refundListener != null) {

                    RefuseParameter parameter=new RefuseParameter();
                    parameter.setOrder_sn(bean.getOrder_sn());
                    parameter.setRefuse_type("1");
                    parameter.setRefuse_food_info(gson.toJson(foolist));
                    parameter.setOrder_id(bean.getOrder_id());
                    parameter.setRefuse_reason("不想吃了");
                    Log.i(TAG, "onClick: 退整单"+bean.getOrder_sn()+"par="+parameter.toString());
                    refundListener.refund(parameter);
                }
            }
        });

    }

    private void initFoodList( RecyclerView recycler_foods,List<FoodListBean> foolist,String order_sn,String ord,boolean isrefuAll) {
        FoodsAdapter foodsAdapter=new FoodsAdapter(foolist);
        foodsAdapter.setOrder_sn(order_sn);
        foodsAdapter.setOrder_id(ord);
        foodsAdapter.setRefuAll(isrefuAll);
        recycler_foods.setAdapter(foodsAdapter);
        recycler_foods.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }


    /**
     * 食物列表适配器
     */
    public class FoodsAdapter extends BaseRecyclerAdapter{
        List<FoodListBean> foolist=new ArrayList<>();
        private String order_sn;
        private String order_id;
        private boolean refuAll;//是否整单退款了

        public FoodsAdapter(List<FoodListBean> foolist) {
            this.foolist = foolist;
        }


        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public void setRefuAll(boolean refuAll) {
            this.refuAll = refuAll;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.refund_foods_list_item,parent,false);
            return new BaseViewHolder(view,onRecyclerItemClick,mContext);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            initview(holder,position);


        }

        private void initview(RecyclerView.ViewHolder holder, final int position) {
            final FoodListBean food = foolist.get(position);
            View view=holder.itemView;
            TextView food_name= (TextView) view.findViewById(R.id.food_name);
            List<FoodAttributeBean> attr = food.getFood_attribute();
           final String  foodname= getfoodname(food.getFood_name(),attr);
            food_name.setText(foodname);
            TextView food_num= (TextView) view.findViewById(R.id.food_num);
            food_num.setText(food.getFood_num());
            TextView food_price= (TextView) view.findViewById(R.id.food_price);
            food_price.setText(food.getFood_price2());
            Button refund_one= (Button) view.findViewById(R.id.refund_one);
            int reNum=Integer.valueOf(food.getRefuse_num());
            if (reNum!=0||refuAll){
                refund_one.setEnabled(false);
            }else {
                refund_one.setEnabled(true);
            }

            refund_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (refundListener != null) {

                        RefuseParameter parameter=new RefuseParameter();
                        parameter.setOrder_sn(order_sn);
                        parameter.setRefuse_type("2");
                        List<FoodListBean> rlist=new ArrayList<FoodListBean>();
                        food.setRefuse_num(food.getFood_num());
                        rlist.add(food);
                        parameter.setRefuse_food_info(gson.toJson(rlist));
                        parameter.setOrder_id(order_id);
                        parameter.setRefuse_reason("不想吃了");
                        Log.i(TAG, "onClick: 退单个菜"+foodname+"par="+parameter.toString());

                        refundListener.refund(parameter);
                    }
                }
            });
        }

        private String getfoodname(String food_name, List<FoodAttributeBean> attr) {
            StringBuffer buffer=new StringBuffer(food_name);
            for (FoodAttributeBean foodAttributeBean : attr) {
                buffer.append("<"+foodAttributeBean.getFood_attribute_name()+">");
            }
            return buffer.toString();
        }

        @Override
        public int getItemCount() {
            return foolist.size();
        }
    }

}
