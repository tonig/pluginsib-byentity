package es.apb.login.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.apb.login.negocio.ClaveService;
import es.apb.login.negocio.exception.ErrorFrontException;
import es.apb.login.negocio.exception.ServiceException;
import es.apb.login.negocio.model.login.ErrorCodes;
import es.apb.login.negocio.model.login.PeticionClave;
import es.apb.login.negocio.model.login.SimularClave;
import es.apb.login.negocio.model.login.TicketClave;
import es.apb.login.negocio.util.ConvertUtil;
import es.apb.login.web.model.DatosInicioSesionClave;
import es.apb.login.web.model.DatosRetornoClave;

/**
 * Inicia sesion en clave.
 * 
 * @author rsanz
 * 
 */
@Controller
public final class SesionClaveController {

    /** Servicio Clave. */
    @Resource(name = "claveService")
    private ClaveService claveService;

    /** Log. */
    private final Logger log = LoggerFactory
            .getLogger(SesionClaveController.class);

    /**
     * Muestra pagina de bienvenida.
     * 
     * @return pagina bienvenida para test
     */
    @RequestMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * Recibe peticion de inicio de sesion en Clave de Sistra y redirige a
     * Clave.
     * 
     * @param urlCallbackLoginB64
     *            Url de Sistra para volver tras autenticar en clave, que
     *            forzara la autenticacion con el ticket y redirigirá a Url
     *            destino.
     * @param urlDestinoB64
     *            Url destino tras autenticar (se almacena en sesión).
     * @param metodos
     *            Metodos de autenticacion (C: certificado / U: usuario).
     * @param idioma
     *            idioma.
     * @return pagina que realiza la redireccion a Clave
     */
    @RequestMapping(value = "/iniciarSesionClave.html", method = RequestMethod.POST)
    public ModelAndView iniciarSesionClaveSistra(
            @RequestParam("urlCallbackLogin") final String urlCallbackLoginB64,
            @RequestParam(value = "urlDestino") final String urlDestinoB64,
            @RequestParam("metodos") final String metodos,
            @RequestParam("idioma") final String idioma) {

        log.debug("Iniciar sesion clave para Sistra");

        // Comprobamos si se ha deshabilitado
        if (claveService.isAccesoClaveDeshabilitado()) {
            // Mostramos pagina generica de error
            return new ModelAndView("redirect:/error.html?code="
                    + ErrorCodes.CLAVE_DESHABILITADO.toString());
        }

        // Decodificamos URLs de B64
        String urlCallbackLogin, urlDestino;
        try {
            urlCallbackLogin = ConvertUtil
                    .base64UrlSafeToCadena(urlCallbackLoginB64);
            urlDestino = ConvertUtil.base64UrlSafeToCadena(urlDestinoB64);
        } catch (final Exception e) {
            log.error("Error decodificando urls", e);
            throw new ErrorFrontException("Error decodificando urls", e);
        }

        // Evaluamos idioma
        final String language = sanitizeIdioma(idioma);

        log.debug("urlCallbackLogin = " + urlCallbackLogin);
        log.debug("idioma = " + language);
        log.debug("urlDestino = " + urlDestino);

        // Establecemos idps en funcion metodos autenticacion
        final String idps = claveService.obtenerIdpsSistra(metodos);

        // Creamos sesion clave
        final String idSesion = claveService.crearSesionClave(urlCallbackLogin,
                urlDestino, language, idps, true);

        // Vemos si simulamos clave
        if (claveService.isAccesoClaveSimulado()) {
            final SimularClave simularClave = new SimularClave();
            simularClave.setIdSesion(idSesion);
            simularClave.setSistra(true);
            return new ModelAndView("simularClave", "simularClave",
                    simularClave);
        }

        // Generamos request SAML
        final PeticionClave peticionClave = claveService.generarPeticionClave(
                idSesion, true);

        // Iniciar sesion en clave
        final DatosInicioSesionClave isc = new DatosInicioSesionClave();
        isc.setIdps(peticionClave.getIdps());
        isc.setSamlRequest(peticionClave.getSamlRequestB64());
        isc.setUrlClave(peticionClave.getUrlClave());
        isc.setIdioma(peticionClave.getIdioma());

        log.debug("Redirigimos a clave");

        return new ModelAndView("inicioSesionClave", "datos", isc);
    }

    /**
     * Recibe peticion de inicio de sesion en Clave de Sistra y redirige a
     * Clave.
     * 
     * @param idSesion
     *            idSesion
     * @return pagina que realiza la redireccion a Clave
     */
    @RequestMapping(value = "/redirigirClave.html")
    public ModelAndView iniciarSesionClaveExterna(
            @RequestParam("idSesion") final String idSesion) {

        log.debug("Iniciar sesion clave para aplicacion externa");

        // Comprobamos si se ha deshabilitado
        if (claveService.isAccesoClaveDeshabilitado()) {
            // Mostramos pagina generica de error
            return new ModelAndView("redirect:/error.html?code="
                    + ErrorCodes.CLAVE_DESHABILITADO.toString());
        }

        // Vemos si simulamos clave
        if (claveService.isAccesoClaveSimulado()) {
            final SimularClave simularClave = new SimularClave();
            simularClave.setIdSesion(idSesion);
            simularClave.setSistra(false);
            return new ModelAndView("simularClave", "simularClave",
                    simularClave);
        }

        // Generamos request SAML
        final PeticionClave peticionClave = claveService.generarPeticionClave(
                idSesion, false);

        // Iniciar sesion en clave
        final DatosInicioSesionClave isc = new DatosInicioSesionClave();
        isc.setIdps(peticionClave.getIdps());
        isc.setSamlRequest(peticionClave.getSamlRequestB64());
        isc.setUrlClave(peticionClave.getUrlClave());
        isc.setIdioma(peticionClave.getIdioma());

        log.debug("Redirigimos a clave");

        return new ModelAndView("inicioSesionClave", "datos", isc);

    }

