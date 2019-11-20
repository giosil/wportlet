package org.dew.portlet;

import java.lang.reflect.Array;
import java.util.*;

import javax.portlet.*;

/**
 * Classe che estende HashMap per una gestione facilitata dei parametri.
 */
@SuppressWarnings({"rawtypes","unchecked","serial"})
public 
class Parameters extends HashMap 
{
  protected PortletConfig      _portletConfig;
  protected PortletPreferences _portletPreferences;
  protected String             _sNamespace;
  protected ActionRequest      _actionRequest;
  protected RenderRequest      _renderRequest;
  
  public
  Parameters(PortletConfig portletConfig, ActionRequest request, String sNamespace)
  {
    super();
    this._portletConfig      = portletConfig;
    this._portletPreferences = request.getPreferences();
    this._sNamespace         = sNamespace;
    this._actionRequest      = request;
    Enumeration enumeration  = request.getParameterNames();
    while(enumeration.hasMoreElements()) {
      String sParName = (String) enumeration.nextElement();
      if(sNamespace != null && sParName.startsWith(sNamespace)) {
        put(sParName.substring(sNamespace.length()), 
        request.getParameter(sParName));
      }
      else {
        put(sParName, request.getParameter(sParName));
      }
    }
  }
  
  public
  Parameters(PortletConfig portletConfig, RenderRequest request, String sNamespace)
  {
    super();
    this._portletConfig      = portletConfig;
    this._portletPreferences = request.getPreferences();
    this._sNamespace         = sNamespace;
    this._renderRequest      = request;
    Enumeration enumeration = request.getParameterNames();
    while(enumeration.hasMoreElements()) {
      String sParName = (String) enumeration.nextElement();
      if(sNamespace != null && sParName.startsWith(sNamespace)) {
        put(sParName.substring(sNamespace.length()), 
        request.getParameter(sParName));
      }
      else {
        put(sParName, request.getParameter(sParName));
      }
    }
  }
  
  public PortletConfig getPortletConfig() {
    return _portletConfig;
  }
  
  public PortletPreferences getPortletPreferences() {
    return _portletPreferences;
  }
  
  public String getNamespace() {
    if(_sNamespace == null) _sNamespace = "";
    return _sNamespace;
  }
  
  public ActionRequest getActionRequest() {
    return _actionRequest;
  }
  
  public void setActionRequest(ActionRequest actionRequest) {
    this._actionRequest = actionRequest;
    this._renderRequest = null;
  }
  
  public RenderRequest getRenderRequest() {
    return _renderRequest;
  }
  
  public void setRenderRequest(RenderRequest renderRequest) {
    this._renderRequest = renderRequest;
    this._actionRequest = null;
  }
  
  public PortletRequest getPortletRequest() {
    if(_actionRequest != null) return _actionRequest;
    return _renderRequest;
  }
  
  public PortletSession getPortletSession() {
    if(_actionRequest != null) return _actionRequest.getPortletSession();
    if(_renderRequest != null) return _renderRequest.getPortletSession();
    return null;
  }
  
  public User getUser() {
    PortletSession portletSession = null;
    if(_actionRequest != null) {
      portletSession = _actionRequest.getPortletSession();
    }
    if(_renderRequest != null) {
      portletSession = _renderRequest.getPortletSession();
    }
    if(portletSession == null) return null;
    Object user = portletSession.getAttribute(WNames.sSESS_USER);
    if(user instanceof User) {
      return (User) user;
    }
    return null;
  }
  
  public 
  Properties getConfig() 
  {
    Properties propResult = new Properties();
    if(_portletConfig != null) {
      Enumeration enumNames = _portletConfig.getInitParameterNames();
      while(enumNames.hasMoreElements()) {
        String sName = (String) enumNames.nextElement();
        propResult.setProperty(sName, _portletConfig.getInitParameter(sName));
      }
    }
    return propResult;
  }
  
  public 
  Properties getPreferences() 
  {
    Properties propResult = new Properties();
    if(_portletPreferences != null) {
      Enumeration enumNames = _portletPreferences.getNames();
      while(enumNames.hasMoreElements()) {
        String sName  = (String) enumNames.nextElement();
        propResult.setProperty(sName, _portletPreferences.getValue(sName, ""));
      }
    }
    return propResult;
  }
  
  public 
  boolean storePreferences(Map mapPreferences) 
  {
    if(_portletPreferences != null) {
      try {
        Iterator itEntries = mapPreferences.entrySet().iterator();
        while(itEntries.hasNext()) {
          Map.Entry entry = (Map.Entry) itEntries.next();
          String sKey = (String) entry.getKey(); 
          String sVal = (String) entry.getValue();
          _portletPreferences.setValue(sKey, sVal);
        }
        _portletPreferences.store();
        return true;
      }
      catch(Exception ex) {
        System.err.println("[WPortlet] Exception in Parameters.storePreferences: " + ex);
      }
    }
    return false;
  }
  
