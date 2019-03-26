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
				<form:form method="POST" action="/newEstrategiaForm"
					modelAttribute="estrategia">
					<table>
						<tr>
							<td><form:label path="nombre" for="estrategiaFormInputName">Nombre</form:label></td>
							<td><form:input path="nombre" class="form-control" placeholder="Introduce Nombre Estrategia" id="estrategiaFormInputName"/></td>
						</tr>
						<tr>
							<td><form:label path="fechaFin" for="estrategiaFormInputDate">Fecha Fin</form:label></td>
							<td><form:input path="fechaFin" type="date" class="form-control" id="estrategiaFormInputDate"/></td>
						</tr>
						<tr>
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
$('#estrategiaForm').on('shown.bs.modal', function () {
	/*   $('#estrategiaFormInputDate').attr('min' , new Date().getFullYear() + "-" +  new Date().getMonth() + "-" + new Date().getDate()); */
	  $('#estrategiaFormInputDate').change(function(){
		  var hoy = new Date().getTime();
		  hoy.setHours(0,0,0,0);
		  var fechaIntroducida = $("#estrategiaFormInputDate").val();
		  var fecha = new Date(fechaIntroducida).getTime();
		  console.log(fecha);
		  
		  if(hoy <= fecha){
			  $("#crearEstrategia").prop( "disabled", false );
			  console.log("Es mayor");
		  }
	  })
	
	})
</script>