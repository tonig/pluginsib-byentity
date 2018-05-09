package es.sistra.clientcert.loginmodule;

import java.security.Principal;

import es.caib.mock.loginModule.MockPrincipal;
import es.sistra.clientcert.loginmodule.ClientCertHelper;


/**
 * Creacion de Principal particularizado para mock.
 * @author rsanz
 *
 */
public class ClientCertHelperMock implements ClientCertHelper {	

	public Principal crearPrincipal(String nif, String nombreCompleto) {
		return new MockPrincipal(nif, nombreCompleto, nif, 'C');
	}

	public Class obtenerPrincipalClass() {
		return MockPrincipal.class;
	}
	
}
