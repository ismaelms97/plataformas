var arrayTasksBackup;
var arrayInTasksBackup;
var k = 0;
try{
	arrayTasksBackup = tasks.slice(0);
	arrayInTasksBackup = inTasks.slice(0);
}catch(e){

}

function dragDrop(arr, bool){

	// Con este codigo conseguimos que se mueva cada tarea unicamente en su eje x, y
	// a su vez que cuando los dejes en el sitio, cambien de color
	$(function () {
		if(bool){
			$(".rect").draggable(
					{
						axis: "x",
						containment: ".table-responsive",
						scroll: false,
						opacity: 0.7,
						helper: "clone",
						start: function (event, ui) {
							$(".ui-draggable-dragging").removeClass("noLeft");

							// Descomentar esto para seleccionar
							// if ($(this).hasClass("ui-selected")){
							// selected = $(".ui-selected").each(function()
							// {
							// var el = $(this);
							// el.data("offset", el.offset());
							// });
							// }
							// else {
							// selected = $([]);
							// $(".rect").removeClass("ui-selected");
							// }
							// offset = $(this).offset();

						},

						drag: function (event, ui) {

							// Descomentar esto para seleccionar
							// var dt = ui.position.top - offset.top, dl =
							// ui.position.left
							// - offset.left;
							// // Coje todos los elementos seleccionados excepto
							// $(this),
							// que es el elemento que queremos mover
							// selected.not(this).each(function() {

							// // Crea la variable para que no necesitemos
							// llamar a $(this)
							// // el = Elemento actual
							// // off = En que posicion esteaba este elemento
							// antes de
							// moverlo
							// var el = $(this), off = el.data("offset");
							// el.css({top: off.top + dt, left: off.left + dl});
							// });
							// },
						},

						stop: function (event, ui) {
							document.getElementsByClassName("rect")[(this.getAttribute("data-rtc") - 1)].style.display = "";

							if (this.parentElement.classList.contains(estados[this.getAttribute("data-posInitial")].replace(/\s/g, "-").replace(/[\.]/g, "_")) && arr[this.getAttribute("data-rtc") - 1].modified) {

								arr[this.getAttribute("data-rtc") - 1].modified = false;
								arr[this.getAttribute("data-rtc") - 1].k = 0;

							} else {

								if (estados.indexOf(this.parentElement.classList[0].replace(/-/g, " ").replace(/_/g, ".")) >= this.getAttribute("data-posInitial")) {
									arr[this.getAttribute("data-rtc") - 1].modified = true;
									arr[this.getAttribute("data-rtc") - 1].estadoFinal = this.parentElement.classList[0].replace(/-/g, " ").replace(/_/g, ".");
									arr[this.getAttribute("data-rtc") - 1].k = calculateK(this.childNodes[2].innerHTML - 1, this.childNodes[0].innerHTML, this.getAttribute("data-posInitial"), estados.indexOf(arr[this.getAttribute("data-rtc") - 1].estadoFinal))

									console.log(arr[this.getAttribute("data-rtc") - 1])
								}
							}

							habilitarBotonEnvio();
							drawTeamUsers(equipo);
							document.getElementsByClassName("k")[0].innerHTML = "K: " + k;
						},
					});
		}
		$("td").droppable(
				{
					drop: function (event, ui) {
						if(!ui.draggable[0].classList.contains("chip")){
							// Pinta de rojo todos los modificados
							$(ui.draggable[0]).addClass("orange");
							var $this = $(this);
							ui.draggable.position({
								my: "center",
								at: "center",
								of: $this,
								using: function (pos) {
									$(this).animate(pos, 200, "linear");

								}
							});

							if (event.target.children.length == 1) {

								event.target.children[0].style.display = "none";

								// Al colocar el rtc en su posicion inicial, cambiale el color, y su estado de modificado
								if (arr[ui.draggable[0].getAttribute("data-rtc") - 1].modified) {
									$(ui.draggable[0]).removeClass("orange");

								}
							}

							$(ui.draggable[0]).addClass("noLeft");
							if (estados.indexOf(event.target.classList[0].replace(/-/g, " ").replace(/_/g, ".")) >= ui.draggable[0].getAttribute("data-posInitial")) {

								if (document.getElementsByClassName("clone")[(ui.draggable[0].getAttribute("data-rtc") - 1)].style.display == "none"
									&& !(event.target.children.length >= 1)) {

									document.getElementsByClassName("clone")[(ui.draggable[0].getAttribute("data-rtc") - 1)].style.display = "";

								}

								$(ui.draggable[0]).appendTo(event.target);

							}
						}else{
							// SI estas arrastrando a los usuarios
							if(event.target.children.length >= 1){

								var user = $(ui.draggable[0]).find(".name").text();
								if(user.toLowerCase() == "sin propietario"){
									user = "unassigned"
								}
								console.log(equipo);
								tasks.find(tarea => parseInt(tarea.id) === parseInt(event.target.children[0].innerText.split("\n")[1].trim())).propiedad = user;
								console.log(parseInt(event.target.children[0].innerText.split("\n")[1].trim()));

								$.notify({
									title: '<strong>Cambio de Propietario</strong>',
									message: ' en la tarea ' + event.target.children[0].innerText.split("\n")[1].trim() + '.'
								},{
									type: 'success',
									newest_on_top: true,
									placement: {
										from: "bottom",
										align: "right"
									},
									delay: 1000
								});

							}
							drawTeamUsers(equipo);
							document.getElementsByClassName("k")[0].innerHTML = "K: " + k;
						}
					}
				});

		// SI se quiere seleccionar, solo hace falta desbloquear el siguiente
		// codigo, aunque si se quiere seleccionar, buscar información sobre  la seleccion multiple

		// $(".rect").selectable()

		// Manualmente activa el select que los elementos clicados
		// $( ".rect" ).click( function(e){
		// if (e.ctrlKey == false) {
		// // Si la tecla de comando se presiona no deselecciones los elementos
		// $( ".rect" ).removeClass("ui-selected");
		// $(this).addClass("ui-selecting");
		// }
		// else {
		// if ($(this).hasClass("ui-selected")) {
		// // Elimina la clase selected de lo elementos seleccionados
		// $(this).removeClass("ui-selected");
		// }
		// else {
		// // Sino, añadeles la clase
		// $(this).addClass("ui-selecting");
		// }
		// }

		// $( ".rect" ).data("selectable")._mouseStop(null);

		// });

	})
}

