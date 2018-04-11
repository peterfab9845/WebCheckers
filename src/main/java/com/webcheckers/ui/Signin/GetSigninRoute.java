package com.webcheckers.ui.Signin;

import com.webcheckers.appl.PlayerLobby.PlayerLobby;
import com.webcheckers.model.entities.Player;
import com.webcheckers.ui.Game.GetGameRoute;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetSigninRoute implements Route {

  /**
   * Logger for logging things to the console
   */
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  /**
   * Template engine for desplaying things to users
   */
  private final TemplateEngine templateEngine;

  /**
   * Player Lobby to receive info about players in game
   */
  private PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSigninRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;
    //
    LOG.config("GetSigninRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetSigninRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Sign-In");

    Player user = playerLobby.getPlayer(request.session());
    if(user != null){
      response.redirect("/");
      throw halt(200);
    }

    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }

}