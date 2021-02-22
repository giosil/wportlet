package org.dew.portlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// portal-service-6.1.2.jar
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

// portal-kernel-7.2.1.jar
//import com.liferay.portal.kernel.model.EmailAddress;
//import com.liferay.portal.kernel.model.Phone;
//import com.liferay.portal.kernel.model.Role;
//import com.liferay.portal.kernel.model.User;
//import com.liferay.portal.kernel.upload.UploadPortletRequest;
//import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
//import com.liferay.portal.kernel.service.UserLocalServiceUtil;
//import com.liferay.portal.kernel.util.PortalUtil;

public 
class PlatformUtil 
{
  private static final int BUFF_LENGTH = 1024;
  
  public static
  void log(String message)
  {
    System.out.println(new java.sql.Timestamp(System.currentTimeMillis()) + " " + message);
  }
  
  public static
  void log(String message, Throwable th)
  {
    System.err.println(new java.sql.Timestamp(System.currentTimeMillis()) + " " + message + ": " + th);
  }
  
  public static
  org.dew.portlet.User getInternalUser(PortletRequest request)
  {
    String sLiferayUser  = "";
    long   lUserId       = 0;
    String sScreenName   = null;
    String sEmailAddress = null;
    String sPhoneNumber  = null;
    List<String> listRoles = new ArrayList<String>();
    try {
      lUserId = PrincipalThreadLocal.getUserId();
      
      User liferayUser = UserLocalServiceUtil.getUser(lUserId);
      if(liferayUser == null) return null;
      
      sScreenName   = liferayUser.getScreenName();
      
      sLiferayUser = "userId=" + lUserId + ",screenName=" + sScreenName;
      
      List<EmailAddress> listOfEmailAddress = liferayUser.getEmailAddresses();
      if(listOfEmailAddress == null || listOfEmailAddress.size() == 0) {
        sEmailAddress = liferayUser.getEmailAddress();
        sLiferayUser += ",emailAddress=" + sEmailAddress;
      }
      else {
        for(int i = 0; i < listOfEmailAddress.size(); i++) {
          EmailAddress emailAddress = listOfEmailAddress.get(i);
          if(emailAddress == null) {
            sLiferayUser += ",emailAddresses[" + i + "]=null";
            continue;
          }
          else if(emailAddress.isPrimary()) {
            sEmailAddress = emailAddress.getAddress();
            sLiferayUser += ",emailAddresses[" + i + "]*=" + sEmailAddress;
          }
          else {
            String sEmailAddress_i = emailAddress.getAddress();
            if(sEmailAddress == null || sEmailAddress.length() < 2) {
              sEmailAddress = sEmailAddress_i;
              sLiferayUser += ",emailAddresses[" + i + "]^=" + sEmailAddress_i;
            }
            else {
              sLiferayUser += ",emailAddresses[" + i + "]=" + sEmailAddress_i;
            }
          }
        }
        if(sEmailAddress == null || sEmailAddress.length() < 3) {
          sEmailAddress = liferayUser.getEmailAddress();
          sLiferayUser += ",emailAddress=" + sEmailAddress;
        }
      }
      
      List<Phone> phones = liferayUser.getPhones();
      if(phones != null && phones.size() > 0) {
        for(int i = 0; i < phones.size(); i++) {
          Phone phone = phones.get(0);
          if(phone == null) {
            sLiferayUser += ",phones[" + i + "]=null";
            continue;
          }
          else if(phone.isPrimary()) {
            sPhoneNumber = phone.getNumber();
            sLiferayUser += ",phones[" + i + "]*=" + sPhoneNumber;
          }
          else {
            String sPhoneNumber_i = phone.getNumber();
            if(sPhoneNumber == null || sPhoneNumber.length() < 2) {
              sPhoneNumber = sPhoneNumber_i;
              sLiferayUser += ",phones[" + i + "]^=" + sPhoneNumber_i;
            }
            else {
              sLiferayUser += ",phones[" + i + "]=" + sPhoneNumber_i;
            }
          }
        }
      }
      
      List<Role> roles = liferayUser.getRoles();
      if(roles != null && roles.size() > 0) {
        for(int i = 0; i < roles.size(); i++) {
          Role role = roles.get(i);
          String roleName = role.getName();
          if(roleName == null || roleName.length() == 0) continue;
          listRoles.add(roleName);
        }
        sLiferayUser += ",roles=" + listRoles;
      }
    }
    catch(Exception ex) {
      log("Exception in PlatformUtil.getInternalUser(request)", ex);
      ex.printStackTrace();
      return null;
    }
    
    if(sLiferayUser != null && sLiferayUser.length() > 0) {
      PlatformUtil.log("liferayUser: " + sLiferayUser);
    }
    
    org.dew.portlet.User result = new org.dew.portlet.User();
    result.setId(lUserId);
    result.setUserName(sScreenName);
    result.setEmail(sEmailAddress);
    result.setMobile(sPhoneNumber);
    result.setGroups(listRoles);
    return result;
  }
  
