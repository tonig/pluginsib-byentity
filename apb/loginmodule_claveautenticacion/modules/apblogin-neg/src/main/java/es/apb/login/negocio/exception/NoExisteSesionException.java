package es.apb.login.negocio.exception;

/**
 * 
 * Excepcion cuando no existe sesion.
 * 
 * @author rsanz
 * 
 */
@SuppressWarnings("serial")
public final class NoExisteSesionException extends ServiceRollbackException {

    /**
     * Constructor NoExiseSesionException.
     * 
     */
    public NoExisteSesionException() {
        super("No existe sesion");
    }

}
