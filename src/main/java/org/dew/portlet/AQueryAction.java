package org.dew.portlet;

import java.util.*;
import java.sql.*;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * Implementazione strutturata di IAction per l'esecuzione di una query SQL.
 */
@SuppressWarnings("rawtypes")
public abstract
class AQueryAction implements IAction 
{  
  public 
  Object action(String sAction, Parameters parameters, ActionRequest request, ActionResponse response) 
    throws Exception 
  {  
    String sSQL = getSQL(parameters, request);
    if(sSQL == null) {
      throw new Exception("Query non disponibile.");
    }
    int iMaxRows = parameters.getInt("maxRows");
    
    List listResult = null;
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    try {
      conn = ResourcesMgr.getConnection(parameters.getPortletConfig());
      stm = conn.createStatement();
      rs = stm.executeQuery(sSQL);
      List<Object> listColumnNames = getColumnNames(parameters, request);
      if(listColumnNames == null) {
        listColumnNames = readColumnNames(rs);
      }
      listResult = readData(rs, iMaxRows, listColumnNames);
      response.setWindowState(WindowState.MAXIMIZED);
    }
    finally {
      if(rs   != null) try { rs.close();   } catch(Exception ex) {};
      if(stm  != null) try { stm.close();  } catch(Exception ex) {};
      if(conn != null) try { conn.close(); } catch(Exception ex) {};
    }
    return listResult;
  }
  
  public 
  String view(String sAction, Parameters parameters, Object actionResult, RenderRequest request, RenderResponse response)
    throws Exception
  {
    request.setAttribute("result",   actionResult);
    request.setAttribute("title",    getTitle(parameters, request));
    request.setAttribute("subtitle", getSubtitle(parameters, request));
    request.setAttribute("notes",    getNotes(parameters, actionResult, request));
    
    return getResultPage(parameters, request);
  }
  
  public 
  String exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response) 
    throws Exception
  {
    return null;
  }
  
  protected
  List<Object> readColumnNames(ResultSet rs)
    throws Exception
  {
    List<Object> listResult = new ArrayList<Object>();
    ResultSetMetaData rsmd = rs.getMetaData();
    int iColumnCount = rsmd.getColumnCount();
    for (int i = 0; i < iColumnCount; i++){
      String sColumnName = rsmd.getColumnName(i + 1);
      listResult.add(sColumnName);
    }
    return listResult;
  }
  
  protected
  List<List<Object>> readData(ResultSet rs, int iMaxRows, List<Object> listColNames)
    throws Exception
  {
    ResultSetMetaData rsmd = rs.getMetaData();
    int iColumnCount = listColNames.size();
    List<List<Object>> listResult = new ArrayList<List<Object>>();
    listResult.add(listColNames);
    int iRow = 0;
    while(rs.next()) {
      List<Object> listRecord = new ArrayList<Object>();
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
      if(iMaxRows > 0 && iRow >= iMaxRows) break;
    }
    return listResult;
  }
  
  protected abstract
  String getSQL(Parameters parameters, ActionRequest request) throws Exception;
  
  protected abstract
  List<Object> getColumnNames(Parameters parameters, ActionRequest request);
  
  protected abstract
  String getResultPage(Parameters parameters, RenderRequest request);
  
  protected abstract
  String getTitle(Parameters parameters, RenderRequest request);
  
  protected abstract
  String getSubtitle(Parameters parameters, RenderRequest request);
  
  protected abstract
  String getNotes(Parameters parameters, Object actionResult, RenderRequest request);
}
