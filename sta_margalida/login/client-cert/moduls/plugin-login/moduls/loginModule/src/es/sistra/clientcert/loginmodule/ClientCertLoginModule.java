package es.sistra.clientcert.loginmodule;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.security.acl.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

/**
 * Login module para ClientCert basado en ticket.
 * 
 * 
 * Username={TICKET-sessionId}
 * Password=ticket
 *
 */
public class ClientCertLoginModule extends UsernamePasswordLoginModule {			
	
	/** Timeout ticket (segundos) */
	private long timeoutTicket; 
	
	/** Purga ticket (minutos) */
	private int purgaTicket; 
	
	/**
	 * Principal customizado
	 */
	private Principal caller;
	
	/**
	 * Role de acceso publico
	 */
	private String roleTothom;	

	/**
	 * Helper para generar Principal.
	 */
	private ClientCertHelper clientCertHelper;

	/**
	 * Inicializacion
	 */
	public void initialize(Subject subject, CallbackHandler callbackHandler,Map sharedState, Map options)
	{
		super.initialize(subject, callbackHandler, sharedState, options);
		roleTothom = (String) options.get("roleTothom");		
		timeoutTicket = Long.parseLong((String) options.get("timeoutTicket"));
		purgaTicket = Integer.parseInt((String) options.get("purgaTicket"));				
		
		try {
			clientCertHelper =  (ClientCertHelper) Class.forName((String) options.get("clientCertHelper")).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("No se puede crear ClientCertHelper: " + e.getMessage(), e);
		}
	}
	
	 
	/**
	 * Login 
	 */
	 public boolean login() throws LoginException
	 {
		 // Comprobamos si esta habilitado UseFirstPass
		 if (getUseFirstPass() == true){
			 return super.login();		         
		 } else {
			 return loginCertificate();
		 }
	 }
	 
	 
	 
	 public boolean loginCertificate() throws LoginException{
		 log.debug("enter: login()");
	     super.loginOk = false;
	     
	     // Obtenemos usuario y password
	     String[] userPass = this.getUsernameAndPassword();
	     String username = userPass[0]; 	// Usuario: {TICKET-sessionId}	     
	     String ticketClave = userPass[1]; 	// Password: ticket
	     if (username == null || !username.startsWith("{TICKET-")) {
	     		 return false;
	     }
	     
	     // Obtenemos sesion id
	     String sesionId = username.substring("{TICKET-".length(), username.length() - 1);
	     
	    // Creamos principal	    
	     try {
			caller = obtenerInfoSesionAutenticacion(sesionId, ticketClave);
		} catch (Exception e) {
			log.error("Error creando Principal a partir ticket",e);
			throw new LoginException("Error creando Principal a partir ticket");			
		}
	     
	     // Establecemos shared state 
	     if (getUseFirstPass() == true)
	      {
	         // Add authentication info to shared state map
	         sharedState.put("javax.security.auth.login.name", caller.getName());
	         sharedState.put("javax.security.auth.login.password", ticketClave);	         
	      }
	     
	     
	     // Damos login por realizado
	      super.loginOk = true;
	      log.debug("Login OK para " + caller.getName());
	      return true;
	      
	 }	

	protected Principal getIdentity()
	   {
	      Principal identity = caller;
	      if( identity == null )
	         identity = super.getIdentity();
	      return identity;
	   }


	/**
	 * No utilizada se sobreescribe login
	 */
	protected String getUsersPassword() throws LoginException {
		return null;
	}

	
	/**
	 * Obtiene roles usuario (modificado para que no llame a createIdentity al crear cada role)
	 */
	 protected Group[] getRoleSets() throws LoginException
	   {
	 	  Principal principal = getIdentity ();
	      
	     String customizedPrincipalClassName = this.clientCertHelper.obtenerPrincipalClass().getName();
		 if ( ! (principal.getClass().getName().equals(customizedPrincipalClassName) ) ){
	      	if (log.isTraceEnabled()) log.trace("Principal "+principal+" not a " + customizedPrincipalClassName);
	      	return new Group[0];
	      }
	      
	      String username = getUsername();
	      
	      List roles = null;
	      try {
				roles = getUserRoles(username);	    	  	
		  } catch (Exception e) {			
			log.error("Excepcion obteniendo roles",e);  
			throw new LoginException("Excepcion obteniendo roles");
		  }
	      
		  Group rolesGroup = new SimpleGroup("Roles");            
	      for (Iterator iterator = roles.iterator();iterator.hasNext();){
	        	String roleName = (String) iterator.next();	        		       
	        	rolesGroup.addMember(new SimplePrincipal(roleName));
	      }
	      HashMap setsMap = new HashMap();
	      setsMap.put("Roles", rolesGroup);            

		  // Montamos grupo "CallerPrincipal"
			Group principalGroup = new SimpleGroup("CallerPrincipal");
			principalGroup.addMember(principal);
			setsMap.put("CallerPrincipal", principalGroup);
	        
	        // Devolvemos respuesta
          Group roleSets[] = new Group[setsMap.size()];
          setsMap.values().toArray(roleSets);
          return roleSets;
	   }


