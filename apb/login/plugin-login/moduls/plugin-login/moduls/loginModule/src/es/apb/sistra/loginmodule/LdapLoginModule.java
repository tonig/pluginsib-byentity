package es.apb.sistra.loginmodule;

import java.lang.reflect.Constructor;
import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;



/**
 *  Login mediante LDAP.
 */
public class LdapLoginModule extends UsernamePasswordLoginModule
{

   /** Role de acceso publico */
   private String roleTothom;
   
   /** Role auto */
   private String roleAuto;
   
   /** Url Ldap */
   private String url;
   
   /** Dominio a añadir a usuario (user@dominio) */
   private String dominio;
   
   /** Base busquedas */
   private String searchBase;  
   
   /**  Principal customizado */
   private ApbPrincipal caller;
   
   /** Roles */
   private transient SimpleGroup userRoles = new SimpleGroup("Roles");
   
   public void initialize(Subject subject, CallbackHandler callbackHandler,Map sharedState, Map options)
	{
		super.initialize(subject, callbackHandler, sharedState, options);
		roleTothom = (String) options.get("roleTothom");
		url = (String) options.get("url");
		searchBase = (String) options.get("searchBase");
		dominio = (String) options.get("dominio");
		roleAuto = (String) options.get("roleAuto");
	}

   	 /** Login */
	 public boolean login() throws LoginException
	 {
		 // Comprobamos si esta habilitado UseFirstPass
		 if (getUseFirstPass() == true){
			 return super.login();		         
		 } else {
			 return loginLDAP();
		 }
	 }
	 
	 
	 public boolean loginLDAP() throws LoginException{
		 log.debug("enter: login()");
	     super.loginOk = false;
	     
	     // Obtenemos usuario y password
	     String[] userPass = this.getUsernameAndPassword();
	     String username = userPass[0]; 	  
	     String password = userPass[1]; 		     	     		      
	     
	    // Creamos principal	    
	     try {
			obtenerInfoSesionAutenticacion(username, password);
		} catch (Exception e) {
			log.error("Error creando ApbPrincipal",e);
			throw new LoginException("Error creando ApbPrincipal");			
		}
	     
	     // Establecemos shared state 
	     if (getUseFirstPass() == true)
	      {
	         // Add authentication info to shared state map
	         sharedState.put("javax.security.auth.login.name", caller.getName());
	         sharedState.put("javax.security.auth.login.password", password);	         
	      }
	     
	     
	     // Damos login por realizado
	      super.loginOk = true;
	      log.debug("Login OK para " + caller.getName());
	      return true;
	      
	 }	
   
   
   
   protected String getUsersPassword() throws LoginException
   {
      return "";
   }
   
	/**
	 * Obtiene roles usuario (modificado para que no llame a createIdentity al
	 * crear cada role)
	 */
	protected Group[] getRoleSets() throws LoginException {
		Principal principal = getIdentity();

		if (!(principal instanceof ApbPrincipal)) {
			if (log.isTraceEnabled())
				log.trace("Principal " + principal + " not a ApbPrincipal");
			return new Group[0];
		}

		HashMap setsMap = new HashMap();
		setsMap.put("Roles", userRoles);

		Group principalGroup = new SimpleGroup("CallerPrincipal");
		principalGroup.addMember(principal);
		setsMap.put("CallerPrincipal", principalGroup);

		// Devolvemos respuesta
		Group roleSets[] = new Group[setsMap.size()];
		setsMap.values().toArray(roleSets);
		return roleSets;
	}
   
   protected Principal getIdentity()
   {
      Principal identity = caller;
      if( identity == null )
         identity = super.getIdentity();
      return identity;
   }
   
   

