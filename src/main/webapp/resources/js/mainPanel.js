var estrategias = [];
var equipos = [];
var roles = [];


function checkStatus(){
	var estrategiaCards = document.getElementsByTagName("a");
	estrategias.forEach(estrategia => {
			var today = new Date().setHours(0, 0, 0, 0);
		    var fecha = new Date(estrategia.endDate).getTime();
		    if (today <= fecha && estrategia.estado.trim() != "Finalizada") {
		        for(var i = 0; i < estrategiaCards.length; i++){
//						console.log(estrategiaCards[i].getAttribute("id") + " " + estrategia.id)
		        		if(estrategiaCards[i].getAttribute("id") == estrategia.id){
			                estrategiaCards[i].children[0].classList.add("started");
			               
			            }  
		        }
		    } else {
		        for(var i = 0; i < estrategiaCards.length; i++){
		        		if(estrategiaCards[i].getAttribute("id") == estrategia.id){
			                estrategiaCards[i].children[0].classList.add("ended");
			                
		        	} 
		        }
	        }
	});
//	console.log(estrategiaCards[estrategiaCards.length -1])
	estrategiaCards[estrategiaCards.length -1].children[0].classList.add("newStrategy");
}