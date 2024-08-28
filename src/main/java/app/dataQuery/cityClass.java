package app.dataQuery;

public class cityClass {
    String city_id;
    String cityName;

    public cityClass(String city_id, String cityName){
        this.city_id = city_id;
        this.cityName = cityName;
    }

    public String getCity_id(){
        return city_id;
    }

    public String getCityName(){
        return cityName;
    }
}
