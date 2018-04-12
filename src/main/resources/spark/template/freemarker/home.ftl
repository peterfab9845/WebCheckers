<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="1">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">

    <h1>Web Checkers 🅱️</h1>

    <div class="navigation">
      <#if currentPlayer??>
          <a href="/">my home</a> |
          <a href="/saves">my saves</a> |
          <a href="/signout">sign out [${currentPlayer.name}]</a>
      <#else>
        <a href="/">my home</a> |
        <a href="/signin">sign in</a>
      </#if>
    </div>

    <div class="body">
        <#if message??>
            <div class="error">${message.text}</div>
        </#if>
        <p>Welcome to the world of online Checkers.</p>
        <p>${playerCount} player<#if (playerCount == 1)> is<#else>s are</#if> online right now.</p>
        <#if currentPlayer??>
            <table width="80%">
            <tr>
                <th align="left">Players In Lobby</th>
                <th align="left">Players In Game</th>
            </tr>
            <tr>

                <td>
                    <p>
                        <#list playerList as player>
                            ${player.name} - <a href="/game?opponentName=${player.name}">Challenge</a>
                            <br>
                        </#list>
                    </p>
                </td>

                <td>
                    <p>
                        <#list gameList as player>
                            ${player.name} - <a href="/spectate?name=${player.name}">Spectate</a>
                            <br>
                        </#list>
                    </p>
                </td>
            </tr>
            </table>
        </#if>
    </div>


</div>
</body>
</html>
