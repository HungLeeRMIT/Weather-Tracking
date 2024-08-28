package app.dataQuery;

public class temperatureClass {
    private int year;
    private double avgTemperature;
    private double maxTemperature;
    private double minTemperature;
    private String countryName;
    private int maxYear;
    private int minYear;
    private int countYear;
    private String startAmount;
    private String endAmount;
    private String startAmountMin;
    private String endAmountMin;
    private String startAmountMax;
    private String endAmountMax;
    private String change;
    private String changeMin;
    private String changeMax;
    private String stateOrCityName;
    private String stateName;
    private String cityName;
    private String avg;
    private String avgMin;
    private String avgMax;
    private String avgDiff;
    private String startYear;
    private String endYear;
    private String period;
    private String temperatureDifference;
    

    public temperatureClass(){
    }

    public temperatureClass(int year, double avgTemperature, double maxTemperature, double minTemperature){
        this.year = year;
        this.avgTemperature = avgTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public temperatureClass(int countYear, int maxYear, int minYear){
        this.countYear = countYear;
        this.maxYear = maxYear;
        this.minYear = minYear;
    }

    public temperatureClass(int year, double avgTemperature){
        this.year = year;
        this.avgTemperature = avgTemperature;
    }

    public temperatureClass(int year, double avgTemperature, String countryName){
        this.year = year;
        this.avgTemperature = avgTemperature;
        this.countryName = countryName;
    }

    public temperatureClass(int year, double avgTemperature, double maxTemperature, double minTemperature, String countryName){
        this.year = year;
        this.avgTemperature = avgTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.countryName = countryName;
    }

    public temperatureClass(double startAmount, double endAmount, double startAmountMin, double endAmountMin, double startAmountMax, double endAmountMax, double change, double changeMin, double changeMax){
        this.startAmount = String.format("%.2f", startAmount);
        this.endAmount = String.format("%.2f", endAmount);
        this.startAmountMin = String.format("%.2f", startAmountMin);
        this.endAmountMin = String.format("%.2f", endAmountMin);
        this.startAmountMax = String.format("%.2f", startAmountMax);
        this.endAmountMax = String.format("%.2f", endAmountMax);
        this.change = String.format("%.2f", change);
        this.changeMin = String.format("%.2f", changeMin);
        this.changeMax = String.format("%.2f", changeMax);
    }

    public temperatureClass(String stateName, double startAmount, double endAmount, double startAmountMin, double endAmountMin, double startAmountMax, double endAmountMax, double change, double changeMin, double changeMax){
        this.stateOrCityName = stateName;
        this.startAmount = String.format("%.2f", startAmount);
        this.endAmount = String.format("%.2f", endAmount);
        this.startAmountMin = String.format("%.2f", startAmountMin);
        this.endAmountMin = String.format("%.2f", endAmountMin);
        this.startAmountMax = String.format("%.2f", startAmountMax);
        this.endAmountMax = String.format("%.2f", endAmountMax);
        this.change = String.format("%.2f", change);
        this.changeMin = String.format("%.2f", changeMin);
        this.changeMax = String.format("%.2f", changeMax);
    }

    public temperatureClass(String period, String country, String state, String city, double avg, double min, double max, double diff) {
        this.period = period;
        this.countryName = country;
        this.stateName = state;
        this.cityName = city;
        this.avg = String.format("%.2f", avg);
        this.avgMin = String.format("%.2f", min);
        this.avgMax = String.format("%.2f", max);
        this.avgDiff = String.format("%.2f", diff);
    }

    public temperatureClass(String period, String country, String state, String city, double avg) {
        this.period = period;
        this.countryName = country;
        this.stateName = state;
        this.cityName = city;
        this.avg = String.format("%.2f", avg);
    }

    public temperatureClass(String period, String country, String state, String city, double avg, double temperatureDifference) {
        this.period = period;
        this.countryName = country;
        this.stateName = state;
        this.cityName = city;
        this.avg = String.format("%.2f", avg);
        this.temperatureDifference = String.format("%.5f", temperatureDifference);
    }

    public int getYear(){
        return year;
    }

    public double getavgTemperature(){
        return avgTemperature;
    }

    public double getmaxTemperature(){
        return maxTemperature;
    }

    public double getminTemperature(){
        return minTemperature;
    }

    public String getCountryName(){
        return countryName;
    }
    
    public int getMaxYear(){
        return maxYear;
    }

    public int getMinYear(){
        return minYear;
    }

    public int getCountYear(){
        return countYear;
    }

    public String getStartAmount(){
        return startAmount;
    }

    public String getEndAmount(){
        return endAmount;
    }

    public String getStartAmountMin(){
        return startAmountMin;
    }   

    public String getEndAmountMin(){
        return endAmountMin;
    }

    public String getStartAmountMax(){
        return startAmountMax;
    }

    public String getEndAmountMax(){
        return endAmountMax;
    }

    public String getChange(){
        return change;
    }

    public String getChangeMin(){
        return changeMin;
    }

    public String getChangeMax(){
        return changeMax;
    }

    public String getStateOrCityName(){
        return stateOrCityName;
    }

    public String getStateName(){
        return stateName;
    }

    public String getCityName(){
        return cityName;
    }

    public String getAvg(){
        return avg;
    }

    public String getAvgMin(){
        return avgMin;
    }

    public String getAvgMax(){
        return avgMax;
    }

    public String getAvgDiff(){
        return avgDiff;
    }

    public String getStartYear(){
        return startYear;
    }

    public String getEndYear(){
        return endYear;
    }

    public String getPeriod(){
        return period;
    }

    public String getTemperatureDifference(){
        return temperatureDifference;
    }

}
