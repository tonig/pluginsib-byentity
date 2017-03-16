package es.apb.login.negocio;

/**
 * 
 * Acceso a propiedades aplicacion.
 * 
 * @author Indra
 * 
 */
public final class ModuleConfig {

    /** Qaa. */
    private String qaa;

    /** spId. */
    private String spId;

    /** providerName. */
    private String providerName;

    /** spSector. */
    private String spSector;

    /** spApplication. */

    private String spApplication;

    /** pepsUrl. */
    private String pepsUrl;

    /** returnUrl. */
    private String returnUrlSistra;

    /** returnUrl para externa. */
    private String returnUrlExterna;

    /** Timeout ticket para aplicaciones externas. */
    private Long timeoutTicketExterna;

    /** Timeout sesion para aplicaciones externas. */
    private Long timeoutSesionExterna;

    /** Deshabilitado. */
    private boolean accesoClaveDeshabilitado;

    /** Inicio sesion clave para aplicaciones externas. */
    private String inicioUrlExterna;

    /** Si se simula proceso de login. */
    private boolean accesoClaveSimulado;

    /**
     * Gets the qaa.
     * 
     * @return the qaa
     */
    public String getQaa() {
        return qaa;
    }

    /**
     * Sets the qaa.
     * 
     * @param pQaa
     *            the qaa to set
     */
    public void setQaa(final String pQaa) {
        qaa = pQaa;
    }

    /**
     * Gets the sp id.
     * 
     * @return the spId
     */
    public String getSpId() {
        return spId;
    }

    /**
     * Sets the sp id.
     * 
     * @param pSpId
     *            the spId to set
     */
    public void setSpId(final String pSpId) {
        spId = pSpId;
    }

    /**
     * Gets the provider name.
     * 
     * @return the providerName
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Sets the provider name.
     * 
     * @param pProviderName
     *            the providerName to set
     */
    public void setProviderName(final String pProviderName) {
        providerName = pProviderName;
    }

    /**
     * Gets the sp sector.
     * 
     * @return the spSector
     */
    public String getSpSector() {
        return spSector;
    }

    /**
     * Sets the sp sector.
     * 
     * @param pSpSector
     *            the spSector to set
     */
    public void setSpSector(final String pSpSector) {
        spSector = pSpSector;
    }

    /**
     * Gets the sp application.
     * 
     * @return the spApplication
     */
    public String getSpApplication() {
        return spApplication;
    }

    /**
     * Sets the sp application.
     * 
     * @param pSpApplication
     *            the spApplication to set
     */
    public void setSpApplication(final String pSpApplication) {
        spApplication = pSpApplication;
    }

    /**
     * Gets the peps url.
     * 
     * @return the pepsUrl
     */
    public String getPepsUrl() {
        return pepsUrl;
    }

    /**
     * Sets the peps url.
     * 
     * @param pPepsUrl
     *            the pepsUrl to set
     */
    public void setPepsUrl(final String pPepsUrl) {
        pepsUrl = pPepsUrl;
    }

    /**
     * Gets the return url sistra.
     * 
     * @return the returnUrl
     */
    public String getReturnUrlSistra() {
        return returnUrlSistra;
    }

    /**
     * Sets the return url sistra.
     * 
     * @param pReturnUrl
     *            the returnUrl to set
     */
    public void setReturnUrlSistra(final String pReturnUrl) {
        returnUrlSistra = pReturnUrl;
    }

    /**
     * Checks if is acceso clave deshabilitado.
     * 
     * @return the accesoClaveDeshabilitado
     */
    public boolean isAccesoClaveDeshabilitado() {
        return accesoClaveDeshabilitado;
    }

    /**
     * Sets the acceso clave deshabilitado.
     * 
     * @param pAccesoClaveDeshabilitado
     *            the accesoClaveDeshabilitado to set
     */
    public void setAccesoClaveDeshabilitado(
            final boolean pAccesoClaveDeshabilitado) {
        accesoClaveDeshabilitado = pAccesoClaveDeshabilitado;
    }

    /**
     * Gets the return url externa.
     * 
     * @return the return url externa
     */
    public String getReturnUrlExterna() {
        return returnUrlExterna;
    }

    /**
     * Sets the return url externa.
     * 
     * @param pReturnUrlExterna
     *            the new return url externa
     */
    public void setReturnUrlExterna(final String pReturnUrlExterna) {
        returnUrlExterna = pReturnUrlExterna;
    }

    /**
     * Gets the timeout ticket externa.
     * 
     * @return the timeout ticket externa
     */
    public Long getTimeoutTicketExterna() {
        return timeoutTicketExterna;
    }

    /**
     * Sets the timeout ticket externa.
     * 
     * @param pTimeoutTicketExterna
     *            the new timeout ticket externa
     */
    public void setTimeoutTicketExterna(final Long pTimeoutTicketExterna) {
        timeoutTicketExterna = pTimeoutTicketExterna;
    }

    /**
     * Gets the timeout sesion externa.
     * 
     * @return the timeout sesion externa
     */
    public Long getTimeoutSesionExterna() {
        return timeoutSesionExterna;
    }

    /**
     * Sets the timeout sesion externa.
     * 
     * @param pTimeoutSesionExterna
     *            the new timeout sesion externa
     */
    public void setTimeoutSesionExterna(final Long pTimeoutSesionExterna) {
        timeoutSesionExterna = pTimeoutSesionExterna;
    }

    /**
     * Gets the inicio url externa.
     * 
     * @return the inicioUrlExterna
     */
    public String getInicioUrlExterna() {
        return inicioUrlExterna;
    }

    /**
     * Sets the inicio url externa.
     * 
     * @param pInicioUrlExterna
     *            the inicioUrlExterna to set
     */
    public void setInicioUrlExterna(final String pInicioUrlExterna) {
        inicioUrlExterna = pInicioUrlExterna;
    }

    /**
     * @return the simularClave
     */
    public boolean isAccesoClaveSimulado() {
        return accesoClaveSimulado;
    }

    /**
     * @param pSimularClave
     *            the simularClave to set
     */
    public void setAccesoClaveSimulado(final boolean pSimularClave) {
        accesoClaveSimulado = pSimularClave;
    }

}
