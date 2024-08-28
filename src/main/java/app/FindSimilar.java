package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dataQuery.cityClass;
import app.dataQuery.countryClass;
import app.dataQuery.level2;
import app.dataQuery.level3;
import app.dataQuery.stateClass;
import app.dataQuery.temperatureClass;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class FindSimilar implements Handler {
    public static final String URL = "/FindSimilar.html";
    private static final String TEMPLATE = ("FindSimilar.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<String, Object>();
        level2 l2 = new level2();
        level3 l3 = new level3();
        String inputPeriod = context.sessionAttribute("inputPeriodSimilar");
        model.put("selectedPeriod", "Input Period");
        //populate country list
        ArrayList<countryClass> countries = l2.getCountryListSimple();
        model.put("countries", countries);
        String country_code = context.sessionAttribute("selectedCountrySimilar");
        model.put("selectedCountrySimilar", country_code);
        
        //populate city list and state list
        if (country_code != null) {
            ArrayList<stateClass> states = l2.getStateList(country_code);
            model.put("states", states);
            String state = context.sessionAttribute("selectedStateSimilar");
            model.put("selectedStateSimilar", state);
            ArrayList<cityClass> cities = l2.getCityList(country_code);
            model.put("cities", cities);
            String city = context.sessionAttribute("selectedCitySimilar");
            model.put("selectedCitySimilar", city);

            //populate start year list
            String table = "temperature";
            List<Integer> startYears = new ArrayList<Integer>();
            if (state == null && city == null) {
                startYears = l2.getStartYear(table, country_code);
            } else if(state != null && city == null) {
                startYears = l2.getStartYearState(table, country_code, state);
            } else if(state == null && city != null) {
                startYears = l2.getStartYearCity(table, country_code, city);
            }
            model.put("startYears", startYears);
            String startYear = context.sessionAttribute("startYearSimilar");
            model.put("selectedStartYear", startYear);
            inputPeriod = context.sessionAttribute("inputPeriodSimilar");
            if (inputPeriod == null) {
                model.put("selectedPeriod", "Input Period");
            } else {
                //calculate the base period
                temperatureClass basePeriod = new temperatureClass();
                if (state == null && city == null) {
                    basePeriod = l3.calBaseTempCountry(country_code, startYear, inputPeriod);
                } else if(state != null && city == null) {
                    basePeriod = l3.calBaseTempCountryState(country_code, state, startYear, inputPeriod);
                } else if(state == null && city != null) {
                    basePeriod = l3.calBaseTempCountryCity(country_code, city, startYear, inputPeriod);
                }
                model.put("baseCountry", basePeriod);
                String limit = context.sessionAttribute("limit");
                if (limit != null) {
                    //calculate the similar cities
                    ArrayList<temperatureClass> similarCountries = new ArrayList<temperatureClass>();
                    if (state == null && city == null) {
                        similarCountries = l3.findSimilarPeriodCountry(country_code, startYear, inputPeriod, limit);
                    } else if(state != null && city == null) {
                        similarCountries = l3.findSimilarPeriodCountryState(country_code, state, startYear, inputPeriod, limit);
                    } else if(state == null && city != null) {
                        similarCountries = l3.findSimilarPeriodCountryCity(country_code, city, startYear, inputPeriod, limit);
                    }
                    model.put("similarCountries", similarCountries);
                    model.put("similarSize", similarCountries.size());
                }
            }
        }
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }
}
