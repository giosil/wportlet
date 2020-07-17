package org.dew.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Interfaccia da implementare per gestire gli eventi fondamentali della Portlet.
 */
public 
interface IPortletListener 
{
  /**
   * Invocato alla fine del metodo <code>WPortlet.init</code>. 
   *  
   * @param portletConfig
   */
  public void init(PortletConfig portletConfig);
  
  /**
   * Invocato dopo l'action di login. 
   *  
   * @param user User
   * @param request javax.portlet.ActionRequest
   * @param response javax.portlet.ActionResponse
   */
  public void afterLogin(User user, ActionRequest request, ActionResponse response);
  
  /**
   * Invocato prima dell'action di logout. 
   *  
   * @param user User
   * @param request javax.portlet.ActionRequest
   * @param response javax.portlet.ActionResponse
   */
  public void beforeLogout(User user, ActionRequest request, ActionResponse response);
  
  /**
   * Metodo richiamato nel caso in cui nell'invocazione
   * del metodo di una action si verifica un'eccezione.
   * 
   * @param sAction Azione
   * @param parameters Parametri
   * @param actionException Eccezione
   * @param request javax.portlet.RenderRequest
   * @param response javax.portlet.RenderResponse
   */
  public void exception(String sAction, Parameters parameters, Exception actionException, RenderRequest request, RenderResponse response);
  
  /**
   * Invocato alla fine del metodo <code>WPortlet.destroy</code>. 
   */
  public void destroy();
}
