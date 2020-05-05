<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.0.min.js"
	integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ="
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
	
	 
</head>
<body>
	<div class="form-group">
		<label>Username</label> <input type="text" id="username">
	</div>
	<div class="form-group">
		<label>Password</label> <input type="password" id="password">
	</div>
	<button type="button" class="btn btn-primary" onclick="login()">Login</button>
	<script>
		function login() {
			var username = $('#username').val();
			var password = $('#password').val()
			localStorage.setItem('username', username);
			localStorage.setItem('password', password);
			$.get('/hc/api/user/user/'+username, function(data) {
				localStorage.setItem('user', JSON.stringify(data));
				window.location.href = "appoinment.jsp";
			})

		}
	</script>
</body>
</html>