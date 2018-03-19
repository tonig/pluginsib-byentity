package es.apb.login.negocio.exception;

/**
 * 
 * Excepcion ticket no valido.
 * 
 * @author rsanz
 * 
 */
@SuppressWarnings("serial")
public final class TicketNoValidoException extends ServiceRollbackException {

    /**
     * Constructor TicketNoValidoException.
     * 
     * @param cause
     *            Causa
     */
    public TicketNoValidoException(final Throwable cause) {
        super("Ticket no valido: " + cause.getMessage(), cause);
    }

    /**
     * Constructor TicketNoValidoException.
     * 
     * @param mensaje
     *            mensaje
     */
    public TicketNoValidoException(final String mensaje) {
        super("Ticket no valido: " + mensaje);
    }

}
