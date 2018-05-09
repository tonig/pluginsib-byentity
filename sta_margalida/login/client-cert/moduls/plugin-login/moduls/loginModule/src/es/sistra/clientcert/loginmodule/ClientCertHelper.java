package es.sistra.clientcert.loginmodule;

import java.security.Principal;

/** 
 * 	Interfaz para implementar la construccion personalizada de 
 *  un principal para el modulo ClientCert. 
 * 	@author rsanz
 *
 */
public interface ClientCertHelper {

	/**
	 * Crea princpipal a partir certificado.
	 * @param certificado Certificado
	 * @return Principal
	 */
	public Principal crearPrincipal(String nif, String nombre);
	
	/**
	 * Devuelve clase personalizada de principal.
	 * @return clase personalizada de principal.
	 */
	public Class obtenerPrincipalClass();
}
