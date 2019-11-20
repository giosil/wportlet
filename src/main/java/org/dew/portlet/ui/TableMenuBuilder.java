package org.dew.portlet.ui;

import java.util.Map;

/**
 * Classe che estende AMenuBuilder e che permette di costruire un menu a due livelli costituito da una table.
 */
@SuppressWarnings("rawtypes")
public
class TableMenuBuilder extends AMenuBuilder
{
	protected
	void beginMenu(Map mapMenu)
	{
		_sToString += "<table>\n";
	}
	
	protected
	void addNode(int iIndex, int iIdNode, int iIdParent, int iLevel, int iDepth, String sDescription, String sURL)
	{
		if(iIndex == 0) return;
		
		if(sURL == null) {
			_sToString += "<tr " + WebUtil.sCLASS_TR_HEADER + ">";
			_sToString += "<th>" + sDescription + "</th>";
			_sToString += "</tr>\n";
		}
		else {
			_sToString += "<tr " + WebUtil.sCLASS_TR_EVEN + "align=\"right\">";
			if(_iLastItemSelected == iIdNode) {
				_sToString += "<td><strong><a href=\"" + sURL + "\">" + sDescription + "</a></strong></td>";
			}
			else {
				_sToString += "<td><a href=\"" + sURL + "\">" + sDescription + "</a></td>";
			}
			_sToString += "</tr>\n";
		}
	}
	
	protected
	void endNode(int iIndex, int iIdNode, int iIdParent, int iLevel, int iDepth, String sDescription, String sURL)
	{
	}
	
	protected
	void endMenu()
	{
		_sToString += "</table>\n";
	}
}
