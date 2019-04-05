<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<script src="/resources/js/mainPanel.js"></script>
<body>
	<p>${mensajeAcceso} <p>
	<div  class="d-flex flex-row align-items-center ">
		<form method="GET" action="/estrategia/panelControl">
		<label>Panel de Control</label>
			<button id="buttonHome" type="submit"
				class="btn btn-outline-secondary">
				<i class="fa fa-solar-panel"></i>
			</button>
		</form>
	
	<form method="GET" action="/">
	<label>Volver al login</label>
	<button id="buttonHome" type="submit"
			class="btn btn-outline-secondary"><i class="fa fa-home"></i></button>
	</form>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>