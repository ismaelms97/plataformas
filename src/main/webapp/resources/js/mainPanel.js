var estrategias = [];

function checkStatus(){
	var estrategiaCards = document.getElementsByTagName("a");
	estrategias.forEach(estrategia => {
			var today = new Date().setHours(0, 0, 0, 0);
		    var fecha = new Date(estrategia.endDate).getTime();
		    if (today <= fecha) {
		        for(var i = 0; i < estrategiaCards.length; i++){
					/*if(estrategiaCards[i].getAttribute("href") != null){*/
						console.log(estrategiaCards[i].getAttribute("id") + " " + estrategia.id)
		        		if(estrategiaCards[i].getAttribute("id") == estrategia.id){
			                estrategiaCards[i].children[0].classList.add("started");
			                console.log(estrategiaCards[i]);
			            }
		        	//}  
		        }
		    } else {
		        for(var i = 0; i < estrategiaCards.length; i++){
		        	/*if(estrategiaCards[i].getAttribute("href") != null){*/
		        		if(estrategiaCards[i].getAttribute("id") == estrategia.id){
			                estrategiaCards[i].children[0].classList.add("ended");
			                console.log(estrategiaCards[i]);
			           // }/
		        	} 
		        }
	        }
	});
	console.log(estrategiaCards[estrategiaCards.length -1])
	estrategiaCards[estrategiaCards.length -1].children[0].classList.add("newStrategy");
}