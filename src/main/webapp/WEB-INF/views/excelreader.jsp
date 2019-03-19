<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!-- xlsx.js (C) 2013-present  SheetJS http://sheetjs.com -->
<!-- vim: set ts=2: -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>SheetJS Live Demo</title>
<style>
#drop{
	border:2px dashed #bbb;
	-moz-border-radius:5px;
	-webkit-border-radius:5px;
	border-radius:5px;
	padding:25px;
	text-align:center;
	font:20pt bold,"Vollkorn";color:#bbb;
	cursor: pointer;
	/*background: lightblue;*/
	width: 600px;
}
</style>
</head>
<body>
<!--Output Format: <select name="format" onchange="setfmt()">
		<option value="json" selected> JSON</option>
	</select><br />>-->
	<div id="drop">Drop a spreadsheet file here to see sheet data <input type="file" name="xlfile" id="xlf" hidden />
	</div>
	<!--<input type="file" name="xlfile" id="xlf" /> ... or click here to select a file

	<b>Advanced Demo Options:</b>
	Use Web Workers: (when available) <input type="checkbox" name="useworker">
	Use readAsBinaryString: (when available) <input type="checkbox" name="userabs">-->
</pre>
<pre id="out"></pre>
<!--<div id="htmlout"></div>-->
<br />
<script src="/resources/libs/js/shim.js"></script>
<script src="/resources/libs/js/xlsx.full.min.js"></script>
<script>
		var tasks = []
		var idPos, typePos, prioPos, resuPos, statusPos;
		var X = XLSX;
		var XW = {
			/* worker message */
			msg: 'xlsx',
			/* worker scripts */
			worker: '/resources/libs/js/xlsxworker.js'
		};

		var global_wb;

		var process_wb = (function () {
			var OUT = document.getElementById('out');
			var HTMLOUT = document.getElementById('htmlout');

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
				if (OUT.innerText === undefined) OUT.textContent = output;
				else OUT.innerText = output;
				console.log(to_json(wb)) //my console
				for (var i = 0; i < JSON.parse(output).Tareas[0].length; i++) {
					if (JSON.parse(output).Tareas[0][i] == "ID") {
						idPos = i;
					} else if (JSON.parse(output).Tareas[0][i] == "Tipo") {
						typePos = i;
					} else if (JSON.parse(output).Tareas[0][i] == "Piroridad") {
						prioPos = i;
					} else if (JSON.parse(output).Tareas[0][i] == "Resumen") {
						resuPos = i;
					} else if (JSON.parse(output).Tareas[0][i] == "Estado") {
						statusPos = i;
					}
				}
				for (var i = 1; i < 5; i++) { //JSON.parse(output).Tareas.length
					var task = new Object();
					task.id = JSON.parse(output).Tareas[i][idPos]
					task.tipo = JSON.parse(output).Tareas[i][typePos]
					task.prioridad = JSON.parse(output).Tareas[i][prioPos]
					task.resumen = JSON.parse(output).Tareas[i][resuPos]
					task.estado = JSON.parse(output).Tareas[i][statusPos]
					tasks.push(task);
				}
				console.log(tasks)
				if (typeof console !== 'undefined') console.log("output", new Date());
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
				var worker = new Worker(XW.worker);
				worker.onmessage = function (e) {
					switch (e.data.t) {
						case 'ready': break;
						case 'e': console.error(e.data.d); break;
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
					if (typeof console !== 'undefined') console.log("onload", new Date(), rABS, use_worker);
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

			console.log(truncated)
		});
	</script>
</body>
</html>
