package es.sistra.clientcert.persistence.ejb;


import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.sistra.clientcert.persistence.util.Configuracion;

/**
 * EJB que sirve para obtener la configuracion del modulo
 *
 * @ejb.bean
 *  name="login/persistence/ConfiguracionFacade"
 *  local-jndi-name = "es.sistra.clientcert.persistence.ConfiguracionFacade"
 *  type="Stateless"
 *  view-type="local"
 */
public class ConfiguracionFacadeEJB implements SessionBean  {
	
	protected static Log log = LogFactory.getLog(ConfiguracionFacadeEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {		
	}
	
	public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	public void ejbRemove() throws EJBException, RemoteException {
	}

	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
	}
		
	/**
	 * 
	 * Obtiene propiedades de configuracion
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public String obtenerPropiedad(String property) {
    	try{
    		return Configuracion.getInstance().getProperty(property);
    	}catch(Exception ex){
    		throw new EJBException(ex);
    	}         
    }

}
