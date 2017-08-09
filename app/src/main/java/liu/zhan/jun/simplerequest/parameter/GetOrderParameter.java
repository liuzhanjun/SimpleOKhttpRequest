package liu.zhan.jun.simplerequest.parameter;

/**
 * Created by yunniu on 2017/8/9.
 */

public class GetOrderParameter extends BaseParameter {
    String _id;//（订单id）
    String order_sn;//（订单号）
    String zhifuhao;//（支付号，只查询当天时间内的订单）
    String start_time;//,end_time（只有start_time时查询start_time以后的订单）

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getZhifuhao() {
        return zhifuhao;
    }

    public void setZhifuhao(String zhifuhao) {
        this.zhifuhao = zhifuhao;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    @Override
    public String getDevice_cdoe() {
        return super.getDevice_cdoe();
    }

    @Override
    public void setDevice_cdoe(String device_cdoe) {
        super.setDevice_cdoe(device_cdoe);
    }
}
