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
			drawTable(inTasks);
			console.log("enter")
		}

	} catch (e) {
		console.log(e);

	}
}

function drawTable(array) {
	array = orderByPrio(array);
	if (array.length >= 1) {
		for (var i = 0; i < array.length; i++) {
			var tr = document.createElement("tr");
			document.getElementsByTagName("TBODY")[0].appendChild(tr);
			for (var j = 0; j < 10; j++) {
				var el = document.createElement("td");
				el.setAttribute("class", estados[j].replace(/\s/g, "-"));
				document.getElementsByTagName("TR")[i + 1].appendChild(el);
			}
			drawRTC(array, i);
		}

		// creamos el clon de los RTC
		var rect = document.getElementsByClassName("rect");
		for (var i = 0; i < rect.length; i++) {
			var el = rect[i];
			var cln = $(el).clone();
			cln.attr("class", "clone");
			$(el).parent().append(cln);
			$(cln).css("display", "none");
		}

	}

}
function drawRTC(array, pos) {
	var estadoActual = 0;

	if(estados.length >= 1){

		for (var j = 0; j < estados.length; j++) {
			if (array[pos].estado.toLowerCase().startsWith(estados[j])) {
				estadoActual = j
			}
		}
	}

	document.getElementsByTagName("TR")[pos + 1].children[estadoActual].innerHTML = '<div class="rect" data-posInitial="' + estadoActual + '" data-rtc="' + (pos + 1) + '" title="'+ array[pos].resumen +'" data-placement="left" onclick="verDetallesRTC('+array +','+  pos +')">'
	+ '<small class="tamano">'+ array[pos].tamano + '</small> '+ array[pos].id + ' <small class="complejidad">'+ array[pos].complejidad + '</small></div>';

	dragDrop();
	tooltip()
}

/* Función que muestra la tooltip en html */
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

function emptyTable(){
	for (var j = $("TR").length -1; j > 0 ; j--) {
		$("TR")[j].remove();
	}
}

function getNameEstrategia(){
	$(document).ready(function(){
		$("#estartegiasCard").on("click", function(e) {
			console.log(e.target);
			$(".mainTitle").html(e.target.innerHTML.trim());
		})
	})
}