package es.sistra.clientcert.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.sistra.clientcert.persistence.util.ConfiguracionFacadeUtil;
import es.sistra.clientcert.persistence.intf.ConfiguracionFacadeLocal;

public class ConfiguracionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public String obtenerPropiedad(String propiedad) throws DelegateException
	{
		try
		{			
			return getFacade().obtenerPropiedad(propiedad);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private ConfiguracionFacadeLocal getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return ConfiguracionFacadeUtil.getLocalHome().create();
    }

    protected ConfiguracionDelegate() throws DelegateException {        
    }                  
}

