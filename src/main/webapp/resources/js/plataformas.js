//Con este codigo conseguimos que se mueva cada tarea unicamente en su eje x, y a su vez que cuando los dejes en el sitio, cambien de color
$( function() {

	$(".rect").draggable(
			{
				axis: "x" , 
				containment: ".table-responsive",
				scroll: false,
				opacity: 0.7,
				helper: "clone",
				appendTo: "td"
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
			
//			console.log($("tr")[$( ui.draggable)[0].attributes[2].value].children);
//			console.log($("tr")[$( ui.draggable)[0].attributes[2].value].children[$( ui.draggable)[0].attributes[1].value].length);
//			$(".rect").clone().attr({"class":"first"}).appendTo($("tr")[$( ui.draggable)[0].attributes[2].value].children[$( ui.draggable)[0].attributes[1].value]);
		}
	});

})