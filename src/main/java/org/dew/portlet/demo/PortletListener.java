package org.dew.portlet.demo;

import javax.portlet.*;

import org.dew.portlet.*;

public
class PortletListener implements IPortletListener
{
  @Override
  public
  void init(PortletConfig portletConfig) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] init");
  }
  
  @Override
  public
  void afterLogin(User user, ActionRequest request, ActionResponse response) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] afterLogin(" + user + ")");
  }
  
  @Override
  public
  void beforeLogout(User user, ActionRequest request, ActionResponse response) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] beforeLogout(" + user + ")");
  }
  
  @Override
  public 
  String exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response) 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] exception(" + sAction + "," + actionException + ")");
    return null;
  }
  
  @Override
  public
  void destroy() 
  {
    PlatformUtil.log("[org.dew.portlet.demo.PortletListener] destroy");
  }
}