    /**
     * Retorno de Clave y vuelta a Sistra.
     * 
     * @param idSesion
     *            idSesion.
     * @param samlResponse
     *            samlResponse.
     * @return pagina que realiza la redireccion a Sistra tras el login en Clave
     */
    @RequestMapping(value = "/retornoClave/{idSesion}.html", method = RequestMethod.POST)
    public ModelAndView retornoClaveSistra(
            @PathVariable("idSesion") final String idSesion,
            @RequestParam("SAMLResponse") final String samlResponse) {
        return retornoClave(idSesion, samlResponse, true);
    }

    /**
     * Retorno de Clave y vuelta a aplicacion externa.
     * 
     * @param idSesion
     *            idSesion.
     * @param samlResponse
     *            samlResponse.
     * @return pagina que realiza la redireccion a aplicacion externa tras el
     *         login en Clave
     */
    @RequestMapping(value = "/retornoClaveExterna/{idSesion}.html", method = RequestMethod.POST)
    public ModelAndView retornoClaveExterna(
            @PathVariable("idSesion") final String idSesion,
            @RequestParam("SAMLResponse") final String samlResponse) {
        return retornoClave(idSesion, samlResponse, false);
    }

    /**
     * Simulamos acceso clave.
     * 
     * @param idSesion
     *            idSesion
     * @param sistra
     *            sistra
     * @param idp
     *            idp
     * @param nif
     *            nif
     * @param nombre
     *            nombre
     * @param apellidos
     *            apellidos
     * @return retorno aplicacion
     */
    @RequestMapping(value = "loginClaveSimulado.html", method = RequestMethod.POST)
    public ModelAndView loginClaveSimulado(
            @RequestParam("idSesion") final String idSesion,
            @RequestParam("sistra") final boolean sistra,
            @RequestParam("idp") final String idp,
            @RequestParam("nif") final String nif,
            @RequestParam("nombre") final String nombre,
            @RequestParam("apellidos") final String apellidos) {

        log.debug("Retorno clave simulado: id sesion = " + idSesion);

        // Generamos ticket autenticacion
        final TicketClave ticket = claveService.simularRespuestaClave(idSesion,
                idp, nif, nombre, apellidos, sistra);

        // Retornamos aplicacion
        log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
        final DatosRetornoClave drc = new DatosRetornoClave();
        drc.setTicket(ticket.getTicket());
        drc.setUrlCallbackLogin(ticket.getUrlCallback());
        drc.setIdioma(ticket.getIdioma());

        return new ModelAndView("retornoClave", "datos", drc);
    }

    /**
     * Retorno de Clave.
     * 
     * @param idSesion
     *            idSesion.
     * @param samlResponse
     *            samlResponse.
     * @param sistra
     *            Indica si es sistra, si no aplicacion externa.
     * @return pagina que realiza la redireccion a la aplicacion
     */
    private ModelAndView retornoClave(final String idSesion,
            final String samlResponse, final boolean sistra) {
        log.debug("Retorno clave: id sesion = " + idSesion);

        // Generamos ticket autenticacion
        final TicketClave ticket = claveService.procesarRespuestaClave(
                idSesion, samlResponse, sistra);

        // Retornamos aplicacion
        log.debug("Retornamos a aplicacion: ticket = " + ticket.getTicket());
        final DatosRetornoClave drc = new DatosRetornoClave();
        drc.setTicket(ticket.getTicket());
        drc.setUrlCallbackLogin(ticket.getUrlCallback());
        drc.setIdioma(ticket.getIdioma());
        return new ModelAndView("retornoClave", "datos", drc);
    }

    /**
     * Muestra error.
     * 
     * @param errorCode
     *            codigo error
     * @return pagina error
     */
    @RequestMapping("/error.html")
    public ModelAndView error(@RequestParam("code") final String errorCode) {
        // Mostramos pagina generica de error
        ErrorCodes error = ErrorCodes.fromString(errorCode);
        if (error == null) {
            error = ErrorCodes.ERROR_GENERAL;
        }
        return new ModelAndView("errorGeneral", "mensaje", error.toString());
    }

    /**
     * Handler de excepciones de negocio.
     * 
     * @param pex
     *            Excepción
     * @param request
     *            Request
     * @return Respuesta JSON indicando el mensaje producido
     */
    @ExceptionHandler({Exception.class})
    public final ModelAndView handleServiceException(final Exception pex,
            final HttpServletRequest request) {

        // Si no es una excepcion de negocio, generamos log
        final Exception ex = pex;
        if (!(pex instanceof ServiceException)) {
            log.error("Excepcion en capa front: " + pex.getMessage(), pex);
        }

        // Mostramos pagina generica de error
        return new ModelAndView("redirect:/error.html?code="
                + ErrorCodes.ERROR_GENERAL.toString());
    }

    // --- Metodos auxiliares

    /**
     * Obtiene idioma valido.
     * 
     * @param idioma
     *            Idioma
     * @return idioma
     */
    private String sanitizeIdioma(final String idioma) {
        String res = "es";
        if ("en".equals(idioma)) {
            res = "en";
        } else if ("ca".equals(idioma)) {
            res = "ca";
        }
        return res;
    }

}
