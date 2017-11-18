<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bank XYZ</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
  <jsp:include page="menu.jsp" />
    <!-- /login?error=true -->
     <c:if test="${param.error == 'true'}">
         <div style="color:red;margin:10px 0px;">
          
                Login Failed!!!<br />
                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                 
         </div>
    </c:if>
	<div class="container">
		<div class="wrapper">
			<form name="loginForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="POST" class="form-signin">
				<h3 class="form-signin-heading">Welcome To Bank XYZ</h3>
				<hr class="colorgraph">
				<br> <input type="text" class="form-control" name="username"
					placeholder="Username" required="" autofocus="" /> <input
					type="password" class="form-control" name="password"
					placeholder="Password" required="" />

				<button name="Submit"
					value="submit" type="submit">Login</button>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<a class="blue" href="customer/add">register</a>
			</form>
		</div>
	</div>
</body>
</html>