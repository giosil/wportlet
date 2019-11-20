package org.dew.test;

import java.util.Locale;

import org.dew.portlet.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestWPortlet extends TestCase {
  
  public TestWPortlet(String testName) {
    super(testName);
  }
  
  public static Test suite() {
    return new TestSuite(TestWPortlet.class);
  }
  
  public void testApp() {
    Locale locale = Locale.getDefault();
    String message = ResourcesMgr.getMessage(locale, "error.name");
    System.out.println("ResourcesMgr.getMessage(locale, \"error.name\") -> " + message);
  }
  
}
