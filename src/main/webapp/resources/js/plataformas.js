//Con este codigo conseguimos que se mueva cada tarea unicamente en su eje x, y a su vez que cuando los dejes en el sitio, cambien de color
$( function() {
	$(".rect").draggable({ axis: "x" });
	$( "td" ).droppable({
		drop: function( event, ui ) {
			// Pinta de rojo todos los modificados
			$( event.originalEvent.target ).addClass( "red" );
		}
	});
//	$( "td" ).selectable();
})