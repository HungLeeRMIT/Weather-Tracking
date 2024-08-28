package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dataQuery.cityClass;
import app.dataQuery.countryClass;
import app.dataQuery.level2;
import app.dataQuery.stateClass;
import app.dataQuery.temperatureClass;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class Temperature implements Handler {
    public static final String URL = "/Temperature.html";
    private static final String TEMPLATE = ("Temperature.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<String, Object>();
        level2 l2 = new level2();

        //handle search input
        String searchInput = context.sessionAttribute("searchInputTemp");
        String sortBy = context.sessionAttribute("sortByTemp");
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
        String selectedCountry = context.sessionAttribute("selectedCountryTemp");
        if (selectedCountry == null) {
            selectedCountry = "WLD";
        }
        String countryName = l2.getCountryName(selectedCountry);
        model.put("selectedCountry", countryName);

        //populate state list
        ArrayList<stateClass> states = l2.getStateList(selectedCountry);
        model.put("states", states); 

        //populate city list
        ArrayList<cityClass> cities = l2.getCityList(selectedCountry);
        model.put("cities", cities);

        //handle start year - end year
        String table = "temperature";
        List<Integer> startYears = l2.getStartYear(table, selectedCountry);
        model.put("startYears", startYears);
        String selectedStartYear = context.sessionAttribute("startYearTemp");
        model.put("selectedStartYear", selectedStartYear);
        System.out.println(selectedStartYear);
        if (selectedStartYear != null) {
            List<Integer> endYears = l2.getEndYear(table, selectedCountry, selectedStartYear);
            model.put("endYears", endYears);
        }
        String selectedEndYear = context.sessionAttribute("endYearTemp");
        if (selectedEndYear != null) {
            model.put("selectedEndYear", selectedEndYear);
        }

        //handle data
        if (selectedStartYear != null && selectedEndYear != null) {
            temperatureClass temperatureChange = l2.caclTempChange(selectedCountry, selectedStartYear, selectedEndYear);
            model.put("temperatures", temperatureChange);

            //populate state percentage change table
            String view = "state";
            String rankBy = context.sessionAttribute("rankByTemp");
            model.put("rank", rankBy);
            if (rankBy == null) {
                rankBy = "";
            }
            ArrayList<temperatureClass> stateChange = l2.calcTempChangeAllStateORCity(selectedCountry, selectedStartYear, selectedEndYear, view, rankBy);
            model.put("stateChange", stateChange);

            //populate city percentage change table
            view = "city";
            ArrayList<temperatureClass> cityChange = l2.calcTempChangeAllStateORCity(selectedCountry, selectedStartYear, selectedEndYear, view, rankBy);
            model.put("cityChange", cityChange);
        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        String viewBy = context.sessionAttribute("viewByTemp");
        model.put("viewBy", viewBy);
        context.render(TEMPLATE, model);
    }
}
