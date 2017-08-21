<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	
	<link rel="stylesheet" type="text/css"
		href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
	
	<!-- 
		<spring:url value="/css/main.css" var="springCss" />
		<link href="${springCss}" rel="stylesheet" />
		 -->
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />

</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">Code Smell Detection</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/">Home</a></li>
					<li><a href="uploadProject">Upload Project</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="row">
			<div class="col-lg-6 col-lg-offset-3">
				<h1>Spring Boot - Upload Status</h1>

				<div th:if="${message}">
				    <h2>${message}</h2>
				</div>
			</div>
		</div>

	</div>
	<!-- /.container -->

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>



