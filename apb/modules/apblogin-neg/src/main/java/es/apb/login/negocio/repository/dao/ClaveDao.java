package es.apb.login.negocio.repository.dao;

import es.apb.login.negocio.model.login.DatosRepresentante;
import es.apb.login.negocio.model.login.DatosSesion;
import es.apb.login.negocio.model.login.TicketClave;
import es.apb.login.negocio.model.login.TicketDatos;

/**
 * Interfaz de acceso a base de datos para crear ticket acceso sesion clave.
 * 
 * @author Indra
 * 
 */
public interface ClaveDao {

    /**
     * Iniciar sesion.
     * 
     * @param urlCallback
     *            url Callback
     * @param urlDestino
     *            url Destino
     * @param idioma
     *            idioma
     * @param idps
     *            idps
     * @param sistra
     *            indica si es Sistra, si no es aplicación externa
     * @return identificador sesion
     */
    String crearSesion(String urlCallback, String urlDestino, String idioma,
            final String idps, boolean sistra);

    /**
     * Obtener datos sesion.
     * 
     * @param idSesion
     *            Id sesion
     * @param sistra
     *            indica si es Sistra, si no es aplicación externa
     * @return Datos sesion
     */
    DatosSesion obtenerDatosSesion(String idSesion, boolean sistra);

    /**
     * Crea ticket.
     * 
     * @param idSesion
     *            id sesion
     * @param idp
     *            idp
     * @param nif
     *            Nif
     * @param nombre
     *            Nombre
     * @param apellidos
     *            Apelllidos
     * @param representante
     *            representante
     * @param sistra
     *            Indica si es Sistra, si no es aplicación externa
     * @return Ticket
     */
    TicketClave generateTicket(String idSesion, String idp, String nif,
            String nombre, String apellidos, DatosRepresentante representante,
            final boolean sistra);

    /**
     * Obtiene ticket aplicacion externa y lo borra.
     * 
     * @param ticket
     *            Ticket
     * @return Datos ticket
     */
    TicketDatos consumirTicketSesionExterna(final String ticket);

    /**
     * Realiza purga tickets aplicaciones externas.
     * 
     * @param timeoutSesion
     *            timeout sesion
     * @param timeoutTicket
     *            timeout ticket
     */
    void purgaTicketSesionExterna(long timeoutSesion, long timeoutTicket);

}
