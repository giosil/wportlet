package org.dew.portlet.demo;

import javax.portlet.*;

import org.dew.portlet.*;

public
class PortletListener implements IPortletListener
{
  public
  void init(PortletConfig portletConfig) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] init");
  }
  
  public
  void afterLogin(User user, ActionRequest request, ActionResponse response) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] afterLogin(" + user + ")");
  }
  
  public
  void beforeLogout(User user, ActionRequest request, ActionResponse response) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] beforeLogout(" + user + ")");
  }
  
  public 
  void exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] exception(" + sAction + "," + actionException + ")");
  }
  
  public
  void destroy() 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] destroy");
  }
}
