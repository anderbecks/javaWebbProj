<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<nav class="navbar navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand"><h4>BackFlash Forum</h4></a>
    <form class="d-flex" id="mylogoutbtn" action="<%=request.getContextPath()%>/Logout" method="post">
      <button class="btn btn-outline-danger" type="submit">Logout</button>
    </form>
  </div>
</nav>