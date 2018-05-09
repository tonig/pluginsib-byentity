package es.sistra.clientcert.persistence.delegate;

import java.rmi.RemoteException;
import java.security.cert.X509Certificate;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.sistra.clientcert.persistence.util.LoginFacadeUtil;
import es.sistra.clientcert.persistence.intf.LoginFacade;

/**
 * Business delegate para operar con asistente pagos
 */
public class LoginDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public String generarTicket(X509Certificate certificado, String urlCallbackLogin, String urlDestino, String idioma) throws DelegateException{
    	try {
            return getFacade().generarTicket(certificado, urlCallbackLogin, urlDestino, idioma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }


    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private LoginFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return LoginFacadeUtil.getHome().create();
    }

    protected LoginDelegate() throws DelegateException {       
    }

	               
}
