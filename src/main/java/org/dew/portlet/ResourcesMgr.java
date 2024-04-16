package org.dew.portlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.sql.DataSource;

import org.dew.portlet.ui.AMenuBuilder;
import org.dew.portlet.ui.ATabsBuilder;
import org.dew.portlet.ui.DataUtil;
import org.dew.portlet.ui.SimpleMenuBuilder;
import org.dew.portlet.ui.SimpleTabsBuilder;

/**
 * Classe per la gestione delle risorse di WPortlet.
 */
public 
class ResourcesMgr 
{
  public static String sJDBC_PATH = "jdbc/";
  public static String sPORTAL_PLATFORM = "";
  public static int    iPORTAL_VERSION  = 6;
  
  private static Map<String, ResourceBundle> _mapPoolBoundle = new HashMap<String, ResourceBundle>();
  private static Map<String, IAction> _mapIActions = new HashMap<String, IAction>();
  
  public static
  String checkPortalPlatform(PortletConfig portletConfig)
  {
    if(portletConfig == null || portletConfig.getPortletContext() == null) {
      return sPORTAL_PLATFORM;
    }
    String sServerInfo = portletConfig.getPortletContext().getServerInfo();
    PlatformUtil.log("ServerInfo: " + sServerInfo);
    sPORTAL_PLATFORM = "";
    if(sServerInfo != null && sServerInfo.length() > 0) {
      int iSep = sServerInfo.indexOf('/');
      if(iSep > 0) {
        sPORTAL_PLATFORM = sServerInfo.substring(0, iSep).toLowerCase();
        int iSp = sPORTAL_PLATFORM.indexOf(' ');
        if(iSp > 0) {
          sPORTAL_PLATFORM = sPORTAL_PLATFORM.substring(0, iSp);
        }
        String sVersion  = sServerInfo.substring(iSep + 1).trim();
        if(sVersion != null && sVersion.length() > 0) {
          int iDot = sVersion.indexOf('.');
          if(iDot > 0) {
            try { iPORTAL_VERSION = Integer.parseInt(sVersion.substring(0, iDot)); } catch(Exception ex) {}
          }
          else {
            try { iPORTAL_VERSION = Integer.parseInt(sVersion); } catch(Exception ex) {}
          }
        }
      }
      else {
        sPORTAL_PLATFORM = sServerInfo.trim().toLowerCase();
        int iSp = sPORTAL_PLATFORM.indexOf(' ');
        if(iSp > 0) {
          sPORTAL_PLATFORM = sPORTAL_PLATFORM.substring(0, iSp);
        }
        iPORTAL_VERSION = 1;
      }
    }
    PlatformUtil.log("sPORTAL_PLATFORM = " + sPORTAL_PLATFORM);
    PlatformUtil.log("iPORTAL_VERSION  = " + iPORTAL_VERSION);
    return sPORTAL_PLATFORM;
  }
  
  public static
  String getMessage(ActionRequest request, String sKey)
  {
    Locale locale = request.getLocale();
    ResourceBundle bundle = getBoundle(locale);
    return bundle.getString(sKey);
  }
  
  public static
  String getMessage(ActionRequest request, String sKey, Object... asParams)
  {
    Locale locale = request.getLocale();
    ResourceBundle bundle = getBoundle(locale);
    String sMessage = bundle.getString(sKey);
    return MessageFormat.format(sMessage, asParams);
  }
  
  public static
  String getMessage(RenderRequest request, String sKey)
  {
    Locale locale = request.getLocale();
    ResourceBundle bundle = getBoundle(locale);
    return bundle.getString(sKey);
  }
  
  public static
  String getMessage(RenderRequest request, String sKey, Object... asParams)
  {
    Locale locale = request.getLocale();
    ResourceBundle bundle = getBoundle(locale);
    String sMessage = bundle.getString(sKey);
    return MessageFormat.format(sMessage, asParams);
  }
  
  public static
  String getMessage(Locale locale, String sKey)
  {
    ResourceBundle bundle = getBoundle(locale);
    return bundle.getString(sKey);
  }
  
  public static
  String getMessage(Locale locale, String sKey, Object... asParams)
  {
    ResourceBundle bundle = getBoundle(locale);
    String sMessage = bundle.getString(sKey);
    return MessageFormat.format(sMessage, asParams);
  }
  
  public static
  ResourceBundle getBoundle(Locale locale)
  {
    if(locale == null) locale = Locale.getDefault();
    ResourceBundle bundle;
    bundle = _mapPoolBoundle.get(locale.toString());
    if(bundle == null) {
      bundle = ResourceBundle.getBundle("content/Language", locale);
      _mapPoolBoundle.put(locale.toString(), bundle);
    }
    return bundle;
  }
  
