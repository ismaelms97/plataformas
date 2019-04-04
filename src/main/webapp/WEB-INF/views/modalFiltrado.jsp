<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<div class="modal fade left" id="modalFiltrado" tabindex="-1"
	role="dialog" aria-labelledby="Modal" aria-hidden="true">
	<div class="modal-dialog modal-full-height modal-left " role="document">
		<!--Content-->
		<div class="modal-content">
			<!--Header-->
			<div class="modal-header">
				<p class="heading lead" id="detallesTitulo"></p>

				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true" class="white-text">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<h5 class="detailsSubtitle">Filtros</h5>
				<hr>
				<div class="card">
					<div class="card-header" id="headingOne" data-toggle="collapse"
						data-target="#collapseType">
						<h6 class="d-inline accordion-toggle" aria-expanded="false"
							aria-controls="collapseExample">Tipo</h6>
					</div>
				</div>
				<div class="collapse" id="collapseType">
					<div class="card card-body">
						<!-- Material unchecked -->
						<div class="custom-control custom-checkbox">
							<input type="checkbox" id="incidencia" value="incidencia"
								class="custom-control-input filtros taskType"><label
								class="custom-control-label" for="incidencia">Incidencia</label><br>
						</div>
						<div class="custom-control custom-checkbox">
							<input type="checkbox" id="consulta" value="consulta"
								class="custom-control-input filtros taskType"><label
								class="custom-control-label" for="consulta">Consulta</label><br>
						</div>
						<div class="custom-control custom-checkbox">
							<input type="checkbox" id="tarea" value="tarea" class="custom-control-input filtros taskType">
							<label class="custom-control-label" for="tarea">Tarea</label><br>
						</div>
					</div>
				</div>
				<div class="card">
					<div class="card-header" id="headingOne" data-toggle="collapse"
						data-target="#collapsePropertyOf">
						<h6 class="d-inline accordion-toggle" aria-expanded="false"
							aria-controls="collapseExample">Propietario</h6>
					</div>
				</div>
				<div class="collapse" id="collapsePropertyOf">
					<div class="card card-body">
					<!-- Propietario -->
					
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" id="filter" class="btn btn-warning"
					data-dismiss="modal">Filtrar</button>
				<button type="button" class="btn btn-outline-info"
					data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function() {
	// Objeto con los filtros
	var filters = {
			tipo : [],
			propiedad : [],
	};
	
	
	var arrayInTasksBackup = [];
	
	$(".filtros").on("click", function(){
		
		if((this).checked){
			
			if($(this).hasClass("taskType")){
				
				filters.tipo.push(this.value);
				
			}else if($(this).hasClass("taskPropertyOf")) {
				filters.propiedad.push(this.value);
				console.log(this.value);
			}
		}else{
			if($(this).hasClass("taskType")){
				filters.tipo.splice(filters.tipo.indexOf(this.value), 1);
				
			}else if($(this).hasClass("taskPropertyOf")) {
				filters.propiedad.splice(filters.propiedad.indexOf(this.value), 1);
				
			}
		}
		console.log("filtered:");
		console.log(filters);
	})
	
	$('#modalFiltrado').on('shown.bs.modal',function() {
		
		document.getElementById("filter").addEventListener("click", function() {
			if(filters.tipo.length >= 1 || filters.propiedad.length >= 1){
				arrayTasksBackup = filter(tasks, filters);
				arrayInTasksBackup = filter(inTasks, filters);
			}else{
				arrayTasksBackup = tasks;
				arrayInTasksBackup = inTasks;
			}
			
			emptyTable();
			drawTable(arrayInTasksBackup, true);
			drawTable(arrayTasksBackup, false);
			}, false);
		
			$(".card-header").on("click", function(){
				$(this).toggleClass("arrowDown");
			})
		})
	})
</script>