package com.hehongdan.coolweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hehongdan.coolweather.db.City;
import com.hehongdan.coolweather.db.County;
import com.hehongdan.coolweather.db.Province;
import com.hehongdan.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类描述：网络请求代理解析类。
 *
 * @author hehongdan
 * @version v2019/11/18
 * @date 2019/11/18
 */
public class Utility {

    private static MyLogger hhdLog = MyLogger.HHDLog();

    /**
     * 解析和处理服务器返回的省级数据。
     *
     * @param response 服务器响应的字符串数据。
     * @return 是否解析成功。
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                hhdLog.e("解析服务器返回数据异常=" + e);
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据。
     *
     * @param response 服务器响应的字符串数据。
     * @param provinceId 省份的ID。
     * @return 是否解析成功。
     */
    public static boolean handleCityResponse(String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                hhdLog.e("解析服务器返回数据异常=" + e);
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据。
     *
     * @param response 服务器响应的字符串数据。
     * @param cityId 城市的ID。
     * @return 是否解析成功。
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的天气数据。
     *
     * @param response 服务器响应的字符串数据。
     * @return 是否解析成功。
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