  public static
  Connection getConnection(String sDataSource)
    throws Exception
  {
    Context ctx = new InitialContext();
    // Inizialmente sJDBC_PATH = "jdbc/"
    try {
      DataSource ds = (DataSource) ctx.lookup(sJDBC_PATH + sDataSource);
      if(ds != null) return ds.getConnection(); 
    }
    catch(Exception ex) {}
    sJDBC_PATH = "java:/comp/env/jdbc/";
    try {
      DataSource ds = (DataSource) ctx.lookup(sJDBC_PATH + sDataSource);
      if(ds != null) return ds.getConnection();
    }
    catch(Exception ex) {}
    sJDBC_PATH = "java:/";
    try {
      DataSource ds = (DataSource) ctx.lookup(sJDBC_PATH + sDataSource);
      if(ds != null) return ds.getConnection();
    }
    catch(Exception ex) {}
    sJDBC_PATH = "java:/jdbc/";
    try {
      DataSource ds = (DataSource) ctx.lookup(sJDBC_PATH + sDataSource);
      if(ds != null) return ds.getConnection();
    }
    catch(Exception ex) {}
    sJDBC_PATH = "jdbc/";
    try {
      DataSource ds = (DataSource) ctx.lookup(sJDBC_PATH + sDataSource);
      if(ds != null) return ds.getConnection();
    }
    catch(Exception ex) {}
    throw new Exception("DataSource " + sDataSource + " not available.");
  }
  
  public static
  Connection getConnection(PortletConfig portletConfig)
    throws Exception
  {
    String sDataSource = portletConfig.getInitParameter("jdbc.ds");
    if(sDataSource != null && sDataSource.length() > 0) {
      return getConnection(sDataSource);
    }
    String sURL = portletConfig.getInitParameter("jdbc.url");
    if(sURL != null) {
      String sDriver = portletConfig.getInitParameter("jdbc.driver");
      if(sDriver == null) sDriver = "oracle.jdbc.driver.OracleDriver";
      String sUser = portletConfig.getInitParameter("jdbc.user");
      String sPassword = portletConfig.getInitParameter("jdbc.password");
      if(sPassword == null) sPassword = "";
      try {
        Class.forName(sDriver);
      }
      catch(Exception ex) {
        PlatformUtil.log("Exception in ResourcesMgr.getConnection/Class.forName(" + sDriver + ")", ex);
        throw new Exception(ex.getMessage());
      }
      Connection conn = null;
      try {
        conn = DriverManager.getConnection(sURL, sUser, sPassword);
      }
      catch(Exception ex) {
        PlatformUtil.log("Exception in ResourcesMgr.getConnection/DriverManager.getConnection(" + sURL + ", " + sUser + ", " + sPassword + ")", ex);
        throw new Exception(ex.getMessage());
      }
      return conn;
    }
    throw new Exception("jdbc configuration not found in portlet.xml");
  }
  
  public static
  Connection getConnection(Parameters parameters)
    throws Exception
  {
    return getConnection(parameters.getPortletConfig());
  }
  
  public static 
  IAction getDefaultAction(PortletConfig portletConfig)
  {
    return getAction(portletConfig, "default");
  }
  
  public static 
  IAction getAction(PortletConfig portletConfig, String sCommand)
  {
    String sIAction = portletConfig.getInitParameter("action." + sCommand);
    if(sIAction == null) {
      sIAction = portletConfig.getInitParameter("action.default");
      if(sIAction == null) return null;
    }
    IAction iaction = _mapIActions.get(sIAction);
    if(iaction != null) return iaction;
    try {
      Object oNewInstance = Class.forName(sIAction).newInstance();
      if(oNewInstance instanceof IAction) {
        _mapIActions.put(sIAction, (IAction) oNewInstance);
        return (IAction) oNewInstance;
      }
    } 
    catch (Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.getAction", ex);
    }
    return null;
  }
  
  public static 
  boolean existsAction(PortletConfig portletConfig, String sCommand)
  {
    if(portletConfig == null) return false;
    String sIAction = portletConfig.getInitParameter("action." + sCommand);
    return sIAction != null && sIAction.length() > 0;
  }
  
  public static
  void setActionResult(PortletSession portletSession, String action, IAction actionHandler, Object actionResult)
  {
    if(portletSession == null) return;
    
    WActionResult wActionResult = new WActionResult(action, actionHandler, actionResult);
    
    portletSession.setAttribute(WNames.sSESS_ACTION_RESULT, wActionResult, PortletSession.PORTLET_SCOPE);
  }
  
