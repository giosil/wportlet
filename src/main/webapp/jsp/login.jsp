<%@ include file="init.jsp" %>

<div style="text-align: center">
  <strong>Login Page</strong>
  <br>
  <br>
  <form method="POST" action="<portlet:actionURL />" class="form">
    <label for="por-username" class="form-label">Username:</label> <input type="text" name="<portlet:namespace />username" class="form-control" id="por-username"><br><br>
    <label for="por-password" class="form-label">Password:</label> <input type="text" name="<portlet:namespace />password" class="form-control" id="por-password"><br><br>
  <% if(WebUtil.checkMessage(request)) { %>
    <span style="font-weight: bold;color: #aa0000"><%= WebUtil.getMessage(request) %></span><br><br>
  <% } %>
    <input type="submit" value="Login" class="btn btn-primary">
    <input type="hidden" name="<portlet:namespace />a" value="login">
  </form>
</div>