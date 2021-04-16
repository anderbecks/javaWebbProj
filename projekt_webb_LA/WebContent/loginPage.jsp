<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
// check if there is a session with an user
if (session.getAttribute("user") != null) {
	// there is one goto the login servlet
	RequestDispatcher rd = request.getRequestDispatcher("SQLservlet");
	rd.forward(request, response);// this lands on the GET in the servlet
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BackFlash Forum</title>
<link href="signin.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
	crossorigin="anonymous">
</head>

<body class="text-center">

	<main class="form-signin">
		<form action="<%=request.getContextPath()%>/SQLservlet"
			method="post">

			<h1 class="h3 mb-3 fw-normal">Please sign in</h1>

			<div class="form-floating">
				<input class="form-control" name="user" id="floatingInput"
					placeholder="userNameExample"> <label for="floatingInput">Username:</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" name="password"
					id="floatingPassword" placeholder="Password"> <label
					for="floatingPassword">Password</label>
			</div>


			<button class="w-100 btn btn-lg btn-primary" type="submit">Sign
				in</button>

		</form>
	</main>



</body>


</html>