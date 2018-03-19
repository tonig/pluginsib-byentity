package es.apb.login.negocio;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.apb.login.negocio.exception.ErrorNoControladoException;
import es.apb.login.negocio.exception.ErrorRespuestaClaveException;
import es.apb.login.negocio.exception.GenerarPeticionClaveException;
import es.apb.login.negocio.exception.TicketNoValidoException;
import es.apb.login.negocio.interceptor.NegocioInterceptor;
import es.apb.login.negocio.model.comun.ConstantesNumero;
import es.apb.login.negocio.model.login.DatosRepresentante;
import es.apb.login.negocio.model.login.DatosSesion;
import es.apb.login.negocio.model.login.DatosUsuario;
import es.apb.login.negocio.model.login.PeticionClave;
import es.apb.login.negocio.model.login.TicketClave;
import es.apb.login.negocio.model.login.TicketDatos;
import es.apb.login.negocio.repository.dao.ClaveDao;
import es.apb.login.negocio.util.AFirmaUtil;
import es.apb.login.negocio.util.NifCif;
import eu.stork.peps.auth.commons.IPersonalAttributeList;
import eu.stork.peps.auth.commons.PEPSUtil;
import eu.stork.peps.auth.commons.PersonalAttribute;
import eu.stork.peps.auth.commons.PersonalAttributeList;
import eu.stork.peps.auth.commons.STORKAuthnRequest;
import eu.stork.peps.auth.commons.STORKAuthnResponse;
import eu.stork.peps.auth.engine.STORKSAMLEngine;
import eu.stork.peps.exceptions.STORKSAMLEngineException;

/**
 * Implementación SecurityService.
 * 
 * @author Indra
 * 
 */
@Service("claveService")
@Transactional
public final class ClaveServiceImpl implements ClaveService {

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(ClaveServiceImpl.class);

    /** Fichero configuracion Clave. */
    private static final String SP_CONF = "SP";

    /** Configuracion. */
    @Resource(name = "negocioModuleConfig")
    private ModuleConfig config;

    /** Dao. */
    @Resource(name = "claveDao")
    private ClaveDao claveDao;

    @Override
    @NegocioInterceptor
    public String crearSesionClave(final String pUrlCallback,
            final String pUrlDestino, final String idioma, final String idps,
            final boolean sistra) {
        log.debug(getAplicacion(sistra) + " Crea sesion clave...");
        final String idSesion = claveDao.crearSesion(pUrlCallback, pUrlDestino,
                idioma, idps, sistra);
        log.debug(getAplicacion(sistra) + " Creada sesion clave: " + idSesion);
        return idSesion;
    }

    @Override
    @NegocioInterceptor
    public PeticionClave generarPeticionClave(final String idSesion,
            final boolean sistra) {

        log.debug(getAplicacion(sistra) + " Generar peticion clave... ");

        // Obtener datos sesion
        final DatosSesion datosSesion = claveDao.obtenerDatosSesion(idSesion,
                sistra);
        if (datosSesion.getFechaTicket() != null) {
            throw new GenerarPeticionClaveException(
                    "sesion ya ha sido autenticada en clave");
        }
        final Date ahora = new Date();
        if (ahora.getTime() - datosSesion.getFechaInicioSesion().getTime() > (config
                .getTimeoutSesionExterna() * ConstantesNumero.N1000)) {
            throw new GenerarPeticionClaveException("sesion ha expirado");
        }

        // Atributos a consultar
        final IPersonalAttributeList pAttList = new PersonalAttributeList();
        /* eIdentifier */
        PersonalAttribute att = new PersonalAttribute();
        att.setName("eIdentifier");
        att.setIsRequired(true);
        pAttList.add(att);
        /* givenName */
        att = new PersonalAttribute();
        att.setName("givenName");
        att.setIsRequired(true);
        pAttList.add(att);
        /* inheritedFamilyName: desglose apellidos */
        att = new PersonalAttribute();
        att.setName("inheritedFamilyName");
        att.setIsRequired(false);
        pAttList.add(att);
        /* surname */
        att = new PersonalAttribute();
        att.setName("surname");
        att.setIsRequired(false);
        pAttList.add(att);
        /* respuesta @firma */
        att = new PersonalAttribute();
        att.setName("afirmaResponse");
        att.setIsRequired(false);
        pAttList.add(att);

        // Parametros peticion
        STORKAuthnRequest authnRequest = new STORKAuthnRequest();
        authnRequest.setDestination(config.getPepsUrl());
        authnRequest.setProviderName(config.getProviderName());
        authnRequest.setQaa(Integer.parseInt(config.getQaa()));
        authnRequest.setPersonalAttributeList(pAttList);
        authnRequest.setAssertionConsumerServiceURL((sistra ? config
                .getReturnUrlSistra() : config.getReturnUrlExterna())
                + "/"
                + idSesion + ".html");
        authnRequest.setSpSector(config.getSpSector());
        authnRequest.setSpApplication(config.getSpApplication());
        authnRequest.setSPID(config.getSpId());

        // Generamos peticion SAML
        final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(SP_CONF);
        try {
            authnRequest = engine.generateSTORKAuthnRequest(authnRequest);
        } catch (final STORKSAMLEngineException e) {
            throw new GenerarPeticionClaveException(e);
        }

        // Pasamos a B64 y retornamos
        final byte[] token = authnRequest.getTokenSaml();
        final String samlRequestB64 = PEPSUtil.encodeSAMLToken(token);
        final String samlRequestXML = new String(token);
        log.debug(getAplicacion(sistra) + " Peticion generada: "
                + samlRequestXML);

        // Devolvemos datos necesarios para invocar a Clave
        final PeticionClave peticionClave = new PeticionClave();
        peticionClave.setSamlRequestB64(samlRequestB64);
        peticionClave.setUrlClave(config.getPepsUrl());
        peticionClave.setIdioma(datosSesion.getIdioma());
        peticionClave.setIdps(datosSesion.getIdps());
        return peticionClave;
    }

