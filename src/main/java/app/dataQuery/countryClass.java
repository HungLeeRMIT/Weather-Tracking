package app.dataQuery;

public class countryClass {
    String countryCode;
    String countryName;

    countryClass(String countryCode, String countryName){
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public String getCountryName(){
        return countryName;
    }
}
