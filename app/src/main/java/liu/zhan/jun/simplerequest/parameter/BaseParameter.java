package liu.zhan.jun.simplerequest.parameter;

import liu.zhan.jun.simplerequest.comerequest.ComeRequestIn;

/**
 * Created by yunniu on 2017/8/9.
 */

public class BaseParameter implements ComeRequestIn.RequestModel {
    protected String device_code;

    protected String getDevice_cdoe() {
        return device_code;
    }

    public void setDevice_cdoe(String device_cdoe) {
        this.device_code = device_cdoe;
    }




    @Override
    public String toString() {
        return "BaseParameter{" +
                "device_code='" + device_code + '\'' +
                '}';
    }
}