	/**
	 * Obtiene roles asociados al usuario. En este caso serán accesos por ciudadanos que tendrán el role tothom
	 * @param username
	 * @return
	 */ 
	private List getUserRoles(String username) {
		List userRoles = new ArrayList();		
		userRoles.add(roleTothom);
		return userRoles;
	}
	

	private Principal obtenerInfoSesionAutenticacion(String sesionIdTicket, String ticket)  throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		  
		  conn = getConnection();
		  
		  // Purgamos sesiones caducadas          
          Calendar calendar = Calendar.getInstance();
  		  calendar.setTime(new Date());
  		  calendar.add(Calendar.MINUTE, (purgaTicket * -1));
  		  ps = conn.prepareStatement("DELETE FROM ZPE_TCKCRT WHERE (TKC_TICKET is not null AND TKC_FCULT is not null AND TKC_FCULT < ?) OR (TKC_TICKET is null AND TKC_FCSES < ?)");
  		  ps.setTimestamp(1, new java.sql.Timestamp(calendar.getTimeInMillis()));
  		  ps.setTimestamp(2, new java.sql.Timestamp(calendar.getTimeInMillis())); 
		  
		  
          ps = conn.prepareStatement("SELECT TKC_FCALTA, TKC_NIF, TKC_NOMAPE, TKC_FCULT FROM ZPE_TCKCRT WHERE TKC_TICKET = ?");
          ps.setString(1, ticket);         
          rs = ps.executeQuery();	 
          
          if (!rs.next()) {
        	  throw new LoginException("No se encuentra informacion de sesion");
          }
	               
          
    	Date fechaTicket = rs.getTimestamp("TKC_FCALTA");
        String nif = rs.getString("TKC_NIF");
    	String nombreCompleto = rs.getString("TKC_NOMAPE");    	    	
    	Date fechaUltimoLogin = rs.getDate("TKC_FCULT");    	  
    	
    	// Controlar Timeout (si es el primer login)
        if (fechaUltimoLogin == null) {
        	 boolean timeout = (System.currentTimeMillis() - fechaTicket.getTime() > ( timeoutTicket * 1000L)); 
        	 if (timeout) {
        		 throw new LoginException("El ticket ha caducado");
        	 }
        }
    	
    	// Marcamos como que se ha realizado login
    	ps = conn.prepareStatement("UPDATE ZPE_TCKCRT SET TKC_FCULT = ? WHERE TKC_TICKET = ?");
    	ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis())); 
    	ps.setString(2, ticket); 
    	ps.execute();
    	       	
    	return clientCertHelper.crearPrincipal(nif, nombreCompleto);
    	
    	
	                  
       }finally
       {
    	  if( rs != null )  try{rs.close();}catch(SQLException e){}
    	  if( ps != null )  try{ps.close();}catch(SQLException e){}
          if( conn != null ) try { conn.close();} catch (SQLException ex){}	          
        }
	}
	
	private static Connection getConnection() throws Exception {
		Connection conn;
		InitialContext ctx = new InitialContext();
		
		// Obtenemos datasource de login.properties
		InputStream fisModul=null; 
		Properties propiedades = new Properties();
        try {
	       	 // Path directorio de configuracion
	       	 String pathConf = System.getProperty("ad.path.properties");
	       	 // Propiedades modulo
	   		 fisModul = new FileInputStream(pathConf + "sistra/plugins/plugin-login-clientcert.properties");
	   		 propiedades.load(fisModul);       	 	    		 
        } catch (Exception e) {
        	propiedades = null;
            throw new Exception("Excepcion accediendo a las propiedadades del modulo", e);
        } finally {            
            try{if (fisModul != null){fisModul.close();}}catch(Exception ex){}
        }		
		DataSource ds = (DataSource) ctx.lookup(propiedades.getProperty("login.clientCert.datasource"));
		conn = ds.getConnection();
		return conn;
	}		
	
	protected Principal createIdentity(String username)  throws Exception {
		return super.createIdentity(username);
	}
	
	/**
	 * Obtiene url destino de sesion de clave basada en ticket.
	 * @param ticket Ticket
	 * @return Url destino
	 * @throws Exception
	 */
	public static String obtenerUrlDestinoSesionAutenticacion(String ticket) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
	          conn = getConnection();	  
	        
	          ps = conn.prepareStatement("SELECT TKC_URLDST FROM ZPE_TCKCRT WHERE TKC_TICKET = ?");
	          ps.setString(1, ticket);         
	          rs = ps.executeQuery();	 	          
	          if (!rs.next()) {
	        	  throw new Exception("No se encuentra informacion de sesion");
	          }
	          String urlDestino = rs.getString("TKC_URLDST");
	          return urlDestino;	          
       }finally
       {
    	  if( rs != null )  try{rs.close();}catch(SQLException e){}
    	  if( ps != null )  try{ps.close();}catch(SQLException e){}
          if( conn != null ) try { conn.close();} catch (SQLException ex){}	          
        }
	}
	
}
