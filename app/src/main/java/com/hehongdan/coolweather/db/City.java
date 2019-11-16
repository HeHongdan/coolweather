package com.hehongdan.coolweather.db;

import lombok.Data;

/**
 * 类描述：城市。
 *
 * @author hehongdan
 * @version v2019/11/16
 * @date 2019/11/16
 */
@Data
public class City {

    private int id;

    private String cityName;

    private int cityCode;

    private int provinceId;



}
