<%@ include file="init.jsp" %>

<table>
<tr>
<td valign="top" width="250px">
<%= WebUtil.buildMenu(request) %>
</td>
<td valign="top" align="center">
<%= WebUtil.getString(request, "title", "<b>", "</b>") %><br />
<%= WebUtil.getString(request, "subtitle", "<i>", "</i>") %><br />
<br />
<table <%= WebUtil.sCLASS_TABLE %> >
<%= WebUtil.buildTableContent(request, "result") %>
</table>
<br />
<%= WebUtil.getString(request, "notes") %>
</td>
</tr>
</table>
<br />
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a> - 
<a href="<portlet:actionURL><portlet:param name="f" value="help.jsp" /></portlet:actionURL>">Help</a> -
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a> -  
<%= WebUtil.getLastForwardLink(request) %>