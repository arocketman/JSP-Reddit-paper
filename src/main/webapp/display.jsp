<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="header.jsp"></c:import>

<form method="get" action="Display">
<input style="margin: 0 auto" type="text" name="topic" placeholder="What do you want to read about?" />
</form>

<%-- Redirect back to index if no parameters are in --%>

<div id="wrapper">
	<div id="columns">
		<c:forEach items="${requestScope.submissions}" var="submission">
			<a href="http://reddit.com/${submission.permalink}"><div class="pin">
				<img src="${submission.thumbnail}"/>
				<h6>from : ${submission.subreddit}</h6>
				<h3>${submission.title}</h3>
				<h4>score: ${submission.score}</h4>
			</div></a>
		</c:forEach>
	</div>
</div>
<c:import url="footer.jsp"></c:import>