package es.apb.login.ws.v1.services;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.apb.login.negocio.ClaveService;
import es.apb.login.negocio.model.login.DatosUsuario;
import es.apb.login.ws.exception.WException;
import es.apb.login.ws.interceptor.WebserviceExceptionInterceptor;
import es.apb.login.ws.v1.model.PeticionIniciarSesion;
import es.apb.login.ws.v1.model.PeticionTicket;
import es.apb.login.ws.v1.model.Representante;
import es.apb.login.ws.v1.model.RespuestaIniciarSesion;
import es.apb.login.ws.v1.model.RespuestaTicket;

/**
 * 
 * Servicio web que permite obtener los datos autenticacion de clave a partir
 * ticket.
 * 
 * @author Indra
 * 
 */
@Component("loginWebServiceCtrl")
public final class LoginWebServiceCtrl implements LoginWebService {

    /** Log. */
    private final Logger LOGGER = LoggerFactory
            .getLogger(LoginWebServiceCtrl.class);

    /** Servicio Clave. */
    @Resource(name = "claveService")
    private ClaveService claveService;

    @Override
    @WebserviceExceptionInterceptor
    public RespuestaTicket obtenerDatosTicket(final PeticionTicket peticion)
            throws WException {
        LOGGER.debug("Obtener datos ticket: " + peticion.getTicket());

        final DatosUsuario du = claveService
                .obtenerDatosUsuarioAplicacionExterna(peticion.getTicket());

        final RespuestaTicket respuesta = new RespuestaTicket();
        respuesta.setNivelAutenticacion(du.getNivelAutenticacion());
        respuesta.setNif(du.getNif());
        respuesta.setNombre(du.getNombre());
        respuesta.setApellidos(du.getApellidos());
        if (du.getRepresentante() != null) {
            final Representante r = new Representante();
            r.setNif(du.getRepresentante().getNif());
            r.setNombre(du.getRepresentante().getNombre());
            r.setApellido1(du.getRepresentante().getApellido1());
            r.setApellido2(du.getRepresentante().getApellido2());
            respuesta.setRepresentante(r);
        }
        return respuesta;
    }

    @Override
    @WebserviceExceptionInterceptor
    public RespuestaIniciarSesion iniciarSesion(
            final PeticionIniciarSesion pPeticion) throws WException {
        LOGGER.debug("Iniciar sesion clave");
        // Creamos sesion
        final String idSesion = claveService.crearSesionClave(
                pPeticion.getUrlCallbackLogin(), null, pPeticion.getIdioma(),
                pPeticion.getMetodos(), false);
        // Obtenemos url redireccion inicio sesion
        final String url = claveService.obtenerUrlInicioExterna(idSesion);
        // Devolvemos url para iniciar sesion en clave
        final RespuestaIniciarSesion respuesta = new RespuestaIniciarSesion();
        respuesta.setUrlRedireccion(url);
        return respuesta;
    }

}
