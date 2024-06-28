package org.dew.portlet.ui;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.dew.portlet.ResourcesMgr;

public 
class WebDialog implements Serializable
{
  private static final long serialVersionUID = -111871847880334508L;
  
  public static final int JQUERY_UI = 0;
  public static final int BOOTSTRAP = 1;
  
  // Static configuration
  public static String CONTENT_ATTRIBUTES = "";
  public static String HEADER_ATTRIBUTES  = "";
  public static String TITLE_TAG          = "h5";
  public static String TITLE_ATTRIBUTES   = "";
  public static String BODY_ATTRIBUTES    = "";
  public static String FOOTER_ATTRIBUTES  = "";
  public static String FIELDS_ATTRIBUTES  = "";
  public static String BUTTON_TITLE_CLOSE = "Close";
  // Produce margin-left: -(width/MODAL_MARGIN_LEFT_FACTOR)
  public static int    MODAL_MARGIN_LEFT_FACTOR = 0;
  
  protected String id;
  protected String title;
  protected String before;
  protected Object body;
  protected String after;
  
  protected boolean autoOpen = false;
  protected int height;
  protected int width;
  protected boolean modal = true;
  protected int framework = JQUERY_UI;
  
  protected List<String> buttonsCaption;
  protected List<String> buttonsOnClick;
  protected String validation;
  protected String openHandler;
  protected String closeHandler;
  
  public WebDialog()
  {
    if("liferay".equals(ResourcesMgr.sPORTAL_PLATFORM) && ResourcesMgr.iPORTAL_VERSION == 7) {
      framework = BOOTSTRAP;
    }
  }
  
  public WebDialog(String id)
  {
    this.id     = id;
    
    if("liferay".equals(ResourcesMgr.sPORTAL_PLATFORM) && ResourcesMgr.iPORTAL_VERSION == 7) {
      framework = BOOTSTRAP;
    }
  }
  
  public WebDialog(String id, String title)
  {
    this.id     = id;
    this.title  = title;
    
    if("liferay".equals(ResourcesMgr.sPORTAL_PLATFORM) && ResourcesMgr.iPORTAL_VERSION == 7) {
      framework = BOOTSTRAP;
    }
  }
  
  public WebDialog(String id, String title, Object body)
  {
    this.id     = id;
    this.title  = title;
    this.body   = body;
    
    if("liferay".equals(ResourcesMgr.sPORTAL_PLATFORM) && ResourcesMgr.iPORTAL_VERSION == 7) {
      framework = BOOTSTRAP;
    }
  }
  
  public WebDialog(String id, String title, String before, Object body)
  {
    this.id     = id;
    this.title  = title;
    this.before = before;
    this.body   = body;
    
    if("liferay".equals(ResourcesMgr.sPORTAL_PLATFORM) && ResourcesMgr.iPORTAL_VERSION == 7) {
      framework = BOOTSTRAP;
    }
  }
  
