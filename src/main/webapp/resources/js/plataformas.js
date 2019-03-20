$(document).ready(function(){
	drawRTC();
	var rect = document.getElementsByClassName("rect");
	for(var i = 0; i < rect.length; i++){
		var el = rect[i];
		var cln = $(el).clone();
		cln.attr("class", "clone");
		$(el).parent().append(cln);
		$(cln).css("display", "none");
	}
});

//Con este codigo conseguimos que se mueva cada tarea unicamente en su eje x, y a su vez que cuando los dejes en el sitio, cambien de color
$( function() {
	var selected = $([]), offset = {top:0, left:0}; 
	$(".rect").draggable(
			{
				axis: "x" , 
				containment: ".table-responsive",
				scroll: false,
				opacity: 0.7,
				helper: "clone",
				start: function(event, ui){
					$(".ui-draggable-dragging").removeClass("noLeft");

//					Descomentar esto para seleccionar
//					if ($(this).hasClass("ui-selected")){
//					selected = $(".ui-selected").each(function() {
//					var el = $(this);
//					el.data("offset", el.offset());
//					});
//					}
//					else {
//					selected = $([]);
//					$(".rect").removeClass("ui-selected");
//					}
//					offset = $(this).offset();


				},

//				Descomentar esto para seleccionar
//				drag: function(ev, ui) {
//				var dt = ui.position.top - offset.top, dl = ui.position.left - offset.left;
//				// Coje todos los elementos seleccionados excepto $(this), que es el elemento que queremos mover
//				selected.not(this).each(function() {

//				// Crea la variable para que no necesitemos llamar a $(this)
//				// el = Elemento actual
//				// off = En que posicion esteaba este elemento antes de moverlo
//				var el = $(this), off = el.data("offset");
//				el.css({top: off.top + dt, left: off.left + dl});
//				});
//				},

				stop: function(event, ui){
					document.getElementsByClassName("rect")[(this.getAttribute("data-rtc") - 1)].style.display = "";

				},
			}
	);

	$( "td" ).droppable({

		drop: function( event, ui ) {
			// Pinta de rojo todos los modificados
			$( ui.draggable[0] ).addClass( "orange" );
			var $this = $(this);
			ui.draggable.position({
				my: "center",
				at: "center",
				of: $this,
				using: function(pos) {
					$(this).animate(pos, 200, "linear");

				}
			});

			if(event.target.children.length == 1){
				console.log(event.target.children.length);
				event.target.children[0].style.display = "none";
			}
			if(document.getElementsByClassName("clone")[(ui.draggable[0].getAttribute("data-rtc") - 1)].style.display == "none" && 
					!(event.target.children.length >= 1)){

				document.getElementsByClassName("clone")[(ui.draggable[0].getAttribute("data-rtc") - 1)].style.display = "";
			}


			$(ui.draggable[0]).addClass("noLeft").appendTo(event.target);



		}
	});


//	SI se quiere seleccionar, solo hace falta desbloquear el siguiente codigo, aunque si es así, mirar la seleccion multiple

//	$(".rect").selectable()

	//Manualmente activa el select que los elementos clicados
//	$( ".rect" ).click( function(e){
//	if (e.ctrlKey == false) {
//	// Si la tecla de comando se presiona no deselecciones los elementos
//	$( ".rect" ).removeClass("ui-selected");
//	$(this).addClass("ui-selecting");
//	}
//	else {
//	if ($(this).hasClass("ui-selected")) {
//	// Elimina la clase selected de lo elementos seleccionados
//	$(this).removeClass("ui-selected");
//	}
//	else {
//	// Sino, añadeles la clase
//	$(this).addClass("ui-selecting");
//	}
//	}

//	$( ".rect" ).data("selectable")._mouseStop(null);

//	});

})

function drawRTC(){
	var el = $("tbody tr:first-child");
	console.log(el);
	for (var i = 0; i < el; i++) {
		
		console.log(el[i]);
		
	}
}
