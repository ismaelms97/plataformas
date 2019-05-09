var estados;
var equipo = [];
var tipoTarea = []

$(document).ready(function(){
	estados = [];
	rellenarEstados();
	inputTasks();
	showListDaily();
	

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

/**
 * Función que rellena una array con los estado disponibles en la tabla, esta array se utiliza para comprobaciones futuras
 * 
 */
function rellenarEstados() {
	if($("th").length >= 1){
		for (var i = 0; i < $("th").length; i++) {
			estados[i] = document.querySelectorAll("th")[i].innerText.toLowerCase();

		}
		console.log("Estados", estados);
	}
} 

/**
 * Función para pintar las tareas de base de datos en la tabla
 * 
 */
function inputTasks() {
	try {
		if(inTasks.length > 0){
			drawTable(inTasks, true);
			fillTypefilter(inTasks);
		}

	} catch (e) {
		console.log(e);

	}
}
/**
 * Función para pintar la tabla
 * 
 * @param array  array a pintar
 * @param db  Viene de Base de datos?
 * 
 */
function drawTable(array , db) {
//	Ordenamos el array por prioridad,
	array = orderBy(array, orderType, orderStyle);
	inTasks = orderBy(inTasks, orderType, orderStyle);

	// PIntamos la tabla
	for (var i = 0; i < array.length; i++) {
		var tr = document.createElement("tr");
		document.getElementsByTagName("TBODY")[0].appendChild(tr);
		for (var j = 0; j < 10; j++) {
			if(document.getElementsByTagName("TR")[i+1].children.length != 10){
				var td = document.createElement("td");
				td.setAttribute("class", estados[j].replace(/\s/g, "-").replace(/[\.]/g,  "_"));
				document.getElementsByTagName("TR")[i + 1].appendChild(td);
			}
		} 
		// Pintamos los RTC
		drawRTC(array, i, db);
	}

	if(db){
		console.log(inDailys);
		console.log(array)
		
		if(inDailys.length > 0){
			equipo = owners(inTasks);
			console.log(equipo)
		}
		
		if(equipo.length > 1 && equipo[0].nombre != "" && tasks.length <= 0){
			drawTeamUsers(equipo, false);
		}
		

		// CLON ESTADO FINAL RECOGIDO DE BASE DE DATOS
		var rect = document.getElementsByClassName("rect");
		
			for (var i = 0; i < rect.length; i++) {
				var el = rect[i];
				var cln = $(el).clone();
				cln.attr("class", "clone orange");
				$(el).parent().siblings("."+array[i].estadoFinal.replace(/\s/g, "-").replace(/[\.]/g,  "_")).append(cln);
				$(cln).css("display", "inline-block");
			}
		
	} else {
		
		for (var i = 0; i < array.length; i++) {
			if(array[i].modified){
				var el = document.getElementsByClassName("rect")[i];
				var cln = $(el).clone();
				cln.attr("class", "rect orange");
				if($(el).parent().siblings("."+array[i].estadoFinal.replace(/\s/g, "-").replace(/[\.]/g,  "_")).length != 0){

					$(el).parent().siblings("."+array[i].estadoFinal.replace(/\s/g, "-").replace(/[\.]/g,  "_")).append(cln);
					document.getElementsByClassName("clone")[i].setAttribute("style", 'display: inline-block')

				} else {

					$(el).parent().append(cln);
					document.getElementsByClassName("clone")[i].setAttribute("style", 'display: none')

				}

				$(cln).css("display", "inline-block");
				document.getElementsByClassName("rect")[i].parentElement.firstChild.remove();
			}
		}
	}
}

/**
 * Función para pintar los RTC
 * 
 * @param array  Array en la que buscaremos las tareas
 * @param pos	 La posición de la array que queremos pintar
 * @param db	 Viene de base de datos?
 * 
 */

function drawRTC(array, pos, db) {
	var estadoActual = -1;

	if(estados.length >= 1){

		for (var j = 0; j < estados.length; j++) {
			if(db){
				if (array[pos].estado.toLowerCase().startsWith(estados[j])) {
					estadoActual = j
				}
			} else {
				try{
					if (array[pos].estadoActual.toLowerCase().startsWith(estados[j])) {
						estadoActual = j
					}
				}catch(e){
					if (array[pos].estado.toLowerCase().startsWith(estados[j])) {
						estadoActual = j
					}
				}
			}
		}
	}


	var classes = 'rect';

	if(!db){

		// creamos el clon de los RTC
		array = orderBy(array, orderType, orderStyle);
		arrayInTasksBackup = orderBy(arrayInTasksBackup, orderType, orderStyle);
		if(arrayInTasksBackup.length > 0){
			if(arrayInTasksBackup[pos].estadoFinal.toLowerCase().startsWith(arrayInTasksBackup[pos].estado.toLowerCase()) &&
					array[pos].estadoActual.toLowerCase().startsWith(arrayInTasksBackup[pos].estado.toLowerCase())){

				// Tricolor, inicio == Actual == Final
				classes += ' gradientGreyBlueOrange';

			}else if(array[pos].estadoActual.toLowerCase().startsWith(arrayInTasksBackup[pos].estadoFinal.toLowerCase())){

				// Verde, COMPLETADO
				classes += ' green';

			}else if(array[pos].estadoActual.toLowerCase().startsWith(arrayInTasksBackup[pos].estado.toLowerCase())){

				// GRIS/AZUL Inicio == Actual
				classes += ' gradientGreyBlue';

			}else{
				// AZUL, resto
				classes += ' blue';
			}
		}

	} else {
		if(array[pos].estado.toLowerCase().startsWith(array[pos].estadoFinal.toLowerCase())){

			// GRIS/AZUL Inicio == Actual
			classes += ' gradientGreyOrange';

		}
	}

	if(estadoActual != -1){
		document.getElementsByTagName("TR")[pos + 1].children[estadoActual].innerHTML = '<div class="'+classes+'" data-posInitial="' + estadoActual + '" data-rtc="' + (pos + 1) + '" title="'+ array[pos].resumen +'" data-placement="left">'
		+ '<small class="tamano">'+ array[pos].tamano + '</small> '+ array[pos].id + ' <small class="complejidad">'+ array[pos].complejidad + '</small></div>';
	}



	if(inTasks.length <= 0 && !db){

		var el = document.getElementsByClassName("rect")[pos];
		var cln = $(el).clone();
		cln.attr("class", "clone");
		$(el).parent().append(cln);
		$(cln).css("display", "none");

		dragDrop(array, true);
	}else{
		dragDrop(array, false);
	}
	if(estadoActual != -1){
		// Ejecutamos la funcion para mostrar los detalles
		document.getElementsByTagName("TR")[pos + 1].children[estadoActual].addEventListener("click", function(){
			verDetallesRTC(array, pos);
		})
	}
	tooltip();
}

/**
 * Función que muestra la tooltip en html
 * 
 */
function tooltip(){
	$( function() {
		$( ".rect, .clone" ).tooltip();
	} );
}

if(document.getElementById("save")){
	document.getElementById("save").addEventListener('click', function(){
		$("div.button").addClass("disabled");
		saveData();
	});
}

/**
 * Función que muestra los detalles de cada tarea
 * 
 * @param array : Array en la que buscar los datos
 * @param i		: Posición de la tarea que queremos encontrar
 * 
 */
function verDetallesRTC(array, i){
	$("#detallesRTC").modal("show");
	$('#detallesRTC').on('shown.bs.modal', function() {
		document.getElementById("detallesTitulo").innerHTML = "Detalles RTC: <strong style='font-size:20px;'>#"+ array[i].id + "</strong>";
		document.getElementById("detallesResumen").innerHTML = array[i].resumen;
		document.getElementById("detallesTamano").innerHTML = array[i].tamano;
		document.getElementById("detallesComplejidad").innerHTML = array[i].complejidad;
		document.getElementById("detallesPropietario").innerHTML = array[i].propiedad;
		document.getElementById("detallesPlanificadoPara").innerHTML = array[i].planificado;
		document.getElementById("detallesK").innerHTML = array[i].k;
	})
}

/**
 * Función que vacia todo el elemento DOM que es la tabla 
 * 
 */
function emptyTable(){
	for (var j = $("TR").length -1; j > 0 ; j--) {
		$("TR")[j].remove();
	}
}