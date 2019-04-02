var estados;


$(document).ready(function(){
	estados = [];
	rellenarEstados();
	inputTasks();


	if(document.getElementById("formContent")){

		document.getElementById("butonDestroy").style.visibility = "hidden";
		document.getElementById("buttonHome").style.visibility = "hidden";

	}else{

		document.getElementById("butonDestroy").style.visibility = "visible";
		document.getElementById("buttonHome").style.visibility = "visible";

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
		}

	} catch (e) {
		console.log(e);

	}
}
/**
 * Función para pintar la tabla
 * */
function drawTable(array , db) {
//	ORdenamos el array por prioridad, 
	array = orderByPrio(array);

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
 * */
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
			if(inTasks[pos].estadoFinal == inTasks[pos].estado && array[pos].estado == inTasks[pos].estado){

				// Tricolor, inicio == Actual == Final
				classes += ' gradientGreyBlueOrange';

			}else if(array[pos].estado == inTasks[pos].estadoFinal){

				// Verde, COMPLETADO
				classes += ' green';

			}else if(array[pos].estado == inTasks[pos].estado){

				// GRIS/AZUL
				classes += ' gradientGreyBlue';

			}else{

				//AZUL
				classes += ' blue';
			}
		}
	}

//	Solucionar problema con onclick event, no nos da ningun valor
	document.getElementsByTagName("TR")[pos + 1].children[estadoActual].innerHTML = '<div class="'+classes+'" data-posInitial="' + estadoActual + '" data-rtc="' + (pos + 1) + '" title="'+ array[pos].resumen +'" data-placement="left">'
	+ '<small class="tamano">'+ array[pos].tamano + '</small> '+ array[pos].id + ' <small class="complejidad">'+ array[pos].complejidad + '</small></div>';




	if(inTasks.length <= 0 && !db){

		var el = document.getElementsByClassName("rect")[pos];
		var cln = $(el).clone();
		cln.attr("class", "clone");
		$(el).parent().append(cln);
		$(cln).css("display", "none");

		dragDrop();
	}
	// Ejecutamos la funcion para mostrar los detalles
	document.getElementsByTagName("TR")[pos + 1].children[estadoActual].addEventListener("click", function(){
		verDetallesRTC(array, pos);
	})

	tooltip()
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
	getNameEstrategia();
	document.getElementById("save").addEventListener('click', saveStrategy);
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

/* Función que sirve para filtrar las tareas, versión 1.0, solo filtra por el tipo de tareas: Incidencias, Tareas, Consulta. */
function filter(array, filtros){

	var filtrado = array.filter(item => item.tipo.toLowerCase() == filtros[0] ||  item.tipo.toLowerCase() == filtros[1] ||  item.tipo.toLowerCase() == filtros[2]);

	return filtrado;
}

/* Función que sirve para filtrar las tareas, versión 1.0, solo filtra por el tipo de tareas: Incidencias, Tareas, Consulta. */
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
		$("#estartegiasCard").on("click", function(e) {
			$(".mainTitle").html(e.target.innerHTML.trim());
		})
	})
}