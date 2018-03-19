package es.apb.login.negocio.model.login;

import java.util.Date;

/**
 * Datos ticket.
 * 
 * @author rsanz
 * 
 */
public final class TicketDatos {

    /** Fecha ticket. */
    private Date fecha;

    /** Datos usuario. */
    private DatosUsuario usuario;

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param pFecha
     *            the fecha to set
     */
    public void setFecha(final Date pFecha) {
        fecha = pFecha;
    }

    /**
     * @return the usuario
     */
    public DatosUsuario getUsuario() {
        return usuario;
    }

    /**
     * @param pUsuario
     *            the usuario to set
     */
    public void setUsuario(final DatosUsuario pUsuario) {
        usuario = pUsuario;
    }

}
