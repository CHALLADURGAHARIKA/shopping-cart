
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login page</title>
    <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="homepagestyle.css">
<link rel="stylesheet" href="loginpage.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
</head>
<body>
    <header>
		<div id="customer">
			<div id="login">
				<a href="login.jsp">login<i class="fa fa-sign-in"></i></a>
			</div>
			<div id="signup">
				<a href="signup.html">signup<i class="fa fa-user-plus" aria-hidden="true"></i></a>
			</div>
		</div>
	</header>
    <div class="login">
        <div class="login-triangle"></div>
        
        <h2 class="login-header">Log in</h2>
      
        <form class="login-container" method="post" action="/products">
          <p><input type="text" name="username"placeholder="username"></p>
          <p><input type="password" name= "password"  placeholder="Password"></p>
          <p><input type="submit" value="Log in"></p>
        </form>
      </div>
</body>
</html>
