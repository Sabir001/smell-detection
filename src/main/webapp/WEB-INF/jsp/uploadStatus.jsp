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
				<h1>Upload Status</h1>

				<div>
					<c:if test="${not empty message}">
					    <h2>${message}</h2>
					</c:if>
				</div>
				
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6 col-md-offset-3">
				<h2> Results of Blob:</h2>
				<c:forEach items="${resultsOfBlob}" var="item">
				 	<c:if test="${not empty item}">
						<p>
							<c:out value="${item}"/>
						</p>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6 col-md-offset-3">
				<h2> Results of Feature Envy:</h2>
				<c:forEach items="${resultsOfFeatureEnvy}" var="item">
				 	<c:if test="${not empty item}">
						<p>
							<c:out value="${item}"/>
						</p>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6 col-md-offset-3">
				<h2> Results of Long Method:</h2>
				<c:forEach items="${resultsOfLongMethod}" var="item">
				 	<c:if test="${not empty item}">
						<p>
							<c:out value="${item}"/>
						</p>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6 col-md-offset-3">
				<h2> Results of Misplaced Class:</h2>
				<c:forEach items="${resultsOfMisplacedClass}" var="item">
				 	<c:if test="${not empty item}">
						<p>
							<c:out value="${item}"/>
						</p>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6 col-md-offset-3">
				<h2> Results of Promiscuous Package:</h2>
				<c:forEach items="${resultsOfPromiscuousPackage}" var="item">
				 	<c:if test="${not empty item}">
						<p>
							<c:out value="${item}"/>
						</p>
					</c:if>
				</c:forEach>
			</div>
		</div>

	</div>
	<!-- /.container -->

	<script type="text/javascript"
		src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>



