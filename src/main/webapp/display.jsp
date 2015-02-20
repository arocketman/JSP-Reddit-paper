<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:import url="header.jsp"></c:import>

<form method="get" action="Display">
<input style="margin: 0 auto" value="${requestScope.topic}" type="text" name="topic" placeholder="What do you want to read about?" />
<div id="selectables">
<label>
Timespan:
	<select name="eta">
		<option value="hour" <c:if test="${'hour' eq requestScope.etaparam}">selected</c:if>>Hour</option>
		<option value="day" <c:if test="${'day' eq requestScope.etaparam}">selected</c:if>>Day</option>
		<option value="week" <c:if test="${'week' eq requestScope.etaparam}">selected</c:if>>Week</option>
		<option value="month" <c:if test="${'month' eq requestScope.etaparam}">selected</c:if>>Month</option>
		<option value="year" <c:if test="${'year' eq requestScope.etaparam}">selected</c:if>>Year</option>
		<option value="all" <c:if test="${'all' eq requestScope.etaparam}">selected</c:if>>All</option>
	</select>
</label>
<label>
nsfw:
	<select name="nsfw">
		<option value="yes"<c:if test="${'yes' eq requestScope.nsfwparam}">selected</c:if>>Yes .</option>
		<option value="no" <c:if test="${'no' eq requestScope.nsfwparam}">selected</c:if>>No .</option>
	</select>
</label>
</div>
</form>

<%-- Redirect back to index if no parameters are in --%>

<div id="wrapper">
	<div id="columns">
		<c:forEach items="${requestScope.submissions}" var="submission">
			<a href="http://reddit.com/${submission.permalink}"><div class="pin">
				<img src="${submission.thumbnail}"/>
				<jsp:useBean id="myDate" class="java.util.Date"/>  
				<c:set target="${myDate}" property="time" value="${submission.createdUTC * 1000}"/>    
				<h6>from : ${submission.subreddit} - <fmt:formatDate value="${myDate}"/></h6>
				<h3>${submission.title}</h3>
				<h4>score: ${submission.score}</h4>
			</div></a>
		</c:forEach>
	</div>
</div>
<c:import url="footer.jsp"></c:import>