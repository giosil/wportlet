package org.dew.portlet.ui;

import org.dew.portlet.WNames;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"rawtypes"})
/**
 * Classe per la costruzione di Form Web.
 * Componenti implementati:
 * 
 * t=TextField, l=Label (StaticText), n=notes (TextArea), c=CheckBox, r=RadioButton
 * d=DateField, h=Hours (TimeField),  s=Select, f=FileField, e=Empty
 */
public 
class WebForm implements Serializable
{
  private static final long serialVersionUID = -8722452868995707246L;
  
  protected String  title;
  protected boolean columnLayout    = false;
  protected String  labelStyle      = null;
  protected String  fieldsetStyle   = null;
  protected String  focusOn         = null;
  protected List<List<String>> rows = new ArrayList<List<String>>();
  protected List<String>       btns = new ArrayList<String>();
  
  protected Map<String,List>   opts = null;
  protected Set<String>    selected = new HashSet<String>();
  protected List<String>       sele = null;
  protected List<String>       date = null;
  protected List<String>       time = null;
  protected List<String>       hidd = null;
  
  protected String name;
  protected String id;
  protected String initFunction;
  protected String namespace;
  protected String method;
  protected String actionUrl;
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
  protected Map<String,String> mapData;
  
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
    rows.add(new ArrayList<String>());
  }
  
  public 
  void addComponent(String sComponent)
  {
    List<String> currRow = getCurrentRow();
    currRow.add(sComponent);
  }
  
  public 
  void addStaticText(String sId, String sLabel, String sText)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("l|" + sId + "|" + sLabel + "|" + sText);
  }
  
  public 
  void addStaticText(String sId, String sLabel, String sText, String sStyle)
  {
    List<String> currRow = getCurrentRow();
    if(sStyle != null && sStyle.length() > 0) {
      currRow.add("l|" + sId + "|" + sLabel + "|<span id=\"" + sId + "\" style=\"" + sStyle + "\">" + sText + "</span>");
    }
    else {
      currRow.add("l|" + sId + "|" + sLabel + "|" + sText);
    }
  }
  
  public 
  void addTextField(String sId, String sLabel)
  {
    List<String> currRow = getCurrentRow();
    String row = "t|" + sId + "|" + sLabel;
    currRow.add(row);
  }
  
  public 
  void addTextField(String sId, String sLabel, String sTagAttributes)
  {
    List<String> currRow = getCurrentRow();
    String row = "t|" + sId + "|" + sLabel;
    if(sTagAttributes != null && sTagAttributes.length() > 0) {
      row += "|" + sTagAttributes;
    }
    currRow.add(row);
  }
  
  public 
  void addTextField(String sId, String sLabel, String sTagAttributes, String sText)
  {
    List<String> currRow = getCurrentRow();
    String row = "t|" + sId + "|" + sLabel;
    if(sTagAttributes != null && sTagAttributes.length() > 0) {
      row += "|" + sTagAttributes;
      if(sText != null && sText.length() > 0) {
        row += " value=\"" + sText.replace("\"", "\\\"") + "\"";
      }
    }
    else {
      row += "|" + sText;
    }
    currRow.add(row);
  }
  
  public 
  void addTextField(String sId, String sLabel, String sTagAttributes, boolean boEnabled)
  {
    if(boEnabled) {
      addTextField(sId, sLabel, sTagAttributes);
    }
    else {
      addStaticText(sId, sLabel, sTagAttributes);
    }
  }
  
  public 
  void addTextArea(String sId, String sLabel, int iRows, int iCols)
  {
    List<String> currRow = getCurrentRow();
    if(iRows > 0 || iCols > 0) {
      if(iRows < 1) iRows = 1;
      if(iCols > 0) {
        currRow.add("n|" + sId + "|" + sLabel + "|rows=\" + iRows + \" cols=\"" + iCols + "\"");
      }
      else {
        currRow.add("n|" + sId + "|" + sLabel + "|rows=\" + iRows + \"");
      }
    }
    else {
      currRow.add("n|" + sId + "|" + sLabel + "|");
    }
  }
  
  public 
  void addTextArea(String sId, String sLabel, int iRows, int iCols, String sTagAttributes)
  {
    List<String> currRow = getCurrentRow();
    if(sTagAttributes == null) sTagAttributes = "";
    if(sTagAttributes != null && sTagAttributes.length() > 0) {
      sTagAttributes = " " + sTagAttributes;
    }
    if(iRows > 0 || iCols > 0) {
      if(iRows < 1) iRows = 1;
      if(iCols > 0) {
        currRow.add("n|" + sId + "|" + sLabel + "|rows=\" + iRows + \" cols=\"" + iCols + "\"" + sTagAttributes);
      }
      else {
        currRow.add("n|" + sId + "|" + sLabel + "|rows=\" + iRows + \"" + sTagAttributes);
      }
    }
    else {
      currRow.add("n|" + sId + "|" + sLabel + "|" + sTagAttributes);
    }
  }
  
  public 
  void addTextArea(String sId, String sLabel, int iRows, int iCols, String sTagAttributes, String sText)
  {
    List<String> currRow = getCurrentRow();
    if(sTagAttributes == null) sTagAttributes = "";
    if(sTagAttributes != null && sTagAttributes.length() > 0) {
      sTagAttributes = " " + sTagAttributes;
    }
    if(iRows > 0 || iCols > 0) {
      if(iRows < 1) iRows = 1;
      if(iCols > 0) {
        currRow.add("n|" + sId + "|" + sLabel + "|rows=\" + iRows + \" cols=\"" + iCols + "\"" + sTagAttributes);
      }
      else {
        currRow.add("n|" + sId + "|" + sLabel + "|rows=\" + iRows + \"" + sTagAttributes);
      }
    }
    else {
      currRow.add("n|" + sId + "|" + sLabel + "|" + sTagAttributes);
    }
    if(mapData == null) mapData = new HashMap<String, String>();
    mapData.put(sId, sText);
  }
  
  public 
  void addCheckBox(String sId, String sLabel)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("c|" + sId + "|" + sLabel);
  }
  
  public 
  void addCheckBox(String sId, String sLabel, boolean checked)
  {
    List<String> currRow = getCurrentRow();
    if(checked) {
      currRow.add("c|" + sId + "|" + sLabel + "|checked=\"checked\"");
    }
    else {
      currRow.add("c|" + sId + "|" + sLabel);
    }
  }
  
  public 
  void addRadioButton(String sId, String sName, boolean checked)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("r|" + sId + "||" + sName);
    if(checked) {
      selected.add(sName);
    }
    else {
      selected.remove(sName);
    }
  }
  
  public 
  void addDateField(String sId, String sLabel)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("d|" + sId + "|" + sLabel);
    if(date == null) date = new ArrayList<String>();
    date.add(sId);
  }
  
  public 
  void addDateField(String sId, String sLabel, String sTagAttributes)
  {
    List<String> currRow = getCurrentRow();
    if(sTagAttributes != null && sTagAttributes.length() > 0) {
      currRow.add("d|" + sId + "|" + sLabel + "|" + sTagAttributes);
    }
    else {
      currRow.add("d|" + sId + "|" + sLabel);
    }
    if(date == null) date = new ArrayList<String>();
    date.add(sId);
  }
  
  public 
  void addTimeField(String sId, String sLabel)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("h|" + sId + "|" + sLabel);
    if(time == null) time = new ArrayList<String>();
    time.add(sId);
  }
  
  public 
  void addSelect(String sId, String sLabel, HttpServletRequest request, String sAttribute)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("s|" + sId + "|" + sLabel);
    if(opts == null) opts = new HashMap<String, List>();
    Object oOption = request.getAttribute(sAttribute);
    if(oOption instanceof List) {
      opts.put(sId, (List) oOption);
    }
    else {
      opts.put(sId, new ArrayList(0));
    }
    if(sele == null) sele = new ArrayList<String>();
    sele.add(sId);
  }
  
  public 
  void addSelect(String sId, String sLabel, List listOptions)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("s|" + sId + "|" + sLabel);
    if(opts == null) opts = new HashMap<String, List>();
    opts.put(sId, listOptions);
    if(sele == null) sele = new ArrayList<String>();
    sele.add(sId);
  }
  
  public 
  void addSelect(String sId, String sLabel, List listOptions, String sSelectedItem)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("s|" + sId + "|" + sLabel + "|" + sSelectedItem);
    if(opts == null) opts = new HashMap<String, List>();
    opts.put(sId, listOptions);
    if(sele == null) sele = new ArrayList<String>();
    sele.add(sId);
  }
  
  public 
  void addSelect(String sId, String sLabel, String... asOptions)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("s|" + sId + "|" + sLabel);
    if(opts == null) opts = new HashMap<String, List>();
    opts.put(sId, Arrays.asList(asOptions));
    if(sele == null) sele = new ArrayList<String>();
    sele.add(sId);
  }
  
  public 
  void addHiddenField(String sId, String sValue)
  {
    if(hidd == null) hidd = new ArrayList<String>();
    hidd.add(sId + "|" + sValue);
  }
  
  public 
  void addFileField(String sId, String sLabel)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("f|" + sId + "|" + sLabel);
    multipart = true;
  }
  
  public 
  void addFileField(String sId, String sLabel, String sNotes)
  {
    List<String> currRow = getCurrentRow();
    currRow.add("f|" + sId + "|" + sLabel + "|" + sNotes);
    multipart = true;
  }
  
  public 
  void addBlankField()
  {
    List<String> currRow = getCurrentRow();
    currRow.add("e|&nbsp;|");
  }
  
  public 
  void addBlankField(String sText)
  {
    List<String> currRow = getCurrentRow();
    if(sText != null && sText.length() > 0) {
      currRow.add("e|&nbsp;|" + sText);
    }
    else {
      currRow.add("e|&nbsp;|");
    }
  }
  
  public 
  void addButton(String sLabel, String sOnClick)
  {
    btns.add("b|" + sLabel + "|" + sOnClick);
  }
  
  public 
  void addSubmit(String sLabel, String sOnClick)
  {
    btns.add("s|" + sLabel + "|" + sOnClick);
  }
  
  public 
  void addSubmit(String sLabel)
  {
    btns.add("s|" + sLabel + "|");
  }
  
  public 
  void addCancel(String sLabel, String sOnClick)
  {
    btns.add("c|" + sLabel + "|" + sOnClick);
  }
  
  public 
  void addCancel(String sLabel)
  {
    btns.add("c|" + sLabel + "|");
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
  List<String> getCurrentRow()
  {
    List<String> listResult = null;
    if(rows.size() == 0) {
      listResult = new ArrayList<String>();
      rows.add(listResult);
    }
    else {
      listResult = rows.get(rows.size()-1);
    }
    return listResult;
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
    StringBuffer sb = new StringBuffer();
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
      sb.append("<div class=\"section group\"><strong>" + title + "</strong></div>");
    }
    sb.append("<form");
    if(id != null && id.length() > 0) {
      sb.append(" id=\"" + id + "\"");
    }
    if(name != null && name.length() > 0) {
      sb.append(" name=\"" + name + "\"");
    }
    if(onSubmit != null && onSubmit.length() > 0) {
      sb.append(" onsubmit=\"" + onSubmit + "\"");
    }
    else {
      if(validation != null && validation.length() > 0) {
        sb.append(" onsubmit=\"" + validation + "\"");
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
    sb.append(">");
    if(rows.size() > 0) {
      if(fieldsetStyle != null && fieldsetStyle.length() > 0) {
        sb.append("<fieldset style=\"" + fieldsetStyle + "\">");
      }
      else {
        sb.append("<fieldset>");
      }
    }
    if(btnsUp && btns != null && btns.size() > 0) {
      if(!columnLayout) {
        sb.append("<div class=\"section group\">");
      }
      else {
        sb.append("<br/>");
      }
      for(int i = 0; i < btns.size(); i++) {
        String sElement = btns.get(i);
        int iSep1 = sElement.indexOf('|');
        if(iSep1 <= 0) continue;
        int iSep2 = sElement.indexOf('|', iSep1+1);
        if(iSep2 <= 0) continue;
        String sType    = sElement.substring(0, iSep1);
        String sLabel   = sElement.substring(iSep1+1, iSep2);
        String sOnClick = sElement.substring(iSep2+1);
        String oc = "";
        if(sOnClick != null && sOnClick.length() > 0 && !sOnClick.equals("null")) {
          oc = "onclick=\"" + sOnClick + "\" ";
        }
        if(sType.startsWith("s")) {
          sb.append("<button " + oc + "type=\"submit\">");
        }
        else if(sType.startsWith("c")) {
          sb.append("<button " + oc + "type=\"reset\">");
        }
        else {
          sb.append("<button " + oc + "type=\"button\">");
        }
        sb.append(sLabel);
        sb.append("</button>");
        if(i < btns.size()-1) sb.append("&nbsp;&nbsp;");
      }
      if(!columnLayout) sb.append("</div>");
      sb.append("<br/>");
    }
    for(int r = 0; r < rows.size(); r++) {
      if(!columnLayout) sb.append("<div class=\"section group\">");
      List<String> currRow = rows.get(r);
      int iSizeRow = currRow.size();
      int iSmL = 1;
      int iSmF = 1;
      if(iSizeRow <  2) { iSmL = 3 + incLab; iSmF = 9 - incLab; } else
      if(iSizeRow == 2) { iSmL = 2 + incLab; iSmF = 4 - incLab; } else
      if(iSizeRow == 3) { iSmL = 1 + incLab; iSmF = 3 - incLab; } else
      if(iSizeRow == 4) { iSmL = 1 + incLab; iSmF = 2 - incLab; } else {
        iSmL = 1; iSmF = 1;
      }
      int iRowItems = currRow.size();
      for(int c = 0; c < iRowItems; c++) {
        String sElement = currRow.get(c);
        if(sElement.startsWith("<")) {
          sb.append(sElement);
          continue;
        }
        int iSep1 = sElement.indexOf('|');
        if(iSep1 <= 0) continue;
        int iSep2 = sElement.indexOf('|', iSep1+1);
        if(iSep2 <= 0) continue;
        String sType  = sElement.substring(0,iSep1);
        String sName  = sElement.substring(iSep1+1,iSep2);
        String sLabel = sElement.substring(iSep2+1);
        
        String sTAttr = "";
        int iSep3 = sLabel.indexOf('|');
        if(iSep3 > -1) {
          sTAttr = sLabel.substring(iSep3+1);
          sLabel = sLabel.substring(0,iSep3);
          if(sTAttr.length() > 0 && !sTAttr.equals("null")) {
            sTAttr = " " + sTAttr;
          }
          else {
            sTAttr = "";
          }
        }
        if(sName != null && sName.startsWith("!")) {
          sName    = sName.substring(1);
          if(sTAttr.length() > 0 && !sTAttr.equals("null")) {
            sTAttr += " disabled=\"disabled\"";
          }
          else {
            sTAttr = " disabled=\"disabled\"";
          }
        }
        
        int iSepPlaceHolder = sLabel.indexOf('^');
        String sPlaceholder = sLabel;
        if(iSepPlaceHolder > 0) {
          sPlaceholder = sLabel.substring(iSepPlaceHolder + 1);
          sLabel = sLabel.substring(0, iSepPlaceHolder);
        }
        if(sLabel != null && sLabel.length() > 20) {
          sPlaceholder = "";
        }
        
        if(sType.startsWith("e") && !columnLayout) { 
          // Empty
          if(sLabel != null && sLabel.length() > 50) {
            sb.append("<div class=\"col span_12_of_12\">" + sLabel + "</div>");
          }
          else if(sLabel != null && sLabel.length() > 1) {
            sb.append("<div class=\"col span_" + iSmL + "_of_12\"></div>");
            sb.append("<div class=\"col span_" + iSmF + "_of_12\">" + sLabel + "</div>");
          }
          else {
            sb.append("<div class=\"col span_" + iSmL + "_of_12\"></div>");
            sb.append("<div class=\"col span_" + iSmF + "_of_12\"></div>");
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
                sb.append("<label for=\"" + namespace + sName + "\" style=\"display:block;" + labelStyle + "\">" + sLabel + ":</label>");
              }
              else {
                sb.append("<label for=\"" + namespace + sName + "\" style=\"display:block;\">" + sLabel + ":</label>");
              }
            }
          }
        }
        else {
          sb.append("<div class=\"col span_" + iSmL + "_of_12\" style=\"text-align:right;\">");
          if(sLabel != null && sLabel.length() > 0) {
            if(sLabel.startsWith("<")) {
              sb.append(sLabel);
            }
            else {
              if(labelStyle != null && labelStyle.length() > 0) {
                sb.append("<label for=\"" + sName + "\" style=\"" + labelStyle + "\">" + sLabel + ":</label>");
              }
              else {
                sb.append("<label for=\"" + sName + "\">" + sLabel + ":</label>");
              }
            }
          }
          sb.append("</div>");
        }
        
        if(!columnLayout) {
          if(sType.equals("t") || sType.startsWith("d") || sType.startsWith("h")) {
            sb.append("<div class=\"col span_" + iSmF + "_of_12\" style=\"margin-top:1px;\">");
          }
          else {
            sb.append("<div class=\"col span_" + iSmF + "_of_12\">");
          }
        }
        
        if(sType.equals("t")) {
          if(sTAttr != null && sTAttr.length() > 0) {
            int iSepAttrVal = sTAttr.indexOf('=');
            if(iSepAttrVal < 0) {
              sTAttr = " value=\"" + sTAttr.trim().replace("\"", "\\\"") + "\"";
            }
          }
          sb.append("<input type=\"text\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">");
        }
        else if(sType.equals("l")) {
          sTAttr = sTAttr.trim();
          if(sTAttr.startsWith("<")|| sTAttr.indexOf("<b") >= 0 || sTAttr.indexOf("<a") >= 0) {
            sb.append(sTAttr);
          }
          else {
            sb.append(sTAttr.replace("<", "&lt;").replace(">", "&gt;"));
          }
        }
        else if(sType.startsWith("n")) {
          String sText = null;
          if(mapData != null) {
            sText = mapData.get(sName);
          }
          if(sText != null && sText.length() > 0) {
            sb.append("<textarea placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">");
            sb.append(sText.replace("<", "&lt;").replace(">", "&gt;"));
            sb.append("</textarea>");
          }
          else {
            sb.append("<textarea placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + "></textarea>");
          }
        }
        else if(sType.startsWith("c")) {
          sb.append("<input type=\"checkbox\" name=\"" + namespace + sName + "\" id=\"" + sName + "\" value=\"1\"" + sTAttr + ">");
        }
        else if(sType.startsWith("d")) {
          if(sTAttr != null && sTAttr.length() > 0) {
            int iSepAttrVal = sTAttr.indexOf('=');
            if(iSepAttrVal < 0) {
              sTAttr = " value=\"" + sTAttr.trim().replace("\"", "\\\"") + "\"";
            }
          }
          sb.append("<input type=\"text\" class=\"fdate\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\"  id=\"" + sName + "\"" + sTAttr + ">");
        }
        else if(sType.startsWith("h")) {
          sb.append("<input type=\"text\" class=\"ftime\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\"  id=\"" + sName + "\"" + sTAttr + ">");
        }
        else if(sType.startsWith("r")) {
          if(sTAttr != null && sTAttr.indexOf("=\"") >= 0) {
            sb.append("<input type=\"radio\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + "> ");
          }
          else {
            String sValue = sTAttr != null ? sTAttr.trim() : "";
            sb.append("<input type=\"radio\" name=\"" + namespace + sName + "\" id=\"" + sName + "\" " + (selected.contains(sValue) ? "checked" : "") + " value=\"" + sValue + "\"> " + sValue);
          }
        } 
        else if(sType.startsWith("s")) {
          String sSelectedItem = null;
          if(sTAttr != null && sTAttr.length() > 0) {
            int iSepAttrVal = sTAttr.indexOf('=');
            if(iSepAttrVal < 0) {
              sSelectedItem = sTAttr.trim();
              sTAttr = "";
            }
          }
          sb.append("<select name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">");
          if(opts != null) {
            List listOptions = opts.get(sName);
            if(listOptions != null && listOptions.size() > 0) {
              for(int i = 0; i < listOptions.size(); i++) {
                Object oOption   = listOptions.get(i);
                String sSelected = "";
                if(oOption instanceof List) {
                  List listOption = (List) oOption;
                  if(listOption.size() == 0) continue;
                  Object oValue = listOption.get(0);
                  if(oValue == null) continue;
                  Object oDesc  = listOption.size() > 1 ? listOption.get(1) : null;
                  if(sSelectedItem != null) {
                    if(sSelectedItem.equals(oValue)) {
                      sSelected = " selected";
                    }
                    else
                    if(oDesc != null && sSelectedItem.equals(oDesc)) {
                      sSelected = " selected";
                    }
                  }
                  if(oDesc != null) {
                    sb.append("<option value=\"" + oValue + "\"" + sSelected + ">" + oDesc + "</option>");
                  }
                  else {
                    sb.append("<option" + sSelected + ">" + oValue + "</option>");
                  }
                }
                else {
                  if(sSelectedItem != null && sSelectedItem.equals(oOption)) {
                    sSelected = " selected";
                  }
                  sb.append("<option" + sSelected + ">" + oOption + "</option>");
                }
              }
            }
          }
          sb.append("</select>");
        }
        else
        if(sType.startsWith("f")) {
          String sNotes = "";
          if(sTAttr != null && sTAttr.length() > 0) {
            int iSepAttrVal = sTAttr.indexOf('=');
            if(iSepAttrVal < 0) {
              sNotes = sTAttr;
              sTAttr = "";
            }
          }
          boolean boOnlyPdf    = false;
          boolean boOnlyImages = false;
          if(sNotes != null && sNotes.length() > 0) {
            if(sNotes.toLowerCase().indexOf("pdf") >= 0) {
              boOnlyPdf = true;
            }
            if(sNotes.toLowerCase().indexOf("jpg") >= 0) {
              boOnlyImages = true;
            }
          }
          if(sTAttr == null || sTAttr.length() == 0) {
            sTAttr = " style=\"width:100%\"";
          }
          if(boOnlyImages) {
            sb.append("<input type=\"file\" accept=\".bmp, .jpg, .png, image/bmp, image/jpeg, image/png\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">" + sNotes);
          }
          else if(boOnlyPdf) {
            sb.append("<input type=\"file\" accept=\".pdf, application/pdf\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">" + sNotes);
          }
          else {
            sb.append("<input type=\"file\" placeholder=\"" + sPlaceholder + "\" name=\"" + namespace + sName + "\" id=\"" + sName + "\"" + sTAttr + ">" + sNotes);
          }
        }
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
        sb.append("<div class=\"section group\">");
        sb.append("<br/>");
      }
      else {
        sb.append("<br/>");
        sb.append("<br/>");
      }
      for(int i = 0; i < btns.size(); i++) {
        String sElement = btns.get(i);
        int iSep1 = sElement.indexOf('|');
        if(iSep1 <= 0) continue;
        int iSep2 = sElement.indexOf('|', iSep1+1);
        if(iSep2 <= 0) continue;
        String sType    = sElement.substring(0, iSep1);
        String sLabel   = sElement.substring(iSep1+1, iSep2);
        String sOnClick = sElement.substring(iSep2+1);
        String oc = "";
        if(sOnClick != null && sOnClick.length() > 0 && !sOnClick.equals("null")) {
          oc = "onclick=\"" + sOnClick + "\" ";
        }
        if(sType.startsWith("s")) {
          sb.append("<button " + oc + "type=\"submit\">");
        }
        else if(sType.startsWith("c")) {
          sb.append("<button " + oc + "type=\"reset\">");
        }
        else {
          sb.append("<button " + oc + "type=\"button\">");
        }
        sb.append(sLabel);
        sb.append("</button>");
        if(i < btns.size()-1) sb.append("&nbsp;&nbsp;");
      }
      if(!columnLayout) sb.append("</div>");
    }
    if(hidd != null) {
      for(int i = 0; i < hidd.size(); i++) {
        String sHiddenField = hidd.get(i);
        int iSep1 = sHiddenField.indexOf('|');
        if(iSep1 <= 0) continue;
        String sName  = sHiddenField.substring(0,  iSep1);
        String sValue = sHiddenField.substring(iSep1 + 1);
        if(sValue != null && sValue.equals("null")) sValue = "";
        sb.append("<input type=\"hidden\" name=\"" + namespace + sName +  "\" id=\"" + sName + "\" value=\"" + sValue + "\">");
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
}