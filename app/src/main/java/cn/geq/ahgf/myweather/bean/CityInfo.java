package cn.geq.ahgf.myweather.bean;

public class CityInfo {
    private  String city_code;
    private  String city_name;

    public CityInfo(String city_code, String city_name) {
        this.city_code = city_code;
        this.city_name = city_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
