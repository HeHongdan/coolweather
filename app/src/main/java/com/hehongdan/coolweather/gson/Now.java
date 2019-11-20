package com.hehongdan.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 类描述：当前天气。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class Now {

    /** 当前气温。 */
    @SerializedName("tmp")
    public String temperature;
    /** 更多信息。 */
    @SerializedName("cond")
    public More more;

    /**
     * 更多天气信息。
     */
    public class More {
        /** 更多天气信息。 */
        @SerializedName("txt")
        public String info;
    }
}
