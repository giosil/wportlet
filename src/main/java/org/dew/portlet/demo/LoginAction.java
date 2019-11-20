package org.dew.portlet.demo;

import java.security.cert.X509Certificate;
import java.util.List;

import javax.portlet.*;

import org.dew.portlet.*;

public 
class LoginAction extends ALoginAction
{
  @Override
  protected
  User doLogin(PortletConfig portletConfig, String sUserName, String sPassword)
    throws Exception
  {
    if(!sUserName.equals(sPassword)) return null;
    
    User user = new User();
    user.setUserName(sUserName);
    user.setPassword(sPassword);
    return user;
  }
  
  @Override
  protected
  void setUserMenu(PortletConfig portletConfig, User user)
    throws Exception
  {
    // Se in portlet.xml vi e' specificato il menu si puo' fare a meno di caricarlo alla login.
    // Nel caso in cui si voglia un menu diverso a seconda dell'utente, del ruolo, ecc. occorre implementare tale metodo.
    
    // Map mapMenu = ResourcesMgr.loadMenu(portletConfig, "default.txt");
    // user.setMenu(mapMenu);
  }
  
  protected
  void doLogout(PortletConfig portletConfig, User user)
    throws Exception
  {
  }
  
  @Override
  protected 
  User checkRemoteUser(PortletConfig portletConfig, String sRemoteUser, List<String> listRoles, String sCurrentURL) 
    throws Exception 
  {
    return null;
  }
  
  @Override
  protected 
  void doLogout(PortletSession portletSession, PortletConfig portletConfig, User user) 
    throws Exception 
  {
  }
  
  @Override
  protected String checkCertificate(String subjectNameId, String authnContextClassRef, X509Certificate certificate)
    throws Exception 
  {
    return null;
  }
}
