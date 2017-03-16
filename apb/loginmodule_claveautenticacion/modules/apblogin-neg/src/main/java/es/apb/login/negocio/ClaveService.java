package es.apb.login.negocio;

import es.apb.login.negocio.model.login.DatosUsuario;
import es.apb.login.negocio.model.login.PeticionClave;
import es.apb.login.negocio.model.login.TicketClave;

/**
 * Acceso a funcionalidades Clave.
 * 
 * @author Indra
 * 
 */
public interface ClaveService {

    /**
     * Crea sesion para clave.
     * 
     * @param urlCallback
     *            Url callback
     * @param urlDestino
     *            Url destino
     * @param idioma
     *            idioma
     * @param idps
     *            Idps
     * @param sistra
     *            indica si es Sistra, si no es aplicación externa
     * @return Id sesion
     */
    String crearSesionClave(final String urlCallback, final String urlDestino,
            final String idioma, final String idps, final boolean sistra);

    /**
     * Genera petición de autenticación para Clave.
     * 
     * @param idSesion
     *            id sesion
     * @param sistra
     *            indica si es Sistra, si no es aplicación externa
     * @return Petición Clave
     */
    PeticionClave generarPeticionClave(final String idSesion,
            final boolean sistra);

    /**
     * Procesa peticion clave, extrae los datos de autenticacion, los almacena
     * en bbdd y devuelve ticket de autenticacion.
     * 
     * @param samlResponseB64
     *            respuesta clave
     * @param idSesion
     *            idSesion
     * @param sistra
     *            indica si es Sistra, si no es aplicación externa
     * @return ticket de acceso
     */
    TicketClave procesarRespuestaClave(final String idSesion,
            final String samlResponseB64, final boolean sistra);

    /**
     * Obtiene Idps a partir metodos autenticacion.
     * 
     * @param metodos
     *            metodos autenticacion (C/U)
     * @return idps
     */
    String obtenerIdpsSistra(final String metodos);

    /**
     * Indica si el acceso a clave esta deshabilitado.
     * 
     * @return true si esta deshabilitado
     */
    boolean isAccesoClaveDeshabilitado();

    /**
     * Indica si el acceso a clave esta simulado.
     * 
     * @return true si esta simulado
     */
    boolean isAccesoClaveSimulado();

    /**
     * Obtiene datos usuario para aplicacion externa.
     * 
     * @param ticket
     *            Ticket
     * @return Datos usuario
     */
    DatosUsuario obtenerDatosUsuarioAplicacionExterna(String ticket);

    /**
     * Realiza purga de las claves.
     */
    void purgar();

    /**
     * Obtener url inicio sesion clave para aplicacion externas.
     * 
     * @param idSesion
     *            Id sesion
     * @return url
     */
    String obtenerUrlInicioExterna(String idSesion);

    /**
     * Simula respuesta Clave.
     * 
     * @param pIdSesion
     *            id sesion
     * @param pIdp
     *            idp
     * @param pNif
     *            nif
     * @param pNombre
     *            nombre
     * @param pApellidos
     *            apellidos
     * @param pSistra
     *            si es sistra
     * @return Ticket retorno
     */
    TicketClave simularRespuestaClave(String pIdSesion, String pIdp,
            String pNif, String pNombre, String pApellidos, boolean pSistra);

}
