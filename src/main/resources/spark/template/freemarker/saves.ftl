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
        <a href="/">my home</a> -
        <#if currentPlayer??>
          <a href="/saves">my saves</a> -
          <a class="right-side" href="/signout">sign out [${currentPlayer.name}]</a>
        <#else>
          <a class="right-side" href="/signin">sign in</a>
        </#if>
    </div>

    <div class="body">
        My Saves:<br>

        <#if currentPlayer??>
            <p>
            <#list saveList as savedGame>
                ${savedGame} - <a href="">watch</a> - <a href="/saves?game=${savedGame}">delete</a>
                <br>
            </#list>
            </p>
        </#if>

    </div>

</div>
</body>
</html>
