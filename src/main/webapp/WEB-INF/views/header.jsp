<!DOCTYPE html>
<html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
 <link rel="shortcut icon" type="image/x-icon"
	href="/resources/img/icon.png" /> 

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Plataformas</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- JQuery  -->

<script src="/resources/libs/js/jquery-3.3.1.js"></script>
<script src="/resources/libs/js/jquery-ui/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/libs/css/jquery-ui.css">
<script src="/resources/libs/js/jquery.ui.touch-punch.min.js"></script>

<!-- Bootstrap -->
<link rel="stylesheet" href="/resources/libs/css/bootstrap/4.1.3/css/bootstrap.min.css">

<!-- Material Design Bootstrap -->
<link href="/resources/libs/css/mdb.css" rel="stylesheet">

<!-- Font Awesome -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous">
<script src="/resources/libs/js/bootstrap-notify.min.js"></script>
<!-- Propio -->
<link rel="stylesheet" type="text/css" media="screen" href="/resources/css/styles.css">
<script src="/resources/libs/js/Xlsexport.js"></script>
</head>
<!-- Header -->
<div
	class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
	<h5 id="greeting" class="my-0 mr-md-auto font-weight-normal">${greeting}	  	
<%-- 	  	<c:forEach items="${roles}" var="rol" varStatus="item"> <c:out value = "${rol}"/><p>
	  	</c:forEach> --%>
	  	</h5>

	<form method="GET" action="/estrategia/panelControl">
	<button id="buttonHome" type="submit"
			class="btn btn-outline-secondary"><i class="fa fa-home"></i></button>
	</form>

	<nav class="my-2 my-md-0 mr-md-3"></nav>
	
	<form:form method="POST" action="/closeSession">		
		<button id="butonDestroy" type="submit"	class="btn btn-outline-primary">Cerrar Sesión</button>
	</form:form>
	
</div>
</html>

