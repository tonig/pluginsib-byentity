package es.apb.login.ws.v1.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

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
@WebService(targetNamespace = "urn:es:apb:login:ws:v1:login")
public interface LoginWebService {

    /**
     * Iniciar sesion clave.
     * 
     * @param peticion
     *            Petición inicio sesion.
     * @return Devuelve datos inicio sesion (url para redireccionar).
     * @throws WException
     *             Excepcion lanzada.
     */
    @WebMethod(operationName = "iniciarSesion")
    @RequestWrapper(localName = "iniciarSesionRequest")
    @ResponseWrapper(localName = "iniciarSesionResponse")
    @WebResult(name = "respuesta")
    RespuestaIniciarSesion iniciarSesion(
            @WebParam(name = "peticion") PeticionIniciarSesion peticion)
            throws WException;

    /**
     * Obtener datos ticket.
     * 
     * @param peticion
     *            Petición con el ticket.
     * @return Devuelve los datos del ticket.
     * @throws WException
     *             Excepcion lanzada.
     */
    @WebMethod(operationName = "obtenerDatosTicket")
    @RequestWrapper(localName = "ticketRequest")
    @ResponseWrapper(localName = "ticketResponse")
    @WebResult(name = "respuesta")
    RespuestaTicket obtenerDatosTicket(
            @WebParam(name = "peticion") PeticionTicket peticion)
            throws WException;

}
