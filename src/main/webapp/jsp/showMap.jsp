<%@ include file="init.jsp" %>

<div class="row">
  <div class="col-md-2">
    <%= WebUtil.buildMenu(request) %>
  </div>
  <div class="col-md-10">
    <%= WebUtil.buildTabs(request) %>
    <br />
    <br />
    <strong><%= WebUtil.getString(request, "title") %></strong>
    <br />
    <br />
    <table <%= WebUtil.sCLASS_TABLE %> >
    <%= WebUtil.buildTableContent(request, "result") %>
    </table>
  </div>
</div>
<br />
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a> - 
<a href="<portlet:actionURL><portlet:param name="f" value="help.jsp" /></portlet:actionURL>">Help</a> -
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a>

