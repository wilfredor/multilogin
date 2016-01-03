<%@ page language="java" contentType="text/html; charset=UTF-8;"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

</head>
<body>
	
	<div class="login hd-background-login">
		<div class="div-login">
			<div class="col-md-11">
				<form action="login" method="POST" enctype="application/x-www-form-urlencoded">
					<div class="row">
						<div class="form-group">
							<select class="form-control" id="database" name="database">s
								<option value="NewYork">New York</option>
								<option value="Chicago">Chicago</option>
								<option value="Houston">Houston</option>
								<option value="LosAngeles">Los Angeles</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<input type="text" class="form-control" name="username"
								placeholder="User" id="username">
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<input type="password" class="form-control" name="password"
								placeholder="Password" id="password">
						</div>
					</div>
					<div class="row">
						<input type="submit" class="btn btn-default btn-lg btn-block"
							id="btn-access" value="Login" />
					</div>
				</form>
			</div>
		</div>
	</div>
	
</body>
</html>