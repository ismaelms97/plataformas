var estados;

$(document).ready(function(){
	estados = [];
	rellenarEstados();
	inputTasks();
	getNameEstrategia();

	if(document.getElementsByClassName("mainTitle")[0]){
		document.getElementsByClassName("mainTitle")[0].innerHTML = sessionStorage.getItem('titulo');
		
	}
	
	if(document.getElementById("formContent")){

		document.getElementById("butonDestroy").style.display = "none";
		document.getElementById("buttonHome").style.display = "none";

	}else{

		document.getElementById("butonDestroy").style.display = "initial";
		document.getElementById("buttonHome").style.display = "initial";

	}

})

function rellenarEstados() {

	if($("th").length >= 1){
		for (var i = 0; i < $("th").length; i++) {
			estados[i] = document.querySelectorAll("th")[i].innerText.toLowerCase();
		}
		console.log(estados);
	}
}

function inputTasks() {
	try {
		if(inTasks.length > 0){
			drawTable(inTasks, true);
			inTasks = orderByPrio(inTasks);
		}

	} catch (e) {
		console.log(e);

	}
}
/**
 * Función para pintar la tabla
 */
function drawTable(array , db) {
// ORdenamos el array por prioridad,
	array = orderByPrio(array);
	//tasks = orderByPrio(tasks);
	console.log(array)
	// PIntamos la tabla
	for (var i = 0; i < array.length; i++) {
		var tr = document.createElement("tr");
		document.getElementsByTagName("TBODY")[0].appendChild(tr);
		for (var j = 0; j < 10; j++) {
			if(document.getElementsByTagName("TR")[i+1].children.length != 10){
				var td = document.createElement("td");
				td.setAttribute("class", estados[j].replace(/\s/g, "-"));
				document.getElementsByTagName("TR")[i + 1].appendChild(td);
			}
		} 
		// Pintamos los RTC
		drawRTC(array, i, db);
	}


	if(db){

		// CLON ESTADO FINAL RECOGIDO DE BASE DE DATOS
		var rect = document.getElementsByClassName("rect");
		for (var i = 0; i < rect.length; i++) {
			var el = rect[i];
			var cln = $(el).clone();
			cln.attr("class", "clone orange");
			$(el).parent().siblings("."+array[i].estadoFinal.replace(/\s/g, "-")).append(cln);
			$(cln).css("display", "inline-block");
		}
	}
}

/**
 * Función para pintar los RTC
 */
function drawRTC(array, pos, db) {
	var estadoActual = 0;

	if(estados.length >= 1){

		for (var j = 0; j < estados.length; j++) {
			if (array[pos].estado.toLowerCase().startsWith(estados[j])) {
				estadoActual = j
			}
		}
	}


	var classes = 'rect';

	if(!db){

		// creamos el clon de los RTC
		if(inTasks.length > 0){
			if(inTasks[pos].estadoFinal.toLowerCase().startsWith(inTasks[pos].estado.toLowerCase()) &&
					array[pos].estado.toLowerCase().startsWith(inTasks[pos].estado.toLowerCase())){

				// Tricolor, inicio == Actual == Final
				classes += ' gradientGreyBlueOrange';

			}else if(array[pos].estado.toLowerCase().startsWith(inTasks[pos].estadoFinal.toLowerCase())){

				// Verde, COMPLETADO
				classes += ' green';

			}else if(array[pos].estado.toLowerCase().startsWith(inTasks[pos].estado.toLowerCase())){

				// GRIS/AZUL Inicio == Actual
				classes += ' gradientGreyBlue';

			}else{

				// AZUL, resto
				classes += ' blue';
			}
			console.log(array[pos].id + " " + array[pos].estado + "  " + inTasks[pos].id + " " + inTasks[pos].estadoFinal + "  " + array[pos].estado.toLowerCase().startsWith(inTasks[pos].estadoFinal.toLowerCase()))
		}
	}

// Solucionar problema con onclick event, no nos da ningun valor
	document.getElementsByTagName("TR")[pos + 1].children[estadoActual].innerHTML = '<div class="'+classes+'" data-posInitial="' + estadoActual + '" data-rtc="' + (pos + 1) + '" title="'+ array[pos].resumen +'" data-placement="left">'
	+ '<small class="tamano">'+ array[pos].tamano + '</small> '+ array[pos].id + ' <small class="complejidad">'+ array[pos].complejidad + '</small></div>';




	if(inTasks.length <= 0 && !db){

		var el = document.getElementsByClassName("rect")[pos];
		var cln = $(el).clone();
		cln.attr("class", "clone");
		$(el).parent().append(cln);
		$(cln).css("display", "none");

		dragDrop(array);
	}
	// Ejecutamos la funcion para mostrar los detalles
	document.getElementsByTagName("TR")[pos + 1].children[estadoActual].addEventListener("click", function(){
		verDetallesRTC(array, pos);
	})

	tooltip();
}

/**
 * Función que muestra la tooltip en html
 */
function tooltip(){
	$( function() {
		$( ".rect, .clone" ).tooltip();
	} );
}

if(document.getElementById("save")){
	document.getElementById("save").addEventListener('click', function(){
		saveData();
	});
}

function verDetallesRTC(array, i){
	$("#detallesRTC").modal("show");
	$('#detallesRTC').on('shown.bs.modal', function() {
		document.getElementById("detallesTitulo").innerHTML = "Detalles RTC: <strong style='font-size:20px;'>#"+ array[i].id + "</strong>";
		document.getElementById("detallesResumen").innerHTML = array[i].resumen;
		document.getElementById("detallesTamano").innerHTML = array[i].tamano;
		document.getElementById("detallesComplejidad").innerHTML = array[i].complejidad;
		document.getElementById("detallesPropietario").innerHTML = array[i].propiedad;
		document.getElementById("detallesPlanificadoPara").innerHTML = array[i].planificado;
	})
}

/*
 * Función que sirve para filtrar las tareas, versión 1.0, solo filtra por el
 * tipo de tareas: Incidencias, Tareas, Consulta.
 * 		@param  {Array}  la array a filtrar
 *		@param  {filtros} Un objeto con los filtros como nombres de propiedad
 */
function filter(array, filtros){
	// REVISAR FILTRADO COMPLEJO
	//	https://gist.github.com/jherax/f11d669ba286f21b7a2dcff69621eb72
	
//	var filtrado;
//	filtrado = array.filter(item => item.tipo.toLowerCase() == filtros.tipo[0] ||  item.tipo.toLowerCase() == filtros.tipo[1] ||  item.tipo.toLowerCase() == filtros.tipo[2]);
//	
//	return filtrado;
	
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

function emptyTable(){
	for (var j = $("TR").length -1; j > 0 ; j--) {
		$("TR")[j].remove();
	}
}

function getNameEstrategia(){
	$(document).ready(function(){
		$(".estartegiasCard").on("click", function(e) {
			sessionStorage.setItem('titulo', e.target.innerHTML.trim());
		})
	})
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
			own.push(task.propiedad);
			nombre = task.propiedad;
		var texto = '<div class="custom-control custom-checkbox">'+
		'<input type="checkbox" id="'+ nombre.toLowerCase() +'" value="'+ nombre.toLowerCase() +'" class="custom-control-input filtros taskPropertyOf">'+
		'<label class="custom-control-label" for="'+ nombre.toLowerCase()+'">'+ toCamelCase(nombre) +'</label><br> '+
		'</div>';
		$("#collapsePropertyOf").children(".card").append(texto);
		}else{
			console.log("Repetido");
		}
	})
	console.log("own");
	console.log(own);
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