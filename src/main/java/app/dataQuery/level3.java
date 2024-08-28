package app.dataQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.Temperature;

public class level3 extends JDBCConnection {
    public temperatureClass calTemperatureChangeSelectRegion (String country_code, String state_id, String city_id, String startYear, String inputPeriod) {
        temperatureClass temperatureChange = new temperatureClass();
        String endYear = String.valueOf(Integer.parseInt(startYear) + Integer.parseInt(inputPeriod) - 1);
        try {
            // Connect to JDBC database3            
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "WITH TempPeriodCountry AS (\n" + //
                    "    SELECT c.country AS Country,\n" + //
                    "           NULL AS State,\n" + //
                    "           NULL AS City,\n" + //
                    "           t.average_temp AS AVG,\n" + //
                    "           t.min_temp AS Min,\n" + //
                    "           t.max_temp AS Max,\n" + //
                    "           t.year\n" + //
                    "      FROM temperature t\n" + //
                    "           JOIN country c ON t.country_code = c.country_code\n" + //
                    "     WHERE t.year BETWEEN ?1 AND ?2 \n" + //
                    "           AND t.country_code = '?3' \n" + //
                    "           AND t.state_id ?4 \n" + //
                    "           AND t.city_id ?5\n" + //
                    "),\n" + //
                    "TempPeriodState AS (\n" + //
                    "    SELECT c.country AS Country,\n" + //
                    "           st.name AS State,\n" + //
                    "           NULL AS City,\n" + //
                    "           t.average_temp AS AVG,\n" + //
                    "           t.min_temp AS Min,\n" + //
                    "           t.max_temp AS Max,\n" + //
                    "           t.year\n" + //
                    "      FROM temperature t\n" + //
                    "           JOIN state st ON t.state_id = st.state_id\n" + //
                    "           JOIN country c ON t.country_code = c.country_code\n" + //
                    "     WHERE t.year BETWEEN ?1 AND ?2 \n" + //
                    "           AND t.country_code = '?3' \n" + //
                    "           AND t.state_id ?6 \n" + //
                    "           AND t.city_id IS NULL\n" + //
                    "),\n" + //
                    "TempPeriodCity AS (\n" + //
                    "    SELECT c.country AS Country,\n" + //
                    "           NULL AS State,\n" + //
                    "           ci.name AS City,\n" + //
                    "           t.average_temp AS AVG,\n" + //
                    "           t.min_temp AS Min,\n" + //
                    "           t.max_temp AS Max,\n" + //
                    "           t.year\n" + //
                    "      FROM temperature t\n" + //
                    "           JOIN city ci ON t.city_id = ci.city_id\n" + //
                    "           JOIN country c ON t.country_code = c.country_code\n" + //
                    "     WHERE t.year BETWEEN ?1 AND ?2 \n" + //
                    "           AND t.country_code = '?3' \n" + //
                    "           AND t.state_id IS NULL \n" + //
                    "           AND t.city_id ?7\n" + //
                    ")\n" + //
                    "SELECT Country,\n" + //
                    "       State,\n" + //
                    "       City,\n" + //
                    "       AVG(AVG) AS [Period AVG],\n" + //
                    "       AVG(Min) AS [Period AVG Min],\n" + //
                    "       AVG(Max) AS [Period AVG Max],\n" + //
                    "       (AVG(Max) - AVG(Min)) AS [Difference in AVG],\n" + //
                    "       Min(year) AS yearMin,\n" + //
                    "       Max(year) AS yearMax\n" + //
                    "FROM (\n" + //
                    "    SELECT * FROM TempPeriodCountry\n" + //
                    "    UNION ALL\n" + //
                    "    SELECT * FROM TempPeriodState\n" + //
                    "    UNION ALL\n" + //
                    "    SELECT * FROM TempPeriodCity\n" + //
                    ") AS CombinedData\n" + //
                    "GROUP BY Country, State, City;";
            query = query.replace("?1", startYear);
            query = query.replace("?2", endYear);
            query = query.replace("?3", country_code);
            if (state_id == null && city_id == null) {
                query = query.replace("?4", "IS NULL")
                        .replace("?5", "IS NULL")
                        .replace("?6", "= 0")
                        .replace("?7", "= 0");
            } else if (state_id != null && city_id == null) {
                query = query.replace("?4", "= 0")
                        .replace("?5", "= 0")
                        .replace("?6", "= " + state_id)
                        .replace("?7", "= 0");
            } else if (state_id == null && city_id != null) {
                query = query.replace("?4", "= 0")
                        .replace("?5", "= 0")
                        .replace("?6", "= 0")
                        .replace("?7", "= " + city_id);
            }
            // Get Results
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String country = results.getString("Country");
                String state = results.getString("State");
                String city = results.getString("City");
                double avg = results.getDouble("Period AVG");
                double min = results.getDouble("Period AVG Min");
                double max = results.getDouble("Period AVG Max");
                double diff = results.getDouble("Difference in AVG");
                String yearMax = results.getString("yearMax");
                String yearMin = results.getString("yearMin");
                String period = yearMin + " - " + yearMax;
                temperatureChange = new temperatureClass(period, country, state, city, avg, min, max, diff);
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return temperatureChange;
    }

    public temperatureClass calBaseTempCountry(String Country_code, String starYear, String inputPeriod) {
        temperatureClass baseTemperature = new temperatureClass();
        String endYear = String.valueOf(Integer.parseInt(starYear) + Integer.parseInt(inputPeriod) - 1);
        String period = starYear + " - " + endYear;
        try {// Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "SELECT c.country AS country,\n" + //
                    "                    t.state_id AS state,\n" + //
                    "                    t.city_id AS city,\n" + //
                    "                    AVG(average_temp) AS AVGTemp,\n" + //
                    "                    Max(t.year) AS yearEnd,\n" + //
                    "                    Min(t.year) AS yearStart\n" + //
                    "                    FROM temperature t JOIN country c\n" + //
                    "                    ON t.country_code = c.country_code\n" + //
                    "                    WHERE t.country_code = '?1'\n" + //
                    "                    AND city_id IS NULL\n" + //
                    "                    AND state_id IS NULL\n" + //
                    "                    AND year >= ?2\n" + //
                    "                    AND year <= ?3;";
            query = query.replace("?1", Country_code)
                            .replace("?2", starYear)
                            .replace("?3", endYear);
            // Get Results
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String country = results.getString("country");
                String state = results.getString("state");
                String city = results.getString("city");
                double avg = results.getDouble("AVGTemp");
                baseTemperature = new temperatureClass(period, country, state, city, avg);
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return baseTemperature;
    }

    public temperatureClass calBaseTempCountryState (String Country_code, String state_id, String starYear, String inputPeriod) {
        temperatureClass baseTemperature = new temperatureClass();
        String endYear = String.valueOf(Integer.parseInt(starYear) + Integer.parseInt(inputPeriod) - 1);
        String period = starYear + " - " + endYear;
        try {// Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "SELECT c.country AS country,\n" + //
                    "            st.name AS state,\n" + //
                    "            t.city_id AS city,\n" + //
                    "            AVG(average_temp) AS AVGTemp,\n" + //
                    "            Max(t.year) AS yearEnd,\n" + //
                    "            Min(t.year) AS yearStart\n" + //
                    "            FROM temperature t JOIN state st\n" + //
                    "            ON t.state_id = st.state_id\n" + //
                    "            JOIN country c ON t.country_code = c.country_code\n" + //
                    "            WHERE t.country_code = '?1'\n" + //
                    "            AND t.city_id IS NULL\n" + //
                    "            AND t.state_id = ?2\n" + //
                    "            AND year >= ?3\n" + //
                    "            AND year <= ?4;";
            query = query.replace("?1", Country_code)
                            .replace("?2", state_id)
                            .replace("?3", starYear)
                            .replace("?4", endYear);
            // Get Results
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String country = results.getString("country");
                String state = results.getString("state");
                String city = results.getString("city");
                double avg = results.getDouble("AVGTemp");
                baseTemperature = new temperatureClass(period, country, state, city, avg);
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return baseTemperature;
    }
    public temperatureClass calBaseTempCountryCity (String Country_code, String city_id, String starYear, String inputPeriod) {
        temperatureClass baseTemperature = new temperatureClass();
        String endYear = String.valueOf(Integer.parseInt(starYear) + Integer.parseInt(inputPeriod) - 1);
        try {// Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "SELECT c.country AS country,\n" + //
                    "            t.state_id AS state,\n" + //
                    "            ci.name AS city,\n" + //
                    "            AVG(average_temp) AS AVGTemp,\n" + //
                    "            Max(t.year) AS yearEnd,\n" + //
                    "            Min(t.year) AS yearStart\n" + //
                    "            FROM temperature t JOIN city ci\n" + //
                    "            ON t.city_id = ci.city_id\n" + //
                    "            JOIN country c ON t.country_code = c.country_code\n" + //
                    "            WHERE t.country_code = '?1'\n" + //
                    "            AND t.city_id = ?2\n" + //
                    "            AND t.state_id IS NULL\n" + //
                    "            AND year >= ?3\n" + //
                    "            AND year <= ?4";
            query = query.replace("?1", Country_code)
                    .replace("?2", city_id)
                    .replace("?3", starYear)
                    .replace("?4", endYear);
            // Get Results
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String country = results.getString("country");
                String state = results.getString("state");
                String city = results.getString("city");
                double avg = results.getDouble("AVGTemp");
                String yearEnd = results.getString("yearEnd");
                String yearStart = results.getString("yearStart");
                String period = yearStart + " - " + yearEnd;
                baseTemperature = new temperatureClass(period, country, state, city, avg);
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return baseTemperature;
    }

    public ArrayList<temperatureClass> findSimilarPeriodCountry (String Country_code, String starYear, String inputPeriod, String limit){
        ArrayList<temperatureClass> similarPeriodCountry = new ArrayList<temperatureClass>();
        String endYear = String.valueOf(Integer.parseInt(starYear) + Integer.parseInt(inputPeriod) - 1);
        String periodQuery = String.valueOf(Integer.parseInt(inputPeriod) - 1);
        try {// Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "WITH RECURSIVE\n" + //
                    "    BaseTemperature AS (\n" + //
                    "        SELECT AVG(average_temp) AS base_average_temperature\n" + //
                    "        FROM temperature\n" + //
                    "        WHERE country_code = '?1'\n" + //
                    "        AND city_id IS NULL\n" + //
                    "        AND state_id IS NULL\n" + //
                    "        AND year >= ?2\n" + //
                    "        AND year <= ?3\n" + //
                    "    ),\n" + //
                    "    YearRange AS (\n" + //
                    "        SELECT MIN(year) AS min_year, MAX(year) AS max_year\n" + //
                    "        FROM temperature\n" + //
                    "    ),\n" + //
                    "    Periods(start_year, end_year) AS (\n" + //
                    "        SELECT min_year, min_year + ?4\n" + //
                    "        FROM YearRange\n" + //
                    "        UNION ALL\n" + //
                    "        SELECT start_year + ?4, end_year + ?4\n" + //
                    "        FROM Periods\n" + //
                    "        WHERE start_year + ?4 <= (SELECT max_year FROM YearRange)\n" + //
                    "    )\n" + //
                    "SELECT\n" + //
                    "    p.start_year AS periodStartYear,\n" + //
                    "    p.end_year AS periodEndYear,\n" + //
                    "    c.country AS country_name,\n" + //
                    "    t.city_id AS city,\n" + //
                    "    t.state_id AS state,\n" + //
                    "    AVG(t.average_temp) AS averageTemperature,\n" + //
                    "    ABS(AVG(t.average_temp) - (SELECT base_average_temperature FROM BaseTemperature)) AS temperatureDifference\n" + //
                    "FROM\n" + //
                    "    Periods p\n" + //
                    "    JOIN\n" + //
                    "    temperature t ON t.year >= p.start_year AND t.year < p.end_year\n" + //
                    "    JOIN country c ON t.country_code = c.country_code\n" + //
                    "    CROSS JOIN\n" + //
                    "    BaseTemperature bt\n" + //
                    "GROUP BY\n" + //
                    "    p.start_year, p.end_year, t.country_code, bt.base_average_temperature, t.state_id, t.city_id\n" + //
                    "HAVING\n" + //
                    "    ABS(AVG(t.average_temp) - bt.base_average_temperature) <= 1\n" + //
                    "    AND city_id IS NULL\n" + //
                    "    AND state_id IS NULL\n" + //
                    "ORDER BY\n" + //
                    "    temperatureDifference LIMIT ?5";
            query = query.replace("?1", Country_code)
                            .replace("?2", starYear)
                            .replace("?3", endYear)
                            .replace("?4", periodQuery)
                            .replace("?5", limit);
            // Get Results
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String periodStart = results.getString("periodStartYear");
                String periodEnd = results.getString("periodEndYear");
                String periodResult = periodStart + " - " + periodEnd;
                String country = results.getString("country_name");
                String state = results.getString("state");
                String city = results.getString("city");
                double avg = results.getDouble("averageTemperature");
                double temperatureDifference = results.getDouble("temperatureDifference");
                similarPeriodCountry.add(new temperatureClass(periodResult, country, state, city, avg, temperatureDifference));
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return similarPeriodCountry;
    }

    public ArrayList<temperatureClass> findSimilarPeriodCountryState (String Country_code, String state_id, String starYear, String inputPeriod, String limit){
        ArrayList<temperatureClass> similarPeriodCountryState = new ArrayList<temperatureClass>();
        String endYear = String.valueOf(Integer.parseInt(starYear) + Integer.parseInt(inputPeriod) - 1);
        String periodQuery = String.valueOf(Integer.parseInt(inputPeriod) - 1);
        try {// Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "WITH RECURSIVE\n" + //
                    "    BaseTemperature AS (\n" + //
                    "        SELECT AVG(average_temp) AS base_average_temperature\n" + //
                    "        FROM temperature\n" + //
                    "        WHERE country_code = '?1'\n" + //
                    "        AND city_id IS NULL\n" + //
                    "        AND state_id = ?2\n" + //
                    "        AND year >= ?3\n" + //
                    "        AND year <= ?4\n" + //
                    "    ),\n" + //
                    "    YearRange AS (\n" + //
                    "        SELECT MIN(year) AS min_year, MAX(year) AS max_year\n" + //
                    "        FROM temperature\n" + //
                    "    ),\n" + //
                    "    Periods(start_year, end_year) AS (\n" + //
                    "        SELECT min_year, min_year + ?5\n" + //
                    "        FROM YearRange\n" + //
                    "        UNION ALL\n" + //
                    "        SELECT start_year + ?5, end_year + ?5\n" + //
                    "        FROM Periods\n" + //
                    "        WHERE start_year + ?5 <= (SELECT max_year FROM YearRange)\n" + //
                    "    )\n" + //
                    "SELECT\n" + //
                    "    p.start_year AS periodStartYear,\n" + //
                    "    p.end_year AS periodEndYear,\n" + //
                    "    c.country AS country_name,\n" + //
                    "    t.city_id AS city,\n" + //
                    "    st.name AS state,\n" + //
                    "    AVG(t.average_temp) AS averageTemperature,\n" + //
                    "    MAX(t.max_temp) AS maxTemperature,\n" + //
                    "    MIN(t.min_temp) AS minTemperature,\n" + //
                    "    ABS(AVG(t.average_temp) - (SELECT base_average_temperature FROM BaseTemperature)) AS temperatureDifference\n" + //
                    "FROM\n" + //
                    "    Periods p\n" + //
                    "    JOIN\n" + //
                    "    temperature t ON t.year >= p.start_year AND t.year < p.end_year\n" + //
                    "    JOIN country c ON t.country_code = c.country_code\n" + //
                    "    JOIN state st ON t.state_id = st.state_id\n" + //
                    "    CROSS JOIN\n" + //
                    "    BaseTemperature bt\n" + //
                    "GROUP BY\n" + //
                    "    p.start_year, p.end_year, t.country_code, bt.base_average_temperature, t.state_id, t.city_id\n" + //
                    "HAVING\n" + //
                    "    ABS(AVG(t.average_temp) - bt.base_average_temperature) <= 1\n" + //
                    "    AND t.city_id IS NULL\n" + //
                    "ORDER BY\n" + //
                    "    temperatureDifference LIMIT ?6";
            query = query.replace("?1", Country_code)
                            .replace("?2", state_id)
                            .replace("?3", starYear)
                            .replace("?4", endYear)
                            .replace("?5", periodQuery)
                            .replace("?6", limit);
            // Get Results
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String periodStart = results.getString("periodStartYear");
                String periodEnd = results.getString("periodEndYear");
                String periodResult = periodStart + " - " + periodEnd;
                String country = results.getString("country_name");
                String state = results.getString("state");
                String city = results.getString("city");
                double avg = results.getDouble("averageTemperature");
                double temperatureDifference = results.getDouble("temperatureDifference");
                similarPeriodCountryState.add(new temperatureClass(periodResult, country, state, city, avg, temperatureDifference));
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return similarPeriodCountryState;
    }

    public ArrayList<temperatureClass> findSimilarPeriodCountryCity (String Country_code, String city_id, String starYear, String inputPeriod, String limit){
        ArrayList<temperatureClass> similarPeriodCountryState = new ArrayList<temperatureClass>();
        String endYear = String.valueOf(Integer.parseInt(starYear) + Integer.parseInt(inputPeriod) - 1);
        String periodQuery = String.valueOf(Integer.parseInt(inputPeriod) - 1);
        try {// Connect to JDBC database
            Connection connection = DriverManager.getConnection(DATABASE);
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // The Query
            String query = "WITH \n" + //
                    "    BaseTemperature AS (\n" + //
                    "        SELECT AVG(average_temp) AS base_average_temperature\n" + //
                    "        FROM temperature\n" + //
                    "        WHERE country_code = '?1'\n" + //
                    "        AND city_id = ?2\n" + //
                    "        AND state_id IS NULL\n" + //
                    "        AND year >= ?3\n" + //
                    "        AND year <= ?4\n" + //
                    "    ),\n" + //
                    "    YearRange AS (\n" + //
                    "        SELECT MIN(year) AS min_year, MAX(year) AS max_year\n" + //
                    "        FROM temperature\n" + //
                    "    ),\n" + //
                    "    Periods(start_year, end_year) AS (\n" + //
                    "        SELECT min_year, min_year + ?5\n" + //
                    "        FROM YearRange\n" + //
                    "        UNION ALL\n" + //
                    "        SELECT start_year + ?5, end_year + ?5\n" + //
                    "        FROM Periods\n" + //
                    "        WHERE start_year + ?5 <= (SELECT max_year FROM YearRange)\n" + //
                    "    )\n" + //
                    "SELECT\n" + //
                    "    p.start_year AS periodStartYear,\n" + //
                    "    p.end_year AS periodEndYear,\n" + //
                    "    c.country AS country,\n" + //
                    "    ci.name AS city,\n" + //
                    "    t.state_id AS state,\n" + //
                    "    AVG(t.average_temp) AS averageTemperature,\n" + //
                    "    MAX(t.max_temp) AS maxTemperature,\n" + //
                    "    MIN(t.min_temp) AS minTemperature,\n" + //
                    "    ABS(AVG(t.average_temp) - (SELECT base_average_temperature FROM BaseTemperature)) AS temperatureDifference\n" + //
                    "FROM\n" + //
                    "    (((Periods p\n" + //
                    "    JOIN\n" + //
                    "    temperature t ON t.year >= p.start_year AND t.year < p.end_year)\n" + //
                    "    JOIN country c ON t.country_code = c.country_code)\n" + //
                    "    JOIN city ci ON t.city_id = ci.city_id)\n" + //
                    "    CROSS JOIN\n" + //
                    "    BaseTemperature bt\n" + //
                    "GROUP BY\n" + //
                    "    p.start_year, p.end_year, t.country_code, bt.base_average_temperature, t.state_id, t.city_id\n" + //
                    "HAVING\n" + //
                    "    ABS(AVG(t.average_temp) - bt.base_average_temperature) <= 1\n" + //
                    "    AND state_id IS NULL\n" + //
                    "ORDER BY\n" + //
                    "    temperatureDifference LIMIT ?6";
            query = query.replace("?1", Country_code)
                            .replace("?2", city_id)
                            .replace("?3", starYear)
                            .replace("?4", endYear)
                            .replace("?5", periodQuery)
                            .replace("?6", limit);
            // Get Results
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                String periodStart = results.getString("periodStartYear");
                String periodEnd = results.getString("periodEndYear");
                String periodResult = periodStart + " - " + periodEnd;
                String country = results.getString("country");
                String state = results.getString("state");
                String city = results.getString("city");
                double avg = results.getDouble("averageTemperature");
                double temperatureDifference = results.getDouble("temperatureDifference");
                similarPeriodCountryState.add(new temperatureClass(periodResult, country, state, city, avg, temperatureDifference));
            }
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return similarPeriodCountryState;
    }

    public ArrayList<temperatureClass> sort (ArrayList<Temperature> originalTemperatures) {
        ArrayList<temperatureClass> sorted = new ArrayList<temperatureClass>();

        return sorted;
    }
}
