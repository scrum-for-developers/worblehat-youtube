<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>
<head>
<title>Return All Books - Worblehat Bookmanager</title>
</head>
<body>
	<h1>Return all Books</h1>

	<form:form commandName="returnAllBookFormData" method="POST">
	     Email Address:<form:input id="emailAddress" path="emailAddress" />
		<form:errors path="emailAddress" />
		
		<br />
		<input type="submit" id="returnAllBooks" value="Return All Books" />

	</form:form>

	<hr />
	<a href="<spring:url value="/" htmlEscape="true" />">Back to Home</a>
</body>
</html>
