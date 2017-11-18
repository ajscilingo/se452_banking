<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Account</title>
<link rel="stylesheet" type="text/css" href="../styles.css">
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="container">
		<div class="wrapper"></div>
		<div id="accounts">
			<table>
				<tr>
					<th>Account Type</th>
					<th>Balance</th>
				</tr>
				<tr>
					<td class="center"><c:out value="${account.accountType.displayName}" /></td>
					<td class="center"><fmt:formatNumber value="${account.balance}"
							type="currency" /></td>
				</tr>
			</table>
		</div>
		<form:form method="POST" class="form-signin" action="deposit"
			id="accountDepositForm" commandName="deposit">
			<h3 class="form-signin-heading">Make Deposit/Withdraw</h3>
			<hr class="colorgraph">
			<br>
			<input type="text" class="form-control" name="amount"
				placeholder="Deposit Amount" required="" autofocus="" />
			<button name="Submit" type="Submit">Deposit</button>
		</form:form>
		<form:form method="POST" class="form-signin" action="withdraw"
			id="accountWithdrawForm" commandName="withdraw">
			<input type="text" class="form-control" name="amount"
				placeholder="Withdraw Amount" required="" autofocus="" />
			<button name="Submit" type="Submit">Withdraw</button>
		</form:form>
		<form:form method="POST" class="form-signin" action="close"
		id="accountCloseForm" commandName="account">
		<button name="Submit" type="Submit">Close Account</button>
		</form:form>
	</div>
</body>
</html>