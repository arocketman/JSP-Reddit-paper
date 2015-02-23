<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="wrapper">
	<div id="columns">
		<c:forEach items="${submissions}" var="submission">
			<a <c:if test="${fn:contains(submission.url,'youtu')}">class="test-popup-link"</c:if><c:if test="${fn:endsWith(submission.url, '.jpg') or fn:endsWith(submission.url, '.png') or fn:endsWith(submission.url, '.gif')}">class="test-popup-image"</c:if> href="${submission.url}">
			<div class="pin">
				<img src="${submission.thumbnail}"/>
				<jsp:useBean id="myDate" class="java.util.Date"/>  
				<c:set target="${myDate}" property="time" value="${submission.createdUTC * 1000}"/>    
				<h6>from : <a href="http://reddit.com/r/${submission.subreddit }">${submission.subreddit}</a> - <fmt:formatDate value="${myDate}"/></h6>
				<a href="http://reddit.com${submission.permalink}"><h3>${submission.title}</h3></a>
				<h4>score: ${submission.score}</h4>
			</div>
			</a>
		</c:forEach>
	</div>
</div>
