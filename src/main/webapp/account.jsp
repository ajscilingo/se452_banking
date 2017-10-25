<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Account Details</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<div class="container">
		<div class="wrapper">
			<form action="account" method="post" id="accountForm"
				name="accountForm" class="form-signin">
				<h3 class="form-signin-heading">Open New Account</h3>
				<hr class="colorgraph">
				<br> <input type="hidden" name="customerId"
					value="${customer.id}" /> <input type="hidden" name="accountId"
					value="${account.id}" /> <input type="text" name="startingBalance"
					placeholder="Starting Balance" class="form-control" required=""
					autofocus="" /> <select name="accountType">
					<option value="CHECKING">Checking</option>
					<option value="SAVINGS">Savings</option>
					<option value="CD">CD</option>
				</select>
				<c:if test="${not empty customer.id }">
					<button name="Submit" value="openAccount" type="Submit">Open
						Account</button>
				</c:if>
			</form>
		</div>
	</div>
</body>
</html>