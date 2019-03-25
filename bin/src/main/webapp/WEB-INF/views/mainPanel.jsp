<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>

<body>
	<div class="parent cartas">
		<c:forEach items="${listaEstrategia}" var="data" varStatus="item">
			<a>
				<div class="estartegiasCard">
					<c:out value="${data.nombre}" />
				</div>
			</a>
		</c:forEach>
		<a href="newEstrategia">
			<div class="estartegiasCard">
				Crear nueva Estrategia
			</div>
		</a>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>