package es.sistra.clientcert.persistence.ejb;

import java.rmi.RemoteException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.sf.hibernate.SessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.util.GeneradorId;
import es.sistra.clientcert.model.RespuestaValidarCertificado;
import es.sistra.clientcert.persistence.util.Configuracion;
import es.sistra.clientcert.persistence.util.VerificadorCertificados;

/**
 * SessionBean que implementa la interfaz servicio login
 *
 * @ejb.bean
 *  name="login/persistence/LoginFacade"
 *  jndi-name="es.sistra.clientcert.persistence.LoginFacade"
 *  type="Stateless"
 *  view-type="remote" 
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * 
 *
 */
public class LoginFacadeEJB implements SessionBean  {
 
	private static Log log = LogFactory.getLog(LoginFacadeEJB.class);
	
	 protected SessionFactory sf = null;
     protected SessionContext ctx = null;

    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }

    public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.todos}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public void ejbCreate() throws CreateException {			
	}
	
	public void ejbRemove() throws EJBException, RemoteException {	
	}
	
	/**
	 * Genera ticket de login
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public String generarTicket(X509Certificate certificado, String urlCallbackLogin, String urlDestino, String idioma) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			// Creamos instancia verificador
			String classNameVerificador = Configuracion.getInstance().getProperty("login.clientCert.verificador");
			VerificadorCertificados verificadorCertificados =  (VerificadorCertificados) Class.forName(classNameVerificador).newInstance();
			
			// Obtenemos informacion del certificado
			RespuestaValidarCertificado resp = verificadorCertificados.validarCertificado(Configuracion.getInstance(), certificado);
			if (!resp.isValido()) {
				throw new Exception("El certiticado no es valido");
			}
			
			String nif = resp.getNif();
			String nombre = resp.getNombre();		
					
			// Genera ticket
			String ticket = GeneradorId.generarId();
			
			// Insertamos ticket en ZPE
			InitialContext ctx = new InitialContext();
			String datasource = Configuracion.getInstance().getProperty("login.clientCert.datasource");
			DataSource ds = (DataSource) ctx.lookup(datasource);
			conn = ds.getConnection();
			
			ps = conn.prepareStatement("INSERT INTO ZPE_TCKCRT (TKC_TICKET, TKC_FCALTA, TKC_IDIOMA, TKC_URLCBK, TKC_URLDST, TKC_NIF, TKC_NOMAPE) VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, ticket);
	    	ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis())); 
	    	ps.setString(3, idioma); 
	    	ps.setString(4, urlCallbackLogin);
	    	ps.setString(5, urlDestino);
	    	ps.setString(6, nif);
	    	ps.setString(7, nombre);
	    	ps.execute();    	       	     
			
	    	return ticket;	
    	
		} catch (Exception ex) {
			log.error("Excepcion generando ticket: " + ex.getMessage() , ex);
			throw new EJBException("Excepcion generando ticket: " + ex.getMessage() , ex);
		 }finally {	    	  
	    	  if( ps != null )  try{ps.close();}catch(SQLException e){}
	          if( conn != null ) try { conn.close();} catch (SQLException ex){}	          
	        }
		
			
	}

}
