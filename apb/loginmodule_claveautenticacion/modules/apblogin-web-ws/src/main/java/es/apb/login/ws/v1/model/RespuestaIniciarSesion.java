package es.apb.login.ws.v1.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Respuesta datos ticket.
 * 
 * @author indra
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class RespuestaIniciarSesion {

    /**
     * Url para redireccionar.
     * 
     */
    @XmlElement(required = true)
    private String urlRedireccion;

    /**
     * Gets the url redireccion.
     * 
     * @return the url redireccion
     */
    public String getUrlRedireccion() {
        return urlRedireccion;
    }

    /**
     * Sets the url redireccion.
     * 
     * @param pUrlRedireccion
     *            the new url redireccion
     */
    public void setUrlRedireccion(final String pUrlRedireccion) {
        urlRedireccion = pUrlRedireccion;
    }

}