    @Override
    @NegocioInterceptor
    public TicketClave procesarRespuestaClave(final String pIdSesion,
            final String pSamlResponseB64, final boolean sistra) {

        log.debug(getAplicacion(sistra) + " Procesando respuesta clave");

        log.debug(getAplicacion(sistra) + " Respuesta clave: "
                + pSamlResponseB64);

        // Decodifica respuesta Clave
        STORKAuthnResponse authnResponse = null;
        // final IPersonalAttributeList personalAttributeList = null;
        final byte[] decSamlToken = PEPSUtil.decodeSAMLToken(pSamlResponseB64);
        final STORKSAMLEngine engine = STORKSAMLEngine.getInstance(SP_CONF);
        try {
            authnResponse = engine.validateSTORKAuthnResponse(decSamlToken, "");
        } catch (final STORKSAMLEngineException e) {
            throw new ErrorRespuestaClaveException(e);
        }

        // Verificamos si hay error al interpretar la respuesta
        if (authnResponse.isFail()) {
            throw new ErrorRespuestaClaveException(
                    "La respuesta indica que hay un error : "
                            + authnResponse.getMessage());
        }

        // Obtenemos atributos
        final String idp = authnResponse.getAssertions().get(0).getIssuer()
                .getValue();
        log.debug(getAplicacion(sistra) + " Idp: " + idp);
        String nifClave = null;
        String nombre = null;
        String apellidos = null;
        String apellido1 = null;
        String apellido2 = null;
        String afirmaResponse = null;
        final ArrayList<PersonalAttribute> attrList = new ArrayList<PersonalAttribute>(
                authnResponse.getPersonalAttributeList().values());
        for (final PersonalAttribute pa : attrList) {
            log.debug(getAplicacion(sistra) + " Atributo: " + pa.getName()
                    + "- Valor: " + listToString(pa.getValue()));
            if ("eIdentifier".equals(pa.getName())) {
                nifClave = pa.getValue().get(0);
                log.debug(getAplicacion(sistra) + " eIdentifier: "
                        + pa.getValue().get(0) + " -> NIF: " + nifClave);
            } else if ("givenName".equals(pa.getName())) {
                nombre = pa.getValue().get(0);
                log.debug(getAplicacion(sistra) + " givenName: "
                        + pa.getValue().get(0) + " -> Nombre: " + nombre);
            } else if ("inheritedFamilyName".equals(pa.getName())) {
                apellido1 = pa.getValue().get(0);
                log.debug("inheritedFamilyName -> Apellido1: " + apellido1);
            } else if ("surname".equals(pa.getName())) {
                apellidos = pa.getValue().get(0);
                log.debug(getAplicacion(sistra) + " surname: "
                        + pa.getValue().get(0) + " -> Apellidos: " + nombre);
            } else if ("afirmaResponse".equals(pa.getName())) {
                afirmaResponse = pa.getValue().get(0);
            }
        }

        // Extrae nif
        String nif = extraerNif(nifClave);
        if (nif == null) {
            throw new ErrorRespuestaClaveException(
                    "La respuesta devuelve un nif no valido: " + nifClave);
        }

        // Si es cif, no contempla apellidos
        if (NifCif.esCIF(nifClave)) {
            apellido1 = null;
            apellido2 = null;
            apellidos = null;
        } else {
            // Extrae apellidos si vienen desglosados
            if (apellido1 != null && apellidos != null) {
                // Control extranjeros, solo 1 apellido
                if (apellidos.length() > apellido1.length()) {
                    apellido2 = apellidos.substring(apellido1.length() + 1);
                }
            }
        }

        // Verificamos si son certificados de tipo 11 y 12 para extraer la
        // persona juridica
        DatosRepresentante representante = null;
        if (afirmaResponse != null) {
            final Map<String, String> infoCertificado = AFirmaUtil
                    .extraerInfoCertificado(afirmaResponse);
            final String clasificacion = infoCertificado.get("clasificacion");
            if ("11".equals(clasificacion) || "12".equals(clasificacion)) {
                // Datos representante
                representante = new DatosRepresentante();
                representante.setNif(nif);
                representante.setNombre(nombre);
                representante.setApellido1(apellido1);
                representante.setApellido2(apellido2);

                // Datos persona juridica
                nif = infoCertificado.get("NIF-CIF");
                nombre = infoCertificado.get("razonSocial");
                apellidos = null;
            }
        }

        // Almacenar en tabla y generar ticket sesion (OTP)
        log.debug(" Datos obtenidos clave: Nivel=" + idp + ", Nif=" + nif
                + ", Nombre=" + nombre + " " + apellidos);
        final TicketClave respuesta = claveDao.generateTicket(pIdSesion, idp,
                nif, nombre, apellidos, representante, sistra);

        log.debug(getAplicacion(sistra) + " Ticket generado: "
                + respuesta.getTicket());

        return respuesta;
    }

