<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:import url="header.jsp"></c:import>

<form method="get" action="Display">
<input onkeyup="showUser(this.value)" id="topic" style="margin: 0 auto" value="${requestScope.topic}" type="text" name="topic" placeholder="What do you want to read about?" />
<div id="selectables">
<label>
Timespan:
	<select id="eta" name="eta" onchange="showUser(this.value)">
		<option value="all" <c:if test="${'all' eq requestScope.etaparam}">selected</c:if>>All</option>
		<option value="hour" <c:if test="${'hour' eq requestScope.etaparam}">selected</c:if>>Hour</option>
		<option value="day" <c:if test="${'day' eq requestScope.etaparam}">selected</c:if>>Day</option>
		<option value="week" <c:if test="${'week' eq requestScope.etaparam}">selected</c:if>>Week</option>
		<option value="month" <c:if test="${'month' eq requestScope.etaparam}">selected</c:if>>Month</option>
		<option value="year" <c:if test="${'year' eq requestScope.etaparam}">selected</c:if>>Year</option>
	</select>
</label>
<label>
nsfw:
	<select id="nsfw" name="nsfw" onchange="showUser(this.value)">
		<option value="no" <c:if test="${'no' eq requestScope.nsfwparam}">selected</c:if>>No .</option>
		<option value="yes"<c:if test="${'yes' eq requestScope.nsfwparam}">selected</c:if>>Yes .</option>
	</select>
</label>
</div>
</form>

<%-- Redirect back to index if no parameters are in --%>

<div id="wrapper">
	<c:import url="results.jsp"></c:import>
</div>

<c:import url="footer.jsp"></c:import>

<script>
            function showUser(str){
            	topic = document.getElementById("topic").value;
            	nsfw = document.getElementById("nsfw").value;
            	eta = document.getElementById("eta").value;
                if (str==""){
                    return;
                }
                if (window.XMLHttpRequest){
                    // code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else{
                    // code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=function(){
                    if (xmlhttp.readyState==4 && xmlhttp.status==200){ 
                    	document.getElementById("wrapper").innerHTML=xmlhttp.responseText;
                    }
                }
                //send request
                xmlhttp.open("GET","Display?topic="+topic+"&eta="+eta+"&nsfw="+nsfw+"&ajax=true",true);
                xmlhttp.send();
            }
</script>
