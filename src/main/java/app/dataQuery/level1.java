package app.dataQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class level1 extends JDBCConnection{
    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public populationClass getTotalYearPopulation() {
        populationClass totalYearPopulation = new populationClass();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT Count(year),\n" + //
                    "MAX(year),\n" + //
                    "MIN(year)\n" + //
                    "FROM population WHERE country_code='WLD'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            int countYear =  results.getInt("Count(year)");
            int maxYear = results.getInt("MAX(year)");
            int minYear = results.getInt("MIN(year)");  
            totalYearPopulation = new populationClass(countYear, maxYear, minYear); 

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return totalYearPopulation;
    }

    public temperatureClass getTotalYearTemperature() {
        temperatureClass totalYearTemperature = new temperatureClass();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT Count(year),\n" + //
                    "MAX(year),\n" + //
                    "MIN(year)\n" + //
                    "FROM Temperature WHERE country_code='WLD'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            int countYear = results.getInt("Count(year)");
            int maxYear = results.getInt("MAX(year)");
            int minYear = results.getInt("MIN(year)");
            totalYearTemperature = new temperatureClass(countYear, maxYear, minYear);

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return totalYearTemperature;
    }

    public int getCountryCount() {
        int countryCount = 0;
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT Count(*) FROM Country WHERE country_code <> 'WLD'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            countryCount = results.getInt("Count(*)");

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return countryCount;
    }

    public ArrayList<populationClass> getWorldPopulation() {
        // Create the ArrayList of LGA objects to return
        ArrayList<populationClass> worldPopulation = new ArrayList<populationClass>();

        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Population WHERE country_code='WLD'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int year     = results.getInt("year");
                Long population  = results.getLong("amount");

                // Create a LGA Object
                populationClass populations = new populationClass(year, population);

                // Add the lga object to the array
                worldPopulation.add(populations);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        // Finally we return all of the lga
        return worldPopulation;
    }

    public ArrayList<temperatureClass> getWorldTemperature() {
        // Create the ArrayList of LGA objects to return
        ArrayList<temperatureClass> worldTemperature = new ArrayList<temperatureClass>();

        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "WITH RECURSIVE\n" + //
                    "    YearRange AS (\n" + //
                    "        SELECT MIN(year) AS min_year, MAX(year) AS max_year\n" + //
                    "        FROM temperature\n" + //
                    "    ),\n" + //
                    "    Periods(start_year, end_year) AS (\n" + //
                    "        SELECT min_year, min_year + 10\n" + //
                    "        FROM YearRange\n" + //
                    "        UNION ALL\n" + //
                    "        SELECT start_year + 10, end_year + 10\n" + //
                    "        FROM Periods\n" + //
                    "        WHERE start_year + 10 <= (SELECT max_year FROM YearRange)\n" + //
                    "    )\n" + //
                    "SELECT\n" + //
                    "    p.start_year AS periodStartYear,\n" + //
                    "    AVG(t.average_temp) AS averageTemperature\n" + //
                    "FROM\n" + //
                    "    Periods p\n" + //
                    "    JOIN\n" + //
                    "    temperature t ON t.year >= p.start_year AND t.year < p.end_year\n" + //
                    "GROUP BY\n" + //
                    "    p.start_year, p.end_year, t.country_code\n" + //
                    "HAVING\n" + //
                    "    t.country_code = 'WLD'\n" + //
                    "    AND t.state_id IS NULL\n" + //
                    "    AND t.city_id IS NULL\n" + //
                    "UNION ALL\n" + //
                    "SELECT year, average_temp\n" + //
                    "FROM temperature WHERE country_code = 'WLD'\n" + //
                    "AND year = 2013";
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int year     = results.getInt("periodStartYear");
                Double temperature  = results.getDouble("averageTemperature");

                // Create a temperature Object
                temperatureClass temperatures = new temperatureClass(year, temperature);

                // Add the lga object to the array
                worldTemperature.add(temperatures);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        // Finally we return all of the lga
        return worldTemperature;
    }

    public ArrayList<personaClass> getPersona() {
        ArrayList<personaClass> personas = new ArrayList<personaClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASEMEM);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM personas";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name  = results.getString("name");
                String image  = results.getString("image");
                String quote  = results.getString("quote");
                String age  = results.getString("age");
                age = "- Age: " + age;
                String role  = results.getString("role");
                role = "- Role: " + role;
                String otherattribute1  = results.getString("otherattribute1");
                otherattribute1 = "- " + otherattribute1;
                String otherattribute2  = results.getString("otherattribute2");
                if (otherattribute2 != null) {
                    otherattribute2 = "- " + otherattribute2;
                }
                String need  = results.getString("need");
                need = "- Needs: " + need;
                String goal  = results.getString("goal");
                goal = "- Goals: " + goal;
                String skill  = results.getString("skill");
                skill = "- Skills: " + skill;
                String experience  = results.getString("experience");
                experience = "- Experience: " + experience;

                // Create a temperature Object
                personaClass persona = new personaClass(name, image, quote, age, role, otherattribute1, otherattribute2, need, goal, skill, experience);

                // Add the lga object to the array
                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return personas;
    }

    public ArrayList<memberClass> getMember(){
        ArrayList<memberClass> members = new ArrayList<memberClass>();
        try {
            // Connect to JDBC data base
            Connection connection = DriverManager.getConnection(DATABASEMEM);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM members";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String sID  = results.getString("sID");
                String name  = results.getString("name");
                String image  = results.getString("image");
                String facebook  = results.getString("facebook");
                String instagram  = results.getString("instagram");
                String twitter  = results.getString("twitter");
                String role  = results.getString("role");

                // Create a temperature Object
                memberClass member = new memberClass(sID, name, image, facebook, instagram, twitter, role);

                // Add the lga object to the array
                members.add(member);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        }
        return members;
    }
}
