package com.hehongdan.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 类描述：OkHttp封装类。
 * @author hehongdan
 * @version v2019/11/18
 * @date 2019/11/18
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
