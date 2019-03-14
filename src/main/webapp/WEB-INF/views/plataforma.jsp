<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>
<head>
<link rel="shortcut icon" href="">
</head>
<body>
	<h1 class="mainTitle">TITLE</h1>

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
			    <tr>
			    	<td scope="row"><div class="rect"><small class="tamano">sm</small> 123456 <small class="complejidad">3</small></div></td>
			    	<td class="PteQual"></td>
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
			    	<td><div class="rect"></div></td>
			    	<td colspan="9"></td>
			    </tr>
			    <tr>
			    	<td><div class="rect"></div></td>
			    	<td colspan="9"></td>
			    </tr>
			    <tr>
			    	<td><div class="rect"></div></td>
			    	<td colspan="9"></td>
			    </tr>
		    </tbody>
		</table>
	</div>


	<script src="/resources/libs/js/popper.min.js"></script>
	<script src="/resources/libs/css/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>