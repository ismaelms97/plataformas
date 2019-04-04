/**
 * Función que sirve para filtrar las tareas, versión 1.0, solo filtra por el
 * tipo de tareas: Incidencias, Tareas, Consulta.
 * 		@param  {Array}  la array a filtrar
 *		@param  {filtros} Un objeto con los filtros como nombres de propiedad
 */
function filter(array, filtros){
	// REVISAR FILTRADO COMPLEJO
	//	https://gist.github.com/jherax/f11d669ba286f21b7a2dcff69621eb72

	const filterKeys = Object.keys(filtros);
	return array.filter(eachObj => {
		return filterKeys.every(eachKey => {
			if (!filtros[eachKey].length) {
				return true; // passing an empty filter means that filter is ignored.
			}
			return filtros[eachKey].includes(eachObj[eachKey].toLowerCase());
		});
	});

}

/*
 * Función que sirve para filtrar las tareas, versión 1.0, solo filtra por el
 * tipo de tareas: Incidencias, Tareas, Consulta.
 */
function strategyFilter(array){

	var filtrado = array.filter(item => inTasks.find(item2 => item.id === item2.id));
	return filtrado;
}

/**
 * Appends al the owners of the tasks to the filtering modal
 * @returns
 */
function owners(){
	var own = [];
	var nombre = "";
	tasks.forEach(function(task){
		if(!own.includes(task.propiedad)){
			
			if(task.propiedad.toLowerCase() == "unassigned") {
				task.propiedad = "";
			}
			
			own.push(task.propiedad);
			nombre = task.propiedad.toLowerCase();
			var texto = '<div class="custom-control custom-checkbox">'+
			'<input type="checkbox" id="'+ nombre +'" value="'+ nombre +'" class="custom-control-input filtros taskPropertyOf">'+
			'<label class="custom-control-label" for="'+ nombre +'">'+ toCamelCase(nombre) +'</label><br> '+
			'</div>';
			$("#collapsePropertyOf").children(".card").append(texto);
		}
	})
	return own;
}

/**
 * Little function that formats a string a transforms it to camel case
 * 
 * @param str String to transform to Camel Case
 * @returns
 */
function toCamelCase(str) {
	return str.substring(0,1).toUpperCase() + str.substring(1,str.length).replace(/\W+(.)/g, function(match, chr)
			{
		return ' ' + chr.toUpperCase();
			});
}

function filtering(){
	// Objeto con los filtros
	var filters = {
			tipo : [],
			propiedad : [],
			urgente : [],
	};

	$(".filtros").on("click", function(){

		if((this).checked){

			if($(this).hasClass("taskType")){
				filters.tipo.push(this.value);

			}else if($(this).hasClass("taskPropertyOf")) {
				filters.propiedad.push(this.value);

			}else if($(this).hasClass("taskUrgent")){
				filters.urgente.length = 0;

				if(this.value == ""){
					filters.urgente.push("sí");
					filters.urgente.push("no");
					console.log("Urgente");
				}
				filters.urgente.push(this.value);
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

	modalFilter(filters);
}

function modalFilter(filters){
	var arrayInTasksBackup = [];

	$('#modalFiltrado').on('shown.bs.modal',function() {

		document.getElementById("filter").addEventListener("click", function() {
			if(filters.tipo.length >= 1 || filters.propiedad.length >= 1 || filters.urgente.length >= 1){
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
}