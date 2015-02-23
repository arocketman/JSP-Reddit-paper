<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp"></c:import>
<h1>JSP Reddit Paper</h1>
<form method="get" action="Display">
<input type="text" name="topic" placeholder="What do you want to read about?" />
<c:if test="${not empty requestScope.errMessage}">
<p style="text-align:center;color:red;"><c:out value="${requestScope.errMessage}"></c:out></p>
</c:if>
<input class="belize-hole-flat-button" type="submit" value="Paperize it"/>
</form>

<div id="wrapper">
	<jsp:useBean id="homeBean" class="main.java.webapp.HomeBean"></jsp:useBean>
	<c:set var="submissions" value="${homeBean.submissions}" scope="request" />
	<c:import url="results.jsp"></c:import>	
</div>

<c:import url="footer.jsp"></c:import>