  public static
  void setActionException(PortletSession portletSession, String action, IAction actionHandler, Throwable throwable)
  {
    if(portletSession == null) return;
    
    WActionResult wActionResult = new WActionResult(action, actionHandler, throwable);
    
    portletSession.setAttribute(WNames.sSESS_ACTION_RESULT, wActionResult, PortletSession.PORTLET_SCOPE);
  }
  
  public static
  String callView(String action, Parameters parameters, Object actionResult, RenderRequest request, RenderResponse response) 
    throws Exception
  {
    if(action == null || parameters == null) return null;
    
    IAction actionHandler = ResourcesMgr.getAction(parameters.getPortletConfig(), action);
    
    if(actionHandler == null) return null;
    
    return actionHandler.view(action, parameters, actionResult, request, response);
  }
  
  public static
  String callException(String action, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response) 
    throws Exception
  {
    if(action == null || parameters == null) return null;
    
    IAction actionHandler = ResourcesMgr.getAction(parameters.getPortletConfig(), action);
    
    if(actionHandler == null) return null;
    
    return actionHandler.exception(action, parameters, actionException, request, response);
  }
  
  public static
  AMenuBuilder getMenuBuilder(Map<String, Object> mapMenu)
  {
    if(mapMenu == null || mapMenu.isEmpty()) {
      return new SimpleMenuBuilder();
    }
    Object oClass = mapMenu.get(sPORTAL_PLATFORM + "_" + AMenuBuilder.sCLASS);
    if(oClass == null) {
      oClass = mapMenu.get(AMenuBuilder.sCLASS);
    }
    try{
      Object oNewInstance = null;
      if(oClass instanceof AMenuBuilder) {
        return (AMenuBuilder) oClass;
      }
      else if(oClass instanceof Class) {
        oNewInstance = ((Class<?>) oClass).newInstance();
      }
      else if(oClass instanceof String) {
        oNewInstance = Class.forName((String) oClass).newInstance();
      }
      if(oNewInstance instanceof AMenuBuilder) {
        return (AMenuBuilder) oNewInstance;
      }
      else {
        PlatformUtil.log("ResourcesMgr.getMenuBuilder: " + oClass + " is not AMenuBuilder");
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.getMenuBuilder(" + AMenuBuilder.sCLASS + "=" + oClass + ")", ex);
    }
    return new SimpleMenuBuilder();
  }
  
  public static
  ATabsBuilder getTabsBuilder(Map<String, Object> mapTabs)
  {
    if(mapTabs == null || mapTabs.isEmpty()) {
      return new SimpleTabsBuilder();
    }
    Object oClass = mapTabs.get(sPORTAL_PLATFORM + "_" + ATabsBuilder.sCLASS);
    if(oClass == null) {
      oClass = mapTabs.get(ATabsBuilder.sCLASS);
    }
    try{
      Object oNewInstance = null;
      if(oClass instanceof ATabsBuilder) {
        return (ATabsBuilder) oClass;
      }
      else if(oClass instanceof Class) {
        oNewInstance = ((Class<?>) oClass).newInstance();
      }
      else if(oClass instanceof String) {
        oNewInstance = Class.forName((String) oClass).newInstance();
      }
      if(oNewInstance instanceof ATabsBuilder) {
        return (ATabsBuilder) oNewInstance;
      }
      else {
        PlatformUtil.log("ResourcesMgr.getTabsBuilder: " + oClass + " is not ATabsBuilder");
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.getTabsBuilder(" + ATabsBuilder.sCLASS + "=" + oClass + ")", ex);
    }
    return new SimpleTabsBuilder();
  }
  
  public static
  InputStream openPortletFile(PortletConfig portletConfig, String path)
  {
    if(path == null || path.length() == 0) {
      return null;
    }
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    if(is != null) return is;
    
    String sFilePath = portletConfig.getPortletContext().getRealPath(path);
    if(sFilePath == null || sFilePath.length() == 0) {
      return null;
    }
    try {
      return new FileInputStream(sFilePath);
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.openPortletFile(portletConfig, " + path + ")", ex);
    }
    return null;
  }
  
  public static
  Map<String, Object> loadTabs(PortletConfig portletConfig, String sTabsFile)
  {
    InputStream is = openPortletFile(portletConfig, "tabs/" + sTabsFile);
    if(is == null) return null;
    
    Map<String, Object> mapResult = new HashMap<String, Object>();
    List<String> listTabs = new ArrayList<String>();
    mapResult.put(ATabsBuilder.sTABS, listTabs);
    BufferedReader br = null;
    try {
      if(sTabsFile != null && sTabsFile.endsWith(".properties")) {
        return ResourcesMgr.loadFileProperties(is);
      }
      br = new BufferedReader(new InputStreamReader(is));
      String sLine = null;
      while((sLine = br.readLine()) != null) {
        if(sLine.trim().length() == 0) continue;
        if(sLine.charAt(0) == '#') continue;
        int iSepRef    = sLine.indexOf("->");
        int iSepEquals = sLine.indexOf('=');
        
        if(iSepEquals > 0 && iSepRef < 0) {
          String sConfKey = sLine.substring(0, iSepEquals).trim();
          String sConfVal = sLine.substring(iSepEquals + 1).trim();
          mapResult.put(sConfKey, sConfVal);
          continue;
        }
        
        if(iSepRef > 0) {
          listTabs.add(sLine);
        }
      }
    } 
    catch(Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.loadTabs(portletConfig, " + sTabsFile + ")", ex);
    }
    finally {
      if(br != null) try{ br.close(); } catch(Exception ex) {}
      if(is != null) try{ is.close(); } catch(Exception ex) {}
    }
    String sName = (String) mapResult.get(ATabsBuilder.sNAME);
    if(sName == null || sName.length() == 0) {
      mapResult.put(ATabsBuilder.sNAME, ATabsBuilder.sTABS);
    }
    return mapResult;
  }
  
  public static
  Map<String, Object> loadMenu(PortletConfig portletConfig, String sMenuFile)
  {
    InputStream is = openPortletFile(portletConfig, "menu/" + sMenuFile);
    if(is == null) return null;
    
    Map<String, Object> mapResult = new HashMap<String, Object>();
    List<Integer> listCounters = new ArrayList<Integer>();
    BufferedReader br = null;
    try {
      if(sMenuFile != null && sMenuFile.endsWith(".properties")) {
        return ResourcesMgr.loadFileProperties(is);
      }
      br = new BufferedReader(new InputStreamReader(is));
      String sLine = null;
      while((sLine = br.readLine()) != null) {
        if(sLine.trim().length() == 0) continue;
        if(sLine.charAt(0) == '#') continue;
        int iSepRef    = sLine.indexOf("->");
        int iSepEquals = sLine.indexOf('=');
        int iTabs = 0;
        for(int i = 0; i < sLine.length(); i++) {
          if(sLine.charAt(i) == '\t') iTabs++; else break;
        }
        if(iSepEquals > 0 && iSepRef < 0 && iTabs == 0) {
          String sConfKey = sLine.substring(0, iSepEquals).trim();
          String sConfVal = sLine.substring(iSepEquals + 1).trim();
          mapResult.put(sConfKey, sConfVal);
          continue;
        }
        int iCountersSize = listCounters.size();
        if(iTabs >= iCountersSize) {
          for(int i = 0; i < iTabs + 1 - iCountersSize; i++) {
            listCounters.add(new Integer(0));
          }
        }
        if(iTabs < iCountersSize - 1) {
          for(int i = 0; i < iCountersSize - iTabs - 1; i++) {
            listCounters.remove(listCounters.size() - 1);
          }
        }
        Integer oCounter = listCounters.get(iTabs);
        oCounter = new Integer(oCounter.intValue() + 1);
        listCounters.set(iTabs, oCounter);
        String sKeyNode = "";
        for(int i = 0; i < listCounters.size(); i++) {
          int iCount = listCounters.get(i).intValue();
          String sCount = (iCount < 10) ? "0" + iCount : String.valueOf(iCount);
          sKeyNode += sCount + ".";
        }
        String sDescription = null;
        String sURL = null;
        if(iSepRef > 0) {
          sDescription = sLine.substring(0, iSepRef).trim();
          sURL = sLine.substring(iSepRef + 2).trim();
          mapResult.put(sKeyNode + "@r", sURL);
        }
        else {
          sDescription = sLine.trim();
        }
        mapResult.put(sKeyNode + "@d", sDescription);
      }
    }
    catch (Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.loadMenu(portletConfig, " + sMenuFile + ")", ex);
    }
    finally {
      if(br != null) try{ br.close(); } catch(Exception ex) {}
      if(is != null) try{ is.close(); } catch(Exception ex) {}
    }
    return mapResult;
  }
  
  public static
  Map<String, Object> loadFileProperties(String sFilePath)
  {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(sFilePath));
    }
    catch (Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.loadFileProperties(" + sFilePath + ")", ex);
    }
    return DataUtil.expectMap(properties, true);
  }
  
