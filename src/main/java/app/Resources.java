package app;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import app.dataQuery.*;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class Resources implements Handler {
    public static final String URL = "/Resources.html";
    //private static final String TEMPLATE = ("Resources.html");
    private static final String TEMPLATE = ("resources.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<String, Object>();

        ArrayList<personaClass> personaList = new ArrayList<personaClass>();
        level1 level1 = new level1();
        personaList = level1.getPersona();
        model.put("personas", personaList);


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }
}
