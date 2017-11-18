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
<title>My Accounts</title>
<link rel="stylesheet" type="text/css" href="../styles.css">
</head>

<body>
	<jsp:include page="../menu.jsp" />
	<fmt:setLocale value="en_US" />
	<div class="container">
		<div class="wrapper">
			<div id="accounts">
				<h3 class="form-signin-heading">Accounts</h3>
				<hr class="colorgraph">
				<c:if test="${not empty customer.id}">
					<c:if test="${not empty customerAccounts}">
						<table>
							<tr>
								<th>Account Type</th>
								<th>Balance</th>
								<th>Details</th>
							</tr>
							<c:forEach items="${customerAccounts}" var="customerAccount">
								<tr>
									<td><c:out
											value="${customerAccount.accountType.displayName}" /></td>
									<td><fmt:formatNumber value="${customerAccount.balance}"
											type="currency" /></td>
									<td class="center"><a class="blue" href="/account/view?id=${customerAccount.id}">View Account</a></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${empty customerAccounts}">
						<p>No Accounts Found</p>
					</c:if>
					<button name="Submit" value="createAcount"
						onClick="javascript:window.location='/account/add';">Add
						Account</button>
				</c:if>
			</div>
		</div>
	</div>
</body>

</html>