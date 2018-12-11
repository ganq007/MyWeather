package cn.geq.ahgf.myweather.bean;

import java.util.List;

public class CityWeather2 {
    public String time;
    public cityInfo cityInfo;
    public String date;
    public String status;
    public String message;
    public data data;



    public class cityInfo{
        public String city;
        public String  cityId;
        public String parent;
        public String updateTime;
    }

    public class data{
        public String shidu;
        public String  pm25;
        public String  pm10;
        public String quality;
        public String wendu;
        public String  ganmao;
        public yesterday yesterday;
        public List<yesterday> forecast;
    }

    public class yesterday{
        public String date;
        public String sunrise;
        public String high;
        public String low;
        public String sunset;
        public String aqi;
        public String fx;
        public String  fl;
        public String type;
        public String  notice;
    }





}
