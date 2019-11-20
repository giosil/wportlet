package org.dew.portlet.ui;

import java.util.Map;

/**
 * Classe che estende ATabsBuilder e che permette di costruire un tabs costituito da una table.
 */
@SuppressWarnings("rawtypes")
public 
class TableTabsBuilder extends ATabsBuilder 
{
  protected 
  void beginTabs(Map mapTabs) 
  {
    _sToString += "<table cellspacing=\"4\">\n";
    _sToString += "<tr>";
  }
  
  protected 
  void addTab(int iIndex, String sDescription, String sURL) 
  {
    if(iIndex == _iLastItemSelected) {
      _sToString += "<td " + WebUtil.sCLASS_TR_ODD + "><strong><a href=\"" +  sURL + "\">" + sDescription + "</a></strong></td>";
    }
    else {
      _sToString += "<td " + WebUtil.sCLASS_TR_EVEN + "><a href=\"" +  sURL + "\">" + sDescription + "</a></td>";
    }
  }
  
  protected 
  void endTabs() 
  {
    _sToString += "</tr>\n";
    _sToString += "</table>\n";
  }
}
