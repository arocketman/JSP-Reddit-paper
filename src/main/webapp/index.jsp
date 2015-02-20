<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp"></c:import>
<h1>JSP Reddit Paper</h1>
<form method="get" action="Display">
<input type="text" name="topic" placeholder="What do you want to read about?" />
<c:if test="${not empty requestScope.errMessage}">
<p style="text-align:center;color:red;"><c:out value="${requestScope.errMessage}"></c:out></p>
</c:if>
<input type="submit" value="Paperize it"/>
</form>
<c:import url="footer.jsp"></c:import>