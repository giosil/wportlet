package org.dew.portlet.ui;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
}
