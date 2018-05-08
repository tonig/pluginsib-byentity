package es.apb.login.ws.v1.services;

import javax.annotation.Resource;
import javax.jws.WebService;

import es.apb.login.ws.exception.WException;
import es.apb.login.ws.v1.model.PeticionIniciarSesion;
import es.apb.login.ws.v1.model.PeticionTicket;
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
@WebService(serviceName = "LoginService_v1_00", endpointInterface = "es.apb.login.ws.v1.services.LoginWebService", targetNamespace = "urn:es:apb:login:ws:v1:login")
public final class LoginWebServiceImpl implements LoginWebService {

    /**
     * Controlador webservice.
     */
    @Resource(name = "loginWebServiceCtrl")
    private LoginWebService loginWebServiceCtrl;

    @Override
    public RespuestaTicket obtenerDatosTicket(final PeticionTicket peticion)
            throws WException {
        return loginWebServiceCtrl.obtenerDatosTicket(peticion);
    }

    @Override
    public RespuestaIniciarSesion iniciarSesion(
            final PeticionIniciarSesion pPeticion) throws WException {
        return loginWebServiceCtrl.iniciarSesion(pPeticion);
    }

}
