package org.dew.portlet.ui;

import java.util.Map;

/**
 * Classe che estende AMenuBuilder e che permette di costruire un semplice menu a piu' livelli.
 */
@SuppressWarnings({"rawtypes"})
public
class SimpleMenuBuilder extends AMenuBuilder
{
  protected
  void beginMenu(Map mapMenu)
  {
  }
  
  protected
  void addNode(int iIndex, int iIdNode, int iIdParent, int iLevel, int iDepth, String sDescription, String sURL)
  {
    String sIndent = "";
    for(int i = 0; i < iLevel; i++) {
      sIndent += "&nbsp;&nbsp;&nbsp;";
    }
    
    if(sURL == null) {
      _sToString += sIndent + sDescription + "<br />";
    }
    else {
      if(_iLastItemSelected == iIdNode) {
        _sToString += sIndent + "<strong><a href=\"" + sURL + "\">" + sDescription + "</a></strong><br />";
      }
      else {
        _sToString += sIndent + "<a href=\"" + sURL + "\">" + sDescription + "</a><br />";
      }
    }
  }
  
  protected
  void endNode(int iIndex, int iIdNode, int iIdParent, int iLevel, int iDepth, String sDescription, String sURL)
  {
  }
  
  protected
  void endMenu()
  {
  }
}
