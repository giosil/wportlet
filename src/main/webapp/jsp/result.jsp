<%@ include file="init.jsp" %>

<div class="row">
  <div class="col-md-2">
    <%= WebUtil.buildMenu(request) %>
  </div>
  <div class="col-md-10">
    <%= WebUtil.getString(request, "title",    "<b>", "</b>") %><br />
    <%= WebUtil.getString(request, "subtitle", "<i>", "</i>") %><br />
    <br />
    <table <%= WebUtil.sCLASS_TABLE %> >
    <%= WebUtil.buildTableContent(request, "result") %>
    </table>
    <br />
    <%= WebUtil.getString(request, "notes") %>
  </div>
</div>
<br />
<a href="<%= WebUtil.buildForwardHome(request) %>">Home</a> - 
<a href="<portlet:actionURL><portlet:param name="f" value="help.jsp" /></portlet:actionURL>">Help</a> -
<a href="<portlet:actionURL windowState="<%= WindowState.NORMAL.toString() %>"><portlet:param name="a" value="logout" /></portlet:actionURL>">Logout</a> -  
<%= WebUtil.getLastForwardLink(request) %>