var arrayTasksBackup;
var arrayInTasksBackup;
var k = 0;
try{
	arrayTasksBackup = tasks.slice(0);
	arrayInTasksBackup = inTasks.slice(0);
}catch(e){}

/**
 * Función Para habilitar el drag & Drop en toda la página
 * 
 * @param arr   Array que modificaremos y usaremopara mover
 * @param bool  Falso unicamente cuando vas a ver daily
 * 
 */
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
						},
						drag: function (event, ui) {

						},
						stop: function (event, ui) {
							document.getElementsByClassName("rect")[(this.getAttribute("data-rtc") - 1)].style.display = "";

							if (this.parentElement.classList.contains(estados[this.getAttribute("data-posInitial")].replace(/\s/g, "-").replace(/[\.]/g, "_").replace(/[(]/g, "0").replace(/[)]/g, "9")) && arr[this.getAttribute("data-rtc") - 1].modified) {

								arr[this.getAttribute("data-rtc") - 1].modified = false;
								arr[this.getAttribute("data-rtc") - 1].k = 0;

							} else {

								if (estados.indexOf(this.parentElement.classList[0].replace(/-/g, " ").replace(/_/g, ".").replace(/[0]/g, "(").replace(/[9]/g, ")")) >= this.getAttribute("data-posInitial")) {
									arr[this.getAttribute("data-rtc") - 1].modified = true;
									arr[this.getAttribute("data-rtc") - 1].estadoFinal = this.parentElement.classList[0].replace(/-/g, " ").replace(/_/g, ".").replace(/[0]/g, "(").replace(/[9]/g, ")");
									arr[this.getAttribute("data-rtc") - 1].k = calculateK(this.childNodes[2].innerHTML - 1, this.childNodes[0].innerHTML, this.getAttribute("data-posInitial"), estados.indexOf(arr[this.getAttribute("data-rtc") - 1].estadoFinal))
								}
							}

							habilitarBotonEnvio();
							drawTeamUsers(equipo, true);
							
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
							if (estados.indexOf(event.target.classList[0].replace(/-/g, " ").replace(/_/g, ".").replace(/[0]/g, "(").replace(/[9]/g, ")")) >= ui.draggable[0].getAttribute("data-posInitial")) {

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

								tasks.find(tarea => parseInt(tarea.id) === parseInt(event.target.children[0].innerText.split(/[\s\n]+/)[1].trim())).propiedad = user;
								if(inTasks.length > 0){
									inTasks.find(tarea => parseInt(tarea.id) === parseInt(event.target.children[0].innerText.split(/[\s\n]+/)[1].trim())).propiedad = user;	
								}
								
								$.notify({
									title: '<strong>Cambio de Propietario</strong>',
									message: ' en la tarea ' + event.target.children[0].innerText.split(/[\s\n]+/)[1].trim() + '.'
								},{
									type: 'success',
									newest_on_top: true,
									placement: {
										from: "bottom",
										align: "right"
									},
									delay: 1000
								});
								
								drawTeamUsers(equipo, true);
//								document.getElementsByClassName("k")[0].innerHTML = "K: " + k.toFixed(2);
							}
						}
					}
				});
	})
	document.getElementsByClassName("k")[0].innerHTML = "K: " + k.toFixed(2);
}

/**
 * Función, guarda los datos en la base de datos, temporalmente también exporta la información a excel
 * 
 */
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

/**
 * Como indica su nombre habilita el boton de envio, está vinculado con la anterior función
 * 
 */
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

/**
 *  Función que ordenara un array dependiendo de sus parametros
 *  
 * @param inArr		Array a ordenar
 * @param type		El tipo de orden que queremos por defecto es 'undefined'
 * @param order		Si queremos que se ordene de manera ascente o descendente, por defecto es ascendete
 * @returns
 */
