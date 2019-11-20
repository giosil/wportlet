<%@ include file="init.jsp" %>

<%
	String sPortletName = WebUtil.getPortletName(request);
%>
<img src="<%= request.getContextPath() %>/images/help.png" />&nbsp;&nbsp;Help page
<br />
<br />
PortletName: <strong><%= sPortletName %></strong>