   private void obtenerInfoSesionAutenticacion(String username, String password) throws NamingException
   {
	    // Usuario unautenticado
		if (username == null || username.equals( (String) options.get("unauthenticatedIdentity"))){
			log.debug("Autenticando usuario anonimo");
			caller = new ApbPrincipal((String) options.get("unauthenticatedIdentity"),"","",'A');
			List<String> roles = new ArrayList<String>();
			roles.add(roleTothom);
			userRoles = calcularRoles(roles);			
		} else {
		// Usuario LDAP
			log.debug("Autenticando usuario a traves LDAP: " + username);
			DirContext ctx = null;
			try {
				NamingEnumeration<SearchResult> answer;
				
				// Usuario es usuario@dominio
				String user = username + "@" + dominio;
				
				Hashtable<String, String> env = new Hashtable<String, String>();
				env.put(Context.INITIAL_CONTEXT_FACTORY,
						"com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.SECURITY_AUTHENTICATION, "simple");		
				env.put(Context.PROVIDER_URL, url);
				env.put(Context.SECURITY_PRINCIPAL, user);
				env.put(Context.SECURITY_CREDENTIALS, password);
				
				// Autentica contra LDAP
				ctx = new InitialDirContext(env); 
			   
				// Obtener info usuario: nombre y roles
				StringBuilder searchFilter = new StringBuilder("(&");
				searchFilter.append("(objectClass=person)");
				searchFilter
						.append("(userPrincipalName=" + user + ")");
				searchFilter.append(")");
		
				String returnAttrs[] = { "memberOf", "cn" };
				SearchControls sCtrl = new SearchControls();
				sCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
				sCtrl.setReturningAttributes(returnAttrs);
		
				answer = ctx.search(searchBase,
						searchFilter.toString(), sCtrl);
				
				List<String> roles = new ArrayList<String>();
				String nombre = null;
				if (answer.hasMoreElements()) {
					SearchResult sr = answer.next();
					if (sr.getAttributes().get("memberOf") != null) {
						String memberOfAttrValue = sr.getAttributes().get("memberOf")
								.toString();
						log.debug("Grupos LDAP: " + memberOfAttrValue);
						roles = extraerRoles(memberOfAttrValue);		
					}			
					if (sr.getAttributes().get("cn") != null) {
						String cn = sr.getAttributes().get("cn").toString();
						log.debug("CN LDAP: " + cn);
						nombre = extraerNombre(cn);
					}
				}
				
				caller = new ApbPrincipal(username,nombre,"",'U');			
				userRoles = calcularRoles(roles);
			} finally {
				if (ctx != null) {
					ctx.close();
				}
			}
		}
	}
   
   private String extraerNombre(String cn) {
		String nombre = "";
		if (cn != null) {
			int posCN = cn.indexOf("cn:");
			if (posCN != -1) {
				nombre = cn.substring(posCN + "cn:".length()).trim();
			}
		}
		return nombre;
	}

	private List<String> extraerRoles(String memberOfAttrValue) {
		List<String> roles = new ArrayList<String>();
		int posIni = 0;
		while (posIni != -1) {
			int posCN = memberOfAttrValue.indexOf("CN=", posIni);
			if (posCN > 0) {
				int posComa = memberOfAttrValue.indexOf(",",posCN);
				String role = memberOfAttrValue.substring(posCN + "CN=".length(), posComa).trim();
				roles.add(role);
				posIni = posComa;			
			} else {
				posIni = -1;
			}
		}
		return roles;
	}
	
	private SimpleGroup calcularRoles(List<String> roles) {
		SimpleGroup rolesGroup = new SimpleGroup("Roles");            
	    for (Iterator iterator = roles.iterator();iterator.hasNext();){
	       	String roleName = (String) iterator.next();	        		       
	     	rolesGroup.addMember(new SimplePrincipal(roleName));
	    }
	    
	    // Añadir role tothom: a todos menos usuario auto
	    if (!roles.contains(roleAuto)) {
	    	rolesGroup.addMember(new SimplePrincipal(roleTothom));
	    }
	    
	    return rolesGroup;
	}
	

	   /** Utility method to create a Principal for the given username. This
	    * creates an instance of the principalClassName type if this option was
	    * specified using the class constructor matching: ctor(String). If
	    * principalClassName was not specified, a SimplePrincipal is created.
	    *
	    * @param username the name of the principal
	    * @return the principal instance
	    * @throws java.lang.Exception thrown if the custom principal type cannot be created.
	    */ 
	   protected Principal createIdentity(String username)
	      throws Exception
	   {
	      Principal p = null;
	      if( principalClassName == null )
	      {
	        // p = new SimplePrincipal(username);
	    	 p = new ApbPrincipal((String) options.get("unauthenticatedIdentity"),"","",'A');
	      }
	      else
	      {
	            ClassLoader loader = Thread.currentThread().getContextClassLoader();
	            Class clazz = loader.loadClass(principalClassName);
	            Class[] ctorSig = {String.class};
	            Constructor ctor = clazz.getConstructor(ctorSig);
	            Object[] ctorArgs = {username};
	            p = (Principal) ctor.newInstance(ctorArgs);
	      }
	      return p;
	   }
	   
	   
	   
	   
}