  public WebDialog(String id, String title, String before, Object body, String after)
  {
    this.id     = id;
    this.title  = title;
    this.before = before;
    this.body   = body;
    this.after  = after;
    
    if("liferay".equals(ResourcesMgr.sPORTAL_PLATFORM) && ResourcesMgr.iPORTAL_VERSION == 7) {
      framework = BOOTSTRAP;
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBefore() {
    return before;
  }

  public void setBefore(String before) {
    this.before = before;
  }

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

  public String getAfter() {
    return after;
  }

  public void setAfter(String after) {
    this.after = after;
  }

  public int getFramework() {
    return framework;
  }

  public void setFramework(int framework) {
    this.framework = framework;
  }

  public List<String> getButtonsCaption() {
    return buttonsCaption;
  }

  public void setButtonsCaption(List<String> buttonsCaption) {
    this.buttonsCaption = buttonsCaption;
  }

  public List<String> getButtonsOnClick() {
    return buttonsOnClick;
  }

  public void setButtonsOnClick(List<String> buttonsOnClick) {
    this.buttonsOnClick = buttonsOnClick;
  }

  public String getValidation() {
    return validation;
  }

  public void setValidation(String validation) {
    this.validation = validation;
  }

  public String getOpenHandler() {
    return openHandler;
  }

  public void setOpenHandler(String openHandler) {
    this.openHandler = openHandler;
    if(this.openHandler != null && this.openHandler.length() > 0) {
      if(!this.openHandler.endsWith(";")) this.openHandler += ";";
    }
  }

  public String getCloseHandler() {
    return closeHandler;
  }

  public void setCloseHandler(String closeHandler) {
    this.closeHandler = closeHandler;
    if(this.closeHandler != null && this.closeHandler.length() > 0) {
      if(!this.closeHandler.endsWith(";")) this.closeHandler += ";";
    }
  }
  
  public WebDialog onOpen(String openHandler) {
    this.openHandler = openHandler;
    if(this.openHandler != null && this.openHandler.length() > 0) {
      if(!this.openHandler.endsWith(";")) this.openHandler += ";";
    }
    return this;
  }
  
  public WebDialog onClose(String closeHandler) {
    this.closeHandler = closeHandler;
    if(this.closeHandler != null && this.closeHandler.length() > 0) {
      if(!this.closeHandler.endsWith(";")) this.closeHandler += ";";
    }
    return this;
  }
  
  public WebDialog size(int width, int height) {
    this.width  = width;
    this.height = height;
    return this;
  }
  
  public WebDialog validation(String validation) {
    this.validation = validation;
    return this;
  }
  
  public WebDialog addButton(String name, String onclick) {
    if(name == null || name.length() == 0) {
      return this;
    }
    if(buttonsCaption == null) buttonsCaption = new ArrayList<String>();
    if(buttonsOnClick == null) buttonsOnClick = new ArrayList<String>();
    if(onclick == null) onclick = "";
    buttonsCaption.add(name);
    buttonsOnClick.add(onclick);
    return this;
  }
  
  public WebDialog addSubmit(String name) {
    if(name == null || name.length() == 0) {
      name = "OK";
    }
    if(buttonsCaption == null) buttonsCaption = new ArrayList<String>();
    if(buttonsOnClick == null) buttonsOnClick = new ArrayList<String>();
    buttonsCaption.add(name);
    buttonsOnClick.add("$('#" + getNotNullId() + "').find(\'form\').submit();");
    return this;
  }
  
  public WebDialog addCancel(String name) {
    if(name == null || name.length() == 0) {
      name = "Annulla";
    }
    if(buttonsCaption == null) buttonsCaption = new ArrayList<String>();
    if(buttonsOnClick == null) buttonsOnClick = new ArrayList<String>();
    buttonsCaption.add(name);
    buttonsOnClick.add("");
    return this;
  }
  
  public 
  String getShowCode(String varName)
  {
    if(framework == BOOTSTRAP) {
      return bootstrap_show(varName);
    }
    return jqueryui_show(varName);
  }
  
  public 
  String getHideCode(String varName)
  {
    if(framework == BOOTSTRAP) {
      return bootstrap_hide(varName);
    }
    return jqueryui_hide(varName);
  }
  
  public 
  String getInitCode(String varName)
  {
    if(framework == BOOTSTRAP) {
      return bootstrap_init(varName);
    }
    return jqueryui_init(varName);
  }
  
  protected
  String getNotNullId()
  {
    if(this.id == null || this.id.length() == 0) {
      StringBuilder sbId = new StringBuilder();
      sbId.append("d");
      for(int i = 0; i < 9; i++) {
        int n = (int) (10 * Math.random());
        sbId.append(String.valueOf(n));
      }
      this.id = sbId.toString();
    }
    return this.id;
  }
  
  @Override
  public
  boolean equals(Object obj) 
  {
    if(obj instanceof WebDialog) {
      String sObjId = ((WebDialog) obj).getId();
      if(this.id == null && sObjId == null) return true;
      return this.id != null && this.id.equals(sObjId); 
    }
    return false;
  }
  
  @Override
  public
  int hashCode()
  {
    if(id != null) return id.hashCode();
    return 0;
  }
  
  @Override
  public
  String toString()
  {
    if(framework == BOOTSTRAP) {
      return bootstrap_dialog();
    }
    return jqueryui_dialog();
  }
  
  // Bootstrap Dialog Implementation -------------------------------------------
  protected
  String bootstrap_dialog()
  {
    StringBuilder sb = new StringBuilder();
    
    // sb.append("<div id=\"" + getNotNullId() + "\" class=\"modal\" tabindex=\"-1\" role=\"dialog\">");
    String styleDim = "";
    if(width > 0) {
      styleDim += "width:" + width + "px;";
    }
    if(height > 0) {
      styleDim += "height:" + height + "px;";
    }
    String marginLeftRule = "";
    if(width > 0 && MODAL_MARGIN_LEFT_FACTOR != 0) {
      int marginLeft = width / MODAL_MARGIN_LEFT_FACTOR;
      marginLeftRule = "margin-left:-" + marginLeft + "px;";
    }
    
    sb.append("<div id=\"" + getNotNullId() + "\" class=\"modal\" tabindex=\"-1\" role=\"dialog\" style=\"display:none;" + styleDim + marginLeftRule + "\">");
    if(styleDim != null && styleDim.length() > 0) {
      sb.append("<div class=\"modal-dialog\" role=\"document\" style=\"" + styleDim + "\">");
    }
    else {
      sb.append("<div class=\"modal-dialog\" role=\"document\">");
    }
    
    if(CONTENT_ATTRIBUTES != null && CONTENT_ATTRIBUTES.length() > 0) {
      sb.append("<div class=\"modal-content\" " + CONTENT_ATTRIBUTES + ">");
    }
    else {
      sb.append("<div class=\"modal-content\">");
    }
    
    if(HEADER_ATTRIBUTES != null && HEADER_ATTRIBUTES.length() > 0) {
      sb.append("<div class=\"modal-header\" " + HEADER_ATTRIBUTES + ">");
    }
    else {
      sb.append("<div class=\"modal-header\">");
    }
    if(title != null && title.length() > 0) {
      if(TITLE_TAG == null || TITLE_TAG.length() == 0) {
        TITLE_TAG = "h5";
      }
      if(TITLE_ATTRIBUTES != null && TITLE_ATTRIBUTES.length() > 0) {
        sb.append("<" + TITLE_TAG + " class=\"modal-title\" " + TITLE_ATTRIBUTES + ">" + title + "</" + TITLE_TAG + ">");
      }
      else {
        sb.append("<" + TITLE_TAG + " class=\"modal-title\">" + title + "</" + TITLE_TAG + ">");
      }
    }
    
    String buttonTitle = "";
    if(BUTTON_TITLE_CLOSE != null && BUTTON_TITLE_CLOSE.length() > 0) {
      buttonTitle += " title=\"" + BUTTON_TITLE_CLOSE + "\"";
    }
    sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\" onclick=\"" + attrib(getHideCode(null)) + "\"" + buttonTitle + ">");
    sb.append("<span aria-hidden=\"true\">&times;</span>");
    sb.append("</button>");
    sb.append("</div>"); // modal-header
    
    if(BODY_ATTRIBUTES != null && BODY_ATTRIBUTES.length() > 0) {
      sb.append("<div class=\"modal-body\" " + BODY_ATTRIBUTES + ">");
    }
    else {
      sb.append("<div class=\"modal-body\">");
    }
    if(before != null && before.length() > 0) {
      sb.append(before);
    }
    if(body != null) {
      String sBody = body.toString();
      if(sBody != null && sBody.length() > 0) {
        sb.append(sBody);
      }
    }
    if(after != null && after.length() > 0) {
      sb.append(after);
    }
    sb.append("</div>"); // modal-body
    
    if(FOOTER_ATTRIBUTES != null && FOOTER_ATTRIBUTES.length() > 0) {
      sb.append("<div class=\"modal-footer\" " + FOOTER_ATTRIBUTES + ">");
    }
    else {
      sb.append("<div class=\"modal-footer\">");
    }
    if(buttonsCaption != null && buttonsCaption.size() > 0) {
      for(int i = 0; i < buttonsCaption.size(); i++) {
        String sCaption = buttonsCaption.get(i);
        String sOnClick = buttonsOnClick != null && buttonsOnClick.size() > i ? buttonsOnClick.get(i) : "";
        if(sOnClick != null && sOnClick.length() > 0 && !sOnClick.equalsIgnoreCase("x")) {
          if(!sOnClick.endsWith(";")) sOnClick += ";";
          if(validation != null && validation.length() > 0) {
            sb.append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"if(" + validation + "){" + attrib(sOnClick, getHideCode(null)) + "}\">" + sCaption + "</button>");
          }
          else {
            sb.append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"" + attrib(sOnClick, getHideCode(null)) + "\">" + sCaption + "</button>");
          }
        }
        else {
          sb.append("<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\" onclick=\"" + attrib(getHideCode(null)) + "\">" + sCaption + "</button>");
        }
      }
    }
    sb.append("</div>"); // modal-footer
    
