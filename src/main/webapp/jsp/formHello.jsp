<%@ include file="init.jsp" %>

<table>
	<tr>
		<td valign="top" width="250px">
			<%= WebUtil.buildMenu(request) %>
		</td>
		<td valign="top" align="center">
			<strong>Hello</strong>
			<br>
			<br>
			<form method="POST" action="<portlet:actionURL />">
				<label>Name:</label> <input type="text" name="<portlet:namespace />name"><br><br>
				<input type="submit" value="Greeting">
				<input type="hidden" name="<portlet:namespace />a" value="hello">
			</form>
		</td>
	</tr>
</table>
<br>
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a> - 
<a href="<portlet:actionURL><portlet:param name="f" value="help.jsp" /></portlet:actionURL>">Help</a> -
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a>

