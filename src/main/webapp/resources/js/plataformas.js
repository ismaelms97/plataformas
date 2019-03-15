//Con este codigo conseguimos que se mueva cada tarea unicamente en su eje x, y a su vez que cuando los dejes en el sitio, cambien de color
$( function() {

	$(".rect").draggable(
			{

				axis: "x" , 
				containment: ".table-responsive",
				scroll: false,
				opacity: 0.7,
				helper: "clone",
				stop: function(event, ui){
					if($(this).attr("data-clone") == "false"){

						$(this).clone().attr({"class":"rect clone"}).appendTo($("tr")[$(this).attr("data-rtc")].children[$(this).attr("data-posInitial")]);
						$(this).attr({"data-clone" : "true"});
					
					}
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
		}
	});

})