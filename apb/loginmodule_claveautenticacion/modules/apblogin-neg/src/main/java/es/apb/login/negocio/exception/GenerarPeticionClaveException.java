package es.apb.login.negocio.exception;

/**
 * 
 * Excepcion al generar peticion para Clave.
 * 
 * @author rsanz
 * 
 */
@SuppressWarnings("serial")
public final class GenerarPeticionClaveException extends
        ServiceRollbackException {

    /**
     * Constructor GenerarPeticionClaveException.
     * 
     * @param cause
     *            Causa
     */
    public GenerarPeticionClaveException(final Throwable cause) {
        super("Error al generar peticion para Clave: " + cause.getMessage(),
                cause);
    }

    /**
     * Constructor GenerarPeticionClaveException.
     * 
     * @param cause
     *            Causa
     */
    public GenerarPeticionClaveException(final String cause) {
        super("Error al generar peticion para Clave: " + cause);
    }

}
