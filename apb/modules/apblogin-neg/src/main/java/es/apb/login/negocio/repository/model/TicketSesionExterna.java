package es.apb.login.negocio.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Ticket sesion Clave para aplicacion externa.
 * 
 * @author indra
 * 
 */
@Entity
@Table(name = "ZPE_TICKEX")
public final class TicketSesionExterna {

    /** Id secuencial. */
    @Id
    @Column(name = "TCX_CODIGO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ZPE_SEQTCK")
    @SequenceGenerator(name = "ZPE_SEQTCK", sequenceName = "ZPE_SEQTCK")
    private Long id;

    /** Fecha inicio sesion. **/
    @Column(name = "TCX_FCSES")
    private Date fechaInicioSesion;

    /** Idioma. */
    @Column(name = "TCX_IDIOMA")
    private String idioma;

    /** Url callback. */
    @Column(name = "TCX_URLCBK")
    private String urlCallback;

    /** IDPs (separados por ;). */
    @Column(name = "TCX_IDPS")
    private String idps;

    /** Ticket sesion. **/
    @Column(name = "TCX_TICKET")
    private String ticket;

    /** Fecha ticket. **/
    @Column(name = "TCX_FCALTA")
    private Date fechaTicket;

    /** Nivel autenticacion (C/U). **/
    @Column(name = "TCX_NIVAUT")
    private String idp;

    /** Nif usuario autenticado. */
    @Column(name = "TCX_NIF")
    private String nif;

    /** Nombre usuario autenticado. */
    @Column(name = "TCX_NOM")
    private String nombre;

    /** Nombre usuario autenticado. */
    @Column(name = "TCX_APE")
    private String apellidos;

    /** Nif representante. */
    @Column(name = "TCX_NIFRPT")
    private String representanteNif;

    /** Nombre representante. */
    @Column(name = "TCX_NOMRPT")
    private String representanteNombre;

    /** Apellido 1 representante. */
    @Column(name = "TCX_AP1RPT")
    private String representanteApellido1;

    /** Apellido 2 representante. */
    @Column(name = "TCX_AP2RPT")
    private String representanteApellido2;

    /**
     * Gets the ticket.
     * 
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Sets the ticket.
     * 
     * @param pTicket
     *            the ticket to set
     */
    public void setTicket(final String pTicket) {
        ticket = pTicket;
    }

    /**
     * Gets the fecha ticket.
     * 
     * @return the fecha
     */
    public Date getFechaTicket() {
        return fechaTicket;
    }

    /**
     * Sets the fecha ticket.
     * 
     * @param pFecha
     *            the fecha to set
     */
    public void setFechaTicket(final Date pFecha) {
        fechaTicket = pFecha;
    }

    /**
     * Gets the nivel autenticacion.
     * 
     * @return the nivelAutenticacion
     */
    public String getIdp() {
        return idp;
    }

    /**
     * Sets the nivel autenticacion.
     * 
     * @param pNivelAutenticacion
     *            the nivelAutenticacion to set
     */
    public void setIdp(final String pNivelAutenticacion) {
        idp = pNivelAutenticacion;
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
     *            the nif to set
     */
    public void setNif(final String pNif) {
        nif = pNif;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param pId
     *            the id to set
     */
    public void setId(final Long pId) {
        id = pId;
    }

    /**
     * Gets the fecha inicio sesion.
     * 
     * @return the fechaInicioSesion
     */
    public Date getFechaInicioSesion() {
        return fechaInicioSesion;
    }

    /**
     * Sets the fecha inicio sesion.
     * 
     * @param pFechaInicioSesion
     *            the fechaInicioSesion to set
     */
    public void setFechaInicioSesion(final Date pFechaInicioSesion) {
        fechaInicioSesion = pFechaInicioSesion;
    }

    /**
     * Gets the url callback.
     * 
     * @return the urlCallback
     */
    public String getUrlCallback() {
        return urlCallback;
    }

    /**
     * Sets the url callback.
     * 
     * @param pUrlCallback
     *            the urlCallback to set
     */
    public void setUrlCallback(final String pUrlCallback) {
        urlCallback = pUrlCallback;
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
     *            the new idps
     */
    public void setIdps(final String pIdps) {
        idps = pIdps;
    }

    /**
     * @return the representanteNif
     */
    public String getRepresentanteNif() {
        return representanteNif;
    }

    /**
     * @param pRepresentanteNif
     *            the representanteNif to set
     */
    public void setRepresentanteNif(final String pRepresentanteNif) {
        representanteNif = pRepresentanteNif;
    }

    /**
     * @return the representanteNombre
     */
    public String getRepresentanteNombre() {
        return representanteNombre;
    }

    /**
     * @param pRepresentanteNombre
     *            the representanteNombre to set
     */
    public void setRepresentanteNombre(final String pRepresentanteNombre) {
        representanteNombre = pRepresentanteNombre;
    }

    /**
     * @return the representanteApellido1
     */
    public String getRepresentanteApellido1() {
        return representanteApellido1;
    }

    /**
     * @param pRepresentanteApellido1
     *            the representanteApellido1 to set
     */
    public void setRepresentanteApellido1(final String pRepresentanteApellido1) {
        representanteApellido1 = pRepresentanteApellido1;
    }

    /**
     * @return the representanteApellido2
     */
    public String getRepresentanteApellido2() {
        return representanteApellido2;
    }

    /**
     * @param pRepresentanteApellido2
     *            the representanteApellido2 to set
     */
    public void setRepresentanteApellido2(final String pRepresentanteApellido2) {
        representanteApellido2 = pRepresentanteApellido2;
    }

}
