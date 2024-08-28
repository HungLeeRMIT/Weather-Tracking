package app.dataQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class level2 extends JDBCConnection{
    public ArrayList<countryClass> getCountryListSimple() {
        ArrayList<countryClass> countryList = new ArrayList<countryClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM country ORDER BY country;";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String countryCode = results.getString("country_code");
                String countryName = results.getString("country");
                countryList.add(new countryClass(countryCode, countryName));
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return countryList;
    }

    public ArrayList<countryClass> getCountryList(String input, String orderBy) {
        ArrayList<countryClass> countryList = new ArrayList<countryClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String name = "SELECT * FROM country WHERE country LIKE '?1%' ORDER BY country ?2;";

            String pop = "SELECT p.country_code AS code,\n" + //
                    "       c.country AS name\n" + //
                    "  FROM country c\n" + //
                    "       JOIN\n" + //
                    "       population p ON c.country_code = p.country_code\n" + //
                    " WHERE p.year = '2013' AND \n" + //
                    "       c.country LIKE '?1%'\n" + //
                    " ORDER BY p.amount ?2;";

            String temp = "SELECT t.country_code AS code,\n" + //
                    "       c.country AS name\n" + //
                    "  FROM country c\n" + //
                    "       JOIN\n" + //
                    "       temperature t ON c.country_code = t.country_code\n" + //
                    " WHERE t.year = '2013' AND \n" + //
                    "       c.country LIKE '?1%'\n" + //
                    "       AND city_id IS NULL\n" + //
                    "       AND state_id IS NULL\n" + //
                    " ORDER BY t.average_temp ?2;";
                
            if (input.isEmpty() && orderBy.isEmpty()) {
                name = name.replace("?1", input);
                name = name.replace("?2", orderBy);
                ResultSet results = statement.executeQuery(name);
                // Process all of the results
                while (results.next()) {
                    String countryCode = results.getString("country_code");
                    String countryName = results.getString("country");
                    countryList.add(new countryClass(countryCode, countryName));
                }
            } else if (orderBy.isEmpty()) {
                name = name.replace("?1", input);
                name = name.replace("?2", orderBy);
                ResultSet results = statement.executeQuery(name);
                // Process all of the results
                while (results.next()) {
                    String countryCode = results.getString("country_code");
                    String countryName = results.getString("country");
                    countryList.add(new countryClass(countryCode, countryName));
                }
            } else if (orderBy.equals("nameasc") || orderBy.equals("namedesc")) {
                name = name.replace("?1", input);
                if (orderBy.equals("nameasc")) {
                    orderBy = "ASC";
                } else {
                    orderBy = "DESC";
                }
                name = name.replace("?2", orderBy);
                ResultSet results = statement.executeQuery(name);
                // Process all of the results
                while (results.next()) {
                    String countryCode = results.getString("country_code");
                    String countryName = results.getString("country");
                    countryList.add(new countryClass(countryCode, countryName));
                }
            } else if (orderBy.equals("popasc") || orderBy.equals("popdesc")) {
                pop = pop.replace("?1", input);
                if (orderBy.equals("popasc")) {
                    orderBy = "ASC";
                } else {
                    orderBy = "DESC";
                }
                pop = pop.replace("?2", orderBy);
                ResultSet results = statement.executeQuery(pop);
                // Process all of the results
                while (results.next()) {
                    String countryCode = results.getString("code");
                    String countryName = results.getString("name");
                    countryList.add(new countryClass(countryCode, countryName));
                }
            } else if (orderBy.equals("tempasc") || orderBy.equals("tempdesc")) {
                temp = temp.replace("?1", input);
                if (orderBy.equals("tempasc")) {
                    orderBy = "ASC";
                } else {
                    orderBy = "DESC";
                }
                temp = temp.replace("?2", orderBy);
                ResultSet results = statement.executeQuery(temp);
                // Process all of the results
                while (results.next()) {
                    String countryCode = results.getString("code");
                    String countryName = results.getString("name");
                    countryList.add(new countryClass(countryCode, countryName));
                }
            }
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return countryList;
    }

    public String getCountryName (String countryCode) {
        String countryName = "";
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT country FROM country WHERE country_code = '?1'";
            query = query.replace("?1", countryCode);
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                countryName = results.getString("country");
            }
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return countryName;
    }
    
    public List<Integer> getStartYear(String table, String country_code) {
        List<Integer> startYearList = new ArrayList<Integer>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT year FROM ?1 WHERE country_code = '?2' ORDER BY year ASC;";
            query = query.replace("?1", table);
            query = query.replace("?2", country_code);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year = results.getInt("year");
                startYearList.add(year);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return startYearList;
    }

    public List<Integer> getStartYearState(String table, String country_code, String state_id) {
        List<Integer> startYearList = new ArrayList<Integer>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT year FROM ?1 \n" + //
                    "WHERE country_code = '?2' AND state_id = ?3\n" + //
                    "AND city_id IS NULL\n" + //
                    "ORDER BY year ASC;";
            query = query.replace("?1", table);
            query = query.replace("?2", country_code);
            query = query.replace("?3", state_id);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year = results.getInt("year");
                startYearList.add(year);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return startYearList;
    }

    public List<Integer> getStartYearCity(String table, String country_code, String city_id) {
        List<Integer> startYearList = new ArrayList<Integer>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT year FROM ?1 \n" + //
                    "WHERE country_code = '?2' AND city_id = ?3\n" + //
                    "AND state_id IS NULL\n" + //
                    "ORDER BY year ASC;";
            query = query.replace("?1", table);
            query = query.replace("?2", country_code);
            query = query.replace("?3", city_id);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year = results.getInt("year");
                startYearList.add(year);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return startYearList;
    }

    public List<Integer> getEndYear(String table, String country_code, String startYear) {
        List<Integer> endYearList = new ArrayList<Integer>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT DISTINCT year\n" + //
                    "  FROM ?1\n" + //
                    " WHERE country_code = '?2' AND \n" + //
                    "       year > ?3\n" + //
                    " ORDER BY year ASC";
            query = query.replace("?1", table);
            query = query.replace("?2", country_code);
            query = query.replace("?3", startYear);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year = results.getInt("year");
                endYearList.add(year);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return endYearList;
    }

    public populationClass caclPopChange(String country_code, String startYear, String endYear) {
        populationClass populationChange = new populationClass();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "SELECT p2.amount AS end_pop,  \n" + //
                    "       p1.amount AS start_pop,  \n" + //
                    "       CASE     WHEN p1.amount <> 0  \n" + //
                    "               THEN ((p2.amount - p1.amount) * 100.0 / p1.amount)  \n" + //
                    "           ELSE NULL -- Handle the case when p1.amount is 0 to avoid division by zero  \n" + //
                    "           END AS percentage_change  \n" + //
                    "FROM population p1  \n" + //
                    "         JOIN population p2 ON p1.country_code = p2.country_code  \n" + //
                    "WHERE p1.year = ?1  \n" + //
                    "  AND p2.year = ?2  \n" + //
                    "  AND p1.country_code = '?3';  ";
            query = query.replace("?1", startYear);
            query = query.replace("?2", endYear);
            query = query.replace("?3", country_code);
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                Long startAmount = results.getLong("start_pop");
                Long endAmount = results.getLong("end_pop");
                double change = results.getDouble("percentage_change");
                populationChange = new populationClass(startAmount, endAmount, change);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return populationChange;
    }

    public ArrayList<populationClass> getCountryPop (String country_code, String startYear, String endYear) {
        ArrayList<populationClass> countryPop = new ArrayList<populationClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT p.year, p.amount\n" + //
                    "From population p JOIN country c\n" + //
                    "ON p.country_code = c.country_code\n" + //
                    "WHERE c.country_code = '?1'\n" + //
                    "AND p.year BETWEEN '?2' AND '?3';";
            query = query.replace("?1", country_code);
            query = query.replace("?2", startYear);
            query = query.replace("?3", endYear);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year = results.getInt("year");
                Long amount = results.getLong("amount");
                countryPop.add(new populationClass(year, amount));
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return countryPop;
    }

    public temperatureClass caclTempChange(String country_code, String startYear, String endYear) {
        temperatureClass temperatureChange = new temperatureClass();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "SELECT p2.average_temp AS endAmount,  \n" + //
                    "       p1.average_temp AS startAmount,  \n" + //
                    "       p1.min_temp     AS startAmountMin,  \n" + //
                    "       p1.max_temp     AS startAmountMax,  \n" + //
                    "       p2.min_temp     AS endAmountMin,  \n" + //
                    "       p2.max_temp     AS endAmountMax,  \n" + //
                    "       (((CAST(p2.average_temp AS numeric) - p1.average_temp) / p1.average_temp) *  \n" + //
                    "        100)           AS percentageChange,  \n" + //
                    "       (((CAST(p2.min_temp AS numeric) - p1.min_temp) / p1.min_temp) *  \n" + //
                    "        100)           AS percentageChangeMin,  \n" + //
                    "       (((CAST(p2.max_temp AS numeric) - p1.max_temp) / p1.max_temp) *  \n" + //
                    "        100)           AS percentageChangeMax  \n" + //
                    "FROM temperature p1  \n" + //
                    "         JOIN temperature p2  \n" + //
                    "              ON p1.country_code = p2.country_code  \n" + //
                    "                  AND p1.year = ?1 \n" + //
                    "                  AND p2.year = ?2\n" + //
                    "WHERE p1.country_code = '?3'  \n" + //
                    "  AND p1.city_id IS NULL  \n" + //
                    "  AND p2.city_id IS NULL  \n" + //
                    "  AND p2.state_id IS NULL  \n" + //
                    "  AND p1.state_id IS NULL";
            query = query.replace("?1", startYear);
            query = query.replace("?2", endYear);
            query = query.replace("?3", country_code);
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                double startAmount = results.getDouble("startAmount");
                double endAmount = results.getDouble("endAmount");
                double startAmountMin = results.getDouble("startAmountMin");
                double endAmountMin = results.getDouble("endAmountMin");
                double startAmountMax = results.getDouble("startAmountMax");
                double endAmountMax = results.getDouble("endAmountMax");
                double change = results.getDouble("percentageChange");
                double changeMin = results.getDouble("percentageChangeMin");
                double changeMax = results.getDouble("percentageChangeMax");
                temperatureChange = new temperatureClass(startAmount, endAmount, startAmountMin, endAmountMin, startAmountMax, endAmountMax, change, changeMin, changeMax);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return temperatureChange;
    }
    
    public ArrayList<stateClass> getStateList(String country_code) {
        ArrayList<stateClass> stateList = new ArrayList<stateClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "SELECT state_id, name\n" + //
                    "FROM state\n" + //
                    "WHERE country_code = '?'\n" + //
                    "ORDER BY name;";
            query = query.replace("?", country_code);
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String state_id = results.getString("state_id");
                String stateName = results.getString("name");
                stateList.add(new stateClass(state_id, stateName));
            }
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return stateList;
    }

    public ArrayList<cityClass> getCityList(String country_code) {
        ArrayList<cityClass> cityList = new ArrayList<cityClass>();
        try {
            // Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "SELECT city_id, name\n" + //
                    "FROM city\n" + //
                    "WHERE country_code = '?'\n" + //
                    "ORDER BY name;";
            query = query.replace("?", country_code);
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String city_id = results.getString("city_id"); 
                String cityName = results.getString("name"); 
                cityList.add(new cityClass(city_id, cityName)); 
            }
            statement.close();
        } catch (SQLException e) {
            // If there is an error, let's just print the error
            System.err.println(e.getMessage());
        }
        return cityList;
    }

    public ArrayList<temperatureClass> calcTempChangeAllStateORCity (String country_code, String startYear, String endYear, String view, String sortBy) {
        ArrayList<temperatureClass> tempChangeList = new ArrayList<temperatureClass>();
        try {
            // Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String state = "SELECT st.name AS name,\n" + //
                    "       p2.average_temp AS endAmount,\n" + //
                    "       p1.average_temp AS startAmount,\n" + //
                    "       p1.min_temp AS startAmountMin,\n" + //
                    "       p1.max_temp AS startAmountMax,\n" + //
                    "       p2.min_temp AS endAmountMin,\n" + //
                    "       p2.max_temp AS endAmountMax,\n" + //
                    "       ( ( (CAST (p2.average_temp AS NUMERIC) - p1.average_temp) / p1.average_temp) * 100) AS percentageChange,\n" + //
                    "       ( ( (CAST (p2.min_temp AS NUMERIC) - p1.min_temp) / p1.min_temp) * 100) AS percentageChangeMin,\n" + //
                    "       ( ( (CAST (p2.max_temp AS NUMERIC) - p1.max_temp) / p1.max_temp) * 100) AS percentageChangeMax\n" + //
                    "  FROM temperature p1\n" + //
                    "       JOIN\n" + //
                    "       temperature p2 ON p1.country_code = p2.country_code\n" + //
                    "       JOIN\n" + //
                    "       state st ON p1.state_id = st.state_id AND \n" + //
                    "                   p1.year = ?1 AND \n" + //
                    "                   p2.year = ?2\n" + //
                    " WHERE p1.country_code = '?3' AND \n" + //
                    "       p1.city_id IS NULL AND \n" + //
                    "       p2.city_id IS NULL AND \n" + //
                    "       p2.state_id = p1.state_id \n" + //
                    " ORDER BY ?4;";
            state = state.replace("?1", startYear);
            state = state.replace("?2", endYear);
            state = state.replace("?3", country_code);
            if (sortBy.isEmpty()) {
                state = state.replace("?4", "name");
            } else if (sortBy.equals("avg")) {
                state = state.replace("?4", "percentageChange DESC");
            } else if (sortBy.equals("min")) {
                state = state.replace("?4", "percentageChangeMin DESC");
            } else if (sortBy.equals("max")) {
                state = state.replace("?4", "percentageChangeMax DESC");
            }

            String city = "SELECT ci.name AS name,\n" + //
                    "       p2.average_temp AS endAmount,\n" + //
                    "       p1.average_temp AS startAmount,\n" + //
                    "       p1.min_temp AS startAmountMin,\n" + //
                    "       p1.max_temp AS startAmountMax,\n" + //
                    "       p2.min_temp AS endAmountMin,\n" + //
                    "       p2.max_temp AS endAmountMax,\n" + //
                    "       ( ( (CAST (p2.average_temp AS NUMERIC) - p1.average_temp) / p1.average_temp) * 100) AS percentageChange,\n" + //
                    "       ( ( (CAST (p2.min_temp AS NUMERIC) - p1.min_temp) / p1.min_temp) * 100) AS percentageChangeMin,\n" + //
                    "       ( ( (CAST (p2.max_temp AS NUMERIC) - p1.max_temp) / p1.max_temp) * 100) AS percentageChangeMax\n" + //
                    "  FROM temperature p1\n" + //
                    "       JOIN\n" + //
                    "       temperature p2 ON p1.country_code = p2.country_code\n" + //
                    "       JOIN\n" + //
                    "       city ci ON p1.city_id = ci.city_id AND \n" + //
                    "                  p1.year = ?1 AND \n" + //
                    "                  p2.year = ?2\n" + //
                    " WHERE p1.country_code = '?3' AND \n" + //
                    "       p1.city_id = p2.city_id AND \n" + //
                    "       p1.state_id IS NULL AND \n" + //
                    "       p2.state_id IS NULL \n" + //
                    " ORDER BY ?4;";;
            city = city.replace("?1", startYear);
            city = city.replace("?2", endYear);
            city = city.replace("?3", country_code);
            if (sortBy.isEmpty()) {
                city = city.replace("?4", "name");
            } else if (sortBy.equals("avg")) {
                city = city.replace("?4", "percentageChange DESC");
            } else if (sortBy.equals("min")) {
                city = city.replace("?4", "percentageChangeMin DESC");
            } else if (sortBy.equals("max")) {
                city = city.replace("?4", "percentageChangeMax DESC");
            }
                
            // Get Result
            ResultSet results = null;
            if (view.equals("state")) {
                results = statement.executeQuery(state); 
            } else if (view.equals("city")) {
                results = statement.executeQuery(city);
            }
            // Process all of the results
            while (results.next()) {
                String stateOrCityName = results.getString("name");
                double startAmount = results.getDouble("startAmount");
                double endAmount = results.getDouble("endAmount");
                double startAmountMin = results.getDouble("startAmountMin");
                double endAmountMin = results.getDouble("endAmountMin");
                double startAmountMax = results.getDouble("startAmountMax");
                double endAmountMax = results.getDouble("endAmountMax");
                double change = results.getDouble("percentageChange");
                double changeMin = results.getDouble("percentageChangeMin");
                double changeMax = results.getDouble("percentageChangeMax");
                temperatureClass temp = new temperatureClass(stateOrCityName, startAmount, endAmount, startAmountMin, endAmountMin, startAmountMax, endAmountMax, change, changeMin, changeMax);
                tempChangeList.add(temp);
            }
        }catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return tempChangeList;
    }

    //not done
    public ArrayList<countryClass> getAllCountry(String input) {
        ArrayList<countryClass> allCountry = new ArrayList<countryClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Country WHERE country LIKE '?'";
            query = query.replace("?", input+"%");
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String countryCode = results.getString("country_code");
                String countryName = results.getString("country");
                allCountry.add(new countryClass(countryCode, countryName));
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return allCountry;
    }

    public ArrayList<countryClass> getAllCountryPopulation(String orderBy) {
        ArrayList<countryClass> allCountry = new ArrayList<countryClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT c.country\n" + //
                    "from country c  \n" + //
                    "         JOIN population p  \n" + //
                    "              on c.country_code = p.country_code  \n" + //
                    "                  AND p.year = '2013'  \n" + //
                    "                  AND c.country_code <> 'WLD'\n" + //
                    "ORDER BY p.amount ?1;  ";
            query = query.replace("?1", orderBy);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String countryCode = results.getString("country_code");
                String countryName = results.getString("country_name");
                allCountry.add(new countryClass(countryCode, countryName));
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return allCountry;
    }

    public ArrayList<countryClass> getAllCountryTemperature(String orderBy) {
        ArrayList<countryClass> allCountry = new ArrayList<countryClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT c.country_code  \n" + //
                    "from country c  \n" + //
                    "         JOIN temperature t  \n" + //
                    "              on c.country_code = t.country_code  \n" + //
                    "                  and t.city_id IS NULL  \n" + //
                    "                  and t.state_id IS NULL  \n" + //
                    "                  AND t.year = '2013'  \n" + //
                    "ORDER BY t.average_temp DESC;  ";
            query = query.replace("?1", orderBy);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                String countryCode = results.getString("country_code");
                String countryName = results.getString("country_name");
                allCountry.add(new countryClass(countryCode, countryName));
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just print the error
            System.err.println(e.getMessage());
        }
        return allCountry;
    }

    

    

    

    public ArrayList<temperatureClass> getCountryTemperatures(String Country, String startYear, String endYear) {
        ArrayList<temperatureClass> countryTemp = new ArrayList<temperatureClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT t.year,\n" + //
                    "       t.average_temp AS AVG,\n" + //
                    "       t.min_temp AS min,\n" + //
                    "       t.max_temp AS max\n" + //
                    "  FROM temperature t\n" + //
                    "       JOIN\n" + //
                    "       country c ON t.country_code = c.country_code\n" + //
                    " WHERE Country = '?1' AND \n" + //
                    "       city_id IS NULL AND \n" + //
                    "       state_id IS NULL AND \n" + //
                    "       t.year BETWEEN '?2' AND '?3';";
            query = query.replace("?1", Country);
            query = query.replace("?2", startYear);
            query = query.replace("?3", endYear);

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                int year = results.getInt("year");
                double averageTemp = results.getDouble("AVG");
                double minTemp = results.getDouble("min");
                double maxTemp = results.getDouble("max");
                temperatureClass temp = new temperatureClass(year, averageTemp, maxTemp, minTemp);
                countryTemp.add(temp);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return countryTemp;
    }

}
