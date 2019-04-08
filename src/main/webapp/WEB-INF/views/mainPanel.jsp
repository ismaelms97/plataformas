<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="formularioEstrategia.jsp"></jsp:include>
<script src="/resources/js/mainPanel.js"></script>

<body>
	<div class="parent cartas">

		<c:forEach items="${listaEstrategia}" var="estrategia"
			varStatus="item">
			<script>
				var estrategia = new Object();
				estrategia.id = "${estrategia.id}";
				estrategia.endDate = "${estrategia.fechaFin}";
				console.log("ID " + estrategia.endDate)
				estrategias.push(estrategia);
				//href="/estrategia/findEstrategia/${estrategia.id}"
			</script>

			<a id="${estrategia.id}" class="a">
				<div class="estartegiasCard">
					<c:out value="${estrategia.nombre}" />
				</div>
				<div class="divOptions">
					<span class="options createDaily">Crear Daily</span> 
					<span class="options">Ver Daily</span>
				</div>
			</a>
		</c:forEach>

		<a data-toggle="modal" data-target="#estrategiaForm">
			<div class="estartegiasCard">Nueva Estrategia</div>
		</a>
	</div>

	<script>
		checkStatus();
		 
 		$(document).ready(function() {
		    $(".a").click(function () {
					var el = this;
					console.log(el.getAttribute("data-dailyDate"))
					if(el.getAttribute("data-dailyDate") == null){
						 $.ajax({
							type: "POST",
							url: "/daily/date",
							data: {
								id: this.getAttribute("id")
							}, success: function (data) {
								var date = new Date();
								var today = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate()
								console.log(date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate() + " data: " + data)
								console.log(today == data)
								console.log(el.classList)
								if((today == data && data.trim() != "") || el.children[0].classList.contains("ended")){
									console.log(el.children[0].nextElementSibling);
									el.children[0].nextElementSibling.children[0].classList.add("disabled");
									console.log("Cant create new daily a")
								} else {
									console.log("You can create new daily a")
								}
								el.setAttribute("data-dailyDate", data)

							}
						}); 
					} else {
						var date = new Date();
						var today = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate()
						var data = el.getAttribute("data-dailyDate")
						if((today == data && data.trim() != "") || el.children[0].classList.contains("ended")){
									console.log("Cant create new daily b")
								} else {
									console.log("You can create new daily b")
								}
					}
					
		    	$(".options", this).toggle();
		    });
		    
				$(".options", this).toggle();
				
				$(".options").click(function(e){
					if(!e.target.classList.contains("disabled") && e.target.classList.contains("createDaily")){ 
						window.location.href = "/estrategia/findEstrategia/" + e.target.parentElement.parentElement.getAttribute("id");
					}
				});
	
		var acc = document.querySelectorAll("a .a");
		var i;

		for (i = 0; i < acc.length; i++) {
		  acc[i].addEventListener("click", function() {
		    this.classList.toggle("actived");
			console.log("Activo")
			console.log(this);

		    var panel = this.nextElementSibling;
		    if (panel.style.maxHeight){
		      panel.style.maxHeight = null;
		    } else {
		      panel.style.maxHeight = panel.scrollHeight + "px";
		    } 
		  });
		} 
		
		});


	</script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
