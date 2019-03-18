$(document).ready(function(){
	var rect = document.getElementsByClassName("rect");
	for(var i = 0; i < rect.length; i++){
		var el = rect[i];
		var cln = $(el).clone();
		cln.attr("class", "clone");
		$(el).parent().append(cln);
		console.log(cln);
		$(cln).addClass("display");
	}
});

//Con este codigo conseguimos que se mueva cada tarea unicamente en su eje x, y a su vez que cuando los dejes en el sitio, cambien de color
$( function() {
	$(".rect").draggable(
			{
				axis: "x" , 
				containment: ".table-responsive",
				scroll: false,
				opacity: 0.7,
				helper: "clone",
				start: function(event, ui){
					$(".ui-draggable-dragging").removeClass("noLeft");
				},
				stop: function(event, ui){
//					$(".clone")[($(this).attr("data-rtc") - 1)].removeClass("display");
					document.getElementsByClassName("clone")[(this.getAttribute("data-rtc") - 1)].classList.remove("display");
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

			// Corregir, el rtc se mueve porque tiene un valor en el left que a la hora de hacer un append, le suma ese valor y parece que lo adjudique mal
			$(ui.draggable[0]).addClass("noLeft").appendTo(event.target);
		}
	});

})