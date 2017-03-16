package es.apb.login.web.model;

/**
 * Datos para iniciar sesion en clave.
 * 
 * @author rsanz
 * 
 */
public final class DatosInicioSesionClave {

    /** Url clave. */
    private String urlClave;

    /** Metodos autenticacion permitidos en clave. */
    private String idps;

    /** Peticion SAML de autenticacion. */
    private String samlRequest;

    /** Idioma. */
    private String idioma;

    /**
     * @return the urlClave
     */
    public String getUrlClave() {
        return urlClave;
    }

    /**
     * @param pUrlClave
     *            the urlClave to set
     */
    public void setUrlClave(final String pUrlClave) {
        urlClave = pUrlClave;
    }

    /**
     * @return the idps
     */
    public String getIdps() {
        return idps;
    }

    /**
     * @param pIdps
     *            the idps to set
     */
    public void setIdps(final String pIdps) {
        idps = pIdps;
    }

    /**
     * @return the samlRequest
     */
    public String getSamlRequest() {
        return samlRequest;
    }

    /**
     * @param pSamlRequest
     *            the samlRequest to set
     */
    public void setSamlRequest(final String pSamlRequest) {
        samlRequest = pSamlRequest;
    }

    /**
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param pIdioma
     *            the idioma to set
     */
    public void setIdioma(final String pIdioma) {
        idioma = pIdioma;
    }

}
