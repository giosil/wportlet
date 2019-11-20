<%@ include file="init.jsp" %>

<%= WebUtil.buildMenu(request) %>

<hr />
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a>
<br />

