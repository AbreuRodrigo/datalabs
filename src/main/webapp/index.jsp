<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
		
		<link rel="shortcut icon" href="images/favicon.png" />
		
		<title>DataLab | Login</title>
		
		<!--CSS-->
		<link rel="stylesheet" type="text/css" href="css/style.css"/>
		
		<!--JS-->
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<script type="text/javascript" src="js/common-behaviours.js"></script>
	</head>	
	<body>
		<header class="flex-box flex-justify-center flex-align-center">
			<img src="images/logo2.png"/>
		</header>
		<section class="flex-box flex-justify-center">
			<div class="content flex-box flex-direction-column flex-justify-center flex-align-center">
				<form id="log-in-form" class="flex-box flex-direction-column flex-justify-space-between flex-align-center">
					<div class="form-title flex-box flex-align-center">
						<p>Log in to your DataLab</p>
					</div>
					<div class="form-control flex-box flex-direction-column flex-justify-center">
						<input id="user-input" class="user-background-icon" type="text" name="user" placeholder="Email"/>
						<input id="password-input" class="password-background-icon " type="password" name="password" placeholder="Password"/>
						<button id="submit-button" type="submit">Log in</button>
					</div>
					<div class="form-bottom flex-box flex-justify-center flex-align-center">
						<p>Don't have an account yet? <a href="#">Sign up</a> here.</p>
					</div>
				</form>
				<a id="retrieve-password" href="#">Forgot Password?</a>
			</div>
		</section>
		<footer class="flex-box flex-justify-center">
			<p id="copyright"></p>
		</footer>
	</body>
</html>