<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<div class="modal fade" id="detallesRTC" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered"
		role="document">
		<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header modal-info">
				<p class="heading lead" id="detallesTitulo"></p>

				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>
			<div class="modal-body">
			<h6 class="detailsSubtitle">Resumen</h6>
				<div id="detallesResumen"></div>
				<hr>
				<h6 class="detailsSubtitle">Tamaño</h6>
				<div id="detallesTamano"></div>
				<hr>
				<h6 class="detailsSubtitle">Complejidad</h6>
				<div id="detallesComplejidad"></div>
				<hr>
				<h6 class="detailsSubtitle">Propiedad de</h6>
				<div id="detallesPropietario"></div>
				<hr>
				<h6 class="detailsSubtitle">Planificado Para</h6>
				<div id="detallesPlanificadoPara"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-info" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>