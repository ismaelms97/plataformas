<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<div class="modal fade" id="detallesUsuarios" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-notify modal-info"
		role="document">
		<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header">
				<p class="heading lead" id="detalles">Información Usuario</p>

				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>
			<div class="modal-body">
			<h6 class="detailsSubtitle">Nombre:</h6>
				<div id="detallesNombre"></div>
				<hr>
				<h6 class="detailsSubtitle">Tareas: </h6>
				<div id="detallesTareas"></div>
				<hr>
				<h6 class="detailsSubtitle">K:</h6>
				<div id="detallesKTotalPorUsuario"></div>
				<hr>
				<h6 class="detailsSubtitle">Tareas en las que esta Activo:</h6>
				<div id="detallesTareasActivo"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-info" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>