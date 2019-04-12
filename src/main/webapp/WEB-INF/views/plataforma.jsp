<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="detallesRTC.jsp"></jsp:include>
<jsp:include page="modalFiltrado.jsp"></jsp:include>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h1 class="mainTitle"></h1>
	<div class="flexButton">
		<i class="fa fa-filter fa-2x" id="filter" data-toggle="modal" data-target="#modalFiltrado"></i>
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
				console.log("${daily.id}")
				var exists = false;
				
				inDailys.forEach(el => {
					if(el.id == "${daily.id}"){
						exists = true;
					} 
				});
				console.log(exists)
				if(!exists){
					var daily = new Object();
					daily.id = "${daily.id}";
					daily.fecha = "${daily.fecha}";
					daily.estadoActual = [];
					inDailys.push(daily);
					console.log("Daly", "${daily.tareaId}")
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
				console.log(inDailys)
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
				console.log(task)
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
		<div id="errorContainer" style="display: none"><p id="errorMsg" >Archivo no válido</p></div>
		<div id="drop">
			Arrastra el archivo excel aquí para cargarlo<input type="file"
				name="xlfile" id="xlf" hidden="hidden" />
		</div>
	</div>
	<div id="loadAnimation" class="lds-dual-ring" style="display: none"></div>
	<!--style="display: none"-->

	<script>
			if(inDailys.length > 0){
				document.getElementById("drop").style.display =  "none";
			}
	</script>
		
	<script src="/resources/libs/js/shim.js"></script>
	<script src="/resources/libs/js/xlsx.full.min.js"></script>
	<script src="/resources/js/readExcel.js"></script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>