function saveData() {
	if(inTasks.length == 0){
		strategy = new Object();
		strategy.tasks = [];
		strategy.tasksModified = [];

		tasks.forEach(task => {
			if (task.modified) {
				strategy.tasks.push(task);
			}
		});

		var tasksToString = "";

		strategy.tasks.forEach(task => {
			tasksToString += "RTC:" + task.id + "--";
			tasksToString += "Tipo:" + task.tipo + "--";
			tasksToString += "Estado:" + task.estadoActual + "--";
			tasksToString += "EstadoFinal:" + task.estadoFinal+ "--";
			tasksToString += "prioridad:" + task.prioridad + "--";
			tasksToString += "resumen:" + task.resumen + "--";
			tasksToString += "tamaño:" + task.tamano + "--";
			tasksToString += "complejidad:" + task.complejidad + "--";
			tasksToString += "propiedad:" + task.propiedad + "--";
			tasksToString += "peticionario:" + task.peticionario + "--";

			if(task.relevante == "Sí"){
				tasksToString += "relevante:true--";
			} else {
				tasksToString += "relevante:false--";
			}

			if(task.urgente == "Sí"){
				tasksToString += "urgente:true--";
			} else {
				tasksToString += "urgente:false--";
			}

			tasksToString += "planificado:" + task.planificado + "qwer" ;
		});

		tasksToString = tasksToString.substring(0, tasksToString.length - 4);
		$.ajax({
			type: "POST",
			url: "/estrategia/saveEstrategia",
			data: {
				stratTasks: tasksToString
			}, success: function (data) {

				if(data == "true"){

					location.href = "/estrategia/panelControl";
					console.log("success");

				}else{

					$.notify({
						title: '<strong>Error</strong>',
						message: 'al guardar estrategia'
					},{
						type: 'danger',
						newest_on_top: true,
						placement: {
							from: "top",
							align: "center"
						},
						delay: 2000
					});
					$("div.button").removeClass("disabled");
				}

			}
		});
	} else {
		var date = new Date();
		tasksToString = "";
		tasks.forEach(task => {
			tasksToString += "id:" + task.id + "--";
			tasksToString += "estadoActual:" + task.estadoActual + "--"; 
			tasksToString += "subEstadoActual:" + task.estadoActual+ "--";
			tasksToString += "propiedad:" + task.propiedad+ "qwer";
		});

		$.ajax({
			type: "POST",
			url: "/daily/saveDaily",
			data: {
				stratDaily: tasksToString
			}, success: function (data) {

				if(data == "true"){

					location.href = "/estrategia/panelControl";
					console.log("success");

				}else{

					$.notify({
						title: '<strong>Error</strong>',
						message: 'al guardar daily'
					},{
						type: 'danger',
						newest_on_top: true,
						placement: {
							from: "top",
							align: "center"
						},
						delay: 2000
					});
					$("div.button").removeClass("disabled");

				}

			}
		});

	}

}

