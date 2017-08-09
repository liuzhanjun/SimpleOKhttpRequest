package liu.zhan.jun.simplerequest.parameter;

import liu.zhan.jun.simplerequest.comerequest.ComeRequestIn;

/**
 * Created by yunniu on 2017/8/9.
 */

public class BaseParameter implements ComeRequestIn.RequestModel {
    public String device_cdoe;

    public String getDevice_cdoe() {
        return device_cdoe;
    }

    public void setDevice_cdoe(String device_cdoe) {
        this.device_cdoe = device_cdoe;
    }
}
