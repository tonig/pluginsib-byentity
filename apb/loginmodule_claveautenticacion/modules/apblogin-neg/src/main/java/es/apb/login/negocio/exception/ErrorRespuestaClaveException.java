package es.apb.login.negocio.exception;

/**
 * 
 * Excepcion al interpretar respuesta Clave.
 * 
 * @author rsanz
 * 
 */
@SuppressWarnings("serial")
public final class ErrorRespuestaClaveException extends
		ServiceRollbackException {

	/**
	 * Constructor GenerarPeticionClaveException.
	 * 
	 * @param cause
	 *            Causa
	 */
	public ErrorRespuestaClaveException(final Throwable cause) {
		super("Error al interpretar respuesta Clave: " + cause.getMessage(),
				cause);
	}

	/**
	 * Constructor GenerarPeticionClaveException.
	 * 
	 * @param cause
	 *            Causa
	 */
	public ErrorRespuestaClaveException(final String cause) {
		super("Error al interpretar respuesta Clave: " + cause);
	}

}
