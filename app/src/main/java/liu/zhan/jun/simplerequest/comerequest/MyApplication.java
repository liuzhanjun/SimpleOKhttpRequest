package liu.zhan.jun.simplerequest.comerequest;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import liu.zhan.jun.simplerequest.BuildConfig;
import liu.zhan.jun.simplerequest.utils.Macuits;
import liu.zhan.jun.simplerequest.utils.StoreSp;

/**
 * Created by 刘展俊 on 2017/6/11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);
//
//        }
        String mac = Macuits.getMacAddress(getApplicationContext());
        StoreSp.storeMacAdress(getApplicationContext(), mac);
    }
}
