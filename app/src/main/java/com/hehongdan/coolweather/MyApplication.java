package com.hehongdan.coolweather;

import android.app.Application;

import org.litepal.LitePal;


/**
 * 类描述：
 *
 * @author hehongdan
 * @version v2019/11/16
 * @date 2019/11/16
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LitePal.initialize(this);
    }
}
