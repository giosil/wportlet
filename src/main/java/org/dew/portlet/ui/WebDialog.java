package org.dew.portlet.ui;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.dew.portlet.ResourcesMgr;

public 
class WebDialog implements Serializable
{
  private static final long serialVersionUID = -111871847880334508L;
  
  protected String id;
  protected String title;
  protected String before;
  protected Object body;
  protected String after;
  
  protected boolean autoOpen = false;
  protected int height;
  protected int width;
  protected boolean modal = true;
  
  protected List<String> buttonsCaption;
  protected List<String> buttonsOnClick;
  protected String openHandler;
  protected String closeHandler;
  
  public WebDialog()
  {
  }
  
  public WebDialog(String id)
  {
    this.id     = id;
  }
  
  public WebDialog(String id, String title)
  {
    this.id     = id;
    this.title  = title;
  }
  
  public WebDialog(String id, String title, Object body)
  {
    this.id     = id;
    this.title  = title;
    this.body   = body;
  }
  
  public WebDialog(String id, String title, String before, Object body)
  {
    this.id     = id;
    this.title  = title;
    this.before = before;
    this.body   = body;
  }
  
  public WebDialog(String id, String title, String before, Object body, String after)
  {
    this.id     = id;
    this.title  = title;
    this.before = before;
    this.body   = body;
    this.after  = after;
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
    if(ResourcesMgr.iPORTAL_VERSION > 6) {
      return bootstrap_show(varName);
    }
    return jqueryui_show(varName);
  }
  
  public 
  String getHideCode(String varName)
  {
    if(ResourcesMgr.iPORTAL_VERSION > 6) {
      return bootstrap_hide(varName);
    }
    return jqueryui_hide(varName);
  }
  
  public 
  String getInitCode(String varName)
  {
    if(ResourcesMgr.iPORTAL_VERSION > 6) {
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
    if(ResourcesMgr.iPORTAL_VERSION > 6) {
      return bootstrap_dialog();
    }
    return jqueryui_dialog();
  }
  
  // Liferay Dialog Implementation
  
  // Bootstrap Dialog Implementation
  
  protected
  String bootstrap_dialog()
  {
    // In Liferay 7 il framework JS e' YUI, mentre bootstrap e' utilizzato come framework CSS.
    // Per questo motivo la gestione dei modal bootstrap e' da gestire programmaticamente.
    // 
    // In alternativa occorre utilizzare le API di liferay. Ad esempio:
    //
    // Liferay.Util.openWindow({
    // dialog:{width:500,height:300,draggable:false,resizable:false,visible:true,after:{ render:function(e){ console.log('render',e); }}},
    // id:'dlgtest',
    // title:'Test'
    // });
    
    StringBuilder sb = new StringBuilder();
    
    // sb.append("<div id=\"" + getNotNullId() + "\" class=\"modal\" tabindex=\"-1\" role=\"dialog\">");
    String styleDim = "";
    if(width > 0) {
      styleDim += "width:" + width + "px;";
    }
    if(height > 0) {
      styleDim += "height:" + width + "px;";
    }
    sb.append("<div id=\"" + getNotNullId() + "\" class=\"modal\" tabindex=\"-1\" role=\"dialog\" style=\"display:none;" + styleDim + "\">");
    sb.append("<div class=\"modal-dialog\" role=\"document\">");
    sb.append("<div class=\"modal-content\">");
    
    sb.append("<div class=\"modal-header\">");
    if(title != null && title.length() > 0) {
      sb.append("<h5 class=\"modal-title\">" + title + "</h5>");
    }
    // sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">");
    sb.append("<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\" onclick=\"" + attrib(getHideCode(null)) + "\">");
    sb.append("<span aria-hidden=\"true\">&times;</span>");
    sb.append("</button>");
    sb.append("</div>");
    
    sb.append("<div class=\"modal-body\">");
    if(body != null) {
      String sBody = body.toString();
      if(sBody != null && sBody.length() > 0) {
        sb.append(sBody);
      }
    }
    sb.append("</div>");
    
    sb.append("<div class=\"modal-footer\">");
    if(buttonsCaption != null && buttonsCaption.size() > 0) {
      for(int i = 0; i < buttonsCaption.size(); i++) {
        String sCaption = buttonsCaption.get(i);
        String sOnClick = buttonsOnClick != null && buttonsOnClick.size() > i ? buttonsOnClick.get(i) : "";
        if(sOnClick != null && sOnClick.length() > 0 && !sOnClick.equalsIgnoreCase("x")) {
          if(!sOnClick.endsWith(";")) sOnClick += ";";
          sb.append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"" + attrib(sOnClick, getHideCode(null)) + "\">" + sCaption + "</button>");
        }
        else {
          sb.append("<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\" onclick=\"" + attrib(getHideCode(null)) + "\">" + sCaption + "</button>");
        }
      }
    }
    sb.append("</div>");
    
    sb.append("</div>");
    sb.append("</div>");
    sb.append("</div>");
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
  
  // JQuery UI Dialog Implementation
  
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
