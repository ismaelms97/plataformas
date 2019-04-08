
var tasks = []
var equipo = [];
var idPos, typePos, prioPos, resuPos, statusPos, sizePos, complejPos, ownerPos, petPos, relevancePos, urgenPos, plannedPos;
var X = XLSX;
var XW = {
		/* worker message */
		msg: 'xlsx',
		/* worker scripts */
		worker: '/resources/libs/js/xlsxworker.js'
};
var global_wb;

var process_wb = (function () {
//	var OUT = document.getElementById('out');
//	var HTMLOUT = document.getElementById('htmlout');

	var get_format = (function () {
		var radios = document.getElementsByName("format");
		return function () {
			for (var i = 0; i < radios.length; ++i) if (radios[i].checked || radios.length === 1) return radios[i].value;
		};
	})();

	var to_json = function to_json(workbook) {
		var result = {};
		workbook.SheetNames.forEach(function (sheetName) {
			var roa = X.utils.sheet_to_json(workbook.Sheets[sheetName], { header: 1 });
			if (roa.length) result[sheetName] = roa;
		});
		return JSON.stringify(result, 2, 2);
	};


	return function process_wb(wb) {
		global_wb = wb;
		var output = "";
		switch (get_format()) {
		default: output = to_json(wb);
		}
		/*if (OUT.innerText === undefined) OUT.textContent = output;
        else OUT.innerText = output;*/
		//console.log(to_json(wb)) //my console
		//document.getElementById("drop").style.display =  "none";
		
		try{
			for (var i = 0; i < JSON.parse(output).Tareas[0].length; i++) {
				if(JSON.parse(output).Tareas[0][i] != null){
					if (JSON.parse(output).Tareas[0][i].toLowerCase() == "id") {
						idPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "tipo") {
						typePos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "prioridadcbk") {
						prioPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "resumen") {
						resuPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "estado") {
						statusPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "tamaño") {
						sizePos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "complejidad") {
						complejPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "propiedad de") {
						ownerPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "peticionario") {
						petPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "relevante") {
						relevancePos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "urgente") {
						urgenPos = i;
					} else if (JSON.parse(output).Tareas[0][i].toLowerCase() == "planificado para") {
						plannedPos = i;
					} 
				}
			}
			for (var i = 1; i < JSON.parse(output).Tareas.length; i++) { //JSON.parse(output).Tareas.length
				if(JSON.parse(output).Tareas[i][idPos] != null && JSON.parse(output).Tareas[i][statusPos].toLowerCase() != "finalizada"){

					var task = new Object();
					task.id = JSON.parse(output).Tareas[i][idPos]
					task.tipo = JSON.parse(output).Tareas[i][typePos]
					task.resumen = JSON.parse(output).Tareas[i][resuPos]
					task.estado = JSON.parse(output).Tareas[i][statusPos]
					task.propiedad = JSON.parse(output).Tareas[i][ownerPos]
					task.relevante = JSON.parse(output).Tareas[i][relevancePos]
					task.planificado = JSON.parse(output).Tareas[i][plannedPos]
					task.peticionario = JSON.parse(output).Tareas[i][petPos]
					task.urgente = JSON.parse(output).Tareas[i][urgenPos];
					task.estadoFinal = "";
					
					// Hacemos comprovaciones
					if(JSON.parse(output).Tareas[i][complejPos] != null && JSON.parse(output).Tareas[i][complejPos].toLowerCase() != "sin asignar"){
						task.complejidad = JSON.parse(output).Tareas[i][complejPos].substring(0,1)	
					}else{
						task.complejidad = 0;
					}
					
					if(JSON.parse(output).Tareas[i][prioPos] != null && JSON.parse(output).Tareas[i][prioPos] != "undefined"){
						task.prioridad = JSON.parse(output).Tareas[i][prioPos];
					} else {
						task.prioridad = 0;
					}

					if(JSON.parse(output).Tareas[i][sizePos] != null){
						task.tamano = JSON.parse(output).Tareas[i][sizePos]					
					}else{
						task.tamano = 0;
					}
					
					task.modified = false;
					tasks.push(task);
				}
			}
			document.getElementById("loadAnimation").style.display =  "none"; //Hide load animation
			console.log(tasks)
			if(inTasks.length >= 1){
				tasks = strategyFilter(tasks);
				$("div.button").removeClass("disabled");
			}
			// Collect Users from propertyOf 
			equipo = owners();
			// Activate the action to filter
			filtering();
			document.getElementById("errorContainer").setAttribute("style", "display: none")
			drawTable(tasks, false);
		} catch (e){
			dropError();
		}
		
	};
})();

var do_file = (function () {
	var rABS = typeof FileReader !== "undefined" && (FileReader.prototype || {}).readAsBinaryString;
	var domrabs = document.getElementsByName("userabs")[0];
	if (!rABS) domrabs.disabled = !(domrabs.checked = false);

	var use_worker = typeof Worker !== 'undefined';
	var domwork = document.getElementsByName("useworker")[0];
	if (!use_worker) domwork.disabled = !(domwork.checked = false);

	var xw = function xw(data, cb) {

		document.getElementById("drop").style.display =  "none"; //Hide dropZone div
		document.getElementById("loadAnimation").removeAttribute("style"); //Show load animation
		var worker = new Worker(XW.worker);
		worker.onmessage = function (e) {
			switch (e.data.t) {
			case 'ready': break;
			case 'e': dropError();
			case XW.msg: cb(JSON.parse(e.data.d)); break;
			}
		};
		worker.postMessage({ d: data, b: rABS ? 'binary' : 'array' });
	};

	return function do_file(files) {
		//rABS = domrabs.checked;
		//use_worker = domwork.checked;
		var f = files[0];
		var reader = new FileReader();
		reader.onload = function (e) {
//			if (typeof console !== 'undefined') console.log("onload", new Date(), rABS, use_worker);
			var data = e.target.result;
			if (!rABS) data = new Uint8Array(data);
			if (use_worker) xw(data, process_wb);
			else process_wb(X.read(data, { type: rABS ? 'binary' : 'array' }));
		};
		if (rABS) reader.readAsBinaryString(f);
		else reader.readAsArrayBuffer(f);
	};
})();

(function () {
	var drop = document.getElementById('drop');
	if (!drop.addEventListener) return;

	function handleDrop(e) {
		e.stopPropagation();
		e.preventDefault();
		do_file(e.dataTransfer.files);
	}

	function handleDragover(e) {
		e.stopPropagation();
		e.preventDefault();
		e.dataTransfer.dropEffect = 'copy';
	}

	drop.addEventListener('dragenter', handleDragover, false);
	drop.addEventListener('dragover', handleDragover, false);
	drop.addEventListener('drop', handleDrop, false);
})();

(function () {
	var xlf = document.getElementById('xlf');
	if (!xlf.addEventListener) return;
	function handleFile(e) { do_file(e.target.files); }
	xlf.addEventListener('change', handleFile, false);
})();
var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-36810333-1']);
_gaq.push(['_trackPageview']);

(function () {
	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();


document.getElementById("drop").addEventListener('click', () => {
	document.getElementById("xlf").click();
});

document.getElementById("xlf").addEventListener('change', () => {
	const name = document.getElementById("xlf").value.split(/\\|\//).pop();
	const truncated = name.length > 20
	? name.substr(name.length - 20)
			: name;

	//console.log(truncated)
});



function dropError(){

	console.log("elol")
	document.getElementById("errorContainer").removeAttribute("style");
	document.getElementById("drop").removeAttribute("style")
	document.getElementById("loadAnimation").setAttribute("style", "display: none")
}