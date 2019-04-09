<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<div class="modal fade" id="estrategiaForm" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="estrategiaFormTitle">Nueva Estrategia</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form:form method="POST" action="/estrategia/pushEstrategia"
					modelAttribute="estrategia">
					<table>
						<tr>
							<td><form:label path="nombre" for="estrategiaFormInputName">Nombre</form:label></td>
							<td><form:input path="nombre" cssClass="form-control" placeholder="Introduce Nombre Estrategia" id="estrategiaFormInputName"/></td>
						</tr>
						<tr>
							<td><form:label path="fechaFin" for="estrategiaFormInputDate">Fecha Fin</form:label></td>
							<td><form:input path="fechaFin" type="date" cssClass="form-control" id="estrategiaFormInputDate"/></td>
						</tr>
						<tr>
							<td><form:label path="" for="estrategiaFormSelectTeam">Equipo</form:label></td>
							<td><form:select path=""  cssClass="form-control" id="estrategiaFormSelectTeam" items="${teams}"/></td>
							<%-- <c:forEach items="${teamsID}" var="id" varStatus="item">
								<option value="id"></option>
								</c:forEach> --%>
						</tr>
						<tr>

						<form:input path="fechaInicio" class="form-control" id="estrategiaFormInputDateInit" type="hidden"/>
						<form:input path="estado" class="form-control" id="estrategiaFormInputEstado" type="hidden"/>
						<form:input path="equipoId" class="form-control" id="estrategiaFormInputEquipoId" type="hidden"/>
							<td><input type="submit" value="Crear" class="btn btn-primary" id="crearEstrategia" disabled/></td>
						</tr>
					</table>
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal" >Close</button>
			</div>
		</div>
	</div>
</div>


<script>
$(document).ready(function(){
$('#estrategiaForm').on('shown.bs.modal', function () {
	  $('#estrategiaFormInputDate').change(function(e){
		  var hoy = new Date().setHours(0,0,0,0);
		  var fechaIntroducida = e.target.value;
		  var fecha = new Date(fechaIntroducida).getTime();
		  
		  if(hoy <= fecha){
			  $("#crearEstrategia").prop( "disabled", false );
			  $("#crearEstrategia").click(function(){
				 console.log(toCamelCase(document.getElementById("estrategiaFormInputName").innerHTML.trim()))
				 sessionStorage.setItem('titulo', toCamelCase(document.getElementById("estrategiaFormInputName").value.trim()));
			  })
			
		  }else{
			  $("#crearEstrategia").prop( "disabled", true );
		  }
	  })
	  
	})
	 
	$("#estrategiaFormInputDateInit").val(new Date().getFullYear() + "-" +  new Date().getMonth() + "-" + new Date().getDate());
	$("#estrategiaFormInputEstado").val("En Curso");
	$("#estrategiaFormInputEquipoId").val("1");
	
	
})
</script>