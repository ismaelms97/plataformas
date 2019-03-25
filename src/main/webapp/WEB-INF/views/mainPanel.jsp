<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>

<body>
	<div class="parent cartas">



		<c:forEach items="${listaEstrategia}" var="data" varStatus="item">
			<form:form method="POST" action="/estrategia" modelAttribute="estrategia">
				<form:input type="hidden" path="id" value="${data.id}" />
				<a>
					<div class="estartegiasCard" type="submit" role="button">
						<c:out value="${data.nombre}" />
					</div>
				</a>
			</form:form>
		</c:forEach>

		<a href="newEstrategia">
			<div class="estartegiasCard">Crear nueva Estrategia</div>
		</a>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>