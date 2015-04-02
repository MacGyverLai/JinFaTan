<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to Jin Fa Tan Customer Manager System</title>	

<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="js/Login.js"></script>

<script src="js/bootstrap.min.js"></script>

<link type="text/css" href="css/jquery-ui-1.7.2.custom.css"
	rel="stylesheet" />
<link type="text/css" href="css/JinFaTan.css" rel="stylesheet" />
<link type="text/css" href="css/bootstrap.min.css" rel="stylesheet"
	media="screen">
</head>
<body>
	<div class="container">
		<h2>進法壇 客戶管理系統</h2>
		<div class="span4 offset4">
			<form id="login_form" method="POST" action="login.do">
				<fieldset>
					<legend>請先登入</legend>
					<label>使用者帳號 :</label> <input name="userName" type="text"
						placeholder="請輸入使用者帳號" required="required" autofocus /> <label>使用者密碼
						:</label> <input name="password" type="password" placeholder="請輸入使用者密碼"
						required />
					<div>
						<a href="#">忘記密碼</a> <a href="#">加入會員</a>
					</div>
					<input class="btn" id="login_button" type="button" value="登入" />
				</fieldset>
			</form>
		</div>
	</div>
</body>
</html>
