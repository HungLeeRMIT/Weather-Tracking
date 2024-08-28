package app.dataQuery;

public class populationClass {
    private int year;
    private Long population;
    private int countryName;
    private int maxYear;
    private int minYear;
    private int countYear;
    private String populationF;
    private String startPopulation;
    private String endPopulation;
    private String change;

    public populationClass(){
    }
    
    public populationClass(Long startPopulation, Long endPopulation, double change){
        this.startPopulation = String.format("%,d", startPopulation);
        this.endPopulation = String.format("%,d", endPopulation);
        this.change = String.format("%.2f", change);
    }
    
    public populationClass(int countYear, int maxYear, int minYear){
        this.countYear = countYear;
        this.maxYear = maxYear;
        this.minYear = minYear;
    }

    public populationClass(int year, Long population){
        this.year = year;
        this.population = population;
        this.populationF = String.format("%,d", population);
    }

    public populationClass(int year, Long population, int countryName){
        this.year = year;
        this.population = population;
        this.countryName = countryName;
    }

    public int getYear(){
        return year;
    }

    public Long getPopulation(){
        return population;
    }

    public int getCountryName(){
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

    public String getStartPopulation(){
        return startPopulation;
    }

    public String getEndPopulation(){
        return endPopulation;
    }

    public String getChange(){
        return change;
    }

    public String getPopulationF(){
        return populationF;
    }

}
