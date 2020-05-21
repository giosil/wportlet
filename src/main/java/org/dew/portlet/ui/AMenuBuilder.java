package org.dew.portlet.ui;

import org.dew.portlet.WNames;
import org.dew.portlet.ResourcesMgr;

import java.util.*;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.servlet.http.*;
import javax.swing.tree.*;

/**
 * Classe astratta per la costruzione di menu a piu' livelli.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public abstract
class AMenuBuilder
{
  public static final String sROOT     = "root";
  public static final String sCLASS    = "class";
  public static final String sSTATE    = "state";
  public static final String sCSS_PATH = "css_path";
  public static final String sIMG_PATH = "img_path";
  public static final String sJS_PATH  = "js_path";
  public static final String sCTX_PATH = "ctx_path";
  
  public static final String sREFERENCES         = "references";
  public static final String sCACHE_TREE         = "tree";
  public static final String sLAST_ITEM_SELECTED = "lastItemSelected";
  
  public static final String sITEM_DESCRIPTION = "@d";
  public static final String sITEM_INDEX       = "@i";
  public static final String sITEM_REFERENCE   = "@r";
  
  protected String _sToString;
  protected int _iIndex = 0;
  protected int _iLastItemSelected = 0;
  protected WindowState _defaultWindowState = WindowState.MAXIMIZED;
  protected String _contextPath = "";
  
  public
  String build(HttpServletRequest request, Map mapMenu)
  {  
    _sToString = "";
    _iIndex = 0;
    
    if(mapMenu == null || mapMenu.isEmpty()) {
      return _sToString;
    }
    
    Object oLastItemSelected = mapMenu.get(sLAST_ITEM_SELECTED);
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
  
    String sState = (String) mapMenu.get(sSTATE);
    if(sState != null) {
      sState = sState.toLowerCase();
      if(sState.startsWith("nor")) {
        _defaultWindowState = WindowState.NORMAL;
      }
      else if(sState.startsWith("max")) {
        _defaultWindowState = WindowState.MAXIMIZED;
      }
    }
    
    TreeMenuNode root = (TreeMenuNode) mapMenu.get(sCACHE_TREE);
    if(root == null) {
      root = buildTree(mapMenu);
    }
    
    RenderResponse renderResponse = null;
    if(request != null) {
      _contextPath = request.getContextPath();
      mapMenu.put(sCTX_PATH, request.getContextPath());
      renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
    }
    
    if(_contextPath == null) _contextPath = "";
    
    beginMenu(mapMenu);
    
    walkTree(root, -1, renderResponse);
    
    endMenu();
    
    return _sToString;
  }
  
  public
  String buildReadyFunction(HttpServletRequest request, Map mapMenu)
  {
    return "";
  }
  
  protected
  void walkTree(TreeMenuNode node, int iParentId, RenderResponse renderResponse)
  {
    int iIdNode  = _iIndex + 1; 
    String sDesc = node.toString();
    String sURL  = normalizeURL(renderResponse, node.getURL(), iIdNode);
    
    addNode(_iIndex, iIdNode, iParentId, node.getLevel(), node.getDepth(), sDesc, sURL);
    
    _iIndex++;
    
    int iChildCount = node.getChildCount();
    for(int i = 0; i < iChildCount; i++) {
      TreeMenuNode child = (TreeMenuNode) node.getChildAt(i);
      walkTree(child, iIdNode, renderResponse);
    }
    
    endNode(_iIndex, iIdNode, iParentId, node.getLevel(), node.getDepth(), sDesc, sURL);
  }
  
  protected
  TreeMenuNode buildTree(Map mapMenu)
  {
    String sRoot = (String) mapMenu.get(sROOT);
    if(sRoot == null) sRoot = "Menu";
    Map mapReferences = (Map) mapMenu.get(sREFERENCES);
    
    Map mapNodes = new HashMap();
    List listNodes = new ArrayList();
    List listNodesToRemove = new ArrayList();
    
    TreeMenuNode oRoot = new TreeMenuNode(sRoot);
    listNodes.add(oRoot);
    
    List listKeys = getSortedKeys(mapMenu);
    for(int i = 0; i < listKeys.size(); i++) {
      String sKey = (String) listKeys.get(i);
      if(sKey.indexOf('.') < 0) continue;
      
      StringTokenizer st = new StringTokenizer(sKey, ".");
      TreeMenuNode oPrevNode = oRoot;
      String sIdNode = null;
      while(st.hasMoreTokens()) {
        String sToken = st.nextToken().trim();
        
        if(sToken.equals(sITEM_DESCRIPTION)) continue;
        if(sToken.equals(sITEM_INDEX)) continue;
        if(sToken.equals(sITEM_REFERENCE)) continue;
        if(sIdNode == null) {
          sIdNode = sToken;
        }
        else {
          sIdNode = sIdNode + "." + sToken;
        }
        
        TreeMenuNode oCurrNode = null;
        oCurrNode = (TreeMenuNode) mapNodes.get(sIdNode);
        if(oCurrNode == null) {
          String sDesc = (String) mapMenu.get(sIdNode + "." + sITEM_DESCRIPTION);
          if(sDesc == null) sDesc = sToken;
          String sReference = (String) mapMenu.get(sIdNode + "." + sITEM_REFERENCE);
          
          if(sReference != null) {
            String sURL = null;
            if(mapReferences != null) {
              sURL = (String) mapReferences.get(sReference);
            }
            else {
              sURL = sReference;
            }
            if(sURL != null) {
              oCurrNode = new TreeMenuNode(sDesc, sURL);
            }
            else {
              if(oPrevNode != null && !listNodesToRemove.contains(oPrevNode)) {
                listNodesToRemove.add(oPrevNode);
              }
              continue;
            }
          }
          else {
            oCurrNode = new TreeMenuNode(sDesc);
          }
          
          mapNodes.put(sIdNode, oCurrNode);
          listNodes.add(oCurrNode);
          if(oPrevNode != null) {
            oPrevNode.add(oCurrNode);
          }
        }
        
        oPrevNode = oCurrNode;
      }
    }
    
    for(int i = 0; i < listNodesToRemove.size(); i++) {
      remove((DefaultMutableTreeNode) listNodesToRemove.get(i));
    }
    
    return oRoot;
  }
  
  protected static
  List getSortedKeys(Map mapMenu)
  {
    List listResult = new ArrayList();
    
    Iterator oItKeys = mapMenu.keySet().iterator();
    while(oItKeys.hasNext()) {
      String sKey = (String) oItKeys.next();
      int iLastPoint = sKey.lastIndexOf('.');
      if(iLastPoint < 0) {
        continue; 
      }
      int iBegin = sKey.lastIndexOf('.', iLastPoint - 1);
      String sKeyIndex = sKey.substring(0, iLastPoint + 1) + sITEM_INDEX;
      String sIndex = (String) mapMenu.get(sKeyIndex);
      if(sIndex == null) {
        sIndex = "000";
      }
      else {
        int iLength = sIndex.length();
        for(int i = 0; i < 3-iLength; i++) {
          sIndex = "0" + sIndex;
        }
      }
      String s1 = sKey.substring(0, iBegin + 1);
      String s2 = sKey.substring(iBegin + 1);
      String sKeyToAdd = s1 + "'" + sIndex + "'" + s2;
      listResult.add(sKeyToAdd);
    }
    
    Collections.sort(listResult);
    
    for(int i = 0; i < listResult.size(); i++) {
      String sKey = (String) listResult.get(i);
      int iBegin = sKey.indexOf('\'');
      int iEnd   = sKey.indexOf('\'', iBegin + 1);
      String sKeyToSet = sKey.substring(0, iBegin) + sKey.substring(iEnd + 1);
      listResult.set(i, sKeyToSet);
    }
    
    return listResult;
  }
  
  protected static
  void remove(DefaultMutableTreeNode oNode)
  {
    if(oNode.getChildCount() > 0) return;
    DefaultMutableTreeNode oParent = (DefaultMutableTreeNode) oNode.getParent();
    oParent.remove(oNode);
    remove(oParent);
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
  
  protected
  String normalizeURL(RenderResponse renderResponse, String sURL, int iIdNode)
  {
    if(sURL == null) return null;
    if(renderResponse == null) return sURL;
    boolean boActionURL = false;
    
    if(sURL.startsWith("#")) {
      return sURL;
    }
    if(sURL.startsWith("$")) {
      return sURL.substring(1);
    }
    if(sURL.startsWith("@")) {
      boActionURL = true;
      sURL = sURL.substring(1);
    }
    int iColon = sURL.indexOf(':');
    if(iColon >= 0) {
      return sURL;
    }
    
    int iSepPar = sURL.indexOf('?');
    String sForward = (iSepPar > 0) ? sURL.substring(0, iSepPar) : sURL;
    
    PortletURL portletURL = renderResponse.createActionURL();
    if(_defaultWindowState != null) {
      try { portletURL.setWindowState(_defaultWindowState); } catch (Exception e) {};
    }
    if(boActionURL) {
      portletURL.setParameter(WNames.sPAR_ACTION, sForward);
    }
    else {
      portletURL.setParameter(WNames.sPAR_FORWARD, sForward);
    }
    portletURL.setParameter(WNames.sPAR_MENU_ITEM, String.valueOf(iIdNode));
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
  
  protected static
  int getTabs(String sLine)
  {
    int iResult = 0;
    for(int i = 0; i < sLine.length(); i++) {
      char c = sLine.charAt(i);
      if(c == ' ' || c == '\t') {
        iResult++; 
      }
      else {
        break;
      }
    }
    return iResult;
  }
  
  public
  String toString()
  {
    return _sToString;
  }
  
  // Abstract methods
  
  protected abstract
  void beginMenu(Map mapMenu);
  
  protected abstract
  void addNode(int iIndex, int iIdNode, int iIdParent, int iLevel, int iDepth, String sDescription, String sURL);
  
  protected abstract
  void endNode(int iIndex, int iIdNode, int iIdParent, int iLevel, int iDepth, String sDescription, String sURL);
  
  protected abstract
  void endMenu();
  
  static
  class TreeMenuNode extends DefaultMutableTreeNode
  {
    private static final long serialVersionUID = 1L;
    
    private String sURL;
    
    public
    TreeMenuNode(Object oDescription)
    {
      super(oDescription);
    }
    
    public
    TreeMenuNode(Object oDescription, String sURL)
    {
      super(oDescription);
      this.sURL = sURL;
    }
    
    public
    String getURL()
    {
      return sURL;
    }
  }
}