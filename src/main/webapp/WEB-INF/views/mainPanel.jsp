<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="formularioEstrategia.jsp"></jsp:include>
<script src="/resources/js/mainPanel.js"></script>

<body>
	<div class="parent cartas">

		<c:forEach items="${roles}" var="rol" varStatus="item">
			<script>
 	  			roles.push("${rol}");
 	  	
 	  		</script>
		</c:forEach>

		<c:forEach items="${equipos}" var="team" varStatus="item">
			<script>;
			var eq = "${team}".split("=");
			var equipo = new Object();
			equipo.id = eq[0];
			equipo.name = eq[1];
			equipo.rol = roles["${item.index}"];
			$(".cartas ").append('<div id="'+ equipo.name.replace(/\s/g, "-") +'" class="strategyTeams"><span class="teamName">'+ equipo.name +'</span><div class="strategyContainer"></div></div><br>'); 
			equipos.push(equipo);
			</script>


		</c:forEach>
		<c:forEach items="${listaEstrategia}" var="estrategia"
			varStatus="item">
			<script>
				var estrategia = new Object();
				estrategia.id = "${estrategia.id}";
				estrategia.fechaInicio = "${estrategia.fechaInicio}"
				estrategia.endDate = "${estrategia.fechaFin}";
				estrategia.equipoId = "${estrategia.equipoId}";
				estrategia.estado ="${estrategia.estado}";
				estrategia.equipo = equipos[equipos.findIndex(equipo => parseInt(equipo.id) === parseInt(estrategia.equipoId))].name;
				estrategia.fecha ="${estrategia.fechaDaily}";
				estrategias.push(estrategia);
				
				var nombre = "${estrategia.nombre}";

				var fecha = estrategia.fechaInicio.split("-");
				fecha[1] = parseInt(fecha[1]) + 1;
				estrategia.fechaInicio = fecha.reverse().join("/");
				
				fecha =  estrategia.endDate.split("-");
				fecha[1] = parseInt(fecha[1]);
				fecha = fecha.reverse().join("/");
				
				$("#" + estrategia.equipo.replace(/\s/g, "-") + ">.strategyContainer").append(
						'<a id="'+estrategia.id+'" class="a" data-index= "'+ ${item.index} +'" '+/*'data-toggle="tooltip" data-placement="top"'+*/' title="'+ nombre +'">'+
						'<div class="estartegiasCard">'+ nombre.big().bold() +'<br>Inicio: <b>'+estrategia.fechaInicio+'</b><br>Fin: <b>'+fecha+'</b></div>'+
					'<div class="divOptions">'+
						'<form action="/estrategia/findEstrategia" method="post">'+
						'<input  type="hidden" name="id" value="'+estrategia.id+'">'+
							'<input class="options createDaily" type="submit" value="Crear Daily" name="'+estrategia.id+'">'+
						'</form>'+
						'<form action="/daily/findDailys" method="post">'+
						'<input  type="hidden" name="id" value="'+estrategia.id+'">'+
							'<input class="options viewDailys" type="submit" value="Ver Daily" >'+
						'</form>'+
					'</div></a>');
			</script>
		</c:forEach>

		<a data-toggle="modal" data-target="#estrategiaForm">
			<div class="estartegiasCard">Nueva Estrategia</div>
		</a>
	</div>
	<script>
		checkStatus();
		console.log("Equipos", equipos);
 		$(document).ready(function() {
 			$("a:not(.a)").on("click",function(){
 				$(".options").hide();
 			});
		    $(".a").click(function () {
					var el = this;
					var elId = this.getAttribute("data-index");
					if(el.getAttribute("data-dailyDate") == null){
								var date = new Date();
								var day;
								var month;
								
								if((date.getMonth()+1) < 10){
									month = "0" + (date.getMonth()+1);
								} else {
									month = (date.getMonth()+1);
								}
								
								if(date.getDate() < 10){
									day = "0" + date.getDate();
								} else {
									day = date.getDate();
								}
								
								var today = date.getFullYear() + "-" + month + "-" + day;
								if((today == estrategias[elId].fecha && estrategias[elId].fecha.trim() != "") || el.children[0].classList.contains("ended")){
									el.children[0].nextElementSibling.children[0].classList.add("disabled");
									el.children[0].nextElementSibling.children[0].setAttribute("style", "display:none");
									console.log("Can't create new daily")
								} else {
									console.log("You can create new daily")
								}
								el.setAttribute("data-dailyDate", estrategias[elId].fecha)

					} else {
						var date = new Date();
						var today = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate()
						var data = el.getAttribute("data-dailyDate")
						if((today == data && data.trim() != "") || el.children[0].classList.contains("ended")){
									console.log("Cant create new daily")
								} else {
									console.log("You can create new daily")
								}
					}
					
		    	$(".options", this).toggle();
		    });
		    
				$(".options", this).toggle();
				

				$(".options").click(function(e){
					if(!e.target.parentElement.parentElement.previousSibling.classList.contains("disabled")){ 
						sessionStorage.setItem('titulo', e.target.parentElement.parentElement.previousSibling.innerHTML.substring(8, e.target.parentElement.parentElement.previousSibling.innerHTML.indexOf("</big>")).trim());
					}
				});
				

		var acc = document.querySelectorAll(".a");
		var i;

		for (i = 0; i < acc.length; i++) {
		  acc[i].addEventListener("click", function() {
		    this.classList.toggle("actived");

		  });
		} 

 	});
	</script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
