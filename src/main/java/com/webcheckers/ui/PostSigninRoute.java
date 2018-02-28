package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to POST the chosen username to the server.
 *
 * @author Peter Fabinski
 */
public class PostSigninRoute implements Route {

  private static final Logger LOG = Logger.getLogger(PostSigninRoute.class.getName());

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) for the {@code POST /signin} HTTP request.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public PostSigninRoute(final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    //
    LOG.config("PostSigninRoute is initialized.");
  }

  /**
   * Handle the user's sign-in request.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Sign-in page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostSigninRoute is invoked.");

    // retrieve request parameter
    final String username = request.queryParams("name");
    Player currentPlayer = new Player(username);

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Sign-in");
    return templateEngine.render(new ModelAndView(vm, "home.ftl"));
  }

}