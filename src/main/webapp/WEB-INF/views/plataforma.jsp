<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp"></jsp:include>

<body>
	<h1 class="mainTitle">Estrategia</h1>

	<div class="table-responsive table-bordered">
		<table class="table">
			<thead>
				<tr class="table-info">
					<th scope="col">Pte. Alta</th>
					<th scope="col">Pte.Qualificar</th>
					<th scope="col">Listo Para Analizar</th>
					<th scope="col">Cierre Requerimientos</th>
					<th scope="col">En Analisis</th>
					<th scope="col">Aceptación Usuario</th>
					<th scope="col">En Curso</th>
					<th scope="col">Aceptación a las Pruebas</th>
					<th scope="col">Pendiente Implantar</th>
					<th scope="col">Implantado</th>
				</tr>
			</thead>
			<tbody>
				<tr id="1">
					<td scope="row">
						<div class="rect" data-posInitial="0" data-rtc="1">
							<small class="tamano">sm</small> 123456 <small
								class="complejidad">3</small>
						</div>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td scope="row">
						<div class="rect" data-posInitial="0" data-rtc="2">
							<small class="tamano">md</small> 781567 <small
								class="complejidad">2</small>
						</div>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td scope="row">
						<div class="rect" data-posInitial="0" data-rtc="3">
							<small class="tamano">sm</small> 456987 <small
								class="complejidad">1</small>
						</div>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td scope="row">
						<div class="rect" data-posInitial="0" data-rtc="4">
							<small class="tamano">sm</small> 987654 <small
								class="complejidad">3</small>
						</div>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>


	<script src="/resources/libs/js/popper.min.js"></script>
	<script src="/resources/libs/css/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>