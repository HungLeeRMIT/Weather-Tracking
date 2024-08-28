package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the 
 * Javalin HTTP Server and our web application.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class App {

    public static final int         JAVALIN_PORT    = 7001;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";
    public static final String      JS_DIR          = "js/";


    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7001
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            
            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
            config.addStaticFiles(JS_DIR);

        }).start(JAVALIN_PORT);


        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // Note in this example we must add Movies Type as a GET and a POST!

        // ADD ALL OF YOUR WEBPAGES HERE
        //done
        app.get(Landing.URL, new Landing());
        app.get(Sign_Up.URL, new Sign_Up());
        app.get(Sign_In.URL, new Sign_In());
        app.get(AboutUs.URL, new AboutUs());
        app.get(FAQ.URL, new FAQ());
        app.get(Mission.URL, new Mission());
        app.get(User.URL, new User());
        app.get(Event.URL, new Event());
        app.get(Payment.URL, new Payment());
        app.get(Resources.URL, new Resources());
        app.get(Population.URL, new Population());
        app.get(Temperature.URL, new Temperature());
        app.get(CompareData.URL, new CompareData());
        app.get(FindSimilar.URL, new FindSimilar());
        app.get(Forecast.URL, new Forecast());
        app.get(Planet.URL, new Planet());
        app.get(Travel.URL, new Travel());
        app.get(Clock.URL, new Clock());
        app.get(Fish.URL, new Fish());
        app.get(Error.URL, new Error());


        //after handle
        app.after(Population.URL, ctx -> {
            ctx.sessionAttribute("selectedCountryTemp", null);
            ctx.sessionAttribute("startYearTemp", null);
            ctx.sessionAttribute("endYearTemp", null);
            ctx.sessionAttribute("searchInputTemp", null);
            ctx.sessionAttribute("rankByTemp", null);
            ctx.sessionAttribute("cityOrStateTemp", null);
            ctx.sessionAttribute("viewByTemp", null);
        });
        app.after(Temperature.URL, ctx -> {
            ctx.sessionAttribute("selectedCountry", null);
            ctx.sessionAttribute("startYear", null);
            ctx.sessionAttribute("endYear", null);
            ctx.sessionAttribute("searchInput", null);
            ctx.sessionAttribute("sortBy", null);
        });

        // POST pages can accept form data
        //handle detail population page
        app.post("/searchPop", ctx -> {
            ctx.sessionAttribute("searchInput", ctx.formParam("search"));
            ctx.redirect("/Population.html");
        });
        app.post("/sortPop", ctx -> {
            ctx.sessionAttribute("sortBy", ctx.formParam("sortby"));
            ctx.redirect("/Population.html");
        });
        app.post("/selectCountryPop", ctx -> {
            ctx.sessionAttribute("selectedCountry", ctx.formParam("country"));
            ctx.sessionAttribute("startYear", null);
            ctx.sessionAttribute("endYear", null);
            ctx.sessionAttribute("searchInput", null);
            ctx.redirect("/Population.html");
        });
        app.post("/getStartYearPop", ctx -> {
            ctx.sessionAttribute("startYear", ctx.formParam("startYear"));
            ctx.sessionAttribute("endYear", null);
            ctx.redirect("/Population.html");
        });
        app.post("/getEndYearPop", ctx -> {
            ctx.sessionAttribute("endYear", ctx.formParam("endYear"));
            ctx.redirect("/Population.html");
        });

        //handle detail temperature page
        app.post("/searchTemp", ctx -> {
            ctx.sessionAttribute("searchInputTemp", ctx.formParam("search"));
            ctx.redirect(Temperature.URL);
        });
        app.post("/sortTemp", ctx -> {
            ctx.sessionAttribute("sortByTemp", ctx.formParam("sortby"));
            ctx.redirect(Temperature.URL);
        });
        app.post("/selectCountryTemp", ctx -> {
            ctx.sessionAttribute("selectedCountryTemp", ctx.formParam("country"));
            ctx.sessionAttribute("startYearTemp", null);
            ctx.sessionAttribute("endYearTemp", null);
            ctx.sessionAttribute("searchInputTemp", null);
            ctx.redirect(Temperature.URL);
        });
        app.post("/getStartYearTemp", ctx -> {
            ctx.sessionAttribute("startYearTemp", ctx.formParam("startYear"));
            ctx.sessionAttribute("endYearTemp", null);
            ctx.redirect(Temperature.URL);
        });
        app.post("/getEndYearTemp", ctx -> {
            ctx.sessionAttribute("endYearTemp", ctx.formParam("endYear"));
            ctx.redirect(Temperature.URL);
        });
        app.post("/rank", ctx -> {
            ctx.sessionAttribute("rankByTemp", ctx.formParam("rank"));
            ctx.redirect(Temperature.URL);
        });
        app.post("/cityOrState", ctx -> {
            ctx.sessionAttribute("cityOrStateTemp", ctx.formParam("cityOrState"));
            ctx.redirect(Temperature.URL);
        });
        app.post("/viewBy", ctx -> {
            ctx.sessionAttribute("viewByTemp", ctx.formParam("viewBy"));
            ctx.redirect(Temperature.URL);
        });

        //handle detail compare data page
        app.post("/selectCountry", ctx -> {
            ctx.sessionAttribute("selectedCountryCompare", ctx.formParam("selectCountry"));
            ctx.sessionAttribute("selectedStateCompare", null);
            ctx.sessionAttribute("selectedCityCompare", null);
            ctx.sessionAttribute("startYearCompare", null);
            ctx.sessionAttribute("inputPeriod", null);
            ctx.redirect(CompareData.URL);
        });
        app.post("/selectCityOrState", ctx -> {
            ctx.sessionAttribute("selectedStateCompare", ctx.formParam("selectState"));
            ctx.sessionAttribute("selectedCityCompare", ctx.formParam("selectCity"));
            ctx.sessionAttribute("startYearCompare", null);
            ctx.sessionAttribute("inputPeriod", null);
            ctx.redirect(CompareData.URL);
        });
        app.post("/inputPeriod", ctx -> {
            ctx.sessionAttribute("startYearCompare", ctx.formParam("selectedStartYearCompare"));
            ctx.sessionAttribute("inputPeriod", ctx.formParam("inputPeriod"));
            ctx.redirect(CompareData.URL);
        });
        app.post("/filterTemperature", ctx -> {
            ctx.sessionAttribute("lowerBound", ctx.formParam("minTemperature"));
            ctx.sessionAttribute("upperBound", ctx.formParam("maxTemperature"));
            ctx.sessionAttribute("selectedCountryCompare", null);
            ctx.sessionAttribute("selectedStateCompare", null);
            ctx.sessionAttribute("selectedCityCompare", null);
            ctx.sessionAttribute("startYearCompare", null);
            ctx.sessionAttribute("inputPeriod", null);
            ctx.redirect(CompareData.URL);
        });
        app.post("/rankingAVG", ctx -> {
            ctx.sessionAttribute("sort", ctx.formParam("rankingAVG"));
            ctx.sessionAttribute("selectedCountryCompare", null);
            ctx.sessionAttribute("selectedStateCompare", null);
            ctx.sessionAttribute("selectedCityCompare", null);
            ctx.sessionAttribute("startYearCompare", null);
            ctx.sessionAttribute("inputPeriod", null);
            ctx.redirect(CompareData.URL);
        });
        app.post("/clear", ctx -> {
            CompareData.tableCompare.clear();
            ctx.sessionAttribute("selectedCountryCompare", null);
            ctx.sessionAttribute("selectedStateCompare", null);
            ctx.sessionAttribute("selectedCityCompare", null);
            ctx.sessionAttribute("startYearCompare", null);
            ctx.sessionAttribute("inputPeriod", null);
            ctx.sessionAttribute("sort", null);
            ctx.sessionAttribute("lowerBound", null);
            ctx.sessionAttribute("upperBound", null);
            ctx.redirect(CompareData.URL);
        });


        //handle detail find country page
        app.post("/selectCountrySimilar", ctx -> {
            ctx.sessionAttribute("selectedCountrySimilar", ctx.formParam("selectCountry"));
            ctx.sessionAttribute("selectedStateSimilar", null);
            ctx.sessionAttribute("selectedCitySimilar", null);
            ctx.sessionAttribute("startYearSimilar", null);
            ctx.sessionAttribute("inputPeriodSimilar", null);
            ctx.sessionAttribute("limit", null);
            ctx.redirect(FindSimilar.URL);
        });
        app.post("/selectCityOrStateSimilar", ctx -> {
            ctx.sessionAttribute("selectedStateSimilar", ctx.formParam("selectState"));
            ctx.sessionAttribute("selectedCitySimilar", ctx.formParam("selectCity"));
            ctx.sessionAttribute("startYearSimilar", null);
            ctx.sessionAttribute("inputPeriodSimilar", null);
            ctx.sessionAttribute("limit", null);
            ctx.redirect(FindSimilar.URL);
        });
        app.post("/inputPeriodSimilar", ctx -> {
            ctx.sessionAttribute("startYearSimilar", ctx.formParam("selectedStartYearSimilar"));
            ctx.sessionAttribute("inputPeriodSimilar", ctx.formParam("inputPeriod"));
            ctx.redirect(FindSimilar.URL);
        });
        app.post("/limit", ctx -> {
            ctx.sessionAttribute("limit", ctx.formParam("limit"));
            ctx.redirect(FindSimilar.URL);
        });
        app.post("/clearData", ctx -> {
            ctx.sessionAttribute("selectedCountrySimilar", null);
            ctx.sessionAttribute("selectedStateSimilar", null);
            ctx.sessionAttribute("selectedCitySimilar", null);
            ctx.sessionAttribute("startYearSimilar", null);
            ctx.sessionAttribute("inputPeriodSimilar", null);
            ctx.sessionAttribute("limit", null);
            ctx.redirect(FindSimilar.URL);
        });
        

        //handle error page
        app.exception(Exception.class, (e, ctx) -> {
            // log the exception, e.g. e.printStackTrace();
            ctx.status(404);
            ctx.redirect(Error.URL);
            ctx.sessionAttribute("selectedCountryTemp", null);
            ctx.sessionAttribute("startYearTemp", null);
            ctx.sessionAttribute("endYearTemp", null);
            ctx.sessionAttribute("searchInputTemp", null);
            ctx.sessionAttribute("rankByTemp", null);
            ctx.sessionAttribute("cityOrStateTemp", null);
            ctx.sessionAttribute("viewByTemp", null);
            ctx.sessionAttribute("selectedCountry", null);
            ctx.sessionAttribute("startYear", null);
            ctx.sessionAttribute("endYear", null);
            ctx.sessionAttribute("searchInput", null);
            ctx.sessionAttribute("sortBy", null);
            ctx.sessionAttribute("selectedCountryCompare", null);
            ctx.sessionAttribute("selectedStateCompare", null);
            ctx.sessionAttribute("selectedCityCompare", null);
            ctx.sessionAttribute("startYearCompare", null);
            ctx.sessionAttribute("inputPeriod", null);
            ctx.sessionAttribute("lowerBound", null);
            ctx.sessionAttribute("upperBound", null);
            ctx.sessionAttribute("sort", null);
            ctx.sessionAttribute("selectedCountrySimilar", null);
            ctx.sessionAttribute("selectedStateSimilar", null);
            ctx.sessionAttribute("selectedCitySimilar", null);
            ctx.sessionAttribute("startYearSimilar", null);
            ctx.sessionAttribute("inputPeriodSimilar", null);
            ctx.sessionAttribute("limit", null);
        });
        
        app.error(404, ctx -> {
            ctx.redirect(Error.URL);
            ctx.sessionAttribute("selectedCountryTemp", null);
            ctx.sessionAttribute("startYearTemp", null);
            ctx.sessionAttribute("endYearTemp", null);
            ctx.sessionAttribute("searchInputTemp", null);
            ctx.sessionAttribute("rankByTemp", null);
            ctx.sessionAttribute("cityOrStateTemp", null);
            ctx.sessionAttribute("viewByTemp", null);
            ctx.sessionAttribute("selectedCountry", null);
            ctx.sessionAttribute("startYear", null);
            ctx.sessionAttribute("endYear", null);
            ctx.sessionAttribute("searchInput", null);
            ctx.sessionAttribute("sortBy", null);
            ctx.sessionAttribute("selectedCountryCompare", null);
            ctx.sessionAttribute("selectedStateCompare", null);
            ctx.sessionAttribute("selectedCityCompare", null);
            ctx.sessionAttribute("startYearCompare", null);
            ctx.sessionAttribute("inputPeriod", null);
            ctx.sessionAttribute("lowerBound", null);
            ctx.sessionAttribute("upperBound", null);
            ctx.sessionAttribute("sort", null);
            ctx.sessionAttribute("selectedCountrySimilar", null);
            ctx.sessionAttribute("selectedStateSimilar", null);
            ctx.sessionAttribute("selectedCitySimilar", null);
            ctx.sessionAttribute("startYearSimilar", null);
            ctx.sessionAttribute("inputPeriodSimilar", null);
            ctx.sessionAttribute("limit", null);
        });
    }
}

