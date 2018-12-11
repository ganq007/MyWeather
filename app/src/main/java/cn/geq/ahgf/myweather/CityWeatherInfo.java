package cn.geq.ahgf.myweather;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.SystemClock;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.lidroid.xutils.HttpUtils;
        import com.lidroid.xutils.exception.HttpException;
        import com.lidroid.xutils.http.ResponseInfo;
        import com.lidroid.xutils.http.callback.RequestCallBack;
        import com.lidroid.xutils.http.client.HttpRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

        import cn.geq.ahgf.myweather.bean.CityWeather;
        import cn.geq.ahgf.myweather.utils.SharedPreferencesUtil;

public  class CityWeatherInfo extends Activity {
    private List<CityWeather> cityWeathers;
    private TextView mCity;
    private TextView mType;
    private TextView mWendu;
    private TextView mDate;
    private TextView mHigh;
    private TextView mLow;
    private TextView mPm25;
    private TextView mQualiry;
    private TextView mGanmao;
    private ListView mListView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_weather_info);
        initView();
        initDate();

    }
    //初始化控件
    private void initView() {
        mCity = findViewById(R.id.city_tv_city);
        mType = findViewById(R.id.city_tv_type);
        mWendu = findViewById(R.id.city_tv_wendu);
        mDate = findViewById(R.id.city_tv_date);
        mHigh = findViewById(R.id.city_tv_high);
        mLow = findViewById(R.id.city_tv_low);
        mPm25 = findViewById(R.id.dateweather_tv_pm25);
        mQualiry = findViewById(R.id.dateweather_tv_qualiry);
        mGanmao = findViewById(R.id.dateweather_tv_ganmao);
        mListView = findViewById(R.id.dateweather_lv_listview);
    }
    //初始化数据，展示数据
    private void initDate() {
        final String result =  SharedPreferencesUtil.getString(CityWeatherInfo.this, "weather", "");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                String city_code = intent.getStringExtra("city_code");
                getData(city_code);

                Log.e("---------------TAG", "sp数据 : "+result );
                cityWeathers = PressJson(result);
                if(cityWeathers!=null){
                    mCity.setText(cityWeathers.get(0).getCity());
                    mType.setText(cityWeathers.get(0).getTime());
                    mWendu.setText(cityWeathers.get(0).getWendu());
                    mDate.setText(cityWeathers.get(0).getDate());
                    mHigh.setText(cityWeathers.get(0).getHigh());
                    mLow.setText(cityWeathers.get(0).getLow());
                    mPm25.setText(cityWeathers.get(0).getPm25());
                    mQualiry.setText(cityWeathers.get(0).getQuality());
                    mGanmao.setText(cityWeathers.get(0).getGanmao());
                    myAdapter = new MyAdapter();
                    mListView.setAdapter(myAdapter);
                }
            }
        });
    }

    public class  MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cityWeathers.size();
        }

        @Override
        public Object getItem(int position) {
            return cityWeathers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView = View.inflate(getApplicationContext(),R.layout.city_weather_item,null);
                viewHolder = new ViewHolder();
                viewHolder.mDate = convertView.findViewById(R.id.dateweather_tv_date);
                viewHolder.mType = convertView.findViewById(R.id.dateweather_tv_type);
                viewHolder.mHigh = convertView.findViewById(R.id.dateweather_tv_high);
                viewHolder.mLow = convertView.findViewById(R.id.dateweather_tv_low);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mDate.setText(cityWeathers.get(position).getDate());
            viewHolder.mType.setText(cityWeathers.get(position).getType());
            viewHolder.mHigh.setText(cityWeathers.get(position).getHigh());
            viewHolder.mLow .setText(cityWeathers.get(position).getLow());
            return convertView;
        }
    }
    public class ViewHolder{
        private TextView mDate ,mType,mHigh,mLow;
    }

    //联网操作获取数据
    public void getData(String city_code){
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.send(HttpRequest.HttpMethod.GET, "http://t.weather.sojson.com/api/weather/city/"+city_code, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("------------TAG", "onSuccess: 联网获取数据成功"+ result);
                SharedPreferencesUtil.saveString(CityWeatherInfo.this,"weather",result);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("------------TAG", "onSuccess: 联网获取数据失败" );
            }
        });
    }
    //解析json
    public List<CityWeather> PressJson(String result) {
        List<CityWeather> list = new ArrayList<CityWeather>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            //得到城市信息
            JSONObject cityInfo = jsonObject.getJSONObject("cityInfo");
            String city = cityInfo.getString("city");
            //获取当天城市天气数据
            JSONObject data = jsonObject.getJSONObject("data");
            String shidu = data.getString("shidu")==null?"":data.getString("shidu");
            Log.e("TAG", " city   "+city);
//            String pm25 = data.getString("pm25")==null?"":data.getString("pm25");
//            String pm10 = data.getString("pm10")==null?"":data.getString("pm10");
//            String quality = data.getString("quality")==null?"":data.getString("quality");
            String wendu = data.getString("wendu")==null?"":data.getString("wendu");
            String ganmao = data.getString("ganmao")==null?"":data.getString("ganmao");
            //获取今、明天等往后几天天气数据
            JSONArray forecast = data.getJSONArray("forecast");
            for(int i = 0;i<forecast.length();i++){
                JSONObject forecastData = forecast.getJSONObject(i);
                Log.e("----TAG", "PressJson: "+forecast.length());
                Log.e("===TAG", "PressJson: "+forecastData.getString("date"));
                String date = forecastData.getString("date")==null?"":forecastData.getString("date");
                String sunrise = forecastData.getString("sunrise")==null?"":forecastData.getString("sunrise");
                String high = forecastData.getString("high")==null?"":forecastData.getString("high");
                String low = forecastData.getString("low")==null?"":forecastData.getString("low");
                String sunset = forecastData.getString("sunset")==null?"":forecastData.getString("sunset");
                String aqi = forecastData.getString("aqi")==null?"":forecastData.getString("aqi");
                String fx = forecastData.getString("fx")==null?"":forecastData.getString("fx");
                String fl = forecastData.getString("fl")==null?"":forecastData.getString("fl");
                String type = forecastData.getString("type")==null?"":forecastData.getString("type");
                String notice = forecastData.getString("notice")==null?"":forecastData.getString("notice");
                CityWeather cityWeather = new CityWeather();
                cityWeather.setCity(city);
                cityWeather.setShidu(shidu);
//                cityWeather.setPm25(pm25);
//                cityWeather.setPm10(pm10);
//                cityWeather.setQuality(quality);
                cityWeather.setWendu(wendu);
                cityWeather.setGanmao(ganmao);
                cityWeather.setDate(date);
                cityWeather.setSunrise(sunrise);
                cityWeather.setHigh(high);
                cityWeather.setLow(low);
                cityWeather.setAqi(aqi);
                cityWeather.setFx(fx);
                cityWeather.setFl(fl);
                cityWeather.setType(type);
                cityWeather.setNotice(notice);
                list.add(cityWeather);
            }
            return  list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }





}
