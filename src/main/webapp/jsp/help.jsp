<%@ include file="init.jsp" %>

<%
	String name = WebUtil.getPortletName(request);
%>
<img src="<%= request.getContextPath() %>/images/help.png" />&nbsp;&nbsp;Help page
<br />
<br />
PortletName: <strong><%= name %></strong>
<br />
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a>