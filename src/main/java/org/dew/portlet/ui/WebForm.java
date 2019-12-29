package org.dew.portlet.ui;

import org.dew.portlet.WNames;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe per la costruzione di Form Web.
 */
public 
class WebForm implements Serializable
{
  private static final long serialVersionUID = 953736094564294951L;
  
  protected String  title;
  protected boolean columnLayout;
  protected String  labelStyle;
  protected String  fieldsetStyle;
  protected String  focusOn;

  protected List<List<WField>> rows = new ArrayList<List<WField>>();
  protected List<WField> btns;
  protected List<WField> hidd;
  protected List<String> date;

  protected String name;
  protected String id;
  protected String initFunction;
  protected String namespace;
  protected String method;
  protected String actionUrl;
  protected String style;
  protected String onSubmit;
  protected String validation;
  protected boolean multipart = false;
  protected boolean btnsUp    = false;
  protected boolean btnsDown  = true;

  protected String endRow;
  protected String before;
  protected String after;
  protected String footer;
  protected int incLab = 0;

  protected List<WebForm> forms = null;
  protected List<String> titles = null;

  public static String ROW_CLASS        = "section group";
  public static String COL_CLASS_BEG    = "col span_";
  public static String COL_CLASS_END    = "_of_12";

  public static String STYLE_STATIC_TXT = "line-height:2;vertical-align:middle;";
  public static String STYLE_DIV_LABEL  = "line-height:2;vertical-align:middle;text-align:right;";
  public static String STYLE_DIV_BLANK  = "padding:2px 0 2px 0;";
  public static String STYLE_DIV_INPUT  = "padding:2px 0 2px 0;";
  public static String STYLE_DIV_FIELD  = "padding:2px 0 2px 0;";
  public static String STYLE_DIV_STATIC = "padding:0px 0 0px 0;";
  
  public WebForm()
  {
  }

  public WebForm(String id, String title, String onSubmit)
  {
    this.id       = id;
    this.title    = title;
    this.onSubmit = onSubmit;
  }

  public WebForm(HttpServletRequest request)
  {
    this.namespace = WebUtil.getNamespace(request);
    this.actionUrl = WebUtil.getActionURL(request);
  }

  public WebForm(HttpServletRequest request, String title)
  {
    this.namespace = WebUtil.getNamespace(request);
    this.actionUrl = WebUtil.getActionURL(request);
    this.title     = title;
  }

  public WebForm(HttpServletRequest request, String title, String action)
  {
    this.namespace = WebUtil.getNamespace(request);
    this.actionUrl = WebUtil.buildActionURL(request, action, null);
    this.title     = title;
  }

  public WebForm(RenderResponse renderResponse)
  {
    this.namespace = renderResponse.getNamespace();
    this.actionUrl = renderResponse.createActionURL().toString();
  }

  public WebForm(RenderResponse renderResponse, String title)
  {
    this.namespace = renderResponse.getNamespace();
    this.actionUrl = renderResponse.createActionURL().toString();
    this.title     = title;
  }

  public WebForm(RenderResponse renderResponse, String title, String action)
  {
    this.namespace = renderResponse.getNamespace();
    PortletURL portletURL = renderResponse.createActionURL();
    if(action != null && action.length() > 0) {
      portletURL.setParameter(WNames.sPAR_ACTION, action);
    }
    this.actionUrl = portletURL.toString();
    this.title     = title;
  }

