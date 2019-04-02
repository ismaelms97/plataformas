<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>

<jsp:include page="header.jsp"></jsp:include>

<body>
	<div class="container login-container">

		<div id="formContent" class="row justify-content-center">
			<div class="text-center col-md-6 col-lg-8 col-xl-8 login-form-1 ">
				<h3>Log In</h3>

				<form:form method="POST" action="/mainPanel" modelAttribute="user"
					class="form-group">
					<div class="form-group">
						<form:input type="text" cssClass="form-control"
							placeholder="Introduce Usuario *" path="username" />
					</div>
					<div class="form-group">
						<form:input type="password" cssClass="form-control"
							placeholder="Introduce Contraseña" path="password" id="passwd" />
						<i class="fa fa-fw fa-eye" id="togglePasswd"></i>
					</div>
					<button type="submit" id="loginBtn"
						class="btn-primary form-control">Login</button>
				</form:form>

				<div>${errorMsg}</div>
			</div>
		</div>
	</div>

	<div class="parent">
		<div id="loadAnimation" class="lds-dual-ring loginAnimation"
			style="display: none"></div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
	<script>
		$("#togglePasswd").click(function() {
			$(this).toggleClass("fa-eye fa-eye-slash");
			togglePassword();
		})

		function togglePassword() {
			var x = document.getElementById("passwd");
			if (x.type === "password") {
				x.type = "text";
			} else {
				x.type = "password";
			}
		}

		document
				.getElementById('loginBtn')
				.addEventListener(
						'click',
						function() {
							document.getElementById("formContent").style.display = "none";
							document.getElementById("loadAnimation").style.display = "inline";
						});
	</script>
</body>
</html>