package com.hehongdan.coolweather.db;

import org.litepal.crud.DataSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类描述：县城/街道。
 *
 * @author hehongdan
 * @version v2019/11/16
 * @date 2019/11/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class County extends DataSupport {

    private int id;

    private String countyName;

    private String weatherId;

    private int cityId;


}
