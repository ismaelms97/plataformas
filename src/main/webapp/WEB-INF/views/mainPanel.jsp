<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="formularioEstrategia.jsp"></jsp:include>
<script src="/resources/js/mainPanel.js"></script>

<body>
	<div class="parent cartas">

		<c:forEach items="${listaEstrategia}" var="estrategia"
			varStatus="item">
			<script>
				var estrategia = new Object();
				estrategia.id = "${estrategia.id}";
				estrategia.endDate = "${estrategia.fechaFin}";
				console.log("ID " + estrategia.endDate)
				estrategias.push(estrategia);
			</script>
			<a href="#<%-- /estrategia/findEstrategia/${estrategia.id} --%>">
				<div class="estartegiasCard">
					<c:out value="${estrategia.nombre}" />
					<div class="divOptions">
						<span class="options">Crear</span>
						<span class="options">Ver</span>
					</div>
				</div>
			</a>
		</c:forEach>

		<a data-toggle="modal" data-target="#estrategiaForm">
			<div class="estartegiasCard">Nueva Estrategia</div>
		</a>
	</div>

	<script>
		checkStatus();
		
		$(document).ready(function() {
		    $(".estartegiasCard").click(function () {
		        $(".options", this).toggle();
		    });
		    
		    $(".options", this).toggle();
		});

	</script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>