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
public final class RespuestaTicket {

    /**
     * Nif.
     * 
     */
    @XmlElement(required = true)
    private String nivelAutenticacion;

    /**
     * Nif.
     * 
     */
    @XmlElement(required = true)
    private String nif;

    /**
     * Nombre.
     * 
     */
    @XmlElement(required = true)
    private String nombre;

    /**
     * Apellidos.
     * 
     */
    @XmlElement(required = false)
    private String apellidos;

    /**
     * Representante.
     * 
     */
    @XmlElement(required = false)
    private Representante representante;

    /**
     * Gets the nivel autenticacion.
     * 
     * @return the nivel autenticacion
     */
    public String getNivelAutenticacion() {
        return nivelAutenticacion;
    }

    /**
     * Sets the nivel autenticacion.
     * 
     * @param pNivelAutenticacion
     *            the new nivel autenticacion
     */
    public void setNivelAutenticacion(final String pNivelAutenticacion) {
        nivelAutenticacion = pNivelAutenticacion;
    }

    /**
     * Gets the nif.
     * 
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * Sets the nif.
     * 
     * @param pNif
     *            the new nif
     */
    public void setNif(final String pNif) {
        nif = pNif;
    }

    /**
     * Gets the nombre.
     * 
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     * 
     * @param pNombre
     *            the new nombre
     */
    public void setNombre(final String pNombre) {
        nombre = pNombre;
    }

    /**
     * Gets the apellidos.
     * 
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Sets the apellidos.
     * 
     * @param pApellidos
     *            the new apellidos
     */
    public void setApellidos(final String pApellidos) {
        apellidos = pApellidos;
    }

    /**
     * @return the representante
     */
    public Representante getRepresentante() {
        return representante;
    }

    /**
     * @param pRepresentante
     *            the representante to set
     */
    public void setRepresentante(final Representante pRepresentante) {
        representante = pRepresentante;
    }

}
