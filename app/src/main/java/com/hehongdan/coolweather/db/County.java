package com.hehongdan.coolweather.db;

import lombok.Data;

/**
 * 类描述：县城/街道。
 *
 * @author hehongdan
 * @version v2019/11/16
 * @date 2019/11/16
 */
@Data
public class County {

    private int id;

    private String countyName;

    private String weatherId;

    private int cityId;


}
