<%@ include file="init.jsp" %>

<%= WebUtil.buildMenu(request) %>

<hr />
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a>
<br />

<script>
$(document).ready(function(){
	// bug fix datepicker liferay
	var uidpd=document.getElementById('ui-datepicker-div');
	if(uidpd)uidpd.remove();
});
</script>