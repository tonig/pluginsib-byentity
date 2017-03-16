package es.apb.login.ws.v1.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Peticion iniciar sesion.
 * 
 * @author indra
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class PeticionIniciarSesion {

    /**
     * Url Callback login.
     */
    @XmlElement(required = true)
    private String urlCallbackLogin;

    /**
     * Metodos autenticacion clave (separados por ;).
     */
    @XmlElement(required = true)
    private String metodos;

    /**
     * Idioma.
     */
    @XmlElement(required = true)
    private String idioma;

    /**
     * Gets the url callback login.
     * 
     * @return the url callback login
     */
    public String getUrlCallbackLogin() {
        return urlCallbackLogin;
    }

    /**
     * Sets the url callback login.
     * 
     * @param pUrlCallbackLogin
     *            the new url callback login
     */
    public void setUrlCallbackLogin(final String pUrlCallbackLogin) {
        urlCallbackLogin = pUrlCallbackLogin;
    }

    /**
     * Gets the metodos.
     * 
     * @return the metodos
     */
    public String getMetodos() {
        return metodos;
    }

    /**
     * Sets the metodos.
     * 
     * @param pMetodos
     *            the new metodos
     */
    public void setMetodos(final String pMetodos) {
        metodos = pMetodos;
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
     *            the new idioma
     */
    public void setIdioma(final String pIdioma) {
        idioma = pIdioma;
    }

}
