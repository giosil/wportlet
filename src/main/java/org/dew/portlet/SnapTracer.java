package org.dew.portlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("rawtypes")
public 
class SnapTracer
{
  private final static int LENGTH = 250;
  private final static String[] buffer = new String[LENGTH];
  private final static AtomicInteger atomicInteger = new AtomicInteger(0);
  
  public static
  void trace(Object module, String message) 
  {
    String sModule = "";
    if(module instanceof String) {
      sModule = (String) module;
    }
    else
    if(module instanceof Class) {
      sModule = ((Class) module).getName();
    }
    else
    if(module != null) {
      sModule = module.getClass().getName();
    }
    write(0, sModule + "|" + message);
  }
  
  public static
  void trace(Object module, String method, String message) 
  {
    String sModule = "";
    if(module instanceof String) {
      sModule = (String) module;
    }
    else
    if(module instanceof Class) {
      sModule = ((Class) module).getName();
    }
    else
    if(module != null) {
      sModule = module.getClass().getName();
    }
    if(method != null && method.length() > 0) {
      sModule += "." + method;
    }
    write(0, sModule + "|" + message);
  }
  
  public static
  void trace(Object module, Throwable throwable) 
  {
    String sModule = "";
    if(module instanceof String) {
      sModule = (String) module;
    }
    else
    if(module instanceof Class) {
      sModule = ((Class) module).getName();
    }
    else
    if(module != null) {
      sModule = module.getClass().getName();
    }
    String message = throwable != null ? throwable.toString() : "throwable=null"; 
    write(1, sModule + "|" + message);
  }
  
  public static
  void trace(Object module, String method, Throwable throwable) 
  {
    String sModule = "";
    if(module instanceof String) {
      sModule = (String) module;
    }
    else
    if(module instanceof Class) {
      sModule = ((Class) module).getName();
    }
    else
    if(module != null) {
      sModule = module.getClass().getName();
    }
    if(method != null && method.length() > 0) {
      sModule += "." + method;
    }
    String message = throwable != null ? throwable.toString() : "throwable=null";
    write(1, sModule + "|" + message);
  }
  
  public static
  List<Map<String,Object>> listMessages()
  {
    return listMessages(null);
  }
  
  public static
  List<Map<String,Object>> listMessages(String filterModule)
  {
    List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>(LENGTH);
    int index = atomicInteger.get();
    if(index >= LENGTH) index = 0;
    int count = 0;
    while(count <= LENGTH) {
      index--;
      count++;
      if(index < 0) index = LENGTH-1;
      String item = buffer[index];
      if(item == null) break;
      
      // code|datetime|module|message
      int iSep1 = item.indexOf('|');
      if(iSep1 < 0) continue;
      int iSep2 = item.indexOf('|',iSep1+1);
      if(iSep2 < 0) continue;
      int iSep3 = item.indexOf('|',iSep2+1);
      if(iSep3 < 0) continue;
      
      String type     = item.substring(0,       iSep1);
      String dateTime = item.substring(iSep1+1, iSep2);
      String module   = item.substring(iSep2+1, iSep3);
      String message  = item.substring(iSep3+1);
      
      if(filterModule != null && filterModule.length() > 0) {
        if(!filterModule.equals(module)) {
          continue;
        }
      }
      
      Map<String,Object> mapItem = new HashMap<String,Object>(4);
      mapItem.put("t", type);
      mapItem.put("d", dateTime);
      mapItem.put("o", module);
      mapItem.put("m", message);
      
      listResult.add(mapItem);
    }
    return listResult;
  }
  
  protected static
  void write(int type, String message) 
  {
    int index = atomicInteger.getAndIncrement();
    atomicInteger.compareAndSet(LENGTH, 0);
    if(index >= LENGTH) {
      atomicInteger.set(0);
      index = 0;
    }
    String logMessage = type + "|" + Parameters.formatDateTime(new Date(), "-", true) + "|" + message;
    buffer[index] = logMessage;
  }
}