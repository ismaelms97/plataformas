<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<script src="/resources/js/mainPanel.js"></script>
<body>
	<p> ${mensajeAcceso} <p>
	<jsp:include page="footer.jsp"></jsp:include>
</body>