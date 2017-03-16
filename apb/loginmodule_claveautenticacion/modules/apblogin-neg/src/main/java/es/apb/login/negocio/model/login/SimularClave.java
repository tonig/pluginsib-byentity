package es.apb.login.negocio.model.login;

/**
 * Peticion simular clave.
 * 
 * @author Indra
 * 
 */
public final class SimularClave {

    /** Indica si es sistra. */
    private boolean sistra;
    /** Id sesion. */
    private String idSesion;

    /**
     * @return the sistra
     */
    public boolean isSistra() {
        return sistra;
    }

    /**
     * @param pSistra
     *            the sistra to set
     */
    public void setSistra(final boolean pSistra) {
        sistra = pSistra;
    }

    /**
     * @return the idSesion
     */
    public String getIdSesion() {
        return idSesion;
    }

    /**
     * @param pIdSesion
     *            the idSesion to set
     */
    public void setIdSesion(final String pIdSesion) {
        idSesion = pIdSesion;
    }

}