  public static
  Map<String, Object> loadFileProperties(InputStream is)
  {
    Properties properties = new Properties();
    try {
      properties.load(is);
    }
    catch (Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.loadFileProperties(InputStream is)", ex);
    }
    return DataUtil.expectMap(properties, true);
  }
  
  public static
  String loadSQL(PortletConfig portletConfig, String sSQLFile)
  {
    InputStream is = openPortletFile(portletConfig, "sql/" + sSQLFile);
    if(is == null) return null;
    
    StringBuffer sb   = new StringBuffer();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      String sLine = null;
      while((sLine = br.readLine()) != null) {
        if(sLine.trim().length() == 0) continue;
        if(sLine.startsWith("--")) continue;
        sb.append(sLine);
        sb.append(' ');
      }
    } 
    catch (Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.loadSQL(portletConfig," + sSQLFile + ")", ex);
    }
    finally {
      if(br != null) try{ br.close(); } catch(Exception ex) {}
      if(is != null) try{ is.close(); } catch(Exception ex) {}
    }
    return sb.toString();
  }
  
  public static
  String loadHTML(Parameters parameters, String sHTMLFile, RenderResponse renderResponse)
  {
    return loadHTML(parameters.getPortletConfig(), sHTMLFile, renderResponse);
  }
  
  public static
  String loadHTML(PortletConfig portletConfig, String sHTMLFile, RenderResponse renderResponse)
  {
    InputStream is = openPortletFile(portletConfig, "html/" + sHTMLFile);
    if(is == null) return null;
    
    StringBuffer sb   = new StringBuffer();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      String sLine = null;
      while((sLine = br.readLine()) != null) {
        sb.append(sLine + "\n");
      }
    } 
    catch (Exception ex) {
      PlatformUtil.log("Exception in ResourcesMgr.loadHTML(portletConfig," + sHTMLFile + ",renderResponse)", ex);
    }
    finally {
      if(br != null) try{ br.close(); } catch(Exception ex) {}
      if(is != null) try{ is.close(); } catch(Exception ex) {}
    }
    String sResult = sb.toString();
    int iBeginBody = sResult.indexOf("<body");
    if(iBeginBody >= 0) {
      int iInnerHtml = sResult.indexOf('>', iBeginBody + 1);
      if(iInnerHtml < 0) iInnerHtml = iBeginBody + 6;
      int iEndBody = sResult.indexOf("</body>");
      if(iEndBody < iBeginBody) iEndBody = sResult.length();
      sResult = sResult.substring(iInnerHtml + 1, iEndBody);
    }
    String contextName = portletConfig.getPortletContext().getPortletContextName();
    sResult = sResult.replace("\"../images/", "\"/" + contextName + "/images/");
    if(renderResponse != null) {
      PortletURL portletURL = renderResponse.createActionURL();
      int iBegLink = sResult.indexOf("\"../jsp/");
      while(iBegLink >= 0) {
        int iEndLink = sResult.indexOf('"', iBegLink + 1);
        if(iEndLink <= 0) {
          iBegLink = sResult.indexOf("\"../jsp/", iBegLink + 1);
          continue;
        }
        String sLink   = sResult.substring(iBegLink + 8, iEndLink);
        int iSepParams = sLink.indexOf('?');
        String sPage   = iSepParams > 0 ? sLink.substring(0,  iSepParams) : sLink;
        String sParams = iSepParams > 0 ? sLink.substring(iSepParams + 1) : "";
        
        portletURL.setParameter(WNames.sPAR_FORWARD, sPage);
        if(sParams != null && sParams.length() > 0) {
          StringTokenizer st = new StringTokenizer(sParams, "&");
          while(st.hasMoreTokens()) {
            String sKeyValue = st.nextToken();
            int iSep = sKeyValue.indexOf('=');
            if(iSep < 0) continue;
            String sKey = sKeyValue.substring(0, iSep);
            String sValue = sKeyValue.substring(iSep + 1);
            portletURL.setParameter(sKey, sValue);
          }
        }
        String sPorletURL = portletURL.toString();
        int lLengthURL = sPorletURL.length();
        String sLeft   = sResult.substring(0, iBegLink);
        String sRight  = sResult.substring(iEndLink + 1);
        sResult = sLeft + "\"" + sPorletURL + "\"" + sRight;
        
        iBegLink = sResult.indexOf("\"../jsp/", iBegLink + lLengthURL);
      }
    }
    return sResult;
  }
}
