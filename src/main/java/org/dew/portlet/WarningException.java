package org.dew.portlet;

public 
class WarningException extends Exception 
{
  private static final long serialVersionUID = -8090503256923384432L;
  
  public 
  WarningException(String message) 
  {
    super(message);
  }
  
  public 
  WarningException(String message, Throwable cause) 
  {
    super(message, cause);
  }
  
  public 
  WarningException(Throwable cause) 
  {
    super(cause);
  }
}
