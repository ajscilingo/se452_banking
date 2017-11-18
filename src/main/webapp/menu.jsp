<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="styles.css">
<div style="border: 1px solid #ccc;padding:5px;margin-bottom:20px;">
 
   <a href="${pageContext.request.contextPath}/customer/add">User Info</a>&nbsp;|&nbsp;
   <a href="${pageContext.request.contextPath}/account/list">Accounts</a>
  <c:if test="${pageContext.request.userPrincipal.name != null}">
  &nbsp;|&nbsp;
     <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </c:if>
</div> 