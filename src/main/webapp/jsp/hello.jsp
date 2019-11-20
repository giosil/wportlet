<%@ include file="init.jsp" %>

<% 
	String sHello = (String) request.getAttribute("hello");
%>
<table>
<tr>
<td valign="top" width="250px">
<%= WebUtil.buildMenu(request) %>
</td>
<td valign="top" align="center">	
<i><%= sHello %></i>
</td>
</tr>
</table>
<br />
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a> - 
<a href="<portlet:actionURL><portlet:param name="f" value="help.jsp" /></portlet:actionURL>">Help</a> -
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a> -  
<%= WebUtil.getLastForwardLink(request) %>