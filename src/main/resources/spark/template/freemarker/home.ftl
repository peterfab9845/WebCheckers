<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">
    <h1>Web Checkers</h1>

    <div class="navigation">
        <a href="/">my home</a>
    <#if currentPlayer??>
      <a class="right-side" href="/signout">sign out [${currentPlayer.name}]</a>
    <#else>
      <a class="right-side" href="/signin">sign in</a>
    </#if>
    </div>

    <div class="body">
        <p>Welcome to the world of online Checkers.</p>
        <p>${playerCount} player<#if (playerCount == 1)> is<#else>s are</#if> online right now.</p>
        <#if currentPlayer??>
            <p>
            <#list playerList as player>
                <a href="/game?opponentName=${player}">${player}</a>
                <br>
            </#list>
            </p>
        </#if>
    </div>

</div>
</body>
</html>
