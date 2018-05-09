package es.stm.sistra.loginmodule;

import java.security.Principal;

import es.sistra.clientcert.loginmodule.ClientCertHelper;


/**
 * Creacion de Principal particularizado para Sta. Margalida.
 * @author rsanz
 *
 */
public class ClientCertHelperStm implements ClientCertHelper {	

	public Principal crearPrincipal(String nif, String nombreCompleto) {
		return new StmPrincipal(nif, nombreCompleto, nif, 'C');
	}

	public Class obtenerPrincipalClass() {
		return StmPrincipal.class;
	}
	
}
