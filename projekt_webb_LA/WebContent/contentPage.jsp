<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="Beans.UserBean"%>
<%@page import="Beans.ContentBean"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
	crossorigin="anonymous">
<title>BackFlash Forum</title>
</head>
<body>
	<main class="container">
		<%@ include file="navbar.jsp"%>
		<%
		//check if there is a session with a user
		if (session.getAttribute("user") == null) {
			// there is one goto the login servlet
			RequestDispatcher rd = request.getRequestDispatcher("SQLservlet");
			rd.forward(request, response);// this lands on the GET in the servlet
		}
		//getting username and print welcome +name
		UserBean Bean = (UserBean) session.getAttribute("user");
		out.print("<h1> Welcome " + Bean.getUserN() + "</h1>");
		%>
		<!--form for user post-->
		<form action="<%=request.getContextPath()%>/ContentController"
			method="post">
			<div class="input-group mb-3">
				<input type="text" name="title" class="form-control"
					placeholder="Title" aria-label="Title"> <span
					class="input-group-text">#</span> <input type="text" name="tags"
					class="form-control" placeholder="Tags" aria-label="Tags">
			</div>

			<div class="input-group">
				<textarea class="form-control" name="text"
					aria-label="With textarea"></textarea>
			</div>
			<input class="btn btn-secondary" type="submit" value="Post">

		</form>
		<div>
			<div class="my-3 p-3 bg-body rounded shadow-sm">
				<h6 class="border-bottom pb-2 mb-0">Posts:</h6>
				<%
				//getting content from controller to arraylist of ContentBeans
				ArrayList<ContentBean> cbList = new ArrayList<>();
				cbList = (ArrayList<ContentBean>) session.getAttribute("content");

				//looping through list to display on site
				for (ContentBean cb : cbList) {
					out.print("<div class=\"d-flex text-muted pt-3\">"
					+ "<svg class=\"bd-placeholder-img flex-shrink-0 me-2 rounded\" width=\"32\" height=\"32\" xmlns=\"http://www.w3.org/2000/svg\" role=\"img\" aria-label=\"Placeholder: 32x32\" preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\"><title>Placeholder</title><rect width=\"100%\" height=\"100%\" fill=\"#007bff\"/><text x=\"50%\" y=\"50%\" fill=\"#007bff\" dy=\".3em\">32x32</text></svg>"

					+ "<p class=\"pb-3 mb-0 small lh-sm border-bottom\">" + "<strong class=\"d-block text-gray-dark\">User: "
					+ cb.getUser() + ". Title: " + cb.getTitle() + ". Tags: " + cb.getTags() + "</strong>" + cb.getText()
					+ ".</p>" + "</div>");
				}
				%>
			</div>
		</div>
	</main>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>

	<script src="offcanvas.js"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
		crossorigin="anonymous"></script>
</body>
</html>