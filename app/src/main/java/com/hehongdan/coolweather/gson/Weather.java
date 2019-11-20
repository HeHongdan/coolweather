package com.hehongdan.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 类描述：天气（整合所有信息）。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class Weather {
    /** 请求状态。 */
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    /** 未来一天（分时段预报）。 */
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
