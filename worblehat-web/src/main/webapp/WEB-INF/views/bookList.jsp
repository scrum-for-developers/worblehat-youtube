<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<html>
<head>
	<title>Show all Books</title>
</head>
<body>
	<table>
	<thead>  
		<tr>
			<th>Title</th>
			<th>Author</th>
			<th>Year</th>
			<th>Edition</th>
			<th>ISBN</th>
			<th>Borrower</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${books}" var="book">
		<tr>
			<td>${book.title}</td>
			<td>${book.author}</td>
			<td>${book.year}</td>
			<td>${book.edition}</td>
			<td>${book.isbn}</td>
			<td>${book.currentBorrowing.borrowerEmailAddress}</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	<hr />
	<a href="<spring:url value="/" htmlEscape="true" />">Back to Home</a>
</body>
</html>