    @Override
    @NegocioInterceptor
    public TicketClave simularRespuestaClave(final String pIdSesion,
            final String pIdp, final String pNif, final String pNombre,
            final String pApellidos, final boolean pSistra) {

        if (!isAccesoClaveSimulado()) {
            throw new TicketNoValidoException("No se puede simular sesion.");
        }

        final TicketClave respuesta = claveDao.generateTicket(pIdSesion, pIdp,
                pNif, pNombre, pApellidos, null, pSistra);
        return respuesta;
    }

    @Override
    @NegocioInterceptor
    public String obtenerIdpsSistra(final String metodos) {
        String idps = "";
        if (metodos.contains("C")) {
            // Acceso por certificado
            idps += "aFirma;";
        }
        if (metodos.contains("U")) {
            // Acceso por certificado
            idps += "AEAT;SS;";
        }
        if (idps.length() > 0) {
            idps = idps.substring(0, idps.length() - ConstantesNumero.N1);
        }
        return idps;
    }

    @Override
    @NegocioInterceptor
    public boolean isAccesoClaveDeshabilitado() {
        return config.isAccesoClaveDeshabilitado();
    }

    @Override
    @NegocioInterceptor
    public void purgar() {
        claveDao.purgaTicketSesionExterna(config.getTimeoutSesionExterna(),
                config.getTimeoutTicketExterna());
    }

    @Override
    @NegocioInterceptor
    public DatosUsuario obtenerDatosUsuarioAplicacionExterna(
            final String pTicket) {

        final TicketDatos t = claveDao.consumirTicketSesionExterna(pTicket);

        // No existe ticket
        if (t == null || t.getFecha() == null) {
            throw new TicketNoValidoException("No existe ticket");
        }

        // Ticket caducado
        if (t.getFecha() != null
                && (new Date()).getTime() - t.getFecha().getTime() > (config
                        .getTimeoutTicketExterna() * ConstantesNumero.N1000)) {
            throw new TicketNoValidoException("Ticket caducado");
        }

        // Devuelve datos usuario
        return t.getUsuario();
    }

    @Override
    @NegocioInterceptor
    public String obtenerUrlInicioExterna(final String pIdSesion) {
        try {
            return config.getInicioUrlExterna() + "?idSesion="
                    + URLEncoder.encode(pIdSesion, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new ErrorNoControladoException(e);
        }
    }

    @Override
    @NegocioInterceptor
    public boolean isAccesoClaveSimulado() {
        return config.isAccesoClaveSimulado();
    }

    /**
     * Extrae nif del formato de nif Clave ES/ES/nif. Los cifs no llevan
     * prefijo.
     * 
     * @param pNifClave
     *            Nif formato clave
     * @return nif
     */
    private String extraerNif(final String pNifClave) {
        String nif = null;
        if (pNifClave != null) {
            final String nifClave = pNifClave.trim();
            final int pos = nifClave.lastIndexOf("ES/ES/");
            if (pos >= 0) {
                // Nifs
                nif = nifClave.substring(pos + "ES/ES/".length());
            } else {
                // Cifs: no tienen prefijo (validamos que tenga formato CIF)
                if (Pattern
                        .matches(
                                "[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}",
                                nifClave)) {
                    nif = nifClave;
                }
            }
        }
        return nif;
    }

    /**
     * Obtiene aplicación (sistra o aplicación externa).
     * 
     * @param sistra
     *            Sistra
     * @return aplicacion
     */
    private String getAplicacion(final boolean sistra) {
        return (sistra ? "[Sistra]" : "[Aplicacion externa]");
    }

    /**
     * Convierte a String.
     * 
     * @param list
     *            list
     * @return string
     */
    private String listToString(final List<String> list) {
        String res = "";
        if (list != null) {
            for (final String s : list) {
                res += s + " ";
            }
        }
        return res;
    }
}
