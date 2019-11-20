package org.dew.portlet.ui;

import java.text.MessageFormat;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.*;
import javax.portlet.*;

import org.dew.portlet.*;

/**
 * Classe di utilita' per lo sviluppo rapido di JSP.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public 
class WebUtil 
{
	/**
	 * Classe di stile della sezione result (tabella).
	 */
	public static String sCLASS_RES_GRID = ""; // Div
	/**
	 * Style della sezione result (tabella).
	 */
	public static String sSTYLE_RES_GRID = ""; // Div
	/**
	 * Classe di stile delle tabelle.
	 */
	public static String sCLASS_TABLE = ""; // Tabella
	/**
	 * Classe di stile dell'intestazione delle tabelle.
	 */
	public static String sCLASS_TR_HEADER = ""; // Intestazione
	/**
	 * Classe di stile delle righe dispari delle tabelle.
	 */
	public static String sCLASS_TR_ODD    = ""; // Dispari
	/**
	 * Classe di stile delle righe pari delle tabelle.
	 */
	public static String sCLASS_TR_EVEN   = ""; // Pari
	/**
	 * Root del portale.
	 */
	public static String sPORTAL_ROOT     = "/web/guest";
	/**
	 * Classe di stile per le celle da far "scomparire" in caso di visualizzazione mobile.
	 */
	public static String sCLASS_RESPONSIVE_CELL_HIDE = "class=\"res-cell-hide\"";
	
	/**
	 * Inizializzazione per la definizione delle classi di stile.
	 * 
	 * @param portletConfig
	 */
	public static 
	void init(PortletConfig portletConfig)
	{
		if(ResourcesMgr.sPORTAL_PLATFORM.equals("liferay") || ResourcesMgr.sPORTAL_PLATFORM.equals("sun")) {
			String sLiferayHome = System.getProperty("liferay.home");
			if(sLiferayHome != null && sLiferayHome.indexOf("6.1") >= 0) {
				sSTYLE_RES_GRID  = "style=\"overflow:auto;\"";
				sCLASS_RES_GRID  = "class=\"results-grid aui-searchcontainer-content\"";
				sCLASS_TABLE     = "class=\"taglib-search-iterator\"";
				sCLASS_TR_HEADER = "class=\"portlet-section-header results-header\"";
				sCLASS_TR_ODD    = "class=\"results-row lfr-role portlet-section-alternate-hover\"";
				sCLASS_TR_EVEN   = "class=\"results-row alt lfr-role lfr-role-regular portlet-section-alternate-hover\"";
			}
			else {
				sSTYLE_RES_GRID  = "style=\"overflow:auto;\"";
				sCLASS_RES_GRID  = "class=\"table-responsive\"";
				sCLASS_TABLE     = "class=\"table table-bordered table-striped\"";
				sCLASS_TR_HEADER = "";
				sCLASS_TR_ODD    = "";
				sCLASS_TR_EVEN   = "";
			}
			
			sPORTAL_ROOT     = "/web/guest"; 
		}
		else {
			sSTYLE_RES_GRID  = null;
			sCLASS_RES_GRID  = null;
			sCLASS_TABLE     = "";
			sCLASS_TR_HEADER = "class=\"portlet-section-header\"";
			sCLASS_TR_ODD    = "class=\"portlet-section-body\"";
			sCLASS_TR_EVEN   = "class=\"portlet-section-alternate\"";
			
			sPORTAL_ROOT     = "/";
		}
	}
	
	/**
	 * Restituisce il namespace della portlet.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getNamespace(HttpServletRequest request)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return null;
		return renderResponse.getNamespace();
	}
	
	/**
	 * Restituisce l'action URL della portlet.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getActionURL(HttpServletRequest request)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return "";
		PortletURL portletURL = renderResponse.createActionURL();
		return portletURL.toString();
	}
	
	/**
	 * Restituisce l'action URL della portlet.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getLogoutURL(HttpServletRequest request)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return "";
		PortletURL portletURL = renderResponse.createActionURL();
		portletURL.setParameter(WNames.sPAR_ACTION, "logout");
		try {
			portletURL.setWindowState(WindowState.NORMAL);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return portletURL.toString();
	}
	
	/**
	 * Restituisce il link con label="Indietro" relativo all'ultima azione di forward. 
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getLastForwardLink(HttpServletRequest request)
	{
		String sURL = (String) request.getAttribute(WNames.sATTR_LAST_FORWARD_URL);
		if(sURL == null || sURL.length() == 0) {
			return "";
		}
		return "<a href=\"" + sURL + "\" title=\"Indietro\">Indietro</a>";
	}
	
	/**
	 * Restituisce il link con la label specificata relativo all'ultima azione di forward. 
	 * 
	 * @param request HttpServletRequest
	 * @param sLabel Label del link
	 * @return String
	 */
	public static
	String getLastForwardLink(HttpServletRequest request, String sLabel)
	{
		String sURL = (String) request.getAttribute(WNames.sATTR_LAST_FORWARD_URL);
		if(sURL == null || sURL.length() == 0) {
			return "";
		}
		return "<a href=\"" + sURL + "\" title=\"" + sLabel + "\">" + sLabel + "</a>";
	}
	
	/**
	 * Restituisce la URL relativa all'ultima azione di forward.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getLastForwardURL(HttpServletRequest request)
	{
		return (String) request.getAttribute(WNames.sATTR_LAST_FORWARD_URL);
	}
	
	/**
	 * Restituisce la URL relativa all'ultima azione eseguita.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getLastActionURL(HttpServletRequest request)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return null;
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return null;
		return (String) parameters.getActionURL(renderResponse);
	}
	
	/**
	 * Restituisce la URL relativa all'ultima azione eseguita.
	 * 
	 * @param request HttpServletRequest
	 * @param sAdditionalParameters Parametri addizionali (es. nome=MARIO&cognome=ROSSI)
	 * @return String
	 */
	public static
	String getLastActionURL(HttpServletRequest request, String sAdditionalParameters)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return null;
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return null;
		return (String) parameters.getActionURL(renderResponse, sAdditionalParameters);
	}
	
	/**
	 * Restituisce il corpo della pagina HTML specificata nel parametro sHTMLFile.
	 * 
	 * @param request HttpServletRequest
	 * @param sHTMLFile file html
	 * @return String
	 */
	public static
	String loadHTML(HttpServletRequest request, String sHTMLFile)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return "Attribute <strong>" + WNames.sATTR_RENDER_RESPONSE + "</strong> is null";
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return "Attribute <strong>" + WNames.sATTR_PARAMETERS + "</strong> is null";
		return ResourcesMgr.loadHTML(parameters, sHTMLFile, renderResponse);
	}
	
	/**
	 * Costruisce una tabella HTML dai dati forniti in oValues.
	 * I dati possono essere Collection di Collection oppure un oggetto Map.
	 * 
	 * @param sId Identificativo della tabella
	 * @param oValues Valori, Se null restituisce una stringa vuota
	 * @param boContainsHeader Flag che indica la presenza dell'intestazione
	 * @return String
	 */
	public static
	String buildTable(String sId, Object oValues, boolean boContainsHeader)
	{
		if(oValues == null) return "";
		StringBuffer sb = new StringBuffer();
		if(sCLASS_RES_GRID != null) {
			if(sSTYLE_RES_GRID != null && sSTYLE_RES_GRID.length() > 0) {
				sb.append("<div " + sCLASS_RES_GRID + " " + sSTYLE_RES_GRID + ">");
			}
			else {
				sb.append("<div " + sCLASS_RES_GRID + ">");
			}
		}
		if(sId != null && sId.length() > 0) {
			sb.append("<table id=\"" + sId + "\" " + sCLASS_TABLE + ">");
		}
		else {
			sb.append("<table " + sCLASS_TABLE + ">");
		}
		sb.append(buildTableContent(oValues, true));
		sb.append("</table>");
		if(sCLASS_RES_GRID != null) {
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * Costruisce una tabella HTML dai dati presenti nell'attributo
	 * della richiesta individuato da sKey.
	 * I dati possono essere Collection di Collection oppure un oggetto Map.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return String
	 */
	public static
	String buildTable(HttpServletRequest request, String sKey)
	{
		Object oValues = request.getAttribute(sKey);
		if(oValues instanceof String) {
			return (String) oValues;
		}
		StringBuffer sb = new StringBuffer();
		if(sCLASS_RES_GRID != null) {
			if(sSTYLE_RES_GRID != null && sSTYLE_RES_GRID.length() > 0) {
				sb.append("<div " + sCLASS_RES_GRID + " " + sSTYLE_RES_GRID + ">");
			}
			else {
				sb.append("<div " + sCLASS_RES_GRID + ">");
			}
		}
		sb.append("<table " + sCLASS_TABLE + ">");
		sb.append(buildTableContent(oValues, true));
		sb.append("</table>");
		if(sCLASS_RES_GRID != null) {
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * Costruisce una tabella HTML dai dati presenti nell'attributo
	 * della richiesta individuato da sKey.
	 * I dati possono essere Collection di Collection oppure un oggetto Map.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param boContainsHeader Flag che indica la presenza dell'intestazione
	 * @return String
	 */
	public static
	String buildTable(HttpServletRequest request, String sKey, boolean boContainsHeader)
	{
		Object oValues = request.getAttribute(sKey);
		if(oValues instanceof String) {
			return (String) oValues;
		}
		StringBuffer sb = new StringBuffer();
		if(sCLASS_RES_GRID != null) {
			if(sSTYLE_RES_GRID != null && sSTYLE_RES_GRID.length() > 0) {
				sb.append("<div " + sCLASS_RES_GRID + " " + sSTYLE_RES_GRID + ">");
			}
			else {
				sb.append("<div " + sCLASS_RES_GRID + ">");
			}
		}
		sb.append("<table " + sCLASS_TABLE + ">");
		sb.append(buildTableContent(oValues, boContainsHeader));
		sb.append("</table>");
		if(sCLASS_RES_GRID != null) {
			sb.append("</div>");
		}
		return sb.toString();
	}
	
	/**
	 * Costruisce una tabella HTML dai dati presenti nell'attributo
	 * della richiesta individuato da sKey.
	 * I dati possono essere Collection di Collection oppure un oggetto Map.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return String
	 */
	public static
	String buildTableContent(HttpServletRequest request, String sKey)
	{
		Object oValues = request.getAttribute(sKey);
		if(oValues instanceof String) {
			return (String) oValues;
		}
		return buildTableContent(oValues, true);
	}
	
	/**
	 * Costruisce una tabella HTML dai dati presenti nell'attributo
	 * della richiesta individuato da sKey.
	 * I dati possono essere Collection di Collection oppure un oggetto Map.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param boContainsHeader Flag che indica la presenza dell'intestazione
	 * @return String
	 */
	public static
	String buildTableContent(HttpServletRequest request, String sKey, boolean boContainsHeader)
	{
		Object oValues = request.getAttribute(sKey);
		if(oValues instanceof String) {
			return (String) oValues;
		}
		return buildTableContent(oValues, boContainsHeader);
	}
	
	/**
	 * Costruisce una tabella HTML dai dati presenti nel parametro oValues.
	 * Esso puo' Collection di Collection oppure un oggetto Map.
	 * 
	 * @param oValues Object
	 * @param boContainsHeader Flag che indica la presenza dell'intestazione
	 * @return String
	 */
	public static
	String buildTableContent(Object oValues, boolean boContainsHeader)
	{
		Set<Integer> setOfResponsiveCols = new HashSet<Integer>();
		StringBuffer sb = new StringBuffer();
		int iRow = boContainsHeader ? 0 : 1;
		boolean body = false;
		if(oValues instanceof Collection) {
			String sTR, sTD1, sTD2;
			Collection colRows = (Collection) oValues;
			for (Object oRow : colRows) {
				sTD1 = "<td";
				sTD2 = "</td>";
				if(iRow == 0) {
					if(boContainsHeader) {
						sTR = "<tr " + sCLASS_TR_HEADER + ">";
						sTD1 = "<th";
						sTD2 = "</th>";
					}
					else {
						sTR = "<tr " + sCLASS_TR_EVEN + ">";
					}
				}
				else 
				if((iRow % 2) == 0) {
					sTR = "<tr " + sCLASS_TR_EVEN + ">";
				}
				else {
					sTR = "<tr " + sCLASS_TR_ODD + ">";
				}
				if(iRow == 0 && boContainsHeader) {
					sb.append("<thead>");
				}
				else
				if(iRow == 1) {
					sb.append("<tbody>");
					body = true;
				}
				sb.append(sTR);
				if(oRow instanceof Collection) {
					int index = -1;
					Collection colData = (Collection) oRow;
					for (Object oData : colData) {
						index++;
						String sTagTD = sTD1;
						if(setOfResponsiveCols.contains(index)) {
							sTagTD = sTD1 + " " + sCLASS_RESPONSIVE_CELL_HIDE;
						}
						if(oData == null) {
							sb.append(sTagTD + ">&nbsp;" + sTD2);
						}
						else
						if(oData instanceof String) {
							String sData = (String) oData;
							if(sData.length() == 0) {
								sb.append(sTagTD + ">&nbsp;" + sTD2);
							}
							else {
								if(sData.endsWith("^")) {
									sData = sData.substring(0, sData.length()-1);
									setOfResponsiveCols.add(index);
									sTagTD = sTD1 + " " + sCLASS_RESPONSIVE_CELL_HIDE;
								}
								if(sData.startsWith("<center>") && sData.endsWith("</center>")) {
									int iEnd = sData.lastIndexOf('<');
									if(iEnd <= 0) iEnd = sData.length();
									sData = sData.substring(8, iEnd);
									if(sData.length() == 0 || sData.equals("null")) {
										sb.append(sTagTD + " style=\"text-align: center;\">&nbsp;" + sTD2);
									}
									else {
										sb.append(sTagTD + " style=\"text-align: center;\">" + sData + sTD2);
									}
								}
								else
								if(sData.startsWith("<input ")) {
									sb.append(sTagTD + " style=\"text-align: center;\">" + sData + sTD2);
								}
								else
								if(sData.indexOf("<img") >= 0 && sData.endsWith(">")) {
									sb.append(sTagTD + " style=\"text-align: center;\">" + sData + sTD2);
								}
								else {
									sb.append(sTagTD + " style=\"text-align: left;\">" + sData + sTD2);
								}
							}
						}
						else
						if(oData instanceof Number) {
							if(oData instanceof Double) {
								sb.append(sTagTD + " style=\"text-align: right;\">" + formatDouble(oData) + sTD2);
							}
							else {
								sb.append(sTagTD + " style=\"text-align: right;\">" + oData + sTD2);
							}
						}
						else
						if(oData instanceof Date) {
							sb.append(sTagTD + " style=\"text-align: left;\">" + formatDate((Date) oData) + sTD2);
						}
						else
						if(oData instanceof Calendar) {
							sb.append(sTagTD + " style=\"text-align: left;\">" + formatDate((Calendar) oData) + sTD2);
						}
						else {
							sb.append(sTagTD + " style=\"text-align: left;\">" + oData + sTD2);
						}
					}
				}
				else {
					if(oRow == null) {
						sb.append(sTD1 + ">&nbsp;" + sTD2);
					}
					else
					if(oRow instanceof String) {
						String sRow = (String) oRow;
						if(sRow.length() == 0) {
							sb.append(sTD1 + ">&nbsp;" + sTD2);
						}
						else {
							if(sRow.startsWith("<center>") && sRow.endsWith("</center>")) {
								int iEnd = sRow.lastIndexOf('<');
								if(iEnd <= 0) iEnd = sRow.length();
								sRow = sRow.substring(8, iEnd);
								if(sRow.length() == 0 || sRow.equals("null")) {
									sb.append(sTD1 + " style=\"text-align: center;\">&nbsp;" + sTD2);
								}
								else {
									sb.append(sTD1 + " style=\"text-align: center;\">" + sRow + sTD2);
								}
							}
							else
							if(sRow.startsWith("<input ")) {
								sb.append(sTD1 + " style=\"text-align: center;\">" + sRow + sTD2);
							}
							else
							if(sRow.indexOf("<img") >= 0 && sRow.endsWith(">")) {
								sb.append(sTD1 + " style=\"text-align: center;\">" + sRow + sTD2);
							}
							else {
								sb.append(sTD1 + " style=\"text-align: left;\">" + sRow + sTD2);
							}
						}
					}
					else
					if(oRow instanceof Number) {
						sb.append(sTD1 + " style=\"text-align: right;\">" + oRow + sTD2);
					}
					else
					if(oRow instanceof Date) {
						sb.append(sTD1 + " style=\"text-align: left;\">" + formatDate((Date) oRow) + sTD2);
					}
					else
					if(oRow instanceof Calendar) {
						sb.append(sTD1 + " style=\"text-align: left;\">" + formatDate((Calendar) oRow) + sTD2);
					}
					else {
						sb.append(sTD1 + " style=\"text-align: left;\">" + oRow + sTD2);
					}
				}
				sb.append("</tr>");
				if(iRow == 0 && boContainsHeader) {
					sb.append("</thead>");
				}
				iRow++;
			}
		}
		else
		if(oValues instanceof Map) {
			Map map = (Map) oValues;
			List listKeys = new ArrayList();
			Iterator itKeys = map.keySet().iterator();
			while(itKeys.hasNext()) {
				String sKeyMap = (String) itKeys.next();
				listKeys.add(sKeyMap);
			}
			Collections.sort(listKeys);
			if(boContainsHeader) {
				sb.append("<thead>");
				sb.append("<tr " + sCLASS_TR_HEADER + "><th>Chiave</th><th>Valore</th></tr>");
				sb.append("</thead>");
			}
			sb.append("<tbody>");
			body = true;
			for(int i = 0; i < listKeys.size(); i++) {
				String sKeyMap = (String) listKeys.get(i);
				Object oData   = map.get(sKeyMap);
				if((iRow % 2) == 0) {
					sb.append("<tr " + sCLASS_TR_EVEN + ">");
				}
				else {
					sb.append("<tr " + sCLASS_TR_ODD + ">");
				}
				sb.append("<td align=\"right\">" + sKeyMap + "</td>");
				if(oData == null) {
					sb.append("<td>&nbsp;</td>");
				}
				else
				if(oData instanceof Number) {
					sb.append("<td align=\"right\">" + oData + "</td>");
				}
				else
				if(oData instanceof Date) {
					sb.append("<td align=\"left\">" + formatDate((Date) oData) + "</td>");
				}
				else
				if(oData instanceof Calendar) {
					sb.append("<td align=\"left\">" + formatDate((Calendar) oData) + "</td>");
				}
				else {
					sb.append("<td align=\"left\">" + oData + "</td>");
				}
				sb.append("</tr>");
				iRow++;
			}
		}
		if(body) {
			sb.append("</tbody>");
		}
		else {
			sb.append("<tbody></tbody>");
		}
		return sb.toString();
	}
	
	/**
	 * Costruisce una tabella HTML dai dati presenti nell'attributo
	 * della richiesta individuato da sKey.
	 * I dati devono essere Collection di Map.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo della richiesta
	 * @param asSymbolics Array di chiavi degli oggetti Map
	 * @param asColNames Array nomi colonne
	 * @return String
	 */
	public static
	String buildTableContent(HttpServletRequest request, String sKey, String[] asSymbolics, String[] asColNames)
	{
		StringBuffer sb = new StringBuffer();
		if(asColNames != null && asColNames.length > 0) {
			sb.append("<thead>");
			sb.append("<tr " + sCLASS_TR_HEADER + ">");
			for (String sColName : asColNames) {
				sb.append("<th>" + sColName + "</th>");
			}
			sb.append("</tr>");
			sb.append("</thead>");
		}
		sb.append("<tbody>");
		Object oValues = request.getAttribute(sKey);
		if(oValues instanceof Collection) {
			Collection colRows = (Collection) oValues;
			int iRow = 1;
			String sTR;
			for (Object oRow : colRows) {
				if((iRow % 2) == 0) {
					sTR = "<tr " + sCLASS_TR_EVEN + ">";
				}
				else {
					sTR = "<tr " + sCLASS_TR_ODD + ">";
				}
				sb.append(sTR);
				if(oRow instanceof Map) {
					Map map = (Map) oRow;
					for (String sSymbolic : asSymbolics) {
						Object oData = map.get(sSymbolic);
						if(oData == null) {
							sb.append("<td>&nbsp;</td>");
						}
						else
						if(oData instanceof Number) {
							sb.append("<td align=\"right\">" + oData + "</td>");
						}
						else
						if(oData instanceof Date) {
							sb.append("<td align=\"left\">" + formatDate((Date) oData) + "</td>");
						}
						else
						if(oData instanceof Calendar) {
							sb.append("<td align=\"left\">" + formatDate((Calendar) oData) + "</td>");
						}
						else {
							sb.append("<td align=\"left\">" + oData + "</td>");
						}
					}
				}
				sb.append("</tr>");
				iRow++;
			}
		}
		sb.append("</tbody>");
		return sb.toString();
	}
	
	/**
	 * Costruisce la lista di opzioni di un select HTML (casella combinata).
	 * I dati devono essere Collection o Collection di List.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo della richiesta
	 * @return String
	 */
	public static
	String buildOptions(HttpServletRequest request, String sKey)
	{
		Object oValues = request.getAttribute(sKey);
		if(oValues instanceof Collection) {
			return buildOptions((Collection) oValues, null);
		}
		return "";
	}
	
	/**
	 * Costruisce la lista di opzioni di un select HTML (casella combinata).
	 * I dati devono essere Collection o Collection di List.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo della richiesta
	 * @param oSelected Elemento selezionato
	 * @return String
	 */
	public static
	String buildOptions(HttpServletRequest request, String sKey, Object oSelected)
	{
		Object oValues = request.getAttribute(sKey);
		if(oValues instanceof Collection) {
			return buildOptions((Collection) oValues, oSelected);
		}
		return "";
	}
	
	/**
	 * Costruisce la lista di opzioni di un select HTML (casella combinata).
	 * 
	 * @param colRows Collection
	 * @param oSelected Object
	 * @return String
	 */
	public static
	String buildOptions(Collection colRows, Object oSelected)
	{
		StringBuffer sb = new StringBuffer();
		for (Object oRow : colRows) {
			if(oRow instanceof List) {
				List list = (List) oRow;
				String sSelected = "";
				if(oSelected != null && oSelected.equals(list.get(0))) {
					sSelected = " selected";
				}
				if(list.size() > 1) {
					sb.append("<option value=\"" + list.get(0) +"\"" + sSelected + ">" + list.get(1) + "</option>");
				}
				else {
					sb.append("<option" + sSelected + ">" + list.get(0) + "</option>");
				}
			}
			else {
				if(oSelected != null && oSelected.equals(oRow)) {
					sb.append("<option selected>" + oRow + "</option>");
				}
				else {
					sb.append("<option>" + oRow + "</option>");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Verifica che l'attributo della richiesta individuato da sKey
	 * sia un oggetto non vuoto (diverso da null e la rappresentazione
	 * stringa deve avere lunghezza > 0).
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo della richiesta
	 * @return boolean
	 */
	public static
	boolean check(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		if(oValue == null) {
			return false;
		}
		if(oValue.toString().trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Verifica che vi sia un messaggio.
	 * 
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	public static
	boolean checkMessage(HttpServletRequest request)
	{
		Object oMessage = request.getAttribute(WNames.sATTR_MESSAGE);
		if(oMessage == null) {
			return false;
		}
		if(oMessage.toString().trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Restituisce il messaggio. Se null restituisce una stringa vuota.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getMessage(HttpServletRequest request)
	{
		Object oMessage = request.getAttribute(WNames.sATTR_MESSAGE);
		if(oMessage == null) return "";
		return oMessage.toString();
	}
	
	/**
	 * Restituisce un flag che indica se il messaggio deriva da una WarningException.
	 * 
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	public static
	boolean isWarning(HttpServletRequest request)
	{
		Object oIsWarning = request.getAttribute(WNames.sATTR_IS_WARNING);
		if(oIsWarning == null) return false;
		if(oIsWarning instanceof Boolean) {
			return ((Boolean) oIsWarning).booleanValue();
		}
		return false;
	}
	
	/**
	 * Restituisce un messaggio ottenuto dal ResourceBundle secondo
	 * il Locale della request.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave del messaggio
	 * @return String
	 */
	public static
	String getMessage(HttpServletRequest request, String sKey)
	{
		String sResult = null;
		try {
			ResourceBundle resourceBundle = ResourcesMgr.getBoundle(request.getLocale());
			sResult = resourceBundle.getString(sKey);
		}
		catch(Exception ex) {
		}
		if(sResult == null) sResult = "";
		return sResult;
	}
	
	/**
	 * Restituisce un messaggio ottenuto dal ResourceBundle secondo
	 * il Locale della request.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave del messaggio
	 * @param sDefault Stringa di default in caso di chiave inesistente
	 * @return String
	 */
	public static
	String getMessage(HttpServletRequest request, String sKey, String sDefault)
	{
		String sResult = null;
		try {
			ResourceBundle resourceBundle = ResourcesMgr.getBoundle(request.getLocale());
			sResult = resourceBundle.getString(sKey);
		}
		catch(Exception ex) {
		}
		if(sResult == null) return sDefault;
		return sResult;
	}
	
	/**
	 * Restituisce un messaggio formattato ottenuto dal ResourceBundle secondo
	 * il Locale della request.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave del messaggio
	 * @param asParams Array di parametri
	 * @return String
	 */
	public static
	String getMessage(HttpServletRequest request, String sKey, Object[] asParams)
	{
		String sResult = null;
		try {
			ResourceBundle resourceBundle = ResourcesMgr.getBoundle(request.getLocale());
			String sMessage = resourceBundle.getString(sKey);
			sResult = MessageFormat.format(sMessage, asParams);
		}
		catch(Exception ex) {
		}
		if(sResult == null) sResult = "";
		return sResult;
	}
	
	/**
	 * Restituisce l'oggetto dall'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param classOfResult Classe dell'oggetto
	 * @return ogetto
	 */
	public static <T>
	T getObject(HttpServletRequest request, String sKey, Class<T> classOfResult)
	{
		Object oValue = request.getAttribute(sKey);
		if(oValue == null) return null;
		if(classOfResult.isAssignableFrom(oValue.getClass())) {
			return (T) oValue;
		}
		return null;
	}
	
	/**
	 * Restituisce l'attributo della sessione (request.getSession() e NON PortletSession) individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return String
	 */
	public static
	String getHttpSessionAttr(HttpServletRequest request, String sKey)
	{
		HttpSession httpSession = request.getSession();
		if(httpSession == null) {
			return null;
		}
		return Parameters.toString(httpSession.getAttribute(sKey), "");
	}
	
	/**
	 * Restituisce la rappresentazione string dell'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return String
	 */
	public static
	String getString(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		return Parameters.toString(oValue, "");
	}
	
	/**
	 * Restituisce la rappresentazione string dell'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param sDefault Stringa di default
	 * @return String
	 */
	public static
	String getString(HttpServletRequest request, String sKey, String sDefault)
	{
		Object oValue = request.getAttribute(sKey);
		return Parameters.toString(oValue, sDefault);
	}
	
	/**
	 * Restituisce un intero dall'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return int
	 */
	public static
	int getInt(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		return Parameters.toInt(oValue, 0);
	}
	
	/**
	 * Restituisce un double dall'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return double
	 */
	public static
	double getDouble(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		return Parameters.toDouble(oValue, 0);
	}
	
	/**
	 * Restituisce un boolean dall'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return boolean
	 */
	public static
	boolean getBoolean(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		return Parameters.toBoolean(oValue, false);
	}
	
	/**
	 * Restituisce una Lista dall'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return List
	 */
	public static
	List getList(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		return Parameters.toList(oValue, true);
	}
	
	/**
	 * Restituisce la rappresentazione string dell'attributo della richiesta individuato da sKey.
	 * Nel caso in cui non e' vuota vengono restituiti anche in apertura e chiusura rispettivamente sLeftTag e sRightTag.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param sLeftTag Tag di apertura
	 * @param sRightTag Tag di chiusura
	 * @return String
	 */
	public static
	String getString(HttpServletRequest request, String sKey, String sLeftTag, String sRightTag)
	{
		Object oValue = request.getAttribute(sKey);
		if(oValue == null) return "";
		if(sLeftTag  == null) sLeftTag  = "";
		if(sRightTag == null) sRightTag = "";
		String s = Parameters.toString(oValue, "");
		if(s.length() == 0) return "";
		return sLeftTag + s + sRightTag;
	}
	
	/**
	 * Restituisce la rappresentazione oraria dell'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return String
	 */
	public static
	String getTimeString(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		if(oValue == null) {
			return "";
		}
		else
		if(oValue instanceof Date) {
			return formatTime((Date) oValue);
		}
		else
		if(oValue instanceof Calendar) {
			return formatTime((Calendar) oValue);
		}
		return formatTime(oValue.toString());
	}
	
	/**
	 * Restituisce la rappresentazione oraria dell'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param sDefault Stringa di default
	 * @return String
	 */
	public static
	String getTimeString(HttpServletRequest request, String sKey, String sDefault)
	{
		Object oValue = request.getAttribute(sKey);
		if(oValue == null) {
			return sDefault;
		}
		else
		if(oValue instanceof Date) {
			return formatTime((Date) oValue);
		}
		else
		if(oValue instanceof Calendar) {
			return formatTime((Calendar) oValue);
		}
		return formatTime(oValue.toString());
	}
	
	/**
	 * Restituisce la rappresentazione valuta dell'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return String
	 */
	public static
	String getCurrencyString(HttpServletRequest request, String sKey)
	{
		Object oValue = request.getAttribute(sKey);
		return formatDouble(oValue);
	}
	
	/**
	 * Restituisce la rappresentazione valuta dell'attributo della richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param sDefault Stringa di default
	 * @return String
	 */
	public static
	String getCurrencyString(HttpServletRequest request, String sKey, String sDefault)
	{
		Object oValue = request.getAttribute(sKey);
		if(oValue == null) return sDefault;
		return formatDouble(oValue);
	}
	
	/**
	 * Verifica la presenza di un attributo di sessione individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return boolean
	 */
	public static
	boolean checkSessionAttribute(HttpServletRequest request, String sKey)
	{
		RenderRequest renderRequest = (RenderRequest) request.getAttribute(WNames.sATTR_RENDER_REQUEST);
		if(renderRequest == null) return false;
		PortletSession portletSession = renderRequest.getPortletSession();
		Object oAttribute = portletSession.getAttribute(sKey);
		return oAttribute != null;
	}
	
	/**
	 * Restituisce un attributo di sessione individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @return Object
	 */
	public static
	Object getSessionAttribute(HttpServletRequest request, String sKey)
	{
		RenderRequest renderRequest = (RenderRequest) request.getAttribute(WNames.sATTR_RENDER_REQUEST);
		if(renderRequest == null) return null;
		PortletSession portletSession = renderRequest.getPortletSession();
		Object oAttribute = portletSession.getAttribute(sKey);
		return oAttribute;
	}
	
	/**
	 * Restituisce un attributo di sessione individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave attributo
	 * @param oDefault Oggetto di default
	 * @return Object
	 */
	public static
	Object getSessionAttribute(HttpServletRequest request, String sKey, Object oDefault)
	{
		RenderRequest renderRequest = (RenderRequest) request.getAttribute(WNames.sATTR_RENDER_REQUEST);
		if(renderRequest == null) return oDefault;
		PortletSession portletSession = renderRequest.getPortletSession();
		Object oAttribute = portletSession.getAttribute(sKey);
		if(oAttribute == null) return oDefault;
		return oAttribute;
	}
	
	/**
	 * Restituisce un attributo di sessione individuato da sKey.
	 * La sessione a cui ci si riferisce e' quella costruita dal Portal Container.
	 * 
	 * @param httpSession HttpSession
	 * @param sKey Chiave attributo
	 * @return Object
	 */
	public static
	Object getSessionAttribute(HttpSession httpSession, String sKey)
	{
		if(httpSession == null) return null;
		Enumeration<String> enumeration = httpSession.getAttributeNames();
		while(enumeration.hasMoreElements()) {
			String sAttributeName = enumeration.nextElement();
			if(sAttributeName.endsWith(sKey)) {
				return httpSession.getAttribute(sAttributeName);
			}
		}
		return null;
	}
	
	/**
	 * Restituisce un attributo di sessione individuato da sKey.
	 * La sessione a cui ci si riferisce e' quella costruita dal Portal Container.
	 * 
	 * @param httpSession HttpSession
	 * @param sKey Chiave attributo
	 * @param oDefault Oggetto di default
	 * @return Object
	 */
	public static
	Object getSessionAttribute(HttpSession httpSession, String sKey, Object oDefault)
	{
		if(httpSession == null) return oDefault;
		Enumeration<String> enumeration = httpSession.getAttributeNames();
		while(enumeration.hasMoreElements()) {
			String sAttributeName = enumeration.nextElement();
			if(sAttributeName.endsWith(sKey)) {
				return httpSession.getAttribute(sAttributeName);
			}
		}
		return oDefault;
	}
	
	/**
	 * Restituisce il riferimento all'oggetto PortletSession a partire da una request.
	 * 
	 * @param request (HttpServletRequest, ActionRequest, RenderRequest)
	 * @return PortletSession
	 */
	public static
	PortletSession getPortletSession(Object request)
	{
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			RenderRequest renderRequest = (RenderRequest) httpServletRequest.getAttribute(WNames.sATTR_RENDER_REQUEST);
			if(renderRequest == null) return null;
			return renderRequest.getPortletSession();
		}
		else
		if(request instanceof ActionRequest) {
			return ((ActionRequest) request).getPortletSession();
		}
		else
		if(request instanceof RenderRequest) {
			return ((RenderRequest) request).getPortletSession();
		}
		else
		if(request instanceof Parameters) {
			return ((Parameters) request).getPortletSession();
		}
		return null;
	}
	
	/**
	 * Restituisce il riferimento all'oggetto User a partire da una request.
	 * 
	 * @param request (HttpServletRequest, ActionRequest, RenderRequest, Parameters)
	 * @return PortletSession
	 */
	public static
	User getUser(Object request)
	{
		PortletSession portletSession = getPortletSession(request);
		if(portletSession == null) return null;
		Object oUser = portletSession.getAttribute(WNames.sSESS_USER);
		if(oUser instanceof User) {
			return (User) oUser;
		}
		return null;
	}
	
	/**
	 * Restituisce il parametro action (a) presente nella sessione.
	 * 
	 * @param request (HttpServletRequest, ActionRequest, RenderRequest, Parameters)
	 * @return String
	 */
	public static
	String getAction(Object request)
	{
		PortletSession portletSession = getPortletSession(request);
		if(portletSession == null) return null;
		return (String) portletSession.getAttribute(WNames.sSESS_ACTION);
	}
	
	/**
	 * Restituisce i parametri salvati nella sessione presi come attributo di HttpServletRequest.
	 * 
	 * @param request HttpServletRequest
	 * @return Parameters
	 */
	public static
	Parameters getParameters(HttpServletRequest request)
	{
		return (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
	}
	
	/**
	 * Restituisce il risultato dell'ultima action salvato nella sessione.
	 * 
	 * @param request (HttpServletRequest, ActionRequest, RenderRequest, Parameters)
	 * @return Object
	 */
	public static
	Object getActionResult(Object request)
	{
		PortletSession portletSession = getPortletSession(request);
		if(portletSession == null) return null;
		return portletSession.getAttribute(WNames.sSESS_ACTION_RESULT);
	}
	
	/**
	 * Restituisce una risorsa utente.
	 * Essa viene recuperata dalla Map restituita da User.getResources().
	 * 
	 * @param request (HttpServletRequest, ActionRequest, RenderRequest, Parameters)
	 * @param sKey Chiave della risorsa utente.
	 * @return Object
	 */
	public static
	Object getUserResource(Object request, String sKey)
	{
		PortletSession portletSession = getPortletSession(request);
		if(portletSession == null) return null;
		Object oUser = portletSession.getAttribute(WNames.sSESS_USER);
		if(oUser instanceof User) {
			User user = (User) oUser;
			Map mapResources = user.getResources();
			if(mapResources == null) return null;
			return mapResources.get(sKey);
		}
		return null;
	}
	
	/**
	 * Restituisce una voce di configurazione.
	 * Essa viene recuperata dall'oggetto PortletConfig restituito da Parameters.getPortletConfig().
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave della voce di configurazione.
	 * @return String
	 */
	public static
	String getInitParameter(HttpServletRequest request, String sKey)
	{
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return null;
		PortletConfig portletConfig = parameters.getPortletConfig();
		return portletConfig.getInitParameter(sKey);
	}
	
	/**
	 * Restituisce una Map con le voci di configurazione.
	 * Essa viene recuperata da Parameters.getConfig().
	 * 
	 * @param request HttpServletRequest
	 * @return Properties
	 */
	public static
	Properties getConfig(HttpServletRequest request)
	{
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return new Properties();
		return parameters.getConfig();
	}
	
	/**
	 * Restituisce una Map con le voci relative all'oggetto PortletPreferences.
	 * Essa viene recuperata da Parameters.getPreferences().
	 * 
	 * @param request HttpServletRequest
	 * @return Properties
	 */
	public static
	Properties getPreferences(HttpServletRequest request)
	{
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return new Properties();
		return parameters.getPreferences();
	}
	
	/**
	 * Salva le informazioni relative all'oggetto PortletPreferences.
	 * 
	 * @param request HttpServletRequest
	 * @param mapPreferences Map delle preferenze
	 * @return boolean
	 */
	public static
	boolean storePreferences(HttpServletRequest request, Map mapPreferences)
	{
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return false;
		return parameters.storePreferences(mapPreferences);
	}
	
	/**
	 * Restituisce una Map con le voci relative all'oggetto PortletSession.
	 * 
	 * @param request HttpServletRequest
	 * @return Map
	 */
	public static
	Map getMapSession(HttpServletRequest request)
	{
		RenderRequest renderRequest = (RenderRequest) request.getAttribute(WNames.sATTR_RENDER_REQUEST);
		if(renderRequest == null) return new HashMap();
		PortletSession portletSession = renderRequest.getPortletSession();
		Map mapResult = new HashMap();
		Enumeration enumNames = portletSession.getAttributeNames();
		while(enumNames.hasMoreElements()) {
			String sName = (String) enumNames.nextElement();
			mapResult.put(sName, portletSession.getAttribute(sName));
		}
		return mapResult;
	}
	
	/**
	 * Restituisce il nome della Portlet.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String getPortletName(HttpServletRequest request)
	{
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters == null) return null;
		PortletConfig portletConfig = parameters.getPortletConfig();
		return portletConfig.getPortletName();
	}
	
	/**
	 * Restituisce il nome della piattaforma del Portale.
	 * 
	 * @return String
	 */
	public static
	String getPortalPlatform()
	{
		return ResourcesMgr.sPORTAL_PLATFORM;
	}
	
	/**
	 * Costruisce un link di action forward.
	 * 
	 * @param request HttpServletRequest
	 * @param sLabel Etichetta
	 * @param sURL pagina jsp con i parametri
	 * @return String
	 */
	public static
	String buildForwardLink(HttpServletRequest request, String sLabel, String sURL)
	{
		return "<a href=\"" + buildForwardLink(request, sURL, false) + "\">" + sLabel + "</a>";
	}
	
	/**
	 * Costruisce un link di action forward.
	 * 
	 * @param request HttpServletRequest
	 * @param sLabel Etichetta
	 * @param sURL pagina jsp con i parametri
	 * @param boMaximized Flag Maximized
	 * @return String
	 */
	public static
	String buildForwardLink(HttpServletRequest request, String sLabel, String sURL, boolean boMaximized)
	{
		return "<a href=\"" + buildForwardLink(request, sURL, boMaximized) + "\">" + sLabel + "</a>";
	}
	
	/**
	 * Costruisce una URL di action forward.
	 * 
	 * @param request HttpServletRequest
	 * @param sURL pagina jsp con i parametri
	 * @return String
	 */
	public static
	String buildForwardLink(HttpServletRequest request, String sURL)
	{
		return buildForwardLink(request, sURL, false);
	}
	
	/**
	 * Costruisce una URL di action forward.
	 * 
	 * @param request HttpServletRequest
	 * @param sURL pagina jsp con i parametri
	 * @param boMaximized Flag Maximized
	 * @return String
	 */
	public static
	String buildForwardLink(HttpServletRequest request, String sURL, boolean boMaximized)
	{
		if(sURL == null) return null;
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return sURL;
		if(sURL.startsWith("#")) {
			return sURL;
		}
		int iColon = sURL.indexOf(':');
		if(iColon >= 0) {
			return sURL;
		}
		int iSepPar = sURL.indexOf('?');
		String sForward = (iSepPar > 0) ? sURL.substring(0, iSepPar) : sURL;
		PortletURL portletURL = renderResponse.createActionURL();
		if(boMaximized) {
			try { portletURL.setWindowState(WindowState.MAXIMIZED); } catch (Exception e) {};
		}
		portletURL.setParameter(WNames.sPAR_FORWARD, sForward);
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
	
	/**
	 * Costruisce una URL di action forward che punta alla pagina di view.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String buildForwardHome(HttpServletRequest request)
	{
		return buildForwardHome(request, null);
	}
	
	/**
	 * Costruisce una URL di action forward che punta alla pagina di view.
	 * 
	 * @param request HttpServletRequest
	 * @param sParameters eventuali parametri aggiuntivi separati da &amp;. 
	 * @return String
	 */
	public static
	String buildForwardHome(HttpServletRequest request, String sParameters)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return "#";
		PortletURL portletURL = renderResponse.createActionURL();
		String sForward = null;
		Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
		if(parameters != null) {
			PortletConfig portletConfig = parameters.getPortletConfig();
			sForward = portletConfig.getInitParameter("view-jsp");
		}
		if(sForward == null) sForward = "index.jsp";
		portletURL.setParameter(WNames.sPAR_FORWARD, sForward);
		if(sParameters != null && sParameters.length() > 0) {
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
	
	/**
	 * Costruisce una URL di action.
	 * 
	 * @param request HttpServletRequest
	 * @param sAction identificativo dell'action
	 * @return String
	 */
	public static
	String buildActionURL(HttpServletRequest request, String sAction)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return "";
		PortletURL portletURL = renderResponse.createActionURL();
		if(sAction != null) {
			portletURL.setParameter(WNames.sPAR_ACTION, sAction);
		}
		return portletURL.toString();
	}
	
	/**
	 * Costruisce una URL di action.
	 * 
	 * @param request HttpServletRequest
	 * @param sAction identificativo dell'action
	 * @param sParameters parametri (es. nome=Mario&cognome=Rossi)
	 * @return String
	 */
	public static
	String buildActionURL(HttpServletRequest request, String sAction, String sParameters)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		if(renderResponse == null) return "";
		PortletURL portletURL = renderResponse.createActionURL();
		if(sAction != null) {
			portletURL.setParameter(WNames.sPAR_ACTION, sAction);
		}
		if(sParameters != null && sParameters.length() > 0) {
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
	
	/**
	 * Costruisce il menu predefinito.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String buildMenu(HttpServletRequest request)
	{
		return buildMenu(request, WNames.sATTR_MENU);
	}
	
	/**
	 * Costruisce il menu descritto nell'attributo di richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave dell'attributo di richiesta
	 * @return String
	 */
	public static
	String buildMenu(HttpServletRequest request, String sKey)
	{
		Map mapMenu = (Map) request.getAttribute(sKey);
		if(mapMenu == null || mapMenu.isEmpty()) return "";
		AMenuBuilder aMenuBuilder = ResourcesMgr.getMenuBuilder(mapMenu);
		return aMenuBuilder.build(request, mapMenu);
	}
	
	/**
	 * Costruisce il tabs predefinito.
	 * 
	 * @param request HttpServletRequest
	 * @return String
	 */
	public static
	String buildTabs(HttpServletRequest request)
	{
		return buildTabs(request, WNames.sATTR_TABS);
	}
	
	/**
	 * Costruisce il tabs descritto nell'attributo di richiesta individuato da sKey.
	 * 
	 * @param request HttpServletRequest
	 * @param sKey Chiave dell'attributo di richiesta
	 * @return String
	 */
	public static
	String buildTabs(HttpServletRequest request, String sKey)
	{
		Map mapTabs = (Map) request.getAttribute(sKey);
		if(mapTabs == null || mapTabs.isEmpty()) return "";
		ATabsBuilder aTabsBuilder = ResourcesMgr.getTabsBuilder(mapTabs);
		return aTabsBuilder.build(request, mapTabs);
	}
	
	/**
	 * Restituisce il path dela pagina corrente.
	 * 
	 * @param renderResponse RenderResponse
	 * @return path pagina corrente in cui e' presente la portlet
	 */
	public static
	String getCurrentPage(RenderResponse renderResponse)
	{
		PortletURL portletURL = renderResponse.createActionURL();
		String sPortletURL = portletURL.toString();
		int iIdxRoot = 0;
		if(sPortletURL.startsWith("http://")) {
			iIdxRoot = sPortletURL.indexOf("/", 7);
		}
		else {
			iIdxRoot = sPortletURL.indexOf("/");
		}
		if(iIdxRoot < 0) return sPortletURL;
		int iSepParameters = sPortletURL.indexOf('?');
		if(iSepParameters < 0) {
			return sPortletURL.substring(iIdxRoot);
		}
		return sPortletURL.substring(iIdxRoot, iSepParameters);
	}
	
	/**
	 * Restituisce il path dela pagina corrente.
	 * 
	 * @param request HttpServletRequest
	 * @return path pagina corrente in cui e' presente la portlet
	 */
	public static
	String getCurrentPage(HttpServletRequest request)
	{
		RenderResponse renderResponse = (RenderResponse) request.getAttribute(WNames.sATTR_RENDER_RESPONSE);
		PortletURL portletURL = renderResponse.createActionURL();
		String sPortletURL = portletURL.toString();
		int iIdxRoot = 0;
		if(sPortletURL.startsWith("http://")) {
			iIdxRoot = sPortletURL.indexOf("/", 7);
		}
		else
		if(sPortletURL.startsWith("https://")) {
			iIdxRoot = sPortletURL.indexOf("/", 8);
		}
		else {
			iIdxRoot = sPortletURL.indexOf("/");
		}
		if(iIdxRoot < 0) return sPortletURL;
		int iSepParameters = sPortletURL.indexOf('?');
		if(iSepParameters < 0) {
			return sPortletURL.substring(iIdxRoot);
		}
		return sPortletURL.substring(iIdxRoot, iSepParameters);
	}
	
	/**
	 * Formatta una data.
	 * @param cal Calendar
	 * @return String
	 */
	public static
	String formatDate(Calendar cal)
	{
		if(cal == null) return "";
		int iYear  = cal.get(Calendar.YEAR);
		int iMonth = cal.get(Calendar.MONTH) + 1;
		int iDay   = cal.get(Calendar.DATE);
		String sMonth  = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
		String sDay    = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
		return sDay + "/" + sMonth + "/" + iYear;
	}
	
	/**
	 * Formatta una data.
	 * @param date date
	 * @return String
	 */
	public static
	String formatDate(Date date)
	{
		if(date == null) return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int iYear  = cal.get(Calendar.YEAR);
		int iMonth = cal.get(Calendar.MONTH) + 1;
		int iDay   = cal.get(Calendar.DATE);
		String sMonth  = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
		String sDay    = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
		return sDay + "/" + sMonth + "/" + iYear;
	}
	
	/**
	 * Formatta un orario.
	 * @param cal Calendar
	 * @return String
	 */
	public static
	String formatTime(Calendar cal)
	{
		if(cal == null) return "";
		int iHour   = cal.get(Calendar.HOUR_OF_DAY);
		int iMinute = cal.get(Calendar.MINUTE);
		String sMin = iMinute < 10 ? "0" + iMinute : String.valueOf(iMinute);
		return iHour + ":" + sMin;
	}

	/**
	 * Formatta un orario.
	 * @param date Date
	 * @return String
	 */
	public static
	String formatTime(Date date)
	{
		if(date == null) return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int iHour   = cal.get(Calendar.HOUR_OF_DAY);
		int iMinute = cal.get(Calendar.MINUTE);
		String sMin = iMinute < 10 ? "0" + iMinute : String.valueOf(iMinute);
		return iHour + ":" + sMin;
	}
	
	/**
	 * Formatta un orario.
	 * @param sTime Orario
	 * @return String
	 */
	public static
	String formatTime(String sTime)
	{
		if(sTime == null || sTime.length() == 0) return "";
		if(sTime.length() < 3) return "0:" + sTime;
		String sHour   = sTime.substring(0, sTime.length() - 2);
		String sMinute = sTime.substring(sTime.length() - 2, sTime.length());
		return sHour + ":" + sMinute;
	}
	
	/**
	 * Formatta una data con orario
	 * @param cal Calendar
	 * @return String
	 */
	public static
	String formatDateTime(Calendar cal)
	{
		if(cal == null) return "";
		int iYear   = cal.get(Calendar.YEAR);
		int iMonth  = cal.get(Calendar.MONTH) + 1;
		int iDay    = cal.get(Calendar.DATE);
		int iHour   = cal.get(Calendar.HOUR_OF_DAY);
		int iMinute = cal.get(Calendar.MINUTE);
		int iSecond = cal.get(Calendar.SECOND);
		String sMonth  = iMonth  < 10 ? "0" + iMonth  : String.valueOf(iMonth);
		String sDay    = iDay    < 10 ? "0" + iDay    : String.valueOf(iDay);
		String sMin    = iMinute < 10 ? "0" + iMinute : String.valueOf(iMinute);
		String sSec    = iSecond < 10 ? "0" + iSecond : String.valueOf(iSecond);
		return sDay + "/" + sMonth + "/" + iYear + " " + iHour + ":" + sMin + ":" + sSec;
	}
	
	/**
	 * Formatta una data con orario
	 * @param date Date
	 * @return String
	 */
	public static
	String formatDateTime(Date date)
	{
		if(date == null) return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int iYear   = cal.get(Calendar.YEAR);
		int iMonth  = cal.get(Calendar.MONTH) + 1;
		int iDay    = cal.get(Calendar.DATE);
		int iHour   = cal.get(Calendar.HOUR_OF_DAY);
		int iMinute = cal.get(Calendar.MINUTE);
		int iSecond = cal.get(Calendar.SECOND);
		String sMonth  = iMonth  < 10 ? "0" + iMonth  : String.valueOf(iMonth);
		String sDay    = iDay    < 10 ? "0" + iDay    : String.valueOf(iDay);
		String sMin    = iMinute < 10 ? "0" + iMinute : String.valueOf(iMinute);
		String sSec    = iSecond < 10 ? "0" + iSecond : String.valueOf(iSecond);
		return sDay + "/" + sMonth + "/" + iYear + " " + iHour + ":" + sMin + ":" + sSec;
	}
	
	/**
	 * Formatta un valore double.
	 * 
	 * @param oValue Valore
	 * @return String
	 */
	public static
	String formatDouble(Object oValue)
	{
		if(oValue == null) return "0.00";
		double dValue = 0.0;
		if(oValue instanceof Number) {
			dValue = ((Number) oValue).doubleValue();
		}
		else {
			try {
				dValue = new Double(oValue.toString().replace(',', '.')).doubleValue();
			}
			catch(Exception ex) {
			}
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(dValue);
	}
	
	public static
	String extractTagValue(String sContent, String sTag)
	{
		if(sContent == null || sContent.length() == 0) {
			return "";
		}
		if(sTag == null || sTag.length() == 0) {
			return "";
		}
		char c0 = sTag.charAt(0);
		if(c0 != '<' && c0 != ':') {
			sTag = "<" + sTag;
		}
		int iTag = sContent.indexOf(sTag);
		if(iTag > 0) {
			int iStart = sContent.indexOf('>', iTag);
			if(iStart > 0) {
				int iEnd = sContent.indexOf('<', iStart + 1);
				if(iEnd > 0) {
					return sContent.substring(iStart + 1, iEnd).trim();
				}
			}
		}
		return "";
	}
	
	public static
	String extractTagValue(String sContent, String sTag, String sTagAlt)
	{
		if(sContent == null || sContent.length() == 0) {
			return "";
		}
		if(sTag == null || sTag.length() == 0) {
			return "";
		}
		char c0 = sTag.charAt(0);
		if(c0 != '<' && c0 != ':') {
			sTag = "<" + sTag;
		}
		int iTag = sContent.indexOf(sTag);
		if(iTag > 0) {
			int iStart = sContent.indexOf('>', iTag);
			if(iStart > 0) {
				int iEnd = sContent.indexOf('<', iStart + 1);
				if(iEnd > 0) {
					return sContent.substring(iStart + 1, iEnd).trim();
				}
			}
		}
		if(sTagAlt == null || sTagAlt.length() == 0) {
			return "";
		}
		c0 = sTagAlt.charAt(0);
		if(c0 != '<' && c0 != ':') {
			sTagAlt = "<" +sTagAlt;
		}
		iTag = sContent.indexOf(sTagAlt);
		if(iTag > 0) {
			int iStart = sContent.indexOf('>', iTag);
			if(iStart > 0) {
				int iEnd = sContent.indexOf('<', iStart + 1);
				if(iEnd > 0) {
					return sContent.substring(iStart + 1, iEnd).trim();
				}
			}
		}
		return "";
	}
	
	public static
	String color(int r, int g, int b, int brightness)
	{
		int max = r;
		if(g > max) max = g;
		if(b > max) max = b;
		double pr = 1.0d;
		double pg = 1.0d;
		double pb = 1.0d;
		if(max > 0) {
			if(max != r) {
				pr = (double) r / (double) max;
				if(pr < 0.2) pr = 0.2;
			}
			if(max != g) {
				pg = (double) g / (double) max;
				if(pg < 0.2) pg = 0.2;
			}
			if(max != b) {
				pb = (double) b / (double) max;
				if(pb < 0.2) pb = 0.2;
			}
		}
		
		r = r + ((int) Math.ceil(brightness * pr));
		if(r > 255) r = 255;
		if(r <   0) r = 0;
		g = g + ((int) Math.ceil(brightness * pg));
		if(g > 255) g = 255;
		if(g <   0) g = 0;
		b = b + ((int) Math.ceil(brightness * pb));
		if(b > 255) b = 255;
		if(b <   0) b = 0;
		
		String sR = Integer.toHexString(r);
		if(sR.length() == 1) sR = "0" + sR;
		String sG = Integer.toHexString(g);
		if(sG.length() == 1) sG = "0" + sG;
		String sB = Integer.toHexString(b);
		if(sB.length() == 1) sB = "0" + sB;
		
		return "#" + sR + sG + sB;
	}
}
