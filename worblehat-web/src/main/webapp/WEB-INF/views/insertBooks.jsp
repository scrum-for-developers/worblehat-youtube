<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>
<head>
<title>Add Book - Worblehat Bookmanager</title>
</head>
<body>
	<h1>Add Book</h1>

	<form:form commandName="bookDataFormData" method="POST">
            Title:<form:input id="title" path="title" />
		<form:errors path="title" />
		<br />
            Edition:<form:input id="edition" path="edition" />
		<form:errors path="edition" />
		<br />
            ISBN:<form:input id="isbn" path="isbn" />
		<form:errors path="isbn" />
		<br />
            Author:<form:input id="author" path="author" />
		<form:errors path="author" />
		<br />
            Year:<form:input id="year" path="year" />
		<form:errors path="year" />

		<br />
		<input type="submit" id="addBook" value="Add Book" />
		<hr/>
		<a href="<spring:url value="/" htmlEscape="true" />">Back to Home</a>
	</form:form>


</body>
</html>
