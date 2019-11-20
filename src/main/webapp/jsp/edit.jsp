<%@ include file="init.jsp" %>

<% 
	request.setAttribute("preferences", WebUtil.getPreferences(request));
%>

<img src="<%= request.getContextPath() %>/images/edit.png" />&nbsp;&nbsp;Edit page
<br />
<br />
<table>
<%= WebUtil.buildTableContent(request, "preferences") %>
</table>