package com.hehongdan.coolweather.db;

import org.litepal.crud.DataSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类描述：省份。
 *
 * @author hehongdan
 * @version v2019/11/16
 * @date 2019/11/16
 */
@Data//3/3Lombok插件
@EqualsAndHashCode(callSuper = false)
public class Province extends DataSupport {

    private int id;

    private String provinceName;

    private int provinceCode;


}
