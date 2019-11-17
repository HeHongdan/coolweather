package com.hehongdan.coolweather.db;

import lombok.Data;

/**
 * 类描述：省份。
 *
 * @author hehongdan
 * @version v2019/11/16
 * @date 2019/11/16
 */
@Data//Lombok插件3/3
public class Province {

    private int id;

    private String provinceName;

    private int provinceCode;


}
