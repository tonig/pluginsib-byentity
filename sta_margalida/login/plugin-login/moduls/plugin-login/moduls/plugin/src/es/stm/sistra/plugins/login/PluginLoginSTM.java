package es.stm.sistra.plugins.login;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.stm.sistra.loginmodule.StmPrincipal;

/**
 * Plugin Login STM.
 * @author rsanz
 *
 */
public class PluginLoginSTM implements PluginLoginIntf {

	/** Log. */
	private static Log log = LogFactory.getLog(PluginLoginSTM.class);
	
	/**
	 * Obtiene metodo de autenticacion
	 */
	public char getMetodoAutenticacion(Principal principal) {
		StmPrincipal mp = (StmPrincipal) principal;
		return mp.getMetodoAutenticacion();		
	}
	
	/**
	 * Obtiene nif
	 */
	public String getNif(Principal principal) {
		StmPrincipal mp = (StmPrincipal) principal;
		return mp.getNif();		
	}

	/**
	 * Obtiene nombre y apellidos
	 */
	public String getNombreCompleto(Principal principal) {
		StmPrincipal mp = (StmPrincipal) principal;
		return mp.getNombreCompleto();		
	}
	
	/**
	 * Obtiene url destino de sesion de clave basada en ticket.
	 * @param ticket Ticket
	 * @return Url destino
	 * @throws Exception
	 */
	public String obtenerUrlDestinoSesionAutenticacion(String ticket) throws Exception {
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
	
	private Connection getConnection() throws Exception {
		Connection conn;
		InitialContext ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup(ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("login.clientCert.datasource"));
		conn = ds.getConnection();
		return conn;
	}		
	
}