function orderBy(inArr, type = undefined, order = "asc") {
	var num = 1;
	arr = inArr.slice()

	if(type != undefined && type != ""){
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

/**
 * Función comprueba si un valor existe en una array
 * 
 * @param arr  Array en la que buscar
 * @param val  Valor que buscamos
 * 
 */
function exists(arr, val){
	for(var x = 0; x < arr.length; x++){
		if(arr[x].id == val.id){
			return true;
		}
	}
	return false;
}

/**
 * Pinta por pantalla la lista con todos los usuarios
 * 
 * @param array  Array que pintaremos, array equipo
 * @param bool 	 Falso unicamente cuando vas a ver daily
 * 
 */
function drawTeamUsers(array, bool){

	var tArr = "";
	
	if(inDB && tasks.length < inTasks.length){

		tArr = "inTasks";
	} else {
		tArr = "tasks";
	}
	
	console.log("tarr", tArr);
	
	document.getElementsByClassName("teamUsers")[0].innerHTML = "";

	for (var i = 0; i < array.length; i++) {

		array[i].tareas = getTasksByUser(array[i], eval(tArr));
		
		var txt = '<div class="chip">'
			+'<img src="https://addons.thunderbird.net/static//img/zamboni/anon_user.png" alt="Person" width="96" height="300"><span class="name">'
			+ toPascalCase(array[i].nombre.toLowerCase()) +'</span> <br>Tareas: ' + array[i].tareas + ' | K: '+ array[i].k.toFixed(2) +' </div>';
		
		if(array.length > 8 && i + 1 == 8){
			txt += "<br>";
		}
		k += array[i].k;
		document.getElementsByClassName("teamUsers")[0].innerHTML += txt;
	}
	detallesUsuarios(array);
	moveUsers(bool);
	
}

/**
 * Función que habilita el movimiento de los usuarios
 * 
 * @param bool  Falso unicamente cuando vas a ver daily
 * 
 */
function moveUsers(bool){
	if(bool){
		$(".chip").draggable({
			cursorAt: { top: 30, left: 0 },
			helper: function( event ) {
				return $('<img src="https://addons.thunderbird.net/static//img/zamboni/anon_user.png" alt="Person" width="96" height="96" class="imgClone">');
			},
			containment: "body",
		});
	}
}

/**
 * Función para rellenar los detalles de los usuarios
 * 
 * @param array Array de Usuarios
 * @returns
 */
function detallesUsuarios(array){
	var d = document.getElementsByClassName('chip');
	
	for (var i = 0; i < d.length; i++) {
		d[i].children[0].ondragstart = function() { return false; };
		
		d[i].addEventListener("click", function(){
			$("#detallesUsuarios").modal("show");
			var usuario = array.find(user => user.nombre.toLowerCase() == this.children[1].innerHTML.toLowerCase());
			document.getElementById("detallesNombre").innerHTML = usuario.nombre;
			document.getElementById("detallesTareas").innerHTML = usuario.tareas;
			document.getElementById("detallesKTotalPorUsuario").innerHTML = usuario.k;
			document.getElementById("detallesTareasActivo").innerHTML = usuario.tareasActivo.join(", ");
			
		});
		
	}
}

/**
 * Función que devuelve el numero de tareas en las que trabaja un usuario, rellena la array de tareas en las que esta activo el usuario 
 * y calcula la k total del usuario
 * 
 * @param user		Usuario del cual quieres conocer las tareas
 * @param arrayTareas 	La array de tareas
 * 
 */
function getTasksByUser(user, arrayTareas){
	var sum = 0;
	var count = 0;
	user.tareasActivo = [];
	arrayTareas.forEach(function(task){
		if(task.propiedad.toLowerCase() == user.nombre.toLowerCase()){
			user.tareasActivo.push(task.id);
			count++;
			sum += task.k;
		}
		if(user.nombre.toLowerCase() == "sin propietario" && task.propiedad.toLowerCase()  == "unassigned"){
			count++;
		}
	});

	user.k = sum;
	return count;
}

/**
 * Función que calcula la k de cada tarea
 * 
 * @param comp 			 	Valor de complejidad
 * @param tam 				Valor de tamaño
 * @param estadoInicial  	Estado desde el que empieza la tarea
 * @param estadoFinal 	 	Estado donde acaba la tarea
 * 
 */
function calculateK(comp, tam, estadoInicial, estadoActual){ 

	//	Listo para analizar; Cierre de requirimientos; En análisis; Aceptación usuario; En curso; Aceptación pruebas; Pendiente implantar; Implantado; Cerrado
	//	K = COMPLEJIDAD * TAMAÑO * suma(PESO_FASE_COMPLETADA)

	//	Este: 
	//	"Pte Alta" = 0; "Pte. Cuantificar" = 0; "Listo para analizar" = 0; "Cierre requerimientos" = 0,4; "En análisis"= 0,6; "Aceptación usuario"= 0,72; "En curso" = 0,77; "Aceptación a las pruebas" = 0,97; "Pte. implantar" = 1; "Implantado" = 1; "Finalizada" = 1 : -1

	//	O este: 
	//	"Pte Alta" = 0;
	//	"Pte. Cuantificar" = 0; 
	//	"Listo para analizar" = 0;
	//	"Cierre requerimientos" = 0,4; 
	//	"En análisis"= 0,2; 
	//	"Aceptación usuario"= 0,12;
	//	"En curso" = 0,05; 
	//	"Aceptación a las pruebas" = 0.2;
	//	"Pte. implantar" = 0.03; 
	//	"Implantado" = 0; 
	//	"Finalizada" = 0 	
	//	: -1

	var pesoFase = [0,0,0, 0.4, 0.2, 0.12, 0.05, 0.2, 0.03, 0];
	var tamano =  expand({"XXS, 50": 1, "XS, 100": 1.1, "S, 200":1.2, "M, 400": 1.3, "L, 800": 1.4, "XL, 1600": 1.5, "XXL, 3200": 1.6, "XXXL, 6400": 1.7});
	var complejidad = [1, 5, 20, 50, 100];

	if(parseInt(comp) < 0 || parseInt(tam) < 0){
		return 0;
	}

	return complejidad[parseInt(comp)] * tamano[tam] * sum(pesoFase, parseInt(estadoInicial),parseInt(estadoActual));

}

/**
 * Función útil, Convierte un objeto con una key en formato String separado por comas en un objeto con multiples keys pero con el mismo valor
 * 
 * @param obj  Objeto a pasar
 * 
 * expand({"Nombre, Name" : "Jose"}) = {"Nombre": "Jose", "Name": "Jose"}
 * 
 */
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

/**
 * Función, suma los valores de una array desde un punto X hasta un punto Y
 * 
 * @param array 	Array a sumar
 * @param poInitial La posicion desde la que empezar
 * @param posFinal 	La posicion en la que acaba
 * 
 */
function sum(array, posInitial, posFinal){
	var suma = 0;

	for (var i = posInitial; i <= posFinal; i++) {

		suma += array[i];
	}

	return suma;
}