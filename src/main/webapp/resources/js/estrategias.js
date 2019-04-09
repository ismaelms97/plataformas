var arrayTasksBackup = [];

function dragDrop(arr){

	// Con este codigo conseguimos que se mueva cada tarea unicamente en su eje x, y
	// a su vez que cuando los dejes en el sitio, cambien de color
	$(function () {
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

						if (this.parentElement.classList.contains(estados[this.getAttribute("data-posInitial")].replace(/\s/g, "-")) && arr[this.getAttribute("data-rtc") - 1].modified) {

							arr[this.getAttribute("data-rtc") - 1].modified = false;

						} else {

							if (estados.indexOf(this.parentElement.classList[0].replace(/-/g, " ")) >= this.getAttribute("data-posInitial")) {
								arr[this.getAttribute("data-rtc") - 1].modified = true;
								arr[this.getAttribute("data-rtc") - 1].estadoFinal = this.parentElement.classList[0].replace(/-/g, " ");
							}
						}

						habilitarBotonEnvio();
						console.log(arr[this.getAttribute("data-rtc") - 1]);
						console.log(arr);
					},
				});

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
							if (estados.indexOf(event.target.classList[0].replace(/-/g, " ")) >= ui.draggable[0].getAttribute("data-posInitial")) {

								if (document.getElementsByClassName("clone")[(ui.draggable[0].getAttribute("data-rtc") - 1)].style.display == "none"
									&& !(event.target.children.length >= 1)) {

									document.getElementsByClassName("clone")[(ui.draggable[0].getAttribute("data-rtc") - 1)].style.display = "";

								}

								$(ui.draggable[0]).appendTo(event.target);
							}
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
			console.log(task.id);
			tasksToString += "Tipo:" + task.tipo + "--";
			tasksToString += "Estado:" + task.estado + "--";
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
			console.log(tasksToString);
			if(task.urgente == "Sí"){
				tasksToString += "urgente:true--";
			} else {
				tasksToString += "urgente:false--";
			}

			tasksToString += "planificado:" + task.planificado + "qwer" ;
		});

		tasksToString = tasksToString.substring(0, tasksToString.length - 4);
//		console.log(tasksToString)

		console.log(strategy.tasks)
		$.ajax({
			type: "POST",
			url: "/estrategia/saveEstrategia",
			data: {
				stratTasks: tasksToString
			}, success: function (data) {
				console.log("success");
				location.href = "/estrategia/panelControl";
			}
		});
	} else {
		console.log(inTasks)
		var date = new Date();
		tasksToString = "";

		tasks.forEach(task => {
			tasksToString += "id:" + task.id + "--";
			tasksToString += "estadoActual:" + task.estado + "--"; 
			tasksToString += "subEstadoActual:" + " "+ "--";
			tasksToString += "propiedad:" + task.propiedad+ "qwer";
		});

		$.ajax({
			type: "POST",
			url: "/daily/saveDaily",
			data: {
				date: "" +date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate(),
				stratDaily: tasksToString
			}, success: function (data) {
				console.log("success");
				location.href = "/estrategia/panelControl";
			}
		});
		console.log("not empty")

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

function orderByPrio(arr) {
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
			//console.log(prio[order])
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
//	console.log(orderedArr)
	return orderedArr;
}

function exists(arr, val){
	for(var x = 0; x < arr.length; x++){
		if(arr[x].id == val.id){
//			console.log(val.id)
			return true;
		}
	}
	return false;
}

function drawTeamUsers(array){
	for (var i = 0; i < array.length; i++) {
		var txt = '<div class="chip">'
			+'<img src="https://addons.thunderbird.net/static//img/zamboni/anon_user.png" alt="Person" width="96" height="300"><span class="name">'
			+ toCamelCase(array[i].toLowerCase()) +'</span> <br>Tareas: 2 | K: 10 </div>';

		document.getElementsByClassName("teamUsers")[0].innerHTML += txt;
	}
	moveUsers();
}

function moveUsers(){
	$(".chip").draggable({
		cursor: pointer,
		cursorAt: { top: 30, left: 0 },
		revert: true,
		helper: function( event ) {
			return $('<img src="https://addons.thunderbird.net/static//img/zamboni/anon_user.png" alt="Person" width="96" height="96" class="imgClone">');
		}
	});
}