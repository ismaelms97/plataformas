<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<div class="modal fade" id="detallesRTC" tabindex="-1" role="dialog"
	aria-labelledby="centerTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-info" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="detallesTitulo"></h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<h6>Tamaño</h6>
				<div id="detallesTamano"></div>
				<hr>
				<h6>Complejidad</h6>
				<div id="detallesComplejidad"></div>
				<hr>
				<h6>Propiedad de</h6>
				<div id="detallesPropietario"></div>
				<hr>
				<h6>Planificado Para</h6>
				<div id="detallesPlanificadoPara"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>