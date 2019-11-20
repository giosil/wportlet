package org.dew.portlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.dew.portlet.ui.AMenuBuilder;
import org.dew.portlet.ui.WebUtil;

/**
 * Classe che estende <code>javax.portlet.GenericPortlet</code> e implementa il pattern
 * costituito da <code>action</code> identificati da oggetti che implementano {@link IAction}.<br />
 * In <code>portlet.xml</code> possono essere specificati i seguenti parametri di inizializzazione:
 * 
 * <pre>
 * view-jsp = pagina jsp di view
 * edit-jsp = pagina jsp di edit
 * help-jsp = pagina jsp di help
 * message-jsp = pagina jsp per la visualizzazione dei messaggi
 * login-jsp = pagina jsp di login
 * menu = file di menu
 * </pre>
 * Le azioni si configurano con il prefisso <code>action.</code> seguito dal nome dell'azione.
 * <pre>
 * &lt;init-param&gt;
 *	&lt;name&gt;action.hello&lt;/name&gt;
 *	&lt;value&gt;org.dew.test.HelloAction&lt;/value&gt;
 * &lt;/init-param&gt;
 * </pre>
 * Nel caso in cui e' presente <code>action.login</code> viene attivato il controllo 
 * di sessione utente.<br />
 * L'azione <code>action.forward</code> puo' essere utile per effettuare delle elaborazioni
 * prima di mostrare una pagina jsp richiamata tramite un'azione di forward.<br />
 * 
 * Per configurare l'accesso alla base dati si puo' specificare il data source
 * eventualmente impostato nell'application server:
 * <pre>
 * &lt;init-param&gt;
 *	&lt;name&gt;jdbc.ds&lt;/name&gt;
 *	&lt;value&gt;db_test&lt;/value&gt;
 * &lt;/init-param&gt;
 * </pre> 
 * oppure i parametri per stabilire una connession jdbc:
 * <pre>
 * &lt;init-param&gt;
 *	&lt;name&gt;jdbc.driver&lt;/name&gt;
 *	&lt;value&gt;oracle.jdbc.driver.OracleDriver&lt;/value&gt;
 * &lt;/init-param&gt;
 * &lt;init-param&gt;
 *	&lt;name&gt;jdbc.url&lt;/name&gt;
 *	&lt;value&gt;jdbc:oracle:thin:@localhost:1521:TEST&lt;/value&gt;
 * &lt;/init-param&gt;
 * &lt;init-param&gt;
 *	&lt;name&gt;jdbc.user&lt;/name&gt;
 *	&lt;value&gt;user&lt;/value&gt;
 * &lt;/init-param&gt;
 * &lt;init-param&gt;
 *	&lt;name&gt;jdbc.password&lt;/name&gt;
 *	&lt;value&gt;pass&lt;/value&gt;
 * &lt;/init-param&gt;
 * </pre> 
 * E' possibile specificare un listener nel seguente modo:<br />
 * <pre>
 * &lt;init-param&gt;
 *	&lt;name&gt;listener&lt;/name&gt;
 *	&lt;value&gt;org.dew.test.PortletLister&lt;/value&gt;
 * &lt;/init-param&gt;
 * </pre>
 * Il listener deve implementare l'interfaccia {@link IPortletListener}.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public 
class WPortlet extends GenericPortlet implements WNames
{
	String _sNamespace;
	PortletConfig _portletConfig;
	IPortletListener _portletListener;
	boolean _boHasLogin  = false;
	boolean _boHasLogout = false;
	String _sViewPage;
	String _sEditPage;
	String _sHelpPage;
	String _sLoginPage;
	String _sMsgPage;
	String _sMenu;
	
	public 
	void init(PortletConfig portletConfig) throws PortletException
	{
		super.init(portletConfig);
		
		this._portletConfig = portletConfig;
		
		String sActionLogin  = portletConfig.getInitParameter("action.login");
		_boHasLogin = sActionLogin != null && sActionLogin.length() > 0;
		String sActionLogout = portletConfig.getInitParameter("action.logout");
		_boHasLogout = sActionLogout != null && sActionLogout.length() > 0;
		
		_sViewPage  = portletConfig.getInitParameter("view-jsp");
		_sEditPage  = portletConfig.getInitParameter("edit-jsp");
		_sHelpPage  = portletConfig.getInitParameter("help-jsp");
		_sMsgPage   = portletConfig.getInitParameter("message-jsp");
		_sLoginPage = portletConfig.getInitParameter("login-jsp");
		
		if(_sViewPage  == null) _sViewPage  = "index.jsp";
		if(_sEditPage  == null) _sEditPage  = "edit.jsp";
		if(_sHelpPage  == null) _sHelpPage  = "help.jsp";
		if(_sMsgPage   == null) _sMsgPage   = "message.jsp";
		if(_sLoginPage == null) _sLoginPage = "login.jsp";
		
		String sPortalPlatform = ResourcesMgr.checkPortalPlatform(portletConfig);
		WebUtil.init(portletConfig);
		
		_sMenu = portletConfig.getInitParameter("menu");
		
		String sPortletName = _portletConfig.getPortletName();
		
		this._portletListener = null;
		String sListener = portletConfig.getInitParameter("listener");
		if(sListener != null && sListener.length() > 0) {
			try{
				this._portletListener = (IPortletListener) Class.forName(sListener).newInstance();
			}
			catch(Exception ex) {
				SnapTracer.trace(this, "init", ex);
				System.err.println("[WPortlet] Exception loading class " + sListener + ": " + ex);
			}
		}
		if(_portletListener != null) {
			_portletListener.init(_portletConfig);
		}
		System.out.println("[WPortlet] Portal: " + sPortalPlatform + " - init of " + sPortletName + " completed.");
	}
	
	public 
	void destroy()
	{
		if(_portletListener != null) {
			_portletListener.destroy();
		}
	}
	
	public 
	void processAction(ActionRequest request, ActionResponse response) 
		throws PortletException
	{	
		Parameters parameters = new Parameters(_portletConfig, request, _sNamespace);
		boolean boActionIsBlank  = parameters.isBlank(sPAR_ACTION);
		boolean boForwardIsBlank = parameters.isBlank(sPAR_FORWARD);
		if(boActionIsBlank && boForwardIsBlank) {
			return;
		}
		
		PortletSession session = request.getPortletSession();
		session.removeAttribute(sSESS_ACTION);
		session.removeAttribute(sSESS_PARARAMETERS);
		session.removeAttribute(sSESS_ACTION_RESULT);
		session.removeAttribute(sSESS_ACTION_EXCEPTION);
		
		String sAction = parameters.getString(sPAR_ACTION);
		if(boActionIsBlank) {
			sAction = sACTION_FORWARD;
		}
		
		boolean boActionLogin  = _boHasLogin && sAction.equals(sACTION_LOGIN);
		boolean boActionLogout = _boHasLogin && sAction.equals(sACTION_LOGOUT);
		String sKeyAction = sAction;
		if(boActionLogout && !_boHasLogout) {
			// In caso non vi sia un gestore del logout si utilizza quello del login.
			sKeyAction = sACTION_LOGIN;
		}
		if(_boHasLogin && !boActionLogin) {
			if(session.getAttribute(sSESS_USER) == null) return;
		}
		
		session.setAttribute(sSESS_ACTION,       sAction,    PortletSession.PORTLET_SCOPE);
		session.setAttribute(sSESS_PARARAMETERS, parameters, PortletSession.PORTLET_SCOPE);
		
		Object actionResult = null;
		Exception actionException = null;
		IAction iaction = ResourcesMgr.getAction(_portletConfig, sKeyAction);
		if(iaction != null) {
			try {
				if(boActionLogout) {
					if(_portletListener != null) {
						Object user = session.getAttribute(sSESS_USER);
						if(user instanceof User) {
							_portletListener.beforeLogout((User) user, request, response);
						}
					}
				}
				actionResult = iaction.action(sAction, parameters, request, response);
				if(boActionLogin && (actionResult instanceof User)) {
					session.setAttribute(sSESS_USER, actionResult, PortletSession.PORTLET_SCOPE);
					Map mapMenu = ((User) actionResult).getMenu();
					if(mapMenu != null && !mapMenu.isEmpty()) {
						session.setAttribute(sSESS_MENU, mapMenu, PortletSession.PORTLET_SCOPE);
					}
					if(_portletListener != null) {
						_portletListener.afterLogin((User) actionResult, request, response);
					}
				}
				if(boActionLogout) {
					session.removeAttribute(sSESS_USER);
					session.removeAttribute(sSESS_MENU);
					session.removeAttribute(sSESS_LAST_FORWARD_URL);
					return;
				}
			} 
			catch (Exception ex) {
				SnapTracer.trace(iaction, "action(" + sAction + ")", ex);
				System.out.println("Exception in " + iaction.getClass().getName() + ".action(" + sAction + "):");
				ex.printStackTrace();
				actionException = ex;
			}
		}
		
		session.setAttribute(sSESS_ACTION_RESULT,    actionResult,    PortletSession.PORTLET_SCOPE);
		session.setAttribute(sSESS_ACTION_EXCEPTION, actionException, PortletSession.PORTLET_SCOPE);
	}
	
	public 
	void doView(RenderRequest request, RenderResponse response) 
		throws PortletException, IOException
	{
		processView(request, response, true, _sViewPage);
	}
	
	public 
	void doEdit(RenderRequest request, RenderResponse response) 
		throws PortletException, IOException
	{
		processView(request, response, false, _sEditPage);
	}
	
	public 
	void doHelp(RenderRequest request, RenderResponse response) 
		throws PortletException, IOException
	{
		processView(request, response, false, _sHelpPage);
	}
	
	protected 
	void processView(RenderRequest request, RenderResponse response, boolean boCheckLogin, String sDefaultPage) 
		throws PortletException, IOException
	{
		if(_sNamespace == null) {
			_sNamespace = response.getNamespace();
		}
		request.setAttribute(sATTR_RENDER_REQUEST,  request);
		request.setAttribute(sATTR_RENDER_RESPONSE, response);
		
		PortletSession session = request.getPortletSession();
		
		// Controllo login
		if(boCheckLogin && _boHasLogin && !checkLogin(request, response, session)) {
			return;
		}
		
		// Controllo cambio modalita'
		boolean boChangeMode = false;
		String sPortletMode = request.getPortletMode().toString();
		String sLastMode = (String) session.getAttribute(sSESS_LAST_MODE);
		if(sLastMode != null) {
			boChangeMode = !sLastMode.equals(sPortletMode);
		}
		session.setAttribute(sSESS_LAST_MODE, sPortletMode, PortletSession.PORTLET_SCOPE);
		if(boChangeMode) {
			session.removeAttribute(sSESS_LAST_FORWARD_URL);
			session.removeAttribute(sSESS_ACTION);
			session.removeAttribute(sSESS_PARARAMETERS);
			session.removeAttribute(sSESS_ACTION_RESULT);
			session.removeAttribute(sSESS_ACTION_EXCEPTION);
		}
		else {
			request.setAttribute(sATTR_LAST_FORWARD_URL, session.getAttribute(sSESS_LAST_FORWARD_URL));
		}
		
		// Recupero parametri
		Parameters parameters = (Parameters) session.getAttribute(sSESS_PARARAMETERS);
		if(parameters == null) {
			parameters = new Parameters(_portletConfig, request, _sNamespace);
		}
		else {
			parameters.setRenderRequest(request);
		}
		request.setAttribute(sATTR_PARAMETERS, parameters);
		
		// Gestione menu
		Map mapMenu = (Map) session.getAttribute(sSESS_MENU);
		if(mapMenu == null) {
			if(_sMenu != null && _sMenu.length() > 0) {
				if(_boHasLogin) {
					mapMenu = ResourcesMgr.loadMenu(_portletConfig, _sMenu);
					session.setAttribute(sSESS_MENU, mapMenu, PortletSession.PORTLET_SCOPE);
					
					Object user = session.getAttribute(sSESS_USER);
					if(user instanceof User) {
						((User) user).setMenu(mapMenu);
					}
				}
				else {
					mapMenu = ResourcesMgr.loadMenu(_portletConfig, _sMenu);
					session.setAttribute(sSESS_MENU, mapMenu, PortletSession.PORTLET_SCOPE);
				}
			}
		}
		if(mapMenu != null && !mapMenu.isEmpty()) {
			Object oMenuItem = parameters.get(sPAR_MENU_ITEM);
			if(oMenuItem != null) {
				mapMenu.put(AMenuBuilder.sLAST_ITEM_SELECTED, oMenuItem);
			}
			request.setAttribute(sATTR_MENU, mapMenu);
		}
		
		// Preparazione per l'invocazione di IAction.view
		String sAction = null;
		String sPage   = null;
		if(boChangeMode) {
			sAction = sACTION_FORWARD;
			sPage   = sDefaultPage;
		}
		else {
			sAction = (String) session.getAttribute(sSESS_ACTION);
			if(sAction == null) sAction = sACTION_FORWARD;
			sPage = parameters.getString(sPAR_FORWARD, sDefaultPage);
		}
		parameters.put(sPAR_FORWARD, sPage);
		if(sAction.equals(sACTION_FORWARD)) {
			session.setAttribute(sSESS_LAST_FORWARD_URL, parameters.getActionURL(response), PortletSession.PORTLET_SCOPE);
		}
		
		Object actionResult       = session.getAttribute(sSESS_ACTION_RESULT);
		Exception actionException = (Exception) session.getAttribute(sSESS_ACTION_EXCEPTION);
		
		IAction iaction = ResourcesMgr.getAction(_portletConfig, sAction);
		if(iaction == null) {
			if(sAction.equals(sACTION_FORWARD)) {
				include(request, response, sPage);
				return;
			}
			else {
				showMessage(request, response, _sMsgPage, "No IAction for " + sAction + " action.");
				return;
			}
		}
		
		if(actionException != null) {
			if(actionException instanceof WarningException) {
				request.setAttribute(sATTR_IS_WARNING, Boolean.TRUE);
			}
			else {
				request.setAttribute(sATTR_IS_WARNING, Boolean.FALSE);
			}
			try {
				String sMsgPage = iaction.exception(sAction, parameters, actionException, request, response);
				if(sMsgPage != null) {
					if(!sMsgPage.equals(sNO_JSP)) {
						showMessage(request, response, sMsgPage, actionException);
					}
				}
				else {
					showMessage(request, response, _sMsgPage, actionException);
				}
			} 
			catch (Throwable th) {
				SnapTracer.trace(iaction, "exception(" + sAction + ")", th);
				showMessage(request, response, _sMsgPage, th);
			}
			return;
		}
		
		try {
			String sResultPage = iaction.view(sAction, parameters, actionResult, request, response);
			if(sResultPage != null) {
				if(!sResultPage.equals(sNO_JSP)) {
					include(request, response, sResultPage);
				}
			}
			else {
				include(request, response, sPage);
			}
		}
		catch (Throwable th) {
			SnapTracer.trace(iaction, "view(" + sAction + ")", th);
			System.out.println("Exception in " + iaction.getClass().getName() + ".view(" + sAction + "):");
			th.printStackTrace();
			if(th instanceof Exception) {
				actionException = (Exception) th;
			}
			else {
				actionException = new Exception(th);
			}
			if(actionException instanceof WarningException) {
				request.setAttribute(sATTR_IS_WARNING, Boolean.TRUE);
			}
			else {
				request.setAttribute(sATTR_IS_WARNING, Boolean.FALSE);
			}
			try {
				String sMsgPage = iaction.exception(sAction, parameters, actionException, request, response);
				if(sMsgPage != null) {
					if(!sMsgPage.equals(sNO_JSP)) {
						showMessage(request, response, sMsgPage, actionException);
					}
				}
				else {
					showMessage(request, response, _sMsgPage, actionException);
				}
			}
			catch (Throwable th2) {
				SnapTracer.trace(iaction, "exception(" + sAction + ")", th2);
				showMessage(request, response, _sMsgPage, th2);
			}
		}
	}
	
	protected 
	void include(RenderRequest request, RenderResponse response, String sPage) 
		throws PortletException, IOException
	{
		response.setContentType("text/html");
		PortletContext portletContext = getPortletContext();
		PortletRequestDispatcher dispatcher = portletContext.getRequestDispatcher(sJSP_PATH + sPage);
		dispatcher.include(request, response);
	}
	
	protected 
	void showMessage(RenderRequest request, RenderResponse response, String sPage, String sMessage) 
		throws PortletException, IOException
	{
		request.setAttribute(sATTR_MESSAGE, sMessage);
		response.setContentType("text/html");
		PortletContext portletContext = getPortletContext();
		PortletRequestDispatcher dispatcher = portletContext.getRequestDispatcher(sJSP_PATH + _sMsgPage);
		dispatcher.include(request, response);
	}
	
	protected 
	void showMessage(RenderRequest request, RenderResponse response, String sPage, Throwable th) 
		throws PortletException, IOException
	{
		String sMessage = "";
		if(th != null) {
			sMessage = th.getMessage();
			if(sMessage == null) {
				sMessage = th.toString();
			}
			if(sMessage == null || sMessage.length() == 0) {
				sMessage = "Errore non identificato";
			}
			int iException = sMessage.lastIndexOf("Exception:");
			if(iException >= 0) {
				sMessage = sMessage.substring(iException + 10).trim();
			}
		}
		request.setAttribute(sATTR_MESSAGE, sMessage);
		response.setContentType("text/html");
		PortletContext portletContext = getPortletContext();
		PortletRequestDispatcher dispatcher = portletContext.getRequestDispatcher(sJSP_PATH + sPage);
		dispatcher.include(request, response);
	}
	
	protected
	boolean checkLogin(RenderRequest request, RenderResponse response, PortletSession session) 
		throws PortletException, IOException
	{
		if(session.getAttribute(sSESS_USER) != null) {
			return true;
		}
		
		String sRemoteUser = request.getRemoteUser();
		List<String> listRoles = new ArrayList<String>();
		int iAuthLevel = WUtil.toInt(request.getAuthType(), 0);
		
		if(sRemoteUser != null && sRemoteUser.length() > 0) {
			IAction iaction = ResourcesMgr.getAction(_portletConfig, sACTION_LOGIN);
			if(iaction instanceof ALoginAction) {
				try {
					String sCurrentURL = request.getServerName() + "/" + request.getContextPath();
					User user = ((ALoginAction) iaction).checkRemoteUser(_portletConfig, sRemoteUser, listRoles, sCurrentURL);
					if(user != null) {
						user.setAuthLevel(iAuthLevel);
						session.setAttribute(sSESS_USER, user, PortletSession.PORTLET_SCOPE);
						((ALoginAction) iaction).afterLogin(session, _portletConfig, user);
						return true;
					}
				}
				catch(Exception ex) {
					SnapTracer.trace(this, "checkLogin", ex);
					ex.printStackTrace();
				}
			}
		}
		String sAction = (String) session.getAttribute(sSESS_ACTION);
		if(sAction != null && sAction.equals(sACTION_LOGIN)) {
			Exception exLogin = (Exception) session.getAttribute(sSESS_ACTION_EXCEPTION); 
			if(exLogin == null) {
				return true;
			}
			String sMessage = exLogin.getMessage();
			request.setAttribute(sATTR_MESSAGE, sMessage);
		}
		Object parameters = request.getAttribute(sATTR_PARAMETERS);
		if(parameters == null) {
			request.setAttribute(sATTR_PARAMETERS, new Parameters(_portletConfig, request, _sNamespace));
		}
		include(request, response, _sLoginPage);
		return false;
	}
	
	/**
	 * Redirige la view su un'altra action.
	 * ATTENZIONE: l'oggetto parameters deve essere correttamente costruito. 
	 * 
	 * @param request ActionRequest
	 * @param sAction action
	 * @param parameters Parameters
	 * @return boolean
	 */
	public static
	boolean setViewAction(ActionRequest request, String sAction, Parameters parameters)
	{
		if(parameters != null) {
			boolean exists = ResourcesMgr.existsAction(parameters.getPortletConfig(), sAction);
			if(!exists) return false;
		}
		PortletSession session = request.getPortletSession();
		if(sAction != null && sAction.length() > 0) {
			session.setAttribute(sSESS_ACTION, sAction, PortletSession.PORTLET_SCOPE);
		}
		else {
			return false;
		}
		if(parameters != null) {
			session.setAttribute(sSESS_PARARAMETERS, parameters, PortletSession.PORTLET_SCOPE);
		}
		return true;
	}
}
