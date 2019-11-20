<%@ include file="init.jsp" %>

<div style="text-align: center">
	<strong>Login Page</strong>
	<br>
	<br>
	<form method="POST" action="<portlet:actionURL />">
		<label>Username:</label> <input type="text" name="<portlet:namespace />username"><br><br>
		<label>Password:</label> <input type="text" name="<portlet:namespace />password"><br><br>
	<% if(WebUtil.checkMessage(request)) { %>
		<span style="font-weight: bold;color: #aa0000"><%= WebUtil.getMessage(request) %></span><br><br>
	<% } %>
		<input type="submit" value="Login">
		<input type="hidden" name="<portlet:namespace />a" value="login">
	</form>
</div>
