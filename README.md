# WPortlet

Simple framework to build portlets like a webapp.

## Example

```java
import javax.portlet.*;

import org.dew.portlet.*;

public 
class HelloAction implements IAction 
{
  public
  Object action(String sAction, Parameters parameters, ActionRequest request, ActionResponse response)
    throws Exception
  {
    if(parameters.isBlank("name")) {
      throw new Exception(ResourcesMgr.getMessage(request, "error.name"));
    }
    
    String sName = parameters.getString("name");
    
    return sName;
  }
  
  public
  String view(String sAction, Parameters parameters, Object actionResult, RenderRequest request, RenderResponse response)
    throws Exception
  {
    String sName = (String) actionResult;
    
    String sHello = ResourcesMgr.getMessage(request, "hello", sName);
    
    request.setAttribute("hello", sHello);
    
    return "hello.jsp";
  }
  
  public String exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response)
    throws Exception
  {
    return null;
  }
}
```

## Build

- `git clone https://github.com/giosil/wportlet.git`
- `mvn clean install`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
