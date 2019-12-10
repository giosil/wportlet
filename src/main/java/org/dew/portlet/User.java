package org.dew.portlet;

import java.io.Serializable;
import java.util.*;

/**
 * Bean che raccoglie le informazioni dell'utente.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public
class User implements Serializable
{
  private static final long serialVersionUID = -8306744803593044134L;
  
  private long    id;
  private String  sessionId;
  private String  userName;
  private String  password;
  private String  role;
  private String  email;
  private String  mobile;
  private List<String> groups;
  private Object  profile;
  private boolean portalUser;
  private int     authLevel;
  
  private Map<String,Object> resources;
  private Map<String,Object> menu;
  
  public User()
  {
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getSessionId() {
    return sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public String getUserName() {
    return userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getRole() {
    return role;
  }
  
  public void setRole(String role) {
    this.role = role;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getMobile() {
    return mobile;
  }
  
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  
  public List<String> getGroups() {
    return groups;
  }
  
  public void setGroups(List<String> groups) {
    this.groups = groups;
  }
  
  public Object getProfile() {
    return profile;
  }
  
  public void setProfile(Object profile) {
    this.profile = profile;
  }
  
  public boolean isPortalUser() {
    return portalUser;
  }
  
  public void setPortalUser(boolean portalUser) {
    this.portalUser = portalUser;
  }
  
  public int getAuthLevel() {
    return authLevel;
  }
  
  public void setAuthLevel(int authLevel) {
    this.authLevel = authLevel;
  }
  
  public Map<String, Object> getResources() {
    return resources;
  }
  
  public void setResources(Map<String, Object> resources) {
    this.resources = resources;
  }
  
  public Map<String, Object> getMenu() {
    return menu;
  }
  
  public void setMenu(Map<String, Object> menu) {
    this.menu = menu;
  }
  
  /**
   * Ottiene una mappa di risorse le cui chiavi sono gerarchicamente
   * appartenenti ad una risorsa individuata dal parametro specificato.
   * In altre parole vengono selezionate tutte le chiavi che iniziano per:
   * sFather + "."
   * Si noti che le chiavi della mappa restituita sono ottenute escludendo
   * il prefisso.
   *
   * @param sFather String
   * @param boRemove Flag rimozione chiavi
   * @return Map
   */
  public
  Map getResourcesByFather(String sFather, boolean boRemove)
  {
    Map mapResult = new HashMap();
    if(resources == null) return mapResult;
    String sPrefix = sFather + ".";
    Iterator oItKeys = resources.keySet().iterator();
    while(oItKeys.hasNext()) {
      String sKey = oItKeys.next().toString();
      if(sKey.startsWith(sPrefix)) {
        Object oValue = resources.get(sKey);
        if(boRemove) {
          oItKeys.remove();
        }
        if(sPrefix.length() < sKey.length()) {
          String sNewKey = sKey.substring(sPrefix.length());
          mapResult.put(sNewKey, oValue);
        }
      }
    }
    return mapResult;
  }
  
  /**
   * Ottiene una lista di valori dalle risorse considerando le chiavi
   * che hanno il prefisso specificato.
   *
   * @param sPrefix String
   * @return List
   */
  public
  List getListResourceValuesByPrefix(String sPrefix)
  {
    List listResult = new ArrayList();
    if(resources == null) return listResult;
    Iterator oItKeys = resources.keySet().iterator();
    while(oItKeys.hasNext()) {
      String sKey = oItKeys.next().toString();
      if(sKey.startsWith(sPrefix)) {
        Object oValue = resources.get(sKey);
        listResult.add(oValue);
      }
    }
    return listResult;
  }
  
  /**
   * Restituisce la rappresentazione HTML dell'utente.
   * @return String
   */
  public
  String toHTML()
  {
    String sResult = "<b>UserName:</b> " + userName + "<br>";
    if(role != null) {
      sResult += "<b>Ruolo:</b> " + role + "<br>";
    }
    // Risorse
    if(resources != null) {
      sResult += "<b>Risorse:</b><br>";
      Object[] aoKeys = resources.keySet().toArray();
      Arrays.sort(aoKeys);
      for(int i = 0; i < aoKeys.length; i++) {
        Object oKey   = aoKeys[i];
        Object oValue = resources.get(oKey);
        sResult += "&nbsp;&nbsp;&nbsp;&nbsp;" + oKey + " = ";
        sResult += oValue + "<br>";
      }
    }
    return sResult;
  }
  
  @Override
  public
  String toString()
  {
    return userName;
  }
  
  @Override
  public
  int hashCode()
  {
    if(userName == null) return 0;
    return userName.hashCode();
  }
  
  @Override
  public
  boolean equals(Object anObject)
  {
    if(this == anObject) return true;
    if(anObject instanceof User) {
      String sUserName = ((User) anObject).getUserName();
      return sUserName != null && sUserName.equals(userName);
    }
    return false;
  }
}
