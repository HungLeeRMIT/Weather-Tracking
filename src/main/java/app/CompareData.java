package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class CompareData implements Handler {
    public static final String URL = "/CompareData.html";
    private static final String TEMPLATE = ("CompareData.html");

    public static ArrayList<temperatureClass> tableCompare = new ArrayList<temperatureClass>();

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<String, Object>();
        level2 l2 = new level2();
        level3 l3 = new level3();
        String inputPeriod = context.sessionAttribute("inputPeriod");
        model.put("selectedPeriod", "Input Period");
        //populate country list
        ArrayList<countryClass> countries = l2.getCountryListSimple();
        model.put("countries", countries);
        String country_code = context.sessionAttribute("selectedCountryCompare");
        model.put("selectedCountryCompare", country_code);
        
        //populate city list and state list
        if (country_code != null) {
            ArrayList<stateClass> states = l2.getStateList(country_code);
            model.put("states", states);
            String state = context.sessionAttribute("selectedStateCompare");
            model.put("selectedStateCompare", state);
            ArrayList<cityClass> cities = l2.getCityList(country_code);
            model.put("cities", cities);
            String city = context.sessionAttribute("selectedCityCompare");
            model.put("selectedCityCompare", city);

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
            String startYear = context.sessionAttribute("startYearCompare");
            model.put("selectedStartYear", startYear);
            inputPeriod = context.sessionAttribute("inputPeriod");
            if (inputPeriod == null) {
                model.put("selectedPeriod", "Input Period");
            } else {
                model.put("selectedPeriod", inputPeriod);
                //String addRow = context.sessionAttribute("addRow");
                temperatureClass row = new temperatureClass();                
                row = l3.calTemperatureChangeSelectRegion(country_code, state, city, startYear, inputPeriod);
                tableCompare.add(row);
            }
        }
        String sort = context.sessionAttribute("sort");
        if (sort != null) {
        model.put("rankBy", sort);
        ArrayList<temperatureClass> tableCompareSorted = new ArrayList<temperatureClass>();
            tableCompareSorted = sortTableCompare(tableCompare, sort);
            model.put("tableCompare", tableCompareSorted);
        } else {
        model.put("tableCompare", tableCompare);
        }
        String lowerBound = context.sessionAttribute("lowerBound");
        String upperBound = context.sessionAttribute("upperBound");
        if (lowerBound != null && upperBound != null) {
            ArrayList<temperatureClass> tableCompareFiltered = new ArrayList<temperatureClass>();
            tableCompareFiltered = filterByTemperatureChangeRange(Double.parseDouble(lowerBound), Double.parseDouble(upperBound));
            model.put("tableCompare", tableCompareFiltered);
        } else {
            model.put("tableCompare", tableCompare);
        }
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }

    // Method to sort the tableCompare ArrayList by attribute avgDiff
    private ArrayList<temperatureClass> sortTableCompare(ArrayList<temperatureClass> tableCompare, String sortBy) {
        Collections.sort(tableCompare, new Comparator<temperatureClass>() {
            @Override
            public int compare(temperatureClass t1, temperatureClass t2) {
                double temp1 = Double.parseDouble(t1.getAvgDiff());
                double temp2 = Double.parseDouble(t2.getAvgDiff());
                if (sortBy.equals("asc")) {
                    return Double.compare(temp1, temp2);
                } else {
                    return Double.compare(temp2, temp1);
                }
            }
        });
        return tableCompare;
    }

    // Method to filter the tableCompare ArrayList if the user input a temperature change range
    public ArrayList<temperatureClass> filterByTemperatureChangeRange(double lowerBound, double upperBound) {
        ArrayList<temperatureClass> tableCompareFiltered = new ArrayList<temperatureClass>();
        for (int i = 0; i < tableCompare.size(); i++) {
            double temp = Double.parseDouble(tableCompare.get(i).getAvgDiff());
            if (temp >= lowerBound && temp <= upperBound) {
                tableCompareFiltered.add(tableCompare.get(i));
            }
        }
        return tableCompareFiltered;
    }
}


