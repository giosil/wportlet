package org.dew.portlet.ui;

import org.dew.portlet.*;

import java.util.*;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.http.*;

/**
 * Classe astratta per la costruzione di tabs.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public abstract
class ATabsBuilder 
{
  public static final String sCLASS    = "class";
  public static final String sNAME     = "name";
  public static final String sTABS     = "tabs";
  public static final String sCSS_PATH = "css_path";
  public static final String sIMG_PATH = "img_path";
  public static final String sJS_PATH  = "js_path";
  public static final String sCTX_PATH = "ctx_path";
  
  public static final String sLAST_ITEM_SELECTED = "lastItemSelected";
  
  public static final String sSELECTED = "_s";
  
  String _sToString;
  int _iIndex = 0;
  int _iLastItemSelected = 0;
  
  public
  String build(HttpServletRequest request, Map mapTabs)
  {
    _sToString = "";
    _iIndex = 0;
    
    if(mapTabs == null || mapTabs.isEmpty()) {
      return _sToString;
    }
    
    String sTabsName = (String) mapTabs.get(sNAME);
    if(sTabsName == null) sTabsName = sTABS;
    String sParSelected = request.getParameter(sTabsName + sSELECTED);
    if(sParSelected == null) {
      Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
      if(parameters != null) {
        sParSelected = parameters.getString(sTabsName + sSELECTED);
      }
    }
    if(sParSelected != null) {
      mapTabs.put(sLAST_ITEM_SELECTED, sParSelected);
    }
    
    Object oLastItemSelected = mapTabs.get(sLAST_ITEM_SELECTED);
    if(oLastItemSelected instanceof Integer) {
      _iLastItemSelected = ((Integer) oLastItemSelected).intValue();
    }
    else if(oLastItemSelected instanceof String) {
      if(((String) oLastItemSelected).length() > 0) {
        _iLastItemSelected = Integer.parseInt((String) oLastItemSelected);
      }
    }
    else {
      _iLastItemSelected = 0; 
    }
    
    List listTabs = getTabs(mapTabs);
    if(listTabs == null || listTabs.size() == 0) {
      return _sToString;
    }
    
    RenderResponse renderResponse = null;
    if(request != null) {
      mapTabs.put(sCTX_PATH, request.getContextPath());
      renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
    }
    
    beginTabs(mapTabs);
    
    for(int i = 0; i < listTabs.size(); i++) {
      String sTab = (String) listTabs.get(i);
      int iSep = sTab.indexOf("->");
      if(iSep < 0) continue;
      String sDescription = sTab.substring(0, iSep).trim();
      String sURL = normalizeURL(renderResponse, sTabsName, sTab.substring(iSep + 2).trim(), _iIndex);
      addTab(_iIndex, sDescription, sURL);
      _iIndex++;
    }
    
    endTabs();
    
    return _sToString;
  }
  
  protected static
  List getTabs(Map mapTabs)
  {
    List listResult = (List) mapTabs.get(sTABS);
    if(listResult != null) {
      return listResult;
    }
    
    List listKeys = new ArrayList();
    Iterator itKeys = mapTabs.keySet().iterator();
    while(itKeys.hasNext()) {
      String sKey = (String) itKeys.next();
      if(sKey.startsWith(sTABS + ".")) {
        listKeys.add(sKey);
      }
    }
    
    Collections.sort(listKeys);
    
    listResult = new ArrayList();
    for(int i = 0; i < listKeys.size(); i++) {
      String sKey = (String) listKeys.get(i);
      String sTab = (String) mapTabs.get(sKey);
      listResult.add(sTab);
    }
    
    return listResult;
  }
  
  protected static
  String getConfig(Map map, String sKey)
  {
    String sValue = (String) map.get(ResourcesMgr.sPORTAL_PLATFORM + "_" + sKey);
    if(sValue == null) {
      sValue = (String) map.get(sKey);
    }
    return sValue;
  }
  
  protected static
  String getConfig(Map map, String sKey, String sDefault)
  {
    String sValue = (String) map.get(ResourcesMgr.sPORTAL_PLATFORM + "_" + sKey);
    if(sValue == null) {
      sValue = (String) map.get(sKey);
      if(sValue == null) {
        return sDefault;
      }
    }
    return sValue;
  }
  
  protected static
  String normalizeJSString(String text)
  {
    StringBuffer sbResult = new StringBuffer(text.length());
    char c;
    for (int i = 0; i < text.length(); i++) {
      c = text.charAt(i);
      if (c == '\'') sbResult.append('\\');
      sbResult.append(c);
    }
    return sbResult.toString();
  }
  
  protected static
  String normalizeURL(RenderResponse renderResponse, String sTabsName, String sURL, int iIndex)
  {
    if(sURL == null) return null;
    if(renderResponse == null) return sURL;
    
    if(sURL.startsWith("#")) {
      return sURL;
    }
    if(sURL.startsWith("$")) {
      return sURL.substring(1);
    }
    int iColon = sURL.indexOf(':');
    if(iColon >= 0) {
      return sURL;
    }
    
    int iSepPar = sURL.indexOf('?');
    String sForward = (iSepPar > 0) ? sURL.substring(0, iSepPar) : sURL;
    
    PortletURL portletURL = renderResponse.createActionURL();
    portletURL.setParameter(WNames.sPAR_FORWARD, sForward);
    portletURL.setParameter(sTabsName + sSELECTED, String.valueOf(iIndex));
    if(iSepPar > 0) {
      String sParameters = sURL.substring(iSepPar + 1);
      StringTokenizer st = new StringTokenizer(sParameters, "&");
      while(st.hasMoreTokens()) {
        String sKeyValue = st.nextToken();
        int iSep = sKeyValue.indexOf('=');
        if(iSep < 0) continue;
        String sKey = sKeyValue.substring(0, iSep);
        String sValue = sKeyValue.substring(iSep + 1);
        portletURL.setParameter(sKey, sValue);
      }
    }
    return portletURL.toString();
  }
  
  // Abstract methods
  
  protected abstract
  void beginTabs(Map mapTabs);
  
  protected abstract
  void addTab(int iIndex, String sDescription, String sURL);
  
  protected abstract
  void endTabs();
}
