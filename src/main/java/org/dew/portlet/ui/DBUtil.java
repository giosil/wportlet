package org.dew.portlet.ui;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

import javax.servlet.http.*;
import javax.portlet.*;

import org.dew.portlet.*;

/**
 * Classe di utilita' per lo sviluppo rapido di JSP che interrogano una base dati. <br />
 * I comandi SQL possono essere parametrici secondo la seguente sintassi:<br />
 * <br />
 * SELECT * FROM TABELLA WHERE CAMPO1 = [nome_paramatro] {AND CAMPO2 = [altro_parametro]}<br />
 * <br />
 * Quanto racchiuso in [] viene sostituito dal valore del parametro.<br />
 * Le regioni delimitate da {} invece vengono omesse nel caso in cui un parametro
 * all'interno e' nullo.<br />
 * Se ai metodi di DBUtil viene passato come sSQL una stringa che termina con ".sql"
 * allora l'SQL viene caricato dall'omonimo file di testo contenuto nella cartella sql.<br />
 */
public 
class DBUtil 
{
  /**
   * Restituisce una connessione al database secondo le voci di configurazione.
   * 
   * @param request HttpServletRequest
   * @return Connection
   * @throws Exception
   */
  public static
  Connection getConnection(HttpServletRequest request)
    throws Exception
  {
    Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
    if(parameters == null) return null;
    PortletConfig portletConfig = parameters.getPortletConfig();
    if(portletConfig == null) return null;
    return ResourcesMgr.getConnection(portletConfig);
  }
  
  /**
   * Restituisce l'eventuale messaggio di errore e l'SQL corrispondente.
   * 
   * @param request HttpServletRequest
   * @return String
   */
  public static
  String getMessageAndSQL(HttpServletRequest request)
  {
    Object oMessage = request.getAttribute(WNames.sATTR_MESSAGE);
    if(oMessage == null) return "";
    Object oSQL = request.getAttribute(WNames.sATTR_SQL_WITH_MSG);
    if(oSQL == null) oSQL = "";
    String sResult = "<p><strong><font color=\"#FF0000\">";
    sResult += "Message: " + oMessage;
    sResult += "</font></strong></p>";
    sResult += "<p><strong><font color=\"#FF0000\">";
    sResult += "SQL: " + oSQL;
    sResult += "</font></strong></p>";
    return sResult;
  }
  
  /**
   * Restituisce la lista di tabelle disponibili.
   * 
   * @param request
   * @return List
   */
  public static
  List<String> getTables(HttpServletRequest request)
  {
    List<String> listTables = new ArrayList<String>();
    Connection conn = null;
    try {
      conn = getConnection(request);
      DatabaseMetaData dbmd = conn.getMetaData();
      ResultSet rs = dbmd.getTables(null, null, null, new String[]{"TABLE"});
      while (rs.next()) {
        String sTableName = rs.getString(3);
        listTables.add(sTableName);
      }
    } 
    catch (Exception ex) {
      PlatformUtil.log("Exception in DBUtil.getTables", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
    }
    finally {
      if(conn != null) try{ conn.close(); } catch(Exception ex) {};
    }
    return listTables;
  }
  
  /**
   * Restituisce un boolean dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return boolean
   */
  public static
  boolean getBoolean(HttpServletRequest request, String sSQL)
  {
    String sResult = getString(request, sSQL);
    return Parameters.toBoolean(sResult, false);
  }
  
  /**
   * Restituisce un boolean dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param boDefault
   * @return boolean
   */
  public static
  boolean getBoolean(HttpServletRequest request, String sSQL, boolean boDefault)
  {
    String sResult = getString(request, sSQL);
    return Parameters.toBoolean(sResult, boDefault);
  }
  
  /**
   * Restituisce un double dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return double
   */
  public static
  double getDouble(HttpServletRequest request, String sSQL)
  {
    String sResult = getString(request, sSQL);
    return Parameters.toDouble(sResult, 0.0d);
  }
  
  /**
   * Restituisce un double dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param dDefault
   * @return double
   */
  public static
  double getDouble(HttpServletRequest request, String sSQL, double dDefault)
  {
    String sResult = getString(request, sSQL);
    return Parameters.toDouble(sResult, dDefault);
  }
  
  /**
   * Restituisce un int dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return int
   */
  public static
  int getInt(HttpServletRequest request, String sSQL)
  {
    String sResult = getString(request, sSQL);
    return Parameters.toInt(sResult, 0);
  }
  
  /**
   * Restituisce un int dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param iDefault
   * @return int
   */
  public static
  int getInt(HttpServletRequest request, String sSQL, int iDefault)
  {
    String sResult = getString(request, sSQL);
    return Parameters.toInt(sResult, iDefault);
  }
  
  /**
   * Restituisce una String dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return String
   */
  public static
  String getString(HttpServletRequest request, String sSQL)
  {
    return getString(request, sSQL, null);
  }
  
  /**
   * Restituisce una String dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param sDefault
   * @return String
   */
  public static
  String getString(HttpServletRequest request, String sSQL, String sDefault)
  {
    sSQL = getSQL(request, sSQL);
    
    String sResult = null;
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = getConnection(request);
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      if(rs.next()) {
        sResult = rs.getString(1);
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in DBUtil.getString", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
      request.setAttribute(WNames.sATTR_SQL_WITH_MSG, sSQL);
      return "Exception: " + ex;
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ex) {}
      if(stm  != null) try { stm.close();  } catch(Exception ex) {}
      if(conn != null) try { conn.close(); } catch(Exception ex) {}
    }
    if(sResult == null) return sDefault;
    return sResult;
  }
  
