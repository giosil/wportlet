<%@ include file="init.jsp" %>

<div class="row">
  <div class="col-md-2">
    <%= WebUtil.buildMenu(request) %>
  </div>
  <div class="col-md-10">
    <strong>Hello</strong>
    <br>
    <br>
    <form method="POST" action="<portlet:actionURL />" class="form">
      <label for="por-name" class="form-label">Name:</label> <input type="text" name="<portlet:namespace />name" id="por-name" class="form-control"><br><br>
      <input type="submit" value="Greeting" class="btn btn-primary">
      <input type="hidden" name="<portlet:namespace />a" value="hello">
    </form>
  </div>
</div>
<br>
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a> - 
<a href="<portlet:actionURL><portlet:param name="f" value="help.jsp" /></portlet:actionURL>">Help</a> -
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a>

<script>
$(document).ready(function(){
	// bug fix datepicker liferay
	var uidpd=document.getElementById('ui-datepicker-div');
	if(uidpd)uidpd.remove();
});
</script>