function habilitarBotonEnvio() {
	var contador = 0;
	for (var i = 0; i < tasks.length; i++) {
		if (tasks[i].modified)
			contador++;
	}

	if (contador >= 1) {
		$("div.button").removeClass("disabled");
	} else {
		$("div.button").addClass("disabled");
	}
}

function orderBy(inArr, type = undefined, order = "asc") {
	var num = 1;
	arr = inArr.slice()
	
	if(type != undefined && type != ""){
		 console.log("Not Undefined")
		if(order.toLowerCase() == "desc"){
			num = -num;
		}
		
		if(type == "rtc"){
			arr.sort(function(a, b){
				if(a.id > b.id){
					return num;
				}

				if(a.id < b.id){
					return -num;
				}

				return 0;
			});
		}else if(type == "propietario"){
			arr.sort(function(a, b){
				if(a.propiedad > b.propiedad){
					return num;
				}

				if(a.propiedad < b.propiedad){
					return -num;
				}

				return 0;
			});
		}else if(type == "tamano"){
			arr.sort(function(a, b){
				if(a.tamano > b.tamano){
					return num;
				}

				if(a.tamano < b.tamano){
					return -num;
				}

				return 0;
			});
		}
		return arr;
	}else{
		 console.log("Undefined")
		
		arr.sort(function(a, b){
			if(a.id > b.id){
				return 1;
			}

			if(a.id < b.id){
				return -1;
			}

			return 0;
		});

		arr.sort(function(a, b){
			if(a.prioridad < b.prioridad){
				return 1;
			}

			if(a.prioridad > b.prioridad){
				return -1;
			}

			return 0;
		})

		var orderedArr = []
		var prio = ["arr[i].urgente", "arr[i].complejidad"]
		var prioVal = ["Sí", "Sin asignar"]
		var order = 0;
		while (orderedArr.length < arr.length) {//orderedArr.length < tasks.length
			for (var i = 0; i < arr.length; i++) {
				if (eval(prio[order]) == prioVal[order] && !exists(orderedArr, arr[i]) && order < prio.length) {
					orderedArr.push(arr[i])
				} else if (order >= prio.length && !exists(orderedArr, arr[i])){
					orderedArr.push(arr[i])
				}
			}
			if(order < prio.length){
				order++;	
			} 

		}
		return orderedArr;
	}
}

