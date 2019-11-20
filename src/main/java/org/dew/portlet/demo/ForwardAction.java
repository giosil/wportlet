package org.dew.portlet.demo;

import java.util.*;

import javax.portlet.*;

import org.dew.portlet.*;

public 
class ForwardAction implements IAction 
{
	public
	Object action(String sAction, Parameters parameters, ActionRequest request, ActionResponse response)
		throws Exception
	{
		String sPage = parameters.getString(WNames.sPAR_FORWARD);
		System.out.println("ForwardAction.action action=" + sAction + " (" + WNames.sPAR_FORWARD + "=" + sPage + ")");
		return null;
	}
	
	public
	String view(String sAction, Parameters parameters, Object actionResult, RenderRequest request, RenderResponse response)
		throws Exception
	{
		String sPage = parameters.getString(WNames.sPAR_FORWARD);
		System.out.println("ForwardAction.view action=" + sAction + " (" + WNames.sPAR_FORWARD + "=" + sPage + ")");
		
		if(sPage.equals("showMap.jsp")) {
			initShowMap(parameters, request);
			return null;
		}
		
		return null;
	}
	
	public
	String exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response)
		throws Exception
	{
		return null;
	}
	
	protected
	void initShowMap(Parameters parameters, RenderRequest request)
	{
		Map<String,Object> mapTabs = ResourcesMgr.loadTabs(parameters.getPortletConfig(), "tabsShowMap.txt");
		request.setAttribute(WNames.sATTR_TABS, mapTabs);
		
		String sMap = parameters.getString("map");
		if(sMap.equals("config")) {
			request.setAttribute("title", "Configuration");
			request.setAttribute("result", parameters.getConfig());
		}
		else
		if(sMap.equals("parameters")) {
			request.setAttribute("title", "Parameters");
			request.setAttribute("result", parameters);
		}
		else
		if(sMap.equals("preferences")) {
			request.setAttribute("title", "Preferences");
			request.setAttribute("result", parameters.getPreferences());
		}
		else
		if(sMap.equals("session")) {
			PortletSession portletSession = request.getPortletSession();
			
			Map<String,Object> mapSession = new HashMap<String,Object>();
			Enumeration<String> enumNames = portletSession.getAttributeNames();
			while(enumNames.hasMoreElements()) {
				String sName = (String) enumNames.nextElement();
				Object oAttr = portletSession.getAttribute(sName);
				if(oAttr != null) {
					String sAttr = oAttr.toString();
					if(sAttr.length() > 60) sAttr = sAttr.substring(0, 60) + "...";
					mapSession.put(sName, sAttr);
				}
			}
			request.setAttribute("title", "Session");
			request.setAttribute("result", mapSession);
		}
		else {
			request.setAttribute("title", "Unknow map");
		}
	}
}
