<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title} | Web Checkers️</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">
    <h1>We🅱️ Checkers️</h1>

    <div class="navigation">
        <a href="/">my home</a> -
        <#if currentPlayer??>
          <a href="/saves">my saves</a> -
          <a class="right-side" href="/signout">sign out [${currentPlayer.name}]</a>
        <#else>
          <a class="right-side" href="/signin">sign in</a>
        </#if>
    </div>


    <div align="center" class="body">
        You have ${winLoss}<br>

        <form action="/save" method="POST">
            <button>Save and Watch Replay</button>
        </form>

        <br>
        <form action="/watch" method="POST">
            <button>Watch Replay</button>
        </form>

        <br><br>
        <form action="/end" method="POST">
            <button>Back To Lobby</button>
        </form>
    </div>

</div>
</body>
</html>
