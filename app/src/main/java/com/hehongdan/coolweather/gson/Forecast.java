package com.hehongdan.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 类描述：未来一天的天气信息（分时段预报）。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class Forecast {

    /** 天气数据。 */
    public String date;
    /** 气温。 */
    @SerializedName("tmp")
    public Temperatuer temperatuer;
    /** 更多天气信息。 */
    @SerializedName("cond")
    public More more;

    /**
     * 天气气温。
     */
    public class Temperatuer {
        /** 最大气温。 */
        public String max;
        /** 最小气温。 */
        public String min;
    }

    /**
     * 更多天气信息。
     */
    public class More{
        /** 天气信息。 */
        @SerializedName("txt_d")
        public String info;

    }
}
