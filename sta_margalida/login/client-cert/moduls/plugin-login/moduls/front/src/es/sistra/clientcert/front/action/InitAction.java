package es.sistra.clientcert.front.action;

import java.security.cert.X509Certificate;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.ConvertUtil;
import es.sistra.clientcert.front.Constants;
import es.sistra.clientcert.front.form.InitForm;
import es.sistra.clientcert.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="initForm"
 *  path="/init"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 *  
 * @struts.action-forward
 *  name="success" path=".retorno"
 */
public class InitAction extends BaseAction
{

	Log logger = LogFactory.getLog( InitAction.class );
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		Locale local = (java.util.Locale) request.getSession().getAttribute(org.apache.struts.Globals.LOCALE_KEY);
    	request.getSession().setAttribute(Constants.DATOS_SESION_IDIOMA_KEY,local.getLanguage());
				
		try {
	    	// Recogemos parametros
			InitForm initForm = ( InitForm ) form;
			String urlCallback = null;
			String urlDestino = null;
			String idioma = null;

			
			urlCallback = ConvertUtil.base64UrlSafeToCadena(initForm.getUrlCallbackLogin());
			urlDestino = ConvertUtil.base64UrlSafeToCadena(initForm.getUrlDestino());
			idioma = initForm.getIdioma();
				
			// Obtenemos certificado
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");	
			if (certs == null) {
				logger.error("Error proceso de login con certificado: no existe certificado");
				return mapping.findForward("fail");	
			}		
					
			// Generamos ticket
			String ticket = DelegateUtil.getLoginDelegate().generarTicket(certs[0], urlCallback, urlDestino, idioma);
		
			// Establecemos ticket en la sesion
			request.getSession().setAttribute("urlCallbackLogin", urlCallback);
			request.getSession().setAttribute("ticket", ticket);
			request.getSession().setAttribute("idioma", idioma);
			
			return mapping.findForward("success");				
		
		} catch (Exception ex) {
			logger.error("Error proceso de login con certificado: " + ex.getMessage(), ex);
			return mapping.findForward("fail");	
		}
    }
		
}