function exists(arr, val){
	for(var x = 0; x < arr.length; x++){
		if(arr[x].id == val.id){
			return true;
		}
	}
	return false;
}

function drawTeamUsers(array){
	k = 0;
	document.getElementsByClassName("teamUsers")[0].innerHTML = "";

	for (var i = 0; i < array.length; i++) {

		var txt = '<div class="chip">'
			+'<img src="https://addons.thunderbird.net/static//img/zamboni/anon_user.png" alt="Person" width="96" height="300"><span class="name">'
			+ toCamelCase(array[i].nombre.toLowerCase()) +'</span> <br>Tareas: ' + getTasksByUser(array[i], tasks) + ' | K: '+ array[i].k.toFixed(2) +' </div>';
		if(array.length >= 7 && i + 1 == Math.round((array.length / 2))){
			txt += "<br>";
		}
		k += array[i].k;
		document.getElementsByClassName("teamUsers")[0].innerHTML += txt;
	}
	moveUsers();

}

function moveUsers(){
	$(".chip").draggable({
		cursorAt: { top: 30, left: 0 },
		helper: function( event ) {
			return $('<img src="https://addons.thunderbird.net/static//img/zamboni/anon_user.png" alt="Person" width="96" height="96" class="imgClone">');
		},
	});
}

function getTasksByUser(user, tareas){
	var sum = 0;
	var count = 0;
	tareas.forEach(function(task){
		if(task.propiedad.toLowerCase() == user.nombre.toLowerCase()){
			count++;
//			user.k += task.k;
			sum += task.k;
		}
		if(user.nombre.toLowerCase() == "sin propietario" && task.propiedad.toLowerCase()  == "unassigned"){
			count++;
		}
	});

	user.k = sum;
	return count;
}

function calculateK(comp, tam, estadoInicial, estadoActual){ 

//	Listo para analizar; Cierre de requirimientos; En análisis; Aceptación usuario; En curso; Aceptación pruebas; Pendiente implantar; Implantado; Cerrado
//	K = COMPLEJIDAD * TAMAÑO * suma(PESO_FASE_COMPLETADA)
//	=SI(I4="Pte Alta";0;SI(I4="Pte. Cuantificar";0;SI(I4="Listo para analizar";0;SI(I4="Cierre requerimientos";0,4;SI(I4="En análisis";0,6;SI(I4="Aceptación usuario";0,72;SI(I4="En curso";0,77;SI(I4="Aceptación a las pruebas";0,97;SI(I4="Pte. implantar";1;SI(I4="Implantado";1;SI(I4="Finalizada";1;-1)))))))))))

	var pesoFase = [0,0,0, 0.4, 0.2, 0.12, 0.05, 0.2, 0.03, 0];
	var tamano =  expand({"XXS, 50": 1, "XS, 100": 1.1, "S, 200":1.2, "M, 400": 1.3, "L, 800": 1.4, "XL, 1600": 1.5, "XXL, 3200": 1.6, "XXXL, 6400": 1.7});
	var complejidad = [1, 5, 20, 50, 100];

	if(parseInt(comp) <= 0 || parseInt(tam) <= 0){
		return 0;
	}

	return complejidad[parseInt(comp)] * tamano[tam] * sum(pesoFase, parseInt(estadoInicial),parseInt(estadoActual));

}

function expand(obj) {
	var keys = Object.keys(obj);
	for (var i = 0; i < keys.length; ++i) {
		var key = keys[i],
		subkeys = key.split(/,\s?/),
		target = obj[key];
		delete obj[key];
		subkeys.forEach(function(key) { obj[key] = target; })
	}
	return obj;
}

function sum(array, posInitial, posFinal){
	var suma = 0;

	for (var i = posInitial; i <= posFinal; i++) {

		suma += array[i];
	}

	return suma;
}