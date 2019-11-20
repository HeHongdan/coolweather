package com.hehongdan.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 类描述：建议（生活指数）。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class Suggestion {

    /** 舒适度。 */
    @SerializedName("comf")
    public Comfort comfort;
    /** 洗车指数。 */
    @SerializedName("cw")
    public CarWash carWash;
    /** 运动指数。 */
    public Sport sport;

    /**
     * 舒适度。
     */
    public class Comfort {
        /** 舒适度信息。 */
        @SerializedName("txt")
        public String info;
    }

    /**
     * 洗车指数。
     */
    public class CarWash {
        /** 洗车指数信息。 */
        @SerializedName("txt")
        public String info;
    }

    /**
     * 运动指数。
     */
    public class Sport {
        /** 运动指数信息。 */
        @SerializedName("txt")
        public String info;
    }
}
