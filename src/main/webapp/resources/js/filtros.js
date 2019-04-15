/**
 * Función que sirve para filtrar las tareas, versión 1.0, solo filtra por el
 * tipo de tareas: Incidencias, Tareas, Consulta.
 * 		@param  {Array}  la array a filtrar
 *		@param  {filtros} Un objeto con los filtros como nombres de propiedad
 */
function filter(array, filtros){
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
 * Función que sirve para filtrar las tareas, versión 2.0, Filtra según un objeto que contiene arrays pasada por parametros
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
	var bool = false;
	var nombre = "";
	tasks.forEach(function(task){
		if(!own.includes(task.propiedad)){

			if (task.propiedad.toLowerCase() != "unassigned"){

				own.push(task.propiedad);
				nombre = task.propiedad.toLowerCase();
				var texto = '<div class="custom-control custom-checkbox">'+
				'<input type="checkbox" id="'+ nombre +'" value="'+ nombre +'" class="custom-control-input filtros taskPropertyOf">'+
				'<label class="custom-control-label" for="'+ nombre +'">'+ toCamelCase(nombre) +'</label><br> '+
				'</div>';
				$("#collapsePropertyOf").children(".card").append(texto);

			}
		}
	})

	own.push("Sin Propietario");
	nombre = "Sin Propietario".toLowerCase();
	var texto = '<div class="custom-control custom-checkbox">'+
	'<input type="checkbox" id="'+ nombre +'" value="'+ nombre +'" class="custom-control-input filtros taskPropertyOf">'+
	'<label class="custom-control-label" for="'+ nombre +'">'+ toCamelCase(nombre) +'</label><br> '+
	'</div>';
	$("#collapsePropertyOf").children(".card").append(texto);
	
	return own;
}

/**
 * Little function that formats a string and transforms it to camel case
 * 
 * @param str String to transform to Camel Case
 * @returns
 */
function toCamelCase(str) {

	str = str.toLowerCase().split(' ');
	for (var i = 0; i < str.length; i++) {
		str[i] = str[i].charAt(0).toUpperCase() + str[i].slice(1);
	}
	return str.join(' ');

//	return str.substring(0,1).toUpperCase() + str.substring(1,str.length).replace(/\W+(.)/g, function(match, chr)
//	{
//	return ' ' + chr.toUpperCase();
//	});
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
				}
				filters.urgente.push(this.value);
			}

		}else{

			if($(this).hasClass("taskType")){
				filters.tipo.splice(filters.tipo.indexOf(this.value), 1);

			}else if($(this).hasClass("taskPropertyOf")) {
				filters.propiedad.splice(filters.propiedad.indexOf(this.value), 1);

			}
		}

		console.log("Filtered: " , filters);
	})

	modalFilter(filters);
}

function modalFilter(filters){
	
	$('#modalFiltrado').on('shown.bs.modal',function() {

		document.getElementById("filter").addEventListener("click", function() {
			if(filters.tipo.length >= 1 || filters.propiedad.length >= 1 || filters.urgente.length >= 1){
				arrayTasksBackup = filter(tasks, filters);
				arrayInTasksBackup = filter(inTasks, filters);
				console.log(inTasks);
				console.log("Entro"); 
			}else{
				arrayTasksBackup = tasks.slice(0);
				arrayInTasksBackup = inTasks.slice(0);
			}
			console.log("BackUpTasks", arrayTasksBackup);
			console.log("BackUpInTasks", arrayInTasksBackup);
			
			emptyTable();
			drawTable(arrayInTasksBackup, true);
			drawTable(arrayTasksBackup, false);
		}, false);

		$(".card-header").on("click", function(){
			
			$(this).toggleClass("arrowDown");
		})
		chooseDaily();
	})
}
function showListDaily(){
	try{
		if(inDailys.length >= 1){
			$(".daily-body").show();
			inDailys.forEach(function(d){

				var daily = '<div class="custom-control custom-radio">'+
				'<input type="radio" class="custom-control-input filtros daily" id="'+d.id+'" name="dailyRadio" value="'+d.id+'">'+
				'<label class="custom-control-label" for="'+d.id+'">'+d.fecha+'</label></div>';



				$("#collapseDaily").children(".card").append(daily);
			})
			chooseDaily();
		}else{
			$(".daily-body").hide();
		}
	}catch(e){
		console.log(e)
	}
}


function chooseDaily(){

	$('input[name="dailyRadio"]').change(function(){
		var arr = orderBy(inTasks)
		var dateSelected = this.nextSibling.innerHTML;
		inDailys.forEach(function(daily){
			if(daily.fecha == dateSelected){
				arr.forEach(function(task){
					daily.estadoActual.forEach(function(taskStatus){
						if(task.id == taskStatus[0]){
							task.estadoActual = taskStatus[1].toLowerCase();
							task.propiedad = taskStatus[2];
						}
					})
					
				})
			}
		});
		emptyTable();
		drawTable(inTasks, true);
		drawTable(arr, false);
	});

}