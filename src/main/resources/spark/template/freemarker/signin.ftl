<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
    <h1>Web Checkers</h1>

    <div class="navigation">
      <#if currentPlayer??>
          <a href="/">my home</a> |
          <a href="/signout">sign out [${currentPlayer.name}]</a>
      <#else>
        <a href="/">my home</a> |
        <a href="/signin">sign in</a>
      </#if>
    </div>
    
    <div class="body">
      <h2>Sign in:</h2>
      
      <#if message??>
      <div class="error">${message}</div>
      </#if>
      
      <form action="/signin" method="POST">
        Your name: <input name="name" pattern="[A-Za-z0-9 ]+"/>
        <br/>
        <button type="submit">Ok</button>
      </form>
    </div>
    
  </div>
</body>
</html>
