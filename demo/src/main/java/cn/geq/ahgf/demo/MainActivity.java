package cn.geq.ahgf.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.geq.ahgf.demo.utils.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData("101010100");
    }

    //联网操作获取数据
    public void getData(String city_code){
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.send(HttpRequest.HttpMethod.GET, "http://t.weather.sojson.com/api/weather/city/" + city_code, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                SharedPreferencesUtil.saveString(getApplicationContext(),"weather",result);
                PressJson(result);
            }
            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
    //解析json
    public void PressJson(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray forecast = data.getJSONArray("forecast");
            for (int i = 0;i<forecast.length();i++){
                JSONObject forecastJSONObject = forecast.getJSONObject(i);
                Log.e("TAG", "PressJson: "+ forecastJSONObject.getString("date"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
