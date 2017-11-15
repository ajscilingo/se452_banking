<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Existing Customers</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>

	<h1>Customers</h1>

	<c:forEach items="${customerList}" var="customer">
		<p>${customer.firstName} ${customer.middleInitial}
			${customer.lastName}</p>
	</c:forEach>

</body>
</html>