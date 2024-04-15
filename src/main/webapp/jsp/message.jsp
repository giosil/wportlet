<%@ include file="init.jsp" %>

<table>
<tr>
<td valign="top" width="250px">
<%= WebUtil.buildMenu(request) %>
</td>
<td valign="top" align="center">
<img src="<%= request.getContextPath() %>/images/alert.gif" />&nbsp;&nbsp;<%= WebUtil.getMessage(request) %>
</td>
</tr>
</table>
<br />
<%= WebUtil.getLastForwardLink(request) %>