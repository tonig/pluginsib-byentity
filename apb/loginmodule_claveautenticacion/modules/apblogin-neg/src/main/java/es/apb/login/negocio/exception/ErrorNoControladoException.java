package es.apb.login.negocio.exception;

 
/**
 * 
 * Capturamos en una excepción de negocio las excepcion runtime no controladas
 * (no heredan de ServicioRollbackException).
 * 
 * @author rsanz
 * 
 */
@SuppressWarnings("serial")
public final class ErrorNoControladoException extends ServiceRollbackException {
  

    /**
     * Constructor ErrorNoControladoException.
     * 
     * @param cause
     *            Causa
     */
    public ErrorNoControladoException(final Throwable cause) {
        super("Error no controlado en la capa de servicio: "
                + cause.getMessage(), cause);
    }

}
