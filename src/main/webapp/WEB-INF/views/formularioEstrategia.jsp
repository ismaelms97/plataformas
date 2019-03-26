<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle">Nueva Estrategia</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form:form method="POST" action="/newEstrategiaForm"
					modelAttribute="Estrategia">
					<table>
						<tr>
							<td><form:label path="nombre">Nombre</form:label></td>
							<td><form:input path="nombre"/></td>
						</tr>
						<tr>
							<td><form:label path="fechaFin">Fecha Fin</form:label></td>
							<td><form:input path="fechaFin" type="date" /></td>
						</tr>
						<tr>
							<td><input type="submit" value="Crear" /></td>
						</tr>
					</table>
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>