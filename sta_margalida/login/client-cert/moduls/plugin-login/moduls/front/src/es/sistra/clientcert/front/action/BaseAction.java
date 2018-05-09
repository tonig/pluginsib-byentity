package es.sistra.clientcert.front.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;

import es.sistra.clientcert.front.util.PagosFrontRequestHelper;

/**
 * Action Base.
 */
public abstract class BaseAction extends Action {
	
	public Locale getLocale(HttpServletRequest request) {
		return PagosFrontRequestHelper.getLocale(request);
	}

	public String getLang(HttpServletRequest request) {
		return PagosFrontRequestHelper.getLang(request);
	}	

	protected ActionForm obtenerActionForm(ActionMapping mapping, HttpServletRequest request, String path) {
        ModuleConfig config = mapping.getModuleConfig();
        ActionMapping newMapping = (ActionMapping) config.findActionConfig(path);
        ActionForm newForm = RequestUtils.createActionForm(request, newMapping, config, this.servlet);
        if ("session".equals(newMapping.getScope())) {
            request.getSession(true).setAttribute(newMapping.getAttribute(), newForm);
        } else {
            request.setAttribute(newMapping.getAttribute(), newForm);
        }
        newForm.reset(newMapping, request);
        return newForm;
    }
	
    public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
    	HttpSession session = request.getSession( true );
    	ActionForward result = null;
    	
    	// Ejecutamos tarea
    	synchronized( session )
    	{
    			result = executeTask( mapping, form, request, response );    		    
    	}
    	
    	return result;

    }
    
    public abstract ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

}
