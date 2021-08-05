<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/main.css" type="text/css" />
<script src="js/script.js" type="text/javascript"></script>
<title>StudentManageSystem</title>
<%
	String contentPage = request.getParameter("cmd")+".jsp";
%>
</head>
<body>
	<div class="title">
		<a href="main.jsp?cmd=list">Student Management System</a>
	</div>
	<div class="menu">
		<jsp:include page="menu.jsp"></jsp:include>	
	</div>
	<div class="contents">
		<jsp:include page="<%= contentPage %>"></jsp:include>
	</div>
</body>
</html>