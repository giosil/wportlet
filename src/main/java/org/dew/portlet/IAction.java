package org.dew.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Interfaccia da implementare per gestire l'interazione con la Portlet secondo il parametro <strong>a</strong> (action) che
 * individua una specifica funzione.<br />
 * Il seguente codice illustra un semplice esempio.
 * 
 * <pre>
 * public class HelloAction implements IAction 
 * {
 *   public Object action(String sAction, Parameters parameters, ActionRequest request, ActionResponse response) 
 *    throws Exception 
 *  {
 *    if(parameters.isBlank("name")) {
 *      throw new Exception(ResourcesMgr.getMessage(request, "error.name"));
 *    }
 *
 *    String sName = parameters.getString("name");
 *    
 *    return sName; // Risultato dell'elaborazione
 *  }
 *
 *  public String view(String sAction, Parameters parameters, Object actionResult, RenderRequest request, RenderResponse response)
 *    throws Exception 
 *  {
 *    String sName = (String) actionResult;
 *    
 *    String sHello = ResourcesMgr.getMessage(request, "hello", sName); // da content/Language.properties
 *
 *    request.setAttribute("hello", sHello);
 *    
 *    return "hello.jsp"; // Pagina di rappresentazione
 *  }
 *
 *  public String exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response) 
 *    throws Exception 
 *  {
 *    return null; // L'eccezione viene mostrata con la jsp di default dei messaggi
 *  }
 * }
 * </pre>
 */
public 
interface IAction 
{
  /**
   * Metodo richiamato in corrispondenza di una richiesta di action.<br />
   * L'oggetto restituito viene messo in sessione
   * e riproposto al metodo view con l'azione e i parametri.
   * 
   * @param sAction Azione
   * @param parameters Parametri
   * @param request javax.portlet.ActionRequest
   * @param response javax.portlet.ActionResponse
   * @return Risultato dell'elaborazione
   * @throws Exception
   */
  public
  Object action(String sAction, Parameters parameters, ActionRequest request, ActionResponse response) 
    throws Exception;
  
  /**
   * Metodo richiamato in corrispondenza di una richiesta di view.<br />
   * Esso deve restituire la pagina jsp che sara' mostrata all'interno
   * della Portlet Window.<br />
   * Nel caso si restituisca <code>null</code> viene mostrata la pagina jsp di default.<br />
   * Se l'output viene costruito all'interno dell'Action si puo' restituire <code>WNames.sNO_JSP</code>.
   * In tal modo non viene mostrata alcuna pagina jsp.
   * 
   * @param sAction Azione
   * @param parameters Parametri
   * @param oActionResult Risultato dell'elaborazione
   * @param request javax.portlet.RenderRequest
   * @param response javax.portlet.RenderResponse
   * @return Pagina jsp, null in caso di default, <code>WNames.sNO_JSP</code> nessuna jsp.
   * @throws Exception
   */
  public
  String view(String sAction, Parameters parameters, Object oActionResult, RenderRequest request, RenderResponse response) 
    throws Exception;
  
  /**
   * Metodo richiamato nel caso in cui nell'invocazione
   * del metodo action si verifica un'eccezione.
   * Nel caso si restituisca <code>null</code> viene mostrata la pagina jsp di default per i messaggi.<br />
   * Se l'output viene costruito all'interno dell'Action si puo' restituire <code>WNames.sNO_JSP</code>.
   * In tal modo non viene mostrata alcuna pagina jsp.
   * 
   * @param sAction Azione
   * @param parameters Parametri
   * @param actionException Eccezione
   * @param request javax.portlet.RenderRequest
   * @param response javax.portlet.RenderResponse
   * @return Pagina jsp, null in caso di default.
   * @throws Exception
   */
  public
  String exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response) 
    throws Exception;
}
