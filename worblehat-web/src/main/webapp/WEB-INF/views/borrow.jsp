<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>
<head>
<title>Borrow book - Worblehat Bookmanager</title>
</head>
<body>
	<h1>Borrow Book</h1>

	<form:form commandName="borrowFormData" method="POST">
            ISBN:<form:input id="isbn" path="isbn" />
		<form:errors path="isbn" />
		<br />
            Email:<form:input id="email" path="email" />
		<form:errors path="email" />
		<br />
		<input type="submit" id="borrowBook" value="Borrow Book" />
		<hr />
		<a href="<spring:url value="/" htmlEscape="true" />">Back to Home</a>
	</form:form>


</body>
</html>
