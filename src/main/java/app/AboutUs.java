package app;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import app.dataQuery.*;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AboutUs implements Handler {
    public static final String URL = "/AboutUs.html";
    //private static final String TEMPLATE = ("AboutUs.html");
    private static final String TEMPLATE = ("AboutUs.html");

    @Override
    public void handle(Context context) throws Exception {
        // The model of data to provide to Thymeleaf.
        // In this example the model will remain empty
        Map<String, Object> model = new HashMap<String, Object>();

        ArrayList <memberClass> memberList = new ArrayList<memberClass>();
        level1 level1 = new level1();
        memberList = level1.getMember();
        model.put("members", memberList);


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }
}
