<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="detallesRTC.jsp"></jsp:include>
<jsp:include page="modalFiltrado.jsp"></jsp:include>
<jsp:include page="detailsUser.jsp"></jsp:include>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h1 class="mainTitle"></h1>
	<div class="flexButton">
		<i class="fa fa-filter fa-2x" id="modalFilter" data-toggle="modal" data-target="#modalFiltrado"></i>
		<div class="k">K: 0</div>
		<form:form method="POST" action="/estrategia/saveEstrategia">
			<div class="button disabled" id="save">Guardar</div>
		</form:form>
		
	</div>
	<script>
		var inDailys = [];
		var inTasks = [];
	</script>
	
	
	
	<div class="teamUsers"></div>
	<c:forEach items="${listaDaily}" var="daily" varStatus="item">
			<script>
				var exists = false;
				// Si da error en eclipse es por que jsp no detecta en su codigo que es un Script con codigo js
				// Pero esto no afecta en nada al funcionamiento
				inDailys.forEach(el => {
					if(el.id == "${daily.id}"){
						exists = true;
					} 
				});
				if(!exists){
					var daily = new Object();
					daily.id = "${daily.id}";
					daily.fecha = "${daily.fecha}";
					daily.estadoActual = [];
					inDailys.push(daily);
				}	
			</script>
	</c:forEach>

	<c:forEach items="${listaDaily}" var="daily" varStatus="item">
			<script>
				var taskState = [];
				inDailys.forEach(function(daily) {
					if(daily.id == "${daily.id}"){
						taskState.push("${daily.tareaId}");
						taskState.push("${daily.estadoActual}");
						taskState.push("${daily.propiedad}");
						daily.estadoActual.push(taskState);
					}
				});
			</script>
	</c:forEach>

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
				if("${tarea.urgente}" == "true"){
					task.urgente = "Sí";
				} else {
					task.urgente = "No";
				}
				task.estadoFinal = "${tarea.estadoFinal}";
				task.complejidad = "${tarea.complejidad}";
				task.prioridad = "${tarea.prioridad}";
				task.tamano = "${tarea.tamaño}";
				inTasks.push(task);
			</script>
	</c:forEach>
	
	<div class="table-responsive table-bordered">
		<table class="table" id="tableId">
			<thead>
				<tr class="table-info">
					<th scope="col">Pte Alta</th>
					<th scope="col">Pte. Cuantificar</th>
					<th scope="col">Listo Para Analizar</th>
					<th scope="col">Cierre Requerimientos</th>
					<th scope="col">En Análisis y Valoración</th>
					<th scope="col">Aceptación Usuario</th>
					<th scope="col">En Curso (desarrollo)</th>
					<th scope="col">Aceptación a las Pruebas</th>
					<th scope="col">Pte. Implantar</th>
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

	<script>
			if(inDailys.length > 0){
				document.getElementById("drop").style.display =  "none";		
			}
			
	</script>
		
	<script src="/resources/libs/js/shim.js"></script>
	<script src="/resources/libs/js/xlsx.full.min.js"></script>
	<script src="/resources/js/readExcel.js"></script>
	<jsp:include page="footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		$(document).ready(function(){
			// Activate the action to filter
			filtering();
		});
	</script>
	
</body>
</html>