<!DOCTYPE html>
<html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<!-- <link rel="shortcut icon" type="image/x-icon"
	href="/resources/img/favicon.png" /> -->

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Plataformas</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- JQuery  -->

<script src="/resources/libs/js/jquery-3.3.1.js"></script>
<link rel="stylesheet" href="/resources/libs/css/jquery-ui.css">
<script src="/resources/libs/js/jquery-ui/jquery-ui.js"></script>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="/resources/libs/css/bootstrap/4.1.3/css/bootstrap.min.css">

<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
	integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous">

<!-- Propio -->
<link rel="stylesheet" type="text/css" media="screen"
	href="/resources/css/styles.css">
<script src="/resources/js/plataformas.js"></script>

</head>
<!-- Header -->
<div
	class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
	<h5 id="greeting" class="my-0 mr-md-auto font-weight-normal">${greeting}</h5>
	<nav class="my-2 my-md-0 mr-md-3"></nav>
	<form:form method="POST" action="/closeSession" modelAttribute="user">
		<form:input type="hidden" path="username" value="${user.username}" />
		<button id="butonDestroy" type="submit" class="btn btn-outline-primary">Cerrar
			Sessión</button>
	</form:form>
</div>
<div id="errorMsg" class="alert alert-danger" role="alert">${errorMsg}</div>
<script> 
if (document.getElementById("errorMsg").innerHTML == "" ){
    	document.getElementById("errorMsg").style.visibility = "hidden";
    }
    
if (document.getElementById("greeting").innerHTML == "" ){
	document.getElementById("butonDestroy").style.visibility = "hidden";
}
    </script>
</html>
