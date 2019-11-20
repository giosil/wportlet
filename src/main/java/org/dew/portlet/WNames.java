package org.dew.portlet;

public interface WNames {

	public final static String sPREFIX = "org.dew.portlet.";
	
	public final static String sPAR_ACTION     = "a";
	public final static String sPAR_FORWARD    = "f";
	public final static String sPAR_MENU_ITEM  = "mi";
	
	public final static String sACTION_FORWARD = "forward";
	public final static String sACTION_LOGIN   = "login";
	public final static String sACTION_LOGOUT  = "logout";
	
	public final static String sSESS_ACTION           = sPREFIX + "action";
	public final static String sSESS_PARARAMETERS     = sPREFIX + "parameters";
	public final static String sSESS_ACTION_RESULT    = sPREFIX + "actionResult";
	public final static String sSESS_ACTION_EXCEPTION = sPREFIX + "actionException";
	public final static String sSESS_LAST_FORWARD_URL = sPREFIX + "lastForwardURL";
	public final static String sSESS_LAST_MODE        = sPREFIX + "lastMode";
	public final static String sSESS_USER             = sPREFIX + "user";
	public final static String sSESS_MENU             = sPREFIX + "menu";
	
	public final static String sATTR_MESSAGE          = sPREFIX + "message";
	public final static String sATTR_IS_WARNING       = sPREFIX + "isWarning";
	public final static String sATTR_LAST_FORWARD_URL = sPREFIX + "lastForwardURL";
	public final static String sATTR_MENU             = sPREFIX + "menu";
	public final static String sATTR_TABS             = sPREFIX + "tabs";
	public final static String sATTR_RENDER_REQUEST   = sPREFIX + "renderRequest";
	public final static String sATTR_RENDER_RESPONSE  = sPREFIX + "renderResponse";
	public final static String sATTR_PARAMETERS       = sPREFIX + "parameters";
	public final static String sATTR_SQL_WITH_MSG     = sPREFIX + "sqlWithMessage";
	
	public final static String sNO_JSP   = "";
	public final static String sJSP_PATH = "/jsp/";
	
}
