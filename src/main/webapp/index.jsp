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
	<div id="columns">
	<jsp:useBean id="homeBean" class="main.java.webapp.HomeBean"></jsp:useBean>
		<c:forEach items="${homeBean.submissions}" var="submission">
			<a href="http://reddit.com/${submission.permalink}">
			<div class="pin">
				<img src="${submission.thumbnail}"/>
				<jsp:useBean id="myDate" class="java.util.Date"/>  
				<c:set target="${myDate}" property="time" value="${submission.createdUTC * 1000}"/>    
				<h6>from : ${submission.subreddit} - <fmt:formatDate value="${myDate}"/></h6>
				<h3>${submission.title}</h3>
				<h4>score: ${submission.score}</h4>
			</div>
			</a>
		</c:forEach>
	</div>
</div>

<c:import url="footer.jsp"></c:import>

