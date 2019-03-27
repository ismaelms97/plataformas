var estados = [];
rellenarEstados();

if(document.getElementById("formContent")){
	
	document.getElementById("butonDestroy").style.visibility = "hidden";
	document.getElementById("buttonHome").style.visibility = "hidden";
		
	
}else{
	
	document.getElementById("butonDestroy").style.visibility = "visible";
	document.getElementById("buttonHome").style.visibility = "visible";
	
}

function rellenarEstados() {
	if($("th").length >= 1){
		for (var i = 0; i < $("th").length; i++) {
			estados[i] = document.querySelectorAll("th")[i].innerText.toLowerCase();
		}
		console.log(estados);
	}
}

function drawTable() {
	if (tasks.length >= 1) {
		for (var i = 0; i < tasks.length; i++) {
			var tr = document.createElement("tr");
			document.getElementsByTagName("TBODY")[0].appendChild(tr);
			for (var j = 0; j < 10; j++) {
				var el = document.createElement("td");
				el.setAttribute("class", estados[j].replace(/\s/g, "-"));
				document.getElementsByTagName("TR")[i + 1].appendChild(el);
			}
			drawRTC(i);
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
function drawRTC(pos) {
	var estadoActual = 0;

	if(estados.length >= 1){

		for (var j = 0; j < estados.length; j++) {
			if (tasks[pos].estado.toLowerCase().startsWith(estados[j])) {
				estadoActual = j
			}
		}
	}

//	if(tasks[pos].complejidad.toLowerCase().startsWith("sin asignar")){
//	tasks[pos].complejidad = 0;
//	}

//	if(tasks[pos].tamano.toLowerCase().startsWith("sin asignar")){
//	tasks[pos].tamano = 0;
//	}


	document.getElementsByTagName("TR")[pos + 1].children[estadoActual].innerHTML = '<div class="rect" data-posInitial="' + estadoActual + '" data-rtc="' + (pos + 1) + '" title="'+ tasks[pos].resumen +'" data-placement="left">'
	+ '<small class="tamano">'+ tasks[pos].tamano + '</small> '+ tasks[pos].id + ' <small class="complejidad">'+ tasks[pos].complejidad + '</small></div>';

	dragDrop();
	tooltip()
}

/* Función que muestra la tooltip en html  */
function tooltip(){
	$( function() {
		$( ".rect, .clone" ).tooltip();
	} );
}

if(document.getElementById("save")){
	document.getElementById("save").addEventListener('click', saveStrategy);
}