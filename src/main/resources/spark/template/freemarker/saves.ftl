<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title} | We🅱️ Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">
    <h1>We🅱️ Checkers ️</h1>

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
            <#list saveList as gameLog>
                ${gameLog} - <a href="watch?game=${gameLog}">watch</a> - <a href="/saves?game=${gameLog}">delete</a>
                <br>
            </#list>
            </p>
        </#if>

    </div>

</div>
</body>
</html>