    sb.append("</div>"); // modal-content
    sb.append("</div>"); // modal-dialog
    sb.append("</div>"); // modal
    return sb.toString();
  }
  
  protected
  String bootstrap_init(String varName)
  {
    StringBuilder sb = new StringBuilder();
    if(varName != null && varName.length() > 0) {
      sb.append(varName + "=");
    }
    sb.append("$(\"#" + getNotNullId() + "\");");
    return sb.toString();
  }
  
  protected 
  String bootstrap_show(String varName)
  {
    StringBuilder sb = new StringBuilder();
    
    // Si veda nota in bootstrap_dialog
//    if(varName != null && varName.length() > 0) {
//      sb.append(varName + ".modal(\"show\");");
//    }
//    else {
//      sb.append("$(\"#" + getNotNullId() + "\").modal(\"show\");");
//    }
    
    String sId = getNotNullId();
    
    if(varName != null && varName.length() > 0) {
      sb.append(varName + ".show();");
    }
    else {
      sb.append("$('#" + sId + "').show();");
    }
    
    // Center
    sb.append("$('#" + sId + "').css(\"top\", \"60px\");");
    if(width > 10) {
      sb.append("$('#" + sId + "').css(\"left\", ($(window).width()/2-" + ((width * 8 / 10) / 2) + ")+\"px\");");
    }
    else {
      sb.append("$('#" + sId + "').css(\"left\", ($(window).width()/2-200)+\"px\");");
    }
    
    if(openHandler != null && openHandler.length() > 0) {
      if(!this.openHandler.endsWith(";")) this.openHandler += ";";
      sb.append(openHandler);
    }
    
    return sb.toString();
  }
  
  protected 
  String bootstrap_hide(String varName)
  {
    StringBuilder sb = new StringBuilder();
    
    // Si veda nota in bootstrap_dialog
//    if(varName != null && varName.length() > 0) {
//      sb.append(varName + ".modal(\"hide\");");
//    }
//    else {
//      sb.append("$(\"#" + getNotNullId() + "\").dialog(\"hide\");");
//    }
    
    if(closeHandler != null && closeHandler.length() > 0) {
      if(!this.closeHandler.endsWith(";")) this.closeHandler += ";";
      sb.append(closeHandler);
    }
    
    if(varName != null && varName.length() > 0) {
      sb.append(varName + ".hide();");
    }
    else {
      sb.append("$('#" + getNotNullId() + "').hide();");
    }
    
    return sb.toString();
  }
  
  // JQuery UI Dialog Implementation -------------------------------------------
  protected
  String jqueryui_dialog()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<div id=\"" + getNotNullId() + "\"");
    if(title != null && title.length() > 0) {
      sb.append(" title=\"" + title + "\"");
    }
    sb.append(">");
    if(before != null && before.length() > 0) {
      sb.append(before);
    }
    if(body != null) {
      String sBody = body.toString();
      if(sBody != null && sBody.length() > 0) {
        sb.append(sBody);
      }
    }
    if(after != null && after.length() > 0) {
      sb.append(after);
    }
    sb.append("</div>");
    return sb.toString();
  }
  
  protected
  String jqueryui_init(String varName)
  {
    StringBuilder sb = new StringBuilder();
    if(varName != null && varName.length() > 0) {
      sb.append(varName + "=");
    }
    sb.append("$(\"#" + getNotNullId() + "\").dialog({");
    sb.append("autoOpen:false,");
    if(height != 0) {
      sb.append("height:" + height + ",");
    }
    if(width != 0) {
      sb.append("width:" + width + ",");
    }
    sb.append("modal:" + modal + ",");
    if(buttonsCaption == null || buttonsCaption.size() == 0) {
      sb.append("buttons:{},");
    }
    else {
      sb.append("buttons:{");
      for(int i = 0; i < buttonsCaption.size(); i++) {
        String sOnClick = buttonsOnClick != null && buttonsOnClick.size() > i ? buttonsOnClick.get(i) : "";
        if(sOnClick == null || sOnClick.length() == 0 || sOnClick.equalsIgnoreCase("x")) {
          sOnClick = getHideCode(varName);
        }
        if(i > 0) sb.append(",");
        sb.append("\"" + buttonsCaption.get(i) + "\":function(){" + sOnClick + "}");
      }
      sb.append("},");
    }
    if(openHandler != null && openHandler.length() > 0) {
      sb.append("open:function(){" + openHandler + "},");
    }
    else {
      sb.append("open:function(){},");
    }
    if(closeHandler != null && closeHandler.length() > 0) {
      sb.append("close:function(){" + closeHandler + "},");
    }
    else {
      sb.append("close:function(){}");
    }
    sb.append("});");
    return sb.toString();
  }
  
  protected 
  String jqueryui_show(String varName)
  {
    StringBuilder sb = new StringBuilder();
    if(varName != null && varName.length() > 0) {
      sb.append(varName + ".dialog(\"open\");");
    }
    else {
      sb.append("$(\"#" + getNotNullId() + "\").dialog(\"open\");");
    }
    return sb.toString();
  }
  
  protected 
  String jqueryui_hide(String varName)
  {
    StringBuilder sb = new StringBuilder();
    if(varName != null && varName.length() > 0) {
      sb.append(varName + ".dialog(\"close\");");
    }
    else {
      sb.append("$(\"#" + getNotNullId() + "\").dialog(\"close\");");
    }
    return sb.toString();
  }
  
  protected static 
  String attrib(String s) 
  {
    if(s == null || s.length() == 0) {
      return "";
    }
    return s.replace("\n", "").replace("\"", "&quot;");
  }
  
  protected static 
  String attrib(String s0, String s1) 
  {
    String s = "";
    if(s0 != null && s0.length() > 0) {
      if(!s0.endsWith(";")) s0 += ";";
      s += s0;
    }
    if(s1 != null && s1.length() > 0) {
      if(!s1.endsWith(";")) s1 += ";";
      s += s1;
    }
    return s.replace("\n", "").replace("\"", "&quot;");
  }
}
