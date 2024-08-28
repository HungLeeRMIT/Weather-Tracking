package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dataQuery.countryClass;
import app.dataQuery.level2;
import app.dataQuery.populationClass;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class Population implements Handler {
    public static final String URL = "/Population.html";
    private static final String TEMPLATE = ("Population.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<String, Object>();
        level2 l2 = new level2();

        //handle search input
        String searchInput = context.sessionAttribute("searchInput");
        String sortBy = context.sessionAttribute("sortBy");
        model.put("sortby", sortBy);
        if (searchInput == null) {
            searchInput = "";
        } 
        if (sortBy == null) {
            sortBy = "";
        }
        ArrayList<countryClass> countries = l2.getCountryList(searchInput, sortBy);
        model.put("countries", countries);

        //handle selected country
        String selectedCountry = context.sessionAttribute("selectedCountry");
        
        if (selectedCountry == null) {
            selectedCountry = "WLD";
        }
        model.put("countryCode", selectedCountry);
        String countryName = l2.getCountryName(selectedCountry);
        model.put("selectedCountry", countryName);

        //handle start year - end year
        String table = "population";
        List<Integer> startYears = l2.getStartYear(table, selectedCountry);
        model.put("startYears", startYears);
        String selectedStartYear = context.sessionAttribute("startYear");
        model.put("selectedStartYear", selectedStartYear);
        if (selectedStartYear != null) {
            List<Integer> endYears = l2.getEndYear(table, selectedCountry, selectedStartYear);
            model.put("endYears", endYears);
        }
        String selectedEndYear = context.sessionAttribute("endYear");
        if (selectedEndYear != null) {
            model.put("selectedEndYear", selectedEndYear);
        }
        if (selectedStartYear != null && selectedEndYear != null) {
            //handle population change
            populationClass popChange = l2.caclPopChange(selectedCountry, selectedStartYear, selectedEndYear);
            model.put("popChange", popChange);
            //handle population table
            ArrayList<populationClass> populationTable = l2.getCountryPop(selectedCountry, selectedStartYear, selectedEndYear);
            model.put("countryPop", populationTable);
            
            long maxPop= 0 ;
            for (int i =0; i< populationTable.size(); i++) {
                long currentPop = populationTable.get(i).getPopulation();
                if (currentPop > maxPop){
                    maxPop=currentPop;
                    
                }
            }
            if (maxPop>0) {
                model.put("maxPop", maxPop);
            }
        }
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }
}
