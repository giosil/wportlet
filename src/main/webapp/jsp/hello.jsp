<%@ include file="init.jsp" %>

<% 
  String hello = (String) request.getAttribute("hello");
%>
<div class="row">
  <div class="col-md-2">
    <%= WebUtil.buildMenu(request) %>
  </div>
  <div class="col-md-10">
    <i><%= hello %></i>
  </div>
</div>
<br />
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a> - 
<a href="<portlet:actionURL><portlet:param name="f" value="help.jsp" /></portlet:actionURL>">Help</a> -
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a> -  
<%= WebUtil.getLastForwardLink(request) %>

<script>
$(document).ready(function(){
	// bug fix datepicker liferay
	var uidpd=document.getElementById('ui-datepicker-div');
	if(uidpd)uidpd.remove();
});
</script>