package org.dew.portlet.ui;

import java.util.Map;

/**
 * Classe che estende ATabsBuilder e che permette di costruire un
 * tabs secondo il css definito nel portale Liferay.
 */
@SuppressWarnings({"rawtypes"})
public 
class LiferayTabsBuilder extends ATabsBuilder 
{
  protected 
  void beginTabs(Map mapTabs) 
  {
    // _sToString += "<ul class=\"tabs\">";      // 4.0, 5.0
    _sToString += "<ul class=\"tabs ui-tabs\">"; // 5.1
  }
  
  protected 
  void addTab(int iIndex, String sDescription, String sURL) 
  {
    if(iIndex == _iLastItemSelected) {
      _sToString += "<li class=\"current\">";
      _sToString += "<a href=\"" +  sURL + "\">" + sDescription + "</a>";
      _sToString += "</li>";
    }
    else {
      _sToString += "<li>";
      _sToString += "<a href=\"" +  sURL + "\">" + sDescription + "</a>";
      _sToString += "</li>";
    }
  }
  
  protected 
  void endTabs() 
  {
    _sToString += "</ul>";
  }
}

