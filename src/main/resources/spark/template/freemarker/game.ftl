<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>${title} | We🅱️ Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script>
  window.gameState = {
    "currentPlayer" : "${currentPlayer.name}",
    "viewMode" : "${viewMode}",
    "redPlayer" : "${redPlayer.name}",
    "whitePlayer" : "${whitePlayer.name}",
    "activeColor" : "${activeColor}"
  };
  </script>

    <style>
        .slidecontainer {
            width: 100%;
        }

        .slider {
            -webkit-appearance: none;
            width: 80%;
            height: 25px;
            background: #9cd0d3;
            outline: none;
            opacity: 0.7;
            -webkit-transition: .2s;
            transition: opacity .2s;
        }

        .slider:hover {
            opacity: 1;
        }

        .slider::-webkit-slider-thumb {
            -webkit-appearance: none;
            appearance: none;
            width: 25px;
            height: 25px;
            background: #4139d3;
            cursor: pointer;
        }

        .slider::-moz-range-thumb {
            width: 25px;
            height: 25px;
            background: #74a9af;
            cursor: pointer;
        }
    </style>
  <#if isSpectating??>
    <meta http-equiv="refresh" content="1">
  </#if>
</head>
<body>
  <div class="page">
    <h1>We🅱️ Checkers<font size="1"></h1></font>
    <div class="navigation">
    <#if currentPlayer??>
      <a href="/">my home</a> |
      <a href="/signout">sign out [${currentPlayer.name}]</a>
    <#else>
      <a href="/signin">sign in</a>
    </#if>
    </div>
    
    <div class="body">
      
      <p id="help_text"></p>
      
      <div>
        <div id="game-controls">
        
          <fieldset id="game-info">
            <legend>Info</legend>
            
            <#if message??>
              <div id="message" class="${message.type}">${message.text}</div>
            <#else>
              <div id="message" class="info" style="display:none">
              <!-- keep here for client-side messages -->
            </div>
            </#if>
            
            <div>
              <table data-color='RED'>
                <tr>
                  <td><img src="../img/single-piece-red.svg" /></td>
                  <td class="name">Red</td>
                </tr>
              </table>
              <table data-color='WHITE'>
                <tr>
                  <td><img src="../img/single-piece-white.svg" /></td>
                  <td class="name">White</td>
                </tr>
              </table>
            </div>
          </fieldset>
          
          <fieldset id="game-toolbar">
            <legend>Controls</legend>
            <div class="toolbar"></div>
          </fieldset>
          
        </div>
  
        <div class="game-board">
          <table id="game-board">
            <tbody>
            <#list board.iterator() as row>
              <tr data-row="${row.index}">
              <#list row.iterator() as space>
                <td data-cell="${space.cellIdx}"
                    <#if space.isValid() >
                    class="Space"
                    </#if>
                    >
                <#if space.piece??>
                  <div class="Piece"
                       id="piece-${row.index}-${space.cellIdx}"
                       data-type="${space.piece.type}"
                       data-color="${space.piece.color}">
                  </div>
                </#if>
                </td>
              </#list>
              </tr>
            </#list>

              <#if isReplay??>
              <div align="center">
                  <form action="/speed" method="POST">
                      <input id="speed" name="speed" type="range" min="1" max="10" value="${speed}" class="slider"><br>
                      <button type="submit">Change Speed</button><br>
                  </form>
              </div>

          </#if>
          </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>


  <audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>
  
  <script data-main="js/game/index" src="js/require.js"></script>
  
</body>
</html>
