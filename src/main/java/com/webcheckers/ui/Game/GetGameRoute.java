package com.webcheckers.ui.Game;

import com.webcheckers.appl.Message;
import com.webcheckers.appl.MessageMap;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Entities.Game;
import com.webcheckers.model.Entities.Player;
import com.webcheckers.model.States.MessageType;
import com.webcheckers.model.States.ViewMode;
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
public class GetGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private final TemplateEngine templateEngine;

  private PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    //
    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;
    //
    LOG.config("GetHomeRoute is initialized.");
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
    LOG.finer("GetGameRoute is invoked.");

    Map<String, Object> vm = new HashMap<>();

    Player user = playerLobby.getPlayer(request.session());
    if (user == null) {
      response.redirect("/");
      throw halt(401);
    }
    String opponentName = request.queryParams("opponentName");
    if (opponentName != null ) {
      Player opponent = playerLobby.getPlayer(opponentName);
      playerLobby.Challenge(user, opponent);
      response.redirect("/game");
      throw halt(402);
    }
    Game game = playerLobby.getGame(user);

    vm.put("title", "Game!");
    vm.put("currentPlayer", user);
    vm.put("viewMode", ViewMode.PLAY);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("board", game.getBoardView());


    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }

}