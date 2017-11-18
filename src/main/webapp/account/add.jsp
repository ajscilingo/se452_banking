<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Account</title>
<link rel="stylesheet" type="text/css" href="../styles.css">
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="container">
		<div class="wrapper">
			<form:form method="POST" class="form-signin" action="add"
				id="accountForm" commandName="account">
				<h3 class="form-signin-heading">Open New Account</h3>
				<hr class="colorgraph">
				<c:if test="${not empty customer.id}">
					<br>
					<input type="hidden" name="customerId" value="${customer.id}" />
					<input type="hidden" name="accountId" value="${account.id}" />
					<input type="text" class="form-control" name="balance"
						placeholder="Starting Balance"  required=""
						autofocus="" value="${account.balance}" />
					<button name="Submit" type="Submit">Open Account</button>
				</c:if>
			</form:form>
		</div>
	</div>
</body>
</html>