  /**
   * Restituisce una Map dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return Map
   */
  public static
  Map<String,Object> getMap(HttpServletRequest request, String sSQL)
  {
    sSQL = getSQL(request, sSQL);
    
    Map<String,Object> mapResult = new HashMap<String,Object>();
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = getConnection(request);
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      
      ResultSetMetaData rsmd = rs.getMetaData();
      int iColumnCount = rsmd.getColumnCount();
      if(rs.next()) {
        for (int i = 0; i < iColumnCount; i++){
          String sColName = rsmd.getColumnName(i + 1);
          int iColType    = rsmd.getColumnType(i + 1);
          if(iColType == java.sql.Types.NUMERIC) {
            int iScale = rsmd.getScale(i + 1);
            if(iScale > 0) {
              double dValue = rs.getDouble(i + 1);
              mapResult.put(sColName, dValue);
            }
            else {
              int iValue = rs.getInt(i + 1);
              mapResult.put(sColName, iValue);
            }
          }
          else if(iColType == java.sql.Types.BIGINT) {
            long lValue = rs.getLong(i + 1);
            mapResult.put(sColName, lValue);
          }
          else if(iColType == java.sql.Types.INTEGER || iColType == java.sql.Types.SMALLINT) {
            int iValue = rs.getInt(i + 1);
            mapResult.put(sColName, iValue);
          }
          else if(iColType == java.sql.Types.DECIMAL || iColType == java.sql.Types.DOUBLE || iColType == java.sql.Types.FLOAT || iColType == java.sql.Types.REAL) {
            double dValue = rs.getDouble(i + 1);
            mapResult.put(sColName, dValue);
          }
          else if(iColType == java.sql.Types.DATE || iColType == java.sql.Types.TIMESTAMP) {
            java.sql.Date dValue = rs.getDate(i + 1);
            mapResult.put(sColName, dValue);
          }
          else {
            String sValue = rs.getString(i + 1);
            mapResult.put(sColName, sValue);
          }
        }
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in DBUtil.getMap", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
      request.setAttribute(WNames.sATTR_SQL_WITH_MSG, sSQL);
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ex) {}
      if(stm  != null) try { stm.close();  } catch(Exception ex) {}
      if(conn != null) try { conn.close(); } catch(Exception ex) {}
    }
    return mapResult;
  }
  
  /**
   * Restituisce una Map di String dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return Map
   */
  public static
  Map<String,String> getMapOfString(HttpServletRequest request, String sSQL)
  {
    sSQL = getSQL(request, sSQL);
    
    Map<String,String> mapResult = new HashMap<String,String>();
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = getConnection(request);
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      ResultSetMetaData rsmd = rs.getMetaData();
      int iColumnCount = rsmd.getColumnCount();
      if(rs.next()) {
        for (int i = 0; i < iColumnCount; i++){
          String sColName = rsmd.getColumnName(i + 1);
          String sValue = rs.getString(i + 1);
          mapResult.put(sColName, sValue);
        }
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in DBUtil.getMapOfString", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
      request.setAttribute(WNames.sATTR_SQL_WITH_MSG, sSQL);
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ex) {}
      if(stm  != null) try { stm.close();  } catch(Exception ex) {}
      if(conn != null) try { conn.close(); } catch(Exception ex) {}
    }
    return mapResult;
  }
  
  /**
   * Imposta gli attributi della request dalla mappa ottenuta dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param sPrefix Prefisso degli attributi
   */
  public static
  void setAttributes(HttpServletRequest request, String sSQL, String sPrefix)
  {
    Map<String,Object> mapResult = getMap(request, sSQL);
    Iterator<Map.Entry<String,Object>> itEntries = mapResult.entrySet().iterator();
    while(itEntries.hasNext()) {
      Map.Entry<String,Object> entry = itEntries.next();
      if(sPrefix != null && sPrefix.length() > 0) {
        request.setAttribute(sPrefix + entry.getKey(), entry.getValue());
      }
      else {
        request.setAttribute((String) entry.getKey(), entry.getValue());
      }
    }
  }
  
  /**
   * Restituisce una List (di List) dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param iMaxRows
   * @param boIncludeColumnNames
   * @return List
   */
  public static
  List<List<Object>> getList(HttpServletRequest request, String sSQL, int iMaxRows, boolean boIncludeColumnNames)
  {
    sSQL = getSQL(request, sSQL);
    
    List<List<Object>> listResult = new ArrayList<List<Object>>();
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = getConnection(request);
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      
      ResultSetMetaData rsmd = rs.getMetaData();
      int iColumnCount = rsmd.getColumnCount();
      if(boIncludeColumnNames) {
        List<Object> listColNames = new ArrayList<Object>();
        for (int i = 0; i < iColumnCount; i++){
          listColNames.add(rsmd.getColumnName(i + 1));
        }
        listResult.add(listColNames);
      }
      
      int iRow = 0;
      while(rs.next()) {
        List<Object> listRecord = new ArrayList<Object>(iColumnCount);
        for (int i = 0; i < iColumnCount; i++){
          int iColType = rsmd.getColumnType(i + 1);
          if(iColType == java.sql.Types.NUMERIC) {
            int iScale = rsmd.getScale(i + 1);
            if(iScale > 0) {
              double dValue = rs.getDouble(i + 1);
              listRecord.add(dValue);
            }
            else {
              int iValue = rs.getInt(i + 1);
              listRecord.add(iValue);
            }
          }
          else if(iColType == java.sql.Types.BIGINT) {
            long lValue = rs.getLong(i + 1);
            listRecord.add(lValue);
          }
          else if(iColType == java.sql.Types.INTEGER || iColType == java.sql.Types.SMALLINT) {
            int iValue = rs.getInt(i + 1);
            listRecord.add(iValue);
          }
          else if(iColType == java.sql.Types.DECIMAL || iColType == java.sql.Types.DOUBLE || iColType == java.sql.Types.FLOAT || iColType == java.sql.Types.REAL) {
            double dValue = rs.getDouble(i + 1);
            listRecord.add(dValue);
          }
          else if(iColType == java.sql.Types.DATE || iColType == java.sql.Types.TIMESTAMP) {
            java.sql.Date dValue = rs.getDate(i + 1);
            listRecord.add(dValue);
          }
          else {
            String sValue = rs.getString(i + 1);
            listRecord.add(sValue);
          }
        }
        listResult.add(listRecord);
        iRow++;
        if(iMaxRows > 0 && iRow >= iMaxRows) {
          break;
        }
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in DBUtil.getList", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
      request.setAttribute(WNames.sATTR_SQL_WITH_MSG, sSQL);
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ex) {}
      if(stm  != null) try { stm.close();  } catch(Exception ex) {}
      if(conn != null) try { conn.close(); } catch(Exception ex) {}
    }
    
    return listResult;
  }
  
  /**
   * Restituisce una List (di String) dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param iMaxRows
   * @return List<String>
   */
  public static
  List<String> getListOfString(HttpServletRequest request, String sSQL, int iMaxRows)
  {
    sSQL = getSQL(request, sSQL);
    
    List<String> listResult = new ArrayList<String>();
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = getConnection(request);
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      int iRow = 0;
      while(rs.next()) {
        String sValue = rs.getString(1);
        listResult.add(sValue);
        iRow++;
        if(iMaxRows > 0 && iRow >= iMaxRows) {
          break;
        }
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in DBUtil.getListOfString", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
      request.setAttribute(WNames.sATTR_SQL_WITH_MSG, sSQL);
    }
    finally {
      if(rs != null)   try { rs.close(); }   catch(Exception ex) {}
      if(stm != null)  try { stm.close(); }  catch(Exception ex) {}
      if(conn != null) try { conn.close(); } catch(Exception ex) {}
    }
    return listResult;
  }
  
  /**
   * Costruisce una lista dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return String
   */
  public static
  String buildList(HttpServletRequest request, String sSQL)
  {
    return buildList(request, sSQL, null, null);
  }
  
  /**
   * Costruisce una lista dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param sLeftTag
   * @param sRightTag
   * @return String
   */
  public static
  String buildList(HttpServletRequest request, String sSQL, String sLeftTag, String sRightTag)
  {
    sSQL = getSQL(request, sSQL);
    
    if(sLeftTag == null)  sLeftTag = "";
    if(sRightTag == null) sRightTag = "<br />";
    
    StringBuffer sb = new StringBuffer();
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = getConnection(request);
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      while(rs.next()) {
        String sValue = rs.getString(1);
        if(sValue == null || sValue.length() == 0) sValue = "&nbsp;";
        sb.append(sLeftTag + sValue + sRightTag + "\n");
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in DBUtil.buildList", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
      request.setAttribute(WNames.sATTR_SQL_WITH_MSG, sSQL);
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ex) {}
      if(stm  != null) try { stm.close();  } catch(Exception ex) {}
      if(conn != null) try { conn.close(); } catch(Exception ex) {}
    }
    return sb.toString();
  }
  
  /**
   * Costruisce la lista di opzioni di un select HTML (casella combinata)
   * dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return String
   */
  public static
  String buildOptions(HttpServletRequest request, String sSQL)
  {
    return buildOptions(request, sSQL, null);
  }
  
  /**
   * Costruisce la lista di opzioni di un select HTML (casella combinata)
   * dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param oSelected
   * @return String
   */
  public static
  String buildOptions(HttpServletRequest request, String sSQL, Object oSelected)
  {
    sSQL = getSQL(request, sSQL);
    
    List<List<Object>> listResult = new ArrayList<List<Object>>();
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = getConnection(request);
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      
      ResultSetMetaData rsmd = rs.getMetaData();
      int iColumnCount = rsmd.getColumnCount();
      int iCols = iColumnCount >= 2 ? 2 : 1;
      while(rs.next()) {
        List<Object> listRecord = new ArrayList<Object>(iCols);
        for (int i = 0; i < iCols; i++){
          String sValue = rs.getString(i + 1);
          listRecord.add(sValue);
        }
        listResult.add(listRecord);
      }
    }
    catch(Exception ex) {
      PlatformUtil.log("Exception in DBUtil.buildOptions", ex);
      request.setAttribute(WNames.sATTR_MESSAGE, ex.getMessage());
      request.setAttribute(WNames.sATTR_SQL_WITH_MSG, sSQL);
      return "";
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ex) {}
      if(stm  != null) try { stm.close();  } catch(Exception ex) {}
      if(conn != null) try { conn.close(); } catch(Exception ex) {}
    }
    return WebUtil.buildOptions(listResult, oSelected);
  }
  
  /**
   * Costruisce una tabella HTML dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @return String
   */
  public static
  String buildTableContent(HttpServletRequest request, String sSQL)
  {
    return buildTableContent(request, sSQL, 0);
  }
  
  /**
   * Costruisce una tabella HTML dall'esecuzione della query SQL.
   * 
   * @param request
   * @param sSQL
   * @param iMaxRows
   * @return String
   */
  public static
  String buildTableContent(HttpServletRequest request, String sSQL, int iMaxRows)
  {
    List<List<Object>> listResult = getList(request, sSQL, iMaxRows, true);
    
    return WebUtil.buildTableContent(listResult, true);
  }
  
  /**
   * Permette di ottenere un comando SQL dal template in cui vengono
   * sostituiti i parametri. 
   * 
   * @param request
   * @param sSQL
   * @return String
   */
  public static
  String getSQL(HttpServletRequest request, String sSQL)
  {
    Parameters parameters = (Parameters) request.getAttribute(WNames.sATTR_PARAMETERS);
    if(parameters == null) return sSQL;
    
    if(sSQL.endsWith(".sql")) {
      sSQL = ResourcesMgr.loadSQL(parameters.getPortletConfig(), sSQL);
    }
    if(sSQL.indexOf('[') < 0) return sSQL;
    
    sSQL = removeNullRegions(sSQL, parameters);
    Iterator<Map.Entry<String, Object>> oItEntries = parameters.entrySet().iterator();
    while(oItEntries.hasNext()) {
      Map.Entry<String, Object> entry = oItEntries.next();
      String sKey   = entry.getKey();
      String sValue = null;
      Object oValue = entry.getValue();
      if(oValue == null) {
        sValue = "NULL";
      }
      else {
        sValue = oValue.toString();
      }
      sSQL = substParameter(sSQL, "[" + sKey + "]", sValue);
    }
    sSQL = correctWhereClause(sSQL);
    return sSQL;
  }
  
  /**
   * Permette di ottenere un comando SQL dal template in cui vengono
   * sostituiti i parametri. 
   * 
   * @param sSQL
   * @param parameters
   * @return String
   */
  public static
  String getSQL(String sSQL, Parameters parameters)
  {
    if(sSQL.endsWith(".sql")) {
      sSQL = ResourcesMgr.loadSQL(parameters.getPortletConfig(), sSQL);
    }
    if(sSQL.indexOf('[') < 0) return sSQL;
    
    sSQL = removeNullRegions(sSQL, parameters);
    Iterator<Map.Entry<String, Object>> oItEntries = parameters.entrySet().iterator();
    while(oItEntries.hasNext()) {
      Map.Entry<String, Object> entry = oItEntries.next();
      String sKey   = entry.getKey();
      String sValue = null;
      Object oValue = entry.getValue();
      if(oValue == null) {
        sValue = "NULL";
      }
      else {
        sValue = oValue.toString();
      }
      sSQL = substParameter(sSQL, "[" + sKey + "]", sValue);
    }
    sSQL = correctWhereClause(sSQL);
    return sSQL;
  }
  
  private static
  String substParameter(String sText, String sPar, String sValue)
  {
    if(sValue == null) sValue = "";
    int iParLen  = sPar.length();
    int iTextLen = sText.length();
    int iIndexOf = sText.indexOf(sPar);
    while(iIndexOf >= 0) {
      String sLeft = sText.substring(0, iIndexOf);
      String sParValue = sValue;
      String sRight = null;
      if(iIndexOf + iParLen >= iTextLen) {
        sRight = "";
      }
      else {
        sRight = sText.substring(iIndexOf + iParLen);
      }
      
      if(isTextDelimit(sLeft, sRight)) {
        sParValue = doubleQuotes(sValue);
      }
      
      sText = sLeft + sParValue + sRight;
      
      iIndexOf = sText.indexOf(sPar);
    }
    return sText;
  }
  
  private static
  boolean isTextDelimit(String sLeft, String sRight)
  {
    if(sLeft.length() > 0) {
      char cLast = sLeft.charAt(sLeft.length() - 1);
      if(cLast == '\'' || cLast == '%' || cLast == '_') {
        return true;
      }
    }
    if(sRight.length() > 0) {
      char cFirst = sRight.charAt(0);
      if(cFirst == '\'' || cFirst == '%' || cFirst == '_') {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Rimuove le regioni delimitate da { e } in cui vi sono parametri nulli.
   *
   * @param sSQL String
   * @param parametri Parameters
   * @return String
   */
  private static
  String removeNullRegions(String sSQL, Parameters parametri)
  {
    int iTest = sSQL.indexOf('{');
    if(iTest < 0) return sSQL;
    
    int iSQLLength = sSQL.length();
    StringBuffer sbResult = new StringBuffer(iSQLLength);
    boolean boDoNotCopy = false;
    for(int i = 0; i < iSQLLength; i++) {
      char c = sSQL.charAt(i);
      if(c == '{') {
        int iEndRegion  = sSQL.indexOf('}', i);
        if(iEndRegion > 0) {
          boolean boAtLeastOneNull = false;
          for(int j = i; j < iEndRegion; j++) {
            char cp = sSQL.charAt(j);
            if(cp == '[') {
              int iBeginNextPar = sSQL.indexOf('[', j + 1);
              int iEndPar = sSQL.indexOf(']', j);
              if(iBeginNextPar > 0 && iEndPar > iBeginNextPar) {
                continue;
              }
              if(iEndPar > 0) {
                String sParName = sSQL.substring(j + 1, iEndPar);
                Object oValue = parametri.get(sParName);
                if(oValue == null || oValue.toString().length() == 0) {
                  boAtLeastOneNull = true;
                  break;
                }
              } // end if(iEndPar > 0)
            } // end if(cp == '[')
          } // end for(int j
          boDoNotCopy = boAtLeastOneNull;
        } // end if(iEndRegion > 0)
      }
      if(c == '}') {
        boDoNotCopy = false;
      }
      if(!boDoNotCopy && c != '{' && c != '}') {
        sbResult.append(c);
      }
    }
    return sbResult.toString();
  }
  
  /**
   * Corregge eventuali errori derivanti dalla sostituizione dei parametri nel comando SQL.
   * Puo' succedere ad esempio che la clausola WHERE rimanga vuota. In tal caso viene eliminata del tutto.
   * Se dopo la WHERE si ha un AND oppure un OR esso viene eliminato.
   *
   * @param sSQL String
   * @return String
   */
  private static
  String correctWhereClause(String sSQL)
  {
    String sUSQL = sSQL.toUpperCase() + " ";
    int iWhere = sUSQL.indexOf(" WHERE ");
    if(iWhere < 0) return sSQL;
    int iBeginWord = -1;
    int iEndWord = -1;
    for(int i = iWhere + 7; i < sUSQL.length(); i++) {
      char c = sUSQL.charAt(i);
      if(Character.isLetter(c)) {
        if(iBeginWord < 0) {
          iBeginWord = i;
        }
      }
      else {
        if(iBeginWord > 0) {
          iEndWord = i;
          break;
        }
      }
    }
    String sWord = null;
    if(iBeginWord >= 0) {
      if(iEndWord < 0) iEndWord = sUSQL.length() - 1;
      sWord = sUSQL.substring(iBeginWord, iEndWord);
    }
    if(sWord == null) {
      return sSQL.substring(0, iWhere);
    }
    if(sWord.equals("AND") || sWord.equals("OR")) {
      return sSQL.substring(0, iWhere + 7) + sSQL.substring(iEndWord + 1);
    }
    return sSQL;
  }
  
  private static
  String doubleQuotes(String text)
  {
    StringBuffer result = new StringBuffer(text.length());
    char c;
    for (int i = 0; i < text.length(); i++) {
      c = text.charAt(i);
      if (c == '\'') result.append('\'');
      result.append(c);
    }
    return result.toString();
  }
}
