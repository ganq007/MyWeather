package cn.geq.ahgf.myweather;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.geq.ahgf.myweather.bean.CityInfo;
import cn.geq.ahgf.myweather.utils.FileUtils;

public class MainActivity extends AppCompatActivity {
    private ListView mListViwe;
    private List<CityInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        initView();
        initData();
    }
    //初始化view
    private void initView() {
        mListViwe = findViewById(R.id.weather_lv_listview);
    }

    //初始化数据
    private void initData() {
        //读取assets下的_city.json 文件，拷贝到data/data/file下
        copyDB("_city.json");
        File file = new File(getFilesDir(),"_city.json");
        //读取json数据
        String json = FileUtils.getJson(file);
        //解析json数据 ,城市名 ， 城市编号
        list = CityJosn(json);
        mListViwe.setAdapter( new  MyAdpater());
        //条目点击事件
        onListViewItemClickListener();
    }

    private void onListViewItemClickListener() {

        mListViwe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(MainActivity.this,CityWeatherInfo.class);
                String city_code = list.get(position).getCity_code();
                intent.putExtra("city_code",city_code);
                startActivity(intent);
            }
        });
    }


    public class  MyAdpater extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HodleView hodleView ;
            if (convertView==null){
                convertView = View.inflate(getApplicationContext(), R.layout.city_listview_item, null);
                hodleView = new HodleView();
                hodleView.mtv_name = convertView.findViewById(R.id.city_tv_name);
                hodleView.mtv_code = convertView.findViewById(R.id.city_tv_code);
                convertView.setTag(hodleView);
            }else{
                hodleView = (HodleView) convertView.getTag();
            }
            hodleView.mtv_name.setText(list.get(position).getCity_name());
            hodleView.mtv_code.setText(list.get(position).getCity_code());

            return convertView;
        }
    }
    private class HodleView{
        private TextView mtv_name ,mtv_code;
    }

    //1.2解析json数据
    private List<CityInfo>  CityJosn(String json) {
        List<CityInfo> list = null;
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
            for(int i=0;i<json.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String city_code = (String) jsonObject.getString("city_code");
                String city_name = (String) jsonObject.getString("city_name");
                if (city_code.equals("")){
                    city_code="000000";
                }
                if (list==null){
                    list = new ArrayList<CityInfo>();
                }
                list.add(new CityInfo(city_code,city_name));
            }
        } catch (JSONException e) {
            list.add(new CityInfo("124235","bj"));
        }
        return list;
    }


    //拷贝数据库的方法
    private void copyDB(String dbName) {
        //判断数据库是否已经拷贝,如果已经存在就不拷贝
        File file = new File(getFilesDir(), dbName);
        if(!file.exists()){
            //assets  的管理者
            AssetManager assets = getAssets();
            InputStream in =null;
            FileOutputStream out=null;
            try {
                //读取assets保存的资源
                in = assets.open(dbName);
                //getFilesDir() 获取data目录的包名
                out = new FileOutputStream(new File(getFilesDir(),dbName));
                //写入操作
                byte[] b = new byte[1024];
                int len =-1 ;
                while((len=in.read(b))!=-1){
                    out.write(b,0,len);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (in!=null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out!=null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }





}
