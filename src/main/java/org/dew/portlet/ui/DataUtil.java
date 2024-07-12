package org.dew.portlet.ui;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dew.portlet.SnapTracer;

public
class DataUtil
{
  public static <T> 
  List<T> expectList(Object o, Class<T> itemClass)
  {
    return expectList(o, itemClass, false);
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  List<T> expectList(Object o, Class<T> itemClass, boolean emptyListDefault)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new ArrayList<T>();
      }
      return null;
    }
    if(o.getClass().isArray()) {
      int length = Array.getLength(o);
      if(length == 0) {
        return new ArrayList<T>();
      }
      Object item0 = Array.get(o, 0);
      if(item0 != null && itemClass != null && itemClass.isInstance(item0)) {
        return Arrays.asList((T[]) o);
      }
    }
    if(!(o instanceof List)) {
      if(itemClass != null && itemClass.isInstance(o)) {
        List<T> listResult = new ArrayList<T>();
        listResult.add((T) o);
        return listResult;
      }
      if(emptyListDefault) {
        return new ArrayList<T>();
      }
      return null;
    }
    int size = ((List<?>) o).size();
    if(size == 0) {
      return (List<T>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (List<T>) o;
    }
    if(itemClass == null) {
      return (List<T>) o;
    }
    if(itemClass.isInstance(item0)) {
      return (List<T>) o;
    }
    if(emptyListDefault) {
      return new ArrayList<T>();
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  List<T> expectList(Object o, Class<T> itemClass, boolean emptyListDefault, Object module, Object parameters)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new ArrayList<T>();
      }
      return null;
    }
    if(o.getClass().isArray()) {
      int length = Array.getLength(o);
      if(length == 0) {
        return new ArrayList<T>();
      }
      Object item0 = Array.get(o, 0);
      if(item0 != null && itemClass != null && itemClass.isInstance(item0)) {
        return Arrays.asList((T[]) o);
      }
    }
    if(!(o instanceof List)) {
      if(itemClass != null && itemClass.isInstance(o)) {
        List<T> listResult = new ArrayList<T>();
        listResult.add((T) o);
        return listResult;
      }
      // WARNING
      warn(o, "List", itemClass, module, parameters);
      if(emptyListDefault) {
        return new ArrayList<T>();
      }
      return null;
    }
    int size = ((List<?>) o).size();
    if(size == 0) {
      return (List<T>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (List<T>) o;
    }
    if(itemClass == null) {
      return (List<T>) o;
    }
    if(itemClass.isInstance(item0)) {
      return (List<T>) o;
    }
    // WARNING
    warn(o, "List", itemClass, module, parameters);
    if(emptyListDefault) {
      return new ArrayList<T>();
    }
    return null;
  }
  
  public static <T> 
  Set<T> expectSet(Object o, Class<T> itemClass)
  {
    return expectSet(o, itemClass, false);
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  Set<T> expectSet(Object o, Class<T> itemClass, boolean emptyListDefault)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new HashSet<T>();
      }
      return null;
    }
    if(o.getClass().isArray()) {
      int length = Array.getLength(o);
      if(length == 0) {
        return new HashSet<T>();
      }
      Object item0 = Array.get(o, 0);
      if(item0 != null && itemClass != null && itemClass.isInstance(item0)) {
        return new HashSet<T>(Arrays.asList((T[]) o));
      }
    }
    if(!(o instanceof Set)) {
      if(itemClass != null && itemClass.isInstance(o)) {
        Set<T> setResult = new HashSet<T>();
        setResult.add((T) o);
        return setResult;
      }
      if(emptyListDefault) {
        return new HashSet<T>();
      }
      return null;
    }
    int size = ((List<?>) o).size();
    if(size == 0) {
      return (Set<T>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (Set<T>) o;
    }
    if(itemClass == null) {
      return (Set<T>) o;
    }
    if(itemClass.isInstance(item0)) {
      return (Set<T>) o;
    }
    if(emptyListDefault) {
      return new HashSet<T>();
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  Set<T> expectSet(Object o, Class<T> itemClass, boolean emptyListDefault, Object module, Object parameters)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new HashSet<T>();
      }
      return null;
    }
    if(o.getClass().isArray()) {
      int length = Array.getLength(o);
      if(length == 0) {
        return new HashSet<T>();
      }
      Object item0 = Array.get(o, 0);
      if(item0 != null && itemClass != null && itemClass.isInstance(item0)) {
        return new HashSet<T>(Arrays.asList((T[]) o));
      }
    }
    if(!(o instanceof Set)) {
      if(itemClass != null && itemClass.isInstance(o)) {
        Set<T> setResult = new HashSet<T>();
        setResult.add((T) o);
        return setResult;
      }
      // WARNING
      warn(o, "Set", itemClass, module, parameters);
      if(emptyListDefault) {
        return new HashSet<T>();
      }
      return null;
    }
    int size = ((Set<?>) o).size();
    if(size == 0) {
      return (Set<T>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (Set<T>) o;
    }
    if(itemClass == null) {
      return (Set<T>) o;
    }
    if(itemClass.isInstance(item0)) {
      return (Set<T>) o;
    }
    // WARNING
    warn(o, "Set", itemClass, module, parameters);
    if(emptyListDefault) {
      return new HashSet<T>();
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  T[] expectArray(Object o, Class<T> itemClass)
  {
    if(o == null) {
      return null;
    }
    if(o instanceof List) {
      int size = ((List<?>) o).size();
      if(size == 0) {
        return (T[]) Array.newInstance(itemClass, 0);
      }
      else {
        Object item0 = ((List<?>) o).get(0);
        if(item0 != null && itemClass != null && itemClass.isInstance(item0)) {
          T[] array = (T[]) Array.newInstance(itemClass, size);
          for(int i = 0; i < size; i++) {
            array[i] = ((List<T>) o).get(i);
          }
          return array;
        }
      }
    }
    if(!o.getClass().isArray()) {
      return null;
    }
    int length = Array.getLength(o);
    if(length == 0) {
      return (T[]) o;
    }
    Object item0 = Array.get(o, 0);
    if(item0 == null) {
      return (T[]) o;
    }
    if(itemClass == null) {
      return (T[]) o;
    }
    if(itemClass.isInstance(item0)) {
      return (T[]) o;
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  T[] expectArray(Object o, Class<T> itemClass, Object module, Object parameters)
  {
    if(o == null) {
      return null;
    }
    if(o instanceof List) {
      int size = ((List<?>) o).size();
      if(size == 0) {
        return (T[]) Array.newInstance(itemClass, 0);
      }
      else {
        Object item0 = ((List<?>) o).get(0);
        if(item0 != null && itemClass != null && itemClass.isInstance(item0)) {
          T[] array = (T[]) Array.newInstance(itemClass, size);
          for(int i = 0; i < size; i++) {
            array[i] = ((List<T>) o).get(i);
          }
          return array;
        }
      }
    }
    if(!o.getClass().isArray()) {
      warn(o, "Array", null, module, parameters);
      return null;
    }
    int length = Array.getLength(o);
    if(length == 0) {
      return (T[]) o;
    }
    Object item0 = Array.get(o, 0);
    if(item0 == null) {
      return (T[]) o;
    }
    if(itemClass == null) {
      return (T[]) o;
    }
    if(itemClass.isInstance(item0)) {
      return (T[]) o;
    }
    warn(o, "Array", null, module, parameters);
    return null;
  }
  
  public static
  List<Map<String, Object>> expectListOfMap(Object o)
  {
    return expectListOfMap(o, false);
  }
  
  @SuppressWarnings("unchecked")
  public static
  List<Map<String, Object>> expectListOfMap(Object o, boolean emptyListDefault)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new ArrayList<Map<String, Object>>();
      }
      return null;
    }
    if(!(o instanceof List)) {
      if(o instanceof Map) {
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
        listResult.add((Map<String, Object>) o);
        return listResult;
      }
      if(emptyListDefault) {
        return new ArrayList<Map<String, Object>>();
      }
      return null;
    }
    int size = ((List<?>) o).size();
    if(size == 0) {
      return (List<Map<String, Object>>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (List<Map<String, Object>>) o;
    }
    if(item0 instanceof Map) {
      return (List<Map<String, Object>>) o;
    }
    if(emptyListDefault) {
      return new ArrayList<Map<String, Object>>();
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  List<Map<String, Object>> expectListOfMap(Object o, boolean emptyListDefault, Object module, Object parameters)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new ArrayList<Map<String, Object>>();
      }
      return null;
    }
    if(!(o instanceof List)) {
      if(o instanceof Map) {
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
        listResult.add((Map<String, Object>) o);
        return listResult;
      }
      // WARNING
      warn(o, "List<Map<String, Object>>", null, module, parameters);
      if(emptyListDefault) {
        return new ArrayList<Map<String, Object>>();
      }
      return null;
    }
    int size = ((List<?>) o).size();
    if(size == 0) {
      return (List<Map<String, Object>>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (List<Map<String, Object>>) o;
    }
    if(item0 instanceof Map) {
      return (List<Map<String, Object>>) o;
    }
    // WARNING
    warn(o, "List<Map<String, Object>>", null, module, parameters);
    if(emptyListDefault) {
      return new ArrayList<Map<String, Object>>();
    }
    return null;
  }
  
  public static 
  Map<String, Object> expectMap(Object o)
  {
    return expectMap(o, false);
  }
  
  @SuppressWarnings("unchecked")
  public static 
  Map<String, Object> expectMap(Object o, boolean emptyMapDefault)
  {
    if(o == null) {
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    if(!(o instanceof Map)) {
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    return (Map<String, Object>) o;
  }
  
  @SuppressWarnings("unchecked")
  public static 
  Map<String, Object> expectMap(Object o, boolean emptyMapDefault, Object module, Object parameters)
  {
    if(o == null) {
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    if(!(o instanceof Map)) {
      // WARNING
      warn(o, "Map<String, Object>", null, module, parameters);
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    return (Map<String, Object>) o;
  }
  
  public static <T> 
  Map<String, T> expectMapOf(Object o, Class<T> itemClass)
  {
    return expectMapOf(o, itemClass, false);
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  Map<String, T> expectMapOf(Object o, Class<T> itemClass, boolean emptyMapDefault)
  {
    if(o == null) {
      if(emptyMapDefault) {
        return new HashMap<String, T>();
      }
      return null;
    }
    if(!(o instanceof Map)) {
      if(emptyMapDefault) {
        return new HashMap<String, T>();
      }
      return null;
    }
    Map<String, Object> map = (Map<String, Object>) o;
    Iterator<Object> iterator = map.values().iterator();
    if(iterator.hasNext()) {
      Object o0 = iterator.next();
      if(o0 != null && itemClass != null && !itemClass.isInstance(o0)) {
        if(emptyMapDefault) {
          return new HashMap<String, T>();
        }
        return null;
      }
    }
    return (Map<String, T>) o;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  Map<String, T> expectMapOf(Object o, Class<T> itemClass, boolean emptyMapDefault, Object module, Object parameters)
  {
    if(o == null) {
      if(emptyMapDefault) {
        return new HashMap<String, T>();
      }
      return null;
    }
    if(!(o instanceof Map)) {
      // WARNING
      if(itemClass == null) {
        warn(o, "Map<String,?>", null, module, parameters);
      }
      else {
        warn(o, "Map<String," + itemClass + ">", null, module, parameters);
      }
      if(emptyMapDefault) {
        return new HashMap<String, T>();
      }
      return null;
    }
    Map<String, Object> map = (Map<String, Object>) o;
    Iterator<Object> iterator = map.values().iterator();
    if(iterator.hasNext()) {
      Object o0 = iterator.next();
      if(o0 != null && itemClass != null && !itemClass.isInstance(o0)) {
        // WARNING
        warn(o, "Map<String," + itemClass + ">", null, module, parameters);
        if(emptyMapDefault) {
          return new HashMap<String, T>();
        }
        return null;
      }
    }
    return (Map<String, T>) o;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  T expect(Object o, Class<T> itemClass)
  {
    if(o == null) {
      return null;
    }
    if(itemClass == null) {
      return (T) o;
    }
    if(itemClass.isInstance(o)) {
      return (T) o;
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  T expect(Object o, Class<T> itemClass, Object module, Object parameters)
  {
    if(o == null) {
      return null;
    }
    if(itemClass == null) {
      return (T) o;
    }
    if(itemClass.isInstance(o)) {
      return (T) o;
    }
    // WARNING
    warn(o, null, itemClass, module, parameters);
    return null;
  }
  
  protected static
  void warn(Object o, String sExpected, Class<?> itemClass, Object module, Object parameters)
  {
    String message = "Expected: ";
    
    if(itemClass != null) {
      if(sExpected != null && sExpected.length() > 0) {
        message += sExpected + "<" + itemClass.getCanonicalName() + ">";
      }
      else {
        message += itemClass.getCanonicalName();
      }
    }
    else {
      if(sExpected != null && sExpected.length() > 0) {
        message += sExpected;
      }
      else {
        message += "?";
      }
    }
    
    if(o == null) {
      message += " Found: null"; 
    }
    else {
      String g = "";
      if(o instanceof Collection) {
        Iterator<?> iterator = ((Collection<?>) o).iterator();
        if(iterator.hasNext()) {
          Object val = iterator.next();
          String v = val != null ? val.getClass().getCanonicalName() : "?";
          g = "<" + v + ">";
        }
      }
      else if(o instanceof Map) {
        Iterator<?> iterator = ((Map<?, ?>) o).keySet().iterator();
        if(iterator.hasNext()) {
          Object key = iterator.next();
          Object val = ((Map<?, ?>) o).get(key);
          String k = key != null ? key.getClass().getCanonicalName() : "?";
          String v = val != null ? val.getClass().getCanonicalName() : "?";
          g = "<" + k + "," + v + ">";
        }
      }
      message += " Found: " + o.getClass().getCanonicalName() + g;
    }
    
    if(parameters != null) {
      message += " Parameters: " + parameters;
    }
    
    SnapTracer.trace(module, message);
  }
  
  public static
  java.util.Calendar stringToCalendar(String sTime)
  {
    if(sTime == null) return null;
    int iLength = sTime.length();
    if(iLength ==  0) return null;
    if(iLength > 19 && sTime.endsWith("Z")) {
      int iYear  = Integer.parseInt(sTime.substring( 0,  4));
      int iMonth = Integer.parseInt(sTime.substring( 5,  7));
      int iDay   = Integer.parseInt(sTime.substring( 8, 10));
      int iHour  = Integer.parseInt(sTime.substring(11, 13));
      int iMin   = Integer.parseInt(sTime.substring(14, 16));
      int iSec   = Integer.parseInt(sTime.substring(17, 19));
      int iMill  = 0;
      if(sTime.length() > 23) {
        iMill  = Integer.parseInt(sTime.substring(20, 23));
      }
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR,        iYear);
      c.set(Calendar.MONTH,       iMonth-1);
      c.set(Calendar.DATE,        iDay);
      c.set(Calendar.HOUR_OF_DAY, iHour);
      c.set(Calendar.MINUTE,      iMin);
      c.set(Calendar.SECOND,      iSec);
      c.set(Calendar.MILLISECOND, iMill);
      int iZoneOffset = c.get(Calendar.ZONE_OFFSET);
      c.add(Calendar.MILLISECOND, iZoneOffset);
      int iDST_Offset = c.get(Calendar.DST_OFFSET);
      c.add(Calendar.MILLISECOND, iDST_Offset);
      
      Calendar r = new GregorianCalendar(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
      r.set(Calendar.MILLISECOND,c.get(Calendar.MILLISECOND));
      return r;
    }
    String sDate = null;
    if(iLength > 7) {
      sDate = normalizeStringDate(sTime);
      if(sDate != null) {
        int c = sTime.indexOf(',');
        if(c == 3) {
          sTime = sTime.substring(4).trim();
          iLength = sTime.length();
        }
      }
    }
    String sNorm = normalizeStringTime(sTime, sDate != null);
    if(sNorm == null || sNorm.length() < 4) return null;
    if(sNorm.equals("000000") && sTime.length() < 5 && !Character.isDigit(sTime.charAt(0))) {
      return null;
    }
    int iHH = 0;
    int iMM = 0;
    int iSS = 0;
    int iMS = 0;
    try { iHH = Integer.parseInt(sNorm.substring(0, 2)); } catch(Throwable th) {}
    try { iMM = Integer.parseInt(sNorm.substring(2, 4)); } catch(Throwable th) {}
    if(sNorm.length() > 5) {
      try { iSS = Integer.parseInt(sNorm.substring(4, 6)); } catch(Throwable th) {}
    }
    if(sNorm.length() > 8) {
      try { iMS = Integer.parseInt(sNorm.substring(6, 9)); } catch(Throwable th) {}
    }
    Calendar calendar = Calendar.getInstance();
    if(iLength > 10 || sNorm.equals("000000")) {
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
    calendar.set(Calendar.MILLISECOND, iMS);
    return calendar;
  }
  
  public static
  String normalizeStringDate(String sValue)
  {
    if(sValue == null) return null;
    int c = sValue.indexOf(',');
    if(c == 3 && sValue.length() > 10) {
      // Sab, 15/12/2018
      return normalizeStringDate(sValue.substring(4));
    }
    sValue = sValue.trim();
    int iLength = sValue.length();
    if(iLength == 0) return null;
    if(iLength >= 28 && Character.isLetter(sValue.charAt(0)) && sValue.charAt(3) == ' ') {
      // Ad es. Tue Jan 01 00:00:00 CET 2013
      // oppure Wed Aug 09 2017 16:49:16 GMT+0200 (ora legale Europa occidentale)
      if(sValue.indexOf(" GMT") > 0 && sValue.endsWith(")")) {
        String sYear = sValue.substring(11, 15);
        try { Integer.parseInt(sYear); } catch(Throwable th) { return null; }
        String sMonth = getMonth(sValue.substring(4, 7));
        if(sMonth == null || sMonth.length() < 2) return null;
        String sDay = sValue.substring(8, 10);
        int iDay = 0;
        try { iDay = Integer.parseInt(sDay); } catch(Throwable th) { return null; }
        if(iDay < 1 || iDay > 31) return null;
        return sYear + sMonth + sDay;
      }
      else {
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
    }
    int iFirstNumber = -1;
    for(int i = 0; i < iLength; i++) {
      if(Character.isDigit(sValue.charAt(i))) {
        iFirstNumber = i;
        break;
      }
    }
    if(iFirstNumber < 0) return null;
    if(iFirstNumber > 0) {
      sValue = sValue.substring(iFirstNumber);
    }
    int iFirstSep = sValue.indexOf('/');
    if(iFirstSep < 0) {
      iFirstSep = sValue.indexOf('-');
      if(iFirstSep <= 0 || iFirstSep > 13) {
        iFirstSep = sValue.indexOf('.');
        if(iFirstSep <= 0) {
          // YYYYMMDDHHMMSS[+/-OFFS]
          if(sValue.length() >= 14) {
            try {
              int iDate = Integer.parseInt(sValue.substring(0, 8));
              if(iDate >= 10000101 && iDate <= 99991231) {
                return sValue.substring(0, 8);
              }
            }
            catch(Throwable th) {
            }
          }
          if(sValue.length() > 8) {
            long lValue = 0;
            try { lValue = Long.parseLong(sValue); } catch(Throwable th) { return null; }
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(lValue);
            int iDate = cal.get(Calendar.YEAR)*10000 +(cal.get(Calendar.MONTH)+1)*100 + cal.get(Calendar.DAY_OF_MONTH);
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
      if(iSecondSep < 0 || iSecondSep > 13) {
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
    return normalizeStringTime(sValue, false);
  }
  
  public static
  String normalizeStringTime(String sValue, boolean boValueContainsDate)
  {
    if(sValue == null) return null;
    sValue = sValue.trim();
    if(sValue.length() == 0) return null;
    int iFirstSep = sValue.indexOf(':');
    if(iFirstSep < 0) {
      iFirstSep = sValue.indexOf(',');
      if(iFirstSep <= 0) {
        iFirstSep = sValue.indexOf('.');
        if(iFirstSep <= 0) {
          int iCut = -1;
          boolean boAtLeastANumber = false;
          boolean boAllNumbers     = true;
          for(int i = 0; i < sValue.length(); i++) {
            char c = sValue.charAt(i);
            if(!Character.isDigit(c)) {
              if(i > 11 &&(c == '+' || c == '-' || c == '.')) {
                iCut = i;
                break;
              }
              boAllNumbers = false;
            }
            else {
              boAtLeastANumber = true;
            }
          }
          if(!boAtLeastANumber) return null;
          if(!boAllNumbers) {
            if(boValueContainsDate) {
              return "000000";
            }
            return null;
          }
          if(iCut > 0) sValue = sValue.substring(0, iCut);
          // YYYYMMDDHHMMSS
          if(sValue.length() == 14) return sValue.substring(8, 14);
          // YYYYMMDDHHMM
          if(sValue.length() == 12) return sValue.substring(8, 12) + "00";
          // YYYYMMDD
          if(sValue.length() == 8)  return "000000"; // Is a date
          // HHMMSS?
          if(sValue.length() >  6)  return sValue.substring(0, 6);
          // HHMM
          if(sValue.length() == 1) sValue = "000"  + sValue;
          if(sValue.length() == 2) sValue = "00"   + sValue;
          if(sValue.length() == 3) sValue = "0"    + sValue;
          if(sValue.length() == 4) sValue = sValue +   "00";
          // HHMMSS
          if(sValue.length() == 5) sValue = "0"    + sValue;
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
    int iMillis   = 0;
    int iThirdSep = -1;
    if(iSecondSep > 0) {
      iThirdSep = sValue.indexOf('.', iSecondSep + 1);
      if(iThirdSep > 0) {
        try { iMillis = Integer.parseInt(sValue.substring(iThirdSep+1,iThirdSep+4)); } catch(Throwable th) {}
        sValue = sValue.substring(0, iThirdSep);
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
        if(sSS.length() == 4) {
          int iSS = 0;
          try{ iSS = Integer.parseInt(sSS); } catch(Throwable ex) {}
          // Is a year
          if(iSS >= 1000) return "000000";
        }
        if(sSS.length() > 2) sSS = sSS.substring(0, 2);
      }
      else {
        sMM = sValue.substring(iFirstSep + 1).trim();
        sSS = "00";
        if(sMM.length() > 2) sMM = sMM.substring(0, 2);
      }
    }
    // Check Hour
    if(sHH.length() > 0) {
      char c0 = sHH.charAt(0);
      if(c0 != ' ' && !Character.isDigit(c0)) return null;
    }
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
    String sMillis = null;
    if(iMillis < 10) {
      sMillis = "00" + iMillis;
    }
    else
    if(iMillis < 100) {
      sMillis = "0" + iMillis;
    }
    else {
      sMillis = String.valueOf(iMillis);
    }
    if(iMillis > 0) {
      return sHH + sMM + sSS + sMillis;
    }
    return sHH + sMM + sSS;
  }
  
  public static
  String toString(Object object, String defaultValue)
  {
    if(object == null) {
      return defaultValue;
    }
    return object.toString();
  }
  
  public static
  java.util.Calendar toCalendar(int iValue, int iDefault)
  {
    if(iValue < 10000) {
      if(iDefault < 10000) return null;
      return toCalendar(iDefault, 0);
    }
    int iYear  = iValue / 10000;
    int iMonth = (iValue % 10000) / 100;
    int iDay   = (iValue % 10000) % 100;
    return new GregorianCalendar(iYear, iMonth-1, iDay);
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
      int iYear  = iDate / 10000;
      int iMonth = (iDate % 10000) / 100;
      int iDay   = (iDate % 10000) % 100;
      return new GregorianCalendar(iYear, iMonth-1, iDay);
    }
    Calendar cal = stringToCalendar(object.toString());
    if(cal == null) return toCalendar(oDefault, null);
    return cal;
  }
  
  @SuppressWarnings("unchecked")
  public static
  List<List<Object>> toListOfListObject(Object object)
  {
    if(object == null) {
      return null;
    }
    if(object instanceof List) {
      List<?> list = (List<?>) object;
      if(list.size() == 0) {
        return (List<List<Object>>) list;
      }
      Object item0 = list.get(0);
      if(item0 instanceof List) {
        return (List<List<Object>>) list;
      }
    }
    return null;
  }
}
