package org.dew.portlet;

import java.security.cert.X509Certificate;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;

/**
 * Implementazione strutturata di IAction per l'action login/logout.
 */
public abstract
class ALoginAction implements IAction 
{
	protected String sMSG_INVALID_USERNAME = "Username non valido";
	protected String sMSG_INVALID_PASSWORD = "Password non valida";
	protected String sMSG_UNKNOW_USER      = "Utente non riconosciuto";
	
	public Object action(String sAction, Parameters parameters, ActionRequest request, ActionResponse response) 
		throws Exception 
	{
		PortletConfig portletConfig = parameters.getPortletConfig();
		
		if(sAction.equalsIgnoreCase("logout")) {
			PortletSession portletSession = request.getPortletSession();
			User user = (User) portletSession.getAttribute(WNames.sSESS_USER);
			beforeLogout(request.getPortletSession(), portletConfig, user);
			doLogout(portletSession, portletConfig, user);
			return null;
		}
		
		if(parameters.isBlank("username")) {
			throw new Exception(sMSG_INVALID_USERNAME);
		}
		
		if(parameters.isBlank("password")) {
			throw new Exception(sMSG_INVALID_PASSWORD);
		}
		
		String sUserName = parameters.getString("username");
		String sPassword = parameters.getString("password");
		
		User user = doLogin(portletConfig, sUserName, sPassword);
		if(user == null) {
			throw new Exception(sMSG_UNKNOW_USER);
		}
		
		setUserMenu(portletConfig, user);
		
		setDefaultWindowState(response);
		
		afterLogin(request.getPortletSession(), portletConfig, user);
		
		return user;
	}
	
	public String view(String sAction, Parameters parameters, Object actionResult, RenderRequest request, RenderResponse response)
		throws Exception
	{
		return null;
	}
	
	public String exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response) 
		throws Exception
	{
		return null;
	}
	
	protected
	void setDefaultWindowState(ActionResponse response)
	{
		try {
			response.setWindowState(WindowState.MAXIMIZED);
		}
		catch(Exception ex) {
			System.err.println("Exception in ALoginCommandManager.setDefaultWindowState: " + ex);
		}
	}
	
	protected 
	void afterLogin(PortletSession session, PortletConfig portletConfig, User user) 
	{
	}
	
	protected 
	void beforeLogout(PortletSession session, PortletConfig portletConfig, User user) 
	{
	}
	
	protected abstract
	User checkRemoteUser(PortletConfig portletConfig, String sRemoteUser, List<String> listRoles, String sCurrentURL)
		throws Exception;
	
	protected abstract
	User doLogin(PortletConfig portletConfig, String sUserName, String sPassword)
		throws Exception;
	
	protected abstract
	void setUserMenu(PortletConfig portletConfig, User user)
		throws Exception;
	
	protected abstract
	void doLogout(PortletSession portletSession, PortletConfig portletConfig, User user)
		throws Exception;
	
	protected abstract
	String checkCertificate(String subjectNameId, String authnContextClassRef, X509Certificate certificate)
		throws Exception;
}
