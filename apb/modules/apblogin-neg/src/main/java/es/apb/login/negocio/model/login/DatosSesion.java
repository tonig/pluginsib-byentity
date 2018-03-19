package es.apb.login.negocio.model.login;

import java.util.Date;

/**
 * Datos sesion.
 * 
 * @author Indra
 * 
 */
public final class DatosSesion {

    /** Idps. */
    private String idps;

    /** Fecha inicio sesion. */
    private Date fechaInicioSesion;

    /** Fecha ticket (nulo si no se ha iniciado sesion en clave). */
    private Date fechaTicket;

    /** Idioma. */
    private String idioma;

    /**
     * Gets the idps.
     * 
     * @return the idps
     */
    public String getIdps() {
        return idps;
    }

    /**
     * Sets the idps.
     * 
     * @param pIdps
     *            the idps to set
     */
    public void setIdps(final String pIdps) {
        idps = pIdps;
    }

    /**
     * Gets the fecha.
     * 
     * @return the fecha
     */
    public Date getFechaInicioSesion() {
        return fechaInicioSesion;
    }

    /**
     * Sets the fecha.
     * 
     * @param pFecha
     *            the fecha to set
     */
    public void setFechaInicioSesion(final Date pFecha) {
        fechaInicioSesion = pFecha;
    }

    /**
     * Gets the idioma.
     * 
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Sets the idioma.
     * 
     * @param pIdioma
     *            the idioma to set
     */
    public void setIdioma(final String pIdioma) {
        idioma = pIdioma;
    }

    /**
     * Gets the fecha ticket.
     * 
     * @return the fechaTicket
     */
    public Date getFechaTicket() {
        return fechaTicket;
    }

    /**
     * Sets the fecha ticket.
     * 
     * @param pFechaTicket
     *            the fechaTicket to set
     */
    public void setFechaTicket(final Date pFechaTicket) {
        fechaTicket = pFechaTicket;
    }

}
