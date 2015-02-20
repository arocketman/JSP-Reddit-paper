<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="wrapper">
	<div id="columns">
		<c:forEach items="${requestScope.submissions}" var="submission">
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