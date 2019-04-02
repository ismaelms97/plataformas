<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="detallesRTC.jsp"></jsp:include>
<jsp:include page="modalFiltrado.jsp"></jsp:include>

<body>
	<h1 class="mainTitle"> </h1>

	<i class="fa fa-filter fa-2x" id="filter" data-toggle="modal" data-target="#modalFiltrado"></i>
	
	<!-- <div id="suve">Suve</div> -->
	<form:form method="POST" action="/estrategia/saveEstrategia">
		<div class="button disabled" id="save">Guardar</div>
	</form:form>
		<script>
		var inTasks = [];
	</script>
	<c:forEach items="${listaTareas}" var="tarea" varStatus="item">
			<script>
				var task = new Object();
				task.id = "${tarea.id}";
				task.tipo = "${tarea.tipo}"
				task.resumen = "${tarea.resumen}"
				task.estado = "${tarea.estadoInicio}";
				task.propiedad = "${tarea.propiedad}"
				task.relevante = "${tarea.relevante}"
				task.planificado = "${tarea.planificado}"
				task.peticionario = "${tarea.peticionario}"
				task.urgente = "${tarea.urgente}";
				task.estadoFinal = "${tarea.estadoFinal}";
				task.complejidad = "${tarea.complejidad}";
				task.prioridad = "${tarea.prioridad}";
				task.tamano = "${tarea.tamaño}";
				inTasks.push(task);			
				console.log(task);
			</script>
	</c:forEach>
	<div class="table-responsive table-bordered">
		<table class="table">
			<thead>
				<tr class="table-info">
					<th scope="col">Pte Alta</th>
					<th scope="col">Pte.Cuantificar</th>
					<th scope="col">Listo Para Analizar</th>
					<th scope="col">Cierre Requerimientos</th>
					<th scope="col">En Análisis</th>
					<th scope="col">Aceptación Usuario</th>
					<th scope="col">En Curso</th>
					<th scope="col">Aceptación a las Pruebas</th>
					<th scope="col">Pendiente Implantar</th>
					<th scope="col">Implantado</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		<div id="drop">
			Arrastra el archivo excel aquí para cargarlo<input type="file"
				name="xlfile" id="xlf" hidden="hidden" />
		</div>
	</div>
	<div id="loadAnimation" class="lds-dual-ring" style="display: none"></div>
	<!--style="display: none"-->


	<script src="/resources/libs/js/shim.js"></script>
	<script src="/resources/libs/js/xlsx.full.min.js"></script>
	<script src="/resources/js/readExcel.js"></script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>