  public static
  HttpServletRequest getHttpServletRequest(PortletRequest request)
  {
    return PortalUtil.getHttpServletRequest(request);
  }
  
  public static
  Object getRequestAttribute(PortletRequest request, String attributeName)
  {
    HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
    if(httpServletRequest == null) {
      return null;
    }
    return httpServletRequest.getAttribute(attributeName);
  }
  
  public static
  Object getHttpHeader(PortletRequest request, String headerName)
  {
    HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
    if(httpServletRequest == null) {
      return null;
    }
    return httpServletRequest.getHeader(headerName);
  }
  
  public static
  String getParameter(PortletRequest request, String parameterName)
  {
    UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
    if(uploadPortletRequest == null) {
      return null;
    }
    return uploadPortletRequest.getParameter(parameterName);
  }
  
  public static
  String getParameter(PortletRequest request, String parameterName, String parameterNameAlt)
  {
    UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
    if(uploadPortletRequest == null) {
      return null;
    }
    String sResult = uploadPortletRequest.getParameter(parameterName);
    if(sResult == null || sResult.length() == 0) {
      sResult = uploadPortletRequest.getParameter(parameterNameAlt);
    }
    return sResult;
  }
  
  public static
  Object getSessionAttribute(PortletRequest request, String attributeName)
  {
    HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
    if(httpServletRequest == null) {
      return null;
    }
    HttpSession httpSession = httpServletRequest.getSession(false);
    if(httpSession == null) {
      return null;
    }
    return httpSession.getAttribute(attributeName);
  }
  
  public static
  String getCurrentURL(PortletRequest request)
  {
    return PortalUtil.getCurrentURL(request);
  }
  
  public static
  Map<String, Object> getUploadData(PortletRequest request, String fileParName, String... asParameters)
    throws IOException
  {
    UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
    if(uploadPortletRequest == null) {
      return null;
    }
    
    String sFileName = uploadPortletRequest.getFileName(fileParName);
    InputStream is   = uploadPortletRequest.getFileAsStream(fileParName);
    
    Map<String, Object> mapResult = new HashMap<String, Object>();
    mapResult.put(fileParName, readInputStream(is));
    mapResult.put(fileParName + "_", sFileName);
    if(asParameters != null && asParameters.length > 0) {
      for(int i = 0; i < asParameters.length; i++) {
        mapResult.put(asParameters[i], uploadPortletRequest.getParameter(asParameters[i]));
      }
    }
    return mapResult;
  }
  
  public static
  String getRealPath(Parameters parameters)
  {
    if(parameters == null) return null;
    
    String realPath = null;
    
    PortletConfig portletConfig = parameters.getPortletConfig();
    if(portletConfig != null) {
      PortletContext portletContext = portletConfig.getPortletContext();
      if(portletContext != null) {
        realPath = portletContext.getRealPath("/");
        if(realPath != null && realPath.length() > 0) {
          return realPath;
        }
      }
    }
    
    PortletRequest request = parameters.getRenderRequest();
    if(request != null) {
      HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
      if(httpServletRequest != null) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null) {
          ServletContext servletContext = httpSession.getServletContext();
          if(servletContext != null) {
            realPath = servletContext.getRealPath("/");
            if(realPath != null && realPath.length() > 0) {
              return realPath;
            }
          }
        }
      }
    }
    
    return realPath;
  }
  
  public static
  String getContextPath(Parameters parameters)
  {
    if(parameters == null) {
      return null;
    }
    String result = null;
    PortletRequest request = parameters.getRenderRequest();
    if(request != null) {
      result = request.getContextPath();
      if(result == null || result.length() == 0) {
        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
        if(httpServletRequest != null) {
          result = httpServletRequest.getContextPath();
        }
      }
    }
    return result;
  }
  
  public static
  String getPortalPath(Parameters parameters)
  {
    if(parameters == null) return null;
    String realPath = null;
    PortletRequest request = parameters.getRenderRequest();
    if(request != null) {
      HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);
      if(httpServletRequest != null) {
        String pathTranslated = httpServletRequest.getPathTranslated();
        if(pathTranslated != null && pathTranslated.length() > 0) {
          int iROOT = pathTranslated.indexOf("ROOT");
          if(iROOT > 0) {
            String sRootPath = pathTranslated.substring(0, iROOT);
            realPath = sRootPath + "ROOT" + File.separator + "html";
          }
        }
      }
    }
    return realPath;
  }
  
  public static
  String getPortalContext(Parameters parameters)
  {
    return "/html";
  }
  
  public static
  byte[] readInputStream(InputStream is)
    throws IOException
  {
    if(is == null) return null;
    ByteArrayOutputStream baos = null;
    try {
      baos = new ByteArrayOutputStream();
      byte[] buff = new byte[BUFF_LENGTH];
      int n;
      while ((n = is.read(buff)) > 0) {
        baos.write(buff, 0, n);
      }
    }
    finally {
      if(is   != null) try { is.close();   } catch(Exception ex) {}
      if(baos != null) try { baos.close(); } catch(Exception ex) {}
    }
    if(baos == null) return null;
    return baos.toByteArray();
  }
}
