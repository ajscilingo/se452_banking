<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Details</title>
<link rel="stylesheet" type="text/css" href="../styles.css">
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="container">
		<div class="wrapper">
			<form:form method="POST" class="form-signin" action="add" commandName="customer">
				<h3 class="form-signin-heading">
					<c:if test="${not empty customer.id}">Update User</c:if>
					<c:if test="${empty customer.id}">Create User</c:if>
				</h3>
				<hr class="colorgraph">
				<br> <input type="hidden" name="id" value="${customer.id}" /> <input
					type="text" class="form-control" name="firstName"
					placeholder="First Name" required="" autofocus=""
					value="${customer.firstName}" /> <input type="text"
					class="form-control" name="middleInitial"
					placeholder="Middle Initial" required="" autofocus="" maxlength="1"
					value="${customer.middleInitial}" /> <input type="text"
					class="form-control" name="lastName" placeholder="Last Name"
					required="" value="${customer.lastName}" />
				<hr class="colorgraph">
				<input type="text" class="form-control" name="address.addressLine1"
					placeholder="Address Line 1" required="" autofocus=""
					value="${customer.address.addressLine1}" /> <input type="text"
					class="form-control" name="address.addressLine2"
					placeholder="Address Line 2" autofocus=""
					value="${customer.address.addressLine2}" /> 
					<form:select path="address.county"
					class="form-control" name="address.county" placeholder="County" required=""
					autofocus="" value="${customer.address.county}" items="${allCounties}" /> 
					<form:select path="address.zipcode" 
					class="form-control" name="address.zipcode" placeholder="Zip Code"
					required="" autofocus="" value="${customer.address.zipcode}" items="${allZipcodes}"/>
				<button name="Submit" type="Submit">
					<c:if test="${not empty customer.id}">Update</c:if>
					<c:if test="${empty customer.id}">Create</c:if>
				</button>
			</form:form>
			<c:if test="${not empty customer.id}">
				<form action="account" method="post" id="addNewAccountForm"
					name="addNewAccountForm" class="form-signin">
					<button name="Submit" value="createAcount" type="Submit">Add
						Account</button>
				</form>
			</c:if>
		</div>
	</div>
</body>
</html>