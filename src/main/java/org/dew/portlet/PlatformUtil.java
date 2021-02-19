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
        EmailAddress emailAddress = listOfEmailAddress.get(0);
        if(emailAddress != null) {
          sEmailAddress = emailAddress.getAddress();
          sLiferayUser += ",emailAddresses[0]=" + sEmailAddress;
        }
        if(sEmailAddress == null || sEmailAddress.length() < 3) {
          sEmailAddress = liferayUser.getEmailAddress();
          sLiferayUser += ",emailAddress=" + sEmailAddress;
        }
        
        if(listOfEmailAddress.size() > 1) {
          EmailAddress emailAddress1 = listOfEmailAddress.get(1);
          if(emailAddress1 != null) {
            String sEmailAddress1 = emailAddress1.getAddress();
            sLiferayUser += ",emailAddresses[1]=" + sEmailAddress1;
          }
        }
      }
      
      List<Phone> phones = liferayUser.getPhones();
      if(phones != null && phones.size() > 0) {
        Phone phone0 = phones.get(0);
        if(phone0 != null) {
          sPhoneNumber = phone0.getNumber();
          sLiferayUser += ",phones[0]=" + sPhoneNumber;
        }
        
        if(phones.size() > 1) {
          Phone phone1 = phones.get(1);
          if(phone1 != null) {
            String sPhoneNumber1 = phone1.getNumber();
            sLiferayUser += ",phones[1]=" + sPhoneNumber1;
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
      PlatformUtil.log("ISEDPortlet liferayUser: " + sLiferayUser);
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