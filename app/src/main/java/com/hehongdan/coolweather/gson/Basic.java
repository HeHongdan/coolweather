package com.hehongdan.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 类描述：基础。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class Basic {

    /** 城市名称。 */
    @SerializedName("city")
    public String cityName;
    /** 天气信息ID。 */
    @SerializedName("id")
    public String weatherId;
    public Update update;

    /**
     * 天气更新时间。
     */
    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
