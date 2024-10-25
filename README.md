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

## Setting Up JNDI in Liferay 7.4

In liferay/tomcat/conf/Catalina/localhost/ROOT.xml

```xml
    <Resource name="jdbc/demodb" auth="Container"
        factory="com.zaxxer.hikari.HikariJNDIFactory"
        type="javax.sql.DataSource"
        minimumIdle="10"
        maximumPoolSize="100"
        maxLifetime="1800000"
        connectionTimeout="300000"
        dataSourceClassName="org.postgresql.ds.PGSimpleDataSource"
        dataSource.serverName="demo-db"
        dataSource.portNumber="5432"
        dataSource.databaseName="demo"
        dataSource.user="demo"
        dataSource.password="demo"
        validationQuery="SELECT 1"/>
```

In 7.4 Liferay introduced the "Shielded Class Loader" in an effort to shield the webapp class loader from the OSGi container.
Before invoking the lookup:

```java
Thread thread = Thread.currentThread();

// Get the thread's class loader. You'll reinstate it after using
// the data source you look up using JNDI
ClassLoader origLoader = thread.getContextClassLoader();

// get the shielded class loader
ClassLoader shieldedClassLoader = PortalClassLoaderUtil.getClassLoader();

// get the webapp class loader from it
ClassLoader webappClassLoader = shieldedClassLoader.getClass().getClassLoader();

// Set webapp class loader on the thread
thread.setContextClassLoader(webappClassLoader);
```

## Build

- `git clone https://github.com/giosil/wportlet.git`
- `mvn clean install`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
