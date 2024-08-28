package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.dataQuery.level1;
import app.dataQuery.populationClass;
import app.dataQuery.temperatureClass;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Landing implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    // Name of the Thymeleaf HTML template page in the resources folder
    private static final String TEMPLATE = ("landing.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<String, Object>();

        //create a new level1 object
        level1 level1 = new level1();
        //get the total number of countries
        int countCountry = level1.getCountryCount();
        model.put("country_numb", new String(countCountry + " countries"));
        
        //Identify the total number of years of available data for global population
        //Present the year range (first and last year) for available data population
        populationClass totalYearPopulation = level1.getTotalYearPopulation();
        int countYearPop = totalYearPopulation.getCountYear();
        int maxYearPop = totalYearPopulation.getMaxYear();
        int minYearPop = totalYearPopulation.getMinYear();
        model.put("pop_numb", new String(countYearPop + " years"));
        model.put("pop_start_end", new String("From " + minYearPop + " to " + maxYearPop));
        
        //Identify the total number of years of available data for global temperature
        //Present the year range (first and last year) for available data temperature
        temperatureClass totalYearTemperature = level1.getTotalYearTemperature();
        int countYearTemp = totalYearTemperature.getCountYear();
        int maxYearTemp = totalYearTemperature.getMaxYear();
        int minYearTemp = totalYearTemperature.getMinYear();
        model.put("temp_numb", new String(countYearTemp + " years"));
        model.put("temp_start_end", new String("From " + minYearTemp + " to " + maxYearTemp));

        //Chart Population
        ArrayList<populationClass> worldPopulation = level1.getWorldPopulation();
        model.put("worldPopulation", worldPopulation);
        long maxPop= 0 ;
            for (int i =0; i< worldPopulation.size(); i++) {
                long currentPop = worldPopulation.get(i).getPopulation();
                if (currentPop > maxPop){
                    maxPop=currentPop;
                    
                }
            }
            if (maxPop>0) {
                model.put("maxPopLanding", maxPop);
            }
        

        ArrayList<temperatureClass> worldTemperature = level1.getWorldTemperature();
        model.put("worldTemperature", worldTemperature);
        double maxTemp=0;
        for (int i =0; i< worldTemperature.size(); i++) {
            double currentTemp = worldTemperature.get(i).getavgTemperature();
            if (currentTemp > maxTemp){
                maxTemp=currentTemp;
            }
        }
        if (maxTemp>0) {
            model.put("maxTempLanding", maxTemp);
        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

}
