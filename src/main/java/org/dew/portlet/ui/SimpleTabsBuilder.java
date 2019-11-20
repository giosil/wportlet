package org.dew.portlet.ui;

import java.util.Map;

/**
 * Classe che estende ATabsBuilder e che permette di costruire un semplice tabs.
 */
@SuppressWarnings({"rawtypes"})
public 
class SimpleTabsBuilder extends ATabsBuilder 
{
	protected 
	void beginTabs(Map mapTabs) 
	{
	}
	
	protected 
	void addTab(int iIndex, String sDescription, String sURL) 
	{
		if(iIndex > 0) {
			_sToString += " | ";
		}
		if(iIndex == _iLastItemSelected) {
			_sToString += "<strong><a href=\"" +  sURL + "\">" + sDescription + "</a></strong>";
		}
		else {
			_sToString += "<a href=\"" +  sURL + "\">" + sDescription + "</a>";
		}
	}
	
	protected 
	void endTabs() 
	{
	}
}
