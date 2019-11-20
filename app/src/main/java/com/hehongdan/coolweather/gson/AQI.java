package com.hehongdan.coolweather.gson;

/**
 * 类描述：空气质量指数。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class AQI {

    /** 城市空气质量指数。 */
    public AQICity city;

    /**
     * 城市空气质量指数
     */
    public class AQICity {
        /** 空气质量指数。 */
        public String aqi;
        /** PM2.5值。 */
        public String pm25;
    }
}
