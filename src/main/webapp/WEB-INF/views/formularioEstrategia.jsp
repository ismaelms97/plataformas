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
				<form:form method="POST" action="/estrategia/saveEstrategia"
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
						<form:input type="hidden" path="fechaInicio" cssClass="form-control" id="estrategiaFormInputDateInit" value="" />
						<form:input type="hidden" path="estado" cssClass="form-control" id="estrategiaFormInputEstado" value="En curso"/>
						<form:input path="equipoId" cssClass="form-control" id="estrategiaFormInputEquipoId" hidden/>
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
	;
$('#estrategiaForm').on('shown.bs.modal', function () {
	
	$('#estrategiaFormInputDate').val(new Date().getDate() + "/" + new Date().getMonth() + "/" + new Date().getFullYear());
	/*   $('#estrategiaFormInputDate').attr('min' , new Date().getFullYear() + "-" +  new Date().getMonth() + "-" + new Date().getDate()); */
	  $('#estrategiaFormInputDate').change(function(e){
		  var hoy = new Date().setHours(0,0,0,0);
		  var fechaIntroducida = e.target.value;
		  var fecha = new Date(fechaIntroducida).getTime();
		  
		  if(hoy <= fecha){
			  $("#crearEstrategia").prop( "disabled", false );
			  console.log("Es mayor");
		  }else{
			  $("#crearEstrategia").prop( "disabled", true );
		  }
	  })
		
	 $("#estrategiaFormInputDateInit")
	  
	})
})
</script>