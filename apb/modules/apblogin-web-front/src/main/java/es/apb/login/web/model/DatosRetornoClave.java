package es.apb.login.web.model;

/**
 * Datos para retornar de clave.
 * 
 * @author rsanz
 * 
 */
public final class DatosRetornoClave {

    /** Url callback login. */
    private String urlCallbackLogin;

    /** Ticket. */
    private String ticket;

    /** Idioma. */
    private String idioma;

    /**
     * @return the urlCallbackLogin
     */
    public String getUrlCallbackLogin() {
        return urlCallbackLogin;
    }

    /**
     * @param pUrlCallbackLogin
     *            the urlCallbackLogin to set
     */
    public void setUrlCallbackLogin(final String pUrlCallbackLogin) {
        urlCallbackLogin = pUrlCallbackLogin;
    }

    /**
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * @param pTicket
     *            the ticket to set
     */
    public void setTicket(final String pTicket) {
        ticket = pTicket;
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
