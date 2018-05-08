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
 * Ticket sesion Clave.
 * 
 * @author indra
 * 
 */
@Entity
@Table(name = "ZPE_TICKET")
public final class TicketSesion {

    /** Id secuencial. */
    @Id
    @Column(name = "TCK_CODIGO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ZPE_SEQTCK")
    @SequenceGenerator(name = "ZPE_SEQTCK", sequenceName = "ZPE_SEQTCK")
    private Long id;

    /** Fecha inicio sesion. **/
    @Column(name = "TCK_FCSES")
    private Date fechaInicioSesion;

    /** Idioma. */
    @Column(name = "TCK_IDIOMA")
    private String idioma;

    /** IDPS. */
    @Column(name = "TCK_IDPS")
    private String idps;

    /** Url callback. */
    @Column(name = "TCK_URLCBK")
    private String urlCallback;

    /** Url destino. */
    @Column(name = "TCK_URLDST")
    private String urlDestino;

    /** Ticket sesion. **/
    @Column(name = "TCK_TICKET")
    private String ticket;

    /** Fecha ticket. **/
    @Column(name = "TCK_FCALTA")
    private Date fechaTicket;

    /** Nivel autenticacion (C/U). **/
    @Column(name = "TCK_NIVAUT")
    private String nivelAutenticacion;

    /** Nif usuario autenticado. */
    @Column(name = "TCK_NIF")
    private String nif;

    /** Nombre completo usuario autenticado. */
    @Column(name = "TCK_NOMAPE")
    private String nombreApellidos;

    /** Ultimo login. */
    @Column(name = "TCK_FCULT")
    private Date fechaUltimoLogin;

    /** Nif representante. */
    @Column(name = "TCK_NIFRPT")
    private String representanteNif;

    /** Nombre representante. */
    @Column(name = "TCK_NOMRPT")
    private String representanteNombre;

    /** Apellido 1 representante. */
    @Column(name = "TCK_AP1RPT")
    private String representanteApellido1;

    /** Apellido 2 representante. */
    @Column(name = "TCK_AP2RPT")
    private String representanteApellido2;

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
     * @return the fecha
     */
    public Date getFechaTicket() {
        return fechaTicket;
    }

    /**
     * @param pFecha
     *            the fecha to set
     */
    public void setFechaTicket(final Date pFecha) {
        fechaTicket = pFecha;
    }

    /**
     * @return the nivelAutenticacion
     */
    public String getNivelAutenticacion() {
        return nivelAutenticacion;
    }

    /**
     * @param pNivelAutenticacion
     *            the nivelAutenticacion to set
     */
    public void setNivelAutenticacion(final String pNivelAutenticacion) {
        nivelAutenticacion = pNivelAutenticacion;
    }

    /**
     * @return the nif
     */
    public String getNif() {
        return nif;
    }

    /**
     * @param pNif
     *            the nif to set
     */
    public void setNif(final String pNif) {
        nif = pNif;
    }

    /**
     * @return the nombreApellidos
     */
    public String getNombreApellidos() {
        return nombreApellidos;
    }

    /**
     * @param pNombreApellidos
     *            the nombreApellidos to set
     */
    public void setNombreApellidos(final String pNombreApellidos) {
        nombreApellidos = pNombreApellidos;
    }

    /**
     * @return the urlDestino
     */
    public String getUrlDestino() {
        return urlDestino;
    }

    /**
     * @param pUrlDestino
     *            the urlDestino to set
     */
    public void setUrlDestino(final String pUrlDestino) {
        urlDestino = pUrlDestino;
    }

    /**
     * @return the fechaUltimoLogin
     */
    public Date getFechaUltimoLogin() {
        return fechaUltimoLogin;
    }

    /**
     * @param pFechaUltimoLogin
     *            the fechaUltimoLogin to set
     */
    public void setFechaUltimoLogin(final Date pFechaUltimoLogin) {
        fechaUltimoLogin = pFechaUltimoLogin;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param pId
     *            the id to set
     */
    public void setId(final Long pId) {
        id = pId;
    }

    /**
     * @return the fechaInicioSesion
     */
    public Date getFechaInicioSesion() {
        return fechaInicioSesion;
    }

    /**
     * @param pFechaInicioSesion
     *            the fechaInicioSesion to set
     */
    public void setFechaInicioSesion(final Date pFechaInicioSesion) {
        fechaInicioSesion = pFechaInicioSesion;
    }

    /**
     * @return the urlCallback
     */
    public String getUrlCallback() {
        return urlCallback;
    }

    /**
     * @param pUrlCallback
     *            the urlCallback to set
     */
    public void setUrlCallback(final String pUrlCallback) {
        urlCallback = pUrlCallback;
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
