package com.hehongdan.coolweather;

import android.os.Bundle;

/**
 * 类描述：Activity基类。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
