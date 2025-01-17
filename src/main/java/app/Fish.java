package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;

public class Fish implements Handler {

  public static final String URL = "/Fish.html";
  private static final String TEMPLATE = ("Fish.html");

  @Override
  public void handle(Context context) throws Exception {
    // The model of data to provide to Thymeleaf.
    // In this example the model will remain empty
    Map<String, Object> model = new HashMap<String, Object>();

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage using Thymeleaf
    context.render(TEMPLATE, model);
  }
}
