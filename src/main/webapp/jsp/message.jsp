<%@ include file="init.jsp" %>

<div class="row">
	<div class="col-md-2">
		<%= WebUtil.buildMenu(request) %>
	</div>
	<div class="col-md-10">
		<img src="<%= request.getContextPath() %>/images/alert.gif" />&nbsp;&nbsp;<%= WebUtil.getMessage(request) %>
	</div>
</div>
<br />
<%= WebUtil.getLastForwardLink(request) %>