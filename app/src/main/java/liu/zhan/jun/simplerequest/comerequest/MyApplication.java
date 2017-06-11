package liu.zhan.jun.simplerequest.comerequest;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import liu.zhan.jun.simplerequest.BuildConfig;

/**
 * Created by 刘展俊 on 2017/6/11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);

        }
    }
}
