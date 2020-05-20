package org.dew.portlet.demo;

import javax.portlet.*;

import org.dew.portlet.*;

public
class PortletListener implements IPortletListener
{
  public
  void init(PortletConfig portletConfig) {
    System.out.println("[org.dew.test.PortletListener] init");
  }
  
  public
  void afterLogin(User user, ActionRequest request, ActionResponse response) {
    System.out.println("[org.dew.test.PortletListener] afterLogin(" + user + ")");  
  }
  
  public
  void beforeLogout(User user, ActionRequest request, ActionResponse response) {
    System.out.println("[org.dew.test.PortletListener] beforeLogout(" + user + ")");
  }
  
  public
  void destroy() {
    System.out.println("[org.dew.test.PortletListener] destroy");
  }
}
