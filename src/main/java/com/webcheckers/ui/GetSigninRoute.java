package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Sign-in page.
 *
 * @author Peter Fabinski
 */
public class GetSigninRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) for the {@code GET /signin} HTTP request.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetSigninRoute(final TemplateEngine templateEngine) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    //
    LOG.config("GetSigninRoute is initialized.");
  }

  /**
   * Render the WebCheckers Sign-in page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Sign-in page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetSigninRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Sign-in");
    return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
  }

}