var filters;
var arr, daily, orderType, orderStyle;

$(document).ready(function(){
//	Objeto con los filtros
	filters = {
			tipo : [],
			propiedad : [],
			urgente : [],
	};
	daily = "";
	arr = [];

})

/**
 * Función que sirve para filtrar las tareas, versión 1.0, solo filtra por el
 * tipo de tareas: Incidencias, Tareas, Consulta.
 * 		@param  {Array}  la array a filtrar
 *		@param  {filtros} Un objeto con los filtros como nombres de propiedad
 */
function filter(array, filtros){
	

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

/**
 * 	Función que sirve para filtrar las tareas, versión 2.0, Filtra según un objeto que contiene arrays pasada por parametros
 *	@returns
 *
 */
function strategyFilter(array){

	var filtrado = array.filter(item => inTasks.find(item2 => item.id === item2.id));
	return filtrado;
}

/**
 * 	Appends all the owners of the tasks to the filtering modal
 * 	@returns
 */
function owners(array){
	var own = [];
	var backup = [];
	var name = "";
	array.forEach(function(task){
		if(!backup.includes(task.propiedad)){
			
			if (task.propiedad.toLowerCase() != "unassigned"){
				
				own.push({nombre : task.propiedad, k: 0});
				backup.push(task.propiedad);
				
				name = task.propiedad.toLowerCase();
				var texto = '<div class="custom-control custom-checkbox">'+
				'<input type="checkbox" id="'+ name +'" value="'+ name +'" class="custom-control-input filtros taskPropertyOf">'+
				'<label class="custom-control-label" for="'+ name +'">'+ toCamelCase(name) +'</label><br> '+
				'</div>';
				$("#collapsePropertyOf").children(".card").append(texto);

			}
		}
	})

	own.push({nombre : "Sin Propietario", k: 0});
	name = "Sin Propietario".toLowerCase();
	var texto = '<div class="custom-control custom-checkbox">'+
	'<input type="checkbox" id="'+ name +'" value="'+ name +'" class="custom-control-input filtros taskPropertyOf">'+
	'<label class="custom-control-label" for="'+ name +'">'+ toCamelCase(name) +'</label><br> '+
	'</div>';
	$("#collapsePropertyOf").children(".card").append(texto);

	return own;
}

//function userNoExists(array, user){
//array.forEach(function(us){
//	if(us.nombre == user) return false;
//})
//return true;
//}

/**
 * Appends all the owners of the tasks to the filtering modal of a daily, when the users come from the database
 * @returns
 */
function ownersDaily(array){

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
}

/**
 * Function that activates the filtering system
 * @returns
 */
function filtering(){


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

			}else if($(this).hasClass("daily")){
				daily = (this.value);
			}
			
			if($(this).hasClass("orderAsc") || $(this).hasClass("orderDesc")){
				orderStyle = this.value;
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

	modalFilter();
}

/**
 * 	Function with the action performed when the filter button is pressed 
 * 	@returns
 */
function onClickedFilter(){
	if(filters.tipo.length >= 1 || filters.propiedad.length >= 1 || filters.urgente.length >= 1){
		arrayTasksBackup = filter(tasks, filters);
		arrayInTasksBackup = filter(inTasks, filters);
		arr = filter(inTasks, filters);

	}else{

		arrayTasksBackup = tasks.slice(0);
		arrayInTasksBackup = inTasks.slice(0);
	}
	
	orderType = document.getElementById("orderByUser").value;

	if(daily.trim() != ""){
		chooseDaily();
	}
	
	aurArr = arr.slice();
	emptyTable();
	drawTable(arrayInTasksBackup, true);
	drawTable(arrayTasksBackup, false);
	drawTable(aurArr, false);
}

/**
 *	Function that make the filtering event possible, when clicked, enables the execution of the filter
 *	@returns
 */
function modalFilter(){

	$('#modalFiltrado').on('show.bs.modal',function() {
		document.getElementById("filter").removeEventListener('click', onClickedFilter);
		document.getElementById("filter").addEventListener("click", onClickedFilter);

		$(".card-header").on("click", function(){

			$(this).toggleClass("arrowDown");
		})

	})
}

/**
 *	Function that serves to show all the dailys in the filter modal
 *	@returns
 */
function showListDaily(){
	try{
		if(inDailys.length >= 1){
			$(".daily-body").show();
			inDailys.forEach(function(d){

				var daily = '<div class="custom-control custom-radio">'+
				'<input type="radio" class="custom-control-input filtros daily" id="'+d.id+'" name="dailyRadio" value="'+d.fecha+'">'+
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

/**
 *	Function to activate the actions when a daily is choosed
 *	@returns
 */
function chooseDaily(){
	arr = orderBy(inTasks, orderType, orderStyle)
	var dateSelected = daily;
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
	arr = filter(arr,filters);
}

function fillTypefilter(array){
	var taskType = document.querySelectorAll(".taskType:checked + label");

	array.forEach(function(task){

		if(!tipoTarea.includes(task.tipo)){
			tipoTarea.push(task.tipo);
		}
	});	

	var myNode = document.querySelectorAll("#collapseType  .card")[0];
	while (myNode.firstChild) {
		myNode.removeChild(myNode.firstChild);
	}

	tipoTarea.forEach(function(tipo){
		var i = tipo.toLowerCase();
		var text = '<div class="custom-control custom-checkbox">';

//		for (var k = 0; k < taskType.length; k++) {
//		if(taskType)
		text += '<input type="checkbox" id="'+i+'" value="'+i+'" class="custom-control-input filtros taskType">';
//		}

		text += '<label class="custom-control-label" for="'+i+'">'+toCamelCase(i)+'</label><br></div>';

		document.querySelectorAll("#collapseType .card")[0].innerHTML += text;
	})
	filtering();
}