  public WebForm(RenderResponse renderResponse, String title, String action, String param, String value)
  {
    this.namespace = renderResponse.getNamespace();
    PortletURL portletURL = renderResponse.createActionURL();
    if(action != null && action.length() > 0) {
      portletURL.setParameter(WNames.sPAR_ACTION, action);
    }
    if(param != null && param.length() != 0 && value != null) {
      portletURL.setParameter(param, value);
    }
    this.actionUrl = portletURL.toString();
    this.title     = title;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getActionUrl() {
    return actionUrl;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public void setOnSubmit(String onSubmit) {
    this.onSubmit = onSubmit;
  }

  public void setOnSubmit(String onSubmit, boolean validation) {
    if(validation) {
      this.validation = onSubmit;
      this.onSubmit = null;
    }
    else {
      this.onSubmit = onSubmit;
      this.validation = null;
    }
  }

  public void setEndRow(String endRow) {
    this.endRow = endRow;
  }

  public void setBefore(String before) {
    this.before = before;
  }

  public void setAfter(String after) {
    this.after = after;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }

  public boolean isColumnLayout() {
    return columnLayout;
  }

  public void setColumnLayout(boolean columnLayout) {
    this.columnLayout = columnLayout;
  }

  public void setIncrementLabel(int incLab) {
    this.incLab = incLab;
  }

  public void setLabelStyle(String labelStyle) {
    this.labelStyle = labelStyle;
  }

  public void setFieldsetStyle(String fieldsetStyle) {
    this.fieldsetStyle = fieldsetStyle;
  }

  public String getFocusOn() {
    return focusOn;
  }

  public void setFocusOn(String focusOn) {
    this.focusOn = focusOn;
  }

  public boolean isButtonsDown() {
    return btnsDown;
  }

  public void setButtonsDown(boolean buttonsDown) {
    this.btnsDown = buttonsDown;
  }

  public boolean isButtonsUp() {
    return btnsUp;
  }

  public void setButtonsUp(boolean buttonsUp) {
    this.btnsUp = buttonsUp;
  }

  public String getValidation() {
    return validation;
  }

  public void setValidation(String validation) {
    this.validation = validation;
  }

  public 
  void addForm(WebForm form, String title)
  {
    if(form   == null) return;
    if(forms  == null) forms  = new ArrayList<WebForm>();
    if(titles == null) titles = new ArrayList<String>();
    forms.add(form);
    if (title != null) {
      titles.add(title);
    } 
    else {
      titles.add("");
    }
  }

  public
  void addRow()
  {
    rows.add(new ArrayList<WField>());
  }

  public 
  void addComponent(String sHtml)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.COMPONENT, sHtml));
  }

  public 
  void addComponent(String sLabel, String sHtml)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.COMPONENT, "", sLabel).append(sHtml));
  }

  public 
  void addStaticText(String sId, String sLabel, String sText)
  {
    List<WField> currRow = getCurrentRow();
    if(STYLE_STATIC_TXT != null && STYLE_STATIC_TXT.length() > 0) {
      currRow.add(new WField(Type.STATICTEXT, sId, sLabel).append("<span id=\"" + sId + "\" style=\"" + STYLE_STATIC_TXT + "\">" + esc(sText) + "</span>"));
    }
    else {
      currRow.add(new WField(Type.STATICTEXT, sId, sLabel, sText));
    }
  }

  public 
  void addStaticText(String sId, String sLabel, String sText, String sStyle)
  {
    List<WField> currRow = getCurrentRow();
    if(sStyle != null && sStyle.length() > 0) {
      if(STYLE_STATIC_TXT != null && STYLE_STATIC_TXT.length() > 0) {
        if(!sStyle.endsWith(";")) sStyle += ";";
        sStyle += STYLE_STATIC_TXT;
      }
      currRow.add(new WField(Type.STATICTEXT, sId, sLabel).append("<span id=\"" + sId + "\" style=\"" + sStyle + "\">" + esc(sText) + "</span>"));
    }
    else if(STYLE_STATIC_TXT != null && STYLE_STATIC_TXT.length() > 0) {
      currRow.add(new WField(Type.STATICTEXT, sId, sLabel).append("<span id=\"" + sId + "\" style=\"" + STYLE_STATIC_TXT + "\">" + esc(sText) + "</span>"));
    }
    else {
      currRow.add(new WField(Type.STATICTEXT, sId, sLabel, sText));
    }
  }

  public 
  void addTextField(String sId, String sLabel)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.TEXTFIELD, sId, sLabel));
  }

  public 
  void addTextField(String sId, String sLabel, String sAttributes)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.TEXTFIELD, sId, sLabel, "", sAttributes));
  }

  public 
  void addTextField(String sId, String sLabel, String sAttributes, String sText)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.TEXTFIELD, sId, sLabel, sText, sAttributes));
  }

  public 
  void addTextField(String sId, String sLabel, String sAttributes, boolean boEnabled)
  {
    if(boEnabled) {
      addTextField(sId, sLabel, sAttributes);
    }
    else {
      addStaticText(sId, sLabel, sAttributes);
    }
  }

  public 
  void addTextArea(String sId, String sLabel, int iRows, int iCols)
  {
    List<WField> currRow = getCurrentRow();
    if(iRows > 0 || iCols > 0) {
      if(iRows < 1) iRows = 1;
      if(iCols > 0) {
        currRow.add(new WField(Type.TEXTAREA, sId, sLabel, "", "rows=\"" + iRows + "\" cols=\"" + iCols + "\""));
      }
      else {
        currRow.add(new WField(Type.TEXTAREA, sId, sLabel, "", "rows=\"" + iRows + "\""));
      }
    }
    else {
      currRow.add(new WField(Type.TEXTAREA, sId, sLabel));
    }
  }

  public 
  void addTextArea(String sId, String sLabel, int iRows, int iCols, String sAttributes)
  {
    List<WField> currRow = getCurrentRow();
    if(sAttributes == null) sAttributes = "";
    if(iRows > 0 || iCols > 0) {
      if(sAttributes != null && sAttributes.length() > 0) {
        sAttributes = " " + sAttributes;
      }
      if(iRows < 1) iRows = 1;
      if(iCols > 0) {
        currRow.add(new WField(Type.TEXTAREA, sId, sLabel, "", "rows=\"" + iRows + "\" cols=\"" + iCols + "\"" + sAttributes));
      }
      else {
        currRow.add(new WField(Type.TEXTAREA, sId, sLabel, "", "rows=\"" + iRows + "\"" + sAttributes));
      }
    }
    else {
      currRow.add(new WField(Type.TEXTAREA, sId, sLabel, "", sAttributes));
    }
  }

  public 
  void addTextArea(String sId, String sLabel, int iRows, int iCols, String sAttributes, String sText)
  {
    List<WField> currRow = getCurrentRow();
    if(sAttributes == null) sAttributes = "";
    if(iRows > 0 || iCols > 0) {
      if(sAttributes != null && sAttributes.length() > 0) {
        sAttributes = " " + sAttributes;
      }
      if(iRows < 1) iRows = 1;
      if(iCols > 0) {
        currRow.add(new WField(Type.TEXTAREA, sId, sLabel, sText, "rows=\"" + iRows + "\" cols=\"" + iCols + "\"" + sAttributes));
      }
      else {
        currRow.add(new WField(Type.TEXTAREA, sId, sLabel, sText, "rows=\"" + iRows + "\"" + sAttributes));
      }
    }
    else {
      currRow.add(new WField(Type.TEXTAREA, sId, sLabel, sText, sAttributes));
    }
  }

  public 
  void addCheckBox(String sId, String sLabel)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.CHECKBOX, sId, sLabel));
  }

  public 
  void addCheckBox(String sId, String sLabel, boolean checked)
  {
    List<WField> currRow = getCurrentRow();
    if(checked) {
      currRow.add(new WField(Type.CHECKBOX, sId, sLabel, "", "checked=\"checked\""));
    }
    else {
      currRow.add(new WField(Type.CHECKBOX, sId, sLabel));
    }
  }

  public 
  void addRadioButton(String sId, String sName, boolean checked)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.RADIOBUTTON, sId, sName, checked));
  }

  public 
  void addRadioButton(String sId, String sName, String sValue, boolean checked)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.RADIOBUTTON, sId, sName, sValue, checked));
  }

  public 
  void addDateField(String sId, String sLabel)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.DATEFIELD, sId, sLabel));
    if(date == null) date = new ArrayList<String>();
    date.add(sId);
  }

  public 
  void addDateField(String sId, String sLabel, String sAttributes)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.DATEFIELD, sId, sLabel, "", sAttributes));
    if(date == null) date = new ArrayList<String>();
    date.add(sId);
  }

  public 
  void addTimeField(String sId, String sLabel)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.TIMEFIELD, sId, sLabel));
  }

  public 
  void addSelect(String sId, String sLabel, HttpServletRequest request, String sAttribute)
  {
    List<WField> currRow = getCurrentRow();
    Object oOption = request.getAttribute(sAttribute);
    if(oOption instanceof List) {
      currRow.add(new WField(Type.SELECT, sId, sLabel, (List<?>) oOption));
    }
    else {
      currRow.add(new WField(Type.SELECT, sId, sLabel, Collections.EMPTY_LIST));
    }
  }

  public 
  void addSelect(String sId, String sLabel, List<?> listOptions)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.SELECT, sId, sLabel, listOptions));
  }

  public 
  void addSelect(String sId, String sLabel, List<?> listOptions, String sSelectedItem_Attribute)
  {
    List<WField> currRow = getCurrentRow();
    if(sSelectedItem_Attribute != null && sSelectedItem_Attribute.indexOf("=") > 0) {
      currRow.add(new WField(Type.SELECT, sId, sLabel, listOptions, "", sSelectedItem_Attribute));
    }
    else {
      currRow.add(new WField(Type.SELECT, sId, sLabel, listOptions, sSelectedItem_Attribute));
    }
  }

  public 
  void addSelect(String sId, String sLabel, List<?> listOptions, String sSelectedItem, String sAttribute)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.SELECT, sId, sLabel, listOptions, sSelectedItem, sAttribute));
  }

  public 
  void addSelect(String sId, String sLabel, String... asOptions)
  {
    List<WField> currRow = getCurrentRow();
    int length = asOptions != null ? asOptions.length : 0;
    List<String> listOptions = new ArrayList<String>(length);
    if(length > 0) {
      for(int i = 0; i < length; i++) {
        listOptions.add(asOptions[i]);
      }
    }
    currRow.add(new WField(Type.SELECT, sId, sLabel, listOptions));
  }

  public 
  void addHiddenField(String sId, String sValue)
  {
    if(hidd == null) hidd = new ArrayList<WField>();
    hidd.add(new WField(Type.HIDDEN, sId, "", sValue));
  }

  public 
  void addFileField(String sId, String sLabel)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.FILEFIELD, sId, sLabel));
    multipart = true;
  }

  public 
  void addFileField(String sId, String sLabel, String sNotes)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.FILEFIELD, sId, sLabel, sNotes));
    multipart = true;
  }

  public 
  void addBlankField()
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.BLANK, "&nbsp;"));
  }

  public 
  void addBlankField(String sText_Html)
  {
    List<WField> currRow = getCurrentRow();
    currRow.add(new WField(Type.BLANK, sText_Html));
  }

  public 
  void addButton(String sLabel, String sOnClick)
  {
    if(btns == null) btns = new ArrayList<WField>();
    btns.add(new WField(Type.BUTTON, "", sLabel, sOnClick));
  }

  public 
  void addSubmit(String sLabel, String sOnClick)
  {
    if(btns == null) btns = new ArrayList<WField>();
    btns.add(new WField(Type.SUBMIT, "", sLabel, sOnClick));
  }

  public 
  void addSubmit(String sLabel)
  {
    if(btns == null) btns = new ArrayList<WField>();
    btns.add(new WField(Type.SUBMIT, "", sLabel));
  }

  public 
  void addCancel(String sLabel, String sOnClick)
  {
    if(btns == null) btns = new ArrayList<WField>();
    btns.add(new WField(Type.RESET, "", sLabel, sOnClick));
  }

  public 
  void addCancel(String sLabel)
  {
    if(btns == null) btns = new ArrayList<WField>();
    btns.add(new WField(Type.RESET, "", sLabel));
  }

  public
  void setInitFunction(String functionName)
  {
    this.initFunction = functionName;
  }

  public
  String getInitFunction()
  {
    return initFunction;
  }

  protected
  List<WField> getCurrentRow()
  {
    List<WField> listResult = null;
    if(rows.size() == 0) {
      listResult = new ArrayList<WField>();
      rows.add(listResult);
    }
    else {
      listResult = rows.get(rows.size()-1);
    }
    return listResult;
  }

  protected static
  String esc(String sText)
  {
    if(sText == null) return "";
    int iLength = sText.length();
    if(iLength == 0) return "";
    StringBuilder sb = new StringBuilder(iLength);
    for(int i = 0; i < iLength; i++) {
      char c = sText.charAt(i);
      if(c == '<')  sb.append("&lt;");
      else if(c == '>')  sb.append("&gt;");
      else if(c == '&')  sb.append("&amp;");
      else if(c == '"')  sb.append("&quot;");
      else if(c == '\'') sb.append("&apos;");
      else if(c > 127) {
        int code = (int) c;
        sb.append("&#" + code + ";");
      }
      else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  public
  String buildReadyFunction(String sBefore, String sAfter)
  {
    StringBuffer sb = new StringBuffer();
    if(forms != null && forms.size() > 0){
      if(sBefore != null && sBefore.length() > 0) sb.append(sBefore);
      for(int i = 0; i < forms.size(); i++){
        sb.append(forms.get(i).buildReadyFunction(sb.toString(), ""));
      }
      if(initFunction != null && initFunction.length() > 0) {
        if(initFunction.endsWith(";")) {
          sb.append(initFunction);
        }
        else {
          sb.append(initFunction + ";");
        }
      }
      if(sAfter != null && sAfter.length() > 0) sb.append(sAfter);
      return sb.toString();
    }
    if(date != null && date.size() > 0) {
      if(sBefore == null || sBefore.indexOf("$('.fdate').datepicker") < 0) {
        sb.append("$('.fdate').datepicker($.datepicker.regional[\"it\"]);");
      }
    }
    if(focusOn != null && focusOn.length() > 0) {
      sb.append("$('#" + focusOn + "').focus();");
    }
    if(initFunction != null && initFunction.length() > 0) {
      if(initFunction.endsWith(";")) {
        sb.append(initFunction);
      }
      else {
        sb.append(initFunction + ";");
      }
    }
    if(sb.length() > 1) {
      if(sAfter != null && sAfter.length() > 0) {
        sb.append(sAfter);
      }
      if(sBefore != null && sBefore.length() > 0) {
        return sBefore + sb.toString();
      }
      return sb.toString();
    }
    return "";
  }

  protected
  String build()
  {
    StringBuilder sb = new StringBuilder();
    if(namespace == null) namespace = "";
    if(actionUrl != null && actionUrl.length() > 0) {
      if(method == null || method.length() == 0) {
        method = "POST";
      }
    }
    if(before != null && before.length() > 0) {
      sb.append(before);
    }
    if(title != null && title.length() > 0) {
      sb.append("<div class=\"" + ROW_CLASS + "\"><strong>" + title + "</strong></div>");
    }
    sb.append("<form");
    if(id != null && id.length() > 0) {
      sb.append(" id=\"" + id + "\"");
    }
    if(name != null && name.length() > 0) {
      sb.append(" name=\"" + name + "\"");
    }
    if(onSubmit != null && onSubmit.length() > 0) {
      sb.append(" onsubmit=\"" + esc(onSubmit) + "\"");
    }
    else {
      if(validation != null && validation.length() > 0) {
        sb.append(" onsubmit=\"" + esc(validation) + "\"");
      }
      if(method != null && method.length() > 0) {
        sb.append(" method=\"" + method + "\"");
      }
      if(actionUrl != null && actionUrl.length() > 0) {
        sb.append(" action=\"" + actionUrl + "\"");
      }
    }
    sb.append(" role=\"form\"");
    if(multipart) {
      sb.append(" enctype=\"multipart/form-data\"");
    }
    if(style != null && style.length() > 0) {
      if(style.indexOf(':') > 0) {
        sb.append(" style=\"" + style + "\"");
      }
      else {
        sb.append(" class=\"" + style + "\"");
      }
    }
    sb.append(">");
    if(rows.size() > 0) {
      if(fieldsetStyle != null && fieldsetStyle.length() > 0) {
        if(fieldsetStyle.indexOf(':') > 0) {
          sb.append("<fieldset style=\"" + fieldsetStyle + "\">");
        }
        else {
          sb.append("<fieldset class=\"" + fieldsetStyle + "\">");
        }
      }
      else {
        sb.append("<fieldset>");
      }
    }
    if(btnsUp && btns != null && btns.size() > 0) {
      if(!columnLayout) {
        sb.append("<div class=\"" + ROW_CLASS + "\">");
      }
      else {
        sb.append("<br/>");
      }
      for(int i = 0; i < btns.size(); i++) {
        WField wButton = btns.get(i);
        sb.append(wButton.toString());
        if(i < btns.size()-1) sb.append("&nbsp;&nbsp;");
      }
      if(!columnLayout) sb.append("</div>");
      sb.append("<br/>");
    }
    for(int r = 0; r < rows.size(); r++) {
      if(!columnLayout) sb.append("<div class=\"" + ROW_CLASS + "\">");
      
      List<WField> currRow = rows.get(r);
      
      int iSizeRow = currRow.size();
      int iSmL = 1;
      int iSmF = 1;
      if(iSizeRow <  2) { iSmL = 3 + incLab; iSmF = 9 - incLab; }
      else if(iSizeRow == 2) { iSmL = 2 + incLab; iSmF = 4 - incLab; }
      else if(iSizeRow == 3) { iSmL = 1 + incLab; iSmF = 3 - incLab; }
      else if(iSizeRow == 4) { iSmL = 1 + incLab; iSmF = 2 - incLab; } 
      else {
        iSmL = 1; iSmF = 1;
      }
      int iRowItems = currRow.size();
      for(int c = 0; c < iRowItems; c++) {
        WField wField = currRow.get(c);
        
        Type   type   = wField.getType();
        String sName  = wField.getId();
        String sLabel = wField.getLabel();
        String sHtml  = wField.getHtml();
        
        if(type == Type.COMPONENT && sHtml != null && sHtml.length() > 0) {
          if(sLabel == null || sLabel.length() == 0) {
            sb.append(sHtml);
            continue;
          }
        }
        
        if(sName != null && sName.startsWith("!")) {
          sName = sName.substring(1);
        }
        if(sName == null || sName.length() == 0) {
          sName = "f" + r + "_" + c;
          wField.setId(sName);
        }
        
        if(sLabel == null) sLabel = "";
        int iSepPlaceHolder = sLabel.indexOf('^');
        if(iSepPlaceHolder > 0) {
          sLabel = sLabel.substring(0, iSepPlaceHolder);
        }
        
        if(type == Type.BLANK && !columnLayout) {
          String sStyleBlank = "";
          if(STYLE_DIV_BLANK != null && STYLE_DIV_BLANK.length() > 0) {
            sStyleBlank = " style=\"" + STYLE_DIV_BLANK + "\"";
          }
          // Empty
          if(sHtml != null && sHtml.length() > 50) {
            sb.append("<div class=\"" + COL_CLASS_BEG + "12" + COL_CLASS_END + "\"" + sStyleBlank + ">" + sHtml + "</div>");
          }
          else if(sHtml != null && sHtml.length() > 1) {
            sb.append("<div class=\"" + COL_CLASS_BEG + iSmL + COL_CLASS_END + "\"" + sStyleBlank + "></div>");
            sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\"" + sStyleBlank + ">" + sHtml + "</div>");
          }
          else {
            sb.append("<div class=\"" + COL_CLASS_BEG + iSmL + COL_CLASS_END + "\"" + sStyleBlank + "></div>");
            sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\"" + sStyleBlank + "></div>");
          }
          continue;
        }
        if(columnLayout) {
          if(sLabel != null && sLabel.length() > 0) {
            if(sLabel.startsWith("<")) {
              sb.append(sLabel);
            }
            else {
              if(labelStyle != null && labelStyle.length() > 0) {
                sb.append("<label for=\"" + namespace + sName + "\" style=\"display:block;" + labelStyle + "\">" + esc(sLabel) + ":</label>");
              }
              else {
                sb.append("<label for=\"" + namespace + sName + "\" style=\"display:block;\">" + esc(sLabel) + ":</label>");
              }
            }
          }
        }
        else {
          if(STYLE_DIV_LABEL != null && STYLE_DIV_LABEL.length() > 0) {
            sb.append("<div class=\"" + COL_CLASS_BEG + iSmL + COL_CLASS_END + "\" style=\"" + STYLE_DIV_LABEL + "\">");
          }
          else {
            sb.append("<div class=\"" + COL_CLASS_BEG + iSmL + COL_CLASS_END + "\">");
          }
          if(sLabel != null && sLabel.length() > 0) {
            if(sLabel.startsWith("<")) {
              sb.append(sLabel);
            }
            else {
              if(labelStyle != null && labelStyle.length() > 0) {
                sb.append("<label for=\"" + sName + "\" style=\"" + labelStyle + "\">" + esc(sLabel) + ":</label>");
              }
              else {
                sb.append("<label for=\"" + sName + "\">" + esc(sLabel) + ":</label>");
              }
            }
          }
          sb.append("</div>");
        }
        
        if(!columnLayout) {
          if(type == Type.TEXTFIELD || type == Type.DATEFIELD || type == Type.TIMEFIELD || type == Type.FILEFIELD) {
            if(STYLE_DIV_INPUT != null && STYLE_DIV_INPUT.length() > 0) {
              sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\" style=\"" + STYLE_DIV_INPUT + "\">");
            }
            else {
              sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\">");
            }
          }
          else if(type == Type.STATICTEXT) {
            if(STYLE_DIV_STATIC != null && STYLE_DIV_STATIC.length() > 0) {
              sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\" style=\"" + STYLE_DIV_STATIC + "\">");
            }
            else {
              sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\">");
            }
          }
          else {
            if(STYLE_DIV_FIELD != null && STYLE_DIV_FIELD.length() > 0) {
              sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\" style=\"" + STYLE_DIV_FIELD + "\">");
            }
            else {
              sb.append("<div class=\"" + COL_CLASS_BEG + iSmF + COL_CLASS_END + "\">");
            }
          }
        }
        
        sb.append(wField.toString(namespace));
        
        if(!columnLayout) {
          if(c == iRowItems - 1 && endRow != null) {
            sb.append(endRow);
          }
          sb.append("</div>");
        }
      }
      if(!columnLayout) sb.append("</div>");
    }
    if(btnsDown && btns != null && btns.size() > 0) {
      if(!columnLayout) {
        sb.append("<div class=\"" + ROW_CLASS + "\">");
        sb.append("<br/>");
      }
      else {
        sb.append("<br/>");
        sb.append("<br/>");
      }
      for(int i = 0; i < btns.size(); i++) {
        WField wButton = btns.get(i);
        sb.append(wButton.toString());
        if(i < btns.size()-1) sb.append("&nbsp;&nbsp;");
      }
      if(!columnLayout) sb.append("</div>");
    }
    if(hidd != null) {
      for(int i = 0; i < hidd.size(); i++) {
        WField wHidden = hidd.get(i);
        sb.append(wHidden.toString(namespace));
      }
    }
    if(rows.size() > 0) sb.append("</fieldset>");
    if(footer != null && footer.length() > 0) {
      sb.append(footer);
    }
    sb.append("</form>");
    if(after != null && after.length() > 0) {
      sb.append(after);
    }
    return sb.toString();
  }

  @Override
  public
  boolean equals(Object obj) 
  {
    if(obj instanceof WebForm) {
      String sObjActionUrl = ((WebForm) obj).getActionUrl();
      return sObjActionUrl != null && sObjActionUrl.equals(actionUrl); 
    }
    return false;
  }

  @Override
  public
  int hashCode()
  {
    if(actionUrl != null) return actionUrl.hashCode();
    return 0;
  }

  @Override
  public
  String toString()
  {
    StringBuffer sb = new StringBuffer();
    boolean build = (rows != null && rows.size() > 0) || (btns != null && btns.size() > 0) || (hidd != null && hidd.size() > 0);
    if(build) {
      sb.append(build());
    }
    if(forms != null && forms.size() > 0){
      if(!build && before != null && before.length() > 0) {
        sb.append(before);
      }
      for (int i = 0; i < forms.size(); i++){
        sb.append(titles.get(i));
        sb.append(forms.get(i).toString());
      }
      if(!build && after != null && after.length() > 0) {
        sb.append(after);
      }
    }
    return sb.toString();
  }

  static enum Type
  {
    STATICTEXT, TEXTFIELD, TEXTAREA, CHECKBOX, RADIOBUTTON, DATEFIELD, TIMEFIELD, SELECT, FILEFIELD, HIDDEN, 
    COMPONENT, BLANK, 
    BUTTON, SUBMIT, RESET 
  }

  static class WField implements Serializable
  {
    private static final long serialVersionUID = 8260748273213660357L;
    
    protected Type    type;
    protected String  id      = "";
    protected String  label   = "";
    protected String  value   = "";
    protected String  attr    = "";
    protected String  html    = "";
    protected boolean checked = false;
    protected List<?> options;

    public WField()
    {
      this.type  = Type.BLANK;
    }

    public WField(Type type, String id, String label)
    {
      this.type    = type;
      this.id      = id;
      this.label   = label;
    }

    public WField(Type type, String id, String label, boolean checked)
    {
      this.type    = type;
      this.id      = id;
      this.label   = label;
      this.checked = checked;
    }

    public WField(Type type, String id, String label, String value, boolean checked)
    {
      this.type    = type;
      this.id      = id;
      this.label   = label;
      this.value   = value;
      this.checked = checked;
    }

    public WField(Type type, String id, String label, List<?> options)
    {
      this.type    = type;
      this.id      = id;
      this.label   = label;
      this.options = options;
      if(this.options == null) {
        this.options = Collections.EMPTY_LIST;
      }
    }

    public WField(Type type, String html)
    {
      this.type  = type;
      this.html  = html;
    }

    public WField(Type type, String id, String label, String value)
    {
      this.type  = type;
      this.id    = id;
      this.label = label;
      this.value = value;
    }

    public WField(Type type, String id, String label, List<?> options, String value)
    {
      this.type    = type;
      this.id      = id;
      this.label   = label;
      this.value   = value;
      this.options = options;
      if(this.options == null) {
        this.options = Collections.EMPTY_LIST;
      }
    }

    public WField(Type type, String id, String label, List<?> options, String value, String attr)
    {
      this.type    = type;
      this.id      = id;
      this.label   = label;
      this.value   = value;
      this.attr    = attr;
      this.options = options;
      if(this.options == null) {
        this.options = Collections.EMPTY_LIST;
      }
    }

    public WField(Type type, String id, String label, String value, String attr)
    {
      this.type  = type;
      this.id    = id;
      this.label = label;
      this.value = value;
      this.attr  = attr;
    }

    public Type getType() {
      return type;
    }

    public void setType(Type type) {
      this.type = type;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public String getAttr() {
      return attr;
    }

    public void setAttr(String attr) {
      this.attr = attr;
    }

    public String getHtml() {
      return html;
    }

    public void setHtml(String html) {
      this.html = html;
    }

    public boolean isChecked() {
      return checked;
    }

    public void setChecked(boolean checked) {
      this.checked = checked;
    }

    public List<?> getOptions() {
      return options;
    }

    public void setOptions(List<?> options) {
      this.options = options;
    }

    public WField append(Object obj) {
      if(obj == null) obj = "";
      String sObjHtml = obj.toString();
      if(sObjHtml != null && sObjHtml.length() > 0) {
        if(this.html == null) this.html = "";
        this.html += sObjHtml;
      }
      return this;
    }

    @Override
    public boolean equals(Object obj) {
      if(obj instanceof WField) {
        String sId = ((WField) obj).getId();
        return sId != null && sId.equals(id); 
      }
      return false;
    }

    @Override
    public int hashCode() {
      if(id != null) return id.hashCode();
      return 0;
    }
    
    @Override
    public String toString() {
      return toString("");
    }
    
    public String toString(String namespace) {
      StringBuilder sb = new StringBuilder();
      
      if(html != null && html.length() > 0) {
        sb.append(html);
        return sb.toString();
      }
      
      if(value == null) value = "";
      if(value != null && value.equals("null")) value = "";
      if(type == Type.BUTTON || type == Type.SUBMIT || type == Type.RESET) {
        String onClickAttr = "";
        if(value != null && value.length() > 0) {
          onClickAttr = "onclick=\"" + esc(value) + "\" ";
        }
        if(type == Type.SUBMIT) {
          sb.append("<button " + onClickAttr + "type=\"submit\">");
        }
        else if(type == Type.RESET) {
          sb.append("<button " + onClickAttr + "type=\"reset\">");
        }
        else {
          sb.append("<button " + onClickAttr + "type=\"button\">");
        }
        sb.append(esc(label));
        sb.append("</button>");
        return sb.toString();
      }
      
      if(namespace == null) namespace = "";
      
      String sName  = this.id;
      String sLabel = this.label;
      String sTAttr = this.attr;
      
      if(sTAttr == null) sTAttr = "";
      if(sTAttr.length() > 0) sTAttr = " " + sTAttr;
      if(sName != null && sName.startsWith("!")) {
        sName = sName.substring(1);
        if(sTAttr != null && sTAttr.length() > 0) sTAttr = " ";
        sTAttr += "disabled=\"disabled\"";
      }
      if(sName == null || sName.length() == 0) {
        sName = "f" + Math.round(Math.random() * 100000);
      }
      
      if(type == Type.TEXTFIELD || type == Type.DATEFIELD || type == Type.TIMEFIELD) {
        if(value != null && value.length() > 0) {
          if(sTAttr != null && sTAttr.length() > 0) sTAttr += " ";
          sTAttr += "value=\"" + esc(value) + "\"";
        }
      }
      
      if(sLabel == null) sLabel = "";
      int iSepPlaceHolder = sLabel.indexOf('^');
      String sPlaceholder = sLabel;
      if(iSepPlaceHolder > 0) {
        sPlaceholder = sLabel.substring(iSepPlaceHolder + 1);
        sLabel = sLabel.substring(0, iSepPlaceHolder);
      }
      if(sLabel != null && sLabel.length() > 20) {
        sPlaceholder = "";
      }
      
      if(type == Type.TEXTFIELD) {
        sb.append("<input type=\"text\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">");
      }
      else if(type == Type.HIDDEN) {
        sb.append("<input type=\"hidden\" name=\"" + namespace + sName +  "\" id=\"" + sName + "\" value=\"" + esc(value) + "\">");
      }
      else if(type == Type.STATICTEXT) {
        if(value != null && value.startsWith("<") && value.endsWith(">")) {
          sb.append(value);
        }
        else {
          sb.append(esc(value));
        }
      }
      else if(type == Type.TEXTAREA) {
        sb.append("<textarea placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">");
        if(value != null && value.length() > 0) {
          sb.append(esc(value));
        }
        sb.append("</textarea>");
      }
      else if(type == Type.CHECKBOX) {
        sb.append("<input type=\"checkbox\" name=\"" + namespace + sName + "\" id=\"" + sName + "\" value=\"1\"" + sTAttr + ">");
      }
      else if(type == Type.DATEFIELD) {
        sb.append("<input type=\"text\" class=\"fdate\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\"  id=\"" + sName + "\"" + sTAttr + ">");
      }
      else if(type == Type.TIMEFIELD) {
        sb.append("<input type=\"text\" class=\"ftime\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\"  id=\"" + sName + "\"" + sTAttr + ">");
      }
      else if(type == Type.RADIOBUTTON) {
        if(value != null && value.length() > 0) {
          sb.append("<input type=\"radio\" name=\"" + namespace + sLabel + "\" id=\"" + sName + "\" " + (checked ? "checked" : "") + " value=\"" + esc(value) + "\"> " + esc(value));
        }
        else {
          sb.append("<input type=\"radio\" name=\"" + namespace + sLabel + "\" id=\"" + sName + "\"" + sTAttr + "> ");
        }
      }
      else if(type == Type.COMPONENT) {
        if(html == null) html = "";
        sb.append(html);
      }
      else if(type == Type.SELECT) {
        sb.append("<select name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">");
        if(options != null && options.size() > 0) {
          for(int i = 0; i < options.size(); i++) {
            Object oOption   = options.get(i);
            String sSelected = "";
            if(oOption instanceof List) {
              List<?> listOption = (List<?>) oOption;
              if(listOption.size() == 0) continue;
              Object oValue = listOption.get(0);
              if(oValue == null) continue;
              Object oDesc  = listOption.size() > 1 ? listOption.get(1) : null;
              if(value != null && value.length() > 0) {
                if(value.equals(oValue)) {
                  sSelected = " selected";
                }
                else if(oDesc != null && value.equals(oDesc)) {
                  sSelected = " selected";
                }
              }
              if(oDesc != null) {
                sb.append("<option value=\"" + oValue + "\"" + sSelected + ">" + esc(oDesc.toString()) + "</option>");
              }
              else {
                sb.append("<option" + sSelected + ">" + esc(oValue.toString()) + "</option>");
              }
            }
            else {
              if(oOption == null) oOption = "";
              if(value != null && value.equals(oOption)) {
                sSelected = " selected";
              }
              sb.append("<option" + sSelected + ">" + esc(oOption.toString()) + "</option>");
            }
          }
        }
        sb.append("</select>");
      }
      else if(type == Type.FILEFIELD) {
        boolean boOnlyPdf    = false;
        boolean boOnlyImages = false;
        if(value != null && value.length() > 0) {
          String sValueLC = value.toLowerCase();
          if(sValueLC.indexOf("pdf") >= 0) {
            boOnlyPdf = true;
          }
          if(sValueLC.indexOf("bmp") >= 0 || sValueLC.indexOf("jpg") >= 0 || sValueLC.indexOf("png") >= 0) {
            boOnlyImages = true;
          }
        }
        if(sTAttr == null || sTAttr.length() == 0) {
          sTAttr = " style=\"width:100%\"";
        }
        if(boOnlyImages) {
          sb.append("<input type=\"file\" accept=\".bmp, .jpg, .png, image/bmp, image/jpeg, image/png\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">" + value);
        }
        else if(boOnlyPdf) {
          sb.append("<input type=\"file\" accept=\".pdf, application/pdf\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">" + value);
        }
        else {
          sb.append("<input type=\"file\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">" + value);
        }
      }
      return sb.toString();
    }
  }
}