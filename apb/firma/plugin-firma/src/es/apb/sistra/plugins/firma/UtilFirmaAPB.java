package es.apb.sistra.plugins.firma;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import es.apb.sistra.plugins.firma.ws.ClienteWS;
import es.apb.sistra.plugins.firma.ws.apbfirma.FirmaWebService;
import es.apb.sistra.plugins.firma.ws.apbfirma.FormatoFirma;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

/**
 * Utilidades firma.
 * 
 * @author rsanz
 * 
 */
public class UtilFirmaAPB {

    /**
     * Serializa firma.
     * 
     * @param signatureData
     * @return
     * @throws Exception
     */
    public static byte[] serializaFirmaToBytes(final FirmaAPB signatureData)
            throws Exception {

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        final Map<String, String> datos = new HashMap<String, String>();
        datos.put("formatoFirma", signatureData.getFormatoFirma());
        datos.put(
                "contenidoFirma",
                new String(Base64.encodeBase64(signatureData
                        .getContenidoFirma()), "UTF-8"));
        datos.put("certificadoB64", signatureData.getCertificadoB64());
        datos.put("nif", signatureData.getNif());
        datos.put("nombreApellidos", signatureData.getNombreApellidos());
        datos.put("nifRepresentante", signatureData.getNifRepresentante());
        datos.put("nombreApellidosRepresentante",
                signatureData.getNombreApellidosRepresentante());

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(bos, datos);

        final byte[] serialized = bos.toByteArray();
        bos.close();

        return serialized;
    }

    /**
     * Deserializa firma.
     * 
     * @param serialicedSignature
     * @return
     * @throws Exception
     */
    public static FirmaAPB deserializaFirmaFromBytes(
            final byte[] serialicedSignature) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ByteArrayInputStream bis = new ByteArrayInputStream(
                serialicedSignature);
        final TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };
        final Map<String, String> datos = objectMapper.readValue(bis, typeRef);

        final String formatoFirma = datos.get("formatoFirma");
        final byte[] contenidoFirma = Base64.decodeBase64(datos.get(
                "contenidoFirma").getBytes("UTF-8"));
        final String certificadoB64 = datos.get("certificadoB64");
        final String nif = datos.get("nif");
        final String nombreApellidos = datos.get("nombreApellidos");
        final String nifRepresentante = datos.get("nifRepresentante");
        final String nombreApellidosRepresentante = datos
                .get("nombreApellidosRepresentante");

        final FirmaAPB f = new FirmaAPB(contenidoFirma, formatoFirma, nif,
                nombreApellidos, certificadoB64, nifRepresentante,
                nombreApellidosRepresentante);

        return f;

    }

    /**
     * Genera cliente APB Firma.
     * 
     * @return cliente APB Firma.
     * @throws Exception
     */
    public static FirmaWebService generarClienteApbFirma() throws Exception {
        final String url = ConfigurationUtil.getInstance().obtenerPropiedades()
                .getProperty("apb-firma.url");
        final String user = ConfigurationUtil.getInstance()
                .obtenerPropiedades().getProperty("apb-firma.usr");
        final String pass = ConfigurationUtil.getInstance()
                .obtenerPropiedades().getProperty("apb-firma.pwd");
        final FirmaWebService port = ClienteWS.generarPort(url, user, pass);
        return port;
    }

    /**
     * Traduce formato de firma de PluginFirma y Servicio ApbFirma.
     * 
     * @param formato
     *            Formato ApbFirma
     * @return
     */
    public static String traducirFormatoApbFirmaToPlgFirma(
            final FormatoFirma formato) {
        String result = null;
        switch (formato) {
        case CADES_DETACHED:
            result = PluginFirmaIntf.FORMATO_FIRMA_CADES_DETACHED;
            break;
        case XADES_DETACHED:
            result = PluginFirmaIntf.FORMATO_FIRMA_XADES_DETACHED;
            break;
        default:
            throw new RuntimeException("Formato de firma " + formato.name()
                    + " no soportado por Plugin");
        }
        return result;
    }

    /**
     * Traduce formato de firma Servicio ApbFirma a PluginFirma.
     * 
     * @param formato
     *            Formato ApbFirma
     * @return
     */
    public static FormatoFirma traducirFormatoPlgFirmaToApb(final String formato) {
        FormatoFirma result = null;
        if (PluginFirmaIntf.FORMATO_FIRMA_CADES_DETACHED.equals(formato)) {
            result = FormatoFirma.CADES_DETACHED;
        } else if (PluginFirmaIntf.FORMATO_FIRMA_XADES_DETACHED.equals(formato)) {
            result = FormatoFirma.XADES_DETACHED;
        } else if (PluginFirmaIntf.FORMATO_FIRMA_PADES.equals(formato)) {
            result = FormatoFirma.PADES;
        } else {
            throw new RuntimeException("Formato de firma " + formato
                    + " no soportado por servicio apb-firma");
        }
        return result;
    }

    /**
     * Cambia los caracateres B64 que pueden dar problemas.
     * 
     * @param b64
     * @return
     */
    public static String unescapeChars64UrlSafe(String b64) {
        b64 = b64.replaceAll("-", "+");
        b64 = b64.replaceAll("_", "/");
        return b64;
    }
}
