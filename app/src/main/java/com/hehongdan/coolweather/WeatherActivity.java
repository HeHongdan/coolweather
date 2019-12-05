package com.hehongdan.coolweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.hehongdan.coolweather.gson.Forecast;
import com.hehongdan.coolweather.gson.Weather;
import com.hehongdan.coolweather.util.HttpUtil;
import com.hehongdan.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 类描述：Activity基类。
 *
 * @author hehongdan
 * @version v2019/11/19
 * @date 2019/11/19
 */
public class WeatherActivity extends BaseActivity {

    /** 选择天气归属地的抽屉视图。 */
    public DrawerLayout drawerLayout;
    /** 主页（房子）按钮。 */
    private Button navButton;
    /** 滑动视图。 */
    private ScrollView weatherLayout;
    /** 标题。 */
    private TextView titleCity;
    /** 更新时间。 */
    private TextView titleUpdateTime;
    /**  */
    private TextView degreeText;
    /** 天气信息。 */
    private TextView weatherInfoText;
    /** 预报布局。 */
    private LinearLayout forecastLayout;
    /** 空气质量。 */
    private TextView aqiText;
    /** PM2.5 */
    private TextView pm25Text;
    /** 舒适度。 */
    private TextView comfortText;
    /** 洗车指标。 */
    private TextView carWashText;
    /** 运动指标。 */
    private TextView sportText;
    /** 显示必应背景图。 */
    private ImageView bingPicImg;
    /** 刷新进度条。 */
    public SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 23) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button) findViewById(R.id.nav_button);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sharedPreferences.getString("weather", null);
        final String weatherId;
        if (weatherString != null) {
            //有本地缓存时注解解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            weatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            //无本地缓存时去服务器查询天气数据
            weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        String bingPic = sharedPreferences.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int resourceId_ = getResources().getIdentifier("floating_toolbar_menu_image_width", "dimen", "android");
        //推荐--参数为ID名，第二个为资源属性是ID或者是Drawable，第三个为包名
        int resourceId2 = getResources().getIdentifier("sport_text", "id", "com.hehongdan.coolweather");
        //格式是：包名 + : +资源文件夹名 + / +资源名
        int resourceId3 = getResources().getIdentifier("com.hehongdan.coolweather:drawable/ic_home", null, null);
        String resourceName = getResources().getResourceName(sportText.getId());
        if (resourceId > 0) {
            hhdLog.i("获取任意组件ID=" + resourceId + "，指定值=" + getResources().getDimension(resourceId));
            hhdLog.i("根据ID获取组件名称=" + resourceName + "，根据名称获取组件ID=" + resourceId2);
            hhdLog.i("根据名称获取组件ID=" + resourceId3);
            hhdLog.i("根据名=" + getResources().getDimension(resourceId_));
        }
    }

    /**
     * 工具天气Id请求城市天气信息。
     *
     * @param weatherId 天气Id。
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 处理并展示Weather实体类种的数据。
     *
     * @param weather 天气信息对象。
     */
    private void showWeatherInfo(final Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime;
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.date_text);
            TextView infoText = view.findViewById(R.id.info_text);
            TextView maxText = view.findViewById(R.id.max_text);
            TextView minText = view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperatuer.max);
            minText.setText(forecast.temperatuer.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 加载必应的背景图片。
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

}
