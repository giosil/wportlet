package org.dew.portlet.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    if(!(o instanceof List)) {
      if(itemClass != null && itemClass.isInstance(o)) {
        List<T> listResult = new ArrayList<T>();
        listResult.add((T) o);
        return listResult;
      }
      // WARNING
      warn(o, "List", itemClass, null, null);
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
    warn(o, "List", itemClass, null, null);
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
  
  public static
  List<Map<String,Object>> expectListOfMap(Object o)
  {
    return expectListOfMap(o, false);
  }
  
  @SuppressWarnings("unchecked")
  public static
  List<Map<String,Object>> expectListOfMap(Object o, boolean emptyListDefault)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new ArrayList<Map<String,Object>>();
      }
      return null;
    }
    if(!(o instanceof List)) {
      if(o instanceof Map) {
        List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
        listResult.add((Map<String,Object>) o);
        return listResult;
      }
      // WARNING
      warn(o, "List<Map<String,Object>>", null, null, null);
      if(emptyListDefault) {
        return new ArrayList<Map<String,Object>>();
      }
      return null;
    }
    int size = ((List<?>) o).size();
    if(size == 0) {
      return (List<Map<String,Object>>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (List<Map<String,Object>>) o;
    }
    if(item0 instanceof Map) {
      return (List<Map<String,Object>>) o;
    }
    // WARNING
    warn(o, "List<Map<String,Object>>", null, null, null);
    if(emptyListDefault) {
      return new ArrayList<Map<String,Object>>();
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> 
  List<Map<String,Object>> expectListOfMap(Object o, boolean emptyListDefault, Object module, Object parameters)
  {
    if(o == null) {
      if(emptyListDefault) {
        return new ArrayList<Map<String,Object>>();
      }
      return null;
    }
    if(!(o instanceof List)) {
      if(o instanceof Map) {
        List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
        listResult.add((Map<String,Object>) o);
        return listResult;
      }
      // WARNING
      warn(o, "List<Map<String,Object>>", null, module, parameters);
      if(emptyListDefault) {
        return new ArrayList<Map<String,Object>>();
      }
      return null;
    }
    int size = ((List<?>) o).size();
    if(size == 0) {
      return (List<Map<String,Object>>) o;
    }
    Object item0 = ((List<?>) o).get(0);
    if(item0 == null) {
      return (List<Map<String,Object>>) o;
    }
    if(item0 instanceof Map) {
      return (List<Map<String,Object>>) o;
    }
    // WARNING
    warn(o, "List<Map<String,Object>>", null, module, parameters);
    if(emptyListDefault) {
      return new ArrayList<Map<String,Object>>();
    }
    return null;
  }
  
  public static 
  Map<String,Object> expectMap(Object o)
  {
    return expectMap(o, false);
  }
  
  @SuppressWarnings("unchecked")
  public static 
  Map<String,Object> expectMap(Object o, boolean emptyMapDefault)
  {
    if(o == null) {
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    if(!(o instanceof Map)) {
      // WARNING
      warn(o, "Map<String,Object>", null, null, null);
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    return (Map<String,Object>) o;
  }
  
  @SuppressWarnings("unchecked")
  public static 
  Map<String,Object> expectMap(Object o, boolean emptyMapDefault, Object module, Object parameters)
  {
    if(o == null) {
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    if(!(o instanceof Map)) {
      // WARNING
      warn(o, "Map<String,Object>", null, module, parameters);
      if(emptyMapDefault) {
        return new HashMap<String, Object>();
      }
      return null;
    }
    return (Map<String,Object>) o;
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
    // WARNING
    warn(o, null, itemClass, null, null);
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
      if(o instanceof List) {
        int size = ((List<?>) o).size();
        if(size > 0) {
          Object item0 = ((List<?>) o).get(0);
          if(item0 != null) {
            g += "<" + item0.getClass().getCanonicalName() + ">";
          }
        }
      }
      message += " Found: " + o.getClass().getCanonicalName() + g;
    }
    
    if(parameters != null) {
      message += " Parameters: " + parameters;
    }
    
    SnapTracer.trace(module, message);
  }
}