  public
  void copyInAttributes(RenderRequest request)
  {
    Iterator itEntries = entrySet().iterator();
    while(itEntries.hasNext()) {
      Map.Entry entry = (Map.Entry) itEntries.next();
      String sKey = (String) entry.getKey();
      Object oValue = entry.getValue();
      request.setAttribute(sKey, oValue);
    }
  }
  
  public
  void copyInAttributes(RenderRequest request, String sKey)
  {
    request.setAttribute(sKey, get(sKey));
  }
  
  public
  void copyInAttributes(RenderRequest request, String sKey, Object oDefaultValue)
  {
    Object oValue = get(sKey);
    if(oValue == null) {
      request.setAttribute(sKey, oDefaultValue);
    }
    else {
      request.setAttribute(sKey, oValue);
    }
  }
  
  public
  String getActionURL(RenderResponse response)
  {
    PortletURL portletURL = response.createActionURL();
    Iterator itEntries = this.entrySet().iterator();
    while(itEntries.hasNext()) {
      Map.Entry entry = (Map.Entry) itEntries.next();
      String sKey   = (String) entry.getKey();
      String sValue = (String) entry.getValue();
      portletURL.setParameter(sKey, sValue);
    }
    return portletURL.toString();
  }
  
  public
  String getActionURL(RenderResponse response, String sAdditionalParameters)
  {
    PortletURL portletURL = response.createActionURL();
    Iterator itEntries = this.entrySet().iterator();
    while(itEntries.hasNext()) {
      Map.Entry entry = (Map.Entry) itEntries.next();
      String sKey   = (String) entry.getKey();
      String sValue = (String) entry.getValue();
      portletURL.setParameter(sKey, sValue);
    }
    if(sAdditionalParameters != null && sAdditionalParameters.length() > 0) {
      StringTokenizer st = new StringTokenizer(sAdditionalParameters, "&");
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
  
  public
  List getKeysStartsWith(String sStartKey)
  {
    List listResult = new ArrayList();
    Iterator itKeys = keySet().iterator();
    while(itKeys.hasNext()) {
      String sKey = itKeys.next().toString();
      if(sKey.startsWith(sStartKey)) {
        listResult.add(sKey);
      }
    }
    Collections.sort(listResult);
    return listResult;
  }
  
  public
  List getKeysEndsWith(String sEndKey)
  {
    List listResult = new ArrayList();
    Iterator itKeys = keySet().iterator();
    while(itKeys.hasNext()) {
      String sKey = itKeys.next().toString();
      if(sKey.endsWith(sEndKey)) {
        listResult.add(sKey);
      }
    }
    Collections.sort(listResult);
    return listResult;
  }
  
  // get methods
  public int getInt(String key) {
    return toInt(get(key), 0);
  }
  
  public int getInt(String key, int iDefault) {
    return toInt(get(key), iDefault);
  }
  
  public double getDouble(String key) {
    return toDouble(get(key), 0.0d);
  }
  
  public double getDouble(String key, double dDefault) {
    return toDouble(get(key), dDefault);
  }
  
  public long getLong(String key) {
    return toLong(get(key), 0);
  }
  
  public long getLong(String key, long lDefault) {
    return toLong(get(key), lDefault);
  }
  
  public boolean getBoolean(String key) {
    return toBoolean(get(key), false);
  }
  
  public boolean getBoolean(String key, boolean boDefault) {
    return toBoolean(get(key), boDefault);
  }
  
  public String getString(String key) {
    return toString(get(key), null);
  }
  
  public String getString(String key, int iMaxLength) {
    return toString(get(key), null, iMaxLength);
  }
  
  public String getString(String key, String sDefault) {
    return toString(get(key), sDefault);
  }
  
  public String getString(String key, String sDefault, int iMaxLength) {
    return toString(get(key), sDefault, iMaxLength);
  }
  
  public String getUpperString(String key) {
    return toUpperString(get(key), null);
  }
  
  public String getUpperString(String key, String sDefault) {
    return toUpperString(get(key), sDefault);
  }
  
  public String getUpperString(String key, String sDefault, int iMaxLength) {
    return toUpperString(get(key), sDefault, iMaxLength);
  }
  
  public String getLowerString(String key) {
    return toLowerString(get(key), null);
  }
  
  public String getLowerString(String key, String sDefault) {
    return toLowerString(get(key), sDefault);
  }
  
  public String getLowerString(String key, String sDefault, int iMaxLength) {
    return toLowerString(get(key), sDefault, iMaxLength);
  }
  
  public java.util.Date getDate(String key) {
    return toDate(get(key), null);
  }
  
  public java.util.Date getDate(String key, Object oDate) {
    return toDate(get(key), oDate);
  }
  
  public java.util.Date getTime(String key) {
    return toTime(get(key), null);
  }
  
  public java.util.Date getTime(String key, Object oTime) {
    return toTime(get(key), oTime);
  }
  
  public java.util.Calendar getCalendar(String key) {
    return toCalendar(get(key), null);
  }
  
  public java.util.Calendar getCalendar(String key, Object oDate) {
    return toCalendar(get(key), oDate);
  }
  
  public java.sql.Date getSQLDate(String key) {
    return toSQLDate(get(key), null);
  }
  
  public java.sql.Date getSQLDate(String key, Object oDate) {
    return toSQLDate(get(key), oDate);
  }
  
  public java.sql.Timestamp getSQLTimestamp(String key) {
    return toSQLTimestamp(get(key), null);
  }
  
  public java.sql.Timestamp getSQLTimestamp(String key, Object oDate) {
    return toSQLTimestamp(get(key), oDate);
  }
  
  public int getIntDate(String key) {
    return toIntDate(get(key), 0);
  }
  
  public int getIntDate(String key, int iDate) {
    return toIntDate(get(key), iDate);
  }
  
  public int getIntTime(String key) {
    return toIntTime(get(key), 0);
  }
  
  public int getIntTime(String key, int iTime) {
    return toIntTime(get(key), iTime);
  }
  
  public String getStringTime(String key) {
    return toStringTime(get(key), null);
  }
  
  public String getStringTime(String key, String sDefault) {
    return toStringTime(get(key), sDefault);
  }
  
  public List getList(String key) {
    return toList(get(key), false);
  }
  
  public List getList(String key, boolean notNull) {
    return toList(get(key), notNull);
  }
  
  public List getList(String key, Object oDefault) {
    return toList(get(key), oDefault);
  }
  
  public boolean isBlank(String key) {
    Object oValue = get(key);
    if(oValue == null) return true;
    String sValue = oValue.toString();
    if(sValue == null || sValue.trim().length() == 0 || sValue.equals("null")) {
      return true;
    }
    return false;
  }
  
  // Conversion utils
  public static
  int toInt(Object object, int iDefault)
  {
    if(object == null) return iDefault;
    if(object instanceof Number) {
      return ((Number) object).intValue();
    }
    if(object instanceof java.util.Date) {
      return longDateToInt(((java.util.Date) object).getTime());
    }
    if(object instanceof java.util.Calendar) {
      return longDateToInt(((java.util.Calendar) object).getTimeInMillis());
    }
    if(object instanceof java.lang.Boolean) {
      return ((java.lang.Boolean) object).booleanValue() ? 1 : 0;
    }
    String sValue = object.toString();
    if(sValue.length() == 0) return iDefault;
    try{ return Integer.parseInt(sValue); } catch(Throwable th) {}
    return iDefault;
  }
  
  public static
  double toDouble(Object object, double dDefault)
  {
    if(object == null) return dDefault;
    if(object instanceof Number) {
      return ((Number) object).doubleValue();
    }
    if(object instanceof java.lang.Boolean) {
      return ((java.lang.Boolean) object).booleanValue() ? 1.0d : 0.0d;
    }
    String sValue = object.toString();
    if(sValue.length() == 0) return dDefault;
    try{ return Double.parseDouble(sValue.replace(',', '.')); } catch(Throwable th) {}
    return dDefault;
  }
  
  public static
  long toLong(Object object, long lDefault)
  {
    if(object == null) return lDefault;
    if(object instanceof Number) {
      return ((Number) object).longValue();
    }
    if(object instanceof java.util.Date) {
      return ((java.util.Date) object).getTime();
    }
    if(object instanceof java.util.Calendar) {
      return ((java.util.Calendar) object).getTimeInMillis();
    }
    if(object instanceof java.lang.Boolean) {
      return ((java.lang.Boolean) object).booleanValue() ? 1l : 0l;
    }
    String sValue = object.toString();
    if(sValue.length() == 0) return lDefault;
    try{ return Long.parseLong(sValue); } catch(Throwable th) {}
    return lDefault;
  }
  
  public static
  boolean toBoolean(Object object, boolean bDefault)
  {
    if(object == null) return bDefault;
    if(object instanceof Boolean) {
      return ((Boolean) object).booleanValue();
    }
    if(object instanceof Number) {
      return ((Number) object).intValue() != 0;
    }
    String sValue = object.toString();
    if(sValue.length() == 0) return bDefault;
    char c0 = sValue.charAt(0);
    return "YySsTt1Jj".indexOf(c0) >= 0;
  }
  
  public static
  String toString(Object object, String sDefault)
  {
    if(object == null) return sDefault;
    if(object instanceof String) {
      return (String) object;
    }
    if(object instanceof java.util.Date) {
      return longDateToString(((java.util.Date) object).getTime(), false);
    }
    if(object instanceof java.util.Calendar) {
      return longDateToString(((java.util.Calendar) object).getTimeInMillis(), false);
    }
    return object.toString();
  }
  
  public static
  String toString(Object object, String sDefault, int iMaxLength)
  {
    String sResult = toString(object, sDefault);
    if(sResult != null && iMaxLength > 0 && sResult.length() > iMaxLength) {
      return sResult.substring(0, iMaxLength);
    }
    return sResult;
  }
  
  public static
  String toUpperString(Object object, String sDefault)
  {
    String sResult = toString(object, sDefault);
    if(sResult != null) return sResult.toUpperCase();
    return null;
  }
  
  public static
  String toUpperString(Object object, String sDefault, int iMaxLength)
  {
    String sResult = toUpperString(object, sDefault);
    if(sResult != null && iMaxLength > 0 && sResult.length() > iMaxLength) {
      return sResult.substring(0, iMaxLength);
    }
    return sResult;
  }
  
  public static
  String toLowerString(Object object, String sDefault)
  {
    String sResult = toString(object, sDefault);
    if(sResult != null) return sResult.toLowerCase();
    return null;
  }
  
  public static
  String toLowerString(Object object, String sDefault, int iMaxLength)
  {
    String sResult = toLowerString(object, sDefault);
    if(sResult != null && iMaxLength > 0 && sResult.length() > iMaxLength) {
      return sResult.substring(0, iMaxLength);
    }
    return sResult;
  }
  
  public static
  java.util.Date toDate(int iValue, int iDefault)
  {
    if(iValue < 10000) {
      if(iDefault < 10000) return null;
      return toDate(iDefault, 0);
    }
    return new java.util.Date(intToLongDate(iValue));
  }
  
  public static
  java.util.Date toDate(Object object, Object oDefault)
  {
    if(object == null) {
      if(oDefault == null) return null;
      return toDate(oDefault, null);
    }
    if(object instanceof java.sql.Date) {
      return new java.util.Date(((java.sql.Date) object).getTime());
    }
    if(object instanceof java.sql.Timestamp) {
      return new java.util.Date(((java.sql.Timestamp) object).getTime());
    }
    if(object instanceof java.util.Date) {
      return (java.util.Date) object;
    }
    if(object instanceof java.util.Calendar) {
      return new java.util.Date(((java.util.Calendar) object).getTimeInMillis());
    }
    if(object instanceof Long) {
      return new java.util.Date(((Long) object).longValue());
    }
    if(object instanceof Number) {
      int iDate = ((Number) object).intValue();
      if(iDate < 10000) return toDate(oDefault, null);
      return new java.util.Date(intToLongDate(iDate));
    }
    String sValue = object.toString();
    if(sValue == null || sValue.length() == 0) return toDate(oDefault, null);
    String sDate = normalizeStringDate(sValue);
    if(sDate == null) return toDate(oDefault, null);
    return new java.util.Date(stringToLongDate(sDate));
  }
  
  public static
  java.util.Date toTime(Object object, Object oDefault)
  {
    if(object == null) {
      if(oDefault == null) return null;
      return toTime(oDefault, null);
    }
    if(object instanceof java.sql.Date) {
      return new java.util.Date(((java.sql.Date) object).getTime());
    }
    if(object instanceof java.sql.Timestamp) {
      return new java.util.Date(((java.sql.Timestamp) object).getTime());
    }
    if(object instanceof java.util.Date) {
      return (java.util.Date) object;
    }
    if(object instanceof java.util.Calendar) {
      return new java.util.Date(((java.util.Calendar) object).getTimeInMillis());
    }
    if(object instanceof Long) {
      return new java.util.Date(((Long) object).longValue());
    }
    if(object instanceof Number) {
      return intToTime(((Number) object).intValue());
    }
    String sValue = object.toString();
    if(sValue == null || sValue.length() == 0) return toTime(oDefault, null);
    return stringToTime(sValue);
  }
  
  public static
  java.sql.Date toSQLDate(Object object, Object oDefault)
  {
    if(object == null) {
      if(oDefault == null) return null;
      return toSQLDate(oDefault, null);
    }
    if(object instanceof java.sql.Date) {
      return (java.sql.Date) object;
    }
    if(object instanceof java.util.Date) {
      return new java.sql.Date(((java.util.Date) object).getTime());
    }
    if(object instanceof java.util.Calendar) {
      return new java.sql.Date(((java.util.Calendar) object).getTimeInMillis());
    }
    if(object instanceof Long) {
      return new java.sql.Date(((Long) object).longValue());
    }
    if(object instanceof Number) {
      int iDate = ((Number) object).intValue();
      if(iDate < 10000) return toSQLDate(oDefault, null);
      return new java.sql.Date(intToLongDate(iDate));
    }
    String sValue = object.toString();
    if(sValue == null || sValue.length() == 0) return toSQLDate(oDefault, null);
    String sDate = normalizeStringDate(sValue);
    if(sDate == null) return toSQLDate(oDefault, null);
    return new java.sql.Date(stringToLongDate(sDate));
  }
  
  public static
  java.sql.Timestamp toSQLTimestamp(Object object, Object oDefault)
  {
    if(object == null) {
      if(oDefault == null) return null;
      return toSQLTimestamp(oDefault, null);
    }
    if(object instanceof java.sql.Timestamp) {
      return (java.sql.Timestamp) object;
    }
    if(object instanceof java.util.Date) {
      return new java.sql.Timestamp(((java.util.Date) object).getTime());
    }
    if(object instanceof java.util.Calendar) {
      return new java.sql.Timestamp(((java.util.Calendar) object).getTimeInMillis());
    }
    if(object instanceof Long) {
      return new java.sql.Timestamp(((Long) object).longValue());
    }
    if(object instanceof Number) {
      int iDate = ((Number) object).intValue();
      if(iDate < 10000) return toSQLTimestamp(oDefault, null);
      return new java.sql.Timestamp(intToLongDate(iDate));
    }
    String sValue = object.toString();
    if(sValue == null || sValue.length() == 0) return toSQLTimestamp(oDefault, null);
    Date time = stringToTime(sValue);
    if(time == null) return toSQLTimestamp(oDefault, null);
    return new java.sql.Timestamp(time.getTime());
  }
  
  public static
  java.util.Calendar toCalendar(int iValue, int iDefault)
  {
    if(iValue < 10000) {
      if(iDefault < 10000) return null;
      return toCalendar(iDefault, 0);
    }
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(intToLongDate(iValue));
    return cal;
  }
  
  public static
  java.util.Calendar toCalendar(Object object, Object oDefault)
  {
    if(object == null) {
      if(oDefault == null) return null;
      return toCalendar(oDefault, null);
    }
    if(object instanceof java.util.Date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(((java.util.Date) object).getTime());
      return calendar;
    }
    if(object instanceof java.util.Calendar) {
      return (java.util.Calendar) object;
    }
    if(object instanceof Long) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(((Long) object).longValue());
      return calendar;
    }
    if(object instanceof Number) {
      int iDate = ((Number) object).intValue();
      if(iDate < 10000) return toCalendar(oDefault, null);
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(intToLongDate(iDate));
      return cal;
    }
    String sValue = object.toString();
    if(sValue == null || sValue.length() == 0) return toCalendar(oDefault, null);
    String sDate = normalizeStringDate(sValue);
    if(sDate == null) return toCalendar(oDefault, null);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(stringToLongDate(sDate));
    return cal;
  }
  
  public static
  int toIntDate(Object object, int iDefault)
  {
    Date date = toDate(object, null);
    if(date == null) return iDefault;
    return longDateToInt(date.getTime());
  }
  
  public static
  int toIntTime(Object object, int iDefault)
  {
    java.util.Date time = toTime(object, null);
    if(time == null) return iDefault;
    return timeToInt(time);
  }
  
  public static
  String toStringTime(Object object, String sDefault)
  {
    java.util.Date time = toTime(object, null);
    if(time == null) return sDefault;
    return timeToString(time);
  }
  
  public static
  List toList(Object object, boolean notNull)
  {
    if(object == null) return notNull ? new ArrayList(0) : null;
    if(object instanceof ArrayList) {
      return (ArrayList) object;
    }
    if(object instanceof Collection) {
      Collection collection = (Collection) object;
      ArrayList arrayList = new ArrayList(collection.size());
      Iterator iterator = collection.iterator();
      while(iterator.hasNext()) {
        arrayList.add(iterator.next());
      }
      return arrayList;
    }
    if(object.getClass().isArray()) {
      int length = Array.getLength(object);
      ArrayList arrayList = new ArrayList(length);
      for(int i = 0; i < length; i++) {
        arrayList.add(Array.get(object, i));
      }
      return arrayList;
    }
    if(object instanceof String) {
      return stringToList((String) object);
    }
    ArrayList arrayList = new ArrayList(1);
    arrayList.add(object);
    return arrayList;
  }
  
  public static
  List toList(Object object, Object oDefault)
  {
    List list = toList(object, false);
    if(list == null) {
      if(oDefault == null) return null;
      return toList(oDefault, false);
    }
    return list;
  }
  
  public static
  List<String> stringToList(String sText)
  {
    if(sText == null || sText.length() == 0) return new ArrayList(0);
    if(sText.startsWith("[") && sText.endsWith("]")) {
      sText = sText.substring(1, sText.length()-1);
    }
    ArrayList arrayList = new ArrayList();
    int iIndexOf = 0;
    int iBegin   = 0;
    iIndexOf     = sText.indexOf(',');
    while(iIndexOf >= 0) {
      arrayList.add(sText.substring(iBegin, iIndexOf));
      iBegin = iIndexOf + 1;
      iIndexOf = sText.indexOf(',', iBegin);
    }
    arrayList.add(sText.substring(iBegin));
    return arrayList;
  }
  
  public static 
  int longDateToInt(long lDate) 
  {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(lDate);
    return cal.get(Calendar.YEAR)*10000 + (cal.get(Calendar.MONTH)+1)*100 + cal.get(Calendar.DAY_OF_MONTH);
  }
  
  public static 
  int timeToInt(java.util.Date time) 
  {
    if(time == null) return 0;
    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    return cal.get(Calendar.HOUR_OF_DAY)*100 + cal.get(Calendar.MINUTE);
  }
  
  public static 
  String timeToString(java.util.Date time) 
  {
    if(time == null) return null;
    Calendar cal = Calendar.getInstance();
    cal.setTime(time);
    int iHH = cal.get(Calendar.HOUR_OF_DAY);
    int iMM = cal.get(Calendar.MINUTE);
    String sHH = iHH < 10 ? "0" + iHH : String.valueOf(iHH);
    String sMM = iMM < 10 ? "0" + iMM : String.valueOf(iMM);
    return sHH + ":" + sMM;
  }
  
  public static 
  String longDateToString(long lDateTime, boolean boAddTime) 
  {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(lDateTime);
    int iYear  = cal.get(java.util.Calendar.YEAR);
    int iMonth = cal.get(java.util.Calendar.MONTH) + 1;
    int iDay   = cal.get(java.util.Calendar.DATE);
    String sTime = "";
    if(boAddTime) {
      int iHH = cal.get(Calendar.HOUR_OF_DAY);
      int iMM = cal.get(Calendar.MINUTE);
      int iSS = cal.get(Calendar.SECOND);
      String sHH = iHH < 10 ? "0" + iHH : String.valueOf(iHH);
      String sMM = iMM < 10 ? "0" + iMM : String.valueOf(iMM);
      String sSS = iSS < 10 ? "0" + iSS : String.valueOf(iSS);
      sTime = " " + sHH + ":" + sMM + ":" + sSS;
    }
    String sMonth = iMonth < 10 ? "0" + iMonth : String.valueOf(iMonth);
    String sDay   = iDay   < 10 ? "0" + iDay   : String.valueOf(iDay);
    return sDay + "/" + sMonth + "-" + iYear + sTime;
  }
  
  public static 
  long intToLongDate(int iDate) 
  {
    if (iDate < 10000) return System.currentTimeMillis();
    int iYear  = iDate / 10000;
    int iMonth = (iDate % 10000) / 100;
    int iDay   = (iDate % 10000) % 100;
    Calendar calendar = new GregorianCalendar(iYear, iMonth - 1, iDay);
    return calendar.getTimeInMillis();
  }
  
  public static 
  long stringToLongDate(String sDate) 
  {
    if (sDate == null) return System.currentTimeMillis();
    int iDate = 0;
    try { iDate = Integer.parseInt(sDate); } catch(Exception ex) { return System.currentTimeMillis(); }
    int iYear  = iDate / 10000;
    int iMonth = (iDate % 10000) / 100;
    int iDay   = (iDate % 10000) % 100;
    Calendar calendar = new GregorianCalendar(iYear, iMonth-1, iDay);
    return calendar.getTimeInMillis();
  }
  
  public static 
  java.util.Date intToTime(int iTime) 
  {
    if(iTime < 0) return null;
    int iHH = 0;
    int iMM = 0;
    int iSS = 0;
    if(iTime < 10000) {
      iHH = iTime / 100;
      iMM = (iTime % 100);
    }
    else {
      iHH = iTime / 10000;
      iMM = (iTime % 10000) / 100;
      iSS = (iTime % 10000) % 100;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, iHH);
    calendar.set(Calendar.MINUTE,      iMM);
    calendar.set(Calendar.SECOND,      iSS);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
  
  public static 
  java.util.Date stringToTime(String sTime) 
  {
    if(sTime == null || sTime.length() == 0) return null;
    String sNorm = normalizeStringTime(sTime);
    if(sNorm == null || sNorm.length() < 4) return null;
    int iHH = 0;
    int iMM = 0;
    int iSS = 0;
    try { iHH = Integer.parseInt(sNorm.substring(0, 2)); } catch(Throwable th) {}
    try { iMM = Integer.parseInt(sNorm.substring(2, 4)); } catch(Throwable th) {}
    if(sNorm.length() > 5) {
      try { iSS = Integer.parseInt(sNorm.substring(4, 6)); } catch(Throwable th) {}
    }
    Calendar calendar = Calendar.getInstance();
    if(sTime.length() > 10 || sNorm.equals("000000")) {
      String sDate = normalizeStringDate(sTime);
        if(sDate != null && sDate.length() > 0) {
        int iDate  = 0;
        try { 
          iDate = Integer.parseInt(sDate); 
          int iYear  = iDate / 10000;
          int iMonth = (iDate % 10000) / 100;
          int iDay   = (iDate % 10000) % 100;
          calendar.set(Calendar.YEAR,  iYear);
          calendar.set(Calendar.MONTH, iMonth-1);
          calendar.set(Calendar.DATE,  iDay);
        } 
        catch(Throwable th) { 
        }
      }
    }
    calendar.set(Calendar.HOUR_OF_DAY, iHH);
    calendar.set(Calendar.MINUTE,      iMM);
    calendar.set(Calendar.SECOND,      iSS);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }
  
  public static
  String normalizeStringDate(String sValue)
  {
    return normalizeStringDate(sValue, false);
  }
  
  public static
  String normalizeStringDate(String sValue, boolean boExcludeNumber)
  {
    if(sValue == null) return null;
    sValue = sValue.trim();
    if(sValue.length() == 0) return null;
    if(sValue.length() >= 28 && Character.isLetter(sValue.charAt(0))) {
      // Ad es. Tue Jan 01 00:00:00 CET 2013
      String sYear = sValue.substring(sValue.length() - 4);
      try { Integer.parseInt(sYear); } catch(Throwable th) { return null; }
      String sMonth = getMonth(sValue.substring(4, 7));
      if(sMonth == null || sMonth.length() < 2) return null;
      String sDay = sValue.substring(8, 10);
      int iDay = 0;
      try { iDay = Integer.parseInt(sDay); } catch(Throwable th) { return null; }
      if(iDay < 1 || iDay > 31) return null;
      return sYear + sMonth + sDay;
    }
    int iFirstSep = sValue.indexOf('/');
    if(iFirstSep < 0) {
      iFirstSep = sValue.indexOf('-');
      if(iFirstSep <= 0) {
        iFirstSep = sValue.indexOf('.');
        if(iFirstSep <= 0) {
          if(boExcludeNumber) return null;
          if(sValue.length() > 8) {
            long lValue = 0;
            try { lValue = Long.parseLong(sValue); } catch(Throwable th) { return null; }
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(lValue);
            int iDate = cal.get(Calendar.YEAR)*10000 + (cal.get(Calendar.MONTH)+1)*100 + cal.get(Calendar.DAY_OF_MONTH);
            return String.valueOf(iDate);
          }
          if(sValue.length() != 8) return null;
          try { Integer.parseInt(sValue); } catch(Throwable th) { return null; }
          return sValue;
        }
      }
    }
    int iSecondSep = sValue.indexOf('/', iFirstSep + 1);
    if(iSecondSep < 0) {
      iSecondSep = sValue.indexOf('-', iFirstSep + 1);
      if(iSecondSep < 0) {
        iSecondSep = sValue.indexOf('.', iFirstSep + 1);
        if(iSecondSep < 0) return null;
      }
    }
    String sDay   = null;
    String sMonth = null;
    String sYear  = null;
    if(iFirstSep >= 4) {
      // year - month - day
      sYear  = sValue.substring(0, iFirstSep).trim();
      sMonth = sValue.substring(iFirstSep + 1, iSecondSep).trim();
      sDay   = sValue.substring(iSecondSep + 1).trim();
      if(sDay.length() > 2) sDay = sDay.substring(0, 2);
    }
    else {
      // day - month - year
      sDay   = sValue.substring(0, iFirstSep).trim();
      sMonth = sValue.substring(iFirstSep + 1, iSecondSep).trim();
      sYear  = sValue.substring(iSecondSep + 1).trim();
      if(sYear.length() > 4) sYear = sYear.substring(0, 4);
    }
    // Check Day
    if(sDay.length() == 0) sDay = "01";
    else
    if(sDay.length() == 1) sDay = "0" + sDay;
    int iDay = 0;
    try { iDay = Integer.parseInt(sDay); } catch(Throwable th) { return null; }
    if(iDay < 1 || iDay > 31) return null;
    // Check Month
    if(sMonth.length() > 2 && Character.isLetter(sMonth.charAt(0))) {
      sMonth = getMonth(sMonth);
      if(sMonth == null) return null;
    }
    if(sMonth.length() == 0) sMonth = "01";
    else
    if(sMonth.length() == 1) sMonth = "0" + sMonth;
    int iMonth = 0;
    try { iMonth = Integer.parseInt(sMonth); } catch(Throwable th) { return null; }
    if(iMonth < 1 || iMonth > 12) return null;
    // Check Year
    int iYear = 0;
    try{ iYear = Integer.parseInt(sYear); } catch(Throwable th) { return null; }
    if(iYear >= 0 && iYear < 100 && sValue.length() < 10) {
      Calendar cal = Calendar.getInstance();
      int iCurrCentury = cal.get(Calendar.YEAR) / 100;
      sYear = String.valueOf(iCurrCentury * 100 + iYear);
    }
    else if(iYear < 1000 && sValue.length() < 10) {
      return null;
    }
    if(iYear > 9999) return null;
    return sYear + sMonth + sDay;
  }
  
  public static
  String getMonth(String sMonth)
  {
    String sMonthLC = sMonth.toLowerCase();
    if(sMonthLC.startsWith("jan") || sMonthLC.startsWith("ge"))  return "01";
    if(sMonthLC.startsWith("f"))   return "02";
    if(sMonthLC.startsWith("mar")) return "03";
    if(sMonthLC.startsWith("ap"))  return "04";
    if(sMonthLC.startsWith("may") || sMonthLC.startsWith("mag")) return "05";
    if(sMonthLC.startsWith("jun") || sMonthLC.startsWith("gi"))  return "06";
    if(sMonthLC.startsWith("jul") || sMonthLC.startsWith("lu"))  return "07";
    if(sMonthLC.startsWith("au")  || sMonthLC.startsWith("ag"))  return "08";
    if(sMonthLC.startsWith("s")) return "09";
    if(sMonthLC.startsWith("o")) return "10";
    if(sMonthLC.startsWith("n")) return "11";
    if(sMonthLC.startsWith("d")) return "12";
    return null;
  }
  
  public static
  String normalizeStringTime(String sValue)
  {
    if(sValue == null) return "000000";
    sValue = sValue.trim();
    if(sValue.length() == 0) return "000000";
    int iFirstSep = sValue.indexOf(':');
    if(iFirstSep < 0) {
      iFirstSep = sValue.indexOf(',');
      if(iFirstSep <= 0) {
        iFirstSep = sValue.indexOf('.');
        if(iFirstSep <= 0) {
          try{ Integer.parseInt(sValue); } catch(Throwable th) { return "000000"; }
          if(sValue.length() >  6) return sValue.substring(0, 6);
          if(sValue.length() == 1) sValue = "000" + sValue;
          if(sValue.length() == 2) sValue = "00"  + sValue;
          if(sValue.length() == 3) sValue = "0"   + sValue;
          if(sValue.length() == 4) sValue = sValue +  "00";
          if(sValue.length() == 5) sValue = sValue +   "0";
          return sValue;
        }
      }
    }
    int iSecondSep = sValue.indexOf(':', iFirstSep + 1);
    if(iSecondSep < 0) {
      iSecondSep = sValue.indexOf(',', iFirstSep + 1);
      if(iSecondSep < 0) {
        iSecondSep = sValue.indexOf('.', iFirstSep + 1);
      }
    }
    String sHH = "";
    String sMM = "";
    String sSS = "";
    if(iFirstSep > 0) {
      int iBegin = 0;
      if(iFirstSep > 5) iBegin = iFirstSep - 2;
      sHH = sValue.substring(iBegin, iFirstSep).trim();
      if(iSecondSep > 0) {
        sMM = sValue.substring(iFirstSep + 1, iSecondSep).trim();
        sSS = sValue.substring(iSecondSep + 1).trim();
        if(sSS.length() > 2) sSS = sSS.substring(0, 2);
      }
      else {
        sMM = sValue.substring(iFirstSep + 1).trim();
        sSS = "00";
        if(sMM.length() > 2) sMM = sMM.substring(0, 2);
      }
    }
    // Check Hour
    if(sHH.length() == 0) sHH = "00";
    else
    if(sHH.length() == 1) sHH = "0" + sHH;
    int iHH = 0;
    try { iHH = Integer.parseInt(sHH); } catch(Throwable th) { return "000000"; }
    if(iHH < 0 || iHH > 23) return "000000";
    // Check Minutes
    if(sMM.length() == 0) sMM = "00";
    else
    if(sMM.length() == 1) sMM = "0" + sMM;
    int iMM = 0;
    try { iMM = Integer.parseInt(sMM); } catch(Throwable th) { return "000000"; }
    if(iMM < 0 || iMM > 59) return "000000";
    // Check Seconds
    if(sSS.length() == 0) sSS = "00";
    else
    if(sSS.length() == 1) sSS = "0" + sSS;
    int iSS = 0;
    try { iSS = Integer.parseInt(sSS); } catch(Throwable th) { return "000000"; }
    if(iSS < 0 || iSS > 59) return "000000";
    return sHH + sMM + sSS;
  }
}